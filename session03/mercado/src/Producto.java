import java.util.Date;

public class Producto {
    public String nombre;
    private String descripcion;
    private Float precio;
    private Integer stock;
    private Date caducidad;
    private Long id;
    public static Long contador = 0L;

    public Producto(
            String nombre,
            String descripcion,
            Float precio,
            Integer stock,
            Date caducidad
    ) {
         this.nombre = nombre;
         this.descripcion = descripcion;
         this.precio = precio;
         this.stock = stock;
         this.caducidad = caducidad;
         this.id = ++contador;
    }


    public String toString() {
        return nombre + " " + descripcion + " " + precio + " " + stock + " " + caducidad + " " + id;

        //return "HOLA!!!!!";
    }
}
