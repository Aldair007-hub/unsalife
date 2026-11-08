public class Fecha {

    private int dia;
    private int mes;
    private int anio;

    public Fecha(int dia, int mes, int anio) {
        this.dia = dia;
        this.mes = mes;
        this.anio = anio;
    }

    public int getDia() {
        return dia;
    }

    public int getMes() {
        return mes;
    }

    public int getAnio() {
        return anio;
    }

    // ---------- Métodos de clase (static): validación de fechas ----------

    public static boolean esBisiesto(int anio) {
        return (anio % 4 == 0 && anio % 100 != 0) || anio % 400 == 0;
    }

    public static int diasDelMes(int mes, int anio) {
        int[] dias = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        if (mes == 2 && esBisiesto(anio)) {
            return 29;
        }
        return dias[mes - 1];
    }

    /** Una fecha es válida si el año está entre 2000 y 2100, el mes entre 1 y 12 y el día existe en ese mes. */
    public static boolean esValida(int dia, int mes, int anio) {
        if (anio < 2000 || anio > 2100) {
            return false;
        }
        if (mes < 1 || mes > 12) {
            return false;
        }
        return dia >= 1 && dia <= diasDelMes(mes, anio);
    }

    /** Texto "mm/aaaa", por ejemplo "11/2026". */
    public static String textoMesAnio(int mes, int anio) {
        return dosDigitos(mes) + "/" + anio;
    }

    private static String dosDigitos(int numero) {
        if (numero < 10) {
            return "0" + numero;
        }
        return "" + numero;
    }

    // ---------- Comparaciones ----------

    /** Retorna un número negativo si esta fecha es anterior, 0 si son iguales y positivo si es posterior. */
    public int comparar(Fecha otra) {
        if (anio != otra.anio) {
            return anio - otra.anio;
        }
        if (mes != otra.mes) {
            return mes - otra.mes;
        }
        return dia - otra.dia;
    }

    public boolean esAnteriorA(Fecha otra) {
        return comparar(otra) < 0;
    }

    public boolean esPosteriorA(Fecha otra) {
        return comparar(otra) > 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Fecha)) {
            return false;
        }
        Fecha otra = (Fecha) o;
        return dia == otra.dia && mes == otra.mes && anio == otra.anio;
    }

    @Override
    public int hashCode() {
        return dia + mes * 31 + anio * 372;
    }

    @Override
    public String toString() {
        return dosDigitos(dia) + "/" + dosDigitos(mes) + "/" + anio;
    }
}
