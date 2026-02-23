# Sesión 07 - Herencia y Polimorfismo

**Fecha:** 23 de febrero de 2026

## Contenidos de la Sesión

En esta sesión hemos repasado las relaciones entre clases e introducido los conceptos de **herencia**, **casting**, **instanceof**, **polimorfismo**, **sobrecarga de métodos** y **clases abstractas** en Java.

---

### 1. Repaso de las Relaciones entre Clases

Antes de entrar en los nuevos conceptos, repasamos brevemente los cuatro tipos de relaciones entre clases que vimos en la sesión anterior:

| Relación     | Notación UML | Descripción                                              |
| ------------ | ------------ | -------------------------------------------------------- |
| Dependencia  | `- - - >`    | Uso temporal de una clase como parámetro o variable local |
| Asociación   | `──`         | Relación estructural duradera (atributo)                  |
| Agregación   | `◇──`        | "Tiene-un": las partes existen independientemente         |
| Composición  | `◆──`        | "Parte-de": las partes no existen sin el todo             |

---

### 2. Agregación: Playlist - Song

📁 **Código fuente:** [`aggregation example/`](aggregation%20example/)

En el ejemplo de **agregación**, una `Playlist` contiene canciones (`Song`), pero las canciones pueden existir independientemente de la playlist. Cuando se asigna `null` a la playlist, el Garbage Collector (GC) la elimina, pero los objetos `Song` permanecen vivos en memoria porque hay otras referencias a ellos.

```
┌──────────────┐         ┌──────────────┐
│   Playlist   │◇────────│     Song     │
└──────────────┘  1   *  └──────────────┘
```

**Clases principales:**

```java
public class Song {
    String id;
    String name;
    Long duration;  // en segundos
    Artist artist;

    public Song(String name, Long duration) {
        this.name = name;
        this.duration = duration;
    }
}

public class Playlist {
    String id;
    String name;
    Long duration;
    ArrayList<Song> songs;

    public Playlist(String name) {
        this.name = name;
        this.songs = new ArrayList<Song>();
    }

    public void addSong(Song song) {
        this.songs.add(song);
    }
}
```

**Demostración del ciclo de vida:**

```java
Song s1 = new Song("Diamond", (long)(3 * 60));
Song s2 = new Song("Flashdance", (long)(4 * 60));
Song s3 = new Song("Thriller", (long)(15 * 60));

Playlist p1 = new Playlist("Mi Playlist");
p1.addSong(s1);
p1.addSong(s2);
p1.addSong(s3);

p1 = null;  // La playlist desaparece...

// ✅ Las canciones SIGUEN VIVAS: s1, s2, s3 siguen siendo accesibles
System.out.println(s1.name);  // "Diamond" - sigue existiendo
```

**Puntos clave de la Agregación:**
- ✅ Las canciones se crean **fuera** de la playlist
- ✅ `p1 = null` elimina la playlist, pero **no** las canciones
- ✅ Las canciones pueden pertenecer a múltiples playlists
- ✅ El GC no puede recoger `s1`, `s2`, `s3` mientras haya referencias a ellas

---

### 3. Composición: Chessboard - Cell

📁 **Código fuente:** [`composition example/`](composition%20example/)

En el ejemplo de **composición**, un `Chessboard` está compuesto por 64 `Cell`s. Las celdas se crean **dentro** del constructor del tablero y no tienen sentido fuera de él. Cuando se asigna `null` al tablero, el GC puede recoger tanto el tablero como todas sus celdas.

```
┌──────────────┐         ┌──────────────┐
│  Chessboard  │◆────────│     Cell     │
└──────────────┘  1  64  └──────────────┘
```

**Clases principales:**

```java
public class Cell {
    String position;
    String color;
    Piece piece;

    public Cell(String position, String color) {
        this.position = position;
        this.color = color;
    }
}

public class Chessboard {
    private ArrayList<Cell> cells;

    public Chessboard() {
        this.cells = new ArrayList<Cell>();

        // Las celdas se crean DENTRO del constructor
        for (int i = 0; i < 64; i++) {
            this.cells.add(new Cell(String.valueOf(i), i % 2 == 0 ? "white" : "black"));
        }
    }
}
```

**Demostración del ciclo de vida:**

```java
Chessboard c = new Chessboard();  // Se crean el tablero y las 64 celdas

c = null;  // ❌ El tablero y las 64 celdas quedan disponibles para el GC
// Las celdas NO tienen referencias externas: el GC las puede borrar
```

**Diferencia clave entre Agregación y Composición:**

| Aspecto                      | Agregación (Playlist)   | Composición (Chessboard) |
| ---------------------------- | ----------------------- | ------------------------ |
| ¿Dónde se crean las partes?  | Fuera del contenedor    | Dentro del contenedor    |
| ¿Existen independientemente? | ✅ Sí                   | ❌ No                    |
| ¿GC las borra con el todo?   | ❌ No (hay referencias) | ✅ Sí (si no hay refs)   |

---

### 4. Herencia

📁 **Código fuente:** [`zoo example/`](zoo%20example/)

La **herencia** permite crear nuevas clases a partir de clases existentes, reutilizando y extendiendo su funcionalidad. La clase original se llama **superclase** (o clase padre/base) y la nueva clase se llama **subclase** (o clase hija/derivada).

**Notación UML:**

```
┌──────────────┐
│    Animal    │   ← Superclase
└──────┬───────┘
       │
   ────┼────────────────────────────
   │   │          │        │       │
┌──┴──┐ ┌────┐ ┌──────┐ ┌──────┐ ┌───────┐
│ Dog │ │Cat │ │ Bird │ │ Fish │ │Capyb. │  ← Subclases
└─────┘ └────┘ └──────┘ └──────┘ └───────┘
```

#### 4.1. Superclase: Animal

```java
public class Animal {
    private String name;
    private String type;
    private int age;

    public Animal(String name, String type, int age) {
        this.name = name;
        this.type = type;
        this.age = age;
    }

    public void showInformation() {
        System.out.println("Name: " + name);
        System.out.println("Type: " + type);
        System.out.println("Age: " + age);
    }

    public String toString() {
        return "el animal " + name + " " + type + " " + age;
    }
}
```

#### 4.2. Subclases: Dog, Cat, Bird, Fish

Las subclases **heredan** todos los atributos y métodos de `Animal` y pueden **añadir** o **sobrescribir** comportamiento:

```java
public class Dog extends Animal {
    public Dog(String name, String type, int age) {
        super(name, type, age);  // Llamada al constructor de la superclase
    }

    public void talk() {
        System.out.println("Woof woof");
    }
}

public class Cat extends Animal {
    public Cat(String name, String type, int age) {
        super(name, type, age);
    }

    public void talk() {
        System.out.println("meow meow meow meow");
    }
}
```

**Uso básico:**

```java
Cat c = new Cat("Alice", "Persian", 9000);
Dog d = new Dog("Bob", "Corgi", 300);
Bird b = new Bird("Charlie", "Parakeet", 3);
Fish f = new Fish("Dave", "Clown", 314159265);

c.showInformation();  // Método heredado de Animal
d.talk();             // Método propio de Dog: "Woof woof"
```

**Ventajas de la herencia:**
- ✅ **Reutilización de código**: los atributos y métodos comunes se definen una sola vez
- ✅ **Extensibilidad**: se pueden añadir nuevas subclases sin modificar la superclase
- ✅ **Organización**: modela relaciones "es-un" del mundo real (un Perro ES UN Animal)

---

### 5. Casting e instanceof

Cuando trabajamos con una colección de objetos de la superclase, a veces necesitamos acceder a métodos específicos de una subclase. Para esto usamos **casting** e **instanceof**.

#### 5.1. El problema

```java
ArrayList<Animal> zoo = new ArrayList<Animal>();
zoo.add(new Cat("Alice", "Persian", 9000));
zoo.add(new Dog("Bob", "Corgi", 300));

// Esto NO compila: Animal no tiene el método bark()
for (Animal a : zoo) {
    a.bark();  // ❌ Error: Animal no tiene bark()
}
```

#### 5.2. Solución con instanceof y casting

```java
for (Animal a : zoo) {
    if (a instanceof Dog) {
        ((Dog) a).bark();  // ✅ Casting: tratamos 'a' como Dog
    }
}
```

**Explicación:**
- `instanceof` verifica si un objeto es de un tipo concreto antes de hacer el casting
- `(Dog) a` convierte la referencia de tipo `Animal` a tipo `Dog`
- Sin `instanceof`, el casting puede lanzar una `ClassCastException` en tiempo de ejecución

> **Nota:** En versiones modernas de Java (16+) se puede usar *pattern matching*: `if (a instanceof Dog dog) { dog.bark(); }`

---

### 6. Polimorfismo

El **polimorfismo** permite tratar objetos de diferentes subclases de manera uniforme a través de una referencia de la superclase. Es una de las características más poderosas de la POO.

#### 6.1. Polimorfismo en acción

En lugar de necesitar casting para cada tipo, si definimos el método `talk()` en la superclase (o como abstracto), podemos llamarlo directamente:

```java
ArrayList<Animal> zoo = new ArrayList<Animal>();
zoo.add(new Cat("Alice", "Persian", 9000));
zoo.add(new Dog("Bob", "Corgi", 300));
zoo.add(new Bird("Charlie", "Parakeet", 3));
zoo.add(new Fish("Dave", "Clown", 314159265));
zoo.add(new Capybara("WWW", "WWW", 0));

for (Animal a : zoo) {
    System.out.println(a.toString());
    a.talk();  // ✅ Cada animal habla a su manera (polimorfismo)
}
```

**Salida:**
```
el animal Alice Persian 9000
meow meow meow meow
el animal Bob Corgi 300
Woof woof
el animal Charlie Parakeet 3
Chirp chirp
el animal Dave Clown 314159265
Glup glup
el animal WWW WWW 0
wqeoiqwpueoir qwpeoiru qpiorqweqwporeiu
```

**Cómo funciona el polimorfismo:**
- La referencia es de tipo `Animal`, pero el objeto real es de un tipo concreto (`Cat`, `Dog`, etc.)
- Java ejecuta el método de la clase **real** del objeto, no de la clase de la referencia
- Esto se llama **enlace dinámico** o *dynamic dispatch*

#### 6.2. Sobrecarga de Métodos (Method Overriding)

La **sobrescritura** (`@Override`) es la base del polimorfismo: cada subclase redefine el mismo método con su propio comportamiento.

```java
// En Animal: declaramos talk() (o lo dejamos como abstracto)
public void talk() {
    System.out.println("...");
}

// ❌ SIN polimorfismo: necesitamos instanceof y casting
for (Animal a : zoo) {
    if (a instanceof Dog) ((Dog) a).bark();
    else if (a instanceof Cat) ((Cat) a).meow();
    // ... un if por cada tipo
}

// ✅ CON polimorfismo: un único método funciona para todos
for (Animal a : zoo) {
    a.talk();  // Cada animal sabe cómo hablar
}
```

---

### 7. Clases Abstractas

Una **clase abstracta** es una clase que **no se puede instanciar directamente**. Sirve como plantilla para sus subclases, forzando que implementen ciertos métodos.

#### 7.1. Definición

```java
public abstract class Animal {
    private String name;
    private String type;
    private int age;

    public Animal(String name, String type, int age) {
        this.name = name;
        this.type = type;
        this.age = age;
    }

    public void showInformation() {
        System.out.println("Name: " + name);
        System.out.println("Type: " + type);
        System.out.println("Age: " + age);
    }

    // Método abstracto: OBLIGA a las subclases a implementarlo
    abstract public void talk();
}
```

#### 7.2. No se puede instanciar

```java
// ❌ Error en compilación: no se puede instanciar una clase abstracta
Animal a = new Animal("Rex", "Dog", 5);

// ✅ Correcto: instanciamos una subclase concreta
Animal a = new Dog("Rex", "Labrador", 5);
Animal b = new Cat("Whiskers", "Siamese", 3);
```

#### 7.3. Método Abstracto

Un **método abstracto** no tiene implementación en la superclase. Cada subclase concreta **está obligada** a implementarlo:

```java
// En Animal (abstracto):
abstract public void talk();

// ❌ Si una subclase NO implementa talk(), Java da error de compilación:
public class Fish extends Animal {
    // Si no escribimos talk(), el compilador da error
}

// ✅ Cada subclase DEBE implementar talk():
public class Fish extends Animal {
    @Override
    public void talk() {
        System.out.println("Glup glup");
    }
}
```

**Ventajas de las clases abstractas:**
- ✅ **Fuerza** a las subclases a implementar los métodos abstractos
- ✅ **Evita** instanciar clases que no tienen sentido por sí solas
- ✅ **Garantiza** que todos los animales tienen un método `talk()`
- ✅ **Combina** código compartido (métodos concretos) con comportamiento forzado (métodos abstractos)

---

### 8. Instrucciones de Ejecución

#### Aggregation Example

```bash
cd "session07/aggregation example"
javac *.java
java Main
```

#### Composition Example

```bash
cd "session07/composition example"
javac *.java
java Main
```

#### Zoo Example

```bash
cd "session07/zoo example"
javac *.java
java Main
```

---

## Ejercicios Prácticos

### DIABLO XII - Con Herencia

📁 **Ubicación:** [`ejercicios/TEXTtoUML/DIABLO XII.md`](ejercicios/TEXTtoUML/DIABLO%20XII.md)

Se repitió el ejercicio de DIABLO XII incorporando **herencia** en el diseño UML. Los personajes del juego comparten atributos comunes en una superclase `Personaje`, y las clases específicas como `Guerrero`, `Mago`, `Arquero` heredan de ella.

**Diseño con herencia:**

```
┌────────────────┐
│   Personaje    │   ← Superclase abstracta
│ - nombre       │
│ - nivel        │
│ - hp           │
│ - inventario   │
│ + atacar()     │   ← Método abstracto
└───────┬────────┘
        │
   ─────┼──────────────────
   │         │           │
┌──────────┐ ┌────────┐ ┌──────────┐
│ Guerrero │ │  Mago  │ │ Arquero  │   ← Subclases
└──────────┘ └────────┘ └──────────┘
```

**Relaciones identificadas con herencia:**
- `Personaje` es superclase abstracta de `Guerrero`, `Mago`, `Arquero`
- `Personaje` **tiene** un `Inventario` → **Composición** (el inventario pertenece al personaje)
- `Inventario` **contiene** `Objeto`s → **Agregación** (los objetos pueden existir independientemente)
- `Personaje` **pertenece** a un `Grupo` → **Asociación**
- `Personaje` **realiza** `Misión`es → **Asociación**

### FARM SIMULATOR - Con Herencia

📁 **Ubicación:** [`ejercicios/TEXTtoUML/FARM SIMULATOR.md`](ejercicios/TEXTtoUML/FARM%20SIMULATOR.md)

Se repitió el ejercicio de FARM SIMULATOR incorporando **herencia**. Los animales y los edificios son buenos candidatos para estructuras jerárquicas.

**Diseño con herencia para los animales:**

```
┌──────────────┐
│    Animal    │   ← Superclase abstracta
│ - nombre     │
│ - edad       │
│ - salud      │
│ + producir() │   ← Método abstracto
└──────┬───────┘
       │
  ─────┼─────────────────
  │    │       │        │
┌──┴─┐ ┌───────┐ ┌──────┐ ┌────────┐
│Vaca│ │Pollo  │ │Oveja │ │Caballo │   ← Subclases
└────┘ └───────┘ └──────┘ └────────┘
```

**Diseño con herencia para los edificios:**

```
┌──────────────┐
│   Edificio   │   ← Superclase
│ - capacidad  │
│ + mejorar()  │
└──────┬───────┘
       │
  ─────┼──────────────────
  │    │        │        │
┌──┴──┐ ┌────────┐ ┌──────┐ ┌─────┐
│Granero│ │Gallinero│ │Establo│ │Casa│
└──────┘ └────────┘ └──────┘ └─────┘
```

**Relaciones identificadas:**
- `Granja` **compuesta por** `Terreno`s, `Edificio`s, `Animal`es → **Composición**
- `Edificio` es superclase de `Granero`, `Gallinero`, `Establo`, `Casa`
- `Animal` es superclase de `Vaca`, `Pollo`, `Oveja`, `Caballo`
- `Terreno` **contiene** `Cultivo`s → **Composición**

---

## Resumen de Conceptos

| Concepto           | Descripción                                               | Ejemplo                          |
| ------------------ | --------------------------------------------------------- | -------------------------------- |
| **Herencia**       | Una clase reutiliza y extiende otra (`extends`)            | `Dog extends Animal`             |
| **Superclase**     | La clase padre que proporciona atributos/métodos comunes  | `Animal`                         |
| **Subclase**       | La clase hija que hereda y puede extender la superclase   | `Dog`, `Cat`, `Bird`             |
| **`super()`**      | Llama al constructor de la superclase                     | `super(name, type, age)`         |
| **`instanceof`**   | Comprueba si un objeto es de un tipo concreto             | `if (a instanceof Dog)`          |
| **Casting**        | Convierte una referencia a un tipo más específico         | `((Dog) a).bark()`               |
| **Polimorfismo**   | Un método se comporta diferente según el tipo real        | `a.talk()` → cada animal habla   |
| **`@Override`**    | Indica que un método sobrescribe al de la superclase      | `@Override public void talk()`   |
| **Clase abstracta**| No se puede instanciar; sirve como plantilla              | `public abstract class Animal`   |
| **Método abstracto** | Sin implementación; obliga a las subclases a definirlo  | `abstract public void talk()`    |

---

## Recursos Adicionales

- [Herencia en Java - Oracle Docs](https://docs.oracle.com/javase/tutorial/java/IandI/subclasses.html)
- [Polimorfismo en Java - Oracle Docs](https://docs.oracle.com/javase/tutorial/java/IandI/polymorphism.html)
- [Clases Abstractas - Oracle Docs](https://docs.oracle.com/javase/tutorial/java/IandI/abstract.html)
- [instanceof operator - Baeldung](https://www.baeldung.com/java-instanceof)
- [Herencia y Polimorfismo - GeeksforGeeks](https://www.geeksforgeeks.org/inheritance-in-java/)
