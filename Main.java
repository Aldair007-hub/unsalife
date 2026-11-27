import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;

public class Main extends JFrame {
    public static final Color COLOR_FONDO = new Color(235, 242, 250);
    public static final Color COLOR_PANEL = new Color(245, 248, 253);
    public static final Color COLOR_PRIMARIO = new Color(20, 60, 120);
    public static final Color COLOR_SECUNDARIO = new Color(70, 95, 130);
    public static final Color COLOR_SALIR = new Color(180, 60, 60);
    public static final Color COLOR_FILA_IMPAR = new Color(240, 245, 252);
    private Sesion sesion;
    private JFrame ventanaPrincipal;
    private JFrame ventanaGastos;
    private JFrame ventanaMetas;
    private JFrame ventanaActividades;
    private JFrame ventanaReportes;
    private JTextField txtNombre;
    private JTextField txtCorreo;
    private JButton btnIngresar;
    private JLabel lblSaludo;
    private JLabel lblFecha;
    private JLabel lblEstadoPrincipal;
    private JPanel panelEstadoPrincipal;
    private JTextField[][] celdasResumen;
    private JTextField[][] celdasAlertas;
    private static final int MAX_ALERTAS = 6;
    private JButton btnGastos;
    private JButton btnMetas;
    private JButton btnActividades;
    private JButton btnReportes;
    private JButton btnActualizar;
    private JButton btnSalir;
    private PanelFecha pfPresupuesto;
    private PanelFecha pfGasto;
    private PanelFecha pfDetalle;
    private JTextField txtLimite;
    private JTextField txtMonto;
    private JTextField txtDescripcionGasto;
    private JComboBox<String> cmbCategoria;
    private JButton btnCrearPresupuesto;
    private JButton btnRegistrarGasto;
    private JButton btnVerDetalle;
    private JButton btnVerTodos;
    private JButton btnVolverGastos;
    private JLabel lblEstadoGastos;
    private JPanel panelEstadoGastos;
    private JTextField[] encabezadosGastos;
    private JTextField[][] celdasGastos;
    private static final int MAX_FILAS_GASTOS = 12;
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
    private JButton btnCompletarTarea;
    private JCheckBox chkSoloPendientesTareas;
    private JLabel lblAvisoMetas;
    private JPanel panelAvisoMetas;
    private JTextField[][] celdasMetas;
    private JTextField[][] celdasTareas;
    private static final int MAX_METAS = 5;
    private static final int MAX_TAREAS = 7;
    private JButton btnVolverMetas;
    private JTextField txtTituloAct;
    private JTextField txtDescripcionAct;
    private PanelFecha panelFechaAct;
    private JComboBox<String> cmbActividades;
    private JCheckBox chkSoloPendientesAct;
    private JButton btnCrearAct;
    private JButton btnCompletarAct;
    private JLabel lblResumenAct;
    private JTextField[][] celdasActividades;
    private static final int MAX_ACTIVIDADES = 12;
    private JButton btnVolverAct;
    private JRadioButton rbGenerar;
    private JRadioButton rbVer;
    private ButtonGroup grupoModo;
    private PanelFecha pfReporte;
    private JCheckBox chkGrafico;
    private JButton btnAceptarRep;
    private JButton btnVolverRep;
    private JLabel lblResumenRep;
    private JTextField[] encabezadosReporte;
    private JTextField[][] celdasReporte;
    private static final int MAX_FILAS_REP = 10;

    public Main() {
        this.sesion = new Sesion();
        setTitle("UnsaLife - Inicio");
        setSize(460, 230);
        setLayout(new BorderLayout(10, 10));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        crearContenidoInicio();
        setLocationRelativeTo(null);
        inicializarVentanaGastos();
        inicializarVentanaMetas();
        inicializarVentanaActividades();
        inicializarVentanaReportes();
        inicializarVentanaPrincipal();
        setVisible(true);
    }

    private void estilizarBotonPrimario(JButton boton) {
        boton.setBackground(COLOR_PRIMARIO);
        boton.setForeground(Color.WHITE);
    }

    private void estilizarBotonSecundario(JButton boton) {
        boton.setBackground(COLOR_SECUNDARIO);
        boton.setForeground(Color.WHITE);
    }

    private void estilizarBotonSalir(JButton boton) {
        boton.setBackground(COLOR_SALIR);
        boton.setForeground(Color.WHITE);
    }

    private JTextField crearEncabezadoTabla(String texto) {
        JTextField celda = new JTextField(texto);
        celda.setEditable(false);
        celda.setHorizontalAlignment(JTextField.CENTER);
        celda.setBackground(COLOR_PRIMARIO);
        celda.setForeground(Color.WHITE);
        return celda;
    }

    private JTextField crearCeldaTabla(String texto, Color fondo, Color colorTexto, int alineacion) {
        JTextField celda = new JTextField(texto);
        celda.setEditable(false);
        celda.setHorizontalAlignment(alineacion);
        celda.setBackground(fondo);
        celda.setForeground(colorTexto);
        return celda;
    }

    private double leerMonto(String texto) {
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

    private String dinero(double monto) {
        return "S/ " + (Math.round(monto * 100) / 100.0);
    }

    private String normalizarCategoria(String categoria) {
        return categoria.substring(0, 1).toUpperCase() + categoria.substring(1).toLowerCase();
    }

    private String idDe(Object item) {
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

    private void mensaje(Component padre, String texto) {
        JOptionPane.showMessageDialog(padre, texto);
    }

    private void crearContenidoInicio() {
        getContentPane().setBackground(COLOR_FONDO);

        JPanel panelTitulo = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 10));
        panelTitulo.setBackground(COLOR_PRIMARIO);
        JLabel lblTitulo = new JLabel("UnsaLife - Gestor integral de vida universitaria", SwingConstants.CENTER);
        lblTitulo.setForeground(Color.WHITE);
        panelTitulo.add(lblTitulo);
        add(panelTitulo, BorderLayout.NORTH);

        txtNombre = new JTextField(20);
        txtCorreo = new JTextField(20);
        JPanel panelDatos = new JPanel(new GridLayout(2, 2, 8, 12));
        panelDatos.setBackground(COLOR_FONDO);
        panelDatos.add(new JLabel("   Tu nombre:"));
        panelDatos.add(txtNombre);
        panelDatos.add(new JLabel("   Tu correo:"));
        panelDatos.add(txtCorreo);
        add(panelDatos, BorderLayout.CENTER);

        btnIngresar = new JButton("Ingresar");
        estilizarBotonPrimario(btnIngresar);
        JPanel panelSur = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 8));
        panelSur.setBackground(COLOR_FONDO);
        panelSur.add(btnIngresar);
        add(panelSur, BorderLayout.SOUTH);

        EscuchaIngresar escucha = new EscuchaIngresar();
        btnIngresar.addActionListener(escucha);
    }

    private void inicializarVentanaPrincipal() {
        ventanaPrincipal = new JFrame("UnsaLife - Menú principal");
        ventanaPrincipal.setSize(940, 600);
        ventanaPrincipal.setLayout(new BorderLayout(10, 10));
        ventanaPrincipal.setDefaultCloseOperation(EXIT_ON_CLOSE);
        ventanaPrincipal.getContentPane().setBackground(COLOR_FONDO);

        JPanel panelNorte = new JPanel(new GridLayout(3, 1, 0, 4));
        panelNorte.setBackground(COLOR_PANEL);

        JPanel filaSaludo = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 4));
        filaSaludo.setBackground(COLOR_PANEL);
        lblSaludo = new JLabel("Hola");
        lblSaludo.setForeground(COLOR_PRIMARIO);
        filaSaludo.add(lblSaludo);

        JPanel filaFecha = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 2));
        filaFecha.setBackground(COLOR_PANEL);
        lblFecha = new JLabel("Fecha de hoy: " + sesion.getHoy());
        lblFecha.setForeground(Color.DARK_GRAY);
        filaFecha.add(lblFecha);

        panelEstadoPrincipal = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 4));
        panelEstadoPrincipal.setBackground(new Color(225, 245, 225));
        lblEstadoPrincipal = new JLabel("Todo en orden por ahora.");
        panelEstadoPrincipal.add(lblEstadoPrincipal);

        panelNorte.add(filaSaludo);
        panelNorte.add(filaFecha);
        panelNorte.add(panelEstadoPrincipal);
        ventanaPrincipal.add(panelNorte, BorderLayout.NORTH);

        JPanel panelCentro = new JPanel(new GridLayout(2, 1, 0, 8));
        panelCentro.setBackground(COLOR_FONDO);

        JPanel panelTablaResumen = new JPanel(new GridLayout(5, 3, 2, 2));
        panelTablaResumen.setBackground(COLOR_FONDO);
        panelTablaResumen.add(crearEncabezadoTabla("Módulo"));
        panelTablaResumen.add(crearEncabezadoTabla("Estado Actual"));
        panelTablaResumen.add(crearEncabezadoTabla("Detalle / Indicador"));

        celdasResumen = new JTextField[4][3];
        String[] nombresModulos = {"Presupuesto del mes", "Metas y Tareas", "Actividades", "Reportes Mensuales"};
        for (int f = 0; f < 4; f++) {
            Color fondo = (f % 2 == 0) ? Color.WHITE : COLOR_FILA_IMPAR;
            celdasResumen[f][0] = crearCeldaTabla(nombresModulos[f], fondo, COLOR_PRIMARIO, JTextField.LEFT);
            celdasResumen[f][1] = crearCeldaTabla("", fondo, Color.BLACK, JTextField.CENTER);
            celdasResumen[f][2] = crearCeldaTabla("", fondo, Color.BLACK, JTextField.LEFT);
            panelTablaResumen.add(celdasResumen[f][0]);
            panelTablaResumen.add(celdasResumen[f][1]);
            panelTablaResumen.add(celdasResumen[f][2]);
        }

        JPanel panelTablaAlertas = new JPanel(new GridLayout(MAX_ALERTAS + 1, 4, 2, 2));
        panelTablaAlertas.setBackground(COLOR_FONDO);
        panelTablaAlertas.add(crearEncabezadoTabla("Tipo"));
        panelTablaAlertas.add(crearEncabezadoTabla("Código"));
        panelTablaAlertas.add(crearEncabezadoTabla("Título / Referencia"));
        panelTablaAlertas.add(crearEncabezadoTabla("Condición / Fecha"));

        celdasAlertas = new JTextField[MAX_ALERTAS][4];
        int[] alineaciones = {JTextField.CENTER, JTextField.CENTER, JTextField.LEFT, JTextField.CENTER};
        for (int f = 0; f < MAX_ALERTAS; f++) {
            Color fondo = (f % 2 == 0) ? Color.WHITE : COLOR_FILA_IMPAR;
            for (int c = 0; c < 4; c++) {
                celdasAlertas[f][c] = crearCeldaTabla("", fondo, Color.BLACK, alineaciones[c]);
                panelTablaAlertas.add(celdasAlertas[f][c]);
            }
        }

        panelCentro.add(panelTablaResumen);
        panelCentro.add(panelTablaAlertas);
        ventanaPrincipal.add(panelCentro, BorderLayout.CENTER);

        btnGastos = new JButton("Gastos y presupuestos");
        btnMetas = new JButton("Metas académicas y tareas");
        btnActividades = new JButton("Actividades");
        btnReportes = new JButton("Reportes mensuales");
        btnActualizar = new JButton("Actualizar resumen");
        btnSalir = new JButton("Salir");

        estilizarBotonPrimario(btnGastos);
        estilizarBotonPrimario(btnMetas);
        estilizarBotonPrimario(btnActividades);
        estilizarBotonPrimario(btnReportes);
        estilizarBotonSecundario(btnActualizar);
        estilizarBotonSalir(btnSalir);

        JPanel panelBotones = new JPanel(new GridLayout(0, 1, 6, 8));
        panelBotones.setBackground(COLOR_FONDO);
        panelBotones.add(btnGastos);
        panelBotones.add(btnMetas);
        panelBotones.add(btnActividades);
        panelBotones.add(btnReportes);
        panelBotones.add(btnActualizar);
        panelBotones.add(btnSalir);
        ventanaPrincipal.add(panelBotones, BorderLayout.EAST);

        EscuchaMenu escucha = new EscuchaMenu();
        btnGastos.addActionListener(escucha);
        btnMetas.addActionListener(escucha);
        btnActividades.addActionListener(escucha);
        btnReportes.addActionListener(escucha);
        btnActualizar.addActionListener(escucha);
        btnSalir.addActionListener(escucha);

        ventanaPrincipal.setLocationRelativeTo(null);
    }

    private void cargarDatosEstudiante() {
        Estudiante est = sesion.getEstudiante();
        lblSaludo.setText("Hola, " + est.getNombre() + " (" + est.getCorreo() + ")");
        lblFecha.setText("Fecha de hoy: " + sesion.getHoy() + "   |   Código: " + est.getIdEstudiante());
    }

    private void actualizarResumenPrincipal() {
        Estudiante est = sesion.getEstudiante();
        Fecha hoy = sesion.getHoy();
        boolean hayAlerta = false;
        String detalleAlerta = "";

        String mesTexto = Fecha.textoMesAnio(hoy.getMes(), hoy.getAnio());
        Presupuesto p = est.buscarPresupuesto(hoy);
        String estadoPres = "Sin presupuesto (" + mesTexto + ")";
        String detallePres = "Crea un presupuesto en Gastos";
        Color colorPres = Color.DARK_GRAY;
        if (p != null) {
            estadoPres = "Saldo: " + dinero(p.calcularSaldoRestante());
            detallePres = "Gastado " + dinero(p.calcularTotalGastado()) + " de " + dinero(p.getMontoLimite());
            if (!p.verificarLimite()) {
                colorPres = Color.RED;
                hayAlerta = true;
                detalleAlerta = "Superaste el límite de tu presupuesto. ";
            } else if (p.calcularTotalGastado() >= 0.8 * p.getMontoLimite()) {
                colorPres = Color.ORANGE;
            } else {
                colorPres = Color.BLUE;
            }
        }
        celdasResumen[0][1].setText(estadoPres);
        celdasResumen[0][1].setForeground(colorPres);
        celdasResumen[0][2].setText(detallePres);

        int metasCumplidas = 0;
        int tareasPendientes = 0;
        int tareasVencidas = 0;
        for (MetaAcademica m : est.getMetas()) {
            if (m.estaCumplida()) {
                metasCumplidas++;
            }
            for (Tarea t : m.getTareas()) {
                if (!t.isCompletada()) {
                    tareasPendientes++;
                    if (t.estaVencida(hoy)) {
                        tareasVencidas++;
                    }
                }
            }
        }
        Color colorTareas = tareasVencidas > 0 ? Color.RED : Color.BLUE;
        if (tareasVencidas > 0) {
            hayAlerta = true;
            detalleAlerta += "Tienes " + tareasVencidas + " tarea(s) vencida(s). ";
        }
        celdasResumen[1][1].setText(est.getMetas().size() + " metas (" + metasCumplidas + " cumplidas)");
        celdasResumen[1][2].setText(tareasPendientes + " pendientes | " + tareasVencidas + " vencidas");
        celdasResumen[1][2].setForeground(colorTareas);

        int actividadesPendientes = 0;
        int actividadesHoy = 0;
        for (Actividad a : est.getActividades()) {
            if (!a.isCompletada()) {
                actividadesPendientes++;
                if (a.getFecha().equals(hoy)) {
                    actividadesHoy++;
                }
            }
        }
        celdasResumen[2][1].setText(est.getActividades().size() + " registradas");
        celdasResumen[2][2].setText(actividadesPendientes + " pendientes (" + actividadesHoy + " para hoy)");
        celdasResumen[2][2].setForeground(COLOR_PRIMARIO);

        String ultimoRep = "Ninguno aún";
        if (!est.getReportes().isEmpty()) {
            ReporteMensual ult = est.getReportes().get(est.getReportes().size() - 1);
            ultimoRep = "Último: " + Fecha.textoMesAnio(ult.getMes(), ult.getAnio());
        }
        celdasResumen[3][1].setText(est.getReportes().size() + " generado(s)");
        celdasResumen[3][2].setText(ultimoRep);
        celdasResumen[3][2].setForeground(Color.DARK_GRAY);

        int fila = 0;
        for (MetaAcademica m : est.getMetas()) {
            for (Tarea t : m.getTareas()) {
                if (!t.isCompletada() && t.estaVencida(hoy) && fila < MAX_ALERTAS) {
                    celdasAlertas[fila][0].setText("Tarea");
                    celdasAlertas[fila][0].setForeground(COLOR_PRIMARIO);
                    celdasAlertas[fila][1].setText(t.getId());
                    celdasAlertas[fila][2].setText(t.getTitulo() + " (" + m.getIdMeta() + ")");
                    celdasAlertas[fila][3].setText("VENCIDA (" + t.getFechaLimite() + ")");
                    celdasAlertas[fila][3].setForeground(Color.RED);
                    for (int c = 0; c < 4; c++) {
                        celdasAlertas[fila][c].setVisible(true);
                    }
                    fila++;
                }
            }
        }
        for (Actividad a : est.getActividades()) {
            if (!a.isCompletada() && a.getFecha().equals(hoy) && fila < MAX_ALERTAS) {
                celdasAlertas[fila][0].setText("Actividad");
                celdasAlertas[fila][0].setForeground(COLOR_PRIMARIO);
                celdasAlertas[fila][1].setText(a.getId());
                celdasAlertas[fila][2].setText(a.getTitulo());
                celdasAlertas[fila][3].setText("Programada para HOY");
                celdasAlertas[fila][3].setForeground(Color.BLUE);
                for (int c = 0; c < 4; c++) {
                    celdasAlertas[fila][c].setVisible(true);
                }
                fila++;
            }
        }

        if (fila == 0) {
            celdasAlertas[0][0].setText("-");
            celdasAlertas[0][0].setForeground(Color.DARK_GRAY);
            celdasAlertas[0][1].setText("-");
            celdasAlertas[0][2].setText("Sin tareas vencidas ni actividades para hoy");
            celdasAlertas[0][3].setText("Todo al día");
            celdasAlertas[0][3].setForeground(Color.BLUE);
            for (int c = 0; c < 4; c++) {
                celdasAlertas[0][c].setVisible(true);
            }
            fila = 1;
        }

        for (int f = fila; f < MAX_ALERTAS; f++) {
            for (int c = 0; c < 4; c++) {
                celdasAlertas[f][c].setText("");
                celdasAlertas[f][c].setVisible(false);
            }
        }

        if (hayAlerta) {
            lblEstadoPrincipal.setText("Atención: " + detalleAlerta);
            lblEstadoPrincipal.setForeground(Color.RED);
            panelEstadoPrincipal.setBackground(new Color(255, 228, 228));
        } else {
            lblEstadoPrincipal.setText("Todo en orden por ahora.");
            lblEstadoPrincipal.setForeground(Color.BLUE);
            panelEstadoPrincipal.setBackground(new Color(225, 245, 225));
        }
    }

    private void inicializarVentanaGastos() {
        ventanaGastos = new JFrame("UnsaLife - Gastos y presupuestos");
        ventanaGastos.setSize(960, 620);
        ventanaGastos.setLayout(new BorderLayout(8, 8));
        ventanaGastos.setDefaultCloseOperation(HIDE_ON_CLOSE);
        ventanaGastos.getContentPane().setBackground(COLOR_FONDO);

        Fecha hoy = sesion.getHoy();

        pfPresupuesto = new PanelFecha(hoy, false);
        pfGasto = new PanelFecha(hoy, true);
        pfDetalle = new PanelFecha(hoy, false);
        txtLimite = new JTextField(8);
        txtMonto = new JTextField(7);
        txtDescripcionGasto = new JTextField(24);
        cmbCategoria = new JComboBox<>(new String[]{"Alimentación", "Transporte", "Ocio", "Salud", "Materiales", "Otros"});
        cmbCategoria.setEditable(true);

        btnCrearPresupuesto = new JButton("Crear presupuesto");
        btnRegistrarGasto = new JButton("Registrar gasto");
        btnVerDetalle = new JButton("Ver detalle del mes");
        btnVerTodos = new JButton("Ver todos mis presupuestos");
        btnVolverGastos = new JButton("Volver");

        estilizarBotonPrimario(btnCrearPresupuesto);
        estilizarBotonPrimario(btnRegistrarGasto);
        estilizarBotonSecundario(btnVerDetalle);
        estilizarBotonSecundario(btnVerTodos);
        estilizarBotonSalir(btnVolverGastos);

        JPanel panelSuperior = new JPanel(new GridLayout(0, 1, 0, 5));
        panelSuperior.setBackground(COLOR_FONDO);

        JPanel bloque1 = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 4));
        bloque1.setBackground(COLOR_PANEL);
        JLabel lblB1 = new JLabel("1. Crear presupuesto mensual:");
        lblB1.setForeground(COLOR_PRIMARIO);
        bloque1.add(lblB1);
        bloque1.add(pfPresupuesto);
        bloque1.add(new JLabel("Límite S/"));
        bloque1.add(txtLimite);
        bloque1.add(btnCrearPresupuesto);
        panelSuperior.add(bloque1);

        JPanel bloque2 = new JPanel(new GridLayout(2, 1, 0, 2));
        bloque2.setBackground(new Color(228, 237, 248));
        pfGasto.setBackground(new Color(228, 237, 248));

        JPanel fila2A = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 3));
        fila2A.setBackground(new Color(228, 237, 248));
        JLabel lblB2 = new JLabel("2. Registrar gasto:");
        lblB2.setForeground(COLOR_PRIMARIO);
        fila2A.add(lblB2);
        fila2A.add(pfGasto);
        fila2A.add(new JLabel("Monto S/"));
        fila2A.add(txtMonto);
        fila2A.add(new JLabel("Categoría:"));
        fila2A.add(cmbCategoria);

        JPanel fila2B = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 3));
        fila2B.setBackground(new Color(228, 237, 248));
        fila2B.add(new JLabel("    Descripción (opcional):"));
        fila2B.add(txtDescripcionGasto);
        fila2B.add(btnRegistrarGasto);

        bloque2.add(fila2A);
        bloque2.add(fila2B);
        panelSuperior.add(bloque2);

        JPanel bloque3 = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 4));
        bloque3.setBackground(COLOR_PANEL);
        JLabel lblB3 = new JLabel("3. Consultar presupuesto:");
        lblB3.setForeground(COLOR_PRIMARIO);
        bloque3.add(lblB3);
        bloque3.add(pfDetalle);
        bloque3.add(btnVerDetalle);
        bloque3.add(btnVerTodos);
        panelSuperior.add(bloque3);

        ventanaGastos.add(panelSuperior, BorderLayout.NORTH);

        JPanel centro = new JPanel(new BorderLayout(0, 6));
        centro.setBackground(COLOR_FONDO);

        panelEstadoGastos = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 4));
        panelEstadoGastos.setBackground(COLOR_PANEL);
        lblEstadoGastos = new JLabel("Crea o consulta un presupuesto para ver su estado.");
        lblEstadoGastos.setForeground(Color.DARK_GRAY);
        panelEstadoGastos.add(lblEstadoGastos);
        centro.add(panelEstadoGastos, BorderLayout.NORTH);

        JPanel panelTablaGastos = new JPanel(new GridLayout(MAX_FILAS_GASTOS + 1, 5, 2, 2));
        panelTablaGastos.setBackground(COLOR_FONDO);

        encabezadosGastos = new JTextField[5];
        String[] titulos = {"Código", "Fecha", "Categoría", "Monto", "Descripción"};
        for (int c = 0; c < 5; c++) {
            encabezadosGastos[c] = crearEncabezadoTabla(titulos[c]);
            panelTablaGastos.add(encabezadosGastos[c]);
        }

        celdasGastos = new JTextField[MAX_FILAS_GASTOS][5];
        int[] alineaciones = {JTextField.CENTER, JTextField.CENTER, JTextField.CENTER, JTextField.CENTER, JTextField.LEFT};
        for (int f = 0; f < MAX_FILAS_GASTOS; f++) {
            Color fondo = (f % 2 == 0) ? Color.WHITE : COLOR_FILA_IMPAR;
            for (int c = 0; c < 5; c++) {
                celdasGastos[f][c] = crearCeldaTabla("", fondo, Color.BLACK, alineaciones[c]);
                panelTablaGastos.add(celdasGastos[f][c]);
            }
        }
        centro.add(panelTablaGastos, BorderLayout.CENTER);
        ventanaGastos.add(centro, BorderLayout.CENTER);

        JPanel panelSur = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 6));
        panelSur.setBackground(COLOR_FONDO);
        panelSur.add(btnVolverGastos);
        ventanaGastos.add(panelSur, BorderLayout.SOUTH);

        EscuchaGastos escucha = new EscuchaGastos();
        btnCrearPresupuesto.addActionListener(escucha);
        btnRegistrarGasto.addActionListener(escucha);
        btnVerDetalle.addActionListener(escucha);
        btnVerTodos.addActionListener(escucha);
        btnVolverGastos.addActionListener(escucha);

        ventanaGastos.setLocationRelativeTo(null);
    }

    private void cargarEstadoInicialGastos() {
        Presupuesto actual = sesion.getEstudiante().buscarPresupuesto(sesion.getHoy());
        if (actual != null) {
            mostrarDetallePresupuesto(actual);
        } else {
            lblEstadoGastos.setText("Aún no has creado un presupuesto para el mes actual. Usa la sección 1.");
            lblEstadoGastos.setForeground(Color.DARK_GRAY);
            panelEstadoGastos.setBackground(COLOR_PANEL);
            limpiarFilasGastosDesde(0);
        }
    }

    private void limpiarFilasGastosDesde(int filaInicio) {
        for (int f = filaInicio; f < MAX_FILAS_GASTOS; f++) {
            for (int c = 0; c < 5; c++) {
                celdasGastos[f][c].setText("");
                celdasGastos[f][c].setVisible(false);
            }
        }
    }

    private void crearPresupuesto() {
        Fecha fecha = pfPresupuesto.getFecha();
        double limite = leerMonto(txtLimite.getText());
        Estudiante est = sesion.getEstudiante();

        if (limite < 0) {
            mensaje(ventanaGastos, "El monto límite debe ser un número mayor que cero.");
        } else if (est.buscarPresupuesto(fecha) != null) {
            mensaje(ventanaGastos, "Ya tienes un presupuesto para "
                    + Fecha.textoMesAnio(fecha.getMes(), fecha.getAnio()) + ".");
        } else {
            Presupuesto p = new Presupuesto(sesion.generarId("PRE"), limite, fecha.getMes(), fecha.getAnio());
            est.crearPresupuesto(p);
            txtLimite.setText("");
            mensaje(ventanaGastos, "Presupuesto " + p.getIdPresupuesto() + " creado.");
            mostrarDetallePresupuesto(p);
        }
    }

    private void registrarGasto() {
        Fecha fecha = pfGasto.getFecha();
        if (fecha == null) {
            mensaje(ventanaGastos, "La fecha no es válida: ese mes no tiene ese día.");
            return;
        }
        Estudiante est = sesion.getEstudiante();
        Presupuesto p = est.buscarPresupuesto(fecha);
        if (p == null) {
            mensaje(ventanaGastos, "No tienes presupuesto para "
                    + Fecha.textoMesAnio(fecha.getMes(), fecha.getAnio())
                    + ". Créalo primero (sección 1).");
            return;
        }
        double monto = leerMonto(txtMonto.getText());
        if (monto < 0) {
            mensaje(ventanaGastos, "El monto debe ser un número mayor que cero.");
            return;
        }
        Object seleccion = cmbCategoria.getSelectedItem();
        String categoria = seleccion != null ? seleccion.toString().trim() : "";
        if (categoria.isEmpty()) {
            mensaje(ventanaGastos, "Elige o escribe una categoría.");
            return;
        }
        categoria = normalizarCategoria(categoria);
        String descripcion = txtDescripcionGasto.getText().trim();

        Gasto g = new Gasto(sesion.generarId("GAS"), monto, categoria, fecha, descripcion);
        est.registrarGasto(g);

        String aviso = "Gasto " + g.getIdGasto() + " registrado. Saldo restante: "
                + dinero(p.calcularSaldoRestante());
        if (!p.verificarLimite()) {
            aviso += "\n¡ALERTA! Superaste el límite del mes por " + dinero(-p.calcularSaldoRestante()) + ".";
        } else if (p.calcularTotalGastado() >= 0.8 * p.getMontoLimite()) {
            aviso += "\nAviso: ya usaste más del 80% de tu presupuesto.";
        }
        txtMonto.setText("");
        txtDescripcionGasto.setText("");
        mensaje(ventanaGastos, aviso);
        mostrarDetallePresupuesto(p);
    }

    private void verDetallePresupuesto() {
        Estudiante est = sesion.getEstudiante();
        if (est.getPresupuestos().isEmpty()) {
            mensaje(ventanaGastos, "Aún no tienes presupuestos.");
            return;
        }
        Fecha fecha = pfDetalle.getFecha();
        Presupuesto p = est.buscarPresupuesto(fecha);
        if (p == null) {
            mensaje(ventanaGastos, "No existe un presupuesto para "
                    + Fecha.textoMesAnio(fecha.getMes(), fecha.getAnio()) + ".");
        } else {
            mostrarDetallePresupuesto(p);
        }
    }

    private void verTodosPresupuestos() {
        String[] titulos = {"Código", "Mes / Año", "Límite", "Gastado", "Saldo"};
        for (int c = 0; c < 5; c++) {
            encabezadosGastos[c].setText(titulos[c]);
        }
        int fila = 0;
        for (Presupuesto p : sesion.getEstudiante().getPresupuestos()) {
            if (fila < MAX_FILAS_GASTOS) {
                Color colorSaldo = p.verificarLimite() ? Color.BLUE : Color.RED;
                celdasGastos[fila][0].setText(p.getIdPresupuesto());
                celdasGastos[fila][0].setForeground(COLOR_PRIMARIO);
                celdasGastos[fila][1].setText(Fecha.textoMesAnio(p.getMes(), p.getAnio()));
                celdasGastos[fila][2].setText(dinero(p.getMontoLimite()));
                celdasGastos[fila][3].setText(dinero(p.calcularTotalGastado()));
                celdasGastos[fila][4].setText(dinero(p.calcularSaldoRestante()));
                celdasGastos[fila][4].setForeground(colorSaldo);
                for (int c = 0; c < 5; c++) {
                    celdasGastos[fila][c].setVisible(true);
                }
                fila++;
            }
        }
        limpiarFilasGastosDesde(fila);
        lblEstadoGastos.setText("Mostrando resumen general de todos los presupuestos creados.");
        lblEstadoGastos.setForeground(COLOR_PRIMARIO);
        panelEstadoGastos.setBackground(COLOR_PANEL);
    }

    private void mostrarDetallePresupuesto(Presupuesto p) {
        String[] titulos = {"Código", "Fecha", "Categoría", "Monto", "Descripción"};
        for (int c = 0; c < 5; c++) {
            encabezadosGastos[c].setText(titulos[c]);
        }

        double gastado = p.calcularTotalGastado();
        double saldo = p.calcularSaldoRestante();
        double porcentaje = Math.round((gastado * 100.0 / p.getMontoLimite()) * 10) / 10.0;
        String mes = Fecha.textoMesAnio(p.getMes(), p.getAnio());
        String resumen = "Presupuesto " + mes + " (" + p.getIdPresupuesto() + ") | Límite: "
                + dinero(p.getMontoLimite()) + " | Gastado: " + dinero(gastado)
                + " (" + porcentaje + "%) | Saldo: " + dinero(saldo);

        if (!p.verificarLimite()) {
            lblEstadoGastos.setText(resumen + " -> ¡LÍMITE SUPERADO!");
            lblEstadoGastos.setForeground(Color.RED);
            panelEstadoGastos.setBackground(new Color(255, 228, 228));
        } else if (gastado >= 0.8 * p.getMontoLimite()) {
            lblEstadoGastos.setText(resumen + " -> Cuidado (+80% usado)");
            lblEstadoGastos.setForeground(Color.ORANGE);
            panelEstadoGastos.setBackground(new Color(255, 242, 215));
        } else {
            lblEstadoGastos.setText(resumen + " -> Dentro del límite");
            lblEstadoGastos.setForeground(Color.BLUE);
            panelEstadoGastos.setBackground(new Color(225, 245, 225));
        }

        int total = p.getGastos().size();
        int inicio = total > MAX_FILAS_GASTOS ? total - MAX_FILAS_GASTOS : 0;
        int fila = 0;
        for (int i = inicio; i < total; i++) {
            Gasto g = p.getGastos().get(i);
            String desc = g.getDescripcion().isEmpty() ? "(sin descripción)" : g.getDescripcion();
            celdasGastos[fila][0].setText(g.getIdGasto());
            celdasGastos[fila][0].setForeground(COLOR_PRIMARIO);
            celdasGastos[fila][1].setText(g.getFecha().toString());
            celdasGastos[fila][2].setText(g.getCategoria());
            celdasGastos[fila][3].setText(dinero(g.getMonto()));
            celdasGastos[fila][4].setText(desc);
            celdasGastos[fila][4].setForeground(Color.DARK_GRAY);
            for (int c = 0; c < 5; c++) {
                celdasGastos[fila][c].setVisible(true);
            }
            fila++;
        }
        limpiarFilasGastosDesde(fila);
    }

    private void inicializarVentanaMetas() {
        ventanaMetas = new JFrame("UnsaLife - Metas académicas y tareas");
        ventanaMetas.setSize(1020, 680);
        ventanaMetas.setLayout(new BorderLayout(8, 8));
        ventanaMetas.setDefaultCloseOperation(HIDE_ON_CLOSE);
        ventanaMetas.getContentPane().setBackground(COLOR_FONDO);

        Fecha hoy = sesion.getHoy();

        txtTituloMeta = new JTextField(16);
        txtMateria = new JTextField(12);
        pfMeta = new PanelFecha(hoy, true);
        btnCrearMeta = new JButton("Crear meta");

        cmbMetas = new JComboBox<>();
        txtTituloTarea = new JTextField(14);
        txtDescTarea = new JTextField(16);
        pfTarea = new PanelFecha(hoy, true);
        btnAgregarTarea = new JButton("Agregar tarea");

        cmbTareas = new JComboBox<>();
        btnCompletarTarea = new JButton("Marcar como completada");
        chkSoloPendientesTareas = new JCheckBox("Mostrar solo tareas pendientes");
        chkSoloPendientesTareas.setBackground(COLOR_PANEL);
        btnVolverMetas = new JButton("Volver");

        estilizarBotonPrimario(btnCrearMeta);
        estilizarBotonPrimario(btnAgregarTarea);
        estilizarBotonSecundario(btnCompletarTarea);
        estilizarBotonSalir(btnVolverMetas);

        JPanel panelSuperior = new JPanel(new BorderLayout(0, 6));
        panelSuperior.setBackground(COLOR_FONDO);

        JPanel dosColumnas = new JPanel(new GridLayout(1, 2, 10, 0));
        dosColumnas.setBackground(COLOR_FONDO);

        JPanel colMeta = new JPanel(new GridLayout(3, 1, 0, 2));
        colMeta.setBackground(COLOR_PANEL);
        JPanel mFila1 = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 3));
        mFila1.setBackground(COLOR_PANEL);
        JLabel lblM = new JLabel("1. Nueva meta académica");
        lblM.setForeground(COLOR_PRIMARIO);
        mFila1.add(lblM);

        JPanel mFila2 = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 3));
        mFila2.setBackground(COLOR_PANEL);
        mFila2.add(new JLabel("Título:"));
        mFila2.add(txtTituloMeta);
        mFila2.add(new JLabel("Materia:"));
        mFila2.add(txtMateria);

        JPanel mFila3 = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 3));
        mFila3.setBackground(COLOR_PANEL);
        mFila3.add(new JLabel("Límite:"));
        mFila3.add(pfMeta);
        mFila3.add(btnCrearMeta);

        colMeta.add(mFila1);
        colMeta.add(mFila2);
        colMeta.add(mFila3);

        Color fondoTarea = new Color(228, 237, 248);
        pfTarea.setBackground(fondoTarea);
        JPanel colTarea = new JPanel(new GridLayout(3, 1, 0, 2));
        colTarea.setBackground(fondoTarea);

        JPanel tFila1 = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 3));
        tFila1.setBackground(fondoTarea);
        JLabel lblT = new JLabel("2. Nueva tarea asociada");
        lblT.setForeground(COLOR_PRIMARIO);
        tFila1.add(lblT);
        tFila1.add(new JLabel("Meta:"));
        tFila1.add(cmbMetas);

        JPanel tFila2 = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 3));
        tFila2.setBackground(fondoTarea);
        tFila2.add(new JLabel("Título:"));
        tFila2.add(txtTituloTarea);
        tFila2.add(new JLabel("Desc:"));
        tFila2.add(txtDescTarea);

        JPanel tFila3 = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 3));
        tFila3.setBackground(fondoTarea);
        tFila3.add(new JLabel("Límite:"));
        tFila3.add(pfTarea);
        tFila3.add(btnAgregarTarea);

        colTarea.add(tFila1);
        colTarea.add(tFila2);
        colTarea.add(tFila3);

        dosColumnas.add(colMeta);
        dosColumnas.add(colTarea);
        panelSuperior.add(dosColumnas, BorderLayout.CENTER);

        JPanel filaAccion = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        filaAccion.setBackground(COLOR_PANEL);
        JLabel lblC = new JLabel("3. Completar tarea:");
        lblC.setForeground(COLOR_PRIMARIO);
        filaAccion.add(lblC);
        filaAccion.add(cmbTareas);
        filaAccion.add(btnCompletarTarea);
        filaAccion.add(chkSoloPendientesTareas);
        panelSuperior.add(filaAccion, BorderLayout.SOUTH);

        ventanaMetas.add(panelSuperior, BorderLayout.NORTH);

        JPanel centro = new JPanel(new BorderLayout(0, 6));
        centro.setBackground(COLOR_FONDO);

        panelAvisoMetas = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 4));
        panelAvisoMetas.setBackground(new Color(225, 245, 225));
        lblAvisoMetas = new JLabel("No tienes tareas vencidas.");
        panelAvisoMetas.add(lblAvisoMetas);
        centro.add(panelAvisoMetas, BorderLayout.NORTH);

        JPanel zonaTablas = new JPanel(new GridLayout(2, 1, 0, 8));
        zonaTablas.setBackground(COLOR_FONDO);

        JPanel panelTablaMetas = new JPanel(new GridLayout(MAX_METAS + 1, 5, 2, 2));
        panelTablaMetas.setBackground(COLOR_FONDO);
        panelTablaMetas.add(crearEncabezadoTabla("Código"));
        panelTablaMetas.add(crearEncabezadoTabla("Meta Académica"));
        panelTablaMetas.add(crearEncabezadoTabla("Materia"));
        panelTablaMetas.add(crearEncabezadoTabla("Fecha Límite"));
        panelTablaMetas.add(crearEncabezadoTabla("Progreso"));

        celdasMetas = new JTextField[MAX_METAS][5];
        int[] alinM = {JTextField.CENTER, JTextField.LEFT, JTextField.CENTER, JTextField.CENTER, JTextField.CENTER};
        for (int f = 0; f < MAX_METAS; f++) {
            Color fondo = (f % 2 == 0) ? Color.WHITE : COLOR_FILA_IMPAR;
            for (int c = 0; c < 5; c++) {
                celdasMetas[f][c] = crearCeldaTabla("", fondo, Color.BLACK, alinM[c]);
                panelTablaMetas.add(celdasMetas[f][c]);
            }
        }

        JPanel panelTablaTareas = new JPanel(new GridLayout(MAX_TAREAS + 1, 6, 2, 2));
        panelTablaTareas.setBackground(COLOR_FONDO);
        panelTablaTareas.add(crearEncabezadoTabla("Código"));
        panelTablaTareas.add(crearEncabezadoTabla("Meta"));
        panelTablaTareas.add(crearEncabezadoTabla("Tarea"));
        panelTablaTareas.add(crearEncabezadoTabla("Detalle"));
        panelTablaTareas.add(crearEncabezadoTabla("Límite"));
        panelTablaTareas.add(crearEncabezadoTabla("Estado"));

        celdasTareas = new JTextField[MAX_TAREAS][6];
        int[] alinT = {JTextField.CENTER, JTextField.CENTER, JTextField.LEFT, JTextField.LEFT, JTextField.CENTER, JTextField.CENTER};
        for (int f = 0; f < MAX_TAREAS; f++) {
            Color fondo = (f % 2 == 0) ? Color.WHITE : COLOR_FILA_IMPAR;
            for (int c = 0; c < 6; c++) {
                celdasTareas[f][c] = crearCeldaTabla("", fondo, Color.BLACK, alinT[c]);
                panelTablaTareas.add(celdasTareas[f][c]);
            }
        }

        zonaTablas.add(panelTablaMetas);
        zonaTablas.add(panelTablaTareas);
        centro.add(zonaTablas, BorderLayout.CENTER);
        ventanaMetas.add(centro, BorderLayout.CENTER);

        JPanel panelSur = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 6));
        panelSur.setBackground(COLOR_FONDO);
        panelSur.add(btnVolverMetas);
        ventanaMetas.add(panelSur, BorderLayout.SOUTH);

        EscuchaMetas escucha = new EscuchaMetas();
        btnCrearMeta.addActionListener(escucha);
        btnAgregarTarea.addActionListener(escucha);
        btnCompletarTarea.addActionListener(escucha);
        chkSoloPendientesTareas.addActionListener(escucha);
        btnVolverMetas.addActionListener(escucha);

        ventanaMetas.setLocationRelativeTo(null);
    }

    private void crearMeta() {
        String titulo = txtTituloMeta.getText().trim();
        String materia = txtMateria.getText().trim();
        Fecha fecha = pfMeta.getFecha();
        if (titulo.isEmpty() || materia.isEmpty()) {
            mensaje(ventanaMetas, "Escribe el título y la materia de la meta.");
        } else if (fecha == null) {
            mensaje(ventanaMetas, "La fecha no es válida: ese mes no tiene ese día.");
        } else {
            MetaAcademica m = new MetaAcademica(sesion.generarId("META"), titulo, materia, fecha);
            sesion.getEstudiante().crearMeta(m);
            txtTituloMeta.setText("");
            txtMateria.setText("");
            actualizarMetas();
            mensaje(ventanaMetas, "Meta " + m.getIdMeta() + " creada.");
        }
    }

    private void agregarTarea() {
        if (cmbMetas.getItemCount() == 0) {
            mensaje(ventanaMetas, "Primero crea una meta académica.");
            return;
        }
        MetaAcademica meta = sesion.getEstudiante().buscarMeta(idDe(cmbMetas.getSelectedItem()));
        String titulo = txtTituloTarea.getText().trim();
        String descripcion = txtDescTarea.getText().trim();
        Fecha fechaLimite = pfTarea.getFecha();
        if (meta == null) {
            mensaje(ventanaMetas, "No existe una meta con ese id.");
        } else if (titulo.isEmpty()) {
            mensaje(ventanaMetas, "Escribe el título de la tarea.");
        } else if (fechaLimite == null) {
            mensaje(ventanaMetas, "La fecha no es válida: ese mes no tiene ese día.");
        } else {
            Tarea t = new Tarea(sesion.generarId("TAR"), titulo, descripcion, fechaLimite);
            meta.agregarTarea(t);
            txtTituloTarea.setText("");
            txtDescTarea.setText("");
            actualizarMetas();
            String aviso = "Tarea " + t.getId() + " agregada a " + meta.getIdMeta() + ".";
            if (fechaLimite.esPosteriorA(meta.getFechaLimite())) {
                aviso += "\nNota: la tarea vence después de la fecha límite de la meta.";
            }
            mensaje(ventanaMetas, aviso);
        }
    }

    private void completarTarea() {
        if (cmbTareas.getItemCount() == 0) {
            mensaje(ventanaMetas, "No tienes tareas pendientes por completar.");
            return;
        }
        String idTarea = idDe(cmbTareas.getSelectedItem());
        Tarea tarea = null;
        MetaAcademica meta = null;
        for (MetaAcademica m : sesion.getEstudiante().getMetas()) {
            if (m.buscarTarea(idTarea) != null) {
                meta = m;
                tarea = m.buscarTarea(idTarea);
            }
        }
        if (tarea == null) {
            mensaje(ventanaMetas, "No se encontró esa tarea.");
        } else if (tarea.isCompletada()) {
            mensaje(ventanaMetas, "Esa tarea ya estaba completada.");
        } else {
            tarea.marcarCompletada();
            meta.actualizarProgreso();
            actualizarMetas();
            String aviso = "¡Tarea completada! Progreso de la meta: " + (Math.round(meta.getProgreso() * 10) / 10.0) + "%";
            if (meta.estaCumplida()) {
                aviso += "\n¡Felicitaciones, cumpliste la meta \"" + meta.getTitulo() + "\"!";
            }
            mensaje(ventanaMetas, aviso);
        }
    }

    private void actualizarMetas() {
        Estudiante est = sesion.getEstudiante();
        Fecha hoy = sesion.getHoy();
        boolean soloPendientes = chkSoloPendientesTareas.isSelected();

        int metaElegida = cmbMetas.getSelectedIndex();
        cmbMetas.removeAllItems();
        cmbTareas.removeAllItems();
        for (MetaAcademica m : est.getMetas()) {
            cmbMetas.addItem(m.getIdMeta() + " | " + m.getTitulo());
        }
        if (metaElegida >= 0 && metaElegida < cmbMetas.getItemCount()) {
            cmbMetas.setSelectedIndex(metaElegida);
        }

        int totalMetas = est.getMetas().size();
        int inicioM = totalMetas > MAX_METAS ? totalMetas - MAX_METAS : 0;
        int filaM = 0;
        for (int i = inicioM; i < totalMetas; i++) {
            MetaAcademica m = est.getMetas().get(i);
            double prog = Math.round(m.getProgreso() * 10) / 10.0;
            Color colorProg = m.estaCumplida() ? Color.BLUE : COLOR_PRIMARIO;

            celdasMetas[filaM][0].setText(m.getIdMeta());
            celdasMetas[filaM][0].setForeground(COLOR_PRIMARIO);
            celdasMetas[filaM][1].setText(m.getTitulo());
            celdasMetas[filaM][2].setText(m.getMateria());
            celdasMetas[filaM][3].setText(m.getFechaLimite().toString());
            celdasMetas[filaM][4].setText(prog + " %");
            celdasMetas[filaM][4].setForeground(colorProg);
            for (int c = 0; c < 5; c++) {
                celdasMetas[filaM][c].setVisible(true);
            }
            filaM++;
        }
        for (int f = filaM; f < MAX_METAS; f++) {
            for (int c = 0; c < 5; c++) {
                celdasMetas[f][c].setText("");
                celdasMetas[f][c].setVisible(false);
            }
        }

        int vencidas = 0;
        int pendientes = 0;
        int cumplidas = 0;
        int filaT = 0;
        for (MetaAcademica m : est.getMetas()) {
            if (m.estaCumplida()) {
                cumplidas++;
            }
            for (Tarea t : m.getTareas()) {
                boolean vencida = !t.isCompletada() && t.estaVencida(hoy);
                if (!t.isCompletada()) {
                    pendientes++;
                    cmbTareas.addItem(t.getId() + " | " + t.getTitulo() + " (" + m.getIdMeta() + ")");
                }
                if (vencida) {
                    vencidas++;
                }
                if ((!soloPendientes || !t.isCompletada()) && filaT < MAX_TAREAS) {
                    String desc = t.getDescripcion().isEmpty() ? "(sin detalle)" : t.getDescripcion();
                    String estadoTxt;
                    Color colorEstado;
                    if (t.isCompletada()) {
                        estadoTxt = "Completada";
                        colorEstado = Color.BLUE;
                    } else if (vencida) {
                        estadoTxt = "VENCIDA";
                        colorEstado = Color.RED;
                    } else {
                        estadoTxt = "Pendiente";
                        colorEstado = Color.ORANGE;
                    }

                    celdasTareas[filaT][0].setText(t.getId());
                    celdasTareas[filaT][0].setForeground(COLOR_PRIMARIO);
                    celdasTareas[filaT][1].setText(m.getIdMeta());
                    celdasTareas[filaT][2].setText(t.getTitulo());
                    celdasTareas[filaT][3].setText(desc);
                    celdasTareas[filaT][4].setText(t.getFechaLimite().toString());
                    celdasTareas[filaT][5].setText(estadoTxt);
                    celdasTareas[filaT][5].setForeground(colorEstado);
                    for (int c = 0; c < 6; c++) {
                        celdasTareas[filaT][c].setVisible(true);
                    }
                    filaT++;
                }
            }
        }
        for (int f = filaT; f < MAX_TAREAS; f++) {
            for (int c = 0; c < 6; c++) {
                celdasTareas[f][c].setText("");
                celdasTareas[f][c].setVisible(false);
            }
        }

        String resumen = "Metas: " + est.getMetas().size() + " (" + cumplidas + " cumplidas) | Tareas pendientes: " + pendientes;
        if (vencidas > 0) {
            lblAvisoMetas.setText(resumen + " | ¡Tareas vencidas: " + vencidas + "!");
            lblAvisoMetas.setForeground(Color.RED);
            panelAvisoMetas.setBackground(new Color(255, 228, 228));
        } else {
            lblAvisoMetas.setText(resumen + " | Sin tareas vencidas.");
            lblAvisoMetas.setForeground(Color.BLUE);
            panelAvisoMetas.setBackground(new Color(225, 245, 225));
        }
    }

    private void inicializarVentanaActividades() {
        ventanaActividades = new JFrame("UnsaLife - Actividades");
        ventanaActividades.setSize(920, 560);
        ventanaActividades.setLayout(new BorderLayout(8, 8));
        ventanaActividades.setDefaultCloseOperation(HIDE_ON_CLOSE);
        ventanaActividades.getContentPane().setBackground(COLOR_FONDO);

        txtTituloAct = new JTextField(18);
        txtDescripcionAct = new JTextField(22);
        panelFechaAct = new PanelFecha(sesion.getHoy(), true);
        cmbActividades = new JComboBox<>();
        chkSoloPendientesAct = new JCheckBox("Mostrar solo actividades pendientes");
        chkSoloPendientesAct.setBackground(COLOR_PANEL);

        btnCrearAct = new JButton("Registrar actividad");
        btnCompletarAct = new JButton("Marcar como completada");
        btnVolverAct = new JButton("Volver");

        estilizarBotonPrimario(btnCrearAct);
        estilizarBotonSecundario(btnCompletarAct);
        estilizarBotonSalir(btnVolverAct);

        JPanel superior = new JPanel(new GridLayout(2, 1, 0, 4));
        superior.setBackground(COLOR_PANEL);

        JPanel fila1 = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 4));
        fila1.setBackground(COLOR_PANEL);
        JLabel lblSec1 = new JLabel("1. Nueva actividad:");
        lblSec1.setForeground(COLOR_PRIMARIO);
        fila1.add(lblSec1);
        fila1.add(new JLabel("Título:"));
        fila1.add(txtTituloAct);
        fila1.add(new JLabel("Descripción:"));
        fila1.add(txtDescripcionAct);
        superior.add(fila1);

        JPanel fila2 = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 4));
        fila2.setBackground(COLOR_PANEL);
        fila2.add(new JLabel("    Fecha:"));
        fila2.add(panelFechaAct);
        fila2.add(btnCrearAct);
        superior.add(fila2);
        ventanaActividades.add(superior, BorderLayout.NORTH);

        JPanel centro = new JPanel(new BorderLayout(0, 6));
        centro.setBackground(COLOR_FONDO);

        JPanel franjaResumen = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 4));
        franjaResumen.setBackground(COLOR_PANEL);
        lblResumenAct = new JLabel("Resumen de actividades");
        lblResumenAct.setForeground(COLOR_PRIMARIO);
        franjaResumen.add(lblResumenAct);
        centro.add(franjaResumen, BorderLayout.NORTH);

        JPanel panelTablaActividades = new JPanel(new GridLayout(MAX_ACTIVIDADES + 1, 5, 2, 2));
        panelTablaActividades.setBackground(COLOR_FONDO);
        panelTablaActividades.add(crearEncabezadoTabla("Código"));
        panelTablaActividades.add(crearEncabezadoTabla("Actividad"));
        panelTablaActividades.add(crearEncabezadoTabla("Descripción"));
        panelTablaActividades.add(crearEncabezadoTabla("Fecha"));
        panelTablaActividades.add(crearEncabezadoTabla("Estado"));

        celdasActividades = new JTextField[MAX_ACTIVIDADES][5];
        int[] alineaciones = {JTextField.CENTER, JTextField.LEFT, JTextField.LEFT, JTextField.CENTER, JTextField.CENTER};
        for (int f = 0; f < MAX_ACTIVIDADES; f++) {
            Color fondo = (f % 2 == 0) ? Color.WHITE : COLOR_FILA_IMPAR;
            for (int c = 0; c < 5; c++) {
                celdasActividades[f][c] = crearCeldaTabla("", fondo, Color.BLACK, alineaciones[c]);
                panelTablaActividades.add(celdasActividades[f][c]);
            }
        }
        centro.add(panelTablaActividades, BorderLayout.CENTER);
        ventanaActividades.add(centro, BorderLayout.CENTER);

        JPanel inferior = new JPanel(new BorderLayout(8, 0));
        inferior.setBackground(COLOR_PANEL);

        JPanel filaGestion = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 6));
        filaGestion.setBackground(COLOR_PANEL);
        JLabel lblSec2 = new JLabel("2. Gestionar:");
        lblSec2.setForeground(COLOR_PRIMARIO);
        filaGestion.add(lblSec2);
        filaGestion.add(cmbActividades);
        filaGestion.add(btnCompletarAct);
        filaGestion.add(chkSoloPendientesAct);

        JPanel filaVolver = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 6));
        filaVolver.setBackground(COLOR_PANEL);
        filaVolver.add(btnVolverAct);

        inferior.add(filaGestion, BorderLayout.CENTER);
        inferior.add(filaVolver, BorderLayout.EAST);
        ventanaActividades.add(inferior, BorderLayout.SOUTH);

        EscuchaActividades escucha = new EscuchaActividades();
        btnCrearAct.addActionListener(escucha);
        btnCompletarAct.addActionListener(escucha);
        chkSoloPendientesAct.addActionListener(escucha);
        btnVolverAct.addActionListener(escucha);

        ventanaActividades.setLocationRelativeTo(null);
    }

    private void crearActividad() {
        String titulo = txtTituloAct.getText().trim();
        String descripcion = txtDescripcionAct.getText().trim();
        Fecha fecha = panelFechaAct.getFecha();

        if (titulo.isEmpty()) {
            mensaje(ventanaActividades, "Escribe el título de la actividad.");
        } else if (fecha == null) {
            mensaje(ventanaActividades, "Selecciona una fecha válida.");
        } else {
            Actividad actividad = new Actividad(
                    sesion.generarId("ACT"), titulo, descripcion, fecha);
            sesion.getEstudiante().agregarActividad(actividad);
            txtTituloAct.setText("");
            txtDescripcionAct.setText("");
            actualizarActividades();
            mensaje(ventanaActividades, "Actividad registrada correctamente.");
        }
    }

    private void completarActividad() {
        if (cmbActividades.getSelectedItem() == null) {
            mensaje(ventanaActividades, "No hay actividades pendientes.");
            return;
        }
        String id = idDe(cmbActividades.getSelectedItem());
        Actividad actividad = sesion.getEstudiante().buscarActividad(id);
        if (actividad != null) {
            actividad.marcarCompletada();
            actualizarActividades();
            mensaje(ventanaActividades, "Actividad completada.");
        }
    }

    private void actualizarActividades() {
        Estudiante estudiante = sesion.getEstudiante();
        Fecha hoy = sesion.getHoy();
        boolean soloPendientes = chkSoloPendientesAct.isSelected();
        cmbActividades.removeAllItems();

        int total = estudiante.getActividades().size();
        int pendientes = 0;
        int completadas = 0;
        int paraHoy = 0;

        for (Actividad a : estudiante.getActividades()) {
            if (a.isCompletada()) {
                completadas++;
            } else {
                pendientes++;
                cmbActividades.addItem(a.getId() + " | " + a.getTitulo());
                if (a.getFecha().equals(hoy)) {
                    paraHoy++;
                }
            }
        }
        lblResumenAct.setText("Total: " + total + "   |   Para hoy: " + paraHoy
                + "   |   Pendientes: " + pendientes + "   |   Completadas: " + completadas);

        int fila = 0;
        for (Actividad actividad : estudiante.getActividades()) {
            if ((!soloPendientes || !actividad.isCompletada()) && fila < MAX_ACTIVIDADES) {
                String desc = actividad.getDescripcion().isEmpty() ? "(sin descripción)" : actividad.getDescripcion();
                String fechaTxt = actividad.getFecha().toString();
                if (!actividad.isCompletada() && actividad.getFecha().equals(hoy)) {
                    fechaTxt = fechaTxt + " [HOY]";
                }
                String estadoTxt = actividad.isCompletada() ? "Completada" : "Pendiente";
                Color colorEstado = actividad.isCompletada() ? Color.BLUE : Color.ORANGE;

                celdasActividades[fila][0].setText(actividad.getId());
                celdasActividades[fila][0].setForeground(COLOR_PRIMARIO);
                celdasActividades[fila][1].setText(actividad.getTitulo());
                celdasActividades[fila][2].setText(desc);
                celdasActividades[fila][3].setText(fechaTxt);
                celdasActividades[fila][4].setText(estadoTxt);
                celdasActividades[fila][4].setForeground(colorEstado);
                for (int c = 0; c < 5; c++) {
                    celdasActividades[fila][c].setVisible(true);
                }
                fila++;
            }
        }
        for (int f = fila; f < MAX_ACTIVIDADES; f++) {
            for (int c = 0; c < 5; c++) {
                celdasActividades[f][c].setText("");
                celdasActividades[f][c].setVisible(false);
            }
        }
    }

    private void inicializarVentanaReportes() {
        ventanaReportes = new JFrame("UnsaLife - Reportes mensuales");
        ventanaReportes.setSize(920, 560);
        ventanaReportes.setLayout(new BorderLayout(8, 8));
        ventanaReportes.setDefaultCloseOperation(HIDE_ON_CLOSE);
        ventanaReportes.getContentPane().setBackground(COLOR_FONDO);

        rbGenerar = new JRadioButton("Generar reporte del mes", true);
        rbVer = new JRadioButton("Ver reportes ya generados");
        rbGenerar.setBackground(COLOR_PANEL);
        rbVer.setBackground(COLOR_PANEL);

        grupoModo = new ButtonGroup();
        grupoModo.add(rbGenerar);
        grupoModo.add(rbVer);

        pfReporte = new PanelFecha(sesion.getHoy(), false);
        chkGrafico = new JCheckBox("Incluir desglose por categoría", true);
        chkGrafico.setBackground(COLOR_PANEL);

        btnAceptarRep = new JButton("Aceptar");
        btnVolverRep = new JButton("Volver");
        estilizarBotonPrimario(btnAceptarRep);
        estilizarBotonSalir(btnVolverRep);

        JPanel panelSuperior = new JPanel(new GridLayout(2, 1, 0, 4));
        panelSuperior.setBackground(COLOR_PANEL);

        JPanel fila1 = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 5));
        fila1.setBackground(COLOR_PANEL);
        JLabel lblModo = new JLabel("Modo de consulta:");
        lblModo.setForeground(COLOR_PRIMARIO);
        fila1.add(lblModo);
        fila1.add(rbGenerar);
        fila1.add(rbVer);
        panelSuperior.add(fila1);

        JPanel fila2 = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 5));
        fila2.setBackground(COLOR_PANEL);
        fila2.add(new JLabel("Período:"));
        fila2.add(pfReporte);
        fila2.add(chkGrafico);
        fila2.add(btnAceptarRep);
        panelSuperior.add(fila2);

        ventanaReportes.add(panelSuperior, BorderLayout.NORTH);

        JPanel centro = new JPanel(new BorderLayout(0, 6));
        centro.setBackground(COLOR_FONDO);

        JPanel franjaResumen = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 4));
        franjaResumen.setBackground(COLOR_PANEL);
        lblResumenRep = new JLabel("Elige una opción y pulsa Aceptar.");
        lblResumenRep.setForeground(COLOR_PRIMARIO);
        franjaResumen.add(lblResumenRep);
        centro.add(franjaResumen, BorderLayout.NORTH);

        JPanel panelTablaReporte = new JPanel(new GridLayout(MAX_FILAS_REP + 1, 5, 2, 2));
        panelTablaReporte.setBackground(COLOR_FONDO);

        encabezadosReporte = new JTextField[5];
        String[] titulos = {"Categoría", "Monto Gastado", "Porcentaje", "-", "-"};
        for (int c = 0; c < 5; c++) {
            encabezadosReporte[c] = crearEncabezadoTabla(titulos[c]);
            panelTablaReporte.add(encabezadosReporte[c]);
        }

        celdasReporte = new JTextField[MAX_FILAS_REP][5];
        for (int f = 0; f < MAX_FILAS_REP; f++) {
            Color fondo = (f % 2 == 0) ? Color.WHITE : COLOR_FILA_IMPAR;
            for (int c = 0; c < 5; c++) {
                celdasReporte[f][c] = crearCeldaTabla("", fondo, Color.BLACK, JTextField.CENTER);
                celdasReporte[f][c].setVisible(false);
                panelTablaReporte.add(celdasReporte[f][c]);
            }
        }
        centro.add(panelTablaReporte, BorderLayout.CENTER);
        ventanaReportes.add(centro, BorderLayout.CENTER);

        JPanel panelSur = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 6));
        panelSur.setBackground(COLOR_FONDO);
        panelSur.add(btnVolverRep);
        ventanaReportes.add(panelSur, BorderLayout.SOUTH);

        EscuchaReportes escucha = new EscuchaReportes();
        rbGenerar.addActionListener(escucha);
        rbVer.addActionListener(escucha);
        btnAceptarRep.addActionListener(escucha);
        btnVolverRep.addActionListener(escucha);

        ventanaReportes.setLocationRelativeTo(null);
    }

    private void generarReporte() {
        Fecha fecha = pfReporte.getFecha();
        Estudiante est = sesion.getEstudiante();
        ReporteMensual reporte = est.generarReporte(fecha);
        Presupuesto p = est.buscarPresupuesto(fecha);

        String estadoPres = "Sin presupuesto";
        if (p != null) {
            estadoPres = p.verificarLimite()
                    ? "Saldo: " + dinero(p.calcularSaldoRestante())
                    : "Excedido: " + dinero(-p.calcularSaldoRestante());
        }
        lblResumenRep.setText("Período: " + Fecha.textoMesAnio(fecha.getMes(), fecha.getAnio())
                + " | Gastado: " + dinero(reporte.getTotalGastos())
                + " (" + estadoPres + ")"
                + " | Metas cumplidas: " + reporte.getMetasCumplidas()
                + " | Actividades realizadas: " + reporte.getActividadesRealizadas());

        String[] titulos = {"Categoría", "Monto Gastado", "Porcentaje del Total", "-", "-"};
        for (int c = 0; c < 5; c++) {
            encabezadosReporte[c].setText(titulos[c]);
            encabezadosReporte[c].setVisible(c < 3);
        }

        int fila = 0;
        if (chkGrafico.isSelected() && p != null && !p.getGastos().isEmpty()) {
            HashMap<String, Double> porCategoria = new HashMap<>();
            for (Gasto g : p.getGastos()) {
                double acum = porCategoria.containsKey(g.getCategoria()) ? porCategoria.get(g.getCategoria()) : 0.0;
                porCategoria.put(g.getCategoria(), acum + g.getMonto());
            }

            for (String cat : porCategoria.keySet()) {
                if (fila < MAX_FILAS_REP) {
                    double monto = porCategoria.get(cat);
                    double porc = Math.round((monto * 100.0 / reporte.getTotalGastos()) * 10) / 10.0;

                    celdasReporte[fila][0].setText(cat);
                    celdasReporte[fila][0].setForeground(COLOR_PRIMARIO);
                    celdasReporte[fila][1].setText(dinero(monto));
                    celdasReporte[fila][2].setText(porc + " %");
                    celdasReporte[fila][2].setForeground(Color.BLUE);
                    for (int c = 0; c < 5; c++) {
                        celdasReporte[fila][c].setVisible(c < 3);
                    }
                    fila++;
                }
            }
        }
        for (int f = fila; f < MAX_FILAS_REP; f++) {
            for (int c = 0; c < 5; c++) {
                celdasReporte[f][c].setText("");
                celdasReporte[f][c].setVisible(false);
            }
        }
    }

    private void verReportes() {
        lblResumenRep.setText("Historial de reportes generados en esta sesión.");
        String[] titulos = {"Código", "Período", "Total Gastos", "Metas Cumplidas", "Actividades Realizadas"};
        for (int c = 0; c < 5; c++) {
            encabezadosReporte[c].setText(titulos[c]);
            encabezadosReporte[c].setVisible(true);
        }

        int fila = 0;
        for (ReporteMensual r : sesion.getEstudiante().getReportes()) {
            if (fila < MAX_FILAS_REP) {
                celdasReporte[fila][0].setText(r.getIdReporte());
                celdasReporte[fila][0].setForeground(COLOR_PRIMARIO);
                celdasReporte[fila][1].setText(Fecha.textoMesAnio(r.getMes(), r.getAnio()));
                celdasReporte[fila][2].setText(dinero(r.getTotalGastos()));
                celdasReporte[fila][3].setText("" + r.getMetasCumplidas());
                celdasReporte[fila][3].setForeground(Color.BLUE);
                celdasReporte[fila][4].setText("" + r.getActividadesRealizadas());
                celdasReporte[fila][4].setForeground(COLOR_PRIMARIO);
                for (int c = 0; c < 5; c++) {
                    celdasReporte[fila][c].setVisible(true);
                }
                fila++;
            }
        }
        for (int f = fila; f < MAX_FILAS_REP; f++) {
            for (int c = 0; c < 5; c++) {
                celdasReporte[f][c].setText("");
                celdasReporte[f][c].setVisible(false);
            }
        }
    }

    private class EscuchaIngresar implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String nombre = txtNombre.getText().trim();
            String correo = txtCorreo.getText().trim();
            if (nombre.isEmpty()) {
                JOptionPane.showMessageDialog(Main.this, "Escribe tu nombre.");
            } else if (correo.indexOf('@') < 1 || correo.length() < 5) {
                JOptionPane.showMessageDialog(Main.this, "Escribe un correo válido (ej. nombre@unsa.edu.pe).");
            } else {
                sesion.iniciarEstudiante(nombre, correo);
                cargarDatosEstudiante();
                actualizarResumenPrincipal();
                ventanaPrincipal.setVisible(true);
                setVisible(false);
            }
        }
    }

    private class EscuchaMenu implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == btnGastos) {
                cargarEstadoInicialGastos();
                ventanaGastos.setVisible(true);
            } else if (e.getSource() == btnMetas) {
                actualizarMetas();
                ventanaMetas.setVisible(true);
            } else if (e.getSource() == btnActividades) {
                actualizarActividades();
                ventanaActividades.setVisible(true);
            } else if (e.getSource() == btnReportes) {
                ventanaReportes.setVisible(true);
            } else if (e.getSource() == btnActualizar) {
                actualizarResumenPrincipal();
            } else if (e.getSource() == btnSalir) {
                System.exit(0);
            }
        }
    }

    private class EscuchaGastos implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == btnCrearPresupuesto) {
                crearPresupuesto();
            } else if (e.getSource() == btnRegistrarGasto) {
                registrarGasto();
            } else if (e.getSource() == btnVerDetalle) {
                verDetallePresupuesto();
            } else if (e.getSource() == btnVerTodos) {
                verTodosPresupuestos();
            } else if (e.getSource() == btnVolverGastos) {
                ventanaGastos.setVisible(false);
            }
        }
    }

    private class EscuchaMetas implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == btnCrearMeta) {
                crearMeta();
            } else if (e.getSource() == btnAgregarTarea) {
                agregarTarea();
            } else if (e.getSource() == btnCompletarTarea) {
                completarTarea();
            } else if (e.getSource() == chkSoloPendientesTareas) {
                actualizarMetas();
            } else if (e.getSource() == btnVolverMetas) {
                ventanaMetas.setVisible(false);
            }
        }
    }

    private class EscuchaActividades implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == btnCrearAct) {
                crearActividad();
            } else if (e.getSource() == btnCompletarAct) {
                completarActividad();
            } else if (e.getSource() == chkSoloPendientesAct) {
                actualizarActividades();
            } else if (e.getSource() == btnVolverAct) {
                ventanaActividades.setVisible(false);
            }
        }
    }

    private class EscuchaReportes implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == rbGenerar) {
                pfReporte.setHabilitado(true);
                chkGrafico.setEnabled(true);
            } else if (e.getSource() == rbVer) {
                pfReporte.setHabilitado(false);
                chkGrafico.setEnabled(false);
            } else if (e.getSource() == btnAceptarRep) {
                if (rbGenerar.isSelected()) {
                    generarReporte();
                } else {
                    verReportes();
                }
            } else if (e.getSource() == btnVolverRep) {
                ventanaReportes.setVisible(false);
            }
        }
    }

    public static void main(String[] args) {
        new Main();
    }
}
