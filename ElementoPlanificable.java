public abstract class ElementoPlanificable {

    private String id;
    private String titulo;
    private String descripcion;
    private boolean completada;

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
        completada = true;
    }

    public String toString() {
        return "[" + getTipo() + " " + id + "] " + titulo + " | "
                + (completada ? "Completada" : "Pendiente");
    }

    public boolean equals(Object objeto) {
        if (this == objeto) {
            return true;
        }
        if (!(objeto instanceof ElementoPlanificable)) {
            return false;
        }
        ElementoPlanificable otro = (ElementoPlanificable) objeto;
        return id.equals(otro.id);
    }
}
