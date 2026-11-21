import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Metas académicas y tareas (menuMetas de la versión de consola):
 * crear meta, agregar tarea, completar tarea y ver el listado.
 *
 * INTEGRANTE 3
 */
public class VentanaMetas extends JFrame {

    private static final int ANCHO = 960;
    private static final int ALTO = 720;

    private Sesion sesion;

    private JTextField txtTituloMeta;
    private JTextField txtMateria;
    private PanelFecha pfMeta;
    private JButton btnCrearMeta;

    private JComboBox<String> cmbMetas;
    private JTextField txtTituloTarea;
    private JTextField txtDescTarea;
    private PanelFecha pfTarea;
    private JButton btnAgregarTarea;

    private JComboBox<String> cmbTareas;
    private JButton btnCompletar;

    private JCheckBox chkSoloPendientes;
    private JLabel lblAviso;
    private JTextArea txtSalida;
    private JButton btnVolver;

    public VentanaMetas(Sesion sesion) {
        this.sesion = sesion;
        setTitle("UnsaLife - Metas académicas y tareas");
        setSize(ANCHO, ALTO);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        crearContenido();
        actualizar();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void crearContenido() {
        Fecha hoy = sesion.getHoy();

        txtTituloMeta = new JTextField(20);
        txtMateria = new JTextField(14);
        pfMeta = new PanelFecha(hoy, true);
        btnCrearMeta = new JButton("Crear meta");

        cmbMetas = new JComboBox<>();
        cmbMetas.setPrototypeDisplayValue("META-1 | Título de ejemplo largo");
        txtTituloTarea = new JTextField(16);
        txtDescTarea = new JTextField(20);
        pfTarea = new PanelFecha(hoy, true);
        btnAgregarTarea = new JButton("Agregar tarea");

        cmbTareas = new JComboBox<>();
        cmbTareas.setPrototypeDisplayValue("TAR-1 | Título de ejemplo largo (META-1)");
        btnCompletar = new JButton("Marcar como completada");

        chkSoloPendientes = new JCheckBox("Mostrar solo tareas pendientes");
        btnVolver = new JButton("Volver");

        JPanel panelSuperior = new JPanel(new GridLayout(0, 1));

        JPanel fila1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        fila1.add(new JLabel("1. Nueva meta -  Título:"));
        fila1.add(txtTituloMeta);
        fila1.add(new JLabel("Materia:"));
        fila1.add(txtMateria);
        panelSuperior.add(fila1);

        JPanel fila2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        fila2.add(new JLabel("    Fecha límite de la meta:"));
        fila2.add(pfMeta);
        fila2.add(btnCrearMeta);
        panelSuperior.add(fila2);

        JPanel fila3 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        fila3.add(new JLabel("2. Nueva tarea -  Meta:"));
        fila3.add(cmbMetas);
        fila3.add(new JLabel("Título:"));
        fila3.add(txtTituloTarea);
        fila3.add(new JLabel("Descripción:"));
        fila3.add(txtDescTarea);
        panelSuperior.add(fila3);

        JPanel fila4 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        fila4.add(new JLabel("    Fecha límite de la tarea:"));
        fila4.add(pfTarea);
        fila4.add(btnAgregarTarea);
        panelSuperior.add(fila4);

        JPanel fila5 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        fila5.add(new JLabel("3. Completar tarea:"));
        fila5.add(cmbTareas);
        fila5.add(btnCompletar);
        panelSuperior.add(fila5);

        JPanel fila6 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        fila6.add(chkSoloPendientes);
        panelSuperior.add(fila6);

        add(panelSuperior, BorderLayout.NORTH);

        lblAviso = new JLabel("  ");
        txtSalida = Utilidades.crearAreaSalida();
        JPanel centro = new JPanel(new BorderLayout());
        centro.add(lblAviso, BorderLayout.NORTH);
        centro.add(txtSalida, BorderLayout.CENTER);
        add(centro, BorderLayout.CENTER);

        btnVolver.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        add(btnVolver, BorderLayout.SOUTH);

        EscuchaMetas escucha = new EscuchaMetas();
        btnCrearMeta.addActionListener(escucha);
        btnAgregarTarea.addActionListener(escucha);
        btnCompletar.addActionListener(escucha);
        chkSoloPendientes.addActionListener(escucha);
    }

    // ------------------------------------------------------------------
    // Operaciones (equivalen a crearMeta, agregarTarea, completarTarea de la v1)
    // ------------------------------------------------------------------

    private void crearMeta() {
        String titulo = txtTituloMeta.getText().trim();
        String materia = txtMateria.getText().trim();
        Fecha fecha = pfMeta.getFecha();
        if (titulo.isEmpty() || materia.isEmpty()) {
            Utilidades.mensaje(this, "Escribe el título y la materia de la meta.");
        } else if (fecha == null) {
            Utilidades.mensaje(this, "La fecha no es válida: ese mes no tiene ese día.");
        } else {
            MetaAcademica m = new MetaAcademica(sesion.generarId("META"), titulo, materia, fecha);
            sesion.getEstudiante().crearMeta(m);
            txtTituloMeta.setText("");
            txtMateria.setText("");
            actualizar();
            Utilidades.mensaje(this, "Meta " + m.getIdMeta() + " creada.");
        }
    }

    private void agregarTarea() {
        if (cmbMetas.getItemCount() == 0) {
            Utilidades.mensaje(this, "Primero crea una meta académica.");
            return;
        }
        MetaAcademica meta = sesion.getEstudiante().buscarMeta(Utilidades.idDe(cmbMetas.getSelectedItem()));
        String titulo = txtTituloTarea.getText().trim();
        String descripcion = txtDescTarea.getText().trim();
        Fecha fechaLimite = pfTarea.getFecha();
        if (meta == null) {
            Utilidades.mensaje(this, "No existe una meta con ese id.");
        } else if (titulo.isEmpty()) {
            Utilidades.mensaje(this, "Escribe el título de la tarea.");
        } else if (fechaLimite == null) {
            Utilidades.mensaje(this, "La fecha no es válida: ese mes no tiene ese día.");
        } else {
            Tarea t = new Tarea(sesion.generarId("TAR"), titulo, descripcion, fechaLimite);
            meta.agregarTarea(t);
            txtTituloTarea.setText("");
            txtDescTarea.setText("");
            actualizar();
            String aviso = "Tarea " + t.getId() + " agregada a " + meta.getIdMeta() + ".";
            if (fechaLimite.esPosteriorA(meta.getFechaLimite())) {
                aviso += "\nNota: la tarea vence después de la fecha límite de la meta.";
            }
            Utilidades.mensaje(this, aviso);
        }
    }

    private void completarTarea() {
        if (cmbTareas.getItemCount() == 0) {
            Utilidades.mensaje(this, "No tienes tareas pendientes por completar.");
            return;
        }
        String idTarea = Utilidades.idDe(cmbTareas.getSelectedItem());
        Tarea tarea = null;
        MetaAcademica meta = null;
        for (MetaAcademica m : sesion.getEstudiante().getMetas()) {
            if (m.buscarTarea(idTarea) != null) {
                meta = m;
                tarea = m.buscarTarea(idTarea);
            }
        }
        if (tarea == null) {
            Utilidades.mensaje(this, "No se encontró esa tarea.");
        } else if (tarea.isCompletada()) {
            Utilidades.mensaje(this, "Esa tarea ya estaba completada.");
        } else {
            tarea.marcarCompletada();
            meta.actualizarProgreso();
            actualizar();
            String aviso = "¡Tarea completada! Progreso de la meta: " + (Math.round(meta.getProgreso() * 10) / 10.0) + "%";
            if (meta.estaCumplida()) {
                aviso += "\n¡Felicitaciones, cumpliste la meta \"" + meta.getTitulo() + "\"!";
            }
            Utilidades.mensaje(this, aviso);
        }
    }

    // ------------------------------------------------------------------
    // Refresco de combos, listado y aviso de tareas vencidas
    // ------------------------------------------------------------------

    private void actualizar() {
        Estudiante est = sesion.getEstudiante();
        Fecha hoy = sesion.getHoy();
        boolean soloPendientes = chkSoloPendientes.isSelected();

        int metaElegida = cmbMetas.getSelectedIndex();
        cmbMetas.removeAllItems();
        cmbTareas.removeAllItems();
        for (MetaAcademica m : est.getMetas()) {
            cmbMetas.addItem(m.getIdMeta() + " | " + m.getTitulo());
        }
        if (metaElegida >= 0 && metaElegida < cmbMetas.getItemCount()) {
            cmbMetas.setSelectedIndex(metaElegida);
        }

        String sb = "[Mis metas académicas]\n";
        if (est.getMetas().isEmpty()) {
            sb = sb + ("  (ninguna)\n");
        }
        int vencidas = 0;
        for (MetaAcademica m : est.getMetas()) {
            sb = sb + ("  ") + (m) + ("\n");
            boolean hayTareasMostradas = false;
            for (Tarea t : m.getTareas()) {
                boolean vencida = !t.isCompletada() && t.estaVencida(hoy);
                if (vencida) {
                    vencidas++;
                }
                if (!t.isCompletada()) {
                    cmbTareas.addItem(t.getId() + " | " + t.getTitulo() + " (" + m.getIdMeta() + ")");
                }
                if (!soloPendientes || !t.isCompletada()) {
                    String marca = "";
                    if (vencida) {
                        marca = " [VENCIDA]";
                    }
                    sb = sb + ("      ") + (t) + (marca) + ("\n");
                    hayTareasMostradas = true;
                }
            }
            if (!hayTareasMostradas) {
                if (soloPendientes) {
                    sb = sb + ("      (sin tareas pendientes)\n");
                } else {
                    sb = sb + ("      (sin tareas)\n");
                }
            }
        }
        Utilidades.mostrar(txtSalida, sb, 1);

        if (vencidas > 0) {
            lblAviso.setText("  Tareas vencidas: " + vencidas);
            lblAviso.setForeground(Color.RED);
        } else {
            lblAviso.setText("  No tienes tareas vencidas.");
            lblAviso.setForeground(new Color(0, 128, 0));
        }
    }

    private class EscuchaMetas implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == btnCrearMeta) {
                crearMeta();
            } else if (e.getSource() == btnAgregarTarea) {
                agregarTarea();
            } else if (e.getSource() == btnCompletar) {
                completarTarea();
            } else if (e.getSource() == chkSoloPendientes) {
                actualizar();
            }
        }
    }
}
