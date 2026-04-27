# Sesión 17 – Threads en Java

**Fecha:** 27 de abril de 2026

## Contenidos de la Sesión

La dinámica de esta sesión ha sido una **flipped classroom**: los alumnos han investigado por su cuenta el tema de los Threads en Java y luego se ha puesto en común y debatido en clase. Se ha pedido además que identifiquen un **caso de uso real dentro de su proyecto final** ([laSallefy](../session13/ENUNCIADO.md)) donde aplicar Threads.

Los temas presentados en clase han sido:

- ¿Qué es un Thread y para qué sirve?
- Ejecución de tareas en paralelo
- Mejorar el rendimiento de las aplicaciones
- Condiciones de carrera (*race conditions*)
- Sincronización de Threads

📁 Ejemplos de la sesión: [`thread-examples/`](thread-examples/)

---

## 1. ¿Qué es un Thread?

Un **Thread** (hilo de ejecución) es la unidad más pequeña de procesamiento que puede gestionar un sistema operativo. Dentro de un programa Java, cada Thread ejecuta su propio flujo de instrucciones de forma **independiente** pero **compartiendo** la memoria del proceso.

Por defecto, un programa Java arranca con un único Thread: el llamado **main thread**. Cuando se crean Threads adicionales, el procesador los ejecuta de forma **concurrente** (alternando en un solo núcleo) o **en paralelo** (simultáneamente en varios núcleos).

```
┌───────────────────────────────────────┐
│  Proceso Java (JVM)                   │
│                                       │
│  Thread main  ──► instrucciones A     │
│  Thread 1     ──► instrucciones B     │
│  Thread 2     ──► instrucciones C     │
│                                       │
│   Memoria compartida (heap)           │
└───────────────────────────────────────┘
```

### ¿Para qué sirven los Threads?

| Caso de uso                         | Ejemplo                                                    |
|-------------------------------------|------------------------------------------------------------|
| Ejecución de tareas en paralelo     | Descargar varios ficheros al mismo tiempo                  |
| Mantener la UI responsiva           | Reproducir música mientras el usuario navega por el menú  |
| Mejorar el rendimiento              | Procesar secciones de un array en paralelo                 |
| Tareas programadas en segundo plano | Enviar logs, hacer backups periódicos                      |

---

## 2. Crear Threads en Java

Hay **dos formas principales** de crear un Thread en Java:

### 2.1. Extendiendo la clase `Thread`

Se crea una clase que **extiende** `Thread` y se sobreescribe el método `run()`. Para lanzar el hilo se llama a `start()` (nunca directamente a `run()`).

📁 [`thread-examples/01-extends-thread/`](thread-examples/01-extends-thread/)

```java
public class MyThread extends Thread {
    @Override
    public void run() {
        System.out.println("Hello from a thread!");
    }
}
```

```java
public class Main {
    public static void main(String[] args) {
        MyThread thread = new MyThread();
        thread.start();  // lanza el hilo; NO llamar a run() directamente
    }
}
```

> ⚠️ **`start()` vs `run()`**: llamar a `run()` directamente **no** crea un nuevo hilo; simplemente ejecuta el método en el Thread actual, como cualquier otra llamada a un método.

#### Con parámetros (pasarlos por el constructor)

```java
public class PrinterThread extends Thread {
    private final String message;

    public PrinterThread(String message) {
        this.message = message;
    }

    @Override
    public void run() {
        System.out.println("Thread dice: " + message);
    }
}
```

```java
public class Main {
    public static void main(String[] args) {
        new PrinterThread("Hola!").start();
        new PrinterThread("Adios!").start();
    }
}
```

---

### 2.2. Implementando la interfaz `Runnable`

Se crea una clase que **implementa** la interfaz `Runnable` (solo tiene el método `run()`). Después se envuelve en un objeto `Thread` y se llama a `start()`.

📁 [`thread-examples/02-implements-runnable/`](thread-examples/02-implements-runnable/)

```java
public class MyRunnable implements Runnable {
    @Override
    public void run() {
        System.out.println("Hello from a runnable!");
    }
}
```

```java
public class Main {
    public static void main(String[] args) {
        Thread thread = new Thread(new MyRunnable());
        thread.start();
    }
}
```

#### Con expresión lambda (Java 8+)

Como `Runnable` es una **interfaz funcional** (un solo método abstracto), se puede usar una **lambda**:

```java
public class Main {
    public static void main(String[] args) {
        Thread thread = new Thread(() -> System.out.println("Hello from a lambda!"));
        thread.start();
    }
}
```

---

### 2.3. ¿Cuándo usar `extends Thread` vs `implements Runnable`?

| Criterio                      | `extends Thread`                       | `implements Runnable`                   |
|-------------------------------|----------------------------------------|-----------------------------------------|
| Herencia disponible           | Consume la única herencia de la clase  | ✅ Deja libre la herencia               |
| Reutilización                 | El objeto Thread y la tarea son uno    | ✅ La tarea (`Runnable`) es independiente |
| Uso con lambdas               | ❌ No aplica                            | ✅ Sí (interfaz funcional)              |
| **Recomendado en la práctica**| Casos muy simples                      | ✅ Preferido en general                 |

> 💡 **Buena práctica**: usa `implements Runnable` (o lambda) salvo que necesites sobreescribir otros métodos de `Thread`.

---

## 3. Condiciones de Carrera (*Race Conditions*)

Una **condición de carrera** ocurre cuando dos o más Threads acceden simultáneamente a un recurso compartido (p. ej., una variable) y el resultado final depende del orden impredecible en que se ejecutan.

📁 [`thread-examples/03-race-condition/`](thread-examples/03-race-condition/)

### Ejemplo: contador sin sincronizar

```java
public class Counter {
    private int count = 0;

    // ¡Sin sincronización! Puede dar resultados incorrectos
    public void increment() {
        count++;
    }

    public int getCount() {
        return count;
    }
}
```

```java
public class Main {
    public static void main(String[] args) throws InterruptedException {
        Counter counter = new Counter();

        Thread thread1 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) counter.increment();
        });
        Thread thread2 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) counter.increment();
        });

        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();

        // Esperamos 2000, pero a veces obtenemos menos
        System.out.println("Final count: " + counter.getCount());
    }
}
```

#### ¿Por qué falla `count++`?

La operación `count++` **no es atómica**: se traduce en tres pasos separados que pueden ser interrumpidos entre medias:

```
1. Leer el valor actual de count  →  t1 lee 500
2. Sumar 1                         →  t1 calcula 501
                                       (t2 también lee 500, calcula 501)
3. Escribir el resultado           →  t1 escribe 501
                                       t2 escribe 501  ← ¡se pierde un incremento!
```

**Salida típica (incorrecta):**
```
Final count: 1847   ← debería ser 2000
```

---

## 4. Sincronización de Threads

Para evitar condiciones de carrera, Java ofrece el modificador **`synchronized`**, que garantiza que **solo un Thread** pueda ejecutar el bloque de código marcado a la vez.

📁 [`thread-examples/04-synchronized/`](thread-examples/04-synchronized/)

### 4.1. Método `synchronized`

```java
public class Counter {
    private int count = 0;

    public synchronized void increment() {
        count++;  // solo un Thread a la vez puede ejecutar esto
    }

    public int getCount() {
        return count;
    }
}
```

Con el mismo `Main` de antes, ahora el resultado siempre será 2000:

```
Final count: 2000   ✅
```

### 4.2. Bloque `synchronized`

A veces no es práctico sincronizar el método entero; se puede sincronizar solo la sección crítica:

```java
public void increment() {
    synchronized (this) {
        count++;
    }
}
```

### 4.3. ¿Qué es un monitor?

Cada objeto Java tiene un **monitor** (o cerrojo). Cuando un Thread entra en un bloque `synchronized(obj)`, adquiere el monitor de `obj`. Cualquier otro Thread que intente adquirirlo **queda bloqueado** hasta que el primero lo libere.

```
Thread 1 adquiere el monitor ──► ejecuta count++ ──► libera el monitor
                                                         │
Thread 2 esperaba el monitor ◄──────────────────────────┘
                             ──► ejecuta count++ ──► libera el monitor
```

### 4.4. Resumen: sin vs con `synchronized`

| Sin `synchronized`                   | Con `synchronized`               |
|--------------------------------------|----------------------------------|
| Más rápido (sin bloqueo)             | Ligeramente más lento            |
| Resultado impredecible / incorrecto  | Resultado siempre correcto       |
| Usar solo si no hay estado compartido| ✅ Usar cuando hay estado compartido |

---

## 5. Esperar a que un Thread termine: `join()`

El método `join()` hace que el Thread que lo llama **espere** a que el Thread indicado haya terminado su ejecución.

📁 [`thread-examples/04-synchronized/`](thread-examples/04-synchronized/)

```java
thread1.start();
thread2.start();

try {
    thread1.join();  // main espera a que thread1 termine
    thread2.join();  // main espera a que thread2 termine
} catch (InterruptedException e) {
    e.printStackTrace();
}

// Esta línea solo se ejecuta cuando AMBOS threads han terminado
System.out.println("Final count: " + counter.getCount());
```

> ⚠️ `join()` lanza `InterruptedException`, que hay que capturar o declarar con `throws`.

### ¿Qué pasa sin `join()`?

Sin `join()`, el Thread main podría imprimir el resultado **antes de que los Threads hayan terminado**, obteniendo un valor parcial o 0.

---

## 6. Ciclo de vida de un Thread

```
           new Thread()
                │
                ▼
            NEW (creado)
                │
           start()
                │
                ▼
         RUNNABLE (preparado)
         ┌──────┴──────┐
         │             │
         ▼             ▼
     RUNNING       BLOCKED/WAITING
    (ejecutando)   (espera I/O, lock,
         │          sleep, join...)
         │             │
         └──────┬──────┘
                │
                ▼
          TERMINATED (terminado)
```

| Estado         | Descripción                                                     |
|----------------|-----------------------------------------------------------------|
| `NEW`          | El Thread ha sido creado pero aún no ha arrancado               |
| `RUNNABLE`     | Listo para ejecutarse o ejecutándose                            |
| `BLOCKED`      | Esperando adquirir un monitor (`synchronized`)                  |
| `WAITING`      | Esperando indefinidamente (`join()`, `wait()`)                  |
| `TIMED_WAITING`| Esperando un tiempo concreto (`sleep(ms)`, `join(ms)`)         |
| `TERMINATED`   | La ejecución del método `run()` ha finalizado                   |

---

## 7. Ejercicio: Reproductor de Música en Segundo Plano (laSallefy)

La tarea propuesta al final de la sesión consiste en aplicar Threads al proyecto final **laSallefy** para que la **reproducción de música** ocurra en un Thread separado, permitiendo que el usuario siga interactuando con el menú mientras la canción suena.

### Problema actual (sin Threads)

```
Main Thread:
  menu.show() ──► usuario elige "Play" ──► synth.play(song) ──► [bloqueado esperando] ──► menu.show()
```
El menú queda **congelado** mientras la canción se reproduce.

### Objetivo (con Thread)

```
Main Thread:
  menu.show() ──► usuario elige "Play" ──► inicia MusicPlayerThread ──► menu.show()  (inmediato)
                                                          │
MusicPlayerThread:                                        │
  ←─────────────────────────────────────────────────────┘
  synth.play(song)  [en paralelo, sin bloquear el menú]
```

### Pistas para la implementación

- Crea una clase `MusicPlayerThread` que extienda `Thread` y reciba el `SoundSynth` y la `Song`.
- Usa una variable `volatile boolean` para poder detener la reproducción desde fuera del Thread.
- En el `Controller`, guarda una referencia al Thread activo para poder llamar a `stopPlayback()` cuando el usuario lo pida.
- Usa `join()` si necesitas esperar a que el Thread anterior haya terminado antes de iniciar uno nuevo.

---

## 8. Resumen de Conceptos

| Concepto                    | Descripción                                                                                          |
|-----------------------------|------------------------------------------------------------------------------------------------------|
| **Thread**                  | Hilo de ejecución independiente dentro del mismo proceso                                             |
| **`extends Thread`**        | Forma de crear un Thread sobreescribiendo `run()`                                                    |
| **`implements Runnable`**   | Forma preferida; separa la tarea del mecanismo de hilo                                               |
| **`start()`**               | Lanza el Thread en un nuevo hilo de ejecución (nunca llamar `run()` directamente)                    |
| **`join()`**                | Hace que el Thread llamador espere a que otro Thread termine                                         |
| **Condición de carrera**    | Error cuando dos Threads modifican el mismo dato sin coordinación, produciendo resultados incorrectos |
| **`synchronized`**          | Garantiza acceso exclusivo a un bloque de código para evitar condiciones de carrera                  |
| **Monitor / lock**          | Mecanismo interno de cada objeto Java que usa `synchronized`                                         |
| **`volatile`**              | Garantiza que los cambios a una variable sean visibles inmediatamente entre Threads                  |
| **`InterruptedException`**  | Excepción lanzada cuando un Thread es interrumpido mientras espera (`join`, `sleep`…)                |

---

## Recursos

- 🔗 [Java Thread – Oracle Docs](https://docs.oracle.com/javase/8/docs/api/java/lang/Thread.html)
- 🔗 [Runnable interface – Oracle Docs](https://docs.oracle.com/javase/8/docs/api/java/lang/Runnable.html)
- 🔗 [Concurrency lesson – Oracle Java Tutorials](https://docs.oracle.com/javase/tutorial/essential/concurrency/)
- 🔗 [Synchronized Methods – Oracle Java Tutorials](https://docs.oracle.com/javase/tutorial/essential/concurrency/syncmeth.html)
- 🔗 [Java volatile keyword explained](https://www.baeldung.com/java-volatile)
- [Sesión 13 – Proyecto Final laSallefy](../session13/ENUNCIADO.md)
- [Sesión 12 – Excepciones Propias en Java](../session12/README.md)
- [Sesión 11 – Excepciones en Java](../session11/README.md)
