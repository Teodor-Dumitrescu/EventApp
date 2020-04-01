package MDS;

import java.util.Scanner;

public class Main {
    public static void main(String[] args){
/*
        Application app = new Application();

        Organizer quantic = new Organizer("Quantic Pub");
        Organizer artmania = new Organizer("ARTmania Festival");
        Organizer teatru = new Organizer("Teatru Bucuresti");
        Organizer asociatie = new Organizer("Asociatia Bucuresti");

        app.addOrganizer(quantic);
        app.addOrganizer(artmania);
        app.addOrganizer(teatru);
        app.addOrganizer(asociatie);

        Ticket[] tickets = new Ticket[1];
        tickets[0] = new Ticket(100,800,"Normal","Tyr");


        Event tyr = new Concert("Tyr", new Location("Bucuresti", "Quantic"), quantic,
                1, tickets, "Tyr", "Korpiklaani", "folk metal",
                new Date(20,10,2020));
        app.getOrganizerByName("Quantic Pub").addEvent(tyr);

        tickets = new Ticket[2];
        tickets[0] = new Ticket(200,1000,"Full Access","ARTmania Festival 2020");
        tickets[1] = new Ticket(250,50,"Meet and Greet","ARTmania Festival 2020");
        Event artmaniaFest = new Festival("ARTmania Festival 2020", new Location("Sibiu",
                "Piata Mare"), artmania, 1, tickets, 2,
                new Date(5,10,2020), new Date(7,10,2020));
        app.getOrganizerByName("ARTmania Festival").addEvent(artmaniaFest);

        tickets = new Ticket[1];
        tickets[0] = new Ticket(100,500,"Full Access","Alcest");
        Event alcest = new Concert("Alcest", new Location("Bucuresti", "Quantic"), quantic,
                1, tickets, "Alcest", "Birds in Row", "metal",
                new Date(10,8,2020));
        app.getOrganizerByName("Quantic Pub").addEvent(alcest);

        tickets = new Ticket[1];
        tickets[0] = new Ticket(0,80,"Free access","Party corona");
        Event party = new Party("Party corona", new Location("Bucuresti", "Corona Club"),
                asociatie, 1, tickets, 18, new Date(2,7,2020));
        app.getOrganizerByName("Asociatia Bucuresti").addEvent(party);

        //app.displayAllEvents();

        Client client = new Client("Teodor", "teodordumitrescu314@gmail.com", "adresa");

        int option1, option2;
        Scanner sc = new Scanner(System.in);

        while(true) {
            System.out.println("1. Display");
            System.out.println("2. Add");
            System.out.println("3. Buy");
            option1 = sc.nextInt();
            sc.nextLine();
            while(option1 <= 0 || option1 >= 4) {
                option1 = sc.nextInt();
                sc.nextLine();
            }
            switch(option1) {
                case 1: {
                    System.out.println("1. All events");
                    System.out.println("2. Festivals");
                    System.out.println("3. Concerts");
                    System.out.println("4. Theaters");
                    System.out.println("5. Parties");
                    System.out.println("6. All organizers");
                    System.out.println("7. All events under price X");
                    option2 = sc.nextInt();
                    sc.nextLine();
                    while(option2 <= 0 || option2 >= 8) {
                        option2 = sc.nextInt();
                        sc.nextLine();
                    }
                    switch(option2) {
                        case 1:
                            app.displayAllEvents();
                            break;
                        case 2:
                            app.displayAllFestivals();
                            break;
                        case 3:
                            app.displayAllConcerts();
                            break;
                        case 4:
                            app.displayAllTheaters();
                            break;
                        case 5:
                            app.displayAllParties();
                            break;
                        case 6:
                            app.displayAllOrganizers();
                            break;
                        case 7: {
                            System.out.println("Price limit: ");
                            double priceLimit = sc.nextDouble();
                            app.displayEventsPriceLimit(priceLimit);
                        }
                            break;
                    }
                }
                    break;
                case 2: {
                    System.out.println("1. Organizer");
                    System.out.println("2. Event");
                    option2 = sc.nextInt();
                    sc.nextLine();
                    while(option2 <= 0 || option2 >= 3) {
                        option2 = sc.nextInt();
                        sc.nextLine();
                    }
                    switch(option2) {
                        case 1:
                            app.createOrganizer();
                            break;
                        case 2: {
                            System.out.println("Who is the organizer? (choose one of the organizer names already in the " +
                                    "system: ");
                            String organizerName = sc.nextLine();
                            if(app.getOrganizerByName(organizerName) != null) {
                                app.getOrganizerByName(organizerName).addEvent();
                            }
                        }
                            break;
                    }
                }
                    break;
                case 3: {
                    System.out.println("Choose the event name: ");
                    String eventName = sc.nextLine();
                    if(app.getEventByName(eventName) != null) {
                        client.buyTicketsForEventByName(app, eventName);
                    }
                }
            }

         }
        */
        }

    }

