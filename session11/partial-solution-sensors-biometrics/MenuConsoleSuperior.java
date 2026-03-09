import java.util.Scanner;

public class MenuConsoleSuperior implements Menu {

    Scanner sc;

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

// ....

    public MenuConsoleSuperior() {
        sc = new Scanner(System.in);
    }

    @Override
    public void showWelcome() {
        this.show("Bienvenido a mi aplicación");
    }

    @Override
    public void showMenu() {
        this.show("Menu:");
        this.show("1. Opcion 1");
        this.show("2. Opcion 2");
        this.show("3. Opcion 3");
        this.show("4. Opcion 4");
        this.show("0. Salir");
        this.show("");
    }

    @Override
    public void show(String message) {
        System.out.println(ANSI_YELLOW + message + ANSI_RESET);
    }

    @Override
    public void showError(String message) {
        System.out.println(ANSI_RED + message + ANSI_RESET);
    }

    @Override
    public String getString() {
        return sc.nextLine();
    }

    @Override
    public Integer getInteger() {
        /**
         * a diferencia del getInteger implementado en MenuConsole
         * esta implementación fuerza al usuario a iterar dentro del
         * getInteger hasta que éste introduzca un entero válido.
         *
         * Este métódo es más robusto y reutilizable que el anterior.
         * Ya que puede utilizarse en otros contextos que no sean
         * para escoger una opción de un menú
         */


        for(;;) {
            try {
                return sc.nextInt();
            } catch (Exception e) {
                // he leido texto de pantalla y reventado,
                // por consiguiente, devuelvo:
                showError("Oye que un numero, no te parece?");
            } finally {
                sc.nextLine();
            }
        }
    }
}
