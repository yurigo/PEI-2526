/**
 * Contador CON sincronización.
 * El modificador synchronized en increment() garantiza que solo un Thread
 * a la vez puede ejecutar ese método, eliminando la condición de carrera.
 */
public class Counter {
    private int count = 0;

    public synchronized void increment() {
        count++;
    }

    public int getCount() {
        return count;
    }
}
