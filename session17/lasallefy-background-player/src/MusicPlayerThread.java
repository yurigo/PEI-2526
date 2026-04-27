/**
 * Thread que gestiona la reproducción de una canción en segundo plano.
 *
 * - Extiende Thread para encapsular toda la lógica de reproducción.
 * - La variable 'running' es volatile para que los cambios hechos desde
 *   el Thread principal sean visibles inmediatamente en este Thread.
 * - stopPlayback() permite interrumpir la reproducción desde fuera.
 */
public class MusicPlayerThread extends Thread {

    private final Song song;
    private volatile boolean running = true;

    public MusicPlayerThread(Song song) {
        this.song = song;
    }

    @Override
    public void run() {
        System.out.println("▶ Reproduciendo: " + song);
        for (Sound sound : song.getSounds()) {
            if (!running) {
                System.out.println("⏹ Reproducción detenida por el usuario.");
                return;
            }
            // Simula la reproducción del sonido (en el proyecto real se usaría SoundSynth)
            System.out.println("  ♪ " + sound);
            try {
                Thread.sleep(sound.getDurationMs());
            } catch (InterruptedException e) {
                System.out.println("⚠ Thread interrumpido.");
                return;
            }
        }
        System.out.println("✅ Reproducción completada: " + song.getTitle());
    }

    /**
     * Señaliza al Thread que debe detenerse.
     * La próxima vez que el bucle compruebe 'running' saldrá limpiamente.
     */
    public void stopPlayback() {
        running = false;
    }
}
