import geometry.*;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args){

        ArrayList<Forma> formas = new ArrayList<Forma>();

        formas.add(new Cuadrado(10));
        formas.add(new Rectangulo(5, 6));
        formas.add(new Triangulo(3,4,5));
        formas.add(new Triangulo(1,2,3));
        formas.add(new Circulo(5));

        muestra(formas);
        

    }

    public static void muestra(ArrayList<Forma> formas){
        for (Forma forma : formas) {
            muestra(forma);
        }
    }

    public static void muestra(Forma f){

        System.out.println("-----------------------------------");
        System.out.println("Nombre:    " + f.getNombre());
        System.out.println("Area:      " + f.calcularArea());
        System.out.println("Perimetro: " + f.calcularPerimetro());
        System.out.println("-----------------------------------");


    }
}
