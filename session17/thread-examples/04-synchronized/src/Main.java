/**
 * Ejemplo de sincronización correcta con synchronized + join().
 *
 * - synchronized: garantiza que solo un Thread a la vez incremente el contador.
 * - join(): el Thread main espera a que thread1 y thread2 terminen antes de
 *   imprimir el resultado final.
 *
 * El resultado siempre será 2000.
 */
public class Main {
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

        System.out.println("Resultado final: " + counter.getCount());
        System.out.println("Esperado: 2000");
        System.out.println("¿Correcto? " + (counter.getCount() == 2000 ? "✅ SÍ — sincronización funciona" : "❌ NO"));
    }
}
