public class Main {

    public static void main(String[] args){

        Menu menu = new MenuConsole();
        Controller controller = new Controller(menu);

        for(;;){
            try {
                controller.run();
            } catch (MyExceptionalException e) {
                menu.show("My exceptional exception: " + e.getMessage());
            } catch (NumberFormatException e) {
                menu.show(":( " + e.getMessage());
            } catch (Exception e) {
                menu.show("Excepcion global: " + e.getMessage());
            }
        }

    }
}
