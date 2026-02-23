

public class Song{
    String id;
    String name;
    Long duration;  // en segundos
    Artist artist;

    public Song(String name, Long duration){
        this.name = name;
        this.duration = duration;
    }
}