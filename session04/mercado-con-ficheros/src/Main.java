import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner lector = new Scanner(System.in);
        System.out.println("Hello Mercado");
        System.out.println("Inventario");

        System.out.println("Introduce tantos productos como quieras!:");

        // Producto[] parray = new Producto[4];

        ArrayList<Producto> productos = new ArrayList<Producto>();

        // quiero leer los productos de un fichero:

        String nombreArchivo = "assets/productos.csv";

        try {
            FileReader fileReader = new FileReader(nombreArchivo);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String linea;
            while ((linea = bufferedReader.readLine()) != null) {

                String[] pedazos = linea.split(";");

                Producto aux = new Producto(
                        pedazos[0],
                        pedazos[1],
                        Float.parseFloat(pedazos[2]),
                        Integer.parseInt(pedazos[3]),
                        null
                );

                productos.add(aux);

            }
        }catch(FileNotFoundException e){
            System.out.println("No se puede leer el archivo");
        }catch(IOException e){
            System.out.println("Error al leer el archivo");
        }

        for (int i = 0; i < productos.size(); i++) {
            // System.out.println(parray[i]);
            System.out.println(productos.get(i));
        }


        // Boolean flag = true;

        // for (int i = 0; i < 3; i++) {
        while (true){
            System.out.print("Introduce un producto (nombre): ");
            String lecturaNombre = lector.nextLine();

            System.out.print("Introduce un producto (descripcion): ");
            String lecturaDescripcion = lector.nextLine();

            System.out.print("Introduce un producto (precio): ");
            Float lecturaPrecio = lector.nextFloat();
            lector.nextLine();

            System.out.print("Introduce un producto (stock): ");
            Integer lecturaStock = lector.nextInt();
            lector.nextLine();

            // parray[i] = new Producto(lecturaNombre, lecturaDescripcion, lecturaPrecio, lecturaStock, null);

            Producto aux = new Producto(
                    lecturaNombre,
                    lecturaDescripcion,
                    lecturaPrecio,
                    lecturaStock,
                    null
            );

            productos.add(aux);

            System.out.print("Quiere añadir otro producto? (y/n): ");
            String lecturaOtro = lector.nextLine();

            if (!lecturaOtro.equalsIgnoreCase("y")){
                break;
            }
        }

        for (int i = 0; i < productos.size(); i++) {
            // System.out.println(parray[i]);
            System.out.println(productos.get(i));
        }




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
