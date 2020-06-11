package view;

import domain.*;
import general.*;
import general.Event;
import services.CompanyService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static java.lang.StrictMath.abs;

public class MainForm extends JFrame{
    private JButton loginAsClientButton;
    private JPasswordField loginPasswordField;
    private JTextField usernameTextField;
    private JCheckBox musicCheckBox;
    private JCheckBox culturalCheckBox;
    private JTextField organizerTextField;
    private JTextField cityTextField;
    private JCheckBox sportCheckBox;
    private JPanel mainForm;
    private JButton loginAsOrganizerButton;
    private JButton newClientAccountButton;
    private JButton newOrganizerAccountButton;
    private JButton showEventsButton;
    private JCheckBox sortByPriceCheckBox;
    private JCheckBox sortByDateCheckBox;
    private JPanel loginPanel;
    private JPanel eventsPanel;
    private JPanel clientLoginPanel;
    private JButton updateAccountClientButton;
    private JSpinner priceSpinner;
    private JButton resetFiltersButton;
    private JButton viewTicketsButton;
    private JPanel organizerLoginPanel;
    private JButton viewSoldTicketsButton;
    private JButton updateAccountOrganizerButton;
    private JButton organizerLogoutButton;
    private JButton buyTicketButton;
    private JButton addEventButton;
    private JButton removeEventButton;
    private JScrollPane scrollEventsPanel;
    private JLabel clientLoginLabel;
    private JLabel organizerLoginLabel;
    private JButton clientLogoutButton;
    private JLabel startingPriceLabel;
    private JList eventList;
    private JLabel errorLabel;
    private JLabel infoMessageLabel;
    private JTextField searchEventTextField;
    private JButton searchEventButton;
    private JPanel eventDetailsPanel;
    private JLabel eventNameLabel;
    private JLabel eventPriceLabel;
    private JLabel eventDateLabel;
    private JLabel eventCityLabel;
    private JLabel eventCountryLabel;
    private JLabel eventStreetLabel;
    private JLabel eventPhoneLabel;
    private JLabel eventTypeLabel;
    private JLabel eventGenreLabel;
    private JLabel eventFormationLabel;
    private JLabel eventHomeLabel;
    private JLabel eventGuestLabel;
    private JLabel eventCulturalTypeLabel;
    private JLabel eventGuestsLabel;
    private JLabel eventNameValueLabel;
    private JLabel eventPriceValueLabel;
    private JLabel eventDateValueLabel;
    private JLabel eventCityValueLabel;
    private JLabel eventCountryValueLabel;
    private JLabel eventStreetValueLabel;
    private JLabel eventPhoneValueLabel;
    private JLabel eventTypeValueLabel;
    private JLabel eventGenreValueLabel;
    private JLabel eventFormationValueLabel;
    private JLabel eventHomeValueLabel;
    private JLabel eventGuestValueLabel;
    private JLabel eventCulturalTypeValueLabel;
    private JLabel eventGuestsValueLabel;
    private JLabel eventOrganizerLabel;
    private JLabel eventOrganizerValueLabel;
    private JButton updateEventButton;
    private JLabel photoLabel;
    private JLabel eventAvailableTicketsLabel;
    private JLabel eventAvailableTicketsValueLabel;
    private JPanel eventMusicPanel;
    private JPanel eventSportPanel;
    private JPanel eventCulturalPanel;
    private JPanel eventCulturalValuePanel;
    private JPanel eventSportValuePanel;
    private JPanel eventMusicValuePanel;
    private JLabel eventShowLabel;

    private CompanyService companyService;

    public MainForm(String title, CompanyService companyService) throws HeadlessException {
        super(title);
        this.companyService = companyService;

        //set initial setup
        loadAllEvents();
        setEventDescribeLabels();
        hideSpecificEventLabels();
        clientLoginPanel.setVisible(false);
        organizerLoginPanel.setVisible(false);
        errorLabel.setVisible(false);
        infoMessageLabel.setVisible(false);
        addEventButton.setVisible(false);
        updateEventButton.setVisible(false);
        removeEventButton.setVisible(false);
        searchEventButton.setVisible(false);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(mainForm);
        this.pack();


        /*Set foreground for sold out events. Events which are SOLD OUT will have a red color.*/
        eventList.setCellRenderer(new DefaultListCellRenderer() {

            @Override
            public Component getListCellRendererComponent(JList list, Object value, int index,
                                                          boolean isSelected, boolean cellHasFocus) {

                Component component = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

                String tmp = String.valueOf(value);
                if(tmp.contains("#")) {

                    //get event
                    int eventId = Integer.parseInt(tmp.split("#")[1].split(" ")[0]);
                    int eventType = Integer.parseInt(tmp.split("#")[2].split(" ")[0]);

                    Event event = companyService.getEventService().get(eventId, eventType);

                    //check if is a sold out event
                    if (event.getAvailableTicketsNumber() < 1) {
                        setForeground(Color.red);

                        if(isSelected){
                            setEventLabels();
                        }

                    } else {
                        setForeground(Color.BLACK);

                        if(isSelected){
                            setEventLabels();
                        }
                    }

                }

                return component;
            }

        });



        /*A function that gives functionality to the button loginAsClient. In order to be able to log in to a client
        account there must be such an account and the password and username must be correct.*/
        loginAsClientButton.addActionListener(actionEvent -> {

            //get username and password from input fields
            String username = usernameTextField.getText();
            String password = new String(loginPasswordField.getPassword());

            //try to set the session client for that username and password
            companyService.setSessionClient(companyService.getClientService().login(username,password));

            //if no client with that credential was found just return and show an info error message
            if(companyService.getSessionClient() == null){
                errorLabel.setVisible(true);
                return;
            }

            //login made successful
            companyService.getAuditService().addLogMessage("Login Client " + companyService.getSessionClient().getUsername());

            //hide or show fields
            infoMessageLabel.setVisible(false);
            errorLabel.setVisible(false);
            loginPanel.setVisible(false);
            organizerLoginPanel.setVisible(false);
            clientLoginPanel.setVisible(true);
            clientLoginLabel.setText("Hello " + companyService.getSessionClient().getUsername());

            //load events in main events area
            loadAllEvents();

        });


        /*A function that gives functionality to the button loginAsOrganizer. In order to be able to log in to a organizer
        account there must be such an account and the password and username must be correct.*/
        loginAsOrganizerButton.addActionListener(actionEvent -> {

            //get username and password from input fields
            String username = usernameTextField.getText();
            String password = new String(loginPasswordField.getPassword());

            //try to set the session organizer for that username and password
            companyService.setSessionOrganizer(companyService.getOrganizerService().login(username,password));

            //if no organizer with that credential was found just return and show an info error message
            if(companyService.getSessionOrganizer() == null){
                errorLabel.setVisible(true);
                return;
            }

            //login made successful
            companyService.getAuditService().addLogMessage("Login Organizer " + companyService.getSessionOrganizer().getUsername());

            organizerLoginLabel.setText("Hello " + companyService.getSessionOrganizer().getUsername());

            //show only his events in main event panel
            loadOrganizerEvents();

            //hide or show fields
            infoMessageLabel.setVisible(false);
            errorLabel.setVisible(false);

            //hide some filters
            sortByPriceCheckBox.setVisible(false);
            sortByDateCheckBox.setVisible(false);
            organizerTextField.setVisible(false);
            cityTextField.setVisible(false);
            startingPriceLabel.setVisible(false);
            priceSpinner.setVisible(false);
            resetFiltersButton.setVisible(false);

            //show specific fields
            updateEventButton.setVisible(true);
            addEventButton.setVisible(true);
            removeEventButton.setVisible(true);

            loginPanel.setVisible(false);
            clientLoginPanel.setVisible(false);
            organizerLoginPanel.setVisible(true);
        });


        /*A function that gives functionality to the button UpdateAccountOrganizer.
        When this button is pressed a new form is open. Will be the updateAccount form and access to main form
        will be restricted until the updateAccount form will be closed.*/
        updateAccountOrganizerButton.addActionListener(actionEvent -> {

            UpdateAccountForm updateFrame = new UpdateAccountForm("Update Organizer Account",this);

            //hide form fields
            infoMessageLabel.setVisible(false);
            updateFrame.getAgeSpinner().setVisible(false);
            updateFrame.getAgeLabel().setVisible(false);
            updateFrame.getGenderComboBox().setVisible(false);
            updateFrame.getGenderLabel().setVisible(false);
            updateFrame.getUpdateAccountButton().setVisible(true);
            updateFrame.getDeleteAccountButton().setVisible(true);
            updateFrame.getCreateClientAccountButton().setVisible(false);
            updateFrame.getCreateOrganizerAccountButton().setVisible(false);

            //setup
            updateFrame.setMinimumSize(new Dimension(330,450));
            updateFrame.setMaximumSize(new Dimension(330,450));
            updateFrame.setVisible(true);
            updateFrame.showOrganizerData(companyService.getSessionOrganizer());

            this.setEnabled(false);
        });


        /*A function that gives functionality to the button newOrganizerAccount.
        When this button is pressed a new form is open. Will be the updateAccount form and access to main form
        will be restricted until the updateAccount form will be closed.*/
        newOrganizerAccountButton.addActionListener(actionEvent -> {

            UpdateAccountForm updateFrame= new UpdateAccountForm("Create Organizer Account",this);

            //hide form fields
            infoMessageLabel.setVisible(false);
            updateFrame.getAgeSpinner().setVisible(false);
            updateFrame.getAgeLabel().setVisible(false);
            updateFrame.getGenderComboBox().setVisible(false);
            updateFrame.getGenderLabel().setVisible(false);
            updateFrame.getDeleteAccountButton().setVisible(false);
            updateFrame.getUpdateAccountButton().setVisible(false);
            updateFrame.getCreateClientAccountButton().setVisible(false);

            //setup
            updateFrame.setMinimumSize(new Dimension(330,450));
            updateFrame.setMaximumSize(new Dimension(330,450));
            updateFrame.setVisible(true);

            this.setEnabled(false);
        });


        /*A function that gives functionality to the button updateAccountClient.
        When this button is pressed a new form is open. Will be the updateAccount form and access to main form
        will be restricted until the updateAccount form will be closed.*/
        updateAccountClientButton.addActionListener(actionEvent -> {

            UpdateAccountForm updateFrame = new UpdateAccountForm("Update Client Account",this);

            //hide fields
            infoMessageLabel.setVisible(false);
            updateFrame.getDeleteAccountButton().setVisible(true);
            updateFrame.getUpdateAccountButton().setVisible(true);
            updateFrame.getCreateClientAccountButton().setVisible(false);
            updateFrame.getCreateOrganizerAccountButton().setVisible(false);

            //setup
            updateFrame.setMinimumSize(new Dimension(330,450));
            updateFrame.setMaximumSize(new Dimension(330,450));
            updateFrame.setVisible(true);
            updateFrame.showClientData(companyService.getSessionClient());

            this.setEnabled(false);
        });


        /*A function that gives functionality to the button newClientAccount.
        When this button is pressed a new form is open. Will be the updateAccount form and access to main form
        will be restricted until the updateAccount form will be closed.*/
        newClientAccountButton.addActionListener(actionEvent -> {

            UpdateAccountForm updateFrame = new UpdateAccountForm("Create Client Account",this);

            //hide fields
            infoMessageLabel.setVisible(false);
            updateFrame.getDeleteAccountButton().setVisible(false);
            updateFrame.getUpdateAccountButton().setVisible(false);
            updateFrame.getCreateOrganizerAccountButton().setVisible(false);

            //setup
            updateFrame.setMinimumSize(new Dimension(330,450));
            updateFrame.setMaximumSize(new Dimension(330,450));
            updateFrame.setVisible(true);

            this.setEnabled(false);

        });


        /*A function that gives functionality to the button viewSoldTicketsButton.
        When this button is pressed a new form is open. Will be the tickets form and access to main form
        will be restricted until the tickets form will be closed.*/
        viewSoldTicketsButton.addActionListener(actionEvent -> {

            TicketsForm ticketsFrame = new TicketsForm("Tickets",this);

            //setup
            ticketsFrame.getCancelTicketButton().setVisible(false);
            ticketsFrame.setMinimumSize(new Dimension(850,500));
            ticketsFrame.setMaximumSize(new Dimension(850,500));
            ticketsFrame.setVisible(true);
            infoMessageLabel.setVisible(false);

            this.setEnabled(false);
        });


        /*A function that gives functionality to the button viewTicketsButton.
        When this button is pressed a new form is open. Will be the tickets form and access to main form
        will be restricted until the tickets form will be closed.*/
        viewTicketsButton.addActionListener(actionEvent -> {

            TicketsForm ticketsFrame = new TicketsForm("Tickets",this);

            //setup
            ticketsFrame.getCancelTicketButton().setVisible(true);
            ticketsFrame.setMinimumSize(new Dimension(850,500));
            ticketsFrame.setMaximumSize(new Dimension(850,500));
            ticketsFrame.setVisible(true);
            infoMessageLabel.setVisible(false);

            this.setEnabled(false);
        });


        /*A function that gives functionality to the button organizerLogoutButton. When this button is pressed the
        current organizer session will be made null and the guest mode will be enabled.*/
        organizerLogoutButton.addActionListener(actionEvent -> {
            organizerLogout();
        });


        /*A function that gives functionality to the button clientLogoutButton. When this button is pressed the
        current client session will be made null and the guest mode will be enabled.*/
        clientLogoutButton.addActionListener(actionEvent -> {
            clientLogout();
        });


        /*Sample function which is used as a place holder for the username input field.*/
        usernameTextField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);

                if(usernameTextField.getText().equals("username")) {
                    usernameTextField.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);

                if(usernameTextField.getText().isEmpty()) {
                    usernameTextField.setText("username");
                }
            }
        });


        /*Sample function which is used as a place holder for the password input field.*/
        loginPasswordField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);

                String password = new String(loginPasswordField.getPassword());
                if(password.equals("password")) {
                    loginPasswordField.setText("");
                }

            }

            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);

                if(loginPasswordField.getPassword().length == 0) {
                    loginPasswordField.setText("password");
                }
            }
        });


        /*Sample function which is used as a place holder for the organizer input field in filters section.*/
        organizerTextField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                infoMessageLabel.setVisible(false);

                if(organizerTextField.getText().equals("Organizer")) {
                    organizerTextField.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);

                if(organizerTextField.getText().isEmpty()) {
                    organizerTextField.setText("Organizer");
                }
            }
        });


        /*Sample function which is used as a place holder for the city input field in filters section.*/
        cityTextField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                infoMessageLabel.setVisible(false);

                if(cityTextField.getText().equals("City")) {
                    cityTextField.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);

                if(cityTextField.getText().isEmpty()) {
                    cityTextField.setText("City");
                }
            }
        });


        /*Sample function which is used as a place holder for the search box input field.*/
        searchEventTextField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                infoMessageLabel.setVisible(false);

                if(searchEventTextField.getText().equals("Search Event")) {
                    searchEventTextField.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);

                if(searchEventTextField.getText().isEmpty()) {
                    searchEventTextField.setText("Search Event");
                }
            }
        });


        /*A function that gives functionality to the button addEventButton. When this button is pressed a new form is
         open. Will be the addEvent form and access to main form will be restricted until the addEvent form will
         be closed. This button is available only if an organizer is logged in.*/
        addEventButton.addActionListener(actionEvent -> {

            AddEventForm addEventForm = new AddEventForm("Add Event",this);

            //make setup
            addEventForm.setMinimumSize(new Dimension(300,400));
            addEventForm.setMaximumSize(new Dimension(300,400));
            addEventForm.getUpdateEventButton().setVisible(false);
            addEventForm.setVisible(true);
            infoMessageLabel.setVisible(false);

            this.setEnabled(false);
        });


        /*A function that gives functionality to the button resetFiltersButton. When this button is pressed all
        checkboxes will be unset, the price will be set to 0 and will be set the values for place holders.*/
        resetFiltersButton.addActionListener(actionEvent -> {
            sortByPriceCheckBox.setSelected(false);
            sortByDateCheckBox.setSelected(false);
            musicCheckBox.setSelected(false);
            sportCheckBox.setSelected(false);
            culturalCheckBox.setSelected(false);
            organizerTextField.setText("Organizer");
            cityTextField.setText("City");
            priceSpinner.setValue(0);
            infoMessageLabel.setVisible(false);
            setEventLabels();
        });


        /*A function that gives functionality to the button showEventsButton from the filters area. When this button is
        pressed a search based on the selected filters is made. The search is made using all events in database.*/
        showEventsButton.addActionListener(actionEvent -> {
            infoMessageLabel.setVisible(false);

            //final list with selected events
            List<Event> eventFilterList;

            //get all type of events selected
            if(!musicCheckBox.isSelected() && !sportCheckBox.isSelected() && !culturalCheckBox.isSelected()){
                eventFilterList = new ArrayList<>(companyService.getEventService().getAll());
            }
            else{
                eventFilterList = new ArrayList<>();
                if(musicCheckBox.isSelected()) {
                    List<Event> tmp = companyService.getEventService().getEventsByType(new Music());
                    eventFilterList.addAll(tmp);
                }
                if(sportCheckBox.isSelected()) {
                    List<Event> tmp = companyService.getEventService().getEventsByType(new Sport());
                    eventFilterList.addAll(tmp);
                }
                if(culturalCheckBox.isSelected()) {
                    List<Event> tmp = companyService.getEventService().getEventsByType(new Cultural());
                    eventFilterList.addAll(tmp);
                }
            }


            if(companyService.getSessionOrganizer() != null){ //for organizer I show only his events
                int organizerId = companyService.getSessionOrganizer().getOrganizerId();
                List<Event> tmp = new ArrayList<>();
                for(Event event: eventFilterList){
                    if(event.getOrganizerId() == organizerId){
                        tmp.add(event);
                    }
                }

                eventFilterList = new ArrayList<>(tmp);
            }
            else { //organizer do not have all filters so this are used only if a client or a guest makes the search

                //check what fields are modified
                String organizerValue = organizerTextField.getText().toLowerCase();
                boolean isOrganizer = !organizerValue.equals("organizer"); //organizer is just a placeholder
                String city = cityTextField.getText().toLowerCase();
                boolean isCity = !city.equals("city"); //City is just a placeholder

                String priceString = priceSpinner.getValue().toString();
                double price = abs(Double.parseDouble(priceString));

                List<Event> tmp = new ArrayList<>(eventFilterList);

                //eliminate unnecessary events using the values in organizer input fields, price spinner and
                //data input field if these exists
                for (Event event : tmp) {

                    Organizer organizer = companyService.getOrganizerService().get(event.getOrganizerId());

                    if (isOrganizer && !organizer.getName().toLowerCase().contains(organizerValue)) {
                        eventFilterList.remove(event);
                    } else if (isCity &&
                            !companyService.getAddressService().get(event.getLocation().getAddressId()).getCity().toLowerCase().equals(city)) {
                        eventFilterList.remove(event);
                    } else if (price > 0 && event.getStandardTicketPrice() < price) {
                        eventFilterList.remove(event);
                    }
                }


                //make the sort if checked
                if (sortByPriceCheckBox.isSelected() && !sortByDateCheckBox.isSelected()) {
                    eventFilterList.sort(new EventComparatorByPrice());
                }

                if (!sortByPriceCheckBox.isSelected() && sortByDateCheckBox.isSelected()) {
                    eventFilterList.sort(new EventComparatorByDate());
                }

                //if both are checked make the price sort first and date sort second
                if (sortByPriceCheckBox.isSelected() && sortByDateCheckBox.isSelected()) {
                    eventFilterList.sort(new EventComparatorByPrice());
                    eventFilterList.sort(new EventComparatorByDate());
                }
            }

            //load new events in eventList
            DefaultListModel<String> tmpLoadModelFilter = new DefaultListModel<>();
            for(Event event: eventFilterList) {
                tmpLoadModelFilter.addElement(event.presentationPrint());
            }

            //show events
            eventList.setModel(tmpLoadModelFilter);
            setEventLabels();

        });


        /*A function that gives functionality to the button removeEventButton. When this button is pressed all selected
        events will be removed. This button is available only when an organizer is logged in.*/
        removeEventButton.addActionListener(actionEvent -> {

            //get all selected events
            int[] indexList = eventList.getSelectedIndices();

            //if no event is selected show an info message error
            if(indexList.length == 0){
                infoMessageLabel.setText("Choose one or more events to remove !");
                infoMessageLabel.setVisible(true);
                return;
            }

            infoMessageLabel.setVisible(false);

            List<Event> tmpList = new ArrayList<>();

            //identify selected events in database
            for(int idx: indexList){
                String tmp = eventList.getModel().getElementAt(idx).toString();
                int eventId = Integer.parseInt(tmp.split("#")[1].split(" ")[0]);
                int eventType = Integer.parseInt(tmp.split("#")[2].split(" ")[0]);

                tmpList.add(companyService.getEventService().get(eventId,eventType));
            }

            //remove selected events
            for(Event event: tmpList){

                //get address of the event
                int addressId = event.getLocation().getAddressId();

                //remove event
                companyService.getAuditService().addLogMessage("Cancel Event " + event.getName());
                companyService.getEventService().remove(event);

                //remove address
                companyService.getAddressService().remove(companyService.getAddressService().get(addressId));
            }

            //show the remaining events
            loadOrganizerEvents();

        });


        /*A function that gives functionality to the button buyTicketButton. When this button is pressed the selected
        event will decrease the number of tickets available by 1 and a new ticket will be created for that event and for
        the session client. This button is available only when an client is logged in.*/
        buyTicketButton.addActionListener(actionEvent -> {

            //get all selected tickets
            int[] indexList = eventList.getSelectedIndices();

            //go further only if one ticket is selected else show some info errors
            if(indexList.length == 0){
                infoMessageLabel.setText("Choose a ticket !");
                infoMessageLabel.setVisible(true);
                return;
            }

            if(indexList.length > 1){
                infoMessageLabel.setText("Choose a single ticket !");
                infoMessageLabel.setVisible(true);
                return;
            }

            //extract event id and client id to link the new ticket
            String tmp = eventList.getModel().getElementAt(indexList[0]).toString();
            int eventId = Integer.parseInt(tmp.split("#")[1].split(" ")[0]);
            int eventType = Integer.parseInt(tmp.split("#")[2].split(" ")[0]);

            int clientId = companyService.getSessionClient().getClientId();

            Event event = companyService.getEventService().get(eventId,eventType);

            //check if event has available tickets
            //if no available tickets are an error will be displayed and no ticket will be created
            if(event.getAvailableTicketsNumber() <= 0){
                infoMessageLabel.setText("This event has no available tickets");
                infoMessageLabel.setVisible(true);
                return;
            }

            //here a ticket can be created
            //because every ticket must be unique I create a unique ticket identifier
            while (true) {
                Random rand = new Random();
                String seat = String.valueOf(abs(rand.nextInt()));
                String ticketIdentifier;

                if(event.getName().length() == 1){
                    ticketIdentifier = event.getName().substring(0,1) + "-" + seat;
                }
                else{
                    ticketIdentifier = event.getName().substring(0,2) + "-" + seat;
                }

                //check if is a unique ticket identifier
                //if another ticket has this value go back and generate another one
                if(companyService.getTicketService().existTicket(ticketIdentifier)){
                    continue;
                }

                int organizerId = event.getOrganizerId();
                float price = event.getStandardTicketPrice();

                //the ticket can be created
                Ticket ticket = new Ticket(clientId,organizerId,eventId,eventType,ticketIdentifier,seat,price);
                companyService.getTicketService().add(ticket);
                companyService.getAuditService().addLogMessage("Sold Ticket " + ticketIdentifier);
                infoMessageLabel.setText("Ticket bought");
                infoMessageLabel.setVisible(true);

                //eliminate one value from event available tickets
                event.setAvailableTicketsNumber(event.getAvailableTicketsNumber() - 1);
                companyService.getEventService().update(event);

                setEventLabels();

                break;
            }

        });


        /*When an event is selected using mouse the right panel (event data display panel) is completed with the data
        of the selected event.*/
        eventList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                infoMessageLabel.setVisible(false);
                setEventLabels();
            }
        });


        /*A function that gives functionality to the button updateEventButton. When this button is pressed a new form is
        open. Will be the addEvent form and access to main form will be restricted until the addEvent form will
        be closed. This button is available only if an organizer is logged in.*/
        updateEventButton.addActionListener(actionEvent -> {

            //find the selected event
            int[] indexList = eventList.getSelectedIndices();

            //check if only one event is selected
            if(indexList.length == 0){
                infoMessageLabel.setText("Choose a event !");
                infoMessageLabel.setVisible(true);
                return;
            }

            if(indexList.length > 1){
                infoMessageLabel.setText("Choose a single event !");
                infoMessageLabel.setVisible(true);
                return;
            }

            infoMessageLabel.setVisible(false);

            //show the addEvent form only if one event is selected
            AddEventForm addEventForm = new AddEventForm("Update Event",this);
            addEventForm.setMinimumSize(new Dimension(330,450));
            addEventForm.setMaximumSize(new Dimension(330,450));
            addEventForm.getAddEventButton().setVisible(false);
            addEventForm.setVisible(true);

            //extract id`s
            String tmp = eventList.getModel().getElementAt(indexList[0]).toString();
            int eventId = Integer.parseInt(tmp.split("#")[1].split(" ")[0]);
            int eventType = Integer.parseInt(tmp.split("#")[2].split(" ")[0]);

            //load data from selected event in the form fields
            addEventForm.showData(companyService.getEventService().get(eventId,eventType));

            this.setEnabled(false);
        });


        /*A function that gives functionality to the search event box.
        The search will be done as you enter data from the keyboard. */
        searchEventTextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                infoMessageLabel.setVisible(false);

                //the search is made using all events from database if a a client is logged in or is guest mode
                //or using only organizer events if an organizer is logged in
                if(companyService.getSessionOrganizer() != null){ //an organizer is logged in
                    loadOrganizerEvents();
                }
                else {
                    loadAllEvents();
                }

                //check if is a search value
                String searchValue = searchEventTextField.getText().toLowerCase();
                if(searchValue.equals("search event")){
                    return;
                }

                //get all events from main events area and go through them
                DefaultListModel<String> tmpLoadModel = new DefaultListModel<>();
                int sizeModel = eventList.getModel().getSize();

                for(int idx = 0; idx < sizeModel; idx++){

                    //extract the event
                    String eventString = eventList.getModel().getElementAt(idx).toString();
                    int eventId = Integer.parseInt(eventString.split("#")[1].split(" ")[0]);
                    int eventType = Integer.parseInt(eventString.split("#")[2].split(" ")[0]);

                    Event event = companyService.getEventService().get(eventId,eventType);

                    //add the address and organizer name
                    String searchString = event.toString() + " , " +
                            companyService.getAddressService().get(event.getLocation().getAddressId()).toString() + " , " +
                            companyService.getOrganizerService().get(event.getOrganizerId()).getName();

                    //check if event contains the search value
                    if(searchString.toLowerCase().contains(searchValue)){
                        //if contains then the event is added to the final list
                        tmpLoadModel.addElement(eventString);
                    }
                }

                //load final list
                eventList.setModel(tmpLoadModel);
            }
        });


        /*When an event is selected using the arrow key from keyboard the right panel (event data display panel) is
        completed with the data of the selected event*/
        eventList.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                infoMessageLabel.setVisible(false);
                setEventLabels();
            }
        });
    }


    /**
     * A function that takes all events from database for the current session organizer and displays them in the
     * main event display area. Used when an organizer is logged in.
     */
    public void loadOrganizerEvents(){

        DefaultListModel<String> tmpLoadModel = new DefaultListModel<>();

        //get session organizer and his events
        int organizerId = companyService.getSessionOrganizer().getOrganizerId();
        List<Event> eventFilterList = new ArrayList<>(companyService.getEventService().getOrganizerEvents(organizerId));

        //load events in view list
        for(Event event: eventFilterList){
            tmpLoadModel.addElement(event.presentationPrint());
        }

        //show the events in main area
        eventList.setModel(tmpLoadModel);
        setEventLabels();
    }


    /**
     * A function that takes all events from database and displays them in the main event display area. Used in guest
     * mode or when a client is logged in.
     */
    public void loadAllEvents(){

        DefaultListModel<String> loadModel = new DefaultListModel<>();

        //get events from database
        List<Event> eventListLoad = companyService.getEventService().getAll();

        //load events in view list
        for (Event event : eventListLoad) {
            loadModel.addElement(event.presentationPrint());
        }

        eventList.setFont(new Font("", Font.BOLD, 14));

        //show the events in main area
        eventList.setModel(loadModel);
        setEventLabels();
    }


    /**
     * Just get the right sizes and fonts for label items that will display event data in event data display panel
     */
    private void setEventDescribeLabels(){

        List<JLabel> labelList = new ArrayList<>();

        //get all label items to field names from `event data display panel`
        addLabels(labelList, eventAvailableTicketsLabel, eventOrganizerLabel, eventNameLabel, eventPriceLabel, eventDateLabel,
                eventCityLabel, eventCountryLabel, eventStreetLabel, eventPhoneLabel, eventTypeLabel, eventGenreLabel,
                eventFormationLabel, eventHomeLabel, eventGuestLabel, eventCulturalTypeLabel, eventGuestsLabel);

        //set font and size
        for(JLabel label: labelList){
            label.setFont(new Font("", Font.BOLD, 16));
        }


        labelList.clear();

        //get all label items where event data will be filled in `event data display panel`
        addLabels(labelList, eventAvailableTicketsValueLabel, eventOrganizerValueLabel, eventNameValueLabel, eventPriceValueLabel,
                eventDateValueLabel, eventCityValueLabel, eventCountryValueLabel, eventStreetValueLabel, eventPhoneValueLabel,
                eventTypeValueLabel, eventGenreValueLabel, eventFormationValueLabel, eventHomeValueLabel,
                eventGuestValueLabel, eventCulturalTypeValueLabel, eventGuestsValueLabel);

        //set font and size
        for(JLabel label: labelList){
            label.setFont(new Font("", Font.BOLD, 12));
            label.setText("");
        }
    }


    /**
     * This function is a helper for setEventDescribeLabels() function.
     */
    private void addLabels(List<JLabel> labelList, JLabel eventAvailableTicketsValueLabel, JLabel eventOrganizerValueLabel,
                           JLabel eventNameValueLabel, JLabel eventPriceValueLabel, JLabel eventDateValueLabel,
                           JLabel eventCityValueLabel, JLabel eventCountryValueLabel, JLabel eventStreetValueLabel,
                           JLabel eventPhoneValueLabel, JLabel eventTypeValueLabel, JLabel eventGenreValueLabel,
                           JLabel eventFormationValueLabel, JLabel eventHomeValueLabel, JLabel eventGuestValueLabel,
                           JLabel eventCulturalTypeValueLabel, JLabel eventGuestsValueLabel) {

        labelList.add(eventAvailableTicketsValueLabel);
        labelList.add(eventOrganizerValueLabel);
        labelList.add(eventNameValueLabel);
        labelList.add(eventPriceValueLabel);
        labelList.add(eventDateValueLabel);
        labelList.add(eventCityValueLabel);
        labelList.add(eventCountryValueLabel);
        labelList.add(eventStreetValueLabel);
        labelList.add(eventPhoneValueLabel);
        labelList.add(eventTypeValueLabel);
        labelList.add(eventGenreValueLabel);
        labelList.add(eventFormationValueLabel);
        labelList.add(eventHomeValueLabel);
        labelList.add(eventGuestValueLabel);
        labelList.add(eventCulturalTypeValueLabel);
        labelList.add(eventGuestsValueLabel);
    }



    /**
     * A function used to retrieve details about the selected event and display them in `event data display panel` from
     * right side of the main form.
     */
    public void setEventLabels(){

        //first hide specific labels(every event has some specific fields)
        hideSpecificEventLabels();

        //get selected events
        int[] selectedValues = eventList.getSelectedIndices();

        //if no event is selected the values from `event data display panel` must be cleared
        if(selectedValues.length < 1){
            unsetValuesForEventLabels();
            return;
        }

        //if multiple events are selected I show data just for the first event
        String eventString = eventList.getModel().getElementAt(selectedValues[0]).toString();

        //get the first selected event
        int eventId = Integer.parseInt(eventString.split("#")[1].split(" ")[0]);
        int eventType = Integer.parseInt(eventString.split("#")[2].split(" ")[0]);
        Event event = companyService.getEventService().get(eventId,eventType);

        //set the values in `event data display panel`
        setValuesForEventLabels(event);

    }


    /**
     * This function is a helper for setEventLabels() function and unset the values in `event data display panel`
     * when no event is selected
     */
    public void unsetValuesForEventLabels(){
        eventAvailableTicketsValueLabel.setForeground(Color.black);
        eventAvailableTicketsValueLabel.setText("");
        eventOrganizerValueLabel.setText("");
        eventNameValueLabel.setText("");
        eventPriceValueLabel.setText("");
        eventDateValueLabel.setText("");
        eventCityValueLabel.setText("");
        eventCountryValueLabel.setText("");
        eventStreetValueLabel.setText("");
        eventPhoneValueLabel.setText("");
        eventTypeValueLabel.setText("");

        hideSpecificEventLabels();
    }


    /**
     * This function is a helper for setEventLabels() function and sets the values in `event data display panel`
     * for the event sent as a parameter
     */
    private void setValuesForEventLabels(Event event){

        //Set the available tickets number value. If this is 0 then a red SOLD OUT message will appear.
        if(event.getAvailableTicketsNumber() > 0){
            eventAvailableTicketsValueLabel.setForeground(Color.black);
            eventAvailableTicketsValueLabel.setText(String.valueOf(event.getAvailableTicketsNumber()));
        }
        else{
            eventAvailableTicketsValueLabel.setForeground(Color.red);
            eventAvailableTicketsValueLabel.setText("SOLD OUT");
        }

        //set the rest of the fields
        eventOrganizerValueLabel.setText(companyService.getOrganizerService().get(event.getOrganizerId()).getName());
        eventNameValueLabel.setText(event.getName());
        eventPriceValueLabel.setText(String.valueOf(event.getStandardTicketPrice()));
        eventDateValueLabel.setText(event.getLocation().getDate());
        eventCityValueLabel.setText(companyService.getAddressService().get(event.getLocation().getAddressId()).getCity());
        eventCountryValueLabel.setText(companyService.getAddressService().get(event.getLocation().getAddressId()).getCountry());
        eventStreetValueLabel.setText(companyService.getAddressService().get(event.getLocation().getAddressId()).getStreet());
        eventPhoneValueLabel.setText(companyService.getAddressService().get(event.getLocation().getAddressId()).getPhoneNumber());

        //sets specific fields according to the type of event
        if(event.getClass() == Music.class){ //set music specific fields
            eventMusicPanel.setVisible(true);
            eventMusicValuePanel.setVisible(true);

            eventTypeValueLabel.setText("Music Event");
            eventGenreLabel.setVisible(true);
            eventGenreValueLabel.setVisible(true);
            eventFormationLabel.setVisible(true);
            eventFormationValueLabel.setVisible(true);
            eventGenreValueLabel.setText(((Music)event).getMusicGenre());
            eventFormationValueLabel.setText(((Music)event).getFormationName());
            photoLabel.setIcon(new ImageIcon("src\\view\\res\\music.png"));
            return;
        }

        if(event.getClass() == Sport.class){ //set sport specific fields
            eventSportPanel.setVisible(true);
            eventSportValuePanel.setVisible(true);

            eventTypeValueLabel.setText("Sport Event");
            eventHomeLabel.setVisible(true);
            eventHomeValueLabel.setVisible(true);
            eventGuestLabel.setVisible(true);
            eventGuestValueLabel.setVisible(true);
            eventHomeValueLabel.setText(((Sport)event).getHome());
            eventGuestValueLabel.setText(((Sport)event).getGuest());
            photoLabel.setIcon(new ImageIcon("src\\view\\res\\sport.png"));
            return;
        }

        if(event.getClass() == Cultural.class){ //set cultural specific fields
            eventCulturalPanel.setVisible(true);
            eventCulturalValuePanel.setVisible(true);

            eventTypeValueLabel.setText("Cultural Event");
            eventCulturalTypeLabel.setVisible(true);
            eventCulturalTypeValueLabel.setVisible(true);
            eventGuestsLabel.setVisible(true);
            eventGuestsValueLabel.setVisible(true);
            eventCulturalTypeValueLabel.setText(((Cultural)event).getType());
            eventGuestsValueLabel.setText(((Cultural)event).getGuests());
            photoLabel.setIcon(new ImageIcon("src\\view\\res\\cultural.jpg"));
        }

    }


    /**
     * This function hides all specific labels that are different depending on the event. Every event has two specific
     * fields.
     */
    private void hideSpecificEventLabels(){
        eventMusicPanel.setVisible(false);
        eventMusicValuePanel.setVisible(false);
        eventSportPanel.setVisible(false);
        eventSportValuePanel.setVisible(false);
        eventCulturalPanel.setVisible(false);
        eventCulturalValuePanel.setVisible(false);
    }


    /**
     * A function that closes the current client session. When a client is logged out certain fields and buttons will be
     * hidden and guest mode will be established.
     */
    public void clientLogout(){

        //set the current session to null
        companyService.setSessionClient(null);

        //load all events from database
        loadAllEvents();

        //hide some fields
        infoMessageLabel.setVisible(false);
        clientLoginPanel.setVisible(false);
        organizerLoginPanel.setVisible(false);

        //show login panel (make logout)
        loginPanel.setVisible(true);
    }


    /**
     * A function that closes the current organizer session. When an organizer is logged out certain fields and buttons
     * will be hidden or displayed and guest mode will be established.
     */
    public void organizerLogout(){

        //set the current session to null
        companyService.setSessionOrganizer(null);

        //load all events from database
        loadAllEvents();

        //show all filters
        sortByPriceCheckBox.setVisible(true);
        sortByDateCheckBox.setVisible(true);
        organizerTextField.setVisible(true);
        cityTextField.setVisible(true);
        startingPriceLabel.setVisible(true);
        priceSpinner.setVisible(true);
        resetFiltersButton.setVisible(true);

        //hide specific fields
        updateEventButton.setVisible(false);
        addEventButton.setVisible(false);
        removeEventButton.setVisible(false);
        clientLoginPanel.setVisible(false);
        organizerLoginPanel.setVisible(false);

        //show login panel (make logout)
        loginPanel.setVisible(true);
        infoMessageLabel.setVisible(false);
    }

    public JLabel getInfoMessageLabel() {
        return infoMessageLabel;
    }

    public CompanyService getCompanyService() {
        return companyService;
    }

}
