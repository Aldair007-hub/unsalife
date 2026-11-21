import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class VentanaReportes extends JFrame {

    private static final int ANCHO = 900;
    private static final int ALTO = 560;

    private Sesion sesion;

    private JRadioButton rbGenerar;
    private JRadioButton rbVer;
    private ButtonGroup grupoModo;
    private PanelFecha pfReporte;
    private JCheckBox chkGrafico;
    private JButton btnAceptar;
    private JButton btnVolver;
    private JTextArea txtSalida;

    public VentanaReportes(Sesion sesion) {
        this.sesion = sesion;
        setTitle("UnsaLife - Reportes mensuales");
        setSize(ANCHO, ALTO);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        crearContenido();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void crearContenido() {
        rbGenerar = new JRadioButton("Generar reporte del mes:", true);
        rbVer = new JRadioButton("Ver reportes ya generados");
        grupoModo = new ButtonGroup();
        grupoModo.add(rbGenerar);
        grupoModo.add(rbVer);

        pfReporte = new PanelFecha(sesion.getHoy(), false);
        chkGrafico = new JCheckBox("Incluir gráfico de gastos por categoría", true);
        btnAceptar = new JButton("Aceptar");
        btnVolver = new JButton("Volver");

        JPanel panelSuperior = new JPanel(new GridLayout(0, 1));
        JPanel fila1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        fila1.add(rbGenerar);
        fila1.add(pfReporte);
        fila1.add(chkGrafico);
        panelSuperior.add(fila1);
        JPanel fila2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        fila2.add(rbVer);
        fila2.add(btnAceptar);
        panelSuperior.add(fila2);
        add(panelSuperior, BorderLayout.NORTH);

        txtSalida = Utilidades.crearAreaSalida();
        Utilidades.mostrar(txtSalida, "Elige una opción y pulsa Aceptar.\n");
        add(txtSalida, BorderLayout.CENTER);

        btnVolver.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        add(btnVolver, BorderLayout.SOUTH);

        EscuchaReportes escucha = new EscuchaReportes();
        rbGenerar.addActionListener(escucha);
        rbVer.addActionListener(escucha);
        btnAceptar.addActionListener(escucha);
    }

    private void generarReporte() {
        Fecha fecha = pfReporte.getFecha();
        Estudiante est = sesion.getEstudiante();
        ReporteMensual reporte = est.generarReporte(fecha);

        String sb = "";
        sb = sb + (reporte) + ("\n");
        Presupuesto p = est.buscarPresupuesto(fecha);
        if (p == null) {
            sb = sb + ("  (No hay presupuesto para este mes)\n");
        } else {
            sb = sb + ("  Presupuesto: ") + (Utilidades.dinero(p.getMontoLimite()));
            if (p.verificarLimite()) {
                sb = sb + (" - dentro del límite\n");
            } else {
                sb = sb + (" - ¡límite superado!\n");
            }
        }
        if (chkGrafico.isSelected()) {
            sb = sb + ("\n") + (reporte.generarGrafico());
        }
        Utilidades.mostrar(txtSalida, sb);
    }

    private void verReportes() {
        String sb = "[Reportes generados]\n";
        if (sesion.getEstudiante().getReportes().isEmpty()) {
            sb = sb + ("  (ninguno)\n");
        }
        for (ReporteMensual r : sesion.getEstudiante().getReportes()) {
            sb = sb + ("  ") + (Fecha.textoMesAnio(r.getMes(), r.getAnio())) + (" | Gastos: ") + (Utilidades.dinero(r.getTotalGastos())) + (" | Metas cumplidas: ") + (r.getMetasCumplidas()) + (" | Actividades realizadas: ") + (r.getActividadesRealizadas()) + ("\n");
        }
        Utilidades.mostrar(txtSalida, sb, 1);
    }

    private class EscuchaReportes implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == rbGenerar) {
                pfReporte.setHabilitado(true);
                chkGrafico.setEnabled(true);
            } else if (e.getSource() == rbVer) {
                pfReporte.setHabilitado(false);
                chkGrafico.setEnabled(false);
            } else if (e.getSource() == btnAceptar) {
                if (rbGenerar.isSelected()) {
                    generarReporte();
                } else {
                    verReportes();
                }
            }
        }
    }
}
