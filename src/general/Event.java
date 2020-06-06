package general;

import domain.Location;

abstract public class Event {

    private int eventId; //primary key
    private int organizerId; //foreign key
    private int eventType;
    private String name;
    protected float standardTicketPrice;
    private int availableTicketsNumber;
    private Location location;

    public Event(int eventId, int organizerId, int eventType, String name, float standardTicketPrice,
                 int availableTicketsNumber, Location location) {
        this.eventId = eventId;
        this.organizerId = organizerId;
        this.eventType = eventType;
        this.name = name;
        this.standardTicketPrice = standardTicketPrice;
        this.availableTicketsNumber = availableTicketsNumber;
        this.location = location;
    }

    public Event(int organizerId, int eventType, String name, float standardTicketPrice, int availableTicketsNumber,
                 Location location) {
        this.organizerId = organizerId;
        this.eventType = eventType;
        this.name = name;
        this.standardTicketPrice = standardTicketPrice;
        this.availableTicketsNumber = availableTicketsNumber;
        this.location = location;
    }

    public Event() {
        this.name = "null event";
    }

    public int getEventId() {
        return eventId;
    }

    public int getOrganizerId() {
        return organizerId;
    }

    public String getName() {
        return name;
    }

    public float getStandardTicketPrice() {
        return standardTicketPrice;
    }

    public int getEventType() {
        return eventType;
    }

    public int getAvailableTicketsNumber() {
        return availableTicketsNumber;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStandardTicketPrice(float standardTicketPrice) {
        this.standardTicketPrice = standardTicketPrice;
    }

    public Location getLocation() {
        return location;
    }

    public void setEventType(int eventType) {
        this.eventType = eventType;
    }

    public void setAvailableTicketsNumber(int availableTicketsNumber) {
        this.availableTicketsNumber = availableTicketsNumber;
    }

    /**
     * Show the important elements in the main event show zone in GUI interface
     *
     * @return string
     */
    public String presentationPrint(){
        return "EventId: #" + eventId + "#" + eventType;
    }

    @Override
    public String toString() {
        return eventId + "," + organizerId + "," + location.getAddressId() + "," + eventType + "," + name + "," +
                standardTicketPrice + "," + location.getDate() + "," + availableTicketsNumber + "#";
    }

    public void print(){
        System.out.print(name + "," + standardTicketPrice + " EUR," + location.getDate() + "," + "addressID: " +
                location.getAddressId() + ",");
    }
}
