import java.util.Scanner;

public class MenuSuperConsole  implements MenuInterface {

    Scanner sc = new Scanner(System.in);

    @Override
    public void show(String message) {
        System.out.println("***" + message + "***");

    }

    @Override
    public String getString() {
        System.out.println("***Menu Super Console***");
        return sc.nextLine();
    }

    @Override
    public Integer getInteger() {
        return sc.nextInt();
    }
}
