public class MyThread extends Thread {

    private final String name;

    public MyThread(String name) {
        this.name = name;
    }

    @Override
    public void run() {
        for (int i = 1; i <= 5; i++) {
            System.out.println("[" + name + "] paso " + i);
            try {
                Thread.sleep(200); // simula trabajo
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("[" + name + "] terminado.");
    }
}
