public class Controller {

    Menu menu;

    public Controller(Menu menu) {
        this.menu = menu;
    }

    public void run() throws MyExceptionalException, Exception {
        this.menu.show("Hola!!");

        String numeroText = this.menu.getString("Dame un numero del 1 a 10");

        Integer numero = Integer.parseInt(numeroText);



        if (numero > 10) {
            throw new MoreThanTenException();
        } else if (numero < 1) {
            throw new LessThanZeroException();
        } else if (numero == 5) {
            throw new NumberFiveException();
        } else if (numero == 6) {
            throw new RuntimeException("El numero no puede ser 6");
        } else if (numero == 7) {
            throw new Exception("El numero no puede ser 7");
        }




        this.menu.show(numero.toString());


    }
}
