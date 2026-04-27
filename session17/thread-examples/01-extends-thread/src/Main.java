public class Main {
    public static void main(String[] args) {
        System.out.println("Main: arrancando threads...");

        MyThread thread1 = new MyThread("Thread-A");
        MyThread thread2 = new MyThread("Thread-B");

        thread1.start();
        thread2.start();

        System.out.println("Main: threads lanzados (el main continúa sin esperar).");
    }
}
