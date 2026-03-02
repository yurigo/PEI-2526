# Sesión 09 - Interfaces en Java

**Fecha:** 2 de marzo de 2026

## Contenidos de la Sesión

En esta sesión hemos introducido las **interfaces** como mecanismo para separar el **qué** (contrato que debe cumplir una clase) del **cómo** (implementación concreta). Hemos visto el patrón **DAO**, la interfaz `List` de la librería estándar y hemos construido varias implementaciones de `MenuInterface`.

---

### 1. ¿Qué es una Interfaz?

Una **interfaz** en Java es un contrato: define un conjunto de métodos que cualquier clase que quiera «ser» ese tipo debe implementar. La interfaz declara el **qué** (nombres, parámetros y tipo de retorno de los métodos), pero no el **cómo** (el cuerpo de los métodos).

```java
public interface MenuInterface {
    void show(String message);
    String getString();
    Integer getInteger();
}
```

> 💡 Todos los métodos de una interfaz son implícitamente `public` y `abstract`. No es necesario escribir esas palabras clave (aunque puede hacerse).

**Ventajas de usar interfaces:**

| Sin interfaces | Con interfaces |
|----------------|----------------|
| El código depende de una clase concreta (`MenuConsole`) | El código depende de la abstracción (`MenuInterface`) |
| Cambiar la implementación obliga a modificar el `Main` | Cambiar la implementación solo requiere crear una nueva clase |
| Difícil de testear (depende de `System.in`/`System.out`) | Fácil de testear sustituyendo la implementación por un mock |

---

### 2. Sintaxis: `interface` e `implements`

```java
// Declaración de la interfaz
public interface NombreInterface {
    TipoRetorno metodo1(Parametros...);
    TipoRetorno metodo2(Parametros...);
}

// Implementación de la interfaz
public class ClaseConcreta implements NombreInterface {
    @Override
    public TipoRetorno metodo1(Parametros...) {
        // implementación concreta
    }

    @Override
    public TipoRetorno metodo2(Parametros...) {
        // implementación concreta
    }
}
```

Una clase que implementa una interfaz **debe** proporcionar una implementación para **todos** sus métodos; de lo contrario, el compilador dará error (a menos que la clase sea abstracta).

---

### 3. Ejemplo Teórico: Patrón DAO

El patrón **DAO (Data Access Object)** es uno de los usos más habituales de interfaces. Define cómo se accede a los datos sin especificar dónde se guardan.

```
           ┌──────────────────────────────┐
           │         «interface»          │
           │           DAO                │
           │  + getAll(): List<Medida>    │
           │  + save(Medida m): void      │
           │  + delete(int id): void      │
           └──────────────┬───────────────┘
                          │
          ┌───────────────┼──────────────────┐
          │               │                  │
   ┌──────┴──────┐ ┌──────┴──────┐ ┌────────┴────────┐
   │   DAOCSV   │ │  DAOJSON    │ │   DAOMySQL      │
   │ (fichero   │ │ (fichero    │ │ (base de datos) │
   │  CSV)      │ │  JSON)      │ │                 │
   └────────────┘ └────────────┘ └─────────────────┘
```

Gracias a la interfaz, el código principal trabaja siempre con `DAO` y **nunca** con la implementación concreta:

```java
DAO dao = new DAOCSV("medidas.csv");   // hoy guardamos en CSV
// DAO dao = new DAOMySQL("localhost"); // mañana podemos cambiar a MySQL
//                                      // sin tocar el resto del código
List<Medida> medidas = dao.getAll();
```

Este principio se llama **Principio de inversión de dependencias**: depende de abstracciones, no de concreciones.

---

### 4. Ejemplo Práctico: `MenuInterface` y sus Implementaciones

📁 **Código fuente:** [`interface-examples/`](interface-examples/)

#### 4.1. La interfaz `MenuInterface`

```java
public interface MenuInterface {
    public void show(String message);
    public String getString();
    public Integer getInteger();
}
```

Define **tres operaciones** que todo menú debe saber hacer:
- `show(String message)` → mostrar un mensaje al usuario
- `getString()` → leer un texto del usuario
- `getInteger()` → leer un número entero del usuario

#### 4.2. `MenuConsole` — implementación básica por consola

```java
import java.util.Scanner;

public class MenuConsole implements MenuInterface {
    Scanner sc = new Scanner(System.in);

    @Override
    public void show(String message) {
        System.out.println(message);
    }

    @Override
    public String getString() {
        return sc.nextLine();
    }

    @Override
    public Integer getInteger() {
        return sc.nextInt();
    }
}
```

#### 4.3. `MenuColor` — implementación con colores ANSI

```java
public class MenuColor implements MenuInterface {

    public static final String ANSI_RESET  = "\u001B[0m";
    public static final String ANSI_GREEN  = "\u001B[32m";
    // ... más colores

    Scanner sc = new Scanner(System.in);

    @Override
    public void show(String message) {
        System.out.println(ANSI_GREEN + message + ANSI_RESET);  // verde en terminal
    }

    @Override
    public String getString() { return sc.nextLine(); }

    @Override
    public Integer getInteger() { return sc.nextInt(); }
}
```

#### 4.4. `MenuSuperConsole` — implementación decorada

```java
public class MenuSuperConsole implements MenuInterface {
    Scanner sc = new Scanner(System.in);

    @Override
    public void show(String message) {
        System.out.println("***" + message + "***");   // rodea el mensaje con asteriscos
    }

    @Override
    public String getString() {
        System.out.println("***Menu Super Console***");
        return sc.nextLine();
    }

    @Override
    public Integer getInteger() { return sc.nextInt(); }
}
```

#### 4.5. `MenuWindows` — esqueleto para interfaz gráfica

```java
public class MenuWindows implements MenuInterface {
    @Override
    public void show(String message) {
        // abrir una ventana y mostrar el mensaje (pendiente de implementar)
    }
    // ...
}
```

> 💡 Observa que `MenuWindows` ya implementa el contrato aunque la lógica interna no esté completa. El compilador no se queja mientras estén todos los métodos declarados.

#### 4.6. Diagrama de implementaciones

```
                ┌───────────────────────────┐
                │       «interface»         │
                │       MenuInterface       │
                │  + show(String): void     │
                │  + getString(): String    │
                │  + getInteger(): Integer  │
                └──────────────┬────────────┘
                               │ implements
         ┌─────────────────────┼──────────────────────┐
         │                     │                      │
  ┌──────┴──────┐    ┌─────────┴────────┐    ┌────────┴──────────┐
  │ MenuConsole │    │   MenuColor      │    │   MenuWindows     │
  │ (consola   │    │ (colores ANSI)   │    │ (ventana gráfica) │
  │  básica)   │    └──────────────────┘    └───────────────────┘
  └────────────┘
         │ extends
  ┌──────┴──────────┐
  │ MenuSuperConsole│
  │ (decorada ***)  │
  └─────────────────┘
```

#### 4.7. Uso en `Main.java` — cambiar implementación con una sola línea

```java
// La variable es de tipo INTERFAZ; el objeto es de tipo CONCRETO
// MenuInterface menu = new MenuConsole();      // básico
MenuInterface menu = new MenuColor();           // con colores
// MenuInterface menu = new MenuSuperConsole(); // decorado
// MenuInterface menu = new MenuWindows();      // gráfico

menu.show("Hola! Cómo te llamas?");
String nombre = menu.getString();
menu.show("Bienvenido, " + nombre);
menu.show("Cuantos años tienes?");
Integer age = menu.getInteger();
menu.show("Tienes " + age + " años");
```

> ✅ El código de `Main` **no cambia** aunque cambiemos la implementación del menú. Esto es el principio **Abierto/Cerrado (OCP)**: abierto para extensión, cerrado para modificación.

---

### 5. `List` como Interfaz: `ArrayList`, `Vector` y `MySuperList`

La interfaz `java.util.List` es un contrato estándar de Java que define cómo debe comportarse cualquier lista de elementos. `ArrayList` y `Vector` son dos implementaciones concretas de esa misma interfaz.

```java
List<Medida> lista1 = new ArrayList<>();  // implementación con array dinámico
List<Medida> lista2 = new Vector<>();     // implementación sincronizada (thread-safe)
List<Medida> lista3 = new MySuperList();  // ¡nuestra propia implementación!
```

#### 5.1. `MySuperList` — implementando `List` desde cero

Para demostrar que cualquier clase puede implementar `List`, se creó `MySuperList`. Al escribir `implements List`, el compilador exige que **todos** los métodos del contrato estén implementados:

```java
public class MySuperList implements List {
    @Override public int size()            { return 0; }
    @Override public boolean isEmpty()     { return false; }
    @Override public boolean add(Object o) { return false; }
    @Override public Object get(int index) { return null; }
    // ... y todos los demás métodos de la interfaz List
}
```

> 💡 La clase genera un `stub` (esqueleto vacío) de cada método. El IDE puede generarlos automáticamente. Lo importante es ver que **el contrato obliga a implementarlos todos**.

Este ejemplo muestra que `List` es solo un contrato: la clase que lo cumple puede guardar los datos como quiera (en un array, en un fichero, en memoria, en una base de datos…).

---

### 6. Interfaces vs Clases Abstractas

| Aspecto                        | Interfaz (`interface`)            | Clase abstracta (`abstract class`) |
|--------------------------------|-----------------------------------|------------------------------------|
| Puede tener atributos de estado| ❌ Solo constantes (`static final`)| ✅ Sí                              |
| Puede tener constructores      | ❌ No                             | ✅ Sí                              |
| Puede tener métodos con cuerpo | ✅ Solo `default` y `static`      | ✅ Sí                              |
| Una clase puede implementar... | ✅ Múltiples interfaces            | ❌ Solo una clase abstracta        |
| Una clase puede extender...    | ❌ No se extiende, se implementa  | ✅ Una sola superclase             |
| Uso principal                  | Definir un contrato de comportamiento | Compartir implementación parcial |

**Regla práctica:** usa una **interfaz** cuando defines un contrato puro (qué debe hacerse), usa una **clase abstracta** cuando quieres compartir código entre subclases.

---

### 7. Actividad: Sensores Biomètrics con Interfaces

Como actividad se pide recuperar la **Actividad 1 (session05)** y añadirle dos interfaces:

#### 7.1. Interfaz `Menu`

Desacoplar `System.in` y `System.out` del `main`:

```java
public interface Menu {
    void show(String message);
    String readString();
    int readInt();
}
```

**Implementaciones posibles:**
- `MenuConsola` — usa `System.in` / `System.out`
- `MenuColor` — usa colores ANSI en la consola

#### 7.2. Interfaz `DAO`

Desacoplar la lectura/escritura de ficheros del `main`:

```java
public interface MedidaDAO {
    List<Medida> getAll();
    void save(Medida m);
    void saveAll(List<Medida> medidas);
}
```

**Implementaciones posibles:**
- `MedidaDAOCSV` — lee y escribe ficheros CSV (comportamiento actual)
- `MedidaDAOJSON` — lee y escribe ficheros JSON (extensión futura)
- `MedidaDAOMySQL` — accede a una base de datos (extensión futura)

#### 7.3. Resultado esperado

```
          ┌──────────┐         ┌────────────┐
          │  «intf»  │         │   «intf»   │
          │   Menu   │         │ MedidaDAO  │
          └────┬─────┘         └─────┬──────┘
               │                     │
    ┌──────────┴────────┐   ┌────────┴────────┐
    │   MenuConsola     │   │  MedidaDAOCSV   │
    └───────────────────┘   └─────────────────┘

          Main usa Menu y MedidaDAO (nunca las clases concretas)
```

> ✅ Objetivo: que `Main` solo dependa de las interfaces. De este modo se puede cambiar la fuente de datos o la interfaz de usuario sin modificar `Main`.

---

### 8. Resumen de Conceptos

| Concepto                     | Descripción                                                                 | Ejemplo en el código                            |
|------------------------------|-----------------------------------------------------------------------------|-------------------------------------------------|
| **`interface`**              | Contrato que define qué métodos debe tener una clase                        | `MenuInterface`, `List`                         |
| **`implements`**             | Palabra clave para indicar que una clase cumple el contrato de una interfaz | `MenuConsole implements MenuInterface`          |
| **`@Override`**              | Indica que el método implementa (o sobrescribe) uno de la interfaz/superclase| Todos los métodos de `MenuConsole`             |
| **Polimorfismo de interfaz** | La variable es de tipo interfaz; el objeto es de tipo concreto              | `MenuInterface menu = new MenuColor()`          |
| **Patrón DAO**               | Interfaz para acceder a datos independientemente del almacenamiento         | `DAO dao = new DAOCSV()`                        |
| **`List`**                   | Interfaz estándar de Java para listas ordenadas de elementos                | `List<Medida> lista = new ArrayList<>()`        |
| **`MySuperList`**            | Implementación propia de `List` para ilustrar el concepto de contrato       | `implements List` obliga a definir todos los métodos |
| **OCP**                      | Principio Abierto/Cerrado: extensible sin modificar código existente        | Añadir `MenuWindows` sin tocar `Main`           |
| **DIP**                      | Principio de inversión de dependencias: depender de abstracciones           | `Main` usa `MenuInterface`, no `MenuConsole`    |

---

## Recursos Adicionales

- [Interfaces en Java - Oracle Docs](https://docs.oracle.com/javase/tutorial/java/IandI/createinterface.html)
- [Interfaces vs Clases Abstractas - Oracle Docs](https://docs.oracle.com/javase/tutorial/java/IandI/abstract.html)
- [Patrón DAO - Oracle Java EE](https://www.oracle.com/java/technologies/data-access-object.html)
- [La interfaz `List` - Oracle Docs](https://docs.oracle.com/en/java/se/17/docs/api/java.base/java/util/List.html)
- [Principio Abierto/Cerrado (OCP) - Wikipedia](https://es.wikipedia.org/wiki/Principio_de_abierto/cerrado)
- [Principio de Inversión de Dependencias (DIP) - Wikipedia](https://es.wikipedia.org/wiki/Principio_de_inversi%C3%B3n_de_la_dependencia)
- [Interfaces en Java - Baeldung](https://www.baeldung.com/java-interfaces)
- [Patrón DAO en Java - Baeldung](https://www.baeldung.com/java-dao-pattern)