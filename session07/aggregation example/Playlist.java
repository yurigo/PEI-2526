

public class Playlist{

    String id;
    String name;
    Long duration;
    ArrayList<Song> songs;

    public Playlist(String name){
        this.name = name;
        this.songs = new ArrayList<Song>();
    }

    public addSong(Song song)}{
        // si la canción ya existe en songs...
        // no se introduce

        this.songs.add(song);
    }

    // getters y setters
    // public ArrayList<Song> getSongs(){
    //     return this.songs;
    // }

    // public void setSongs(ArrayList<Song> songs){
    //     this.songs = songs;
    // }

    // si no los voy a usar
    // pues no los creo.

    // Es mejor usar el addSong que hemos creado
    // porque abstrae la implementación.
    

}