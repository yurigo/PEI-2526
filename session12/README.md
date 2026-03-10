# Sesión 12 – Excepciones Propias en Java

**Fecha:** 10 de marzo de 2026

## Contenidos de la Sesión

En esta sesión hemos profundizado en el sistema de excepciones de Java creando **excepciones propias** (*custom exceptions*): clases que extienden `Exception` (checked) o `RuntimeException` (unchecked). Hemos aprendido a encapsular el mensaje de error dentro de la propia excepción y a construir jerarquías de excepciones para representar distintos tipos de errores del dominio.

---

## 1. Repaso: Checked vs Unchecked

| Tipo          | Clase base          | El compilador obliga a tratarla | Cuándo usarla                                     |
|---------------|---------------------|---------------------------------|---------------------------------------------------|
| **Checked**   | `Exception`         | ✅ Sí                           | Errores recuperables: E/S, red, formato de datos… |
| **Unchecked** | `RuntimeException`  | ❌ No                           | Errores de programación o condiciones inesperadas |

> 💡 Si un método puede lanzar una excepción **checked**, debe declararlo en su firma con `throws` o capturarla con `try/catch`. Con las **unchecked** no hay obligación.

```java
// Checked: el compilador obliga a declarar o capturar
public void leerFichero(String ruta) throws IOException {
    // ...
}

// Unchecked: no hay obligación, pero puede lanzarse en tiempo de ejecución
public void dividir(int a, int b) {
    if (b == 0) throw new ArithmeticException("División entre cero");
}
```

---

## 2. Crear Excepciones Propias

### 2.1. ¿Por qué crear excepciones propias?

Las excepciones propias permiten:

- **Dar nombres significativos** a los errores del dominio (`MoreThanTenException` es más claro que `Exception("mayor que 10")`).
- **Encapsular el mensaje** de error dentro de la clase, de modo que el lanzador no necesita conocer el texto concreto.
- **Construir jerarquías** para capturar grupos de errores con un solo `catch`.

### 2.2. Jerarquía implementada en el `exceptional-project`

```
Exception
└── MyExceptionalException          ← checked (extiende Exception)
    ├── MoreThanTenException         ← checked (extiende MyExceptionalException)
    └── LessThanZeroException        ← checked (extiende MyExceptionalException)

RuntimeException
└── NumberFiveException              ← unchecked (extiende RuntimeException)
```

📁 Código del proyecto: [`exceptional-project/`](exceptional-project/)

---

## 3. Código de las Excepciones

### 3.1. `MyExceptionalException` (checked base)

📁 [`exceptional-project/MyExceptionalException.java`](exceptional-project/MyExceptionalException.java)

```java
public class MyExceptionalException extends Exception {

    public MyExceptionalException() {
        super();
    }

    public MyExceptionalException(String message) {
        super(message);
    }
}
```

**Puntos clave:**
- Extiende `Exception` → es **checked**: el compilador obliga a declararla o capturarla.
- Ofrece dos constructores: uno sin mensaje y otro con mensaje personalizado.
- Se llama a `super(message)` para que el mensaje quede almacenado en la clase base `Throwable` y sea accesible mediante `e.getMessage()`.

---

### 3.2. `MoreThanTenException` (checked)

📁 [`exceptional-project/MoreThanTenException.java`](exceptional-project/MoreThanTenException.java)

```java
public class MoreThanTenException extends MyExceptionalException {

    public MoreThanTenException() {
        super("El numero no puede ser mayor a 10");
    }
}
```

**Puntos clave:**
- Extiende `MyExceptionalException` → sigue siendo **checked**.
- El mensaje de error está **encapsulado** en el constructor: quien lanza la excepción no necesita escribir el texto, simplemente hace `throw new MoreThanTenException()`.
- Se puede capturar con `catch (MoreThanTenException e)` o con el `catch (MyExceptionalException e)` más general.

---

### 3.3. `LessThanZeroException` (checked)

📁 [`exceptional-project/LessThanZeroException.java`](exceptional-project/LessThanZeroException.java)

```java
public class LessThanZeroException extends MyExceptionalException {
    public LessThanZeroException() {
        super("El numero no puede ser menor a 0");
    }
}
```

Misma estructura que `MoreThanTenException` pero con un mensaje diferente. Al compartir la misma clase base (`MyExceptionalException`) se pueden capturar ambas juntas si es necesario.

---

### 3.4. `NumberFiveException` (unchecked)

📁 [`exceptional-project/NumberFiveException.java`](exceptional-project/NumberFiveException.java)

```java
public class NumberFiveException extends RuntimeException {
    public NumberFiveException() {
        super("El numero no puede ser 5");
    }
}
```

**Diferencia clave:**
- Extiende `RuntimeException` → es **unchecked**.
- El compilador **no obliga** a declararla con `throws` ni a capturarla.
- Si no se captura, la excepción sube por la pila de llamadas hasta llegar al `Main`.

---

## 4. El `Controller`: Lanzar Excepciones

📁 [`exceptional-project/Controller.java`](exceptional-project/Controller.java)

```java
public class Controller {

    Menu menu;

    public Controller(Menu menu) {
        this.menu = menu;
    }

    public void run() throws MyExceptionalException, Exception {
        this.menu.show("Hola!!");

        String numeroText = this.menu.getString("Dame un numero del 1 a 10");

        Integer numero = Integer.parseInt(numeroText);

        if (numero > 10) {
            throw new MoreThanTenException();
        } else if (numero < 1) {
            throw new LessThanZeroException();
        } else if (numero == 5) {
            throw new NumberFiveException();
        } else if (numero == 6) {
            throw new RuntimeException("El numero no puede ser 6");
        } else if (numero == 7) {
            throw new Exception("El numero no puede ser 7");
        }

        this.menu.show(numero.toString());
    }
}
```

**Aspectos importantes:**

1. **`throws MyExceptionalException, Exception`** en la firma: el compilador sabe que este método puede lanzar estas excepciones checked y obliga al llamador a tratarlas.
2. **`MoreThanTenException` y `LessThanZeroException`** son subclases de `MyExceptionalException`, por lo que con declarar `throws MyExceptionalException` sería suficiente, pero se declaran explícitamente para mayor claridad.
3. **`NumberFiveException`** no necesita declararse en `throws` porque es unchecked (extiende `RuntimeException`).
4. **`Integer.parseInt(numeroText)`** puede lanzar una `NumberFormatException` (unchecked) si el texto no es un número válido.

---

## 5. El `Main`: Capturar Excepciones en Cascada

📁 [`exceptional-project/Main.java`](exceptional-project/Main.java)

```java
public class Main {

    public static void main(String[] args) {

        Menu menu = new MenuConsole();
        Controller controller = new Controller(menu);

        for (;;) {
            try {
                controller.run();
            } catch (MyExceptionalException e) {
                menu.show("My exceptional exception: " + e.getMessage());
            } catch (NumberFormatException e) {
                menu.show(":( " + e.getMessage());
            } catch (Exception e) {
                menu.show("Excepcion global: " + e.getMessage());
            }
        }
    }
}
```

**Flujo de captura:**

```
controller.run() lanza...
│
├── MoreThanTenException    ──→ catch (MyExceptionalException e)  ← capturada aquí
├── LessThanZeroException   ──→ catch (MyExceptionalException e)  ← capturada aquí
├── NumberFiveException     ──→ catch (Exception e)               ← capturada aquí (es RuntimeException, que extiende Exception)
├── RuntimeException("6")   ──→ catch (Exception e)               ← capturada aquí
├── Exception("7")          ──→ catch (Exception e)               ← capturada aquí
└── NumberFormatException   ──→ catch (NumberFormatException e)   ← capturada aquí (antes del genérico)
```

> 💡 **Orden de los `catch`**: siempre de más específico a más general. Si `catch (Exception e)` fuera el primero, capturaría **todas** las excepciones y los bloques siguientes nunca se ejecutarían. El compilador lanza un error si detecta esta situación con excepciones checked.

**El bucle `for(;;)`** mantiene la aplicación en marcha aunque se lance una excepción: después de mostrar el error, vuelve a llamar a `controller.run()`.

---

## 6. Encapsulación del Mensaje de Error

Una de las ideas clave de la sesión es **encapsular el mensaje dentro de la excepción**:

```java
// ❌ Sin encapsulación: el lanzador decide el mensaje
throw new MyExceptionalException("El numero no puede ser mayor a 10");

// ✅ Con encapsulación: el mensaje está en la propia clase
throw new MoreThanTenException();
// → el mensaje "El numero no puede ser mayor a 10" vive en MoreThanTenException
```

**Ventajas:**
- El código del `Controller` queda limpio: `throw new MoreThanTenException()`.
- El mensaje es consistente en toda la aplicación (no depende de que cada lanzador escriba el mismo texto).
- Si se necesita cambiar el mensaje, solo hay que modificar la clase de la excepción.

---

## 7. Cuándo Extender `Exception` vs `RuntimeException`

| Situación                                                                                           | Extiende               |
|-----------------------------------------------------------------------------------------------------|------------------------|
| Error que el llamador **puede y debe recuperar** (p. ej. valor fuera de rango en un formulario)    | `Exception` (checked)  |
| Error de **programación** o condición que nunca debería ocurrir (p. ej. argumento nulo no permitido)| `RuntimeException` (unchecked) |
| **Regla general**: si el llamador puede hacer algo útil ante el error                               | `Exception`            |
| **Regla general**: si el error indica un bug o es irrecuperable                                     | `RuntimeException`     |

En el proyecto de esta sesión:
- `MoreThanTenException` y `LessThanZeroException` son **checked** porque representan entradas inválidas del usuario que el programa puede gestionar mostrando un mensaje y volviendo a pedir el número.
- `NumberFiveException` es **unchecked** porque se usa como ejemplo de excepción de dominio que el programa decide no recuperar desde el `Controller` (se deja subir hasta el `Main`).

---

## 8. Resumen de Conceptos

| Concepto                          | Descripción                                                                                    |
|-----------------------------------|------------------------------------------------------------------------------------------------|
| **Excepción propia (checked)**    | Clase que extiende `Exception`; el compilador obliga a declararla o capturarla                 |
| **Excepción propia (unchecked)**  | Clase que extiende `RuntimeException`; no hay obligación de declararla o capturarla            |
| **Encapsulación del mensaje**     | El constructor de la excepción llama a `super(mensaje)` para fijar el texto de error           |
| **Jerarquía de excepciones**      | Permite capturar un grupo de excepciones con un solo `catch` de la clase base                  |
| **`throws` en la firma**          | Declara qué excepciones checked puede lanzar un método                                         |
| **Orden de `catch`**              | De más específico a más general; el compilador avisa si un `catch` es inalcanzable             |
| **Bucle de reinicio**             | `for(;;)` en `Main` para que la aplicación continúe tras capturar una excepción                |

---

## 9. Ejemplos Adicionales

### 9.1. Capturar la clase base para tratar varias excepciones a la vez

```java
try {
    controller.run();
} catch (MyExceptionalException e) {
    // Captura tanto MoreThanTenException como LessThanZeroException
    System.out.println("Error de dominio: " + e.getMessage());
}
```

### 9.2. Añadir campos extra a una excepción propia

```java
public class ValorFueraDeRangoException extends Exception {
    private final int valorRecibido;

    public ValorFueraDeRangoException(int valor) {
        super("Valor fuera de rango: " + valor);
        this.valorRecibido = valor;
    }

    public int getValorRecibido() {
        return valorRecibido;
    }
}

// Uso:
throw new ValorFueraDeRangoException(42);

// En el catch:
catch (ValorFueraDeRangoException e) {
    System.out.println(e.getMessage());                   // "Valor fuera de rango: 42"
    System.out.println("Valor: " + e.getValorRecibido()); // 42
}
```

### 9.3. Relanzar una excepción envuelta (*exception chaining*)

```java
try {
    Integer.parseInt("abc");
} catch (NumberFormatException e) {
    // Envolvemos la excepción de bajo nivel en una de nuestro dominio
    throw new MyExceptionalException("Entrada no válida: " + e.getMessage());
}
```

---

## 10. Buenas Prácticas

1. **Nombra tus excepciones con el sufijo `Exception`** para que sean fácilmente identificables (`MoreThanTenException`, `ValorFueraDeRangoException`…).
2. **Encapsula el mensaje** dentro del constructor: menos código en el lanzador, mayor consistencia.
3. **Usa jerarquías** cuando tengas varios tipos de error relacionados; te permite capturarlos de forma granular o en bloque según convenga.
4. **No abuses de las unchecked**: reserva `RuntimeException` para errores de programación, no para validaciones de negocio que el usuario puede corregir.
5. **Añade información de contexto** a la excepción (campos extra como `getValorRecibido()`) cuando el llamador necesite más que solo el mensaje de texto.

---

## Recursos Adicionales

- [Creating Exception Classes – The Java Tutorials (Oracle)](https://docs.oracle.com/javase/tutorial/essential/exceptions/creating.html)
- [Checked vs Unchecked Exceptions – Baeldung](https://www.baeldung.com/java-checked-unchecked-exceptions)
- [Exception Handling Best Practices – Baeldung](https://www.baeldung.com/java-exceptions)
- [Throwable – Java SE 17 API](https://docs.oracle.com/en/java/se/17/docs/api/java.base/java/lang/Throwable.html)
- [RuntimeException – Java SE 17 API](https://docs.oracle.com/en/java/se/17/docs/api/java.base/java/lang/RuntimeException.html)
- [Sesión 11 – Excepciones en Java](../session11/README.md)

