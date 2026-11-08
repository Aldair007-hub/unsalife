public class Tarea {

    private String idTarea;
    private String titulo;
    private String descripcion;
    private Fecha fechaLimite;
    private boolean completada;

    public Tarea(String idTarea, String titulo, String descripcion, Fecha fechaLimite) {
        this.idTarea = idTarea;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.fechaLimite = fechaLimite;
        this.completada = false;
    }

    public String getIdTarea() {
        return idTarea;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Fecha getFechaLimite() {
        return fechaLimite;
    }

    public boolean isCompletada() {
        return completada;
    }

    public void marcarCompletada() {
        this.completada = true;
    }

    public boolean estaVencida(Fecha hoy) {
        return !completada && hoy.esPosteriorA(fechaLimite);
    }

    public String toString() {
        return "[Tarea " + idTarea + "] " + titulo + " | Límite: " + fechaLimite + " | " + (completada ? "Completada" : "Pendiente");
    }
}
