public class Tarea extends ElementoPlanificable {
    private Fecha fechaLimite;

    public Tarea(String id, String titulo, String descripcion, Fecha fechaLimite) {
        super(id, titulo, descripcion);
        this.fechaLimite = fechaLimite;
    }

    public Fecha getFechaLimite() {
        return fechaLimite;
    }

    public void setFechaLimite(Fecha fechaLimite) {
        this.fechaLimite = fechaLimite;
    }

    public boolean estaVencida(Fecha hoy) {
        return !isCompletada() && hoy.esPosteriorA(fechaLimite);
    }

    public String getTipo() {
        return "Tarea";
    }

    public String toString() {
        return "[" + getTipo() + " " + getId() + "] " + getTitulo() + " | Límite: " + fechaLimite + " | "
                + (isCompletada() ? "Completada" : "Pendiente");
    }
}
