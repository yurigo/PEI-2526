
public class Main {
    public static void main(String[] args) {

        System.out.println("Bienvenido a la gestión del gimnasio");

        Persona p1 = new Persona("Alice", "123456789V");
        Persona p2 = new Persona("Bob", "123456789V");
        Persona p3 = new Persona("Charlie", "123456789V");

        mostrarPorPantallaLasPersonas(p1, p2, p3);
        // simulo la entrada de la persona 2:
        p2.registrarEntrada();
        mostrarPorPantallaLasPersonas(p1, p2, p3);
        p1.registrarEntrada();
        mostrarPorPantallaLasPersonas(p1, p2, p3);
        p2.registrarSalida();
        mostrarPorPantallaLasPersonas(p1, p2, p3);


    }

    public static void mostrarPorPantallaLasPersonas(Persona a, Persona b, Persona c) {
//        p1.muestrate();
//        p2.muestrate();
//        p3.muestrate();

        // desacoplamos el system.out de la clase persona
        System.out.println(a.getInformation());
        System.out.println(b.getInformation());
        System.out.println(c.getInformation());
    }
}