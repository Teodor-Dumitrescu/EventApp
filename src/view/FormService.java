package view;

public class FormService {

    MainPanel guestFrame;
    UpdateAccountForm updateAccountForm;
    AddEventForm addEventForm;
    TicketsForm ticketsForm;

    public FormService() {
        guestFrame = new MainPanel("Vanzare Bilete");
    }

    public void setGuestFrame(){
        guestFrame.setSize(1366,728);
        guestFrame.setVisible(true);
    }
}
