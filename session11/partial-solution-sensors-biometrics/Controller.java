import java.rmi.UnexpectedException;
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

        menu.showWelcome();

        Boolean askAgain = true;

        while(askAgain){
            menu.showMenu();

            Integer option = menu.getInteger();

            switch(option){
                case 1 -> doOption1();
                case 2 -> doOption2();
                case 3 -> doOption3();
                case 4 -> doOption4();
                case 0 -> askAgain = false;
                default -> iDontUnderstand();
            }



        }

        menu.show("Goodbye!");


    }

    private void doOption1(){
        menu.show("has escogido la opcion 1");
    }

    private void doOption2(){
        menu.show("has escogido la opcion 2");

        throw new RuntimeException("la opcion 2 no está implementada todavía!");
    }

    private void doOption3(){
        menu.show("has escogido la opcion 3");
    }

    private void doOption4(){
        menu.show("has escogido la opcion 4");
    }

    private void quit(){
        System.exit(0);
    }

    private void iDontUnderstand(){
        menu.showError("Entrada invalida. vuelve a intentarlo");
    }


}
