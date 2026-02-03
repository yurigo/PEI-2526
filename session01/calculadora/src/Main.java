import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner lectorDeTeclado = new Scanner(System.in);

        System.out.println("Bienvenido a la calculadora");
        System.out.println("Introduce un numero:");

        int numero = lectorDeTeclado.nextInt();

        System.out.println("tu numero es: " + numero);
        System.out.println("Introduce otro numero:");

        int numero2 = lectorDeTeclado.nextInt();

        System.out.println("tu numero es: " + numero2);

        System.out.println("Introduce la operacion (S: suma, R: resta, M: multi, D: division):");
        String operacion = lectorDeTeclado.next();

        switch (operacion) {
            case "S":
            case "s":
                System.out.println(suma(numero, numero2));
                break;
            case "R":
            case "r":
                System.out.println(resta(numero, numero2));
                break;
            case "M":
            case "m":
                System.out.println(multiplicacion(numero, numero2));
                break;
            case "D":
            case "d":
                System.out.println(division(numero, numero2));
                break;
        }

        // System.out.println("la suma de los numeros es: " + (numero + numero2));
    }

    public static int suma(int a, int b) {
        return a + b;
    }

    public static int resta(int a, int b) {
        return a - b;
    }

    public static int multiplicacion(int a, int b) {
        return a * b;
    }

    public static int division(int a, int b) {
        return a / b;
    }
}
