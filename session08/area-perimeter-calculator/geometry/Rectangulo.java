package geometry;

public class Rectangulo extends Forma{

    private double base;
    private double altura;

    public Rectangulo(double base, double altura) {
        super("Rectangulo");

        this.base = base;
        this.altura = altura;
    }

    @Override
    public double calcularArea() {
        return base*altura;
    }

    @Override
    public double calcularPerimetro() {
        return base+ base+ altura+altura;
    }
}
