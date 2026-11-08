public class Gasto {

    private String idGasto;
    private double monto;
    private String categoria;
    private Fecha fecha;
    private String descripcion;

    public Gasto(String idGasto, double monto, String categoria, Fecha fecha, String descripcion) {
        this.idGasto = idGasto;
        this.monto = monto;
        this.categoria = categoria;
        this.fecha = fecha;
        this.descripcion = descripcion;
    }

    public String getIdGasto() {
        return idGasto;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public Fecha getFecha() {
        return fecha;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        String texto = idGasto + " | " + fecha + " | " + categoria + " | S/ " + (Math.round(monto * 100) / 100.0);
        if (!descripcion.isEmpty()) {
            texto += " | " + descripcion;
        }
        return texto;
    }
}
