
public class Chessboard{
    private ArrayList<Cell> cells;


    public Chessboard(){
        this.cells = new ArrayList<Cell>();

        for (int i = 0; i < 64; i++){
            this.cells = new Cell(i , i % 2 == 0 ? "white" : "black");
        }
    }
}