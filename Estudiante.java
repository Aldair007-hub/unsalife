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


    public ReporteMensual generarReporte(Fecha fecha) {
        int mes = fecha.getMes();
        int anio = fecha.getAnio();
        double total = 0;
        HashMap<String, Double> porCategoria = new HashMap<>();
        Presupuesto p = buscarPresupuesto(fecha);
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
    private ArrayList<Presupuesto> presupuestos = new ArrayList<>();

    public ArrayList<Presupuesto> getPresupuestos() {
        return presupuestos;
    }

    public Presupuesto buscarPresupuesto(Fecha fecha) {
        int mes = fecha.getMes();
        int anio = fecha.getAnio();
        for (Presupuesto p : presupuestos) {
            if (p.getMes() == mes && p.getAnio() == anio) {
                return p;
            }
        }
        return null;
    }

    public boolean crearPresupuesto(Presupuesto p) {
        Fecha fecha = new Fecha(p.getMes(), p.getAnio());
        if (buscarPresupuesto(fecha) != null) {
            return false;
        }
        presupuestos.add(p);
        return true;
    }

    public boolean registrarGasto(Gasto g) {
        Fecha fecha = new Fecha(g.getFecha().getMes(), g.getFecha().getAnio());
        Presupuesto p = buscarPresupuesto(fecha);
        if (p == null) {
            return false;
        }
        p.agregarGasto(g);
        return true;
    }

    public String toString() {
        return idEstudiante + " | " + nombre + " | " + correo;
    }
