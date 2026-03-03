import java.util.Scanner;

public class MenuConsoleSuperior implements Menu {

    Scanner sc;

    public MenuConsoleSuperior() {
        sc = new Scanner(System.in);
    }

    @Override
    public void show(String message) {
        System.out.println("*" + message + "*");
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
