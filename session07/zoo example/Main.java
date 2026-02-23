import java.util.ArrayList;

public class Main {

    public static void main(String[] args){

        Cat c = new Cat("Alice", "Persian", 9000);
        Dog d = new Dog("Bob", "Corgi" , 300);
        Bird b = new Bird("Charlie", "Parakeet" , 3);
        Fish f = new Fish("Dave", "Clown", 314159265);

        Animal animal = new Capybara("WWW", "WWW", 0);


//        c.showInformation();
//        d.showInformation();
//        b.showInformation();
//        f.showInformation();

        ArrayList<Animal> zoo = new ArrayList<Animal>();

        zoo.add(c);
        zoo.add(d);
        zoo.add(b);
        zoo.add(f);
        zoo.add(animal);

        for(Animal a : zoo){

//            if (a instanceof Dog){
//                ((Dog) a).bark();
//            }

            System.out.println(a.toString());
            a.talk();
        }


    }
}
