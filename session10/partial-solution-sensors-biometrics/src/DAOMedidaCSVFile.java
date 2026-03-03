import java.util.ArrayList;

public class DAOMedidaCSVFile implements DAOMedida{

    String filePath;

    public DAOMedidaCSVFile(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public ArrayList<Medida> load() {
        // leer el filePath: this.filePath y crear el array.
        return null;
    }

    @Override
    public void save(ArrayList<Medida> medidas) {
        // a partir del array guardo al filePath: this.filePath.
    }
}
