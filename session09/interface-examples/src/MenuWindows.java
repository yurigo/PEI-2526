import java.util.Scanner;

public class MenuWindows implements MenuInterface {

    Scanner sc = new Scanner(System.in);

    @Override
    public void show(String message) {
        // open a window an show message
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
