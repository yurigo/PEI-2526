# Sesión 03 - Encapsulamiento y Principios de Diseño

**Fecha:** 9 de febrero de 2026

## Contenidos de la Sesión

En esta sesión hemos profundizado en conceptos avanzados de Programación Orientada a Objetos, centrándonos en el encapsulamiento y en principios fundamentales de diseño de software:

### 1. Encapsulamiento y Modificadores de Acceso

El **encapsulamiento** es uno de los pilares fundamentales de la POO. Consiste en ocultar los detalles internos de implementación de una clase y controlar el acceso a sus atributos mediante modificadores de acceso.

#### Modificadores de Acceso en Java

En esta sesión trabajamos con dos modificadores principales:

- **`private`**: El atributo o método solo es accesible dentro de la propia clase. Es la forma más restrictiva de acceso y la más recomendada para atributos.
- **`public`**: El atributo o método es accesible desde cualquier parte del programa.

**Nota importante:** En esta sesión NO trabajamos con `protected` ni con el modificador por defecto (package-private).

#### ¿Por qué usar atributos privados?

```java
// ❌ MAL: Atributos públicos exponen la implementación interna
public class Pokemon {
    public String nombre;
    public Integer nivel;
}

// ✅ BIEN: Atributos privados protegen la información
public class Pokemon {
    private String nombre;
    private Integer nivel;
}
```

**Ventajas del encapsulamiento:**

1. **Protección de datos**: Evita que se modifiquen los atributos de forma incorrecta desde fuera de la clase.
2. **Control de acceso**: Podemos validar los datos antes de asignarlos.
3. **Flexibilidad**: Podemos cambiar la implementación interna sin afectar al código que usa la clase.
4. **Mantenibilidad**: El código es más fácil de entender y mantener.

### 2. Métodos Getters y Setters

Los **getters** y **setters** son métodos públicos que permiten acceder y modificar atributos privados de forma controlada.

#### Convenciones de Nomenclatura

```java
// Getter: obtiene el valor de un atributo
public TipoAtributo getNombreAtributo() {
    return nombreAtributo;
}

// Setter: establece el valor de un atributo
public void setNombreAtributo(TipoAtributo nombreAtributo) {
    this.nombreAtributo = nombreAtributo;
}
```

#### Ejemplo con Validación

```java
public class Pokemon {
    private String nombre;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        if (nombre.equals("")) {
            this.nombre = "NO SE PUEDE VACIO";
        } else {
            this.nombre = nombre;
        }
    }
}
```

En este ejemplo, el setter valida que el nombre no esté vacío antes de asignarlo, demostrando el poder del encapsulamiento.

### 3. Principios de Diseño

#### 3.1. Experto de Información (Information Expert)

**Principio**: Una clase debe ser responsable de la información que posee. Los métodos que operan sobre los datos deben estar en la misma clase que contiene esos datos.

**Ejemplo en Pokédex:**

En lugar de usar múltiples getters para acceder a los datos y modificarlos fuera:

```java
// ❌ MAL: Lógica de negocio fuera de la clase
p1.setNivel(p1.getNivel() + 1);
p1.setVida(p1.getVida() + 100);
```

La clase Pokemon debe ser responsable de su propia lógica:

```java
// ✅ BIEN: La clase es experta en su propia información
public class Pokemon {
    private Integer nivel;
    private Integer vida;

    public void subirNivel() {
        this.nivel += 1;
        this.vida += 100;
    }
}
```

**Beneficios:**

- La lógica está centralizada en un solo lugar
- Más fácil de mantener y modificar
- Reduce duplicación de código

#### 3.2. Bajo Acoplamiento (Low Coupling)

**Principio**: Las clases deben tener la menor dependencia posible con otras clases o sistemas externos.

**Ejemplo en Pokédex y Gimnasio:**

En lugar de acoplar la clase con `System.out.println`:

```java
// ❌ MAL: Acoplamiento con System.out
public class Pokemon {
    public void muestrate() {
        System.out.println(this.nombre + ": " + this.tipo);
    }
}
```

Es mejor devolver la información y dejar que quien llame decida qué hacer con ella:

```java
// ✅ BIEN: Bajo acoplamiento, devuelve información
public class Pokemon {
    public String getInformation() {
        return this.nombre + ": " + this.tipo + ", " + this.nivel;
    }
}

// En Main.java
System.out.println(p1.getInformation());
```

**Beneficios:**

- La clase es reutilizable en diferentes contextos (consola, GUI, web, tests, etc.)
- Mayor flexibilidad para cambios futuros
- Facilita las pruebas unitarias

#### 3.3. DRY - Don't Repeat Yourself

**Principio**: Evita la duplicación de código extrayendo funcionalidad común en métodos reutilizables.

**Ejemplo en Gimnasio:**

En lugar de repetir código para mostrar personas:

```java
// ❌ MAL: Código duplicado
System.out.println(p1.getInformation());
System.out.println(p2.getInformation());
System.out.println(p3.getInformation());
// ... repetir esto en varios lugares
```

Extraemos una función reutilizable:

```java
// ✅ BIEN: Función reutilizable
public static void mostrarPorPantallaLasPersonas(Persona a, Persona b, Persona c) {
    System.out.println(a.getInformation());
    System.out.println(b.getInformation());
    System.out.println(c.getInformation());
}
```

**Beneficios:**

- Si necesitamos cambiar cómo se muestran las personas, solo lo hacemos en un lugar
- Reduce errores
- Código más limpio y mantenible

### 4. El Método toString()

El método `toString()` es un método especial en Java que pertenece a la clase `Object` (la clase padre de todas las clases en Java). Este método se llama automáticamente cuando intentamos imprimir un objeto.

```java
public class Producto {
    private String nombre;
    private Float precio;
    private Long id;

    @Override
    public String toString() {
        return nombre + " " + precio + " " + id;
    }
}

// En Main.java
Producto p1 = new Producto("Alpargatas", 100.0F, 1000);
System.out.println(p1);  // Llama automáticamente a toString()
```

**Ventajas de toString():**

- Java lo llama automáticamente al imprimir un objeto
- Es una convención estándar que facilita el debugging
- Más elegante que métodos personalizados como `getInformation()`

### 5. Variables y Métodos Estáticos (static)

La palabra clave `static` se usa para crear miembros (atributos o métodos) que pertenecen a la clase en sí, no a las instancias individuales.

#### Atributos Estáticos

```java
public class Producto {
    private Long id;
    public static Long contador = 0L;  // Variable compartida por todas las instancias

    public Producto(String nombre, String descripcion, Float precio, Integer stock, Date caducidad) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.stock = stock;
        this.caducidad = caducidad;
        this.id = ++contador;  // Incrementa el contador y asigna el ID
    }
}
```

**Características de atributos static:**

- Existe una única copia compartida por todas las instancias de la clase
- Se puede acceder sin crear una instancia: `Producto.contador`
- Útil para contadores, configuraciones globales, constantes, etc.

**Ejemplo de uso:**

```java
System.out.println(Producto.contador);  // 0

Producto p1 = new Producto("Alpargatas", "Alpargatas negras", 100.0F, 1000, null);
System.out.println(Producto.contador);  // 1

Producto p2 = new Producto("Croquetas", "Croquetas de la iaia", 10.0F, 1000000000, null);
System.out.println(Producto.contador);  // 2
```

Cada vez que se crea un `Producto`, el contador estático se incrementa, asegurando que cada producto tenga un ID único.

## 6. Ejemplos Prácticos

### 6.1. Pokédex - Gestión de Pokémon

**Ubicación:** `pokedex/`

Este proyecto demuestra el uso de encapsulamiento, getters/setters con validación, y el principio del Experto de Información.

#### Clase Pokemon

```java
public class Pokemon {
    private String name;
    private String tipo;
    private Integer nivel;
    private Integer vida;

    public Pokemon(String name, String tipo) {
        this.setName(name);
        this.tipo = tipo;
        this.nivel = 0;
        this.vida = 0;
    }

    private void setName(String name) {
        if (name.equals("")) {
            this.name = "NO SE PUEDE VACIO";
        } else {
            this.name = name;
        }
    }

    public String getInformation() {
        return this.name + ": " + this.tipo + ", Nivel: " + this.nivel + ", Vida: " + this.vida;
    }

    public void subirNivel() {
        this.nivel += 1;
        this.vida += 100;
    }
}
```

**Conceptos demostrados:**

- ✅ Atributos privados para proteger los datos
- ✅ Validación en el setter del nombre
- ✅ Método `subirNivel()` que encapsula la lógica de subir de nivel (Experto de Información)
- ✅ Método `getInformation()` en lugar de `muestrate()` (Bajo Acoplamiento)

#### Uso en Main

```java
Pokemon p1 = new Pokemon("Pikachu", "Eléctrico");
Pokemon p2 = new Pokemon("Psyduck", "Agua");

System.out.println(p1.getInformation());
System.out.println(p2.getInformation());

p1.subirNivel();  // Incrementa nivel y vida automáticamente

System.out.println(p1.getInformation());
```

### 6.2. Gimnasio - Gestión de Personas (Versión Mejorada)

**Ubicación:** `gimnasio/`

Este proyecto es una evolución del de la Sesión 02. Ahora todos los atributos son privados y se aplican los principios de Bajo Acoplamiento y DRY.

#### Clase Persona Mejorada

```java
public class Persona {
    private String nombre;
    private Date fechaEntrada;
    private Date fechaSalida;
    private long id;
    private String DNI;
    private boolean estaDentro;

    public Persona(String nombre, String DNI) {
        this.nombre = nombre;
        this.fechaEntrada = null;
        this.fechaSalida = null;
        this.id = 0;
        this.DNI = DNI;
        this.estaDentro = false;
    }

    public String getInformation() {
        return "-------------------------\n" +
               "Nombre: " + nombre + "\n" +
               nombre + " " + DNI + " " + estaDentro + "\n" +
               "Fecha de entrada: " + fechaEntrada + "\n" +
               "Fecha de salida: " + fechaSalida + "\n" +
               "-------------------------";
    }

    public void registrarEntrada() {
        this.estaDentro = true;
        this.fechaEntrada = new Date();
        this.fechaSalida = null;
    }

    public void registrarSalida() {
        this.estaDentro = false;
        this.fechaEntrada = null;
        this.fechaSalida = new Date();
    }
}
```

**Mejoras respecto a la Sesión 02:**

- ✅ Todos los atributos son privados (incluyendo `nombre` que antes era público)
- ✅ `getInformation()` en lugar de `muestrate()` para desacoplar de System.out
- ✅ Los métodos de registro encapsulan la lógica de gestión de entradas/salidas

#### Uso en Main con DRY

```java
public class Main {
    public static void main(String[] args) {
        Persona p1 = new Persona("Alice", "123456789V");
        Persona p2 = new Persona("Bob", "123456789V");
        Persona p3 = new Persona("Charlie", "123456789V");

        mostrarPorPantallaLasPersonas(p1, p2, p3);

        p2.registrarEntrada();
        mostrarPorPantallaLasPersonas(p1, p2, p3);

        p1.registrarEntrada();
        mostrarPorPantallaLasPersonas(p1, p2, p3);

        p2.registrarSalida();
        mostrarPorPantallaLasPersonas(p1, p2, p3);
    }

    // Función extraída para evitar repetir código (DRY)
    public static void mostrarPorPantallaLasPersonas(Persona a, Persona b, Persona c) {
        System.out.println(a.getInformation());
        System.out.println(b.getInformation());
        System.out.println(c.getInformation());
    }
}
```

### 6.3. Mercado - Gestión de Productos con Static

**Ubicación:** `mercado/`

Este proyecto introduce el concepto de variables estáticas para implementar un contador automático de productos.

#### Clase Producto

```java
public class Producto {
    private String nombre;
    private String descripcion;
    private Float precio;
    private Integer stock;
    private Date caducidad;
    private Long id;
    public static Long contador = 0L;  // Contador compartido por todas las instancias

    public Producto(String nombre, String descripcion, Float precio, Integer stock, Date caducidad) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.stock = stock;
        this.caducidad = caducidad;
        this.id = ++contador;  // Asigna un ID único incrementando el contador
    }

    @Override
    public String toString() {
        return nombre + " " + descripcion + " " + precio + " " + stock + " " + caducidad + " " + id;
    }
}
```

**Conceptos demostrados:**

- ✅ Variable estática `contador` compartida por todas las instancias
- ✅ Auto-incremento del contador en el constructor
- ✅ Uso de `toString()` para representación de objetos
- ✅ Cada producto obtiene automáticamente un ID único

#### Uso en Main

```java
System.out.println(Producto.contador);  // 0

Producto p1 = new Producto("Alpargatas", "Alpargatas negras", 100.0F, 1000, null);
System.out.println(Producto.contador);  // 1
System.out.println(p1);  // Llama automáticamente a toString()

Producto p2 = new Producto("Croquetas", "Croquetas de la iaia", 10.0F, 1_000_000_000, null);
System.out.println(Producto.contador);  // 2
System.out.println(p2);
```

#### Entrada por consola con Scanner

En la demo se añadió interacción con el usuario para crear productos por consola usando `Scanner`:

```java
Scanner lector = new Scanner(System.in);

System.out.print("Introduce un producto (nombre): ");
String lecturaNombre = lector.nextLine();

System.out.print("Introduce un producto (descripcion): ");
String lecturaDescripcion = lector.nextLine();

System.out.print("Introduce un producto (precio): ");
Float lecturaPrecio = lector.nextFloat();

System.out.print("Introduce un producto (stock): ");
Integer lecturaStock = lector.nextInt();

Producto p1 = new Producto(
    lecturaNombre,
    lecturaDescripcion,
    lecturaPrecio,
    lecturaStock,
    null
);

System.out.println(p1);
```

## 7. Comparación: Sesión 02 vs Sesión 03

| Aspecto                     | Sesión 02                           | Sesión 03                                   |
| --------------------------- | ----------------------------------- | ------------------------------------------- |
| **Modificadores de acceso** | Mezcla de public y private          | Todos los atributos privados                |
| **Métodos de información**  | `muestrate()` acoplado a System.out | `getInformation()` o `toString()`           |
| **Lógica de negocio**       | Fuera de la clase (usar setters)    | Dentro de la clase (Experto de Información) |
| **Reutilización de código** | Código repetido                     | Funciones extraídas (DRY)                   |
| **Variables de clase**      | No se usaron                        | static para contadores                      |
| **Entrada por consola**     | No se trabajó                       | Scanner para crear productos                |

## Resumen

En esta sesión aprendimos:

- ✅ **Encapsulamiento**: Proteger datos usando modificadores de acceso `private` y `public`
- ✅ **Getters y Setters**: Acceso controlado a atributos privados con validación
- ✅ **Experto de Información**: Las clases deben contener la lógica que opera sobre sus propios datos
- ✅ **Bajo Acoplamiento**: Evitar dependencias innecesarias (como System.out) para mayor reutilización
- ✅ **DRY**: Extraer código repetido en funciones reutilizables
- ✅ **toString()**: Método estándar de Java para representación de objetos
- ✅ **static**: Variables y métodos que pertenecen a la clase, no a instancias individuales
- ✅ **Scanner**: Entrada por consola para crear productos en tiempo real
- ✅ Tres proyectos prácticos: Pokédex, Gimnasio mejorado, y Mercado

## Recursos Adicionales

- [Documentación oficial de Java - Encapsulamiento](https://docs.oracle.com/javase/tutorial/java/javaOO/accesscontrol.html)
- [Principios GRASP](<https://en.wikipedia.org/wiki/GRASP_(object-oriented_design)>) - Information Expert y Low Coupling
- [Don't Repeat Yourself (DRY)](https://en.wikipedia.org/wiki/Don%27t_repeat_yourself)
- [Static en Java](https://docs.oracle.com/javase/tutorial/java/javaOO/classvars.html)
- [El método toString()](https://docs.oracle.com/javase/8/docs/api/java/lang/Object.html#toString--)
