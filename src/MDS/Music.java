package MDS;

import MDS.*;

import java.util.List;

public class Music extends Event {

    private String formationName;
    private List<String> membersName;

    public Music(String name, Location location, Organizer organizer, int nrTicketTypes, Ticket[] tickets, Date startDate, String formationName, List<String> membersName) {
        super(name, location, organizer, nrTicketTypes, tickets, startDate);
        this.formationName = formationName;
        this.membersName = membersName;
    }


    public void showRestInfo(){}
    protected void setTicketsNumber(){}
    protected void setPrice(){}
}
