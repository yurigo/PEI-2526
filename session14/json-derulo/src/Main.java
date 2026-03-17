import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.FileReader;

public class Main {
    public static void main(String[] args) {

        Gson gson = new Gson();
        String FILENAME_OBJ = "alumno.json";
        String FILENAME_ARR = "alumnos.json";

        try {

            JsonReader reader = new JsonReader(new FileReader(FILENAME_ARR));

//            Alumno a = gson.fromJson(reader, Alumno.class);
//            System.out.println(a);

            Alumno[] aA = gson.fromJson(reader, Alumno[].class);

            for (Alumno a : aA){
                System.out.println(a);
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
