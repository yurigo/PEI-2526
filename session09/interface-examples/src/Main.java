import java.util.List;
import java.util.ArrayList;
import java.util.Vector;

public class Main {

    public static void main(String[] args) {

        // Menu interface : QUÉ tengo que hacer.
        // Menu Console : CÓMO lo tengo que hacer.
        // MenuInterface menu = new MenuConsole();
        MenuInterface menu = new MenuColor();
        String text = "hola";
        Medida m = new Medida(text);

        menu.show("Hola! Cómo te llamas?");
        String nombre = menu.getString();
        menu.show("Bienvenido, " + nombre);
        menu.show("Cuantos años tienes?");
        Integer age = menu.getInteger();
        menu.show("Tienes " + age + " años");

        List<Medida> medidas = new MySuperList();

        medidas.add(new Medida("A"));
        medidas.add(new Medida("B"));
        medidas.add(new Medida("C"));

        for (Medida medida : medidas) {
            menu.show(medida.toString());
        }

    }
}
