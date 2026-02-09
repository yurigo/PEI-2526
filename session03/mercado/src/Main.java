import java.util.Date;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner lector = new Scanner(System.in);
        System.out.println("Hello Mercado");
        System.out.println("Inventario");

        System.out.print("Introduce un producto (nombre): ");
        String lecturaNombre = lector.nextLine();

        System.out.print("Introduce un producto (descripcion): ");
        String lecturaDescripcion = lector.nextLine();

        System.out.print("Introduce un producto (precio): ");
        Float lecturaPrecio = lector.nextFloat();

        System.out.print("Introduce un producto (stock): ");
        Integer lecturaStock = lector.nextInt();

        Producto p1 = new Producto(
                lecturaNombre,
                lecturaDescripcion,
                lecturaPrecio,
                lecturaStock,
                null
        );

        System.out.println(p1);



//        System.out.println(Producto.contador);
//
//        Producto p1 = new Producto("Alpargatas",
//                "Alpargatas negras",
//                100.0F,
//                1000,
//                null);
//
//        System.out.println(Producto.contador);
//
//        // fecha de dentro de 1 mes:
//        Date oneMonthFromNow = new Date();
//        oneMonthFromNow.setMonth(oneMonthFromNow.getMonth() + 1);
//
//        Producto p2 = new Producto("Croquetas",
//                "Croquetas de la iaia",
//                10.0F,
//                1_000_000_000,
//                oneMonthFromNow);
//
//        System.out.println(Producto.contador);
//
//        System.out.println(p1);
//        System.out.println(p2);



    }
}
