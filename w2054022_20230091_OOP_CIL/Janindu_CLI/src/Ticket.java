public class Ticket {
    private static int idCounter = 1;
    private int id;

    public Ticket() {
        this.id = idCounter++;
    }

    @Override
    public String toString() {
        return "Ticket " + id;
    }
}
