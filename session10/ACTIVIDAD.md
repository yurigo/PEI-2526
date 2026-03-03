# AC2 – Refactor a arquitectura en capas (Layered Architecture)

## Sensors biomètrics – versión con Controller + DAO + Vista

---

## Contexto

En la **AC1** se implementó una aplicación de consola para gestionar medidas de sensores biométricos (p. ej. temperatura, pulso, SpO₂) a partir de un fichero CSV.

En esta actividad **no buscamos añadir nuevas features**, sino **mejorar el diseño**: separar responsabilidades, reducir acoplamiento y hacer que el sistema sea más mantenible.

Para facilitar el trabajo, se entrega el ejercicio **parcialmente montado** (código base). Tu tarea es **terminar la arquitectura** y **completar lo que falta**.

---

## Objetivo

Partiendo del código base entregado, refactoriza la solución para que tenga una estructura por capas “decente”:

- **Main** (arranque + wiring)
- **Controller** (lógica de aplicación)
- **DAO** (persistencia CSV)
- **Vista/Menu** (entrada/salida por consola)
- **Model** (`Medida`)

---

## Código base (clases incluidas)

El proyecto ya contiene estas clases (con parte del código hecho):

- `Main`
- `Controller`
- `Medida`
- `Menu` (interfaz)
- `MenuConsole` (implementación)
- `MenuConsoleSuperior` (implementación)
- `DAOMedida` (interfaz)
- `DAOMedidaCSVFile` (implementación)

> Importante: la interfaz `DAOMedida` **solo tiene dos métodos**:  
> `load()` y `save(ArrayList<Medida> medidas)`.  
> **No debes inventarte un CRUD completo** ni añadir métodos extra a la interfaz, salvo que el enunciado (o tu profesor) lo indique expresamente.

---

## Arquitectura y responsabilidades

### 1) Model – `Medida`

Debe representar una medida biométrica (id, tipo, valor, unidad, instante si lo tienes).

✅ Puede contener:

- validaciones básicas
- formateo (`toString` / helpers)
- lógica propia del objeto si es _intrínseca_ (p. ej. representar su valor)

❌ No puede:

- leer/escribir ficheros
- pedir datos al usuario
- imprimir por consola

---

### 2) DAO – Persistencia CSV

#### `DAOMedida`

Interfaz mínima (ya creada):

- `ArrayList<Medida> load()` → carga todas las medidas desde el CSV
- `void save(ArrayList<Medida> medidas)` → guarda **todas** las medidas en el CSV (sobrescribiendo o reconstruyendo el fichero)

#### `DAOMedidaCSVFile`

Implementación concreta que trabaja con un fichero CSV.

✅ Debe:

- leer el fichero indicado por `filePath`
- ignorar líneas de comentario (por ejemplo, las que empiezan por `#`)
- parsear CSV a objetos `Medida`
- persistir el estado actual del sistema usando `save(medidas)`

❌ Ninguna otra clase puede acceder al fichero directamente.

---

### 3) Controller – `Controller`

Es el “cerebro” de la app.

✅ Debe:

- cargar medidas usando `dao.load()` al arrancar
- gestionar el flujo de la aplicación (opciones de menú)
- ejecutar cálculos (resumen por tipo, ASCII, media/último valor…)
- validar antes de guardar (si aplica)
- persistir cambios con `dao.save(medidas)` cuando el usuario añade una medida

❌ No debe:

- leer del teclado directamente (eso es de la vista)
- escribir por consola directamente (eso es de la vista)
- conocer detalles del CSV

---

### 4) Vista – Menús (`Menu`, `MenuConsole`, `MenuConsoleSuperior`)

#### `Menu`

Interfaz que encapsula el I/O de consola.

✅ Debe:

- mostrar mensajes (`show`)
- pedir datos (`getString`, `getInteger`, etc.)

#### `MenuConsole` / `MenuConsoleSuperior`

Implementaciones para consola.

✅ `MenuConsoleSuperior` debe aportar un pequeño “estilo” visual (marco, título, etc.) reutilizando el comportamiento de consola.

---

### 5) Main

✅ Debe limitarse a:

- crear `Menu`
- crear `DAOMedidaCSVFile(filePath)`
- inyectarlos en el `Controller`
- ejecutar `controller.run()`

❌ No debe contener lógica de negocio.

---

## Funcionalidad requerida (igual que AC1)

La aplicación debe ofrecer un menú similar a:

1. Listar medidas
2. Representación ASCII (resumen por tipo)
3. Añadir medida
4. Buscar por tipo
5. Salir

> **Nota:** la lógica de “representación ASCII” y sus estrategias deben vivir en el **Controller**, no en el menú.

---

## Restricciones de diseño (obligatorias)

- **Nada de lógica de negocio en `Main`**
- **Nada de lectura/escritura del CSV fuera del DAO**
- Evitar acoplamiento `Controller -> Scanner` (el Controller solo conoce `Menu`)
- Evitar `System.out.println` fuera de la vista
- No usar `static` para “arreglar” dependencias
- El sistema debe compilar y ejecutarse con normalidad

---

## Qué falta por implementar (pistas)

En el código base, como mínimo, falta:

- `DAOMedidaCSVFile.load()`
- `DAOMedidaCSVFile.save(...)`
- El menú real (mostrar opciones, pedir opción, repetir hasta salir)
- En `Controller`, sustituir el “test” y los objetos hardcodeados por:
  - `medidas = dao.load()` al inicio
  - acciones reales por opción
  - `dao.save(medidas)` cuando se añada una medida

---

## Entrega

Entrega un ZIP con:

1. Proyecto IntelliJ completo.
2. CSV utilizado (con datos iniciales y nuevos)
3. UML actualizado (mínimo: las 8 clases indicadas)
4. README con:
   - decisiones de arquitectura
   - cómo has aplicado low coupling / high cohesion
   - cómo se usa tu programa (opciones y ejemplos)

---

## Preguntas obligatorias (README)

Responde brevemente:

1. ¿Qué responsabilidad tiene el `Controller` que antes estaba “mezclada” en el `Main`?
2. ¿Qué acoplamientos has reducido gracias al `DAO`?
3. Si mañana cambiamos CSV por JSON, ¿qué clases tocarías y cuáles no?
4. ¿Por qué `DAOMedida` solo tiene `load` y `save`? ¿Qué ventajas/inconvenientes tiene ese diseño?

---
