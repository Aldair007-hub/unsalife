import java.text.SimpleDateFormat;
import java.util.Date;

public class Recordatorio {

    private String idRecordatorio;
    private Date fechaHoraAlarma;
    private String mensaje;
    private boolean activo;

    public Recordatorio(String idRecordatorio, Date fechaHoraAlarma, String mensaje, boolean activo) {
        this.idRecordatorio = idRecordatorio;
        this.fechaHoraAlarma = fechaHoraAlarma;
        this.mensaje = mensaje;
        this.activo = activo;
    }

    public String getIdRecordatorio() {
        return idRecordatorio;
    }

    public Date getFechaHoraAlarma() {
        return fechaHoraAlarma;
    }

    public void setFechaHoraAlarma(Date fechaHoraAlarma) {
        this.fechaHoraAlarma = fechaHoraAlarma;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public boolean estaPendiente(Date ahora) {
        return activo && !fechaHoraAlarma.after(ahora);
    }

    public void activarNotificacion() {
        if (activo) {
            System.out.println("   RECORDATORIO (" + new SimpleDateFormat("dd/MM/yyyy HH:mm").format(fechaHoraAlarma)
                    + "): " + mensaje);
        } else {
            System.out.println("  (Recordatorio " + idRecordatorio + " desactivado)");
        }
    }

    public String toString() {
        return idRecordatorio + " | " + new SimpleDateFormat("dd/MM/yyyy HH:mm").format(fechaHoraAlarma) + " | "
                + mensaje + " | " + (activo ? "Activo" : "Inactivo");
    }
}
