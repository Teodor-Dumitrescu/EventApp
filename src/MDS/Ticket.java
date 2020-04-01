package MDS;

public class Ticket {

    private String eventName;
    private double price;
    private int count;
    private String type;

    public Ticket(double price, int count, String type, String eventName) {
        this.price = price;
        this.count = count;
        this.type = type;
        this.eventName = eventName;
    }

    public double getPrice() {
        return price;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getEventName() {
        return eventName;
    }

    public int getCount() {
        return count;
    }

    public String getType() {
        return type;
    }
}
