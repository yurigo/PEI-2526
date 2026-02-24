package geometry;

public class Triangulo extends Forma{
    private double lado1, lado2, lado3;

    public Triangulo(double lado1, double lado2, double lado3) {
        super("Triangulo");
        this.lado1 = lado1;
        this.lado2 = lado2;
        this.lado3 = lado3;
    }

    private double calcularSemiperimetro(){
        return calcularPerimetro() / 2;
    }

    @Override
    public double calcularArea() {
        double semiperimetro = calcularSemiperimetro();
        return Math.sqrt(semiperimetro * (semiperimetro - lado1) * (semiperimetro - lado2) * (semiperimetro - lado3));
    }

    @Override
    public double calcularPerimetro() {
        return lado1 + lado2 + lado3;
    }
}
