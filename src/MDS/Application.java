package MDS;

import java.util.*;


public class Application {
    private PriorityQueue<Event> allEvents;
    private List<Organizer> allOrganizers;

    public Application() {
        this.allEvents = new PriorityQueue<>(10, new EventComparator());
        this.allOrganizers = new ArrayList<>();
    }

    public void addOrganizer(Organizer organizer) {
        organizer.setApplication(this);
        this.allOrganizers.add(organizer);
    }
    public void createOrganizer() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Name: ");
        String name = sc.nextLine();
        Organizer organizer = new Organizer(name);
        this.addOrganizer(organizer);
    }

    public void addEvent(Event event) {
        this.allEvents.add(event);
    }

    public void displayAllOrganizers() {
        for(Organizer organizer : this.allOrganizers) {
            System.out.println("Organizator: " + organizer.getName());
            System.out.print("Evenimente: ");
            organizer.showEvents();
            System.out.print("\n");
        }
    }

    public Organizer getOrganizerByName(String name) {
        for(Organizer organizer : this.allOrganizers) {
            if(organizer.getName().equals(name))
                return organizer;
        }
        return null;
    }

    public Event getEventByName(String name) {
        for(Event event : this.allEvents) {
            if(event.getName().equals(name))
                return event;
        }
        return null;
    }

    public void displayAllEvents() {
        for(Event event : this.allEvents) {
            event.showMainInfo();
        }
    }

    public void displayAllFestivals() {
        for(Event event : this.allEvents)
            if(event instanceof Festival)
                event.showMainInfo();
    }

    public void displayAllConcerts() {
        for(Event event : this.allEvents)
            if (event instanceof Concert)
                event.showMainInfo();
    }

    public void displayAllTheaters() {
        for(Event event : this.allEvents)
            if (event instanceof Theater)
                event.showMainInfo();
    }

    public void displayAllParties() {
        for(Event event : this.allEvents)
            if (event instanceof Party)
                event.showMainInfo();
    }

    public void displayEventsPriceLimit(double priceLimit) { // display all events with at least one ticket type price
//        less than or equal to 'priceLimit'
        for(Event event : this.allEvents) {
            boolean ok = false;
            for(int i = 0; i < event.getNrTicketTypes(); i++) {
                if(event.getTickets()[i].getPrice() <= priceLimit && event.getTickets()[i].getCount() > 0) {
                    ok = true;
                    break;
                }
            }
            if(ok)
                event.showMainInfo();
        }
    }
}

