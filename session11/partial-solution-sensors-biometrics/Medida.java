public class Medida {
    Integer id;
    String type;
    Double valor;
    String unitat;

    public Medida(Integer id, String type, Double valor, String unitat) {
        this.id = id;
        this.type = type;
        this.valor = valor;
        this.unitat = unitat;
    }

    public String getGraph(){
        switch (type){
            case "T" -> { return graphTemperatura(); }
            case "O" -> { return graphOxigen(); }
        }
        return null;
    }

    private String graphOxigen() {
        return "";
    }

    public String graphTemperatura(){
        // minimo 35
        // # cada .5
        Integer n = calculaLosHashtagsDeTemperatura();
        return "#".repeat(n);
    }

    private Integer calculaLosHashtagsDeTemperatura() {
        Double MINIMO = 35.0;
        Double CADA_X = 0.5;
        return new Double((this.valor - MINIMO) / CADA_X).intValue();
    }
}
