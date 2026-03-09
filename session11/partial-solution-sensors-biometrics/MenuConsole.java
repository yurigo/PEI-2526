import java.util.Scanner;

public class MenuConsole implements Menu {

    Scanner sc;

    public MenuConsole() {
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
        System.out.println(message);
    }

    @Override
    public void showError(String message) {
        System.out.println("ERROR: " + message);
    }

    @Override
    public String getString() {
        return sc.nextLine();
    }

    @Override
    public Integer getInteger() {

        try {
            return sc.nextInt();
        } catch (Exception e) {
            // he leido texto de pantalla y reventado,
            // por consiguiente, devuelvo:
            return -99999;
            // Atención:  Esto puede ocasionar errores
            // por ejemplo:
            // si se usa getInteger en otro contexto.
            // (se está usando sólamente para seleccionar una
            // opción del menu y el controller controla la opción
            // con un switch case/default).  Si se usara en otro contexto:
            // dame tu edad y pusieramos un texto, la edad que devolvería
            // este métódo es -99999 ocasionando un error.
            // utilizar este tipo de soluciones (aunque parezcan válidas)
            // puede ocasionarlos errores en un futuro.
        } finally {
            show("esto está dentro del finally");
            // finally se ejecuta siempre que salgamos del contexto
            // del try (o catch).  No es lo mismo finally que implementarlo
            // después del try/catch. Un return o un throw puede alterar el
            // flujo imperativo de las instrucciones y forzar la salida del
            // métódo.  finally te asegura que el código se ejecute siempre.
            sc.nextLine();
        }
    }
}
