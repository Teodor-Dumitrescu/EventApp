package MDS;

import MDS.*;

import java.util.List;

public class Sport extends Event {

    private List<String> refereesName;
    private String homeTeam;
    private String guestTeam;

    public Sport(String name, Location location, Organizer organizer, int nrTicketTypes, Ticket[] tickets, Date startDate, List<String> refereesName, String homeTeam, String guestTeam) {
        super(name, location, organizer, nrTicketTypes, tickets, startDate);
        this.refereesName = refereesName;
        this.homeTeam = homeTeam;
        this.guestTeam = guestTeam;
    }

    public void showRestInfo(){}
    protected void setTicketsNumber(){}
    protected void setPrice(){}
}