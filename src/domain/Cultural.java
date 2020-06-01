package domain;

import general.Event;

public class Cultural extends Event {

    private String type;
    private String guests;

    public Cultural(int eventId, int organizerId, int eventType, String name, float standardTicketPrice,
                    int availableTicketsNumber, Location location, String type, String guests) {
        super(eventId, organizerId, eventType, name, standardTicketPrice, availableTicketsNumber, location);
        this.type = type;
        this.guests = guests;
    }

    public Cultural(int organizerId, int eventType, String name, float standardTicketPrice, int availableTicketsNumber,
                    Location location, String type, String guests) {
        super(organizerId, eventType, name, standardTicketPrice, availableTicketsNumber, location);
        this.type = type;
        this.guests = guests;
    }

    public Cultural(String type, String guests) {
        this.type = type;
        this.guests = guests;
    }

    public Cultural() {
        super();
    }

    public String getGuests() {
        return guests;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setGuests(String guests) {
        this.guests = guests;
    }

    @Override
    public String presentationPrint(){
        return super.presentationPrint() + "      Name: " + super.getName() +
                "       Date: " + super.getLocation().getDate() + "     Price: $" + super.getStandardTicketPrice();
    }

    @Override
    public String toString() {
        return super.toString() + "," + type + "," + guests;
    }

    @Override
    public void print() {
        super.print();
        System.out.print("Cultural Event," + type + "," + guests);
    }
}
