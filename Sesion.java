import java.util.Date;
import java.util.HashMap;

public class Sesion {

    private Estudiante estudiante;
    private Fecha hoy;
    private HashMap<String, Integer> contadores = new HashMap<>();

    public Sesion() {
        this.hoy = new Fecha(new Date());
        this.estudiante = new Estudiante(generarId("EST"), "", "");
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

    /** Genera ids correlativos por prefijo: PRE-1, PRE-2, GAS-1, ... */
    public String generarId(String prefijo) {
        int numero = 1;
        if (contadores.containsKey(prefijo)) {
            numero = contadores.get(prefijo) + 1;
        }
        contadores.put(prefijo, numero);
        return prefijo + "-" + numero;
    }
}
