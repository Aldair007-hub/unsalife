import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class VentanaGastos extends JFrame {

    private static final int ANCHO = 960;
    private static final int ALTO = 640;

    private Sesion sesion;

    private PanelFecha pfPresupuesto;
    private PanelFecha pfGasto;
    private PanelFecha pfDetalle;
    private JTextField txtLimite;
    private JTextField txtMonto;
    private JTextField txtDescripcion;
    private JComboBox<String> cmbCategoria;
    private JButton btnCrearPresupuesto;
    private JButton btnRegistrarGasto;
    private JButton btnVerDetalle;
    private JButton btnVerTodos;
    private JButton btnVolver;
    private JLabel lblEstado;
    private JTextArea txtSalida;

    public VentanaGastos(Sesion sesion) {
        this.sesion = sesion;
        setTitle("UnsaLife - Gastos y presupuestos");
        setSize(ANCHO, ALTO);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        crearContenido();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void crearContenido() {
        Fecha hoy = sesion.getHoy();

        pfPresupuesto = new PanelFecha(hoy, false);
        pfGasto = new PanelFecha(hoy, true);
        pfDetalle = new PanelFecha(hoy, false);
        txtLimite = new JTextField(8);
        txtMonto = new JTextField(7);
        txtDescripcion = new JTextField(30);
        cmbCategoria = new JComboBox<>(new String[]{"Alimentación", "Transporte", "Ocio", "Salud", "Materiales", "Otros"});
        cmbCategoria.setEditable(true); 
        btnCrearPresupuesto = new JButton("Crear presupuesto");
        btnRegistrarGasto = new JButton("Registrar gasto");
        btnVerDetalle = new JButton("Ver detalle del mes");
        btnVerTodos = new JButton("Ver todos mis presupuestos");
        btnVolver = new JButton("Volver");

        JPanel panelSuperior = new JPanel(new GridLayout(0, 1));

        JPanel fila1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        fila1.add(new JLabel("1. Crear presupuesto mensual:"));
        fila1.add(pfPresupuesto);
        fila1.add(new JLabel("Límite S/"));
        fila1.add(txtLimite);
        fila1.add(btnCrearPresupuesto);
        panelSuperior.add(fila1);

        JPanel fila2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        fila2.add(new JLabel("2. Registrar gasto:"));
        fila2.add(pfGasto);
        fila2.add(new JLabel("Monto S/"));
        fila2.add(txtMonto);
        fila2.add(new JLabel("Categoría"));
        fila2.add(cmbCategoria);
        panelSuperior.add(fila2);

        JPanel fila3 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        fila3.add(new JLabel("    Descripción (opcional):"));
        fila3.add(txtDescripcion);
        fila3.add(btnRegistrarGasto);
        panelSuperior.add(fila3);

        JPanel fila4 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        fila4.add(new JLabel("3. Consultar presupuesto:"));
        fila4.add(pfDetalle);
        fila4.add(btnVerDetalle);
        fila4.add(btnVerTodos);
        panelSuperior.add(fila4);

        add(panelSuperior, BorderLayout.NORTH);

        lblEstado = new JLabel("  Crea o consulta un presupuesto para ver su estado.");
        lblEstado.setForeground(Color.DARK_GRAY);
        txtSalida = Utilidades.crearAreaSalida();
        JPanel centro = new JPanel(new BorderLayout());
        centro.add(lblEstado, BorderLayout.NORTH);
        centro.add(txtSalida, BorderLayout.CENTER);
        add(centro, BorderLayout.CENTER);

        btnVolver.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        add(btnVolver, BorderLayout.SOUTH);

        EscuchaGastos escucha = new EscuchaGastos();
        btnCrearPresupuesto.addActionListener(escucha);
        btnRegistrarGasto.addActionListener(escucha);
        btnVerDetalle.addActionListener(escucha);
        btnVerTodos.addActionListener(escucha);
    }


    private void crearPresupuesto() {
        Fecha fecha = pfPresupuesto.getFecha();
        double limite = Utilidades.leerMonto(txtLimite.getText());
        Estudiante est = sesion.getEstudiante();

        if (limite < 0) {
            Utilidades.mensaje(this, "El monto límite debe ser un número mayor que cero.");
        } else if (est.buscarPresupuesto(fecha) != null) {
            Utilidades.mensaje(this, "Ya tienes un presupuesto para "
                    + Fecha.textoMesAnio(fecha.getMes(), fecha.getAnio()) + ".");
        } else {
            Presupuesto p = new Presupuesto(sesion.generarId("PRE"), limite, fecha.getMes(), fecha.getAnio());
            est.crearPresupuesto(p);
            txtLimite.setText("");
            Utilidades.mensaje(this, "Presupuesto " + p.getIdPresupuesto() + " creado.");
            mostrarDetalle(p);
        }
    }

    private void registrarGasto() {
        Fecha fecha = pfGasto.getFecha();
        if (fecha == null) {
            Utilidades.mensaje(this, "La fecha no es válida: ese mes no tiene ese día.");
            return;
        }
        Estudiante est = sesion.getEstudiante();
        Presupuesto p = est.buscarPresupuesto(fecha);
        if (p == null) {
            Utilidades.mensaje(this, "No tienes presupuesto para "
                    + Fecha.textoMesAnio(fecha.getMes(), fecha.getAnio())
                    + ". Créalo primero (sección 1).");
            return;
        }
        double monto = Utilidades.leerMonto(txtMonto.getText());
        if (monto < 0) {
            Utilidades.mensaje(this, "El monto debe ser un número mayor que cero.");
            return;
        }
        Object seleccion = cmbCategoria.getSelectedItem();
        String categoria = "";
        if (seleccion != null) {
            categoria = seleccion.toString().trim();
        }
        if (categoria.isEmpty()) {
            Utilidades.mensaje(this, "Elige o escribe una categoría.");
            return;
        }
        categoria = Utilidades.normalizarCategoria(categoria);
        String descripcion = txtDescripcion.getText().trim();

        Gasto g = new Gasto(sesion.generarId("GAS"), monto, categoria, fecha, descripcion);
        est.registrarGasto(g);

        String aviso = "Gasto " + g.getIdGasto() + " registrado. Saldo restante: "
                + Utilidades.dinero(p.calcularSaldoRestante());
        if (!p.verificarLimite()) {
            aviso += "\n¡ALERTA! Superaste el límite del mes por " + Utilidades.dinero(-p.calcularSaldoRestante()) + ".";
        } else if (p.calcularTotalGastado() >= 0.8 * p.getMontoLimite()) {
            aviso += "\nAviso: ya usaste más del 80% de tu presupuesto.";
        }
        txtMonto.setText("");
        txtDescripcion.setText("");
        Utilidades.mensaje(this, aviso);
        mostrarDetalle(p);
    }

    private void verDetalle() {
        Estudiante est = sesion.getEstudiante();
        if (est.getPresupuestos().isEmpty()) {
            Utilidades.mensaje(this, "Aún no tienes presupuestos.");
            return;
        }
        Fecha fecha = pfDetalle.getFecha();
        Presupuesto p = est.buscarPresupuesto(fecha);
        if (p == null) {
            Utilidades.mensaje(this, "No existe un presupuesto para "
                    + Fecha.textoMesAnio(fecha.getMes(), fecha.getAnio()) + ".");
        } else {
            mostrarDetalle(p);
        }
    }

    private void verTodos() {
        String sb = "[Mis presupuestos]\n";
        if (sesion.getEstudiante().getPresupuestos().isEmpty()) {
            sb = sb + ("  (ninguno)\n");
        }
        for (Presupuesto p : sesion.getEstudiante().getPresupuestos()) {
            sb = sb + ("  ") + (p) + ("\n");
        }
        Utilidades.mostrar(txtSalida, sb, 1);
    }

    private void mostrarDetalle(Presupuesto p) {
        double gastado = p.calcularTotalGastado();
        double porcentaje = gastado * 100.0 / p.getMontoLimite();
        String mes = Fecha.textoMesAnio(p.getMes(), p.getAnio());

        String sb = "";
        sb = sb + ("Presupuesto ") + (mes) + (" (") + (p.getIdPresupuesto()) + (")\n");
        sb = sb + ("  Límite : ") + (Utilidades.dinero(p.getMontoLimite())) + ("\n");
        sb = sb + ("  Gastado: ") + (Utilidades.dinero(gastado)) + (" (") + (Math.round(porcentaje * 10) / 10.0) + ("%)\n");
        sb = sb + ("  Saldo  : ") + (Utilidades.dinero(p.calcularSaldoRestante())) + ("\n");
        sb = sb + ("  Gastos registrados:\n");
        if (p.getGastos().isEmpty()) {
            sb = sb + ("    (ninguno)\n");
        }
        for (Gasto g : p.getGastos()) {
            sb = sb + ("    ") + (g) + ("\n");
        }
        Utilidades.mostrar(txtSalida, sb, 5);

        if (!p.verificarLimite()) {
            lblEstado.setText("  " + mes + ": ¡LÍMITE SUPERADO!");
            lblEstado.setForeground(Color.RED);
        } else if (gastado >= 0.8 * p.getMontoLimite()) {
            lblEstado.setText("  " + mes + ": cuidado, ya usaste más del 80% del presupuesto.");
            lblEstado.setForeground(new Color(200, 110, 0));
        } else {
            lblEstado.setText("  " + mes + ": dentro del límite.");
            lblEstado.setForeground(new Color(0, 128, 0));
        }
    }

    private class EscuchaGastos implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == btnCrearPresupuesto) {
                crearPresupuesto();
            } else if (e.getSource() == btnRegistrarGasto) {
                registrarGasto();
            } else if (e.getSource() == btnVerDetalle) {
                verDetalle();
            } else if (e.getSource() == btnVerTodos) {
                verTodos();
            }
        }
    }
}
