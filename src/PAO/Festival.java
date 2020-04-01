package MDS;

public class Festival extends Event{

    private int nrDays;
    private Date endDate;

    public Festival(String name, Location location, Organizer organizer, int nrTicketTypes, Ticket[] tickets,
                    Integer nrDays, Date startDate, Date endDate) {
        super(name, location, organizer, nrTicketTypes, tickets, startDate);
        this.nrDays = nrDays;;
        this.endDate = endDate;
    }

    @Override
    public void showRestInfo() {
        System.out.println("Start date: " + this.startDate.getDay() + "." + this.startDate.getMonth() + "."
                + this.startDate.getYear() + "  End date: " + this.endDate.getDay() + "." + this.endDate.getMonth()
                + "." + this.endDate.getYear());
    }
}
