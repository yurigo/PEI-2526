import java.util.ArrayList;

public class Controller {

    Menu menu;
    DAOMedida dao;
    ArrayList<Medida> medidas;

    public Controller(Menu menu, DAOMedida dao){
        this.medidas = new ArrayList<Medida>();
        // this.menu = new MenuConsoleSuperior();
        this.menu = menu;
        this.dao = dao;
    }


    public void run(){
        Medida m1 = new Medida(1, "T" , 36.0, "ºC");
        Medida m2 = new Medida(2, "T" , 37.0, "ºC");
        Medida m3 = new Medida(2, "T" , 38.0, "ºC");

        medidas.add(m1);
        medidas.add(m2);
        medidas.add(m3);

        for(Medida medida : medidas) {
            menu.show(medida.getGraph());
        }

        test();
    }

    public void test(){
        menu.show("hola!");
    }
}
