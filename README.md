# UnsaLife - Gestor integral de vida universitaria

Aplicación de consola en Java para controlar gastos, organizar actividades y recordatorios, administrar metas académicas y consultar reportes mensuales.

---

## 1. Cómo usar el programa

### 1.1 Reglas generales de uso

- Todo se maneja escribiendo el número de la opción y pulsando **Enter**.
- En cualquier submenú, la opción `0` vuelve al menú anterior. En el menú principal, la opción `0` cierra el programa.
- **Los datos viven solo en la memoria:** el programa no guarda nada en archivos. Al cerrarlo se pierde todo lo que registraste, y la próxima vez empiezas de cero.
- El programa asigna un **código automático** a cada elemento que creas. Los números suben de uno en uno:

  | Elemento | Código |
  | :--- | :--- |
  | Presupuesto | `PRE-1` |
  | Gasto | `GAS-1` |
  | Meta | `META-1` |
  | Tarea | `TAR-1` |
  | Actividad | `ACT-1` |
  | Recordatorio | `REC-1` |

  Cuando el programa te pida un código, escríbelo como aparece en pantalla (por ejemplo `META-1`). Las mayúsculas y minúsculas no importan en metas, tareas y actividades.
- Las fechas se piden siempre en este orden: **mes, año y día**. El programa muestra el rango válido en cada pregunta, por ejemplo `Día (1-30)`. Cuando también pide hora, las pide después: hora (0-23) y minutos (0-59).
- Escribe **solo números** en los menús y en los montos. Para decimales usa punto (`25.50`). Si escribes letras, el programa se cierra con un error.

### 1.2 Menú principal

Al iniciar, el programa pide tu nombre y tu correo, muestra la fecha de hoy (la toma de la computadora) y abre este menú:

```
1. Gastos y presupuestos
2. Metas académicas y tareas
3. Actividades y recordatorios
4. Reportes mensuales
5. Resumen general
0. Salir
```

**Orden recomendado la primera vez:** primero crea un presupuesto (opción 1), luego registra gastos, después crea metas y tareas (opción 2), luego actividades y recordatorios (opción 3), y al final revisa los reportes (opción 4) y el resumen (opción 5).

### 1.3 Opción 1: Gastos y presupuestos

```
1. Crear presupuesto mensual
2. Registrar un gasto
3. Ver el detalle de un presupuesto
4. Ver todos los presupuestos
0. Volver
```

- **Crear presupuesto mensual:** pide mes, año y el monto límite en soles. Solo se permite un presupuesto por mes.
- **Registrar un gasto:** pide la fecha del gasto (mes, año y día), el monto, la categoría y una descripción (puede quedar vacía con Enter). Al terminar muestra el saldo restante.
  **Importante:** el gasto solo se registra si ya existe un presupuesto para el mes de esa fecha. Si no existe, el programa avisa que debes crearlo primero.
- **Ver el detalle de un presupuesto:** pide mes y año, y muestra el límite, lo gastado con su porcentaje, el saldo, el estado y la lista de gastos con su fecha.
- **Ver todos los presupuestos:** lista los presupuestos creados.
- **Alertas automáticas:** cuando el gasto acumulado pasa el 80 % del límite, aparece un aviso. Si supera el límite, aparece una alerta.
- **Las categorías se normalizan:** "comida" se guarda como "Comida", para que los gastos del mismo tipo se agrupen bien en el reporte.

### 1.4 Opción 2: Metas académicas y tareas

```
1. Crear meta académica
2. Agregar tarea a una meta
3. Ver metas y sus tareas
4. Marcar tarea como completada
0. Volver
```

- **Crear meta académica:** pide el título, la materia y la fecha límite de la meta.
- **Agregar tarea a una meta:** primero lista las metas y pide el código de la meta. Luego pide el título de la tarea, la descripción (opcional) y su fecha límite. Si la tarea vence después de la fecha límite de la meta, el programa muestra una nota de aviso, pero igual la agrega. Necesitas tener al menos una meta creada.
- **Ver metas y sus tareas:** muestra cada meta con su progreso en porcentaje y debajo sus tareas. Las tareas pendientes cuya fecha límite ya pasó aparecen marcadas con `[VENCIDA]`.
- **Marcar tarea como completada:** pide el código de la meta y luego el de la tarea. Muestra el nuevo progreso de la meta (por ejemplo 50.0 % si completaste una de dos tareas). Cuando completas todas las tareas, el programa felicita porque la meta quedó cumplida.
- El progreso de una meta es el porcentaje de sus tareas que están completadas.

### 1.5 Opción 3: Actividades y recordatorios

```
1. Crear actividad
2. Ver mis actividades
3. Marcar actividad como completada
4. Agregar recordatorio (a una actividad o tarea)
5. Ver recordatorios y notificaciones
0. Volver
```

- **Crear actividad:** pide el título, la descripción (opcional) y la fecha y hora (mes, año, día, hora y minutos).
- **Ver mis actividades:** lista las actividades con su estado y cuántos recordatorios tiene cada una.
- **Marcar actividad como completada:** pide el código de la actividad (por ejemplo `ACT-1`).
- **Agregar recordatorio:** lista las actividades y tareas juntas, y pide el código de la que quieras (`ACT-1` o `TAR-1`). Luego pide el mensaje del recordatorio y la fecha y hora de la alarma. Los recordatorios sirven tanto para actividades como para tareas.
- **Ver recordatorios y notificaciones:** muestra todos los recordatorios y, aparte, las notificaciones pendientes. Una notificación aparece cuando la hora de la alarma ya llegó y la actividad o tarea todavía no está completada. Si completas la actividad, su notificación deja de aparecer. Para ver una notificación en una prueba, pon una alarma con fecha y hora ya pasadas.

### 1.6 Opción 4: Reportes mensuales

```
1. Generar el reporte de un mes
2. Ver los reportes generados
0. Volver
```

- **Generar el reporte de un mes:** pide mes y año, y muestra el total de gastos, las metas cumplidas, las actividades realizadas, el presupuesto del mes con su saldo y un gráfico de barras hecho con texto, con los gastos por categoría y su porcentaje. Por ejemplo, con 250 soles en comida y 20 en transporte, comida ocupa casi toda la barra (92.6 %) y transporte una parte muy pequeña (7.4 %).
- **Ver los reportes generados:** lista los reportes que ya generaste en esta sesión.

### 1.7 Opción 5: Resumen general

Muestra de una sola vez tus datos, la fecha de hoy, cuánto has gastado del presupuesto del mes actual, cuántas metas tienes, cuántas tareas están pendientes y cuántas vencidas, y cuántas actividades siguen pendientes. Sirve como tablero rápido de cómo vas.

### 1.8 Validaciones de entrada

Si un dato está fuera de rango, el programa lo dice y vuelve a preguntar hasta que sea válido:

| Dato | Regla |
| :--- | :--- |
| Mes | Entre 1 y 12. Mensaje: "El mes debe estar entre 1 y 12." |
| Año | Entre 2000 y 2100. |
| Día | Debe existir en ese mes, considerando años bisiestos. Por ejemplo, en noviembre el programa responde "Ese mes tiene solo 30 días." |
| Hora | Entre 0 y 23. |
| Minutos | Entre 0 y 59. |
| Monto | Mayor que cero. |

---

## 2. Ejemplo paso a paso

Este recorrido usa todas las funciones del programa. Escribe cada línea y pulsa Enter. Los datos de ejemplo usan noviembre de 2026; si lo pruebas en otro mes, cambia las fechas por las del mes actual.

### Paso 1: iniciar el programa

```bash
java Main
```

Cuando pida `Tu nombre:`, escribe tu nombre. Cuando pida `Tu correo:`, escribe tu correo. Verás el saludo, la fecha de hoy y el menú principal.

### Paso 2: crear el presupuesto del mes

Escribe `1` (Gastos y presupuestos), luego `1` (Crear presupuesto mensual). Responde: mes `11`, año `2026`, límite `300`.

**Resultado esperado:** `Presupuesto PRE-1 creado.`

### Paso 3: registrar un gasto

Escribe `2` (Registrar un gasto). Responde: mes `11`, año `2026`, día `13`, monto `250`, categoría `comida`, descripción `almuerzo`.

**Resultado esperado:** `Gasto GAS-1 registrado. Saldo restante: S/ 50.0` y el aviso `ya usaste más del 80% de tu presupuesto`.

### Paso 4: ver el detalle del presupuesto

Escribe `3` y responde mes `11`, año `2026`. Verás el límite (S/ 300), lo gastado (S/ 250, 83.3 %), el saldo y la lista con el gasto `GAS-1`. Escribe `0` para volver al menú principal.

### Paso 5: crear una meta con tareas

1. Escribe `2` (Metas académicas) y luego `1` (Crear meta). Responde: título `Proyecto final`, materia `POO`, fecha límite mes `12`, año `2026`, día `20`.
   **Resultado:** `Meta META-1 creada.`
2. Escribe `2` (Agregar tarea). Código de la meta: `META-1`. Título `Entregar informe`, sin descripción (Enter), fecha límite mes `11`, año `2026`, día `25`.
   **Resultado:** `Tarea TAR-1 agregada a META-1.`
3. Repite la opción `2` para agregar una segunda tarea, `Subir código`, con fecha límite mes `12`, año `2026`, día `5`.

### Paso 6: ver el progreso y completar una tarea

Escribe `3` para ver la meta con 0.0 % de progreso y sus dos tareas. Si alguna fecha límite ya pasó, verás `[VENCIDA]` junto a ella.

Escribe `4`, código de meta `META-1` y código de tarea `TAR-1`.

**Resultado esperado:** `¡Tarea completada! Progreso de la meta: 50.0%`. Si completas también `TAR-2`, el programa felicita por cumplir la meta. Escribe `0` para volver.

### Paso 7: crear una actividad y un recordatorio

1. Escribe `3` (Actividades y recordatorios) y luego `1` (Crear actividad). Responde: título `Examen POO`, sin descripción, fecha mes `11`, año `2026`, día `20`, hora `10`, minutos `30`.
   **Resultado:** `Actividad ACT-1 creada.`
2. Escribe `4` (Agregar recordatorio). Código: `ACT-1`. Mensaje `Repasar clases`. Fecha de la alarma: mes `11`, año `2026`, día `1`, hora `8`, minutos `0` (una fecha ya pasada, para que se note la notificación).
   **Resultado:** `Recordatorio REC-1 agregado a ACT-1.`
3. Escribe `5` (Ver recordatorios y notificaciones). Verás el recordatorio en la lista y, debajo, la notificación `RECORDATORIO ... Repasar clases`, porque la alarma ya llegó y la actividad sigue pendiente. Escribe `0` para volver.

### Paso 8: generar el reporte mensual

Escribe `4` (Reportes mensuales) y luego `1` (Generar el reporte de un mes). Responde mes `11`, año `2026`. Verás el total de gastos, las metas cumplidas, el saldo del presupuesto y el gráfico de barras por categoría. Escribe `0` para volver.

### Paso 9: ver el resumen general

Escribe `5`. Verás tu nombre, la fecha de hoy, el gasto del mes (S/ 250 de S/ 300), el número de metas, las tareas pendientes y vencidas, y las actividades pendientes.

### Paso 10: salir

Escribe `0`. El programa se despide con `Hasta pronto` y cierra. Recuerda que los datos no se guardan.

### Prueba de errores recomendada

Para comprobar las validaciones: al pedir un mes, escribe `13`; al pedir un día en noviembre, escribe `31`; al pedir un monto, escribe `0`. En los tres casos el programa debe avisar y volver a preguntar. Escribe letras solo si quieres ver el error conocido de `NumberFormatException`.

---

## 3. Estructura del proyecto

```
unsalife/
├── Main.java                  # Menús y punto de entrada del programa
├── Estudiante.java            # Centro del sistema: presupuestos, metas, actividades y reportes
├── Fecha.java                 # Fecha propia con validación (incluye años bisiestos)
├── Presupuesto.java           # Límite mensual y lista de gastos
├── Gasto.java                 # Monto, categoría, fecha y descripción
├── MetaAcademica.java         # Meta con lista de tareas y progreso
├── Tarea.java                 # Tarea con fecha límite (hereda de ElementoPlanificable)
├── ElementoPlanificable.java  # Superclase abstracta
├── Actividad.java             # Actividad con fecha y hora (hereda de ElementoPlanificable)
├── Recordatorio.java          # Alarma asociada a una actividad o tarea
├── ReporteMensual.java        # Resumen del mes con gráfico por categoría
└── img/                       # Diagrama de clases UML
```

---

## 4. Conceptos de POO aplicados

- **Encapsulamiento:** atributos privados y métodos públicos.
- **Herencia:** `Actividad` y `Tarea` heredan de `ElementoPlanificable`.
- **Polimorfismo:** una misma lista trata actividades y tareas de la misma forma.
- **Composición:** un presupuesto contiene sus gastos y una meta contiene sus tareas.
- **Colecciones:** `ArrayList` para las listas y `HashMap` para los gastos por categoría.