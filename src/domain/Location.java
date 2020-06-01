package domain;

public class Location {

    private String date;
    private int addressId; //store foreign key for event

    public Location(String date, int addressId) {
        this.date = date;
        this.addressId = addressId;
    }

    public String getDate() {
        return date;
    }

    public int getAddressId() {
        return addressId;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }

    @Override
    public String toString() {
        return date + "," + addressId;
    }

}
