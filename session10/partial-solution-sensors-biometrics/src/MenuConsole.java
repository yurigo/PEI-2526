import java.util.Scanner;

public class MenuConsole implements Menu {

    Scanner sc;

    public MenuConsole() {
        sc = new Scanner(System.in);
    }

    @Override
    public void show(String message) {
        System.out.println(message);
    }

    @Override
    public String getString() {
        return sc.nextLine();
    }

    @Override
    public Integer getInteger() {
        return sc.nextInt();
    }
}
