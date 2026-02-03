class Main{

    public static void main(String[] args){

        int i = 0;
        Integer i2 = 45;

        int j = 45;

        long w = 56;
        Long w2 = 56L;

        boolean flag = true;
        Boolean flag2 = Boolean.TRUE;

        float f = 45.0f;
        Float f2 = 45.0f;

        double d = 45.0;
        Double d2 = 45.0;

        char c = 'A';

        //char[] str = "hola";

        byte b = 0;

        System.out.println("Hola mundo");
        System.out.print("Hola mundo");
        System.out.print("Hola mundo");
        System.out.println("");
        System.out.println("String" + " " + "se pueden concatenar");

        // Numero pares del 0 al 20:

//        for (int k = 0; k < 20; k++){
//            // System.out.println("La k es " + k);
//
//            if (esPar(k)){
//                System.out.println("La k es " + k);
//            }
//        }

//        while(flag){
//
//        }
//
//        do{
//
//        }while(flag);

        while(true) {
            i++;
            if (i >= 10) {
                break;
            }
            if (esPar(i)) {
                continue;
            }
            // System.out.println("Hola mundo " + i);


            switch (i) {
                case 1:
                case 2:
                    System.out.println("el numero es 1 o 2");
                    break;
                case 3:
                    System.out.println("el numero es 3");
                    break;
                case 4:
                    System.out.println("el numero es 4");
                    break;

            }

            if (i == 1){
                System.out.println("el numero es 1");
            }
            else if (i == 2){
                System.out.println("el numero es 2");
            }
            else if (i == 3){
                System.out.println("el numero es 3");
            }
            else if (i == 4){
                System.out.println("el numero es 4");
            }
        }


    }

    public static boolean esPar(int k){
        return k % 2 == 0;
    }

}