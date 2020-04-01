package MDS;

public class Theater extends Event{

    private String actors;

    public Theater(String name, Location location, Organizer organizer, int nrTicketTypes,
                   Ticket[] tickets, String actors, Date startDate) {
        super(name, location, organizer, nrTicketTypes, tickets, startDate);
        this.actors = actors;
    }
    @Override
    public void showRestInfo() {
        System.out.println("Actors: " + this.actors);
    }
}
