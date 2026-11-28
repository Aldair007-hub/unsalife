import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Scanner;

public class Sesion {

    private Estudiante estudiante;
    private Fecha hoy;
    private HashMap<String, Integer> contadores = new HashMap<>();

    public Sesion() {
        this.hoy = new Fecha(new Date());
        this.estudiante = new Estudiante("", "", "");
    }

    public Sesion(String nombre, String correo) {
        this.hoy = new Fecha(new Date());
        this.estudiante = new Estudiante(generarId("EST"), nombre, correo);
    }

    public void iniciarEstudiante(String nombre, String correo) {
        this.estudiante = new Estudiante(generarId("EST"), nombre, correo);
    }

    public Estudiante getEstudiante() {
        return estudiante;
    }

    public Fecha getHoy() {
        return hoy;
    }

    public String generarId(String prefijo) {
        int numero = 1;
        if (contadores.containsKey(prefijo)) {
            numero = contadores.get(prefijo) + 1;
        }
        contadores.put(prefijo, numero);
        return prefijo + "-" + numero;
    }

    public String archivoDatos(String correo) {
        return "unsalife_" + correo.trim().toLowerCase() + ".dat";
    }

    private String archivoHistorial(String correo) {
        return "historial_" + correo.trim().toLowerCase() + ".txt";
    }

    public String getArchivoDatos() {
        return archivoDatos(estudiante.getCorreo());
    }

    public String getArchivoHistorial() {
        return archivoHistorial(estudiante.getCorreo());
    }

    public String getArchivoReporte(ReporteMensual reporte) {
        String mes = "" + reporte.getMes();
        if (reporte.getMes() < 10) {
            mes = "0" + mes;
        }
        return "reporte_" + estudiante.getCorreo().trim().toLowerCase() + "_" + reporte.getAnio() + "-" + mes + ".txt";
    }

    public int cargar(String correo) {
        try {
            ObjectInputStream fileIn = new ObjectInputStream(new FileInputStream(archivoDatos(correo)));
            Estudiante leido = (Estudiante) fileIn.readObject();
            HashMap<String, Integer> leidos = (HashMap<String, Integer>) fileIn.readObject();
            fileIn.close();
            estudiante = leido;
            contadores = leidos;
            return 0;
        } catch (FileNotFoundException e) {
            return 1;
        } catch (IOException e) {
            return 2;
        } catch (ClassNotFoundException e) {
            return 2;
        }
    }

    public boolean guardar() {
        try {
            ObjectOutputStream fileOut = new ObjectOutputStream(new FileOutputStream(getArchivoDatos()));
            fileOut.writeObject(estudiante);
            fileOut.writeObject(contadores);
            fileOut.close();
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public boolean registrarHistorial(String accion) {
        try {
            PrintWriter fileOut = new PrintWriter(new FileWriter(getArchivoHistorial(), true));
            fileOut.println(new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()) + " | " + accion);
            fileOut.close();
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public ArrayList<String> leerHistorial() {
        ArrayList<String> lineas = new ArrayList<>();
        try {
            Scanner fileIn = new Scanner(new FileReader(getArchivoHistorial()));
            while (fileIn.hasNextLine()) {
                lineas.add(fileIn.nextLine());
            }
            fileIn.close();
        } catch (FileNotFoundException e) {
        }
        return lineas;
    }

    public boolean exportarReporte(ReporteMensual reporte) {
        try {
            PrintWriter fileOut = new PrintWriter(getArchivoReporte(reporte));
            fileOut.println("UNSALIFE - REPORTE MENSUAL");
            fileOut.println("Estudiante: " + estudiante.getNombre() + " (" + estudiante.getCorreo() + ")");
            fileOut.println("Exportado el: " + new SimpleDateFormat("dd/MM/yyyy HH:mm").format(new Date()));
            fileOut.println();
            fileOut.println(reporte);
            fileOut.println();
            fileOut.print(reporte.generarGrafico());
            fileOut.close();
            return true;
        } catch (FileNotFoundException e) {
            return false;
        }
    }
}
