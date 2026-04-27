import java.util.List;

/**
 * Representa una canción con título, artista y lista de sonidos (notas).
 */
public class Song {
    private final String title;
    private final String artist;
    private final List<Sound> sounds;

    public Song(String title, String artist, List<Sound> sounds) {
        this.title = title;
        this.artist = artist;
        this.sounds = sounds;
    }

    public String getTitle()       { return title; }
    public String getArtist()      { return artist; }
    public List<Sound> getSounds() { return sounds; }

    @Override
    public String toString() {
        return title + " – " + artist + " (" + sounds.size() + " notas)";
    }
}
