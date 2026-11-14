import java.text.SimpleDateFormat;
import java.util.Date;
public class Actividad extends ElementoPlanificable {

    private Date fechaHora;

    public Actividad(String id, String titulo, String descripcion, Date fechaHora) {
        super(id, titulo, descripcion);
        this.fechaHora = fechaHora;
    }

    public Date getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(Date fechaHora) {
        this.fechaHora = fechaHora;
    }

    public int getMes() {
        return Integer.parseInt(new SimpleDateFormat("MM").format(fechaHora));
    }

    public int getAnio() {
        return Integer.parseInt(new SimpleDateFormat("yyyy").format(fechaHora));
    }

    public String getTipo() {
        return "Actividad";
    }

    public String toString() {
        return "[" + getTipo() + " " + getId() + "] " + getTitulo() + " | "
                + new SimpleDateFormat("dd/MM/yyyy HH:mm").format(fechaHora) + " | "
                + (isCompletada() ? "Completada" : "Pendiente");
    }
}
