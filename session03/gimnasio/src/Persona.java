import java.util.Date;

public class Persona {
    //public String nombre;
    // los atributos deberían ser (por norma general) privados
    private String nombre;
    private Date fechaEntrada;
    private Date fechaSalida;
    private long id;
    private String DNI;
    private boolean estaDentro;

    public Persona(String nombre, String DNI){
        this.nombre = nombre;
        this.fechaEntrada = null; //new Date();
        this.fechaSalida = null; //new Date();
        this.id = 0;
        this.DNI = DNI;
        this.estaDentro = false;
    }

//    public void muestrate(){
//        System.out.println("-------------------------");
//        System.out.println("Nombre: " + nombre);
//        System.out.println(nombre + " " + DNI + " " + estaDentro);
//        System.out.println("Fecha de entrada: " + fechaEntrada);
//        System.out.println("Fecha de salida: " + fechaSalida);
//        System.out.println("-------------------------");
//    }

    // quiero desacoplarme al system.out.print:
    public String getInformation(){
        return "-------------------------" + "\n" +
                "Nombre: " + nombre + "\n" +
                nombre + " " + DNI + " " + estaDentro + "\n" +
                "Fecha de entrada: " + fechaEntrada + "\n" +
                "Fecha de salida: " + fechaSalida + "\n" +
                "-------------------------";
    }

    public void registrarEntrada(){
        this.estaDentro = true;
        this.fechaEntrada = new Date();
        this.fechaSalida = null;

    }

    public void registrarSalida(){
        this.estaDentro = false;
        this.fechaEntrada = null;
        this.fechaSalida = new Date();
    }

}
