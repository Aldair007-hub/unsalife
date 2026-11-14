import java.util.HashMap;

public class ReporteMensual {

    private String idReporte;
    private int mes;
    private int anio;
    private double totalGastos;
    private int metasCumplidas;
    private int actividadesRealizadas; 
    private HashMap<String, Double> gastosPorCategoria;

    public ReporteMensual(String idReporte, int mes, int anio, double totalGastos, int metasCumplidas,
            int actividadesRealizadas, HashMap<String, Double> gastosPorCategoria) {
        this.idReporte = idReporte;
        this.mes = mes;
        this.anio = anio;
        this.totalGastos = totalGastos;
        this.metasCumplidas = metasCumplidas;
        this.actividadesRealizadas = actividadesRealizadas;
        this.gastosPorCategoria = gastosPorCategoria;
    }

    public String getIdReporte() {
        return idReporte;
    }

    public int getMes() {
        return mes;
    }

    public int getAnio() {
        return anio;
    }

    public double getTotalGastos() {
        return totalGastos;
    }

    public int getMetasCumplidas() {
        return metasCumplidas;
    }
    public int getActividadesRealizadas() {
        return actividadesRealizadas;
    }

    public void generarGrafico() {
        System.out.println("  Gastos por categoría - " + Fecha.textoMesAnio(mes, anio));
        if (gastosPorCategoria.isEmpty() || totalGastos <= 0) {
            System.out.println("  (No hay gastos registrados en este mes)");
            return;
        }
        for (String categoria : gastosPorCategoria.keySet()) {
            double monto = gastosPorCategoria.get(categoria);
            double porcentaje = monto * 100.0 / totalGastos;
            System.out.println("  " + categoria + " " + barra(porcentaje) + " S/ "
                    + (Math.round(monto * 100) / 100.0) + " (" + (Math.round(porcentaje * 10) / 10.0) + "%)");
        }
    }

 
    private String barra(double porcentaje) {
        int llenos = (int) Math.round(porcentaje / 5);
        String barra = "[";
        for (int i = 0; i < 20; i++) {
            if (i < llenos) {
                barra += "#";
            } else {
                barra += "-";
            }
        }
        return barra + "]";
    }

    public String toString() {
        return "REPORTE MENSUAL " + Fecha.textoMesAnio(mes, anio) + " (" + idReporte + ")\n"
                + "  Total de gastos: S/ " + (Math.round(totalGastos * 100) / 100.0) + "\n"
                + "  Metas cumplidas: " + metasCumplidas;
    }
}
