# Sesión 10 - Herencia Múltiple, Interfaces y Arquitectura en Capas

**Fecha:** 3 de marzo de 2026

## Contenidos de la Sesión

En esta sesión hemos abordado el **problema de la herencia múltiple** en Java y cómo resolverlo usando **interfaces**. También hemos introducido el **patrón Controller** como orquestador de la aplicación y el concepto de **inyección de dependencias**.

---

### 1. El Problema de la Herencia Múltiple

Java **no permite** que una clase herede de más de una clase al mismo tiempo. Este es el llamado _problema del diamante_: si `Pajaro extends Animal` y quisiéramos también `Pajaro extends Volador` (siendo `Volador` una clase), Java lo rechazaría.

```
           ┌──────────────┐        ┌───────────────┐
           │    Animal    │        │    Volador    │   ← Dos superclases
           └──────┬───────┘        └──────┬────────┘
                  │                       │
                  └──────────┬────────────┘
                             │
                          ┌──┴────┐
                          │ Pajaro │  ❌ Java no permite esto
                          └───────┘
```

**Solución:** convertir `Volador` en una **interfaz**. Una clase puede implementar múltiples interfaces aunque solo pueda extender una clase.

```
           ┌──────────────┐        ┌──────────────────┐
           │    Animal    │        │  «interface»     │
           │  (abstract)  │        │    Volador       │
           └──────┬───────┘        │  + volar(): void │
                  │ extends        └────────┬─────────┘
                  │                         │ implements
                  └─────────────┬───────────┘
                                 │
                             ┌───┴────┐
                             │ Pajaro │  ✅ Extiende Animal e implementa Volador
                             └────────┘
```

---

### 2. Clase Abstracta `Animal` y sus Subclases

#### 2.1. Definición de `Animal`

`Animal` es una clase abstracta con un **método abstracto** `habla()`. Esto obliga a todas sus subclases a proporcionar su propia implementación del sonido que hace ese animal.

```java
public abstract class Animal {
    private String nombre;
    private int edad;

    public Animal(String nombre, int edad) {
        this.nombre = nombre;
        this.edad = edad;
    }

    public String getNombre() { return nombre; }
    public int getEdad()      { return edad; }

    @Override
    public String toString() {
        return nombre + " (" + edad + " años)";
    }

    // Método abstracto: OBLIGA a las subclases a implementarlo
    public abstract void habla();
}
```

> 💡 Al declarar `Animal` como `abstract`, impedimos que alguien cree un `new Animal(...)` directamente. Solo se pueden instanciar las subclases concretas.

#### 2.2. Subclases de `Animal`

Las cuatro subclases son `Perro`, `Gato`, `Pajaro` y `Mosca`. Cada una extiende `Animal` y proporciona su propia implementación de `habla()`.

```java
public class Perro extends Animal {
    public Perro(String nombre, int edad) {
        super(nombre, edad);
    }

    @Override
    public void habla() {
        System.out.println("¡Guau guau!");
    }
}

public class Gato extends Animal {
    public Gato(String nombre, int edad) {
        super(nombre, edad);
    }

    @Override
    public void habla() {
        System.out.println("¡Miau miau!");
    }
}

public class Pajaro extends Animal implements Volador {
    public Pajaro(String nombre, int edad) {
        super(nombre, edad);
    }

    @Override
    public void habla() {
        System.out.println("¡Pío pío!");
    }

    @Override
    public void volar() {
        System.out.println(getNombre() + " vuela batiendo sus alas.");
    }
}

public class Mosca extends Animal implements Volador {
    public Mosca(String nombre, int edad) {
        super(nombre, edad);
    }

    @Override
    public void habla() {
        System.out.println("¡Bzzz bzzz!");
    }

    @Override
    public void volar() {
        System.out.println(getNombre() + " zumba volando.");
    }
}
```

**Diagrama UML:**

```
             ┌─────────────────────────┐
             │       «abstract»        │
             │          Animal         │
             │  - nombre: String       │
             │  - edad: int            │
             │  + getNombre(): String  │
             │  + getEdad(): int       │
             │  + toString(): String   │
             │  + habla(): void {abs}  │
             └────────────┬────────────┘
                          │
         ┌────────────────┼────────────────┐
         │                │                │
    ┌────┴───┐       ┌────┴───┐       ┌────┴──────┐
    │ Perro  │       │  Gato  │       │  Mosca    │
    │+habla()│       │+habla()│       │ +habla()  │
    └────────┘       └────────┘       │ +volar()  │
                                      └───────────┘
                                      ┌───────────┐
                                      │  Pajaro   │
                                      │ +habla()  │
                                      │ +volar()  │
                                      └───────────┘
```

---

### 3. Clase Abstracta `Vehiculo` y sus Subclases

#### 3.1. Definición de `Vehiculo`

`Vehiculo` es otra clase abstracta con **dos métodos abstractos**: `encender()` y `apagar()`. Cada tipo de vehículo arranca y se detiene de manera diferente.

```java
public abstract class Vehiculo {
    private String marca;
    private int anio;

    public Vehiculo(String marca, int anio) {
        this.marca = marca;
        this.anio = anio;
    }

    public String getMarca() { return marca; }
    public int getAnio()     { return anio; }

    @Override
    public String toString() {
        return marca + " (" + anio + ")";
    }

    // Métodos abstractos: cada subclase define cómo encender y apagar
    public abstract void encender();
    public abstract void apagar();
}
```

#### 3.2. Subclases de `Vehiculo`

Las cuatro subclases son `Coche`, `Moto`, `Helicoptero` y `Avioneta`. Las dos últimas también implementan la interfaz `Volador`.

```java
public class Coche extends Vehiculo {
    public Coche(String marca, int anio) {
        super(marca, anio);
    }

    @Override
    public void encender() {
        System.out.println(getMarca() + ": girando la llave de contacto... ¡en marcha!");
    }

    @Override
    public void apagar() {
        System.out.println(getMarca() + ": apagando motor.");
    }
}

public class Moto extends Vehiculo {
    public Moto(String marca, int anio) {
        super(marca, anio);
    }

    @Override
    public void encender() {
        System.out.println(getMarca() + ": kick start... ¡vroooom!");
    }

    @Override
    public void apagar() {
        System.out.println(getMarca() + ": cortando gas, apagada.");
    }
}

public class Helicoptero extends Vehiculo implements Volador {
    public Helicoptero(String marca, int anio) {
        super(marca, anio);
    }

    @Override
    public void encender() {
        System.out.println(getMarca() + ": iniciando turbina del helicóptero...");
    }

    @Override
    public void apagar() {
        System.out.println(getMarca() + ": deteniendo rotores.");
    }

    @Override
    public void volar() {
        System.out.println(getMarca() + " asciende girando sus rotores.");
    }
}

public class Avioneta extends Vehiculo implements Volador {
    public Avioneta(String marca, int anio) {
        super(marca, anio);
    }

    @Override
    public void encender() {
        System.out.println(getMarca() + ": comprobando instrumentos, encendiendo motor...");
    }

    @Override
    public void apagar() {
        System.out.println(getMarca() + ": freno de estacionamiento, apagado.");
    }

    @Override
    public void volar() {
        System.out.println(getMarca() + " despega por la pista.");
    }
}
```

**Diagrama UML:**

```
             ┌────────────────────────────┐
             │         «abstract»         │
             │           Vehiculo         │
             │  - marca: String           │
             │  - anio: int               │
             │  + getMarca(): String      │
             │  + getAnio(): int          │
             │  + toString(): String      │
             │  + encender(): void {abs}  │
             │  + apagar(): void {abs}    │
             └─────────────┬──────────────┘
                           │
          ┌────────────────┼──────────────────┐
          │                │                  │
     ┌────┴────┐      ┌────┴────┐       ┌─────┴──────┐
     │  Coche  │      │  Moto   │       │ Helicoptero│
     │+encender│      │+encender│       │ +encender()│
     │+apagar()│      │+apagar()│       │ +apagar()  │
     └─────────┘      └─────────┘       │ +volar()   │
                                        └────────────┘
                                        ┌────────────┐
                                        │  Avioneta  │
                                        │ +encender()│
                                        │ +apagar()  │
                                        │ +volar()   │
                                        └────────────┘
```

---

### 4. La Interfaz `Volador` — Solución a la Herencia Múltiple

#### 4.1. Por qué usar una interfaz en lugar de una clase abstracta

Si `Volador` fuese una **clase abstracta**, `Pajaro` no podría extenderla porque ya extiende `Animal`. Con una **interfaz**, el problema desaparece: Java permite implementar múltiples interfaces.

```java
// ❌ Si Volador fuera clase abstracta:
public class Pajaro extends Animal extends Volador { }  // ERROR de compilación

// ✅ Volador como interfaz:
public class Pajaro extends Animal implements Volador { }  // OK
```

#### 4.2. Definición de la interfaz `Volador`

```java
public interface Volador {
    void volar();
}
```

> 💡 Solo define el **contrato** (qué se debe poder hacer), no la implementación (cómo).

#### 4.3. Clases que implementan `Volador`

| Clase         | Jerarquía base  | ¿Implementa `Volador`? |
|---------------|-----------------|------------------------|
| `Perro`       | `Animal`        | ❌ No                  |
| `Gato`        | `Animal`        | ❌ No                  |
| `Pajaro`      | `Animal`        | ✅ Sí                  |
| `Mosca`       | `Animal`        | ✅ Sí                  |
| `Coche`       | `Vehiculo`      | ❌ No                  |
| `Moto`        | `Vehiculo`      | ❌ No                  |
| `Helicoptero` | `Vehiculo`      | ✅ Sí                  |
| `Avioneta`    | `Vehiculo`      | ✅ Sí                  |

#### 4.4. Polimorfismo a través de la interfaz

Gracias a que `Volador` es una interfaz, podemos agrupar en una misma colección objetos de jerarquías completamente distintas (`Animal` y `Vehiculo`):

```java
ArrayList<Volador> voladores = new ArrayList<>();
voladores.add(new Pajaro("Tweety", 2));
voladores.add(new Mosca("Buzz", 1));
voladores.add(new Helicoptero("Bell", 2020));
voladores.add(new Avioneta("Cessna", 2018));

for (Volador v : voladores) {
    v.volar();   // ✅ Cada uno vuela a su manera
}
```

**Salida esperada:**

```
Tweety vuela batiendo sus alas.
Buzz zumba volando.
Bell asciende girando sus rotores.
Cessna despega por la pista.
```

**Diagrama UML completo:**

```
  ┌────────────────────┐             ┌──────────────────────┐
  │     «abstract»     │             │      «abstract»      │
  │       Animal       │             │       Vehiculo       │
  └────────┬───────────┘             └──────────┬───────────┘
           │                                    │
    ┌──────┼──────────┐               ┌──────────┼──────────┐
    │      │          │               │          │          │
┌───┴──┐ ┌─┴──┐  ┌───┴──────┐   ┌───┴───┐ ┌────┴───┐ ┌────┴──────┐
│Perro │ │Gato│  │  Pajaro  │   │ Coche │ │  Moto  │ │Helicoptero│
└──────┘ └────┘  │ +volar() │   └───────┘ └────────┘ │ +volar()  │
                 └────┬─────┘                         └─────┬─────┘
                      │                                     │
              ┌───────┴────┐                       ┌────────┴───┐
              │   Mosca    │                       │  Avioneta  │
              │  +volar()  │                       │  +volar()  │
              └───────┬────┘                       └────────┬───┘
                      │          «interface»                │
                      └──────────► Volador ◄────────────────┘
                                 + volar(): void
```

---

### 5. El Patrón Controller — Orquestador de la Aplicación

#### 5.1. El problema: `Main` sobrecargado

En versiones anteriores, el método `main` contenía toda la lógica: creación de objetos, flujo de menú, cálculos, etc. Esto hace el código difícil de mantener y de probar.

```java
// ❌ Main con demasiada lógica
public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ArrayList<Medida> medidas = new ArrayList<>();
        // ... decenas de líneas de lógica mezclada ...
    }
}
```

#### 5.2. La solución: delegar en un `Controller`

El `Controller` actúa como **orquestador**: recibe las dependencias ya creadas (inyectadas) y coordina el flujo de la aplicación. El `Main` queda reducido a:

1. Crear las dependencias (menú, DAO)
2. Inyectarlas en el `Controller`
3. Llamar a `controller.run()`

```java
// ✅ Main limpio: solo wiring + arranque
public class Main {
    public static void main(String[] args) {
        Menu menu = new MenuConsole();
        DAOMedida dao = new DAOMedidaCSVFile("fichero.csv");

        Controller controller = new Controller(menu, dao);
        controller.run();
    }
}
```

```java
// ✅ Controller: orquesta todo sin conocer Scanner ni ficheros
public class Controller {
    Menu menu;
    DAOMedida dao;
    ArrayList<Medida> medidas;

    public Controller(Menu menu, DAOMedida dao) {
        this.menu = menu;
        this.dao = dao;
    }

    public void run() {
        medidas = dao.load();   // carga datos vía DAO
        // ... lógica de menú usando menu.show() y menu.getInteger() ...
    }
}
```

**Ventajas del patrón Controller:**

| Sin Controller | Con Controller |
|----------------|----------------|
| `Main` mezcla lógica, I/O y persistencia | Responsabilidades claramente separadas |
| Difícil de testear | `Controller` es testeable inyectando mocks |
| Cambiar la fuente de datos rompe `Main` | Solo cambia la clase concreta inyectada en `Main` |

---

### 6. Inyección de Dependencias

#### 6.1. ¿Qué es?

La **inyección de dependencias** (_Dependency Injection_, DI) es el principio de proporcionar a una clase sus dependencias desde el exterior, en lugar de que la propia clase las cree.

```java
// ❌ Sin inyección: Controller crea sus propias dependencias (acoplado)
public class Controller {
    public Controller() {
        this.menu = new MenuConsole();         // acoplado a MenuConsole
        this.dao = new DAOMedidaCSVFile("x");  // acoplado al CSV
    }
}

// ✅ Con inyección: las dependencias llegan desde fuera
public class Controller {
    public Controller(Menu menu, DAOMedida dao) {
        this.menu = menu;   // solo conoce la interfaz Menu
        this.dao = dao;     // solo conoce la interfaz DAOMedida
    }
}
```

#### 6.2. Controller acoplado solo a interfaces

El `Controller` declara sus dependencias usando los **tipos de interfaz**, nunca las clases concretas:

```java
public class Controller {
    Menu menu;        // ← interfaz, no MenuConsole
    DAOMedida dao;    // ← interfaz, no DAOMedidaCSVFile
    // ...
}
```

Esto significa que podemos cambiar la implementación (p. ej., `MenuConsole` → `MenuConsoleSuperior`, o CSV → JSON) sin tocar ni una línea del `Controller`.

#### 6.3. Diagrama de dependencias

```
                    ┌──────────┐
                    │   Main   │
                    └────┬─────┘
                         │ crea y conecta
           ┌─────────────┼──────────────┐
           │             │              │
    ┌──────┴──────┐ ┌────┴────┐  ┌──────┴──────────┐
    │ MenuConsole │ │Controller│  │DAOMedidaCSVFile │
    │(implementa) │ └────┬────┘  │(implementa)     │
    └─────────────┘      │       └─────────────────┘
                         │ depende de (interfaces)
                    ┌────┴────────┐
                    │  Menu       │ «interface»
                    │  DAOMedida  │ «interface»
                    └─────────────┘
```

---

### 7. Código Base de la Solución Parcial

📁 **Código fuente:** [`partial-solution-sensors-biometrics/`](partial-solution-sensors-biometrics/)

El directorio `partial-solution-sensors-biometrics/src/` contiene el punto de partida para la actividad:

| Clase / Interfaz        | Tipo       | Descripción                                          |
|-------------------------|------------|------------------------------------------------------|
| `Main`                  | Clase      | Wiring + arranque. Solo crea y conecta dependencias  |
| `Controller`            | Clase      | Orquestador. Actualmente con código de prueba        |
| `Medida`                | Clase      | Modelo de datos: id, tipo, valor, unidad             |
| `Menu`                  | Interfaz   | Contrato de I/O: `show`, `getString`, `getInteger`   |
| `MenuConsole`           | Clase      | Implementación básica con `System.in`/`System.out`   |
| `MenuConsoleSuperior`   | Clase      | Implementación decorada (rodea mensajes con `*`)     |
| `DAOMedida`             | Interfaz   | Contrato de persistencia: `load()` y `save(...)`     |
| `DAOMedidaCSVFile`      | Clase      | Implementación CSV (métodos pendientes de completar) |

---

### 8. Actividad — AC2: Sensors Biométrics Refactorizado

📋 **Enunciado completo:** [`ACTIVIDAD.md`](ACTIVIDAD.md)

La actividad consiste en completar el código base de [`partial-solution-sensors-biometrics/`](partial-solution-sensors-biometrics/) aplicando la arquitectura en capas vista en esta sesión:

- **Main**: solo wiring + `controller.run()`
- **Controller**: carga con `dao.load()`, gestiona opciones de menú, guarda con `dao.save(...)`
- **DAOMedidaCSVFile**: implementar `load()` (parsear CSV) y `save(...)` (escribir CSV)
- **Menu / MenuConsole / MenuConsoleSuperior**: I/O por consola

> ⚠️ No se resuelve en estos apuntes. El enunciado completo y las restricciones de diseño se encuentran en [`ACTIVIDAD.md`](ACTIVIDAD.md).

---

### 9. Resumen de Conceptos

| Concepto                         | Descripción                                                                                   | Ejemplo                                              |
|----------------------------------|-----------------------------------------------------------------------------------------------|------------------------------------------------------|
| **Herencia múltiple**            | Java no permite extender más de una clase                                                     | `Pajaro extends Animal` (solo una clase padre)       |
| **Interfaz como solución**       | Permite que una clase «sea» varios tipos sin herencia múltiple                                | `Pajaro extends Animal implements Volador`           |
| **Clase abstracta**              | No instanciable; define métodos abstractos que obligan a implementarse en las subclases       | `abstract class Animal`                              |
| **Método abstracto**             | Sin cuerpo en la superclase; cada subclase lo implementa                                      | `public abstract void habla()`                       |
| **`implements` múltiple**        | Una clase puede implementar varias interfaces a la vez                                        | `class X implements A, B, C`                         |
| **Polimorfismo de interfaz**     | Variable de tipo interfaz apunta a objetos de distintas jerarquías                            | `Volador v = new Pajaro(...)`                        |
| **Patrón Controller**            | Clase que orquesta la lógica de aplicación; el `Main` solo la instancia y arranca             | `new Controller(menu, dao).run()`                    |
| **Inyección de dependencias**    | Las dependencias se pasan desde fuera; la clase solo conoce las interfaces                    | `Controller(Menu menu, DAOMedida dao)`               |
| **Bajo acoplamiento**            | El `Controller` no sabe qué implementación concreta de `Menu` o `DAO` recibe                 | `this.menu = menu` (tipo interfaz)                   |
| **Alta cohesión**                | Cada clase tiene una única responsabilidad bien definida                                      | `DAOMedidaCSVFile` solo lee/escribe CSV              |

---

## Recursos Adicionales

- [Interfaces en Java — Oracle Docs](https://docs.oracle.com/javase/tutorial/java/IandI/createinterface.html)
- [Clases Abstractas — Oracle Docs](https://docs.oracle.com/javase/tutorial/java/IandI/abstract.html)
- [Herencia en Java — Oracle Docs](https://docs.oracle.com/javase/tutorial/java/IandI/subclasses.html)
- [El problema del diamante — Wikipedia](https://es.wikipedia.org/wiki/Problema_del_diamante)
- [Principio de inversión de dependencias (DIP) — Wikipedia](https://es.wikipedia.org/wiki/Principio_de_inversi%C3%B3n_de_la_dependencia)
- [Inyección de dependencias — Wikipedia](https://es.wikipedia.org/wiki/Inyecci%C3%B3n_de_dependencias)
- [Patrón DAO en Java — Baeldung](https://www.baeldung.com/java-dao-pattern)
- [Interfaces vs Clases Abstractas — Baeldung](https://www.baeldung.com/java-interface-vs-abstract-class)
- [Principio Abierto/Cerrado (OCP) — Wikipedia](https://es.wikipedia.org/wiki/Principio_de_abierto/cerrado)
