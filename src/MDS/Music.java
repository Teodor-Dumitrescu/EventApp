package MDS;

import java.util.List;

public class Music extends Event {

    private String formationName;
    private List<String> members;

    public Music(String name, Location location, Organizer organizer, int nrTicketTypes, Ticket[] tickets, Date startDate, String formationName, List<String> members) {
        super(name, location, organizer, nrTicketTypes, tickets, startDate);
        this.formationName = formationName;
        this.members = members;
    }

    public String getFormationName() {
        return formationName;
    }

    public List<String> getMembers() {
        return members;
    }

    @Override
    public void showMainInfo() {
        super.showMainInfo();
        System.out.println("Formation name: " + formationName + "\n");
        System.out.println("Members:\n");
        for(String member: members){
            System.out.println(member + "\n");
        }

    }

    public void showRestInfo(){}
}
