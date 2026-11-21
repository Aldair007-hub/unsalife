public class Actividad extends ElementoPlanificable {

    private Fecha fecha;

    public Actividad(String id, String titulo, String descripcion, Fecha fecha) {
        super(id, titulo, descripcion);
        this.fecha = fecha;
    }

    public Fecha getFecha() {
        return fecha;
    }

    public void setFecha(Fecha fecha) {
        this.fecha = fecha;
    }

    public int getMes() {
        return fecha.getMes();
    }

    public int getAnio() {
        return fecha.getAnio();
    }

    public String getTipo() {
        return "Actividad";
    }

    public String toString() {
        return "[" + getTipo() + " " + getId() + "] " + getTitulo() + " | "
                + fecha + " | " + (isCompletada() ? "Completada" : "Pendiente");
    }
}
