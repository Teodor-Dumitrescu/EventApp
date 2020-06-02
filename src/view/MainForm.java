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


        //set foreground for sold out events
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
                        //setBackground(Color.RED);

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

        //actions
        loginAsClientButton.addActionListener(actionEvent -> {

            //make login
            String username = usernameTextField.getText();
            String password = new String(loginPasswordField.getPassword());

            companyService.setSessionClient(companyService.getClientService().login(username,password));

            if(companyService.getSessionClient() == null){
                errorLabel.setVisible(true);
                return;
            }

            companyService.getAuditService().addLogMessage("Login Client " + companyService.getSessionClient().getUsername());

            //hide or show fields
            infoMessageLabel.setVisible(false);
            errorLabel.setVisible(false);
            loginPanel.setVisible(false);
            organizerLoginPanel.setVisible(false);
            clientLoginPanel.setVisible(true);
            clientLoginLabel.setText("Hello " + companyService.getSessionClient().getUsername());
            loadAllEvents();

        });

        loginAsOrganizerButton.addActionListener(actionEvent -> {

            //make login
            String username = usernameTextField.getText();
            String password = new String(loginPasswordField.getPassword());

            companyService.setSessionOrganizer(companyService.getOrganizerService().login(username,password));

            if(companyService.getSessionOrganizer() == null){
                errorLabel.setVisible(true);
                return;
            }

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
        });
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
        });
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

        });
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
        });
        viewSoldTicketsButton.addActionListener(actionEvent -> {
            TicketsForm ticketsFrame = new TicketsForm("Tickets",this);
            ticketsFrame.getCancelTicketButton().setVisible(false);
            ticketsFrame.setMinimumSize(new Dimension(850,500));
            ticketsFrame.setMaximumSize(new Dimension(850,500));
            ticketsFrame.setVisible(true);
            infoMessageLabel.setVisible(false);
        });
        viewTicketsButton.addActionListener(actionEvent -> {
            TicketsForm ticketsFrame = new TicketsForm("Tickets",this);
            ticketsFrame.getCancelTicketButton().setVisible(true);
            ticketsFrame.setMinimumSize(new Dimension(850,500));
            ticketsFrame.setMaximumSize(new Dimension(850,500));
            ticketsFrame.setVisible(true);
            infoMessageLabel.setVisible(false);
        });
        organizerLogoutButton.addActionListener(actionEvent -> {
            organizerLogout();
        });
        clientLogoutButton.addActionListener(actionEvent -> {
            clientLogout();
        });

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

        addEventButton.addActionListener(actionEvent -> {
            AddEventForm addEventForm = new AddEventForm("Add Event",this);
            addEventForm.setMinimumSize(new Dimension(300,400));
            addEventForm.setMaximumSize(new Dimension(300,400));
            addEventForm.getUpdateEventButton().setVisible(false);
            addEventForm.setVisible(true);
            infoMessageLabel.setVisible(false);
        });

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

        showEventsButton.addActionListener(actionEvent -> {
            infoMessageLabel.setVisible(false);

            List<Event> eventFilterList;

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
            else { //organizer do not have all filters

                String organizerValue = organizerTextField.getText().toLowerCase();
                boolean isOrganizer = !organizerValue.equals("organizer"); //organizer is just a placeholder
                String city = cityTextField.getText().toLowerCase();
                boolean isCity = !city.equals("city"); //City is just a placeholder

                String priceString = priceSpinner.getValue().toString();
                double price = abs(Double.parseDouble(priceString));

                List<Event> tmp = new ArrayList<>(eventFilterList);
                for (Event event : tmp) { //eliminate unnecessary events

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


                if (sortByPriceCheckBox.isSelected() && !sortByDateCheckBox.isSelected()) {
                    eventFilterList.sort(new EventComparatorByPrice());

                }

                if (!sortByPriceCheckBox.isSelected() && sortByDateCheckBox.isSelected()) {
                    eventFilterList.sort(new EventComparatorByDate());
                }

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

            eventList.setModel(tmpLoadModelFilter);
            setEventLabels();

        });

        removeEventButton.addActionListener(actionEvent -> {

            int[] indexList = eventList.getSelectedIndices();

            if(indexList.length == 0){
                infoMessageLabel.setText("Choose one or more events to remove !");
                infoMessageLabel.setVisible(true);
                return;
            }

            infoMessageLabel.setVisible(false);

            List<Event> tmpList = new ArrayList<>();
            for(int idx: indexList){
                String tmp = eventList.getModel().getElementAt(idx).toString();
                int eventId = Integer.parseInt(tmp.split("#")[1].split(" ")[0]);
                int eventType = Integer.parseInt(tmp.split("#")[2].split(" ")[0]);
                //System.out.println("remove event button Event id = " + eventId + "  event type = " +eventType);
                tmpList.add(companyService.getEventService().get(eventId,eventType));
            }

            for(Event event: tmpList){

                int addressId = event.getLocation().getAddressId();

                //remove event
                companyService.getAuditService().addLogMessage("Cancel Event " + event.getName());
                companyService.getEventService().remove(event);

                //remove address
                companyService.getAddressService().remove(companyService.getAddressService().get(addressId));
            }

            loadOrganizerEvents();

        });

        buyTicketButton.addActionListener(actionEvent -> {

            int[] indexList = eventList.getSelectedIndices();

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

            //extract id`s
            String tmp = eventList.getModel().getElementAt(indexList[0]).toString();
            int eventId = Integer.parseInt(tmp.split("#")[1].split(" ")[0]);
            int eventType = Integer.parseInt(tmp.split("#")[2].split(" ")[0]);
            //System.out.println("buy ticket button Event id = " + eventId + "  event type = " +eventType);

            int clientId = companyService.getSessionClient().getClientId();

            Event event = companyService.getEventService().get(eventId,eventType);

            //check if event has available tickets
            if(event.getAvailableTicketsNumber() <= 0){
                infoMessageLabel.setText("This event has no available tickets");
                infoMessageLabel.setVisible(true);
                return;
            }

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
                if(companyService.getTicketService().existTicket(ticketIdentifier)){
                    continue;
                }

                int organizerId = event.getOrganizerId();
                float price = event.getStandardTicketPrice();

                Ticket ticket = new Ticket(clientId,organizerId,eventId,eventType,ticketIdentifier,seat,price);
                companyService.getTicketService().add(ticket);
                companyService.getAuditService().addLogMessage("Sold Ticket " + ticketIdentifier);
                infoMessageLabel.setText("Ticket bought");

                //eliminate one value from event available tickets
                event.setAvailableTicketsNumber(event.getAvailableTicketsNumber() - 1);
                companyService.getEventService().update(event);

                setEventLabels();

                break;
            }


        });

        eventList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                infoMessageLabel.setVisible(false);
                setEventLabels();
            }
        });
        updateEventButton.addActionListener(actionEvent -> {

            //find the selected event
            int[] indexList = eventList.getSelectedIndices();

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

            //show the form only if one event is selected
            AddEventForm addEventForm = new AddEventForm("Add Event",this);
            addEventForm.setMinimumSize(new Dimension(330,450));
            addEventForm.setMaximumSize(new Dimension(330,450));
            addEventForm.getAddEventButton().setVisible(false);
            addEventForm.setVisible(true);

            //extract id`s
            String tmp = eventList.getModel().getElementAt(indexList[0]).toString();
            int eventId = Integer.parseInt(tmp.split("#")[1].split(" ")[0]);
            int eventType = Integer.parseInt(tmp.split("#")[2].split(" ")[0]);
            //System.out.println("Event id = " + eventId + "  event type = " +eventType);

            addEventForm.showData(companyService.getEventService().get(eventId,eventType));

        });


        searchEventTextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                infoMessageLabel.setVisible(false);

                loadAllEvents();

                String searchValue = searchEventTextField.getText().toLowerCase();
                if(searchValue.equals("search event")){
                    return;
                }

                //System.out.println(searchValue);

                //get events model
                DefaultListModel<String> tmpLoadModel = new DefaultListModel<>();
                int sizeModel = eventList.getModel().getSize();

                for(int idx = 0; idx < sizeModel; idx++){

                    //extract the event
                    String eventString = eventList.getModel().getElementAt(idx).toString();
                    int eventId = Integer.parseInt(eventString.split("#")[1].split(" ")[0]);
                    int eventType = Integer.parseInt(eventString.split("#")[2].split(" ")[0]);
                    //System.out.println("set event label in main form Event id = " + eventId + "  event type = " + eventType);
                    Event event = companyService.getEventService().get(eventId,eventType);

                    //add the address and organizer name
                    String searchString = event.toString() + " , " +
                            companyService.getAddressService().get(event.getLocation().getAddressId()).toString() + " , " +
                            companyService.getOrganizerService().get(event.getOrganizerId()).getName();

                    if(searchString.toLowerCase().contains(searchValue)){
                        tmpLoadModel.addElement(eventString);
                    }
                }

                eventList.setModel(tmpLoadModel);
            }
        });

        eventList.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                infoMessageLabel.setVisible(false);
                setEventLabels();
            }
        });
    }

    public void loadOrganizerEvents(){
        DefaultListModel<String> tmpLoadModel = new DefaultListModel<>();
        int organizerId = companyService.getSessionOrganizer().getOrganizerId();
        List<Event> eventFilterList = new ArrayList<>(companyService.getEventService().getOrganizerEvents(organizerId));

        for(Event event: eventFilterList){
            tmpLoadModel.addElement(event.presentationPrint());
        }

        eventList.setModel(tmpLoadModel);
        setEventLabels();
    }

    public void loadAllEvents(){
        DefaultListModel<String> loadModel = new DefaultListModel<>();
        List<Event> eventListLoad = companyService.getEventService().getAll();

        for (Event event : eventListLoad) {
            loadModel.addElement(event.presentationPrint());
        }

        eventList.setFont(new Font("", Font.BOLD, 14));
        eventList.setModel(loadModel);
        setEventLabels();
    }

    public JLabel getInfoMessageLabel() {
        return infoMessageLabel;
    }

    public CompanyService getCompanyService() {
        return companyService;
    }

    private void setEventDescribeLabels(){

        List<JLabel> labelList = new ArrayList<>();
        addLabels(labelList, eventAvailableTicketsLabel, eventOrganizerLabel, eventNameLabel, eventPriceLabel, eventDateLabel,
                eventCityLabel, eventCountryLabel, eventStreetLabel, eventPhoneLabel, eventTypeLabel, eventGenreLabel,
                eventFormationLabel, eventHomeLabel, eventGuestLabel, eventCulturalTypeLabel, eventGuestsLabel);

        for(JLabel label: labelList){
            label.setFont(new Font("", Font.BOLD, 16));
        }

        labelList.clear();
        addLabels(labelList, eventAvailableTicketsValueLabel, eventOrganizerValueLabel, eventNameValueLabel, eventPriceValueLabel,
                eventDateValueLabel, eventCityValueLabel, eventCountryValueLabel, eventStreetValueLabel, eventPhoneValueLabel,
                eventTypeValueLabel, eventGenreValueLabel, eventFormationValueLabel, eventHomeValueLabel,
                eventGuestValueLabel, eventCulturalTypeValueLabel, eventGuestsValueLabel);

        for(JLabel label: labelList){
            label.setFont(new Font("", Font.BOLD, 12));
            label.setText("");
        }
    }

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

    public void setEventLabels(){
        hideSpecificEventLabels();
        int[] selectedValues = eventList.getSelectedIndices();

        if(selectedValues.length < 1){
            //System.out.println("no value set in set event label in main form");
            unsetValuesForEventLabels();
            return;
        }

        String eventString = eventList.getModel().getElementAt(selectedValues[0]).toString();

        int eventId = Integer.parseInt(eventString.split("#")[1].split(" ")[0]);
        int eventType = Integer.parseInt(eventString.split("#")[2].split(" ")[0]);
        //System.out.println("set event label in main form Event id = " + eventId + "  event type = " + eventType);

        Event event = companyService.getEventService().get(eventId,eventType);
        setValuesForEventLabels(event);

    }

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

    private void setValuesForEventLabels(Event event){

        if(event.getAvailableTicketsNumber() > 0){
            eventAvailableTicketsValueLabel.setForeground(Color.black);
            eventAvailableTicketsValueLabel.setText(String.valueOf(event.getAvailableTicketsNumber()));
        }
        else{
            eventAvailableTicketsValueLabel.setForeground(Color.red);
            eventAvailableTicketsValueLabel.setText("SOLD OUT");
        }

        eventOrganizerValueLabel.setText(companyService.getOrganizerService().get(event.getOrganizerId()).getName());
        eventNameValueLabel.setText(event.getName());
        eventPriceValueLabel.setText(String.valueOf(event.getStandardTicketPrice()));
        eventDateValueLabel.setText(event.getLocation().getDate());
        eventCityValueLabel.setText(companyService.getAddressService().get(event.getLocation().getAddressId()).getCity());
        eventCountryValueLabel.setText(companyService.getAddressService().get(event.getLocation().getAddressId()).getCountry());
        eventStreetValueLabel.setText(companyService.getAddressService().get(event.getLocation().getAddressId()).getStreet());
        eventPhoneValueLabel.setText(companyService.getAddressService().get(event.getLocation().getAddressId()).getPhoneNumber());

        if(event.getClass() == Music.class){
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

        if(event.getClass() == Sport.class){
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

        if(event.getClass() == Cultural.class){
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

    private void hideSpecificEventLabels(){
        eventMusicPanel.setVisible(false);
        eventMusicValuePanel.setVisible(false);
        eventSportPanel.setVisible(false);
        eventSportValuePanel.setVisible(false);
        eventCulturalPanel.setVisible(false);
        eventCulturalValuePanel.setVisible(false);
    }

    public void clientLogout(){
        companyService.setSessionClient(null);
        loadAllEvents();

        infoMessageLabel.setVisible(false);
        clientLoginPanel.setVisible(false);
        organizerLoginPanel.setVisible(false);
        loginPanel.setVisible(true);
    }

    public void organizerLogout(){
        companyService.setSessionOrganizer(null);
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
        loginPanel.setVisible(true);
        infoMessageLabel.setVisible(false);
    }

}
