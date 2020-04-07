package MDS;

import java.util.List;

abstract class Cultural extends Event {

    private List<String> guests;

    public Cultural(String name, Location location, Organizer organizer, int nrTicketTypes, Ticket[] tickets, Date startDate, List<String> guests) {
        super(name, location, organizer, nrTicketTypes, tickets, startDate);
        this.guests = guests;
    }

    public List<String> getGuests() {
        return guests;
    }

    @Override
    public void showMainInfo() {
        super.showMainInfo();
        System.out.println("Guests\n");
        for(String guest: guests){
            System.out.println(guest + "\n");
        }

    }

    public void showRestInfo(){}

}
