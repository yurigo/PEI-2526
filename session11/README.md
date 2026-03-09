# Sesión 11 – Excepciones en Java

**Fecha:** 9 de marzo de 2026

## Contenidos de la Sesión

En esta sesión se ha dedicado tiempo a avanzar en la **Actividad AC2** (ver [session10/ACTIVIDAD.md](../session10/ACTIVIDAD.md)) y se ha introducido el concepto de **excepciones** en Java: qué son, cómo capturarlas y cómo usarlas para hacer el código más robusto.

---

### 1. ¿Qué es una Excepción?

Una **excepción** es un evento que interrumpe el flujo normal de ejecución de un programa cuando algo inesperado sucede: se intenta leer un entero y el usuario escribe texto, se intenta abrir un fichero que no existe, se divide entre cero, etc.

Java representa estos eventos como objetos de la jerarquía `Throwable`:

```
Throwable
├── Error              ← Errores graves del sistema (no se capturan normalmente)
└── Exception
    ├── IOException    ← Checked: el compilador obliga a capturarlas
    ├── SQLException   ← Checked
    └── RuntimeException  ← Unchecked: no obliga el compilador, pero pueden lanzarse
        ├── NullPointerException
        ├── ArrayIndexOutOfBoundsException
        ├── NumberFormatException
        └── IllegalArgumentException
```

> 💡 **Checked vs Unchecked:**
> - **Checked exceptions** (`IOException`, `SQLException`…): el compilador te **obliga** a tratarlas con `try/catch` o a declarar `throws` en la firma del método.
> - **Unchecked exceptions** (`RuntimeException` y sus subclases): el compilador **no** obliga a capturarlas. Si no las captures y se lanzan, el programa termina con un mensaje de error en consola.

---

### 2. Sintaxis `try / catch / finally`

```java
try {
    // código que puede fallar
} catch (TipoDeExcepcion e) {
    // qué hacer si falla
} finally {
    // se ejecuta SIEMPRE, haya excepción o no
}
```

| Bloque      | Cuándo se ejecuta                                                   |
|-------------|---------------------------------------------------------------------|
| `try`       | Siempre, al inicio                                                  |
| `catch`     | Solo si se lanza una excepción del tipo indicado dentro del `try`   |
| `finally`   | **Siempre**, incluso si hay un `return` o un `throw` dentro del `try`/`catch` |

> ⚠️ `finally` se ejecuta **siempre**. Esto es crucial: un `return` dentro de `catch` no evita que `finally` se ejecute antes de salir del método.

#### Ejemplo básico

```java
try {
    int resultado = 10 / 0;   // lanza ArithmeticException
} catch (ArithmeticException e) {
    System.out.println("Error: " + e.getMessage());   // "/ by zero"
} finally {
    System.out.println("Esto se ejecuta siempre");
}
```

Salida:
```
Error: / by zero
Esto se ejecuta siempre
```

#### Capturar múltiples tipos de excepción

```java
try {
    // código que puede lanzar distintas excepciones
} catch (NumberFormatException e) {
    System.out.println("Número no válido: " + e.getMessage());
} catch (IOException e) {
    System.out.println("Error de E/S: " + e.getMessage());
} catch (Exception e) {
    System.out.println("Error inesperado: " + e.getMessage());
}
```

> 💡 Los bloques `catch` se evalúan **en orden**. Coloca siempre los tipos más específicos antes que los más generales.

---

### 3. Aplicación Práctica: `getInteger()` en el Menú

El problema concreto que se planteó en esta sesión fue: ¿qué hace `getInteger()` si el usuario escribe texto en lugar de un número entero?

`Scanner.nextInt()` lanza una `InputMismatchException` si la entrada no es un entero. Hay que decidir cómo recuperarse de ese error.

Se discutieron **dos aproximaciones**, ambas disponibles en el boilerplate de la actividad:

---

#### 3.1. Aproximación A – `MenuConsole`: devolver un valor centinela

📁 **Código:** [`partial-solution-sensors-biometrics/MenuConsole.java`](partial-solution-sensors-biometrics/MenuConsole.java)

```java
@Override
public Integer getInteger() {
    try {
        return sc.nextInt();
    } catch (Exception e) {
        // El usuario ha escrito texto; devolvemos un valor especial
        return -99999;
    } finally {
        // finally se ejecuta siempre antes de salir del método,
        // aunque haya un return en try o catch.
        // Limpiamos el buffer del scanner para poder leer la siguiente línea.
        show("esto está dentro del finally");
        sc.nextLine();
    }
}
```

**¿Por qué se usa `sc.nextLine()` en el `finally`?**

Cuando `sc.nextInt()` falla, el texto incorrecto sigue en el buffer del `Scanner`. Si no lo limpiamos, la siguiente llamada a `nextLine()` o `nextInt()` leerá ese texto basura y provocará nuevos errores. El bloque `finally` garantiza que la limpieza se hace **siempre**, tanto si hay excepción como si no.

**Problema de esta solución:** el valor `-99999` es un "valor centinela": un número mágico que indica error. El código del `Controller` (que llama a `getInteger()`) debe saber que `-99999` significa "entrada inválida" y tratarlo correctamente:

```java
switch (option) {
    case 1  -> doOption1();
    // ...
    case 0  -> askAgain = false;
    default -> iDontUnderstand();  // aquí llega si option == -99999
}
```

El `default` del `switch` recoge el `-99999` y muestra un mensaje de error. Para **este contexto** (seleccionar una opción de menú) funciona, pero si se usase `getInteger()` para, por ejemplo, pedir la edad del usuario, `-99999` sería una edad absurda que no indicaría que hubo un error tipográfico.

> ⚠️ **Conclusión:** esta solución es válida **solo** en contextos donde el llamador controla el valor devuelto. Fuera de ese contexto puede provocar errores sutiles difíciles de depurar.

---

#### 3.2. Aproximación B – `MenuConsoleSuperior`: reintentar hasta obtener un entero válido

📁 **Código:** [`partial-solution-sensors-biometrics/MenuConsoleSuperior.java`](partial-solution-sensors-biometrics/MenuConsoleSuperior.java)

```java
@Override
public Integer getInteger() {
    for (;;) {           // bucle infinito: solo sale cuando el usuario introduce un entero
        try {
            return sc.nextInt();
        } catch (Exception e) {
            showError("Oye, que un número, ¿no te parece?");
        } finally {
            sc.nextLine();   // limpia el buffer siempre
        }
    }
}
```

**¿Por qué es mejor esta solución?**

- El método **nunca devuelve un valor inválido**: o devuelve un entero correcto, o sigue preguntando.
- Se puede reutilizar en **cualquier contexto** (pedir edad, pedir cantidad, pedir ID…) sin que el llamador tenga que preocuparse de valores centinela.
- La responsabilidad de la validación queda **encapsulada** en la capa de vista (`Menu`), que es quien sabe cómo leer datos del usuario.

Diagrama de flujo del bucle:

```
 ┌──────────────────────────────────┐
 │       getInteger() llamado       │
 └────────────────┬─────────────────┘
                  │
            ┌─────▼─────┐
            │ sc.nextInt │
            └──────┬─────┘
           éxito  │  excepción
          ┌───────┘  └───────────────────────┐
          │                                  │
   ┌──────▼──────┐               ┌───────────▼───────────┐
   │ return int  │               │  showError("¡Número!") │
   └─────────────┘               └───────────┬───────────┘
                                             │ finally: sc.nextLine()
                                             │
                                     ┌───────▼──────┐
                                     │  volver al   │
                                     │  inicio del  │
                                     │  for(;;)     │
                                     └──────────────┘
```

---

### 4. `RuntimeException`: Lanzar Excepciones

Además de capturar excepciones, Java permite **lanzarlas** con la palabra clave `throw`. Esto es útil cuando quieres señalar que algo no está implementado aún o que se ha producido un estado inválido.

📁 **Código:** [`partial-solution-sensors-biometrics/Controller.java`](partial-solution-sensors-biometrics/Controller.java)

En la opción 2 del menú se lanza deliberadamente una `RuntimeException` para indicar que esa opción no está implementada todavía:

```java
private void doOption2() {
    menu.show("has escogido la opcion 2");
    throw new RuntimeException("la opcion 2 no está implementada todavía!");
}
```

Esta excepción **no** es capturada dentro del `Controller`, así que sube por la pila de llamadas hasta llegar a `Main`:

```java
// Main.java
try {
    c.run();
} catch (Exception e) {
    menu.showError(e.getMessage());   // muestra: "la opcion 2 no está implementada todavía!"
}
```

Esto es un patrón habitual durante el desarrollo: **stub de funcionalidad**. El método existe, compila y puede ser invocado, pero avisa al desarrollador (o al usuario) de que aún no está listo.

> 💡 La creación de **excepciones propias** (clases que extienden `Exception` o `RuntimeException`) se trabajará en la siguiente sesión.

---

### 5. Arquitectura del Boilerplate (AC2)

El código de esta sesión está dentro del directorio [`partial-solution-sensors-biometrics/`](partial-solution-sensors-biometrics/) y sigue la arquitectura en capas introducida en la sesión 10:

```
┌────────────────────────────────────────────────┐
│                     Main                       │
│  - crea Menu y DAOMedida                       │
│  - inyecta dependencias en Controller          │
│  - envuelve controller.run() en try/catch      │
└────────────────────────┬───────────────────────┘
                         │ usa
          ┌──────────────▼──────────────┐
          │          Controller         │
          │  - orquesta el flujo        │
          │  - llama a menu y dao       │
          │  - lanza RuntimeException   │
          └────────┬────────────────────┘
                   │ usa
     ┌─────────────┼──────────────────┐
     │             │                  │
┌────▼────┐  ┌─────▼─────┐  ┌────────▼──────┐
│  Menu   │  │  DAOMedida │  │    Medida     │
│(interfaz│  │ (interfaz) │  │   (modelo)    │
└────┬────┘  └─────┬──────┘  └───────────────┘
     │              │
┌────┴──────────┐  ┌┴──────────────────┐
│ MenuConsole   │  │ DAOMedidaCSVFile   │
│ (o Superior)  │  │                   │
└───────────────┘  └───────────────────┘
```

**Clases del boilerplate:**

| Clase / Interfaz          | Capa       | Responsabilidad                                      |
|---------------------------|------------|------------------------------------------------------|
| `Main`                    | Arranque   | Inyectar dependencias y arrancar la aplicación       |
| `Controller`              | Control    | Orquestar el flujo de la aplicación                  |
| `Menu`                    | Vista      | Interfaz de entrada/salida de usuario                |
| `MenuConsole`             | Vista      | Implementación básica (valor centinela en error)     |
| `MenuConsoleSuperior`     | Vista      | Implementación robusta (bucle de reintento + colores)|
| `DAOMedida`               | Datos      | Interfaz de acceso a datos de medidas                |
| `DAOMedidaCSVFile`        | Datos      | Implementación DAO sobre fichero CSV                 |
| `Medida`                  | Modelo     | Entidad que representa una medida biométrica         |

---

### 6. Conceptos Clave Resumidos

| Concepto                    | Descripción                                                                          |
|-----------------------------|--------------------------------------------------------------------------------------|
| **Excepción**               | Evento que interrumpe el flujo normal; representado como un objeto en Java           |
| **`try`**                   | Bloque que envuelve el código que puede fallar                                       |
| **`catch`**                 | Bloque que se ejecuta solo si se lanza una excepción del tipo indicado               |
| **`finally`**               | Bloque que se ejecuta **siempre**, independientemente del flujo del `try`/`catch`    |
| **`throw`**                 | Palabra clave para lanzar una excepción manualmente                                  |
| **`RuntimeException`**      | Excepción no verificada; útil para señalar errores de programación o stubs           |
| **Valor centinela**         | Valor especial (p. ej. `-99999`) que indica una condición de error; desaconsejado    |
| **Bucle de reintento**      | Patrón que repite la solicitud hasta obtener entrada válida; más robusto              |
| **Limpieza del buffer**     | `sc.nextLine()` en `finally` para descartar la entrada inválida del `Scanner`        |

---

### 7. Ejemplos de Código Adicionales

#### 7.1. `try/catch/finally` con `return` (comportamiento del `finally`)

```java
public static String ejemploFinally() {
    try {
        System.out.println("try");
        return "desde try";         // ① se prepara el return…
    } catch (Exception e) {
        System.out.println("catch");
        return "desde catch";
    } finally {
        System.out.println("finally"); // ② …pero finally se ejecuta ANTES de que el return salga
    }
}
```

Salida:
```
try
finally
```
Valor devuelto: `"desde try"`

---

#### 7.2. Captura de `InputMismatchException` con `Scanner`

```java
import java.util.InputMismatchException;
import java.util.Scanner;

Scanner sc = new Scanner(System.in);
int numero = 0;

try {
    numero = sc.nextInt();
} catch (InputMismatchException e) {
    System.out.println("Eso no es un número entero.");
    sc.nextLine(); // limpiar el buffer
}
```

---

#### 7.3. Lanzar una `RuntimeException` desde un método

```java
public void procesoNoImplementado() {
    throw new RuntimeException("Este proceso no está implementado todavía.");
}

// En el llamador:
try {
    procesoNoImplementado();
} catch (RuntimeException e) {
    System.out.println("Error capturado: " + e.getMessage());
}
```

---

### 8. Buenas Prácticas con Excepciones

1. **Captura excepciones específicas** antes que `Exception` genérica. Así sabes exactamente qué falló.
2. **No silencies excepciones** con un `catch` vacío: al menos registra el error con `e.getMessage()` o `e.printStackTrace()`.
3. **Usa `finally`** para liberar recursos (ficheros, conexiones) que deban cerrarse aunque haya error.
4. **No uses excepciones para el flujo normal** del programa. Las excepciones son para situaciones excepcionales, no para control de flujo habitual.
5. **El bucle de reintento** (`for(;;)` con `try/catch` dentro) es una buena estrategia para leer datos del usuario hasta que sean válidos.

---

## Recursos Adicionales

- [Exceptions – The Java Tutorials (Oracle)](https://docs.oracle.com/javase/tutorial/essential/exceptions/)
- [Try-catch-finally – Oracle Docs](https://docs.oracle.com/javase/tutorial/essential/exceptions/handling.html)
- [RuntimeException – Java SE 17 API](https://docs.oracle.com/en/java/se/17/docs/api/java.base/java/lang/RuntimeException.html)
- [Scanner – InputMismatchException (Oracle)](https://docs.oracle.com/en/java/se/17/docs/api/java.base/java/util/InputMismatchException.html)
- [Exception Handling Best Practices – Baeldung](https://www.baeldung.com/java-exceptions)
- [Checked vs Unchecked Exceptions – Baeldung](https://www.baeldung.com/java-checked-unchecked-exceptions)
- [AC2 – Enunciado de la Actividad](../session10/ACTIVIDAD.md)

