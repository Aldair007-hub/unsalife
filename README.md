# UnsaLife - Gestor integral de vida universitaria

## 1. Descripción general del proyecto
UnsaLife es una aplicación que reúne el control de gastos, las actividades, los objetivos académicos y la organización personal del estudiante. El proyecto se desarrolla en Java con programación orientada a objetos y se organiza mediante Scrum, con cuatro sprints de una semana cada uno y una entrega semanal de un script ejecutable con sus evidencias.

### 1.1 Objetivo
Desarrollar UnsaLife, una aplicación que ayude al estudiante universitario a controlar sus gastos, organizar sus actividades y recordatorios, administrar sus metas académicas y consultar reportes mensuales.

### 1.2 Duración y organización

| Elemento | Definición |
| :--- | :--- |
| Duración total | 4 semanas |
| Número de sprints | 4 |
| Duración de cada sprint | 1 semana |
| Equipo de desarrollo | 3 integrantes |
| Entregas | Un script ejecutable al final de cada semana, con sus evidencias |
| Producto final | Aplicación integrada, probada y documentada |

### 1.3 Funcionalidades principales
- Registrar gastos y presupuestos.
- Organizar actividades y recordatorios.
- Administrar metas académicas.
- Mostrar gráficos y reportes mensuales.
- Relacionar objetivos con tareas y fechas límite.

### 1.4 Alcance tecnológico

| Componente | Tecnología / enfoque |
| :--- | :--- |
| Lenguaje | Java, con programación orientada a objetos |
| Versión 1 | Aplicación de consola con `Scanner`, usando solo los temas vistos en clase |
| Versiones siguientes | Interfaz gráfica, archivos o base de datos, según avancen los temas del curso |
| Control de versiones | Git y GitHub |

## 2. Organización del equipo Scrum

### 2.1 Roles

| Rol | Responsable | Responsabilidades |
| :--- | :--- | :--- |
| Product Owner | Piero Andree Zamora Aquino | Define prioridades, aclara requisitos y acepta las historias terminadas. |
| Scrum Master | Rotativo entre los integrantes en cada sprint | Facilita los eventos y ayuda a eliminar impedimentos. |
| Developers | Ronald David Jala Paricanaza y Kevin Aldair Solis Florez | Analizan, diseñan, programan, prueban y documentan. |

### 2.2 Eventos

| Evento | Frecuencia | Propósito |
| :--- | :--- | :--- |
| Planificación del sprint | Al inicio de cada semana | Definir el objetivo del sprint y seleccionar las historias. |
| Seguimiento diario | Diariamente | Revisar el avance hacia el objetivo y los impedimentos. |
| Revisión del sprint | Al final de cada semana | Demostrar el script ejecutable y recibir retroalimentación. |
| Retrospectiva | Al final de cada semana | Identificar mejoras en la forma de trabajar. |

## 3. Product Backlog

Las historias se priorizan según el valor para el usuario, el riesgo y las dependencias técnicas. Los puntos son una estimación relativa de esfuerzo, no horas de trabajo.

| ID | Historia de usuario | Prioridad | Puntos |
| :--- | :--- | :--- | :--- |
| HU01 | Como estudiante, quiero registrar mi presupuesto mensual para controlar mis gastos. | Alta | 3 |
| HU02 | Como estudiante, quiero registrar un gasto con fecha, monto, categoría y descripción. | Alta | 5 |
| HU03 | Como estudiante, quiero que el sistema me avise cuando me acerco o supero mi límite mensual. | Media | 3 |
| HU04 | Como estudiante, quiero ver el detalle de mi presupuesto del mes. | Media | 2 |
| HU05 | Como estudiante, quiero crear metas académicas con fecha límite. | Alta | 3 |
| HU06 | Como estudiante, quiero agregar tareas a una meta con su fecha límite. | Alta | 3 |
| HU07 | Como estudiante, quiero marcar tareas como completadas y ver el progreso de mi meta. | Media | 3 |
| HU08 | Como estudiante, quiero saber qué tareas están vencidas. | Media | 2 |
| HU09 | Como estudiante, quiero generar un reporte mensual con gráfico de gastos por categoría. | Alta | 5 |
| HU10 | Como estudiante, quiero crear actividades con fecha y hora. | Media | 3 |
| HU11 | Como estudiante, quiero agregar recordatorios a mis actividades y tareas. | Media | 5 |
| HU12 | Como estudiante, quiero ver mis recordatorios activos y las notificaciones pendientes. | Media | 2 |
| HU13 | Como estudiante, quiero que el sistema valide los rangos de mes, año, día y montos. | Alta | 2 |

**Criterios de prioridad:**
- **Alta:** indispensable para que la aplicación funcione.
- **Media:** complementa la aplicación y mejora la experiencia.

## 4. Sprints

### 4.1 Sprint 1 (semana 1): gastos y presupuestos
 
**Estado:** Terminado.
 
#### 4.1.1 Sprint Planning
 
**Objetivo del sprint:** que el estudiante pueda crear su presupuesto mensual, registrar sus gastos, ver el saldo disponible y recibir alertas cuando se acerca o supera su límite.
 
| ID | Historia seleccionada | Puntos |
| :--- | :--- | :--- |
| HU01 | Registrar presupuesto mensual | 3 |
| HU02 | Registrar un gasto | 5 |
| HU03 | Avisar al acercarse o superar el límite | 3 |
| HU04 | Ver el detalle del presupuesto del mes | 2 |
| HU13 | Validar rangos de mes, año, día y montos | 2 |
| | **Total** | **15** |
 
#### 4.1.2 Criterios de aceptación
- Se puede crear un presupuesto para un mes, y no se pueden crear dos presupuestos para el mismo mes.
- Un gasto solo se registra si existe un presupuesto para el mes de su fecha.
- Después de cada gasto se muestra el saldo restante.
- Se muestra un aviso al alcanzar el 80 % del límite y una alerta al superarlo.
- El detalle del presupuesto muestra el límite, lo gastado, el porcentaje usado, el saldo, el estado y la lista de gastos.
- El mes debe estar entre 1 y 12, el año entre 2000 y 2100, el día debe existir en ese mes y el monto debe ser mayor que cero. Si un dato está fuera de rango, se vuelve a pedir.
#### 4.1.3 Sprint Backlog 1
 
**Semana 1: Gastos y presupuestos**
 
**Primera presentación** (revisión del sprint, al final de la semana)
- Analizar los requisitos de presupuestos y gastos.
- Diseñar las clases `Fecha`, `Presupuesto` y `Gasto`, y reflejarlas en el diagrama de clases.
- Implementar la clase `Fecha`, con validación de meses, años y días según el mes (incluido el año bisiesto).
- Implementar la clase `Presupuesto`: agregar gastos, calcular el total gastado y el saldo, y verificar el límite.
- Implementar la clase `Gasto`, con fecha, monto, categoría, descripción y fecha de registro.
- Implementar el menú de gastos y presupuestos en la clase principal.
- Implementar las validaciones de entrada de mes, año, día y monto.
- Implementar las alertas al 80 % del límite y al superarlo.
- Probar los casos correctos y los casos de error.
- Preparar el script, las capturas de ejecución y las evidencias de la semana.

**Entregable:** script ejecutable de gastos y presupuestos, con sus evidencias (captura de ejecución, captura del meet, pruebas realizadas y enlace al repositorio).
 
#### 4.1.4 Resultado esperado
Al final de la semana, el estudiante puede crear su presupuesto mensual, registrar gastos con validación de fechas y montos, ver su saldo después de cada gasto y recibir alertas cuando se acerca o supera el límite del mes.

### 4.2 Sprint 2 (semana 2): metas académicas y tareas

**Estado:** pendiente.
**Historias:** HU05, HU06, HU07 y HU08. Total: 11 puntos.
**Objetivo:** el estudiante puede crear metas, agregar tareas, marcarlas como completadas y ver las tareas vencidas.

### 4.3 Sprint 3 (semana 3): reportes y gráficos

**Estado:** pendiente.
**Historias:** HU09. Total: 5 puntos.
**Objetivo:** el estudiante genera un reporte mensual con total gastado, metas cumplidas y gráfico por categoría.

### 4.4 Sprint 4 (semana 4): actividades, recordatorios e integración

**Estado:** pendiente.
**Historias:** HU10, HU11 y HU12. Total: 10 puntos.
**Objetivo:** el estudiante organiza sus actividades con recordatorios, y el programa integra todas las funciones.

## 5. Entregables semanales

| Semana | Entregable | Estado |
| :--- | :--- | :--- |
| 1 | Script de gastos y presupuestos, con sus evidencias | Terminado |
| 2 | Script de metas, tareas y vencimientos, con sus evidencias | Pendiente |
| 3 | Script de reportes mensuales, con sus evidencias | Pendiente |
| 4 | Versión final integrada, con sus evidencias | Pendiente |

## 6. Evidencias de avance

Cada semana se entrega una carpeta o un enlace con:
- **Script:** el archivo ejecutable de la semana, o el zip del proyecto.
- **Captura de ejecución:** pantalla de la consola con un caso de uso completo (por ejemplo, registrar un gasto y ver la alerta).
- **Captura del meet:** captura de la reunión de avance del equipo en la que se presentó el script.
- **Pruebas realizadas:** lista de los casos probados, incluidos los casos de error.
- **Repositorio:** enlace al commit de la semana en GitHub.
- **Problemas encontrados:** qué falló y cómo se resolvió.
- **Actualización del backlog:** historias terminadas y pendientes.

### Evidencias registradas

**Semana 1**

Captura del primer meet (reunión del 7 de noviembre de 2026):

<img width="1917" height="972" alt="Captura de pantalla 2026-11-07 201911" src="https://github.com/user-attachments/assets/97182942-e432-4c17-abb8-80577186be23" />

Captura del segundo meet (reunión del 8 de noviembre de 2026):

<img width="1919" height="978" alt="Captura de pantalla 2026-11-08 205808" src="https://github.com/user-attachments/assets/95dc6e44-a5a3-47f0-8063-a8b8a641aa62" />

Captura del UML del equipo:

![Diagrama de clases](img/Diagrama%20de%20clases%20UnsaLife.jpg)

**Semanas 2, 3 y 4:** pendientes.

## 7. Arquitectura orientada a objetos

### 7.1 Clases principales

| Clase | Responsabilidad |
| :--- | :--- |
| `Estudiante` | Centro del sistema; guarda presupuestos, metas, actividades y reportes. |
| `Presupuesto` | Límite mensual y lista de gastos (composición). |
| `Gasto` | Monto, categoría, fecha y descripción. |
| `Fecha` | Día, mes y año, con validación de fechas existentes. |
| `MetaAcademica` | Meta con materia, fecha límite, progreso y lista de tareas (composición). |
| `ElementoPlanificable` | Superclase abstracta de `Actividad` y `Tarea`, con id, título, descripción, estado y recordatorios. |
| `Actividad` | Elemento con fecha y hora (herencia). |
| `Tarea` | Elemento con fecha límite (herencia). |
| `Recordatorio` | Alarma asociada a un elemento (composición). |
| `ReporteMensual` | Resumen del mes con gráfico por categoría. |

### 7.2 Conceptos de POO aplicados
- **Encapsulamiento:** atributos privados y métodos públicos.
- **Herencia:** `Actividad` y `Tarea` heredan de `ElementoPlanificable`.
- **Polimorfismo:** una lista de elementos planificables que trata actividades y tareas de la misma forma.
- **Composición:** un presupuesto es dueño de sus gastos, y una meta de sus tareas.
- **Colecciones:** `ArrayList` para las listas y `HashMap` para los gastos por categoría.

## 8. Definition of Done

Una historia se considera terminada cuando:
- El código está implementado con programación orientada a objetos.
- Está integrada con el resto del programa.
- Se probó el caso correcto y los casos de error.
- No tiene errores críticos conocidos.
- El código está actualizado en GitHub.
- El Product Owner acepta la historia.

## 9. Resultado esperado y hitos

Al final de la semana 4, el estudiante puede controlar su presupuesto, registrar gastos, administrar metas y tareas, ver reportes mensuales y organizar actividades con recordatorios, todo en una aplicación integrada.

| Semana | Hito | Estado |
| :--- | :--- | :--- |
| 1 | Presupuestos y gastos funcionando | Terminado |
| 2 | Metas y tareas funcionando | Pendiente |
| 3 | Reportes funcionando | Pendiente |
| 4 | Aplicación integrada y probada | Pendiente |
