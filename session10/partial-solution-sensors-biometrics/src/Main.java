public class Main {
    public static void main(String[] args) {

        // Menu menu = new MenuConsoleSuperior();
        Menu menu = new MenuConsole();
        DAOMedida dao = new DAOMedidaCSVFile("fichero.csv");

        // Dependency injection of menu and dao:
        Controller c = new Controller(menu , dao);
        c.run();
    }
}
