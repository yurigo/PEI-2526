package geometry;

public class Circulo extends Forma {
    private double radius;

    public Circulo(double r) {
        super("Circulo");
        radius = r;
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
