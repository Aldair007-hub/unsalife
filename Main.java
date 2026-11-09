import java.util.Date;
import java.util.HashMap;
import java.util.Scanner;

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
