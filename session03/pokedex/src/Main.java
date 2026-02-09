public class Main {
    public static void main(String[] args) {
        Pokemon p1 = new Pokemon("Alice", "Pikachu");
        Pokemon p2 = new Pokemon("Bob", "Psyduck");

        // p1.muestrate();
        System.out.println(p1.getInformation());
        // p2.muestrate();
        System.out.println(p2.getInformation());

        // para subir el nivel...
        // p1.setNivel(p1.getNivel() + 1);
        // p1.setVida(p1.getVida() + 100);

        // mejor encapsularlo en una funcion:
        p1.subirNivel();


        // p1.muestrate();
        System.out.println(p1.getInformation());
        // p2.muestrate();
        System.out.println(p2.getInformation());


    }
}