import java.util.ArrayList;
import java.util.HashMap;

public class Estudiante {

    private String idEstudiante;
    private String nombre;
    private String correo;
    private ArrayList<ReporteMensual> reportes = new ArrayList<>();

    public Estudiante(String idEstudiante, String nombre, String correo) {
        this.idEstudiante = idEstudiante;
        this.nombre = nombre;
        this.correo = correo;
    }

    public String getIdEstudiante() {
        return idEstudiante;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public ArrayList<ReporteMensual> getReportes() {
        return reportes;
    }

    public ReporteMensual generarReporte(int mes, int anio) {
        double total = 0;
        HashMap<String, Double> porCategoria = new HashMap<>();
        Presupuesto p = buscarPresupuesto(mes, anio);
        if (p != null) {
            total = p.calcularTotalGastado();
            for (Gasto g : p.getGastos()) {
                double acumulado = 0;
                if (porCategoria.containsKey(g.getCategoria())) {
                    acumulado = porCategoria.get(g.getCategoria());
                }
                porCategoria.put(g.getCategoria(), acumulado + g.getMonto());
            }
        }

        int metasCumplidas = 0;
        for (MetaAcademica m : metas) {
            Fecha limite = m.getFechaLimite();
            if (m.estaCumplida() && limite.getMes() == mes && limite.getAnio() == anio) {
                metasCumplidas++;
            }
        }

        String id = "REP-" + anio + "-" + mes;
        ReporteMensual reporte = new ReporteMensual(id, mes, anio, total, metasCumplidas, porCategoria);


        for (int i = 0; i < reportes.size(); i++) {
            if (reportes.get(i).getIdReporte().equals(id)) {
                reportes.remove(i);
                break;
            }
        }
        reportes.add(reporte);
        return reporte;
    }


    public String toString() {
        return idEstudiante + " | " + nombre + " | " + correo;
    }
