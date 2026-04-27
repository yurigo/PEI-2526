27/04

En esta sesión se ha hablado sobre los Threads.

La dinámica en clase ha sido una flipped classroom.

Los alumnos han tenido que explorar el tema por su cuenta y luego se ha discutido en clase. Se ha pedido que busquen información sobre los Thread, qué son, para qué sirven, algun ejemplo y se ha pedido también que encuentren un caso de uso en el que puedan aplicar los Threads dentro de la práctica que están realizando (presentada en la sesión 13).

Se han comentado en las presentaciones:

- Ejecución de tareas en paralelo.
- Mejorar el rendimiento de las aplicaciones.
- Condiciones carrera.
- Sincronización de Threads.

Se han mostrado ejemplos de código en java:

```java
public class MyThread extends Thread {
    public void run() {
        System.out.println("Hello from a thread!");
    }
}

public class Main {
    public static void main(String[] args) {
        MyThread thread = new MyThread();
        thread.start();
    }
}
```

```java
public class MyRunnable implements Runnable {
    public void run() {
        System.out.println("Hello from a runnable!");
    }
}

public class Main {
    public static void main(String[] args) {
        Thread thread = new Thread(new MyRunnable());
        thread.start();
    }
}
```

Se ha hablado también sobre la importancia de manejar correctamente los Threads para evitar problemas como las condiciones de carrera y se han dado algunos consejos para sincronizar los Threads.

```java
public class Counter {
    private int count = 0;

    public synchronized void increment() {
        count++;
    }

    public int getCount() {
        return count;
    }
}
```

En este ejemplo, el método `increment` está sincronizado para evitar que múltiples Threads accedan al mismo tiempo y causen una condición de carrera.

En resumen, los Threads son una herramienta poderosa para mejorar el rendimiento de las aplicaciones, pero es importante usarlos con cuidado para evitar problemas de sincronización.

```java
class Main
{
    public static void main(String[] args) {
      Counter counter = new Counter();
      Thread thread1 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                counter.increment();
            }
        });
      Thread thread2 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                counter.increment();
            }
        });

      thread1.start();
      thread2.start();

      try {
          thread1.join();
          thread2.join();
      } catch (InterruptedException e) {
          e.printStackTrace();
      }

      System.out.println("Final count: " + counter.getCount());

    }
}
```

En este ejemplo, se crean dos Threads que incrementan un contador de forma concurrente. Al usar el método `join`, se asegura que el programa espere a que ambos Threads terminen antes de imprimir el resultado final del contador.

Se ha concluido la sesión con una tarea para los alumnos, que consiste en implementar el caso de uso del reproductor de musica para permitir la reproducción de música en segundo plano mientras el usuario interactúa con la aplicación. Esto implica crear un Thread para manejar la reproducción de música y asegurarse de que la interfaz de usuario siga siendo responsiva.
