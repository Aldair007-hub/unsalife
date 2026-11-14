import java.util.ArrayList;

public abstract class ElementoPlanificable {

    private String id;
    private String titulo;
    private String descripcion;
    private boolean completada;
    private ArrayList<Recordatorio> recordatorios = new ArrayList<>();

    public ElementoPlanificable(String id, String titulo, String descripcion) {
        this.id = id;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.completada = false;
    }

    public abstract String getTipo();

    public String getId() {
        return id;
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

    public boolean isCompletada() {
        return completada;
    }

    public void marcarCompletada() {
        this.completada = true;
    }

    public ArrayList<Recordatorio> getRecordatorios() {
        return recordatorios;
    }

    public void agregarRecordatorio(Recordatorio r) {
        recordatorios.add(r);
    }

    public String toString() {
        return "[" + getTipo() + " " + id + "] " + titulo + " | " + (completada ? "Completada" : "Pendiente");
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ElementoPlanificable)) {
            return false;
        }
        ElementoPlanificable otro = (ElementoPlanificable) o;
        return id.equals(otro.id);
    }

}
