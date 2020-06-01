package domain;

public class Organizer {

    private int organizerId;
    private String username;
    private String password;
    private String name;
    private int addressId;

    public Organizer(int organizerId, String username, String password, String name, int addressId) {
        this.organizerId = organizerId;
        this.username = username;
        this.password = password;
        this.name = name;
        this.addressId = addressId;
    }

    public Organizer(String username, String password, String name, int addressId) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.addressId = addressId;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public int getOrganizerId() {
        return organizerId;
    }

    public int getAddressId() {
        return addressId;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return username + "," + password + "," + name + "," + addressId;
    }

    public void print(){
        System.out.print(username + "," + name + "," + addressId);
    }
}
