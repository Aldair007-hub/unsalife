# UnsaLife - Gestor integral de vida universitaria

**Versión 3: persistencia con archivos (Java Swing).**

Aplicación de escritorio en Java para controlar gastos, administrar metas académicas con sus tareas, registrar actividades y consultar reportes mensuales. Todo se maneja con ventanas, botones y listas desplegables.

En esta versión los datos **se guardan en archivos**: al cerrar el programa no se pierde nada, y al volver a ingresar con el mismo correo se recuperan tus presupuestos, gastos, metas, tareas, actividades y reportes. Además, el programa lleva un historial de tus acciones y puede exportar los reportes a archivos de texto.

**Equipo three-team (EPIS - UNSA, Desarrollo de Software Orientado a Objetos):**

| N.º | Integrante |
| :--- | :--- |
| 1 | Piero Andree Zamora Aquino (líder) |
| 2 | Ronald David Jala Paricanaza |
| 3 | Kevin Aldair Solis Florez |

---

## 1. Cómo usar el programa

### 1.1 Reglas generales de uso

- Todo se maneja con el **mouse y el teclado** dentro de ventanas. Cada módulo tiene su propia ventana y se cierra con el botón **Volver**.
- Los resultados se muestran en **tablas con encabezados**, y los avisos y errores aparecen en **ventanas emergentes**.
- **Los datos se guardan automáticamente** después de cada operación que los cambia (crear, registrar, completar, generar). No existe un botón "Guardar" y no hace falta pulsar nada al salir.
- El programa asigna un **código automático** a cada elemento. Los números suben de uno en uno y **continúan donde quedaron** aunque cierres y vuelvas a abrir el programa:

  | Elemento | Código |
  | :--- | :--- |
  | Presupuesto | `PRE-1` |
  | Gasto | `GAS-1` |
  | Meta | `META-1` |
  | Tarea | `TAR-1` |
  | Actividad | `ACT-1` |

- Las **fechas se eligen en listas desplegables** (día, mes y año). El año se puede elegir entre tres años antes y cinco años después del actual. Si eliges un día que no existe (por ejemplo, 31 de febrero), el programa avisa y no guarda nada. La fecha de hoy se toma de la computadora.
- Los **montos** se escriben como número, con punto o coma para los decimales (`25.50` o `25,50`). Si escribes letras, un monto vacío o un valor menor o igual a cero, el programa muestra un aviso y no se cierra.
- Las tablas no tienen barra de desplazamiento, por eso muestran un número limitado de filas (se indica en cada ventana).

### 1.2 Ventana de inicio y archivos de datos

Al ejecutar el programa aparece una ventana que pide tu **nombre** y tu **correo**. Pulsa **Ingresar**. El correo identifica al usuario: **cada correo tiene sus propios archivos**.

| Situación | Qué hace el programa |
| :--- | :--- |
| Primera vez con ese correo | Crea tu cuenta y tu archivo de datos, y entra al menú principal. |
| Ya existe el archivo de ese correo | Carga tus datos y muestra un resumen de bienvenida (cuántos presupuestos, metas, actividades y reportes tienes). Se usa el nombre guardado, no el que escribas. |
| El archivo existe pero no se puede leer | Avisa que está dañado o es de otra versión del programa, indica su nombre y **no entra**. No se sobrescribe nada. |

El correo no distingue mayúsculas de minúsculas (`Ana@unsa.edu.pe` y `ana@unsa.edu.pe` son el mismo usuario).

Validaciones del inicio:

- Nombre vacío: "Escribe tu nombre."
- Correo sin `@` o muy corto: "Escribe un correo válido (ej. nombre@unsa.edu.pe)."
- Correo con alguno de los caracteres `\ / : * ? " < > |`: se rechaza, porque el correo forma parte del nombre de los archivos.

**Archivos que genera el programa** (por cada correo):

| Archivo | Tipo | Contenido |
| :--- | :--- | :--- |
| `unsalife_<correo>.dat` | Archivo de objetos | Todos tus datos y los contadores de códigos. |
| `historial_<correo>.txt` | Archivo de texto | Una línea por cada acción realizada, con fecha y hora. |
| `reporte_<correo>_<año>-<mes>.txt` | Archivo de texto | Reporte mensual exportado (solo si lo exportas). |

El archivo `.dat` no es legible con un editor de texto: solo el programa puede leerlo. Los otros dos se pueden abrir con cualquier editor.

### 1.3 Menú principal

Muestra tu nombre, la fecha de hoy, el **archivo de datos en uso con la hora del último guardado**, un **mensaje de estado con color** y dos tablas:

- **Tabla de resumen:** una fila por módulo (presupuesto del mes, metas y tareas, actividades, reportes mensuales) con su estado actual y un indicador.
- **Tabla de alertas:** muestra hasta 6 avisos, como las tareas vencidas y las actividades programadas para hoy. Si no hay ninguno, indica "Todo al día".

A la derecha están los botones de cada módulo:

| Botón | Qué abre |
| :--- | :--- |
| Gastos y presupuestos | Ventana de gastos |
| Metas académicas y tareas | Ventana de metas |
| Actividades | Ventana de actividades |
| Reportes mensuales | Ventana de reportes |
| Historial de acciones | Ventana con el historial |
| Actualizar resumen | Recalcula el resumen con los datos actuales |
| Salir | Cierra el programa |

El **mensaje de estado** aparece en verde ("Todo en orden por ahora.") o en rojo ("Atención: ...") cuando superaste el límite del presupuesto o tienes tareas vencidas. El resumen no se actualiza solo: después de trabajar en otra ventana, pulsa **Actualizar resumen**.

**Orden recomendado la primera vez:** primero crea un presupuesto, luego registra gastos, después crea metas y tareas, luego actividades, y al final revisa los reportes y el historial.

### 1.4 Ventana de gastos y presupuestos

Tiene tres secciones en la parte superior y una tabla debajo:

- **1. Crear presupuesto mensual:** elige mes y año, escribe el límite en soles y pulsa **Crear presupuesto**. Solo se permite un presupuesto por mes.
- **2. Registrar gasto:** elige la fecha, escribe el monto, elige o escribe una categoría y, si quieres, una descripción. Pulsa **Registrar gasto**.
  **Importante:** el gasto solo se registra si ya existe un presupuesto para el mes de esa fecha. Si no existe, el programa avisa que debes crearlo primero.
  La lista de categorías (Alimentación, Transporte, Ocio, Salud, Materiales, Otros) también permite **escribir una categoría nueva**. Las categorías se normalizan: "comida" se guarda como "Comida", para que los gastos del mismo tipo se agrupen bien en el reporte.
- **3. Consultar presupuesto:** **Ver detalle del mes** muestra el límite, lo gastado con su porcentaje, el saldo y la tabla de gastos del mes (se muestran los últimos 12). **Ver todos mis presupuestos** lista los presupuestos creados (hasta 12).
- **Estado con color:** sobre la tabla, una línea indica si el presupuesto está **dentro del límite**, si ya usaste **más del 80 %** (naranja) o si **superaste el límite** (rojo). Al registrar un gasto también aparece un aviso emergente con el saldo restante.

### 1.5 Ventana de metas académicas y tareas

- **1. Nueva meta académica:** escribe el título y la materia, elige la fecha límite y pulsa **Crear meta**.
- **2. Nueva tarea asociada:** elige la meta en la lista desplegable, escribe el título y una descripción (opcional), elige su fecha límite y pulsa **Agregar tarea**. Si la tarea vence después de la fecha límite de la meta, el programa muestra una nota de aviso, pero igual la agrega. Necesitas tener al menos una meta creada.
- **3. Completar tarea:** elige una tarea pendiente en la lista y pulsa **Marcar como completada**. Muestra el nuevo progreso de la meta. Cuando completas todas las tareas, el programa felicita porque la meta quedó cumplida.
- **Mostrar solo tareas pendientes:** la casilla filtra la tabla de tareas y se aplica al instante.
- **Tablas:** una tabla de metas (código, meta, materia, fecha límite y progreso; muestra las últimas 5) y una tabla de tareas (código, meta, tarea, detalle, límite y estado; muestra hasta 7). Las tareas pendientes cuya fecha límite ya pasó aparecen con el estado **VENCIDA**, y una línea de aviso indica cuántas hay (en **rojo** si hay alguna, en **azul** si no hay ninguna).
- El progreso de una meta es el porcentaje de sus tareas que están completadas.

### 1.6 Ventana de actividades

- **1. Nueva actividad:** escribe el título, una descripción (opcional), elige la fecha y pulsa **Registrar actividad**.
- **2. Gestionar:** elige una actividad pendiente en la lista y pulsa **Marcar como completada**. Las actividades completadas se cuentan en el reporte del mes de su fecha.
- **Mostrar solo actividades pendientes:** la casilla filtra la tabla.
- **Tabla:** código, actividad, descripción, fecha y estado (Pendiente o Completada); muestra hasta 12. Sobre la tabla hay un resumen con el total, las actividades de hoy, las pendientes y las completadas.

### 1.7 Ventana de reportes mensuales

Se elige una de dos opciones con botones de opción y se pulsa **Aceptar**:

- **Generar reporte del mes:** elige mes y año. Muestra el total de gastos, las metas cumplidas, las actividades realizadas, el estado del presupuesto del mes y, si dejas marcada la casilla **Incluir desglose por categoría**, una tabla con el monto y el porcentaje de cada categoría.
- **Ver reportes ya generados:** lista los reportes guardados (código, período, total de gastos, metas cumplidas y actividades realizadas).

Cada vez que generas un reporte, **queda guardado** con tus datos.

**Exportar a .txt:** guarda en un archivo de texto el último reporte que generaste en esta ejecución. Si todavía no generaste ninguno, el programa te avisa. El archivo se llama `reporte_<correo>_<año>-<mes>.txt`; si lo exportas de nuevo, se reemplaza.

### 1.8 Ventana de historial de acciones

Muestra las **últimas 15 acciones** registradas, con la más reciente arriba, y la cantidad total de acciones. Cada línea tiene la fecha y la hora y lo que se hizo, por ejemplo:

```
27/11/2026 22:10:41 | Gasto GAS-1 registrado: S/ 250.0 en Comida
```

El historial se guarda en `historial_<correo>.txt` y **se va acumulando**: no se borra al cerrar el programa. Registra la creación de la cuenta, el inicio de sesión, los presupuestos, gastos, metas, tareas y actividades, los reportes generados y los reportes exportados.

### 1.9 Validaciones de entrada

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
| Inicio | El nombre es obligatorio, el correo debe tener `@` y no puede tener caracteres no permitidos en nombres de archivo. |
| Archivos | Si no se puede guardar, el programa avisa, muestra el error en el menú principal y no se cierra. |

---

## 2. Ejemplo paso a paso

Este recorrido usa todas las funciones del programa. Los datos de ejemplo usan noviembre de 2026; si lo pruebas en otro mes, cambia las fechas por las del mes actual.

### Paso 1: ejecutar el programa

Compila y ejecuta.

### Paso 2: ingresar por primera vez

Escribe el nombre `Ana Torres` y el correo `ana@unsa.edu.pe`, y pulsa **Ingresar**.

**Resultado esperado:** aparece el menú principal con tu saludo, la fecha de hoy y la línea `Archivo de datos: unsalife_ana@unsa.edu.pe.dat`. En la carpeta del proyecto se crearon `unsalife_ana@unsa.edu.pe.dat` y `historial_ana@unsa.edu.pe.txt`.

### Paso 3: crear el presupuesto del mes

Pulsa **Gastos y presupuestos**. Elige mes `11`, año `2026`, escribe el límite `300` y pulsa **Crear presupuesto**.

**Resultado esperado:** aviso `Presupuesto PRE-1 creado.` y el detalle del presupuesto en la tabla.

### Paso 4: registrar gastos

En la sección 2 elige la fecha de hoy, escribe el monto `250`, la categoría `comida` y la descripción `almuerzos del mes`. Pulsa **Registrar gasto**.

**Resultado esperado:** `Gasto GAS-1 registrado. Saldo restante: S/ 50.0` y un aviso de que ya usaste más del 80 % del presupuesto. La categoría queda guardada como `Comida`.

Registra un segundo gasto: monto `20`, categoría `Transporte`. El saldo restante pasa a `S/ 30.0`.

### Paso 5: ver el detalle del presupuesto

En la sección 3 elige mes `11` y año `2026`, y pulsa **Ver detalle del mes**. Verás el límite (`S/ 300.0`), lo gastado (`S/ 270.0`, 90 %), el saldo y la tabla de gastos. La línea de estado aparece en naranja.

Pulsa **Volver**.

### Paso 6: crear una meta con tareas

En el menú principal pulsa **Metas académicas y tareas**.

1. Sección 1: título `Aprobar DSOO`, materia `DSOO`, fecha límite `15/12/2026`. Pulsa **Crear meta** (`Meta META-1 creada.`).
2. Sección 2: elige `META-1`, título `Lab 12`, fecha `30/11/2026`. Pulsa **Agregar tarea**.
3. Repite con el título `Informe final` y la fecha `10/12/2026`.

### Paso 7: completar una tarea

En la sección 3 elige `TAR-1` y pulsa **Marcar como completada**.

**Resultado esperado:** `¡Tarea completada! Progreso de la meta: 50.0%`. Marca la casilla **Mostrar solo tareas pendientes** y verás que `TAR-1` desaparece de la tabla. Pulsa **Volver**.

### Paso 8: registrar una actividad

En el menú principal pulsa **Actividades**. Escribe el título `Estudiar para el examen`, elige la fecha de hoy y pulsa **Registrar actividad** (`Actividad registrada correctamente.`). Luego elige `ACT-1` en la lista y pulsa **Marcar como completada**. Pulsa **Volver**.

### Paso 9: generar y exportar el reporte mensual

Pulsa **Reportes mensuales**. Deja seleccionado **Generar reporte del mes**, elige mes `11` y año `2026`, deja marcada la casilla del desglose y pulsa **Aceptar**.

**Resultado esperado:** `Período: 11/2026 | Gastado: S/ 270.0 (Saldo: S/ 30.0) | Metas cumplidas: 0 | Actividades realizadas: 1`, y la tabla con Comida `92.6 %` y Transporte `7.4 %`.

Pulsa **Exportar a .txt**. Aparece el aviso con el nombre `reporte_ana@unsa.edu.pe_2026-11.txt`. Ábrelo con un editor de texto: contiene el encabezado, el resumen del mes y las barras por categoría:

```
UNSALIFE - REPORTE MENSUAL
Estudiante: Ana Torres (ana@unsa.edu.pe)
Exportado el: 27/11/2026 22:15

REPORTE MENSUAL 11/2026 (REP-2026-11)
  Total de gastos: S/ 270.0
  Metas cumplidas: 0
  Actividades realizadas: 1

Gastos por categoría - 11/2026
  Comida    [###################-] S/ 250.0 (92.6%)
  Transporte    [#-------------------] S/ 20.0 (7.4%)
```

Después elige **Ver reportes ya generados** y pulsa **Aceptar** para ver la lista. Pulsa **Volver**.

### Paso 10: ver el historial

En el menú principal pulsa **Historial de acciones**. Verás cada paso anterior con su fecha y hora, el más reciente arriba. Pulsa **Volver**.

### Paso 11: cerrar y volver a abrir (comprobar que los datos se guardaron)

Pulsa **Salir**. Vuelve a ejecutar el programa e ingresa con el correo `ANA@unsa.edu.pe` (con mayúsculas) y cualquier nombre.

**Resultado esperado:** el mensaje `Bienvenido de nuevo, Ana Torres.` con el resumen `Presupuestos: 1`, `Metas: 1`, `Actividades: 1` y `Reportes: 1`. En el menú principal verás tus datos tal como los dejaste.

Para comprobar que los códigos continúan, crea otro presupuesto (por ejemplo, mes `12` y año `2026`): recibirá el código `PRE-2` y no `PRE-1`.

### Prueba de errores recomendada

Prueba estos casos para ver las validaciones:

- Escribe `abc` como límite de presupuesto o como monto de un gasto.
- Elige el día `31` y el mes `02` al registrar un gasto o una actividad.
- Crea un segundo presupuesto para el mismo mes.
- Registra un gasto en un mes que no tiene presupuesto.
- Agrega una tarea sin haber creado ninguna meta.
- Crea una tarea con fecha límite posterior a la de su meta (muestra una nota de aviso).
- Crea una tarea con una fecha pasada y observa el estado `VENCIDA` y el mensaje en rojo.
- Pulsa **Exportar a .txt** sin haber generado un reporte.
- Ingresa con un correo que contenga `/`.
- Ingresa con otro correo: empiezas con datos vacíos, porque cada correo tiene sus propios archivos.

---

## 3. Estructura del proyecto

```
unsalife/
├── Main.java                  # Ventanas, tablas y escuchas de eventos (interfaz gráfica)
├── Sesion.java                # Estudiante actual, fecha de hoy, códigos y manejo de archivos
├── PanelFecha.java            # Panel reutilizable con listas de día, mes y año
├── Estudiante.java            # Centro del sistema: presupuestos, metas, actividades y reportes
├── Fecha.java                 # Fecha propia con validación (incluye años bisiestos)
├── Presupuesto.java           # Límite mensual y lista de gastos
├── Gasto.java                 # Monto, categoría, fecha y descripción
├── MetaAcademica.java         # Meta con lista de tareas y progreso
├── Tarea.java                 # Tarea con fecha límite (hereda de ElementoPlanificable)
├── ElementoPlanificable.java  # Superclase abstracta
├── Actividad.java             # Actividad con fecha (hereda de ElementoPlanificable)
├── ReporteMensual.java        # Resumen del mes con gráfico por categoría
└── .gitignore                 # Evita subir al repositorio los archivos de datos
```

Las ventanas (inicio, menú principal, gastos, metas, actividades, reportes e historial) están dentro de `Main.java`. Las clases del modelo (desde `Estudiante` hasta `ReporteMensual`) no usan Swing ni archivos; todo el manejo de archivos está en `Sesion.java`.

**Distribución del trabajo.** Cada archivo (y cada sección de `Main.java` y `Sesion.java`) empieza con un comentario `PARTE DEL INTEGRANTE ...`:

| Integrante | Clases del modelo | `Sesion` | `Main` |
| :--- | :--- | :--- | :--- |
| 1 - Piero Andree Zamora Aquino (líder) | `Fecha`, `ElementoPlanificable`, `Tarea`, `MetaAcademica`, `PanelFecha` | Códigos y archivo de objetos (`guardar`, `cargar`) | Ventana de metas y tareas |
| 2 - Ronald David Jala Paricanaza | `Estudiante`, `Presupuesto`, `Gasto` | - | Inicio de sesión, menú principal, guardado automático y gastos |
| 3 - Kevin Aldair Solis Florez | `Actividad`, `ReporteMensual` | Historial y exportación de reportes | Actividades, reportes, exportación e historial |

**Archivos generados al usar el programa** (no se suben al repositorio, el `.gitignore` los excluye): `*.dat`, `historial_*.txt` y `reporte_*.txt`.

---

## 4. Conceptos aplicados

**Programación orientada a objetos**

- **Encapsulamiento:** atributos privados y métodos públicos.
- **Herencia:** `Actividad` y `Tarea` heredan de `ElementoPlanificable`; las ventanas son `JFrame` y `PanelFecha` hereda de `JPanel`.
- **Polimorfismo:** una misma lista trata actividades y tareas de la misma forma.
- **Composición:** un presupuesto contiene sus gastos, una meta contiene sus tareas y el estudiante contiene todo lo demás.
- **Colecciones:** `ArrayList` para las listas y `HashMap` para los gastos por categoría y los contadores de códigos.

**Interfaces gráficas y eventos**

- **Componentes Swing:** `JFrame`, `JPanel`, `JLabel`, `JTextField`, `JButton`, `JComboBox`, `JCheckBox`, `JRadioButton` con `ButtonGroup` y `JOptionPane`.
- **Distribución:** `BorderLayout`, `FlowLayout` y `GridLayout`, combinados con paneles anidados.
- **Programación orientada a eventos:** cada ventana tiene una clase interior que implementa `ActionListener` y distingue el componente con `getSource()`.
- **Color:** paleta común para todas las ventanas y `Color` para resaltar estados (verde, naranja y rojo).

**Archivos (Capítulo 7 del curso)**

| Tipo de archivo | Uso en UnsaLife | Clases |
| :--- | :--- | :--- |
| Archivos binarios de objetos | Guardar y cargar todos los datos del estudiante y los contadores de códigos | `ObjectOutputStream`, `ObjectInputStream`, `FileOutputStream`, `FileInputStream`, `Serializable` |
| Archivos de texto (salida) | Historial en modo añadir y reportes exportados | `PrintWriter`, `FileWriter` |
| Archivos de texto (entrada) | Leer el historial | `Scanner`, `FileReader` |

- **Serialización:** las clases del modelo implementan `Serializable`. Se guarda un solo objeto `Estudiante` y con él todo lo que contiene.
- **Primera vez:** se detecta capturando `FileNotFoundException` al intentar abrir el archivo.
- **Archivo dañado:** se detecta con `IOException` y `ClassNotFoundException`.
- **Modo añadir:** `new FileWriter(nombre, true)` agrega al final del historial sin borrar lo anterior.
- **Formato binario:** el archivo `.dat` es un archivo binario. Los objetos se guardan en el formato nativo de Java y no como caracteres de texto, por eso no se pueden leer con un editor. Se escribe con `FileOutputStream` a través de `ObjectOutputStream`.
- **`DataOutputStream` y `DataInputStream`** (datos primitivos sueltos) no se usaron, porque la serialización ya guarda todos los atributos de los objetos.

---
