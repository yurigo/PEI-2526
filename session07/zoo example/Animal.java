public abstract class Animal {
    private String name;
    private String type;
    private int age;

    public Animal(String name, String type, int age) {
        this.name = name;
        this.type = type;
        this.age = age;
    }

    public void showInformation() {
        System.out.println("Name: " + name);
        System.out.println("Type: " + type);
        System.out.println("Age: " + age);
    }

    abstract public void talk();

    public String toString() {
        return "el animal " + name + " " + type + " " + age;
    }
}
