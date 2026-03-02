public class Medida {
    private String name;

    public Medida(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "medida{" + "name=" + name + '}';
    }

    public void show(){
        System.out.println(name);
    }
}
