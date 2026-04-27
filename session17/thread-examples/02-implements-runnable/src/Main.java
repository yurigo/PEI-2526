public class Main {
    public static void main(String[] args) {
        System.out.println("Main: arrancando threads con Runnable...");

        // Forma 1: usando una clase que implementa Runnable
        Thread thread1 = new Thread(new MyRunnable("Runnable-A"));
        thread1.start();

        // Forma 2: usando una expresión lambda (Java 8+)
        Thread thread2 = new Thread(() -> {
            for (int i = 1; i <= 5; i++) {
                System.out.println("[Lambda-B] paso " + i);
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("[Lambda-B] terminado.");
        });
        thread2.start();

        System.out.println("Main: threads lanzados.");
    }
}
