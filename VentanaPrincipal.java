import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Menú principal (opciones 1 a 5 de la versión de consola) con el resumen general.
 *
 * INTEGRANTE 1
 */
public class VentanaPrincipal extends JFrame {

    private static final int ANCHO = 820;
    private static final int ALTO = 520;

    private Sesion sesion;
    private JLabel lblEstado;
    private JTextArea txtResumen;
    private JButton btnGastos;
    private JButton btnMetas;
    private JButton btnActividades;
    private JButton btnReportes;
    private JButton btnActualizar;
    private JButton btnSalir;

    public VentanaPrincipal(Sesion sesion) {
        this.sesion = sesion;
        setTitle("UnsaLife - Menú principal");
        setSize(ANCHO, ALTO);
        setLayout(new BorderLayout(8, 8));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        crearContenido();
        actualizarResumen();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void crearContenido() {
        // --- Parte superior: saludo, fecha y estado general ---
        JPanel panelNorte = new JPanel(new GridLayout(3, 1));
        JLabel lblSaludo = new JLabel("  Hola, " + sesion.getEstudiante().getNombre());
        lblSaludo.setForeground(new Color(20, 60, 120));
        JLabel lblFecha = new JLabel("  Fecha de hoy: " + sesion.getHoy());
        lblEstado = new JLabel("  ");
        panelNorte.add(lblSaludo);
        panelNorte.add(lblFecha);
        panelNorte.add(lblEstado);
        add(panelNorte, BorderLayout.NORTH);

        // --- Centro: resumen general ---
        txtResumen = Utilidades.crearAreaSalida();
        add(txtResumen, BorderLayout.CENTER);

        // --- Derecha: botones del menú ---
        btnGastos = new JButton("Gastos y presupuestos");
        btnMetas = new JButton("Metas académicas y tareas");
        btnActividades = new JButton("Actividades");
        btnReportes = new JButton("Reportes mensuales");
        btnActualizar = new JButton("Actualizar resumen");
        btnSalir = new JButton("Salir");

        JPanel panelBotones = new JPanel(new GridLayout(0, 1, 6, 6));
        panelBotones.add(btnGastos);
        panelBotones.add(btnMetas);
        panelBotones.add(btnActividades);
        panelBotones.add(btnReportes);
        panelBotones.add(btnActualizar);
        add(panelBotones, BorderLayout.EAST);
        add(btnSalir, BorderLayout.SOUTH);

        EscuchaMenu escucha = new EscuchaMenu();
        btnGastos.addActionListener(escucha);
        btnMetas.addActionListener(escucha);
        btnActividades.addActionListener(escucha);
        btnReportes.addActionListener(escucha);
        btnActualizar.addActionListener(escucha);
        btnSalir.addActionListener(escucha);
    }

    /** Recalcula el resumen general (se llama al abrir y con el botón "Actualizar resumen"). */
    public void actualizarResumen() {
        Estudiante est = sesion.getEstudiante();
        Fecha hoy = sesion.getHoy();
        String sb = "";
        boolean hayAlerta = false;
        String detalleAlerta = "";

        sb = sb + ("RESUMEN GENERAL\n\n");
        sb = sb + ("Estudiante: ") + (est) + ("\n\n");

        // Presupuesto del mes actual
        sb = sb + ("Presupuesto de ") + (Fecha.textoMesAnio(hoy.getMes(), hoy.getAnio())) + (":\n");
        Presupuesto p = est.buscarPresupuesto(hoy);
        if (p == null) {
            sb = sb + ("  (aún no creaste el presupuesto de este mes)\n");
        } else {
            sb = sb + ("  Límite : ") + (Utilidades.dinero(p.getMontoLimite())) + ("\n");
            sb = sb + ("  Gastado: ") + (Utilidades.dinero(p.calcularTotalGastado())) + ("\n");
            sb = sb + ("  Saldo  : ") + (Utilidades.dinero(p.calcularSaldoRestante())) + ("\n");
            if (!p.verificarLimite()) {
                hayAlerta = true;
                detalleAlerta = "Superaste el límite de tu presupuesto. ";
            }
        }

        // Metas y tareas
        int tareasPendientes = 0;
        int tareasVencidas = 0;
        for (MetaAcademica m : est.getMetas()) {
            for (Tarea t : m.getTareas()) {
                if (!t.isCompletada()) {
                    tareasPendientes++;
                    if (t.estaVencida(hoy)) {
                        tareasVencidas++;
                    }
                }
            }
        }
        sb = sb + ("\nMetas académicas: ") + (est.getMetas().size()) + ("\n");
        sb = sb + ("  Tareas pendientes: ") + (tareasPendientes);
        sb = sb + (" (vencidas: ") + (tareasVencidas) + (")\n");
        if (tareasVencidas > 0) {
            hayAlerta = true;
            detalleAlerta += "Tienes " + tareasVencidas + " tarea(s) vencida(s). ";
        }

        // Actividades
        int actividadesPendientes = 0;
        for (Actividad a : est.getActividades()) {
            if (!a.isCompletada()) {
                actividadesPendientes++;
            }
        }
        sb = sb + ("\nActividades: ") + (est.getActividades().size());
        sb = sb + (" (pendientes: ") + (actividadesPendientes) + (")\n");

        sb = sb + ("\nReportes generados: ") + (est.getReportes().size()) + ("\n");

        Utilidades.mostrar(txtResumen, sb);
        if (hayAlerta) {
            lblEstado.setText("  Atención: " + detalleAlerta);
            lblEstado.setForeground(Color.RED);
        } else {
            lblEstado.setText("  Todo en orden por ahora.");
            lblEstado.setForeground(new Color(0, 128, 0));
        }
    }

    private class EscuchaMenu implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == btnGastos) {
                new VentanaGastos(sesion);
            } else if (e.getSource() == btnMetas) {
                new VentanaMetas(sesion);
            } else if (e.getSource() == btnActividades) {
                new VentanaActividades(sesion);
            } else if (e.getSource() == btnReportes) {
                new VentanaReportes(sesion);
            } else if (e.getSource() == btnActualizar) {
                actualizarResumen();
            } else if (e.getSource() == btnSalir) {
                System.exit(0);
            }
        }
    }
}
