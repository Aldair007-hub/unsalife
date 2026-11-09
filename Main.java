import java.util.*;

public class Main {


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        HashMap<String, Integer> contadores = new HashMap<>(); 
        System.out.println("\n-- UnsaLife - Gestor integral de vida universitaria --");

        System.out.print("\nTu nombre: ");
        String nombre = scanner.nextLine().trim();
        System.out.print("Tu correo: ");
        String correo = scanner.nextLine().trim();
        Estudiante estudiante = new Estudiante(generarId("EST", contadores), nombre, correo);
        System.out.println("\n¡Hola, " + nombre + "!");
      
        Fecha hoy = new Fecha(new Date());
        System.out.println("Fecha de hoy: " + hoy);
        // Menú principal
        int opcion;
        do {
            System.out.println("\n---------- MENÚ PRINCIPAL ----------");
            System.out.println("1. Gastos y presupuestos");
            System.out.println("2. Metas académicas y tareas");
            System.out.println("3. Reportes mensuales");
            System.out.println("4. Resumen general");
            System.out.println("5. Cargar datos de ejemplo");
            System.out.println("0. Salir");
            System.out.print("Elige una opción: ");
            opcion = Integer.parseInt(scanner.nextLine());
            switch (opcion) {
                case 1:
                    menuGastos(scanner, estudiante, hoy, contadores);
                    break;
                case 2:
                    menuMetas(scanner, estudiante, hoy, contadores);
                    break;
                case 3:
                    menuReportes(scanner, estudiante);
                    break;
                case 4:
                    mostrarResumenGeneral(estudiante, hoy);
                    break;
                case 5:
                    cargarGastosEjemplo(estudiante, hoy, contadores);
                    cargarMetasEjemplo(estudiante, hoy, contadores);
                    break;
                case 0:
                    break;
                default:
                    System.out.println("  Opción no válida. Intenta de nuevo.");
                    break;
            }
        } while (opcion != 0);
        System.out.println("\nHasta pronto, " + estudiante.getNombre() + ". ¡Éxitos en tus estudios!");
    }

    public static String generarId(String prefijo, HashMap<String, Integer> contadores) {
        int numero = 1;
        if (contadores.containsKey(prefijo)) {
            numero = contadores.get(prefijo) + 1;
        }
        contadores.put(prefijo, numero);
        return prefijo + "-" + numero;
    }


    public static void menuReportes(Scanner scanner, Estudiante estudiante) {
        int opcion;
        do {
            System.out.println("\n--- Reportes mensuales ---");
            System.out.println("1. Generar el reporte de un mes");
            System.out.println("2. Ver los reportes generados");
            System.out.println("0. Volver");
            System.out.print("Elige una opción: ");
            opcion = Integer.parseInt(scanner.nextLine());
            switch (opcion) {
                case 1:
                    generarReporte(scanner, estudiante);
                    break;
                case 2:
                    listarReportes(estudiante);
                    break;
                case 0:
                    break;
                default:
                    System.out.println("  Opción no válida. Intenta de nuevo.");
                    break;
            }
        } while (opcion != 0);
    }

    public static void generarReporte(Scanner scanner, Estudiante estudiante) {
        System.out.println("\n[Reporte mensual]");
        Fecha fecha = lectorMesAnio(scanner);
        ReporteMensual reporte = estudiante.generarReporte(fecha);
        System.out.println();
        System.out.println(reporte);
        Presupuesto p = estudiante.buscarPresupuesto(fecha);
        if (p == null) {
            System.out.println("  Presupuesto del mes: (no hay presupuesto para ese mes)");
        } else {
            System.out.println("  Presupuesto del mes: S/ " + (Math.round(p.getMontoLimite() * 100) / 100.0)
                    + " | Saldo: S/ " + (Math.round(p.calcularSaldoRestante() * 100) / 100.0));
        }
        reporte.generarGrafico();
    }

    public static void listarReportes(Estudiante estudiante) {
        System.out.println("\n[Reportes generados]");
        if (estudiante.getReportes().isEmpty()) {
            System.out.println("  (ninguno)");
        }
        for (ReporteMensual r : estudiante.getReportes()) {
            System.out.println(r);
            System.out.println();
        }
    }


    public static void mostrarResumenGeneral(Estudiante estudiante, Fecha hoy) {
        System.out.println("\n---------- RESUMEN GENERAL ----------");
        System.out.println("Estudiante: " + estudiante);
        System.out.println("Fecha de hoy: " + hoy);

        Presupuesto p = estudiante.buscarPresupuesto(hoy);
        System.out.println("\nPresupuesto del mes actual (" + Fecha.textoMesAnio(hoy.getMes(), hoy.getAnio()) + "):");
        if (p == null) {
            System.out.println("  (no has creado el presupuesto de este mes)");
        } else {
            System.out.println("  Gastado S/ " + (Math.round(p.calcularTotalGastado() * 100) / 100.0) + " de S/ "
                    + (Math.round(p.getMontoLimite() * 100) / 100.0) + " | Saldo: S/ "
                    + (Math.round(p.calcularSaldoRestante() * 100) / 100.0));
        }

        int pendientes = 0;
        int vencidas = 0;
        for (MetaAcademica m : estudiante.getMetas()) {
            for (Tarea t : m.getTareas()) {
                if (!t.isCompletada()) {
                    pendientes++;
                }
                if (t.estaVencida(hoy)) {
                    vencidas++;
                }
            }
        }
        System.out.println("\nMetas académicas: " + estudiante.getMetas().size());
        System.out.println("Tareas pendientes: " + pendientes);
        System.out.println("Tareas vencidas  : " + vencidas);
    }
    
    public static void menuGastos(Scanner scanner, Estudiante estudiante, Fecha hoy,
            HashMap<String, Integer> contadores) {
        int opcion;
        do {
            System.out.println("\n--- Gastos y presupuestos ---");
            System.out.println("1. Crear presupuesto mensual");
            System.out.println("2. Registrar un gasto");
            System.out.println("3. Ver el detalle de un presupuesto");
            System.out.println("4. Ver todos los presupuestos");
            System.out.println("0. Volver");
            System.out.print("Elige una opción: ");
            opcion = Integer.parseInt(scanner.nextLine());
            switch (opcion) {
                case 1:
                    crearPresupuesto(scanner, estudiante, contadores);
                    break;
                case 2:
                    registrarGasto(scanner, estudiante, contadores);
                    break;
                case 3:
                    verDetallePresupuesto(scanner, estudiante);
                    break;
                case 4:
                    listarPresupuestos(estudiante);
                    break;
                case 0:
                    break;
                default:
                    System.out.println("  Opción no válida. Intenta de nuevo.");
                    break;
            }
        } while (opcion != 0);
    }

    public static void crearPresupuesto(Scanner scanner, Estudiante estudiante,
            HashMap<String, Integer> contadores) {
        System.out.println("\n[Nuevo presupuesto]");
        Fecha fecha = lectorMesAnio(scanner);
        if (estudiante.buscarPresupuesto(fecha) != null) {
            System.out.println("  Ya tienes un presupuesto para " + Fecha.textoMesAnio(fecha.getMes(), fecha.getAnio()) + ".");
        } else {
            double limite;
            do {
                System.out.print("Monto límite (S/): ");
                limite = Double.parseDouble(scanner.nextLine());
                if (limite <= 0) {
                    System.out.println("  El monto debe ser mayor que cero.");
                }
            } while (limite <= 0);
    
            Presupuesto p = new Presupuesto(generarId("PRE", contadores), limite, fecha.getMes(), fecha.getAnio());
            estudiante.crearPresupuesto(p);
            System.out.println("  Presupuesto " + p.getIdPresupuesto() + " creado.");
        }
    }

    public static void registrarGasto(Scanner scanner, Estudiante estudiante, HashMap<String, Integer> contadores) {
        System.out.println("\n[Nuevo gasto]");
        System.out.println("Fecha del gasto:");
        Fecha fecha = lectorFecha(scanner);

        Presupuesto p = estudiante.buscarPresupuesto(fecha);
        if (p == null) {
            System.out.println("  No tienes presupuesto para " + Fecha.textoMesAnio(fecha.getMes(), fecha.getAnio())
                    + ". Créalo primero (Gastos y presupuestos > opción 1).");
        } else {
            double monto;
            do {
                System.out.print("Monto (S/): ");
                monto = Double.parseDouble(scanner.nextLine());
                if (monto <= 0) {
                    System.out.println("  El monto debe ser mayor que cero.");
                }
            } while (monto <= 0);
    
            System.out.print("Categoría (ej. alimentación, transporte, ocio): ");
            String categoria = scanner.nextLine().trim();
            categoria = normalizarCategoria(categoria);
    
            System.out.print("Descripción (Enter para omitir): ");
            String descripcion = scanner.nextLine().trim();
    
            Gasto g = new Gasto(generarId("GAS", contadores), monto, categoria, fecha, descripcion);
            estudiante.registrarGasto(g);
    
            System.out.println("  Gasto " + g.getIdGasto() + " registrado. Saldo restante: S/ " + (Math.round(p.calcularSaldoRestante() * 100) / 100.0));
            if (!p.verificarLimite()) {
                System.out.println("  ¡ALERTA! Superaste el límite del mes por S/ " + (Math.round(-p.calcularSaldoRestante() * 100) / 100.0) + ".");
            } else if (p.calcularTotalGastado() >= 0.8 * p.getMontoLimite()) {
                System.out.println("  Aviso: ya usaste más del 80% de tu presupuesto.");
            }
        }

    }

    public static String normalizarCategoria(String categoria) {
        return categoria.substring(0, 1).toUpperCase() + categoria.substring(1).toLowerCase();
    }

    public static void verDetallePresupuesto(Scanner scanner, Estudiante estudiante) {
        if (estudiante.getPresupuestos().isEmpty()) {
            System.out.println("\n  Aún no tienes presupuestos.");
        } else {
            Fecha fecha = lectorMesAnio(scanner);
    
            Presupuesto p = estudiante.buscarPresupuesto(fecha);
            if (p == null) {
                System.out.println("  No existe un presupuesto para ese mes.");
            } else {
                double gastado = p.calcularTotalGastado();
                double porcentaje = gastado * 100.0 / p.getMontoLimite();
                System.out.println("\nPresupuesto " + Fecha.textoMesAnio(fecha.getMes(), fecha.getAnio()) + " (" + p.getIdPresupuesto() + ")");
                System.out.println("  Límite : S/ " + (Math.round(p.getMontoLimite() * 100) / 100.0));
                System.out.println("  Gastado: S/ " + (Math.round(gastado * 100) / 100.0) + " (" + (Math.round(porcentaje * 10) / 10.0) + "%)");
                System.out.println("  Saldo  : S/ " + (Math.round(p.calcularSaldoRestante() * 100) / 100.0));
                if (p.verificarLimite()) {
                    System.out.println("  Estado : Dentro del límite");
                } else {
                    System.out.println("  Estado : ¡LÍMITE SUPERADO!");
                }
                System.out.println("  Gastos registrados:");
                if (p.getGastos().isEmpty()) {
                    System.out.println("    (ninguno)");
                }
                for (Gasto g : p.getGastos()) {
                    System.out.println("    " + g);
                }
            }
        }
    }

    public static void listarPresupuestos(Estudiante estudiante) {
        System.out.println("\n[Mis presupuestos]");
        if (estudiante.getPresupuestos().isEmpty()) {
            System.out.println("  (ninguno)");
        }
        for (Presupuesto p : estudiante.getPresupuestos()) {
            System.out.println("  " + p);
        }
    }

    public static void cargarGastosEjemplo(Estudiante estudiante, Fecha hoy, HashMap<String, Integer> contadores) {
        if (estudiante.buscarPresupuesto(hoy) != null) {
            System.out.println("\n  Ya tienes un presupuesto este mes; no se cargaron gastos de ejemplo.");
        } else {
            Presupuesto p = new Presupuesto(generarId("PRE", contadores), 500, hoy.getMes(), hoy.getAnio());
            estudiante.crearPresupuesto(p);
            String[] categorias = {"Alimentación", "Transporte", "Fotocopias", "Alimentación", "Ocio"};
            double[] montos = {120, 45, 30, 85, 100};
            int[] dias = {3, 5, 8, 12, 15};
            for (int i = 0; i < categorias.length; i++) {
                Gasto g = new Gasto(generarId("GAS", contadores), montos[i], categorias[i],
                          new Fecha(dias[i], hoy.getMes(), hoy.getAnio()), "Gasto de ejemplo");
                estudiante.registrarGasto(g);
            }
            System.out.println("\n  Gastos de ejemplo cargados en " + Fecha.textoMesAnio(hoy.getMes(), hoy.getAnio()) + ".");
        }
    }

    public static void menuMetas(Scanner scanner, Estudiante estudiante, Fecha hoy,
            HashMap<String, Integer> contadores) {
        int opcion;
        do {
            System.out.println("\n--- Metas académicas y tareas ---");
            System.out.println("1. Crear meta académica");
            System.out.println("2. Agregar tarea a una meta");
            System.out.println("3. Ver metas y sus tareas");
            System.out.println("4. Marcar tarea como completada");
            System.out.println("0. Volver");
            System.out.print("Elige una opción: ");
            opcion = Integer.parseInt(scanner.nextLine());
            switch (opcion) {
                case 1:
                    crearMeta(scanner, estudiante, contadores);
                    break;
                case 2:
                    agregarTarea(scanner, estudiante, contadores);
                    break;
                case 3:
                    listarMetas(estudiante, hoy);
                    break;
                case 4:
                    completarTarea(scanner, estudiante, hoy);
                    break;
                case 0:
                    break;
                default:
                    System.out.println("  Opción no válida. Intenta de nuevo.");
                    break;
            }
        } while (opcion != 0);
    }

    public static void crearMeta(Scanner scanner, Estudiante estudiante, HashMap<String, Integer> contadores) {
        System.out.println("\n[Nueva meta académica]");
        System.out.print("Título de la meta: ");
        String titulo = scanner.nextLine().trim();
        System.out.print("Materia: ");
        String materia = scanner.nextLine().trim();
        System.out.println("Fecha límite de la meta:");
        MetaAcademica m = new MetaAcademica(generarId("META", contadores), titulo, materia, lectorFecha(scanner));
        estudiante.crearMeta(m);
        System.out.println("  Meta " + m.getIdMeta() + " creada.");
    }

    public static void agregarTarea(Scanner scanner, Estudiante estudiante, HashMap<String, Integer> contadores) {
        if (estudiante.getMetas().isEmpty()) {
            System.out.println("\n  Primero crea una meta académica.");
        } else {
            System.out.println("\n[Mis metas]");
            for (MetaAcademica m : estudiante.getMetas()) {
                System.out.println("  " + m);
            }
            System.out.print("Id de la meta (ej. META-1): ");
            String idMeta = scanner.nextLine().trim();
            MetaAcademica meta = estudiante.buscarMeta(idMeta);
            if (meta == null) {
                System.out.println("  No existe una meta con ese id.");
            } else {
                String titulo = scanner.nextLine().trim();
                System.out.print("Descripción (Enter para omitir): ");
                String descripcion = scanner.nextLine().trim();
                System.out.println("Fecha límite de la tarea:");
                Fecha fechaLimite = lectorFecha(scanner);
        
                if (fechaLimite.esPosteriorA(meta.getFechaLimite())) {
                    System.out.println("  Nota: la tarea vence después de la fecha límite de la meta.");
                }
                Tarea t = new Tarea(generarId("TAR", contadores), titulo, descripcion, fechaLimite);
                meta.agregarTarea(t);
                System.out.println("  Tarea " + t.getIdTarea() + " agregada a " + meta.getIdMeta() + ".");
            }
    
        }
    }

    public static void listarMetas(Estudiante estudiante, Fecha hoy) {
        System.out.println("\n[Mis metas académicas]");
        if (estudiante.getMetas().isEmpty()) {
            System.out.println("  (ninguna)");
        }
        for (MetaAcademica m : estudiante.getMetas()) {
            System.out.println("  " + m);
            if (m.getTareas().isEmpty()) {
                System.out.println("      (sin tareas)");
            }
            for (Tarea t : m.getTareas()) {
                String aviso = "";
                if (t.estaVencida(hoy)) {
                    aviso = " [VENCIDA]";
                }
                System.out.println("      " + t + aviso);
            }
        }
    }

    public static void completarTarea(Scanner scanner, Estudiante estudiante, Fecha hoy) {
        if (estudiante.getMetas().isEmpty()) {
            System.out.println("\n  Aún no tienes metas.");
        } else {
            listarMetas(estudiante, hoy);
            System.out.print("Id de la meta (ej. META-1): ");
            String idMeta = scanner.nextLine().trim();
            MetaAcademica meta = estudiante.buscarMeta(idMeta);
            if (meta == null) {
                System.out.println("  No existe una meta con ese id.");
            } else {
                System.out.print("Id de la tarea (ej. TAR-1): ");
                String idTarea = scanner.nextLine().trim();
                Tarea tarea = meta.buscarTarea(idTarea);
                if (tarea == null) {
                    System.out.println("  Esa meta no tiene una tarea con ese id.");
                } else if (tarea.isCompletada()) {
                    System.out.println("  Esa tarea ya estaba completada.");
                } else {
                    tarea.marcarCompletada();
                    meta.actualizarProgreso();
                    System.out.println("  ¡Tarea completada! Progreso de la meta: " + (Math.round(meta.getProgreso() * 10) / 10.0) + "%");
                    if (meta.estaCumplida()) {
                        System.out.println("  ¡Felicitaciones, cumpliste la meta \"" + meta.getTitulo() + "\"!");
                    }
                }
            }
        }
    }

    public static void cargarMetasEjemplo(Estudiante estudiante, Fecha hoy, HashMap<String, Integer> contadores) {
        if (!estudiante.getMetas().isEmpty()) {
            System.out.println("  Ya tienes metas; no se cargaron metas de ejemplo.");
        } else {
            int anio = hoy.getAnio();
            MetaAcademica m1 = new MetaAcademica(generarId("META", contadores), "Aprobar Desarrollo de Software OO", "Desarrollo de Software OO", new Fecha(10, 12, anio));
            Tarea t1 = new Tarea(generarId("TAR", contadores), "Repasar herencia y polimorfismo", "", new Fecha(20, hoy.getMes(), anio));
            Tarea t2 = new Tarea(generarId("TAR", contadores), "Practicar diagramas UML", "", new Fecha(25, hoy.getMes(), anio));
            t1.marcarCompletada();
            m1.agregarTarea(t1);
            m1.agregarTarea(t2);
            estudiante.crearMeta(m1);
    
            MetaAcademica m2 = new MetaAcademica(generarId("META", contadores), "Entregar proyecto UnsaLife", "Desarrollo de Software OO", new Fecha(28, hoy.getMes(), anio));
            Tarea t3 = new Tarea(generarId("TAR", contadores), "Versión consola", "", new Fecha(15, hoy.getMes(), anio));
            t3.marcarCompletada();
            m2.agregarTarea(t3);
            estudiante.crearMeta(m2);
    
            System.out.println("  Metas y tareas de ejemplo cargadas.");
        }
    }

    public static Fecha lectorMesAnio(Scanner scanner){
        int mes;
        do {
            System.out.print("  Mes (1-12): ");
            mes = Integer.parseInt(scanner.nextLine());
            if (mes < 1 || mes > 12) {
                System.out.println("  El mes debe estar entre 1 y 12.");
            }
        } while (mes < 1 || mes > 12);

        int anio;
        do {
            System.out.print("  Año (2000-2100): ");
            anio = Integer.parseInt(scanner.nextLine());
            if (anio < 2000 || anio > 2100) {
                System.out.println("  El año debe estar entre 2000 y 2100.");
            }
        } while (anio < 2000 || anio > 2100);

        return new Fecha(1, mes, anio);
    }

    public static Fecha lectorFecha(Scanner scanner){
        Fecha mesAnio = lectorMesAnio(scanner);
        int mes = mesAnio.getMes();
        int anio = mesAnio.getAnio();
        int dia;
        do {
            System.out.print("  Día (1-" + Fecha.diasDelMes(mes, anio) + "): ");
            dia = Integer.parseInt(scanner.nextLine());
            if (dia < 1 || dia > Fecha.diasDelMes(mes, anio)) {
                System.out.println("  Ese mes tiene solo " + Fecha.diasDelMes(mes, anio) + " días.");
            }
        } while (dia < 1 || dia > Fecha.diasDelMes(mes, anio));

        return new Fecha(dia, mes, anio);
    }
}
