public class Alumno {

    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_RED = "\u001B[31m";


    private String nombre;
    private int edad;
    private boolean aprobado;

    public Alumno(String nombre, int edad, boolean aprobado) {
        this.nombre = nombre;
        this.edad = edad;
        this.aprobado = aprobado;
    }

    @Override
    public String toString() {
        return colorAprobado() + "mi nombre es: " + nombre + " y tengo " + edad + " años y " + estoyAprobado() + ANSI_RESET;
    }

    private String estoyAprobado() {
        if (aprobado) {
            return "estoy aprobado";
        } else {
            return "no estoy aprobado";
        }
    }

    private String colorAprobado() {
        if (aprobado) {
            return ANSI_GREEN;
        } else {
            return ANSI_RED;
        }
    }


}
