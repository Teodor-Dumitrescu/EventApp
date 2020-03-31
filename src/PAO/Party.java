package PAO;

public class Party extends Event{

    private int requiredAge;

    public Party(String name, Location location, Organizer organizer, int nrTicketTypes, Ticket[] tickets,
                 int requiredAge, Date startDate) {
        super(name, location, organizer, nrTicketTypes, tickets, startDate);
        this.requiredAge = requiredAge;
    }
    @Override
    public void showRestInfo() {
        if(requiredAge > 0)
            System.out.println("Required age: " + this.requiredAge);
    }
}
