public class Main{
    public void main(String[] args){
        System.out.println("Hola mundo");

        Song s1 = new Song("Diamond" , 3 * 60);
        Song s2 = new Song("Flashdance" , 4 * 60);
        Song s3 = new Song("Thriller" , 15 * 60);

        Playlist p1 = new Playlist();

        // p1.getSongs().add(s1);

        p1.addSong(s1);
        p1.addSong(s2);
        p1.addSong(s3);

        p1 = null;

    }
}