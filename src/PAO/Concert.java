package PAO;

public class Concert extends Event{

    private String mainAct;
    private String supportActs;
    private String genre;

    public Concert(String name, Location location, Organizer organizer, int nrTicketTypes,
                   Ticket[] tickets, String mainAct, String supportActs, String genre, Date startDate) {
        super(name, location, organizer, nrTicketTypes, tickets, startDate);
        this.mainAct = mainAct;
        this.supportActs = supportActs;
        this.genre = genre;
    }

    @Override
    public void showRestInfo() {
        System.out.println("Main act: " + this.mainAct);
        System.out.println("Support act(s): " + this.supportActs);
        System.out.println("Genre: " + this.genre);
    }

}
