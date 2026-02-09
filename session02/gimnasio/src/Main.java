
public class Main {
    public static void main(String[] args) {

        System.out.println("Bienvenido a la gestión del gimnasio");

        Persona p1 = new Persona("Alice", "123456789V");
        Persona p2 = new Persona("Bob", "123456789V");
        Persona p3 = new Persona("Charlie", "123456789V");

        // Persona p4 = p1; // ojo que no creo otro objeto!


        // System.out.println(p2.nombre + " " + p2.DNI + " " + p2.estaDentro);
        // System.out.println(p3.nombre + " " + p3.DNI + " " + p3.estaDentro);

        p1.muestrate();
        p2.muestrate();
        p3.muestrate();

        // simulo la entrada de la persona 2:

        p2.registrarEntrada();

        p1.muestrate();
        p2.muestrate();
        p3.muestrate();

        p1.registrarEntrada();

        p1.muestrate();
        p2.muestrate();
        p3.muestrate();

        p2.registrarSalida();

        p1.muestrate();
        p2.muestrate();
        p3.muestrate();


    }
}