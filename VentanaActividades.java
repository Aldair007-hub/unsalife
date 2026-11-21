import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class VentanaActividades extends JFrame {

    private Sesion sesion;
    private JTextField txtTitulo;
    private JTextField txtDescripcion;
    private PanelFecha panelFecha;
    private JComboBox<String> cmbActividades;
    private JButton btnCrear;
    private JButton btnCompletar;
    private JTextArea txtSalida;
    private JButton btnVolver;

    public VentanaActividades(Sesion sesion) {
        this.sesion = sesion;
        setTitle("UnsaLife - Actividades");
        setSize(850, 550);
        setLayout(new BorderLayout(8, 8));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        crearContenido();
        actualizar();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void crearContenido() {
        txtTitulo = new JTextField(18);
        txtDescripcion = new JTextField(22);
        panelFecha = new PanelFecha(sesion.getHoy(), true);
        cmbActividades = new JComboBox<>();
        btnCrear = new JButton("Registrar actividad");
        btnCompletar = new JButton("Marcar como completada");
        btnVolver = new JButton("Volver");

        JPanel superior = new JPanel(new GridLayout(0, 1, 4, 4));
        JPanel fila1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        fila1.add(new JLabel("Título:"));
        fila1.add(txtTitulo);
        fila1.add(new JLabel("Descripción:"));
        fila1.add(txtDescripcion);
        superior.add(fila1);

        JPanel fila2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        fila2.add(new JLabel("Fecha:"));
        fila2.add(panelFecha);
        fila2.add(btnCrear);
        superior.add(fila2);

        JPanel fila3 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        fila3.add(new JLabel("Actividad pendiente:"));
        fila3.add(cmbActividades);
        fila3.add(btnCompletar);
        superior.add(fila3);
        add(superior, BorderLayout.NORTH);

        txtSalida = Utilidades.crearAreaSalida();
        add(txtSalida, BorderLayout.CENTER);
        add(btnVolver, BorderLayout.SOUTH);

        EscuchaActividades escucha = new EscuchaActividades();
        btnCrear.addActionListener(escucha);
        btnCompletar.addActionListener(escucha);
        btnVolver.addActionListener(escucha);
    }

    private void crearActividad() {
        String titulo = txtTitulo.getText().trim();
        String descripcion = txtDescripcion.getText().trim();
        Fecha fecha = panelFecha.getFecha();

        if (titulo.isEmpty()) {
            Utilidades.mensaje(this, "Escribe el título de la actividad.");
        } else if (fecha == null) {
            Utilidades.mensaje(this, "Selecciona una fecha válida.");
        } else {
            Actividad actividad = new Actividad(
                    sesion.generarId("ACT"), titulo, descripcion, fecha);
            sesion.getEstudiante().agregarActividad(actividad);
            txtTitulo.setText("");
            txtDescripcion.setText("");
            actualizar();
            Utilidades.mensaje(this, "Actividad registrada correctamente.");
        }
    }

    private void completarActividad() {
        if (cmbActividades.getSelectedItem() == null) {
            Utilidades.mensaje(this, "No hay actividades pendientes.");
            return;
        }
        String id = Utilidades.idDe(cmbActividades.getSelectedItem());
        Actividad actividad = sesion.getEstudiante().buscarActividad(id);
        if (actividad != null) {
            actividad.marcarCompletada();
            actualizar();
            Utilidades.mensaje(this, "Actividad completada.");
        }
    }

    private void actualizar() {
        Estudiante estudiante = sesion.getEstudiante();
        cmbActividades.removeAllItems();
        String texto = "MIS ACTIVIDADES\n\n";

        if (estudiante.getActividades().isEmpty()) {
            texto = texto + "Todavía no has registrado actividades.\n";
        }

        for (Actividad actividad : estudiante.getActividades()) {
            texto = texto + actividad + "\n";
            if (!actividad.isCompletada()) {
                cmbActividades.addItem(actividad.getId() + " | " + actividad.getTitulo());
            }
        }
        Utilidades.mostrar(txtSalida, texto);
    }

    private class EscuchaActividades implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == btnCrear) {
                crearActividad();
            } else if (e.getSource() == btnCompletar) {
                completarActividad();
            } else if (e.getSource() == btnVolver) {
                dispose();
            }
        }
    }
}
