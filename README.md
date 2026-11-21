# UnsaLife - Gestor integral de vida universitaria

**Versión 2: interfaz gráfica de usuario (Java Swing).**

Aplicación de escritorio en Java para controlar gastos, administrar metas académicas con sus tareas, registrar actividades y consultar reportes mensuales. Todo se maneja con ventanas, botones y listas desplegables, sin escribir comandos.

---

## 1. Cómo usar el programa

### 1.1 Reglas generales de uso

- Todo se maneja con el **mouse y el teclado** dentro de ventanas. Cada módulo tiene su propia ventana y se cierra con el botón **Volver**.
- Los resultados se muestran en un área de texto dentro de cada ventana, y los avisos y errores aparecen en **ventanas emergentes**.
- **Los datos viven solo en la memoria:** el programa no guarda nada en archivos. Al cerrarlo se pierde todo lo que registraste, y la próxima vez empiezas de cero.
- El programa asigna un **código automático** a cada elemento que creas. Los números suben de uno en uno:

  | Elemento | Código |
  | :--- | :--- |
  | Presupuesto | `PRE-1` |
  | Gasto | `GAS-1` |
  | Meta | `META-1` |
  | Tarea | `TAR-1` |
  | Actividad | `ACT-1` |

  Ya no hace falta escribir los códigos: las tareas y actividades pendientes se eligen de una lista desplegable.
- Las **fechas se eligen en listas desplegables** (día, mes y año). El año se puede elegir entre tres años antes y cinco años después del actual. Si eliges un día que no existe (por ejemplo, 31 de febrero), el programa avisa y no guarda nada.
- Los **montos** se escriben como número, con punto o coma para los decimales (`25.50` o `25,50`). Si escribes letras, un monto vacío o un valor menor o igual a cero, el programa muestra un aviso y no se cierra.
- Cada área de resultados muestra **como máximo 20 líneas**. Si hay más, se conservan el encabezado y lo más reciente, y una línea intermedia indica cuántas líneas no se muestran.

### 1.2 Ventana de inicio

Al ejecutar el programa aparece una ventana que pide tu **nombre** y tu **correo**. Pulsa **Ingresar** (o Enter en el campo del correo). La fecha de hoy se toma automáticamente de la computadora.

- Si dejas el nombre vacío, avisa: "Escribe tu nombre."
- Si el correo no tiene `@` o es muy corto, avisa: "Escribe un correo válido".

### 1.3 Menú principal

Muestra tu nombre, la fecha de hoy, un **mensaje de estado con color** y un **resumen general**. A la derecha están los botones de cada módulo:

| Botón | Qué abre |
| :--- | :--- |
| Gastos y presupuestos | Ventana de gastos |
| Metas académicas y tareas | Ventana de metas |
| Actividades | Ventana de actividades |
| Reportes mensuales | Ventana de reportes |
| Actualizar resumen | Recalcula el resumen con los datos actuales |
| Salir | Cierra el programa |

- **Resumen general:** tus datos, el presupuesto del mes actual (límite, gastado y saldo), cuántas metas tienes, cuántas tareas están pendientes y vencidas, cuántas actividades hay y cuántas siguen pendientes, y cuántos reportes generaste.
- **Mensaje de estado:** aparece en **verde** ("Todo en orden por ahora.") o en **rojo** ("Atención: ...") cuando superaste el límite del presupuesto o tienes tareas vencidas.
- El resumen no se actualiza solo: después de trabajar en otra ventana, pulsa **Actualizar resumen**.

**Orden recomendado la primera vez:** primero crea un presupuesto, luego registra gastos, después crea metas y tareas, luego actividades, y al final revisa los reportes y el resumen.

### 1.4 Ventana de gastos y presupuestos

Tiene tres secciones en la parte superior y el área de resultados debajo:

- **1. Crear presupuesto mensual:** elige mes y año, escribe el límite en soles y pulsa **Crear presupuesto**. Solo se permite un presupuesto por mes.
- **2. Registrar gasto:** elige la fecha (día, mes y año), escribe el monto, elige o escribe una categoría y, si quieres, una descripción. Pulsa **Registrar gasto**.
  **Importante:** el gasto solo se registra si ya existe un presupuesto para el mes de esa fecha. Si no existe, el programa avisa que debes crearlo primero.
  La lista de categorías (Alimentación, Transporte, Ocio, Salud, Materiales, Otros) también permite **escribir una categoría nueva**. Las categorías se normalizan: "comida" se guarda como "Comida", para que los gastos del mismo tipo se agrupen bien en el reporte.
- **3. Consultar presupuesto:** **Ver detalle del mes** muestra el límite, lo gastado con su porcentaje, el saldo y la lista de gastos del mes elegido. **Ver todos mis presupuestos** lista los presupuestos creados.
- **Estado con color:** sobre el área de resultados, una línea indica si el presupuesto está **dentro del límite** (verde), si ya usaste **más del 80 %** (naranja) o si **superaste el límite** (rojo). Al registrar un gasto también aparece un aviso emergente con el saldo restante.

### 1.5 Ventana de metas académicas y tareas

- **1. Nueva meta:** escribe el título y la materia, elige la fecha límite y pulsa **Crear meta**.
- **2. Nueva tarea:** elige la meta en la lista desplegable, escribe el título de la tarea y una descripción (opcional), elige su fecha límite y pulsa **Agregar tarea**. Si la tarea vence después de la fecha límite de la meta, el programa muestra una nota de aviso, pero igual la agrega. Necesitas tener al menos una meta creada.
- **3. Completar tarea:** elige una tarea pendiente en la lista y pulsa **Marcar como completada**. Muestra el nuevo progreso de la meta. Cuando completas todas las tareas, el programa felicita porque la meta quedó cumplida.
- **Mostrar solo tareas pendientes:** la casilla filtra el listado y se aplica al instante.
- **Listado:** cada meta aparece con su progreso en porcentaje y debajo sus tareas. Las tareas pendientes cuya fecha límite ya pasó aparecen marcadas con `[VENCIDA]`, y una línea en **rojo** indica cuántas hay. Si no hay ninguna, la línea aparece en **verde**.
- El progreso de una meta es el porcentaje de sus tareas que están completadas.

### 1.6 Ventana de actividades

- **Registrar actividad:** escribe el título, una descripción (opcional), elige la fecha (día, mes y año) y pulsa **Registrar actividad**.
- **Marcar como completada:** elige una actividad pendiente en la lista y pulsa **Marcar como completada**. Las actividades completadas se cuentan en el reporte del mes de su fecha.
- **Listado:** el área de resultados muestra todas las actividades con su fecha y su estado (Pendiente o Completada).

### 1.7 Ventana de reportes mensuales

Se elige una de dos opciones con botones de opción y se pulsa **Aceptar**:

- **Generar reporte del mes:** elige mes y año. Muestra el total de gastos, las metas cumplidas, las actividades realizadas, el presupuesto del mes y, si dejas marcada la casilla **Incluir gráfico de gastos por categoría**, un gráfico de barras hecho con texto con el monto y el porcentaje de cada categoría. Por ejemplo, con 250 soles en comida y 20 en transporte, comida ocupa casi toda la barra (92.6 %) y transporte una parte muy pequeña (7.4 %).
- **Ver reportes ya generados:** lista los reportes que generaste en esta sesión.

### 1.8 Validaciones de entrada

El programa revisa los datos antes de guardarlos y avisa con una ventana emergente si algo está mal:

| Dato | Regla |
| :--- | :--- |
| Monto y límite | Número mayor que cero. Se rechazan letras, campos vacíos y valores negativos o en cero. |
| Fecha | El día debe existir en ese mes, considerando años bisiestos. |
| Presupuesto | Solo uno por mes. |
| Gasto | Debe existir el presupuesto del mes de la fecha elegida. La categoría no puede quedar vacía. |
| Meta | El título y la materia son obligatorios. |
| Tarea | Se necesita al menos una meta y el título es obligatorio. |
| Actividad | El título es obligatorio. |
| Inicio | El nombre es obligatorio y el correo debe tener `@`. |

---

## 2. Ejemplo paso a paso

Este recorrido usa todas las funciones del programa. Los datos de ejemplo usan noviembre de 2026; si lo pruebas en otro mes, cambia las fechas por las del mes actual.

### Paso 1: ejecutar el programa

Abre la carpeta del proyecto en tu IDE y ejecuta `Main.java`. También puedes hacerlo desde la terminal, dentro de la carpeta:

```bash
javac -encoding UTF-8 *.java
java Main
```

> Los archivos no declaran `package`, así que deben permanecer juntos en la misma carpeta.

### Paso 2: iniciar sesión

Escribe tu nombre y tu correo y pulsa **Ingresar**. Aparece el menú principal con tu saludo, la fecha de hoy y el resumen vacío.

![Ventana de inicio](img/paso-01-inicio.png)

### Paso 3: crear el presupuesto del mes

Pulsa **Gastos y presupuestos**. En la sección 1 elige mes `11`, año `2026`, escribe el límite `300` y pulsa **Crear presupuesto**.

**Resultado esperado:** aviso `Presupuesto PRE-1 creado.` y el detalle del presupuesto en el área de resultados.

![Crear presupuesto](img/paso-02-presupuesto.png)

### Paso 4: registrar gastos

En la sección 2 elige la fecha de hoy, escribe el monto `250`, escribe la categoría `comida` y la descripción `almuerzos del mes`. Pulsa **Registrar gasto**.

**Resultado esperado:** `Gasto GAS-1 registrado. Saldo restante: S/ 50.0` y un aviso de que ya usaste más del 80 % del presupuesto. La categoría queda guardada como `Comida`.

Registra un segundo gasto: monto `20`, categoría `Transporte`. El saldo restante pasa a `S/ 30.0`.

![Registrar gasto](img/paso-03-gasto.png)

### Paso 5: ver el detalle del presupuesto

En la sección 3 elige mes `11` y año `2026`, y pulsa **Ver detalle del mes**. Verás el límite (`S/ 300.0`), lo gastado (`S/ 270.0`, 90 %), el saldo y la lista de gastos. La línea de estado aparece en naranja.

Pulsa **Volver**.

### Paso 6: crear una meta con tareas

En el menú principal pulsa **Metas académicas y tareas**.

1. Sección 1: título `Aprobar DSOO`, materia `DSOO`, fecha límite `15/12/2026`. Pulsa **Crear meta** (`Meta META-1 creada.`).
2. Sección 2: elige `META-1`, título `Lab 12`, fecha `30/11/2026`. Pulsa **Agregar tarea**.
3. Repite con el título `Informe final` y la fecha `10/12/2026`.

![Meta con tareas](img/paso-04-metas.png)

### Paso 7: completar una tarea

En la sección 3 elige `TAR-1` y pulsa **Marcar como completada**.

**Resultado esperado:** `¡Tarea completada! Progreso de la meta: 50.0%`. Marca la casilla **Mostrar solo tareas pendientes** y verás que `TAR-1` desaparece del listado. Pulsa **Volver**.

### Paso 8: registrar una actividad

En el menú principal pulsa **Actividades**. Escribe el título `Estudiar para el examen`, elige la fecha y pulsa **Registrar actividad** (`Actividad registrada correctamente.`). Luego elige `ACT-1` en la lista y pulsa **Marcar como completada**. Pulsa **Volver**.

![Actividades](img/paso-05-actividades.png)

### Paso 9: generar el reporte mensual

Pulsa **Reportes mensuales**. Deja seleccionado **Generar reporte del mes**, elige mes `11` y año `2026`, deja marcada la casilla del gráfico y pulsa **Aceptar**.

**Resultado esperado:** total de gastos `S/ 270.0`, metas cumplidas `0`, actividades realizadas `1`, el presupuesto del mes y las barras por categoría: Comida `92.6%` y Transporte `7.4%`.

Después elige **Ver reportes ya generados** y pulsa **Aceptar** para ver la lista.

![Reporte mensual](img/paso-06-reporte.png)

### Paso 10: ver el resumen general

Pulsa **Volver** y, en el menú principal, **Actualizar resumen**. Verás el presupuesto del mes, las metas, las tareas pendientes, las actividades y el reporte generado.

### Paso 11: salir

Pulsa **Salir**. Recuerda que los datos se pierden al cerrar el programa.

### Prueba de errores recomendada

Prueba estos casos para ver las validaciones:

- Escribe `abc` como límite de presupuesto o como monto de un gasto.
- Elige el día `31` y el mes `02` al registrar un gasto o una actividad.
- Crea un segundo presupuesto para el mismo mes.
- Registra un gasto en un mes que no tiene presupuesto.
- Agrega una tarea sin haber creado ninguna meta.
- Crea una tarea con fecha límite posterior a la de su meta (muestra una nota de aviso).
- Crea una tarea con una fecha pasada y observa la marca `[VENCIDA]` y el mensaje en rojo.

---

## 3. Estructura del proyecto

```
unsalife/
├── Main.java                  # Punto de entrada: abre la ventana de inicio
├── Sesion.java                # Estado compartido: estudiante, fecha de hoy y generador de ids
├── Utilidades.java            # Apoyo común: lectura de montos, formato y área de resultados
├── PanelFecha.java            # Panel reutilizable con listas de día, mes y año
├── VentanaInicio.java         # Pide nombre y correo
├── VentanaPrincipal.java      # Menú principal y resumen general
├── VentanaGastos.java         # Presupuestos y gastos
├── VentanaMetas.java          # Metas académicas y tareas
├── VentanaActividades.java    # Actividades
├── VentanaReportes.java       # Reportes mensuales
├── Estudiante.java            # Centro del sistema: presupuestos, metas, actividades y reportes
├── Fecha.java                 # Fecha propia con validación (incluye años bisiestos)
├── Presupuesto.java           # Límite mensual y lista de gastos
├── Gasto.java                 # Monto, categoría, fecha y descripción
├── MetaAcademica.java         # Meta con lista de tareas y progreso
├── Tarea.java                 # Tarea con fecha límite (hereda de ElementoPlanificable)
├── ElementoPlanificable.java  # Superclase abstracta
├── Actividad.java             # Actividad con fecha (hereda de ElementoPlanificable)
├── ReporteMensual.java        # Resumen del mes con gráfico por categoría
└── img/                       # Diagrama de clases UML y capturas
```

Las clases del modelo (desde `Estudiante` hasta `ReporteMensual`) no usan Swing: solo las clases `Ventana...`, `PanelFecha` y `Utilidades` conocen la interfaz gráfica.

---

## 4. Conceptos aplicados

**Programación orientada a objetos**

- **Encapsulamiento:** atributos privados y métodos públicos.
- **Herencia:** `Actividad` y `Tarea` heredan de `ElementoPlanificable`; las ventanas heredan de `JFrame` y `PanelFecha` hereda de `JPanel`.
- **Polimorfismo:** una misma lista trata actividades y tareas de la misma forma.
- **Composición:** un presupuesto contiene sus gastos y una meta contiene sus tareas.
- **Colecciones:** `ArrayList` para las listas y `HashMap` para los gastos por categoría y los contadores de códigos.

**Interfaces gráficas y eventos**

- **Componentes Swing:** `JFrame`, `JPanel`, `JLabel`, `JTextField`, `JTextArea`, `JButton`, `JComboBox`, `JCheckBox`, `JRadioButton` con `ButtonGroup` y `JOptionPane`.
- **Distribución:** `BorderLayout`, `FlowLayout` y `GridLayout`, combinados con paneles anidados.
- **Programación orientada a eventos:** cada ventana tiene una clase interior que implementa `ActionListener` y distingue el componente con `getSource()`.
- **Color:** `Color` para resaltar estados (verde, naranja y rojo).

---

## 5. Cambios respecto a la versión 1 (consola)

| Aspecto | Versión 1 (consola) | Versión 2 (GUI) |
| :--- | :--- | :--- |
| Entrada de datos | `Scanner` y menús numerados | Campos de texto, listas, casillas y botones |
| Fechas | Se escribían mes, año y día | Se eligen en listas desplegables |
| Códigos | Se escribían a mano | Se eligen de una lista |
| Errores de entrada | El programa se cerraba si se escribían letras | Un aviso emergente y el programa sigue |
| Salida | `System.out.println` | Áreas de texto y ventanas emergentes |
| Actividades | Con fecha, hora y recordatorios | Solo con fecha; se retiró la clase `Recordatorio` |
| Organización | Todo en `Main` | Una ventana por módulo y una clase de sesión |

---

## 6. Limitaciones

- Los datos solo existen mientras la aplicación está abierta; no se usan archivos ni bases de datos.
- Los reportes muestran barras hechas con texto, no gráficos visuales.
- Las áreas de resultados no tienen barra de desplazamiento, por eso muestran como máximo 20 líneas.
- No hay recordatorios ni notificaciones en esta versión.