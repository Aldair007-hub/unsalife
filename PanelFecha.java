import javax.swing.*;
import java.awt.*;

public class PanelFecha extends JPanel {

    private JComboBox<String> cmbDia;
    private JComboBox<String> cmbMes;
    private JComboBox<String> cmbAnio;
    private boolean conDia;

    public PanelFecha(Fecha inicial, boolean conDia) {
        this.conDia = conDia;
        setLayout(new FlowLayout(FlowLayout.LEFT, 3, 0));

        cmbDia = new JComboBox<>(numeros(1, 31, 2));
        cmbMes = new JComboBox<>(numeros(1, 12, 2));
        cmbAnio = new JComboBox<>(numeros(inicial.getAnio() - 3, inicial.getAnio() + 5, 4));

        cmbDia.setSelectedItem(dosDigitos(inicial.getDia()));
        cmbMes.setSelectedItem(dosDigitos(inicial.getMes()));
        cmbAnio.setSelectedItem("" + inicial.getAnio());

        if (conDia) {
            add(new JLabel("Día"));
            add(cmbDia);
        }
        add(new JLabel("Mes"));
        add(cmbMes);
        add(new JLabel("Año"));
        add(cmbAnio);
    }

    public String[] numeros(int desde, int hasta, int ancho) {
        String[] lista = new String[hasta - desde + 1];
        for (int i = 0; i < lista.length; i++) {
            String num = (desde + i) + "";
            while (num.length() < ancho) {
                num = "0" + num;
            }
            lista[i] = num;
        }
        return lista;
    }

    public String dosDigitos(int numero) {
        if (numero < 10) {
            return "0" + numero;
        }
        return "" + numero;
    }

    public int valor(JComboBox<String> combo) {
        return Integer.parseInt((String) combo.getSelectedItem());
    }

    public Fecha getFecha() {
        int dia = 1;
        if (conDia) {
            dia = valor(cmbDia);
        }
        int mes = valor(cmbMes);
        int anio = valor(cmbAnio);
        if (!Fecha.esValida(dia, mes, anio)) {
            return null;
        }
        return new Fecha(dia, mes, anio);
    }

    public void setHabilitado(boolean habilitado) {
        cmbDia.setEnabled(habilitado);
        cmbMes.setEnabled(habilitado);
        cmbAnio.setEnabled(habilitado);
    }
}
