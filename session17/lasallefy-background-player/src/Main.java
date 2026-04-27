import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * Demostración del reproductor de música en segundo plano usando Threads.
 *
 * Mientras la canción se reproduce (MusicPlayerThread), el Thread principal
 * sigue mostrando el menú y aceptando comandos del usuario, lo que hace
 * la aplicación responsiva (no bloqueante).
 *
 * Menú:
 *   1 → Reproducir canción de ejemplo
 *   2 → Detener reproducción
 *   0 → Salir
 */
public class Main {

    private static MusicPlayerThread playerThread = null;

    public static void main(String[] args) {
        Song song = buildExampleSong();
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        System.out.println("=== laSallefy – Reproductor en segundo plano ===\n");

        while (running) {
            System.out.println("\n--- Menú ---");
            System.out.println("1. Reproducir \"" + song.getTitle() + "\"");
            System.out.println("2. Detener reproducción");
            System.out.println("0. Salir");
            System.out.print("> ");

            String input = scanner.nextLine().trim();

            switch (input) {
                case "1":
                    playSong(song);
                    break;
                case "2":
                    stopSong();
                    break;
                case "0":
                    stopSong();
                    running = false;
                    System.out.println("¡Hasta luego!");
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        }

        scanner.close();
    }

    private static void playSong(Song song) {
        if (playerThread != null && playerThread.isAlive()) {
            System.out.println("⚠ Ya hay una canción reproduciéndose. Deteniéndola primero...");
            playerThread.stopPlayback();
            try {
                playerThread.join(); // espera a que el Thread anterior haya terminado del todo
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        playerThread = new MusicPlayerThread(song);
        playerThread.start();  // ← no bloquea el hilo principal
        System.out.println("(La reproducción ocurre en segundo plano; el menú sigue activo)");
    }

    private static void stopSong() {
        boolean alive = playerThread != null && playerThread.isAlive();
        if (alive) {
            playerThread.stopPlayback();
        } else {
            System.out.println("No hay ninguna canción reproduciéndose.");
        }
    }

    /** Construye una canción de ejemplo con notas de "Happy Birthday" */
    private static Song buildExampleSong() {
        List<Sound> sounds = Arrays.asList(
            new Sound(264, 300),
            new Sound(264, 300),
            new Sound(297, 600),
            new Sound(264, 600),
            new Sound(352, 600),
            new Sound(330, 1200),
            new Sound(264, 300),
            new Sound(264, 300),
            new Sound(297, 600),
            new Sound(264, 600),
            new Sound(396, 600),
            new Sound(352, 1200)
        );
        return new Song("Happy Birthday", "Tradicional", sounds);
    }
}
