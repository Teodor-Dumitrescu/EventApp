package MDS;

import MDS.Event;
import MDS.Location;

import java.util.List;

abstract class Cultural extends Event {

    private List<String> guests;

    public Cultural(String name, Location location, Organizer organizer, int nrTicketTypes, Ticket[] tickets, Date startDate, List<String> guests) {
        super(name, location, organizer, nrTicketTypes, tickets, startDate);
        this.guests = guests;
    }

    public void showRestInfo(){}
    protected void setTicketsNumber(){}
    protected void setPrice(){}
}
