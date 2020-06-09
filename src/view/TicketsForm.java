package view;

import domain.*;
import general.Event;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class TicketsForm extends JFrame {
    private JTextField searchTicketTextField;
    private JPanel ticketsPanel;
    private JList ticketList;
    private JButton cancelTicketButton;
    private JButton showTicketsButton;
    private JLabel ticketIdentifierLabel;
    private JLabel ticketIdentifierValueLabel;
    private JLabel eventNameLabel;
    private JLabel eventNameValueLabel;
    private JLabel organizerLabel;
    private JLabel organizerValueLabel;
    private JLabel seatLabel;
    private JLabel seatValueLabel;
    private JLabel priceLabel;
    private JLabel priceValueLabel;
    private JLabel dateLabel;
    private JLabel dateValueLabel;
    private JLabel cityLabel;
    private JLabel cityValueLabel;
    private JLabel ticketInfoLabel;

    private MainForm mainForm;

    public TicketsForm(String title, MainForm mainForm) throws HeadlessException {
        super(title);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setContentPane(ticketsPanel);
        this.pack();

        this.mainForm = mainForm;
        setTicketDescribeLabels();
        showAllTickets(mainForm);
        ticketInfoLabel.setVisible(false);

        /*Sample function which is used as a place holder for the search box.*/
        searchTicketTextField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                if(searchTicketTextField.getText().equals("Search Ticket")){
                    searchTicketTextField.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                if(searchTicketTextField.getText().isEmpty()){
                    searchTicketTextField.setText("Search Ticket");
                }
            }
        });

        /*A function that gives functionality to the button showTicketsButton. */
        showTicketsButton.addActionListener(actionEvent -> {
            setTicketDescribeLabels();
            showAllTickets(mainForm);
        });


        /*A function that gives functionality to the button cancelTicketButton.
        To cancel a ticket must be selected only one ticket. */
        cancelTicketButton.addActionListener(actionEvent -> {

            //get all selected tickets
            int[] indexList = ticketList.getSelectedIndices();

            //check if is only one selected ticket
            if(indexList.length < 1){
                ticketInfoLabel.setText("Choose one ticket to cancel !");
                ticketInfoLabel.setVisible(true);
                return;
            }

            if(indexList.length > 1){
                ticketInfoLabel.setText("Choose just one ticket to cancel !");
                ticketInfoLabel.setVisible(true);
                return;
            }

            //TODO posibila implementare pt anularea mai multor tickete in acelasi timp

            ticketInfoLabel.setVisible(false);

            //extract the selected ticket
            List<Ticket> tmpList = new ArrayList<>();
            for(int idx: indexList){
                String tmp = ticketList.getModel().getElementAt(idx).toString();
                int ticketId = Integer.parseInt(tmp.split("#")[1].split(" ")[0]);
                tmpList.add(mainForm.getCompanyService().getTicketService().get(ticketId));
            }

            //cancel the ticket
            for(Ticket ticket: tmpList){

                //when I cancel a ticket I update available tickets number for that event
                Event event = mainForm.getCompanyService().getEventService().get(ticket.getEventId(),ticket.getEventType());

                if(event != null) { //only if event is still on
                    event.setAvailableTicketsNumber(event.getAvailableTicketsNumber() + 1);
                    mainForm.getCompanyService().getEventService().update(event);
                }

                mainForm.getCompanyService().getAuditService().addLogMessage("Cancel Ticket " + ticket.getTicketIdentifier());
                mainForm.getCompanyService().getTicketService().remove(ticket);
                ticketInfoLabel.setText("Ticket " + ticket.getTicketIdentifier() + " was canceled");
                ticketInfoLabel.setVisible(true);

            }

            //refresh the ticket display area
            showAllTickets(mainForm);
            setTicketDescribeLabels();
            mainForm.setEventLabels();
        });

        /*A function that gives functionality to the search ticket box.
        The search will be done as you enter data from the keyboard. */
        searchTicketTextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);

                showAllTickets(mainForm);

                //get the search value if exists
                //search ticket is the default place holder and will be ignored
                String searchValue = searchTicketTextField.getText().toLowerCase();
                if(searchValue.equals("search ticket")){
                    return;
                }

                //get tickets model (tickets model is the list of tickets from display area)
                DefaultListModel<String> tmpLoadModel = new DefaultListModel<>();
                int sizeModel = ticketList.getModel().getSize();

                //go through all the tickets drawn
                for(int idx = 0; idx < sizeModel; idx++){

                    //extract ticket
                    String tmp = ticketList.getModel().getElementAt(idx).toString().toLowerCase();
                    int ticketId = Integer.parseInt(tmp.split("#")[1].split(" ")[0]);
                    Ticket ticket = mainForm.getCompanyService().getTicketService().get(ticketId);

                    //create the search string
                    String searchString = ticket.toString() + " , ";

                    //add the event name only if this exist
                    Event event = mainForm.getCompanyService().getEventService().get(ticket.getEventId(),ticket.getEventType());

                    if(event != null){

                        //add event name and city
                        searchString += event.getName() + " , " +
                                mainForm.getCompanyService().getAddressService().get(event.getLocation().
                                        getAddressId()).getCity() + " , ";

                    }

                    //add organizer name only if this exist
                    Organizer organizer = mainForm.getCompanyService().getOrganizerService().get(ticket.getOrganizerId());

                    if(organizer != null){
                        searchString += organizer.getName();
                    }

                    //search the `search value` in `search string` and add the results
                    if(searchString.toLowerCase().contains(searchValue)){
                        tmpLoadModel.addElement(ticketList.getModel().getElementAt(idx).toString());
                    }
                }

                //load the search result
                ticketList.setModel(tmpLoadModel);
                setTicketDescribeLabels();
            }
        });


        /*When a ticket is selected using mouse the right panel (ticket data display panel) is completed with the data
        of the selected ticket*/
        ticketList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                setTicketLabels();
            }
        });


        /*When a ticket is selected using the arrow key from keyboard the right panel (ticket data display panel) is
        completed with the data of the selected ticket*/
        ticketList.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                setTicketLabels();
            }
        });


        /*The tickets form can be closed from the X button. In that case main form will receive access*/
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                mainForm.setEnabled(true);
                mainForm.requestFocus();
            }
        });
    }


    /**
     * This function load all tickets for current session client or current session organizer in the main ticket
     * display area.
     */
    private void showAllTickets(MainForm mainForm){

        //preparing the model
        DefaultListModel<String> loadModel = new DefaultListModel<>();

        //check if a client is logged in
        if(mainForm.getCompanyService().getSessionClient() != null) {

            //get client tickets
            int clientId = mainForm.getCompanyService().getSessionClient().getClientId();
            List<Ticket> ticketListLoad = mainForm.getCompanyService().getTicketService().getClientTickets(clientId);

            //load client tickets
            for (Ticket ticket : ticketListLoad) {
                    loadModel.addElement(ticket.presentationPrint());
            }

            ticketList.setModel(loadModel);
            return;
        }

        //here I know for sure a organizer is logged in so I get all sold Tickets for session organizer
        int organizerId = mainForm.getCompanyService().getSessionOrganizer().getOrganizerId();
        List<Ticket> ticketListLoad = mainForm.getCompanyService().getTicketService().getOrganizerSoldTickets(organizerId);

        //load organizer sold tickets
        for (Ticket ticket : ticketListLoad) {
            loadModel.addElement(ticket.presentationPrint());
        }

        ticketList.setModel(loadModel);
    }


    /**
     * Just get the right sizes and fonts for label items that will display ticket data in ticket data display panel
     */
    private void setTicketDescribeLabels(){

        List<JLabel> labelList = new ArrayList<>();

        //get all label items to field names from `ticket data display panel`
        addLabels(labelList,ticketIdentifierLabel,eventNameLabel,organizerLabel,seatLabel,priceLabel,dateLabel,cityLabel);

        //set font and size
        for(JLabel label: labelList){
            label.setFont(new Font("", Font.BOLD, 16));
        }

        //get all label items where ticket data will be filled in `ticket data display panel`
        labelList.clear();
        addLabels(labelList, ticketIdentifierValueLabel,eventNameValueLabel,organizerValueLabel,seatValueLabel,
                priceValueLabel,dateValueLabel,cityValueLabel);

        //set font and size
        for(JLabel label: labelList){
            label.setFont(new Font("", Font.BOLD, 12));
            label.setText("");
        }
    }


    /**
     * This function is a helper for setTicketDescribeLabels() function.
     */
    private void addLabels(List<JLabel> labelList, JLabel ticketIdentifierLabel, JLabel eventLabel, JLabel organizerLabel,
                           JLabel seatLabel, JLabel priceLabel, JLabel dateLabel, JLabel cityLabel) {
        labelList.add(ticketIdentifierLabel);
        labelList.add(eventLabel);
        labelList.add(organizerLabel);
        labelList.add(seatLabel);
        labelList.add(priceLabel);
        labelList.add(dateLabel);
        labelList.add(cityLabel);
    }


    /**
     * A function used to retrieve details about the selected ticket and display them in `ticket data display panel` from
     * right side of the ticket form.
     */
    private void setTicketLabels(){

        //get selected tickets
        int[] selectedValues = ticketList.getSelectedIndices();

        //if no ticket is selected the values from `ticket data display panel` must be cleared
        if(selectedValues.length < 1){
            unsetValuesForTicketLabels();
            return;
        }

        //if multiple tickets are selected I show data just for the first ticket
        String ticketString = ticketList.getModel().getElementAt(selectedValues[0]).toString();
        int ticketId = Integer.parseInt(ticketString.split("#")[1].split(" ")[0]);

        //get the first selected ticket
        Ticket ticket = mainForm.getCompanyService().getTicketService().get(ticketId);

        //set the values in `ticket data display panel`
        setValuesForEventLabels(ticket);
    }


    /**
     * This function is a helper for setTicketLabels() function and unset the values in `ticket data display panel`
     * when no ticket is selected
     */
    private void unsetValuesForTicketLabels(){
        ticketIdentifierValueLabel.setText("");
        seatValueLabel.setText("");
        priceValueLabel.setText("");
        eventNameValueLabel.setText("");
        dateValueLabel.setText("");
        cityValueLabel.setText("");
        organizerValueLabel.setText("");
    }


    /**
     * This function is a helper for setTicketLabels() function and sets the values in `ticket data display panel`
     * for the ticket sent as a parameter
     */
    private void setValuesForEventLabels(Ticket ticket){

        //get the event at which the ticket was sold
        Event event = mainForm.getCompanyService().getEventService().get(ticket.getEventId(),ticket.getEventType());

        //set data about ticket in `ticket data display panel`
        ticketIdentifierValueLabel.setText(ticket.getTicketIdentifier());
        seatValueLabel.setText(ticket.getSeat());
        priceValueLabel.setText(String.valueOf(ticket.getPrice()));

        //add the event values in `ticket data display panel` only if event still exist
        if(event != null){
            eventNameValueLabel.setText(event.getName());
            dateValueLabel.setText(event.getLocation().getDate());
            Address address = mainForm.getCompanyService().getAddressService().get(event.getLocation().getAddressId());
            cityValueLabel.setText(address.getCity());
        }
        else{ //else show an error
            eventNameValueLabel.setText("ERROR [event deleted]");
            dateValueLabel.setText("");
            //Address address = mainForm.getCompanyService().getAddressService().get(event.getAddressId());
            cityValueLabel.setText("");
        }

        //add the organizer values only if organizer exist else show an error
        Organizer organizer;
        organizer = mainForm.getCompanyService().getOrganizerService().get(ticket.getOrganizerId());

        if(organizer != null) {
            organizerValueLabel.setText(organizer.getName());
        }
        else{
            organizerValueLabel.setText("ERROR [organizer deleted]");
        }

    }

    public JButton getCancelTicketButton() {
        return cancelTicketButton;
    }
}
