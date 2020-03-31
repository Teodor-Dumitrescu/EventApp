package PAO;

public class Location {
    private String city;
    private String locationName;

    public String getCity() {
        return city;
    }

    public String getLocationName() {
        return locationName;
    }

    public Location(String city, String locationName) {
        this.city = city;
        this.locationName = locationName;
    }
}
