package PAO;

import java.util.Objects;
import java.util.Scanner;

public abstract class Event {

    protected String name;
    protected Location location;
    protected Organizer organizer;
    protected String description;
    protected Ticket[] tickets;
    protected Integer nrTicketTypes;
    protected Date startDate;

    /*@Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return Objects.equals(name, event.name);
    }*/

    public Event(String name, Location location, Organizer organizer, int nrTicketTypes, Ticket[] tickets,
                 Date startDate) {
        this.name = name;
        this.location = location;
        this.organizer = organizer;
        this.description = "No description.";
        this.nrTicketTypes = nrTicketTypes;
        this.tickets = tickets;
        this.startDate = startDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public String getName() {
        return name;
    }

    public Location getLocation() {
        return location;
    }

    public Ticket[] getTickets() {
        return tickets;
    }

    public Integer getNrTicketTypes() {
        return nrTicketTypes;
    }

    public Organizer getOrganizer() {
        return organizer;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Ticket sellTicket() {
        System.out.println("Choose the type of ticket you want to buy: ");
        for(int i = 0; i < this.nrTicketTypes; i++){
            System.out.println(String.valueOf(i+1) + ". " + this.tickets[i].getType() + " price: " +
                    this.tickets[i].getPrice() + " tickets left: " + this.tickets[i].getCount());
        }

        Scanner sc = new Scanner(System.in);

        int option = -1;
        while(option < 0 || option >= this.nrTicketTypes){
            option = sc.nextInt() - 1;
            sc.nextLine();
        }

        if(this.getTickets()[option].getCount() == 0) {
            System.out.println("Sorry, there are no tickets left in this category...");
            return null;
        }

        System.out.println("How many tickets do you want to buy?: ");
        int nrBilete = -1;
        while(nrBilete < 0 || nrBilete >= this.tickets[option].getCount()) {
            nrBilete = sc.nextInt();
            sc.nextLine();
        }

        this.tickets[option].setCount(tickets[option].getCount() - nrBilete);
        System.out.print("You have successfully bought " + String.valueOf(nrBilete) + " tickets of type " +
                this.tickets[option].getType() + " for " + this.name + "!\n");
        Ticket ticket = new Ticket(this.tickets[option].getPrice(), nrBilete, this.tickets[option].getType(),
                this.name);
        return ticket;
    }
//    @Override
//    public int compareTo(Event event) {
//        if(this.startDate.getDay() == event.startDate.getDay()
//                && this.startDate.getMonth() == event.startDate.getMonth()
//                && this.startDate.getYear() == event.startDate.getYear())
//            return 0; // events have the same start date
//        if(this.startDate.getYear() < event.startDate.getYear())
//            return -1;
//        if(this.startDate.getYear() == event.startDate.getYear()) {
//            if(this.startDate.getMonth() < event.startDate.getMonth())
//                return -1;
//            if(this.startDate.getMonth() == event.startDate.getMonth()) {
//                if(this.startDate.getDay() < event.startDate.getDay())
//                    return -1;
//            }
//        } // current event starts earlier than 'event'
//
//        return 1; // 'event' starts earlier than current event
//        /*if(this.startDate.getDay() < event.startDate.getDay())
//            return -1;
//        else if(this.startDate.getDay() == event.startDate.getDay())
//            return 0;
//        return 1;*/
//    }


    /*@Override
    public int compareTo(Event event) {
        if(this.startDate.getYear() < event.startDate.getYear())
            return -1;
        else if(this.startDate.getYear() > event.startDate.getYear())
            return 1;
        else {
            if(this.startDate.getMonth() < event.startDate.getMonth())
                return -1;
            else if(this.startDate.getMonth() > event.startDate.getMonth())
                return 1;
            else {
                return Integer.compare(this.startDate.getDay(), event.startDate.getDay());
            }
        }
    }*/

//    @Override
//    public int compareTo(Event event) {
//        return this.name.compareTo(event.name);
//    }

    public void showMainInfo() {
        System.out.println("Name: " + this.name);
        System.out.println("Location: " + this.location.getLocationName() + ", " + this.location.getCity());
        System.out.println("Organizer: " + this.organizer.getName());
        System.out.println("Description: " + this.description);
        this.showRestInfo();
        System.out.println();
    }

    public abstract void showRestInfo();
}
