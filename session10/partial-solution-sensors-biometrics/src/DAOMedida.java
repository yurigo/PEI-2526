import java.util.ArrayList;

public interface DAOMedida {

//    ArrayList<Medida> getMedidas();
//    Medida getMedida();
//    void updateMedida();
//    void deleteMedida();
//    void insertMedida();

    ArrayList<Medida> load();
    void save(ArrayList<Medida> medidas);
}
