package domain;


public class Ticket {

    private int ticketId; //primary key
    private int clientId; //foreign key
    private int organizerId; //foreign key
    private int eventId;
    private int eventType;
    private String ticketIdentifier; //unique code for every ticket
    private String seat;
    protected float price;

    public Ticket(int ticketId, int clientId, int organizerId, int eventId, int eventType, String ticketIdentifier,
                  String seat, float price) {
        this.ticketId = ticketId;
        this.clientId = clientId;
        this.organizerId = organizerId;
        this.eventId = eventId;
        this.eventType = eventType;
        this.ticketIdentifier = ticketIdentifier;
        this.seat = seat;
        this.price = price;
    }

    public Ticket(int clientId, int organizerId, int eventId, int eventType, String ticketIdentifier, String seat,
                  float price) {
        this.clientId = clientId;
        this.organizerId = organizerId;
        this.eventId = eventId;
        this.eventType = eventType;
        this.ticketIdentifier = ticketIdentifier;
        this.seat = seat;
        this.price = price;
    }

    public Ticket() {
        ticketIdentifier = "Null ticket";
    }

    public int getClientId() {
        return clientId;
    }

    public int getOrganizerId() {
        return organizerId;
    }

    public int getEventId() {
        return eventId;
    }

    public int getTicketId() {
        return ticketId;
    }

    public int getEventType() {
        return eventType;
    }

    public String getTicketIdentifier() {
        return ticketIdentifier;
    }

    public String getSeat() {
        return seat;
    }

    public float getPrice() {
        return price;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public void setOrganizerId(int organizerId) {
        this.organizerId = organizerId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public String presentationPrint(){
        String tmp =  "TicketId #" + ticketId + "        Ticket identifier: " + ticketIdentifier +
                "           Price: " + price + "        Event: ";

        switch (eventType){
            case 1:
                tmp += " Music";
                break;
            case 2:
                tmp += " Sport";
                break;
            case 3:
                tmp += " Cultural";
                break;
        }

        return tmp;
    }

    @Override
    public String toString() {
        return ticketId + "," + clientId + "," + organizerId + "," + eventId + "," + eventType +  "," +
                ticketIdentifier + "," +
                seat + "," + price + "#";
    }

    public void print(){
        System.out.print(ticketIdentifier + "," + seat + ",");
    }

}
