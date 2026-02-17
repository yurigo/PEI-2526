public class Combat{
    private String title;
    private String location;
    private ArrayList<Unit> participants;
    
    public Combat(){
        participants = new ArrayList<Unit>();
        participants.add(new Unit());
        participants.add(new Unit());
    }
}