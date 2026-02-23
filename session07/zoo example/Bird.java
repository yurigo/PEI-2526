public class Bird extends Animal {
    public Bird(String name, String type, int age) {
        super(name, type, age);
    }

    public void talk(){
        System.out.println("Chirp chirp");
    }
}
