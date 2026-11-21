import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class VentanaInicio extends JFrame {

    private static final int ANCHO = 440;
    private static final int ALTO = 220;

    private JTextField txtNombre;
    private JTextField txtCorreo;
    private JButton btnIngresar;

    public VentanaInicio() {
        setTitle("UnsaLife - Inicio");
        setSize(ANCHO, ALTO);
        setLayout(new BorderLayout(10, 10));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        crearContenido();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void crearContenido() {
        getContentPane().setBackground(new Color(235, 242, 250));

        JLabel lblTitulo = new JLabel("UnsaLife - Gestor integral de vida universitaria", JLabel.CENTER);
        lblTitulo.setForeground(new Color(20, 60, 120));
        add(lblTitulo, BorderLayout.NORTH);

        txtNombre = new JTextField(20);
        txtCorreo = new JTextField(20);
        JPanel panelDatos = new JPanel(new GridLayout(2, 2, 8, 12));
        panelDatos.add(new JLabel("  Tu nombre:"));
        panelDatos.add(txtNombre);
        panelDatos.add(new JLabel("  Tu correo:"));
        panelDatos.add(txtCorreo);
        add(panelDatos, BorderLayout.CENTER);

        btnIngresar = new JButton("Ingresar");
        add(btnIngresar, BorderLayout.SOUTH);

        EscuchaIngresar escucha = new EscuchaIngresar();
        btnIngresar.addActionListener(escucha);
        txtCorreo.addActionListener(escucha);
    }

    private class EscuchaIngresar implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String nombre = txtNombre.getText().trim();
            String correo = txtCorreo.getText().trim();
            if (nombre.isEmpty()) {
                JOptionPane.showMessageDialog(VentanaInicio.this, "Escribe tu nombre.");
            } else if (correo.indexOf('@') < 1 || correo.length() < 5) {
                JOptionPane.showMessageDialog(VentanaInicio.this, "Escribe un correo válido (ej. nombre@unsa.edu.pe).");
            } else {
                Sesion sesion = new Sesion(nombre, correo);
                new VentanaPrincipal(sesion);
                dispose();
            }
        }
    }
}
