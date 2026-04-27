/**
 * Contador SIN sincronización.
 * Demuestra el problema de la condición de carrera:
 * count++ no es una operación atómica (leer → sumar → escribir),
 * por lo que dos Threads pueden interferir entre sí.
 */
public class Counter {
    private int count = 0;

    // ¡Sin synchronized! Puede producir resultados incorrectos
    public void increment() {
        count++;
    }

    public int getCount() {
        return count;
    }
}
