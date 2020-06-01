package domain;

import general.Event;

public class Sport extends Event {

    private String home;
    private String guest;

    public Sport(int eventId, int organizerId, int eventType, String name, float standardTicketPrice,
                 int availableTicketsNumber, Location location, String home, String guest) {
        super(eventId, organizerId, eventType, name, standardTicketPrice, availableTicketsNumber, location);
        this.home = home;
        this.guest = guest;
    }

    public Sport(int organizerId, int eventType, String name, float standardTicketPrice, int availableTicketsNumber,
                 Location location, String home, String guest) {
        super(organizerId, eventType, name, standardTicketPrice, availableTicketsNumber, location);
        this.home = home;
        this.guest = guest;
    }

    public Sport(String home, String guest) {
        this.home = home;
        this.guest = guest;
    }

    public Sport() {
        super();
    }

    public String getHome() {
        return home;
    }

    public String getGuest() {
        return guest;
    }

    public void setHome(String home) {
        this.home = home;
    }

    public void setGuest(String guest) {
        this.guest = guest;
    }

    @Override
    public String presentationPrint(){
        return super.presentationPrint() + "      Name: " + super.getName() +
                "       Date: " + super.getLocation().getDate() + "     Price: $" + super.getStandardTicketPrice();
    }

    @Override
    public String toString() {
        return super.toString() + "," + home + "," + guest;
    }

    @Override
    public void print() {
        super.print();
        System.out.print("Sport Event," + home + " vs " + guest);
    }
}