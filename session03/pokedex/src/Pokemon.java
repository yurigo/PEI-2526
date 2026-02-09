public class Pokemon {
    private String name;
    private String tipo;
    private Integer nivel;
    private Integer vida;

    public Pokemon(String name , String tipo){
        this.setName(name);
        this.tipo = tipo;
        this.nivel = 0;
        this.vida = 0;
    }

    private String getName(){
        return name;
    }

    private void setName(String name){
        if (name.equals("")){
            this.name = "NO SE PUEDE VACIO";
        } else{
            this.name = name;
        }
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Integer getNivel() {
        return nivel;
    }

    public void setNivel(Integer nivel) {
        this.nivel = nivel;
    }

    public Integer getVida() {
        return vida;
    }

    public void setVida(Integer vida) {
        this.vida = vida;
    }

    // No quiero acoplarme con el system.out, no es mi responsabilidad.
//    public void muestrate(){
//        System.out.println(this.getName() + ": " + this.getTipo() + ", " + this.getNivel() + ", " + this.getVida() + ", " + this.getNivel());
//    }

    public String getInformation(){
        return this.getName() + ": " + this.getTipo() + ", " + this.getNivel() + ", " + this.getVida() + ", " + this.getNivel();
    }

    public void subirNivel() {
        this.nivel += 1;
        this.vida += 100;
    }
}
