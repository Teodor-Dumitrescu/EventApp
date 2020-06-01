package domain;

final public class Address {

    private int addressId; //primary key
    private String country;
    private String city;
    private String street;
    private String phoneNumber;

    public Address(int addressId, String country, String city, String street, String phoneNumber) {
        this.addressId = addressId;
        this.country = country;
        this.city = city;
        this.street = street;
        this.phoneNumber = phoneNumber;
    }

    public Address(String country, String city, String street, String phoneNumber) {
        this.country = country;
        this.city = city;
        this.street = street;
        this.phoneNumber = phoneNumber;
    }

    public int getAddressId() {
        return addressId;
    }

    public String getCity() {
        return city;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getCountry() {
        return country;
    }

    public String getStreet() {
        return street;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return city + "," + country + "," + street + "," + phoneNumber;
    }

}
