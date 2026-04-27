/**
 * Representa un sonido individual (nota musical):
 * frecuencia en Hz y duración en milisegundos.
 */
public class Sound {
    private final int frequency;
    private final int durationMs;

    public Sound(int frequency, int durationMs) {
        this.frequency = frequency;
        this.durationMs = durationMs;
    }

    public int getFrequency() { return frequency; }
    public int getDurationMs() { return durationMs; }

    @Override
    public String toString() {
        return "Sound{freq=" + frequency + "Hz, dur=" + durationMs + "ms}";
    }
}
