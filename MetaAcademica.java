import java.util.ArrayList;

public class MetaAcademica {

    private String idMeta;
    private String titulo;
    private String materia;
    private double progreso; 
    private Fecha fechaLimite;
    private ArrayList<Tarea> tareas = new ArrayList<>();

    public MetaAcademica(String idMeta, String titulo, String materia, Fecha fechaLimite) {
        this.idMeta = idMeta;
        this.titulo = titulo;
        this.materia = materia;
        this.fechaLimite = fechaLimite;
        this.progreso = 0;
    }

    public String getIdMeta() {
        return idMeta;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getMateria() {
        return materia;
    }

    public double getProgreso() {
        return progreso;
    }

    public Fecha getFechaLimite() {
        return fechaLimite;
    }

    public ArrayList<Tarea> getTareas() {
        return tareas;
    }

    public void agregarTarea(Tarea t) {
        tareas.add(t);
        actualizarProgreso();
    }

    public Tarea buscarTarea(String idTarea) {
        for (Tarea t : tareas) {
            if (t.getId().equalsIgnoreCase(idTarea)) {
                return t;
            }
        }
        return null;
    }

    public void actualizarProgreso() {
        if (tareas.isEmpty()) {
            progreso = 0;
            return;
        }
        int completadas = 0;
        for (Tarea t : tareas) {
            if (t.isCompletada()) {
                completadas++;
            }
        }
        progreso = completadas * 100.0 / tareas.size();
    }

    public boolean estaCumplida() {
        return !tareas.isEmpty() && progreso >= 100;
    }

    public String toString() {
        return "[Meta " + idMeta + "] " + titulo + " (" + materia + ") | Límite: " + fechaLimite + " | Progreso: " + (Math.round(progreso * 10) / 10.0) + "%";
    }
}
