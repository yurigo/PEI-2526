/**
 * Ejemplo de CONDICIÓN DE CARRERA (race condition).
 *
 * Dos Threads incrementan el mismo contador 1000 veces cada uno.
 * El resultado esperado es 2000, pero sin sincronización se obtiene
 * frecuentemente un valor menor porque los Threads se interfieren.
 *
 * Ejecuta el programa varias veces para ver resultados distintos.
 */
public class Main {
    public static void main(String[] args) throws InterruptedException {
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

        thread1.join();
        thread2.join();

        System.out.println("Resultado final: " + counter.getCount());
        System.out.println("Esperado: 2000");
        System.out.println("¿Correcto? " + (counter.getCount() == 2000 ? "✅ SÍ (esta vez)" : "❌ NO — condición de carrera detectada"));
    }
}
