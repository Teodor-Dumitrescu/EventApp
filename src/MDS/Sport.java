package MDS;

import java.util.List;

public class Sport extends Event {

    private String homeTeam;
    private String guestTeam;

    public Sport(String name, Location location, Organizer organizer, int nrTicketTypes, Ticket[] tickets, Date startDate, String homeTeam, String guestTeam) {
        super(name, location, organizer, nrTicketTypes, tickets, startDate);
        this.homeTeam = homeTeam;
        this.guestTeam = guestTeam;
    }

    public String getHomeTeam() {
        return homeTeam;
    }

    public String getGuestTeam() {
        return guestTeam;
    }

    @Override
    public void showMainInfo() {
        super.showMainInfo();
        System.out.println(homeTeam + " vs " + guestTeam);


    }
    public void showRestInfo(){}

}