package MDS;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Organizer {
    private String name;
    private List<Event> events;
    private Application application;

    public String getName() {
        return name;
    }

    public Organizer(String name) {
        this.name = name;
        this.events = new ArrayList<Event>();
    }

    public void setApplication(Application application){
        this.application = application;
    }

    public void showEvents() {
        for(Event event: this.events) {
            System.out.print(event.getName() + " ");
        }
    }

    public void addEvent() {
        Event event;
        System.out.println("Choose the event type you would like to add: ");
        System.out.println("1. Festival");
        System.out.println("2. Concert");
        System.out.println("3. Theater");
        System.out.println("4. Party");
        Scanner sc = new Scanner(System.in);

        int eventType = -1;
        while(eventType < 1 || eventType > 4) {
            eventType = sc.nextInt();
            sc.nextLine();
        }

        System.out.println("City: ");
        String city = sc.nextLine();

        System.out.println("Name of location: ");
        String locationName = sc.nextLine();

        Location location = new Location(city, locationName);

        System.out.println("Name of event: ");
        String eventName = sc.nextLine();

        System.out.println("Event description: ");
        String description = sc.nextLine();

        System.out.println("Number of ticket types: ");
        int nrTicketTypes = sc.nextInt();
        sc.nextLine();

        Ticket[] tickets = new Ticket[nrTicketTypes];
        String ticketType;
        int nrTickets;
        double ticketPrice;
        for(int i = 0; i < nrTicketTypes; i++) {
            System.out.println("Ticket type: ");
            ticketType = sc.nextLine();
            System.out.println("Ticket count: ");
            nrTickets = sc.nextInt();
            sc.nextLine();
            System.out.println("Ticket price: ");
            ticketPrice = sc.nextInt();
            sc.nextLine();
            tickets[i] = new Ticket(ticketPrice, nrTickets, ticketType, eventName);
        }

        int startDay, startMonth, startYear;

        if(eventType == 1) { // Festival
            System.out.println("Number of days: ");
            int nrDays = sc.nextInt();
            sc.nextLine();

            int endMonth, endDay, endYear;
            System.out.println("Start day: ");
            startDay = sc.nextInt();
            sc.nextLine();
            System.out.println("Start month: ");
            startMonth = sc.nextInt();
            sc.nextLine();
            System.out.println("Start year: ");
            startYear = sc.nextInt();
            sc.nextLine();

            Date startDate = new Date(startDay, startMonth, startYear);

            System.out.println("End day: ");
            endDay = sc.nextInt();
            sc.nextLine();
            System.out.println("End month: ");
            endMonth = sc.nextInt();
            sc.nextLine();
            System.out.println("End year: ");
            endYear = sc.nextInt();
            sc.nextLine();

            Date endDate = new Date(endDay, endMonth, endYear);

            event = new Festival(eventName, location, this, nrTicketTypes, tickets, nrDays, startDate,
                    endDate);
        }

        else { // anything else has a single date
            System.out.println("Day: ");
            startDay = sc.nextInt();
            sc.nextLine();

            System.out.println("Month: ");
            startMonth = sc.nextInt();
            sc.nextLine();

            System.out.println("Year: ");
            startYear = sc.nextInt();
            sc.nextLine();

            Date date = new Date(startDay, startMonth, startYear);

            if(eventType == 2) { // Concert
                System.out.println("Main act: ");
                String mainAct = sc.nextLine();

                System.out.println("Support acts: ");
                String supportActs = sc.nextLine();

                System.out.println("Genre: ");
                String genre = sc.nextLine();

                event = new Concert(eventName, location, this, nrTicketTypes, tickets, mainAct, supportActs,
                        genre, date);
            }

            else if(eventType == 3) { // Theatre
                System.out.println("Actors: ");
                String actors = sc.nextLine();

                event = new Theater(eventName, location, this, nrTicketTypes, tickets, actors, date);
            }

            else { // Party
                System.out.println("Age restriction? 0.No 1.Yes ");
                int restriction = sc.nextInt();
                sc.nextLine();
                int requiredAge;

                if(restriction == 0) {
                    requiredAge = 0;
                }
                else {
                    System.out.println("Required aged: ");
                    requiredAge = sc.nextInt();
                    sc.nextLine();
                }

                event = new Party(eventName, location, this, nrTicketTypes, tickets, requiredAge, date);
                event.setDescription(description);
            }
        }
        this.events.add(event);
        this.application.addEvent(event);
    }

    public void addEvent(Event event) {
        this.events.add(event);
        this.application.addEvent(event);
    }

}
