import java.util.Scanner;

public class MenuConsole implements Menu{

    Scanner sc = new Scanner(System.in);

    public MenuConsole() {
        //
    }


    @Override
    public void show(String message) {
        System.out.println(message);
    }

    @Override
    public String getString(String message) {
        System.out.print(message  + ":  ");
        return sc.nextLine();
    }
}
