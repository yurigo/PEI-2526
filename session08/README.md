# Sesión 08 - Herencia, Visibilidad y Polimorfismo

**Fecha:** 24 de febrero de 2026

## Contenidos de la Sesión

En esta sesión hemos continuado con la **herencia**, profundizando en la **visibilidad de atributos y métodos**, el **polimorfismo vertical** (sobrescritura) y el **polimorfismo horizontal** (sobrecarga). Como ejemplo práctico hemos construido un calculador de áreas y perímetros con la jerarquía `Forma → Rectangulo → Cuadrado`, `Forma → Triangulo` y `Forma → Circulo`.

---

### 1. Herencia Repasada

La **herencia** permite que una clase (subclase) reutilice y extienda el comportamiento de otra (superclase). La subclase hereda todos los atributos y métodos accesibles de la superclase.

**Palabra clave `extends`:**

```java
public class Rectangulo extends Forma { ... }
public class Cuadrado extends Rectangulo { ... }  // herencia en cadena
```

**Palabra clave `super`:**

- `super(...)` llama al constructor de la superclase desde el constructor de la subclase.
- Debe ser la **primera instrucción** del constructor.

```java
public Rectangulo(double base, double altura) {
    super("Rectangulo");   // llama a Forma(String nombre)
    this.base = base;
    this.altura = altura;
}

public Cuadrado(int lado) {
    super(lado, lado);     // llama a Rectangulo(double, double)
    setNombre("Cuadrado"); // renombra tras la inicialización
}
```

---

### 2. Modificadores de Visibilidad

| Modificador | Dentro de la clase | Mismo paquete | Subclases | Cualquier clase |
|-------------|:-----------------:|:-------------:|:---------:|:---------------:|
| `private`   | ✅ | ❌ | ❌ | ❌ |
| `package`   | ✅ | ✅ | ❌* | ❌ |
| `protected` | ✅ | ✅ | ✅ | ❌ |
| `public`    | ✅ | ✅ | ✅ | ✅ |

> \* En Java no existe la palabra clave `package` como modificador. La ausencia de modificador equivale al acceso de paquete.

#### 2.1. La paradoja de `protected` en Java

En teoría (UML / conceptual), los modificadores se definen así:

| Modificador | Acceso previsto                             |
|-------------|---------------------------------------------|
| `private`   | Solo en la clase                            |
| `public`    | Desde cualquier clase                       |
| `protected` | En la clase y en las **subclases**          |
| *package*   | En la clase y en el **mismo paquete**       |

Sin embargo, en Java `protected` otorga acceso tanto a las subclases **como** al mismo paquete, lo que hace que en la práctica `protected` sea *más permisivo* que el acceso de paquete:

```
Teoría:    private < package < protected < public
Java:      private < package ≤ protected < public
                              (protected incluye paquete)
```

**Ejemplo en el código de la sesión:**

En `Forma.java` el atributo `nombre` es `protected`:

```java
public abstract class Forma {
    protected String nombre;   // accesible desde subclases y mismo paquete
    ...
}
```

Gracias a ello, `Circulo` (subclase en el mismo paquete) puede acceder directamente:

```java
public class Circulo extends Forma {
    public Circulo(double r) {
        super("hola.Circulo");
        this.nombre = "Circulo";  // acceso directo al atributo protected
    }
}
```

Y `Cuadrado` (subclase de `Rectangulo`, que a su vez es subclase de `Forma`) usa `setNombre()` (método `public` heredado) para renombrar la figura después de llamar a `super()`:

```java
public class Cuadrado extends Rectangulo {
    public Cuadrado(int lado) {
        super(lado, lado);
        setNombre("Cuadrado");  // método público heredado de Forma
    }
}
```

---

### 3. Ejemplo Práctico: Calculador de Áreas y Perímetros

📁 **Código fuente:** [`area-perimeter-calculator/`](area-perimeter-calculator/)

#### 3.1. Jerarquía de clases

```
            ┌──────────────────────────┐
            │         Forma            │  ← Clase abstracta (paquete geometry)
            │ # nombre: String         │
            │ + calcularArea(): double  │  ← Métodos abstractos
            │ + calcularPerimetro(): double│
            │ + getNombre(): String     │
            │ + setNombre(String): void │
            └───────────┬──────────────┘
                        │
          ┌─────────────┼──────────────┐
          │             │              │
   ┌──────┴──────┐ ┌────┴────┐ ┌──────┴──────┐
   │  Rectangulo │ │Triangulo│ │   Circulo   │
   │ - base      │ │ - lado1 │ │ - radius    │
   │ - altura    │ │ - lado2 │ └─────────────┘
   └──────┬──────┘ │ - lado3 │
          │        └─────────┘
   ┌──────┴──────┐
   │  Cuadrado   │  ← Herencia en cadena: Cuadrado → Rectangulo → Forma
   │ (lado = base = altura) │
   └─────────────┘
```

#### 3.2. Clase abstracta `Forma`

```java
package geometry;

public abstract class Forma {

    protected String nombre;

    public Forma(String nombre) {
        this.nombre = nombre;
    }

    // Métodos abstractos: cada subclase DEBE implementarlos
    public abstract double calcularArea();
    public abstract double calcularPerimetro();

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
}
```

#### 3.3. `Rectangulo extends Forma`

```java
package geometry;

public class Rectangulo extends Forma {

    private double base;
    private double altura;

    public Rectangulo(double base, double altura) {
        super("hola.Rectangulo");   // ⚠️ "hola." es un artefacto de desarrollo; debería ser "Rectangulo"
        this.base = base;
        this.altura = altura;
    }

    @Override
    public double calcularArea() {
        return base * altura;
    }

    @Override
    public double calcularPerimetro() {
        return base + base + altura + altura;
    }
}
```

#### 3.4. `Cuadrado extends Rectangulo` (herencia en cadena)

Un `Cuadrado` **es un** `Rectangulo` donde base == altura. Se reutiliza toda la lógica del rectángulo simplemente pasando el mismo lado dos veces:

```java
package geometry;

public class Cuadrado extends Rectangulo {
    public Cuadrado(int lado) {
        super(lado, lado);       // reutiliza calcularArea y calcularPerimetro de Rectangulo
        setNombre("Cuadrado");   // sobrescribe el nombre
    }
}
```

> 💡 `Cuadrado` no necesita implementar `calcularArea()` ni `calcularPerimetro()` porque los hereda de `Rectangulo`, que ya los implementa.

#### 3.5. `Triangulo extends Forma` — Fórmula de Herón

```java
package geometry;

public class Triangulo extends Forma {
    private double lado1, lado2, lado3;

    public Triangulo(double lado1, double lado2, double lado3) {
        super("hola.Triangulo");   // ⚠️ "hola." es un artefacto de desarrollo; debería ser "Triangulo"
        this.lado1 = lado1;
        this.lado2 = lado2;
        this.lado3 = lado3;
    }

    private double calcularSemiperimetro() {
        return calcularPerimetro() / 2;
    }

    @Override
    public double calcularArea() {
        // Fórmula de Herón: √(s·(s-a)·(s-b)·(s-c))
        double s = calcularSemiperimetro();
        return Math.sqrt(s * (s - lado1) * (s - lado2) * (s - lado3));
    }

    @Override
    public double calcularPerimetro() {
        return lado1 + lado2 + lado3;
    }
}
```

#### 3.6. `Circulo extends Forma`

```java
package geometry;

public class Circulo extends Forma {
    private double radius;

    public Circulo(double r) {
        super("hola.Circulo");    // ⚠️ "hola." es un artefacto de desarrollo
        radius = r;
        this.nombre = "Circulo";  // corrige el nombre directamente vía el atributo protected
    }

    @Override
    public double calcularArea() {
        return Math.PI * radius * radius;
    }

    @Override
    public double calcularPerimetro() {
        return 2 * Math.PI * radius;
    }
}
```

#### 3.7. `Main.java` — Polimorfismo en acción

```java
import geometry.*;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {

        ArrayList<Forma> formas = new ArrayList<Forma>();

        formas.add(new Cuadrado(10));
        formas.add(new Rectangulo(5, 6));
        formas.add(new Triangulo(3, 4, 5));
        formas.add(new Triangulo(1, 2, 3));
        formas.add(new Circulo(5));

        muestra(formas);
    }

    // Sobrecarga: mismo nombre, acepta ArrayList<Forma>
    public static void muestra(ArrayList<Forma> formas) {
        for (Forma forma : formas) {
            muestra(forma);           // llama a la versión sobrecargada
        }
    }

    // Sobrecarga: mismo nombre, acepta una sola Forma
    public static void muestra(Forma f) {
        System.out.println("-----------------------------------");
        System.out.println("Nombre:    " + f.getNombre());
        System.out.println("Area:      " + f.calcularArea());
        System.out.println("Perimetro: " + f.calcularPerimetro());
        System.out.println("-----------------------------------");
    }
}
```

**Salida del programa:**

```
-----------------------------------
Nombre:    Cuadrado
Area:      100.0
Perimetro: 40.0
-----------------------------------
-----------------------------------
Nombre:    hola.Rectangulo
Area:      30.0
Perimetro: 22.0
-----------------------------------
-----------------------------------
Nombre:    hola.Triangulo
Area:      6.0
Perimetro: 12.0
-----------------------------------
-----------------------------------
Nombre:    hola.Triangulo
Area:      0.0
Perimetro: 6.0
-----------------------------------
-----------------------------------
Nombre:    Circulo
Area:      78.53981633974483
Perimetro: 31.41592653589793
-----------------------------------
```

> 💡 El triángulo con lados 1, 2, 3 tiene área 0 porque no es un triángulo válido (1+2 = 3, no cumple la desigualdad triangular estricta).

---

### 4. Polimorfismo Vertical — Sobrescritura (`@Override`)

El **polimorfismo vertical** ocurre cuando una subclase **redefine** (sobrescribe) un método de la superclase con el mismo nombre y la misma firma.

```
Superclase                    Subclase
──────────────────────        ─────────────────────────
Forma.calcularArea()  ──→     Rectangulo.calcularArea()   ← versión concreta
                      ──→     Triangulo.calcularArea()    ← versión concreta
                      ──→     Circulo.calcularArea()      ← versión concreta
```

**Características:**
- Misma firma (nombre + parámetros + tipo de retorno)
- Se usa la anotación `@Override` para indicar al compilador que es una sobrescritura intencional
- Java ejecuta el método de la clase **real** del objeto en tiempo de ejecución (**enlace dinámico**)

```java
// La referencia es de tipo Forma, pero el objeto real puede ser Rectangulo, Triangulo, etc.
Forma f = new Rectangulo(5, 6);
f.calcularArea();   // → ejecuta Rectangulo.calcularArea() = 30.0
```

**Ventaja:** podemos iterar una colección de `Forma`s sin saber qué tipo concreto es cada una:

```java
for (Forma forma : formas) {
    System.out.println(forma.calcularArea());  // polimorfismo: cada forma calcula su área
}
```

---

### 5. Polimorfismo Horizontal — Sobrecarga (Method Overloading)

El **polimorfismo horizontal** ocurre cuando una clase define **varios métodos con el mismo nombre** pero con **diferente firma** (número o tipo de parámetros).

> **Regla:** mismo nombre + diferente firma = sobrecarga

**Ejemplo del código de la sesión:**

```java
// Sobrecarga en Main: dos métodos llamados "muestra" con diferente firma
public static void muestra(ArrayList<Forma> formas) { ... }  // firma: (ArrayList<Forma>)
public static void muestra(Forma f) { ... }                  // firma: (Forma)
```

Java elige automáticamente qué versión llamar según el tipo del argumento:

```java
muestra(formas);  // llama a muestra(ArrayList<Forma>)
muestra(forma);   // llama a muestra(Forma)
```

**Diferencia entre sobrescritura y sobrecarga:**

| Aspecto              | Sobrescritura (`@Override`)          | Sobrecarga (Overloading)            |
|----------------------|--------------------------------------|-------------------------------------|
| Dónde ocurre         | Entre superclase y subclase          | Dentro de la misma clase            |
| Firma del método     | Idéntica                             | Diferente (parámetros distintos)    |
| Se resuelve en       | Tiempo de ejecución (dinámico)       | Tiempo de compilación (estático)    |
| Tipo de polimorfismo | Vertical                             | Horizontal                          |
| Requiere herencia    | ✅ Sí                                | ❌ No                               |

---

### 6. Resumen de Conceptos

| Concepto                     | Descripción                                                        | Ejemplo en el código                   |
|------------------------------|--------------------------------------------------------------------|----------------------------------------|
| **Clase abstracta**          | No instanciable; define la interfaz común para subclases           | `abstract class Forma`                 |
| **Método abstracto**         | Sin implementación; obliga a las subclases a definirlo             | `abstract double calcularArea()`       |
| **`extends`**                | Declara la herencia entre clases                                   | `class Rectangulo extends Forma`       |
| **`super(...)`**             | Llama al constructor de la superclase                              | `super("Rectangulo")`                  |
| **`protected`**              | Accesible desde subclases y mismo paquete                          | `protected String nombre`              |
| **`@Override`**              | Indica sobrescritura intencional de un método                      | `@Override public double calcularArea()`|
| **Polimorfismo vertical**    | Subclase redefine método de la superclase; Java elige en ejecución | `Forma f = new Circulo(5); f.calcularArea()` |
| **Polimorfismo horizontal**  | Mismo nombre, diferente firma en la misma clase                    | `muestra(ArrayList)` / `muestra(Forma)`|
| **Herencia en cadena**       | `Cuadrado → Rectangulo → Forma`                                    | `Cuadrado` hereda de `Rectangulo`      |

---

## Recursos Adicionales

- [Herencia en Java - Oracle Docs](https://docs.oracle.com/javase/tutorial/java/IandI/subclasses.html)
- [Clases Abstractas - Oracle Docs](https://docs.oracle.com/javase/tutorial/java/IandI/abstract.html)
- [Modificadores de acceso - Oracle Docs](https://docs.oracle.com/javase/tutorial/java/javaOO/accesscontrol.html)
- [Sobrescritura de métodos - Oracle Docs](https://docs.oracle.com/javase/tutorial/java/IandI/override.html)
- [Sobrecarga de métodos - Baeldung](https://www.baeldung.com/java-method-overloading)
- [Polimorfismo en Java - GeeksforGeeks](https://www.geeksforgeeks.org/polymorphism-in-java/)
- [Fórmula de Herón - Wikipedia](https://es.wikipedia.org/wiki/F%C3%B3rmula_de_Her%C3%B3n)
