import java.util.*;
public class Presupuesto {

    private String idPresupuesto;
    private double montoLimite;
    private int mes;
    private int anio;
    private ArrayList<Gasto> gastos = new ArrayList<>();

    public Presupuesto(String idPresupuesto, double montoLimite, int mes, int anio) {
        this.idPresupuesto = idPresupuesto;
        this.montoLimite = montoLimite;
        this.mes = mes;
        this.anio = anio;
    }

    public String getIdPresupuesto() {
        return idPresupuesto;
    }

    public double getMontoLimite() {
        return montoLimite;
    }

    public void setMontoLimite(double montoLimite) {
        this.montoLimite = montoLimite;
    }

    public int getMes() {
        return mes;
    }

    public int getAnio() {
        return anio;
    }

    public ArrayList<Gasto> getGastos() {
        return gastos;
    }

    public void agregarGasto(Gasto g) {
        gastos.add(g);
    }

    public double calcularTotalGastado() {
        double total = 0;
        for (Gasto g : gastos) {
            total += g.getMonto();
        }
        return total;
    }

    public double calcularSaldoRestante() {
        return montoLimite - calcularTotalGastado();
    }

    public boolean verificarLimite() {
        return calcularTotalGastado() <= montoLimite;
    }

    public String toString() {
        return idPresupuesto + " | " + Fecha.textoMesAnio(mes, anio) + " | Límite: S/ "
                + (Math.round(montoLimite * 100) / 100.0) + " | Gastado: S/ "
                + (Math.round(calcularTotalGastado() * 100) / 100.0);
    }
}
