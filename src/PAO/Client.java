package PAO;

import java.util.ArrayList;
import java.util.List;

public class Client {
    private String name;
    private String email;
    private String address;
    private List<Ticket> tickets;

    public Client(String name, String email, String address) {
        this.name = name;
        this.email = email;
        this.address = address;
        tickets = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getAdress() {
        return address;
    }

    public void buyTicketsForEventByName(Application app, String eventName) {
        Event event = app.getEventByName(eventName);
        if(event == null) {
            System.out.println("The event doesn't exist!");
            return ;
        }

        Ticket ticket = app.getEventByName(eventName).sellTicket();
        if(ticket == null) {
            System.out.println("Transaction failed!");
            return ;
        }
        else {
            this.tickets.add(ticket);
        }
    }
}
