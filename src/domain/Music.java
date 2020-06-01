package domain;

import general.Event;

public class Music extends Event {

    private String musicGenre;
    private String formationName;

    public Music(int eventId, int organizerId, int eventType, String name, float standardTicketPrice,
                 int availableTicketsNumber, Location location, String musicGenre, String formationName) {
        super(eventId, organizerId, eventType, name, standardTicketPrice, availableTicketsNumber, location);
        this.musicGenre = musicGenre;
        this.formationName = formationName;
    }

    public Music(int organizerId, int eventType, String name, float standardTicketPrice, int availableTicketsNumber,
                 Location location, String musicGenre, String formationName) {
        super(organizerId, eventType, name, standardTicketPrice, availableTicketsNumber, location);
        this.musicGenre = musicGenre;
        this.formationName = formationName;
    }

    public Music(String musicGenre, String formationName) {
        this.musicGenre = musicGenre;
        this.formationName = formationName;
    }

    public Music() {
        super();
    }

    public String getMusicGenre() {
        return musicGenre;
    }

    public String getFormationName() {
        return formationName;
    }

    public void setMusicGenre(String musicGenre) {
        this.musicGenre = musicGenre;
    }

    public void setFormationName(String formationName) {
        this.formationName = formationName;
    }

    @Override
    public String presentationPrint(){
        return super.presentationPrint() + "      Name: " + super.getName() +
                "       Date: " + super.getLocation().getDate() + "     Price: $" + super.getStandardTicketPrice();
    }

    @Override
    public String toString() {
        return super.toString() + "," + musicGenre + "," + formationName;
    }

    @Override
    public void print() {
        super.print();
        System.out.print("Music Event," + musicGenre + "," + formationName);
    }
}
