import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Gson gson = new Gson();
        String FILENAME_ARR = "alumnos.json";

        try {
            // 1. Leer el array de alumnos desde el fichero
            JsonReader reader = new JsonReader(new FileReader(FILENAME_ARR));
            Alumno[] aA = gson.fromJson(reader, Alumno[].class);

            System.out.println("=== Alumnos leídos del fichero ===");
            for (Alumno a : aA) {
                System.out.println(a);
            }

            // Convertir a lista para poder añadir elementos
            List<Alumno> lista = new ArrayList<>(Arrays.asList(aA));

            // 2. Crear un alumno nuevo desde código y añadirlo al array
            Alumno nuevoAlumno = new Alumno("Eva", 22, true);
            lista.add(nuevoAlumno);

            System.out.println("\n=== Lista tras añadir Eva ===");
            for (Alumno a : lista) {
                System.out.println(a);
            }

            // 3. Crear un alumno desde datos introducidos por el usuario (Scanner)
            Scanner sc = new Scanner(System.in);

            System.out.print("\nNombre del nuevo alumno: ");
            String nombre = sc.nextLine();

            System.out.print("Edad: ");
            int edad = Integer.parseInt(sc.nextLine());

            System.out.print("¿Aprobado? (true/false): ");
            boolean aprobado = Boolean.parseBoolean(sc.nextLine());

            Alumno alumnoScanner = new Alumno(nombre, edad, aprobado);
            lista.add(alumnoScanner);

            System.out.println("\n=== Lista final ===");
            for (Alumno a : lista) {
                System.out.println(a);
            }

            // 4. Guardar la lista actualizada en un nuevo fichero JSON
            String jsonOutput = gson.toJson(lista);
            FileWriter fw = new FileWriter("alumnos_updated.json");
            fw.write(jsonOutput);
            fw.close();
            System.out.println("\nFichero guardado: alumnos_updated.json");

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
