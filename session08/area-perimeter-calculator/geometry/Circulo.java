package geometry;

public class Circulo extends Forma {
    private double radius;

    public Circulo(double r) {
        super("hola.Circulo");
        radius = r;
        this.nombre = "Circulo";
    }

    @Override
    public double calcularArea() {

        return Math.PI * radius * radius;
    }

    @Override
    public double calcularPerimetro() {
        return 2 * Math.PI * radius;
    }
}
