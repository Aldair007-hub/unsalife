import javax.swing.*;
import java.awt.*;


public class Utilidades {

    public static final int MAX_LINEAS = 20;

    public static JTextArea crearAreaSalida() {
        JTextArea area = new JTextArea();
        area.setEditable(false);
        area.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        return area;
    }

    public static void mostrar(JTextArea area, String texto) {
        area.setText(recortar(texto, 0));
    }

    public static void mostrar(JTextArea area, String texto, int lineasFijas) {
        area.setText(recortar(texto, lineasFijas));
    }

    public static String recortar(String texto, int lineasFijas) {
        String[] lineas = texto.split("\n");
        if (lineas.length <= MAX_LINEAS) {
            return texto;
        }
        int cola = MAX_LINEAS - lineasFijas - 1;
        int omitidas = lineas.length - lineasFijas - cola;
        String sb = "";
        for (int i = 0; i < lineasFijas; i++) {
            sb = sb + (lineas[i]) + ("\n");
        }
        sb = sb + ("   ... (") + (omitidas) + (" líneas intermedias no se muestran) ...\n");
        for (int i = lineas.length - cola; i < lineas.length; i++) {
            sb = sb + (lineas[i]) + ("\n");
        }
        return sb;
    }


    public static double leerMonto(String texto) {
        String t = texto.trim().replace(',', '.');
        boolean valido = !t.isEmpty() && t.length() <= 10;
        int puntos = 0;
        int digitos = 0;
        for (int i = 0; i < t.length() && valido; i++) {
            char c = t.charAt(i);
            if (c == '.') {
                puntos++;
            } else if (c >= '0' && c <= '9') {
                digitos++;
            } else {
                valido = false;
            }
        }
        if (!valido || puntos > 1 || digitos == 0) {
            return -1;
        }
        double monto = Double.parseDouble(t);
        if (monto <= 0) {
            return -1;
        }
        return monto;
    }

    public static String dinero(double monto) {
        return "S/ " + (Math.round(monto * 100) / 100.0);
    }

    public static String normalizarCategoria(String categoria) {
        return categoria.substring(0, 1).toUpperCase() + categoria.substring(1).toLowerCase();
    }

    public static String idDe(Object item) {
        if (item == null) {
            return "";
        }
        String texto = item.toString();
        int corte = texto.indexOf(" | ");
        if (corte < 0) {
            return texto;
        }
        return texto.substring(0, corte);
    }

    public static void mensaje(JFrame ventana, String texto) {
        JOptionPane.showMessageDialog(ventana, texto);
    }
}
