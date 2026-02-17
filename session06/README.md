# Sesión 06 - Relaciones entre clases

**Fecha:** 17 de febrero de 2026

## Contenidos de la Sesión

En esta sesión hemos profundizado en las **relaciones entre clases** en el diseño orientado a objetos y su representación en **UML (Unified Modeling Language)**. Las relaciones entre clases son fundamentales para modelar sistemas complejos y entender cómo las diferentes partes del sistema interactúan entre sí.

### 1. Introducción a las Relaciones entre Clases

En la programación orientada a objetos, las clases rara vez existen de forma aislada. Normalmente, colaboran y se relacionan entre sí para formar sistemas completos. UML nos proporciona notaciones específicas para representar estos diferentes tipos de relaciones:

- **Dependencia** (Dependency)
- **Asociación** (Association)
- **Agregación** (Aggregation)
- **Composición** (Composition)

Cada tipo de relación tiene un significado semántico diferente y se representa con una notación específica en los diagramas UML de clases.

---

### 2. Dependencia (Dependency)

#### 2.1. Definición

La **dependencia** es la relación más débil entre clases. Indica que una clase **utiliza temporalmente** a otra clase, típicamente como:
- Parámetro de un método
- Variable local dentro de un método
- Valor de retorno de un método

**Característica clave:** La relación es temporal y no permanente. La clase dependiente solo necesita la otra clase durante la ejecución de un método específico.

#### 2.2. Notación UML

En UML, la dependencia se representa con una **flecha discontinua (- - - >)** que apunta desde la clase dependiente hacia la clase de la que depende.

```
┌─────────────┐          ┌──────────────┐
│   Printer   │- - - - ->│   Document   │
└─────────────┘          └──────────────┘
```

#### 2.3. Ejemplo: Printer - Document

Una impresora necesita un documento para imprimir, pero no lo almacena permanentemente. El documento solo se usa durante el proceso de impresión.

**Código Java:**

```java
public class Document {
    private String contenido;
    private String titulo;
    
    public Document(String titulo, String contenido) {
        this.titulo = titulo;
        this.contenido = contenido;
    }
    
    public String getContenido() {
        return contenido;
    }
    
    public String getTitulo() {
        return titulo;
    }
}

public class Printer {
    private String modelo;
    
    public Printer(String modelo) {
        this.modelo = modelo;
    }
    
    // Document es un parámetro - relación de DEPENDENCIA
    public void imprimir(Document documento) {
        System.out.println("Imprimiendo en " + modelo);
        System.out.println("Título: " + documento.getTitulo());
        System.out.println("Contenido: " + documento.getContenido());
        System.out.println("--------------------");
    }
}
```

**Uso:**

```java
Printer impresora = new Printer("HP LaserJet");
Document doc = new Document("Informe", "Contenido del informe...");
impresora.imprimir(doc);  // La impresora usa el documento temporalmente
```

**Puntos clave:**
- ✅ `Printer` no tiene un atributo de tipo `Document`
- ✅ `Document` solo se usa como parámetro del método `imprimir()`
- ✅ La relación es temporal: solo existe durante la ejecución del método

---

### 3. Asociación (Association)

#### 3.1. Definición

La **asociación** representa una relación **estructural y duradera** entre clases. Indica que los objetos de una clase están conectados con objetos de otra clase de forma permanente.

**Característica clave:** La relación es persistente y se materializa como un **atributo** en la clase.

#### 3.2. Notación UML

En UML, la asociación se representa con una **línea continua (──)** entre las dos clases. Puede incluir:
- **Nombre de la asociación** (opcional)
- **Multiplicidad/Cardinalidad** en ambos extremos
- **Direccionalidad** (flecha simple si es unidireccional)

```
┌──────────────┐         ┌─────────────┐
│  University  │◆────────│   Student   │
└──────────────┘  1   *  └─────────────┘
                         
┌──────────────┐         ┌─────────────┐
│  University  │◆────────│   Teacher   │
└──────────────┘  1   *  └─────────────┘
```

#### 3.3. Ejemplo: University - Student - Teacher

Una universidad tiene estudiantes y profesores. Estos estudiantes y profesores están asociados a la universidad de forma permanente.

**Código Java:**

```java
import java.util.ArrayList;

public class Student {
    private String nombre;
    private String matricula;
    
    public Student(String nombre, String matricula) {
        this.nombre = nombre;
        this.matricula = matricula;
    }
    
    @Override
    public String toString() {
        return "Estudiante: " + nombre + " (" + matricula + ")";
    }
}

public class Teacher {
    private String nombre;
    private String departamento;
    
    public Teacher(String nombre, String departamento) {
        this.nombre = nombre;
        this.departamento = departamento;
    }
    
    @Override
    public String toString() {
        return "Profesor: " + nombre + " - Depto: " + departamento;
    }
}

public class University {
    private String nombre;
    // ASOCIACIÓN: University tiene estudiantes (relación permanente)
    private ArrayList<Student> estudiantes;
    // ASOCIACIÓN: University tiene profesores (relación permanente)
    private ArrayList<Teacher> profesores;
    
    public University(String nombre) {
        this.nombre = nombre;
        this.estudiantes = new ArrayList<>();
        this.profesores = new ArrayList<>();
    }
    
    public void matricularEstudiante(Student estudiante) {
        estudiantes.add(estudiante);
    }
    
    public void contratarProfesor(Teacher profesor) {
        profesores.add(profesor);
    }
    
    public void mostrarInformacion() {
        System.out.println("Universidad: " + nombre);
        System.out.println("\nEstudiantes matriculados:");
        for (Student est : estudiantes) {
            System.out.println("  - " + est);
        }
        System.out.println("\nProfesores contratados:");
        for (Teacher prof : profesores) {
            System.out.println("  - " + prof);
        }
    }
}
```

**Uso:**

```java
University upc = new University("UPC - Universidad Politécnica de Cataluña");

Student s1 = new Student("Ana García", "20231001");
Student s2 = new Student("Carlos Ruiz", "20231002");

Teacher t1 = new Teacher("Dr. Martínez", "Informática");
Teacher t2 = new Teacher("Dra. López", "Matemáticas");

upc.matricularEstudiante(s1);
upc.matricularEstudiante(s2);
upc.contratarProfesor(t1);
upc.contratarProfesor(t2);

upc.mostrarInformacion();
```

**Puntos clave:**
- ✅ `University` tiene atributos de tipo `ArrayList<Student>` y `ArrayList<Teacher>`
- ✅ La relación es permanente: los estudiantes y profesores pertenecen a la universidad
- ✅ La universidad puede existir sin estudiantes o profesores (opcional)
- ✅ Los estudiantes y profesores pueden existir independientemente de la universidad

---

### 4. Agregación (Aggregation)

#### 4.1. Definición

La **agregación** es un tipo especial de asociación que representa una relación **"tiene-un" (has-a)** donde:
- Una clase es el "todo" (contenedor)
- Otra clase es la "parte" (contenido)

**Característica clave:** Las partes pueden existir **independientemente** del todo. Si el contenedor se destruye, las partes siguen existiendo.

#### 4.2. Notación UML

En UML, la agregación se representa con una **línea continua con un rombo vacío (◇)** en el lado del contenedor.

```
┌──────────────┐         ┌──────────────┐
│   Playlist   │◇────────│     Song     │
└──────────────┘  1   *  └──────────────┘
```

#### 4.3. Ejemplo: Playlist - Song

Una lista de reproducción contiene canciones, pero las canciones pueden existir independientemente de la lista. Si eliminamos la playlist, las canciones siguen existiendo y pueden pertenecer a otras playlists.

**Código Java:**

```java
import java.util.ArrayList;

public class Song {
    private String titulo;
    private String artista;
    private int duracionSegundos;
    
    public Song(String titulo, String artista, int duracionSegundos) {
        this.titulo = titulo;
        this.artista = artista;
        this.duracionSegundos = duracionSegundos;
    }
    
    public int getDuracionSegundos() {
        return duracionSegundos;
    }
    
    @Override
    public String toString() {
        return titulo + " - " + artista + " (" + duracionSegundos + "s)";
    }
}

public class Playlist {
    private String nombre;
    // AGREGACIÓN: Playlist contiene canciones, pero las canciones existen independientemente
    private ArrayList<Song> canciones;
    
    public Playlist(String nombre) {
        this.nombre = nombre;
        this.canciones = new ArrayList<>();
    }
    
    public void agregarCancion(Song cancion) {
        canciones.add(cancion);
        System.out.println("'" + cancion + "' añadida a la playlist '" + nombre + "'");
    }
    
    public void eliminarCancion(Song cancion) {
        canciones.remove(cancion);
        System.out.println("Canción eliminada de la playlist");
    }
    
    public void reproducir() {
        System.out.println("\n🎵 Reproduciendo playlist: " + nombre);
        for (Song cancion : canciones) {
            System.out.println("  ▶ " + cancion);
        }
    }
    
    public int getDuracionTotal() {
        int total = 0;
        for (Song cancion : canciones) {
            total += cancion.getDuracionSegundos();
        }
        return total;
    }
}
```

**Uso:**

```java
// Las canciones existen independientemente
Song s1 = new Song("Bohemian Rhapsody", "Queen", 354);
Song s2 = new Song("Imagine", "John Lennon", 183);
Song s3 = new Song("Billie Jean", "Michael Jackson", 294);

// Creamos dos playlists diferentes
Playlist rockClassics = new Playlist("Rock Clásico");
Playlist mejoresExitos = new Playlist("Mejores Éxitos");

// Una misma canción puede estar en múltiples playlists
rockClassics.agregarCancion(s1);
rockClassics.agregarCancion(s2);

mejoresExitos.agregarCancion(s1);  // s1 está en ambas playlists
mejoresExitos.agregarCancion(s3);

rockClassics.reproducir();
mejoresExitos.reproducir();

// Si eliminamos una playlist, las canciones siguen existiendo
rockClassics = null;  // La playlist desaparece
System.out.println("\nLas canciones siguen existiendo:");
System.out.println(s1);  // ✅ s1 sigue existiendo
System.out.println(s2);  // ✅ s2 sigue existiendo
```

**Puntos clave:**
- ✅ `Playlist` contiene referencias a objetos `Song`
- ✅ Las canciones se crean **fuera** de la playlist
- ✅ Una canción puede pertenecer a **múltiples** playlists
- ✅ Si destruimos la playlist, las canciones **siguen existiendo**

---

### 5. Composición (Composition)

#### 5.1. Definición

La **composición** es una forma **fuerte** de agregación que representa una relación **"parte-de" (part-of)** donde:
- Las partes NO pueden existir sin el todo
- El ciclo de vida de las partes depende completamente del todo
- Si el contenedor se destruye, las partes también se destruyen

**Característica clave:** Es la relación más fuerte. Las partes se crean dentro del todo y mueren con él.

#### 5.2. Notación UML

En UML, la composición se representa con una **línea continua con un rombo relleno (◆)** en el lado del contenedor.

```
┌──────────────┐         ┌──────────────┐
│   Picture    │◆────────│    Pixel     │
└──────────────┘  1   *  └──────────────┘

O bien:

┌──────────────┐         ┌──────────────┐
│  Chessboard  │◆────────│     Cell     │
└──────────────┘  1  64  └──────────────┘
```

#### 5.3. Ejemplo 1: Picture - Pixel

Una imagen está compuesta por píxeles. Si eliminamos la imagen, los píxeles dejan de tener sentido y se destruyen también.

**Código Java:**

```java
public class Pixel {
    private int x;
    private int y;
    private String color;  // Formato: "#RRGGBB"
    
    public Pixel(int x, int y, String color) {
        this.x = x;
        this.y = y;
        this.color = color;
    }
    
    public void setColor(String color) {
        this.color = color;
    }
    
    @Override
    public String toString() {
        return "Pixel(" + x + "," + y + "): " + color;
    }
}

public class Picture {
    private int ancho;
    private int alto;
    // COMPOSICIÓN: Los píxeles se crean dentro de Picture y no existen fuera de ella
    private Pixel[][] pixeles;
    
    public Picture(int ancho, int alto, String colorInicial) {
        this.ancho = ancho;
        this.alto = alto;
        this.pixeles = new Pixel[alto][ancho];
        
        // Los píxeles se CREAN dentro del constructor de Picture
        for (int y = 0; y < alto; y++) {
            for (int x = 0; x < ancho; x++) {
                this.pixeles[y][x] = new Pixel(x, y, colorInicial);
            }
        }
    }
    
    public void pintarPixel(int x, int y, String color) {
        if (x >= 0 && x < ancho && y >= 0 && y < alto) {
            pixeles[y][x].setColor(color);
        }
    }
    
    public void mostrar() {
        System.out.println("Imagen " + ancho + "x" + alto + ":");
        for (int y = 0; y < alto; y++) {
            for (int x = 0; x < ancho; x++) {
                System.out.print(pixeles[y][x].toString() + " ");
            }
            System.out.println();
        }
    }
}
```

**Uso:**

```java
// Creamos una imagen de 3x3 píxeles con color blanco
Picture imagen = new Picture(3, 3, "#FFFFFF");

// Los píxeles se crearon automáticamente dentro de Picture
imagen.pintarPixel(0, 0, "#FF0000");  // Rojo
imagen.pintarPixel(1, 1, "#00FF00");  // Verde
imagen.pintarPixel(2, 2, "#0000FF");  // Azul

imagen.mostrar();

// Si destruimos la imagen, los píxeles también desaparecen
imagen = null;  // ❌ Los píxeles ya no existen
```

#### 5.4. Ejemplo 2: Chessboard - Cell

Un tablero de ajedrez está compuesto por 64 celdas (8x8). Las celdas no tienen sentido fuera del tablero.

**Código Java:**

```java
public class Cell {
    private int fila;
    private int columna;
    private String color;  // "blanco" o "negro"
    private String pieza;  // null si no hay pieza
    
    public Cell(int fila, int columna, String color) {
        this.fila = fila;
        this.columna = columna;
        this.color = color;
        this.pieza = null;
    }
    
    public void colocarPieza(String pieza) {
        this.pieza = pieza;
    }
    
    public void quitarPieza() {
        this.pieza = null;
    }
    
    @Override
    public String toString() {
        String contenido = (pieza != null) ? pieza : "[ ]";
        return contenido;
    }
}

public class Chessboard {
    private static final int TAMAÑO = 8;
    // COMPOSICIÓN: Las celdas se crean dentro de Chessboard
    private Cell[][] tablero;
    
    public Chessboard() {
        tablero = new Cell[TAMAÑO][TAMAÑO];
        
        // Las celdas se CREAN dentro del constructor
        for (int fila = 0; fila < TAMAÑO; fila++) {
            for (int columna = 0; columna < TAMAÑO; columna++) {
                // Alternamos colores como en un tablero real
                String color = ((fila + columna) % 2 == 0) ? "blanco" : "negro";
                tablero[fila][columna] = new Cell(fila, columna, color);
            }
        }
        
        inicializarPiezas();
    }
    
    private void inicializarPiezas() {
        // Colocamos algunas piezas de ejemplo
        tablero[0][0].colocarPieza("♜");  // Torre negra
        tablero[0][4].colocarPieza("♚");  // Rey negro
        tablero[7][0].colocarPieza("♖");  // Torre blanca
        tablero[7][4].colocarPieza("♔");  // Rey blanco
    }
    
    public void mostrarTablero() {
        System.out.println("  a  b  c  d  e  f  g  h");
        for (int fila = 0; fila < TAMAÑO; fila++) {
            System.out.print((8 - fila) + " ");
            for (int columna = 0; columna < TAMAÑO; columna++) {
                System.out.print(tablero[fila][columna] + " ");
            }
            System.out.println();
        }
    }
}
```

**Uso:**

```java
Chessboard tablero = new Chessboard();
tablero.mostrarTablero();

// Si destruimos el tablero, las 64 celdas también desaparecen
tablero = null;  // ❌ Las celdas ya no existen
```

**Puntos clave de la Composición:**
- ✅ Las partes (Pixel, Cell) se crean **dentro** del constructor del todo (Picture, Chessboard)
- ✅ Las partes **NO pueden existir** sin el todo
- ✅ El ciclo de vida de las partes está **completamente ligado** al todo
- ✅ Es la relación más fuerte y restrictiva

---

### 6. Cardinalidad y Direccionalidad

#### 6.1. Cardinalidad (Multiplicidad)

La **cardinalidad** indica cuántas instancias de una clase pueden estar relacionadas con instancias de otra clase. Se especifica en ambos extremos de la relación.

**Notaciones comunes:**

| Notación | Significado                                   | Ejemplo                                     |
| -------- | --------------------------------------------- | ------------------------------------------- |
| `1`      | Exactamente uno                               | Un estudiante tiene exactamente 1 matrícula |
| `0..1`   | Cero o uno (opcional)                         | Una persona puede tener 0 o 1 cónyuge       |
| `*`      | Cero o más (cualquier cantidad)               | Una universidad tiene * estudiantes         |
| `1..*`   | Uno o más (al menos uno)                      | Un libro tiene al menos 1 página            |
| `n..m`   | Entre n y m (rango específico)                | Un equipo tiene entre 11 y 15 jugadores     |
| `n`      | Exactamente n (número fijo)                   | Un tablero de ajedrez tiene 64 celdas       |

**Ejemplos visuales:**

```
Universidad  1 ────────── * Estudiante
"Una universidad tiene muchos estudiantes"
"Un estudiante pertenece a una universidad"

Playlist  1 ────────── * Canción
"Una playlist tiene muchas canciones"
"Una canción puede estar en muchas playlists"

Picture  1 ────────── * Pixel
"Una imagen tiene muchos píxeles"
"Un píxel pertenece a una sola imagen"
```

#### 6.2. Direccionalidad

La **direccionalidad** indica la dirección en la que se puede navegar la relación.

**Tipos:**

1. **Bidireccional** (sin flecha): Ambas clases conocen a la otra
   ```
   Estudiante ────────── Universidad
   ```
   - El estudiante conoce su universidad
   - La universidad conoce sus estudiantes

2. **Unidireccional** (con flecha →): Solo una clase conoce a la otra
   ```
   Printer ──────> Document
   ```
   - El Printer conoce al Document
   - El Document NO conoce al Printer

**En Java:**
- La direccionalidad se implementa mediante atributos
- Si A tiene un atributo de tipo B, entonces A → B
- Si solo B tiene un atributo de tipo A, entonces B → A
- Si ambos tienen atributos del otro, es bidireccional

---

### 7. Implementación en Java

#### 7.1. Similitudes en el Código

Como se mencionó en la sesión, **Asociación, Agregación y Composición** son muy semejantes en cuanto a la codificación en Java. Todas se implementan como **atributos** en la clase:

```java
// Asociación
public class University {
    private ArrayList<Student> estudiantes;  // Atributo
}

// Agregación
public class Playlist {
    private ArrayList<Song> canciones;  // Atributo
}

// Composición
public class Picture {
    private Pixel[][] pixeles;  // Atributo
}
```

#### 7.2. Diferencias Conceptuales

La diferencia está en el **ciclo de vida** y la **propiedad**:

| Relación     | ¿Quién crea las partes? | ¿Pueden existir independientemente? | Ejemplo                  |
| ------------ | ----------------------- | ----------------------------------- | ------------------------ |
| Asociación   | Fuera del contenedor    | ✅ Sí                               | Universidad - Estudiante |
| Agregación   | Fuera del contenedor    | ✅ Sí                               | Playlist - Canción       |
| Composición  | Dentro del contenedor   | ❌ No                               | Picture - Pixel          |

```java
// AGREGACIÓN: Las canciones se crean FUERA
Song cancion = new Song("Título", "Artista", 180);
Playlist lista = new Playlist("Mi Lista");
lista.agregarCancion(cancion);  // Se pasa la referencia

// COMPOSICIÓN: Los píxeles se crean DENTRO
Picture imagen = new Picture(100, 100, "#FFFFFF");
// Los píxeles ya están creados automáticamente
```

---

### 8. Resumen Comparativo

| Característica      | Dependencia          | Asociación            | Agregación               | Composición             |
| ------------------- | -------------------- | --------------------- | ------------------------ | ----------------------- |
| **Notación UML**    | `- - - >`            | `──`                  | `◇──`                    | `◆──`                   |
| **Fuerza**          | Muy débil            | Moderada              | Fuerte                   | Muy fuerte              |
| **Permanencia**     | Temporal             | Permanente            | Permanente               | Permanente              |
| **Implementación**  | Parámetro/variable   | Atributo              | Atributo                 | Atributo                |
| **Ciclo de vida**   | Independiente        | Independiente         | Independiente            | Dependiente             |
| **Ejemplo**         | Printer - Document   | University - Student  | Playlist - Song          | Picture - Pixel         |

---

## Ejercicios Prácticos

Durante la sesión se realizaron diversos ejercicios para practicar estos conceptos:

### 1. Traducción UML a Java

📁 **Ubicación:** [`ejercicios/UMLtoJAVA/`](ejercicios/UMLtoJAVA/)

Se proporcionó un diagrama UML completo ([diagrama1.jpg](ejercicios/UMLtoJAVA/diagrama1.jpg)) que incluía ejemplos de:
- Asociación: Ver directorio [`Association/`](ejercicios/UMLtoJAVA/Association/)
- Agregación: Ver directorio [`Aggregation/`](ejercicios/UMLtoJAVA/Aggregation/)
- Composición: Ver directorio [`Composition/`](ejercicios/UMLtoJAVA/Composition/)

A partir de este diagrama UML, se codificaron en Java fragmentos que demuestran cada tipo de relación.

### 2. Ejercicios de Examen

📁 **Ubicación:** [`ejercicios/questions/README.md`](ejercicios/questions/README.md)

Se resolvieron problemas teóricos y prácticos sobre relaciones entre clases, incluyendo:

**Problema 1:** Identificar y justificar relaciones entre clases:
- a. Canción + Lista de Reproducción → **Agregación**
- b. Usuario + Usuario Administrador → **Herencia/Generalización**
- c. Fotografía + Comentarios → **Asociación**
- d. Capítulos + Serie → **Composición**
- e. Sierra + Tronco de Madera → **Dependencia**

**Problema 3:** Análisis de diagramas UML:
- Comparar diferentes relaciones UML
- Identificar cuál representa mejor una mesa y sus patas (Composición)
- Explicar características de cada relación con ejemplos

### 3. Diseño UML desde Texto

📁 **Ubicación:** [`ejercicios/TEXTtoUML/`](ejercicios/TEXTtoUML/)

Se practicó el diseño de diagramas UML a partir de descripciones textuales de sistemas:

- **DIABLO XII:** [Ver enunciado](ejercicios/TEXTtoUML/DIABLO%20XII.md)
  - Sistema de videojuego de rol con personajes, ítems, habilidades, etc.
  
- **FARM SIMULATOR:** [Ver enunciado](ejercicios/TEXTtoUML/FARM%20SIMULATOR.md)
  - Simulador de granja con cultivos, animales, edificios, etc.

Estos ejercicios requieren:
1. Leer y comprender el dominio del problema
2. Identificar las clases principales
3. Determinar las relaciones entre clases
4. Especificar cardinalidades
5. Crear el diagrama UML completo

---

## Conclusiones

En esta sesión aprendimos:

- ✅ **Cuatro tipos de relaciones** entre clases y cuándo usar cada una
- ✅ **Notación UML** para representar visualmente las relaciones
- ✅ **Diferencias conceptuales** entre asociación, agregación y composición
- ✅ **Cardinalidad y direccionalidad** para especificar relaciones con precisión
- ✅ **Implementación en Java** de cada tipo de relación
- ✅ La importancia del **modelado UML** antes de programar
- ✅ Práctica con ejercicios de traducción UML ↔ Java y diseño desde cero

**Reflexión importante:** Aunque en Java las relaciones de asociación, agregación y composición se implementan de forma similar (como atributos), es fundamental entender sus diferencias semánticas para diseñar sistemas bien estructurados y mantenibles.

---

## Recursos Adicionales

- [UML Class Diagrams - Oracle](https://www.oracle.com/technical-resources/articles/java/class-diagram.html)
- [UML Relationships - Visual Paradigm](https://www.visual-paradigm.com/guide/uml-unified-modeling-language/uml-class-diagram-tutorial/)
- [Aggregation vs Composition - GeeksforGeeks](https://www.geeksforgeeks.org/association-composition-aggregation-java/)
- [UML Tutorial - Lucidchart](https://www.lucidchart.com/pages/uml-class-diagram)