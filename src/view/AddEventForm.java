package view;

import domain.*;
import general.Event;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class AddEventForm extends JFrame {
    private JSpinner priceSpinner;
    private JComboBox typeComboBox;
    private JButton addEventButton;
    private JSpinner ticketsSpinner;
    private JLabel genreLabel;
    private JLabel formationLabel;
    private JLabel homeLabel;
    private JLabel guestLabel;
    private JLabel typeLabel;
    private JLabel guestsLabel;
    private JTextField nameTextField;
    private JTextField cityTextField;
    private JTextField countryTextField;
    private JTextField streetTextField;
    private JTextField phoneTextField;
    private JTextField genreTextField;
    private JTextField formationTextField;
    private JTextField homeTextField;
    private JTextField guestTextField;
    private JTextField typeTextField;
    private JTextArea guestsTextArea;
    private JPanel addEventPanel;
    private JLabel nameLabel;
    private JLabel priceLabel;
    private JLabel dateLabel;
    private JLabel cityLabel;
    private JLabel countryLabel;
    private JLabel streetLabel;
    private JLabel phoneLabel;
    private JLabel ticketsNrLabel;
    private JLabel musicTypeLabel;
    private JButton updateEventButton;
    private JComboBox yearComboBox;
    private JComboBox dayComboBox;
    private JComboBox monthComboBox;
    private JLabel dateErrorLabel;

    private Event sessionEvent;//for knowing the updated event
    private MainForm mainForm;

    public AddEventForm(String title, MainForm mainForm) throws HeadlessException {
        super(title);

        //make initial setup
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        hideFields(1);
        hideFields(2);
        hideFields(3);
        this.setContentPane(addEventPanel);
        this.pack();

        this.mainForm = mainForm;
        dateErrorLabel.setVisible(false);

        addEventButton.addActionListener(actionEvent -> {

            dateErrorLabel.setVisible(false);

            if(checkFields()){ //an event is added only all fields are correctly completed

                int organizerId = mainForm.getCompanyService().getSessionOrganizer().getOrganizerId();
                String tmp = priceSpinner.getValue().toString();
                float price = Float.parseFloat(tmp);
                String type = typeComboBox.getItemAt(typeComboBox.getSelectedIndex()).toString();

                int availableTicketsNumber = Integer.parseInt(ticketsSpinner.getValue().toString());

                String day = dayComboBox.getItemAt(dayComboBox.getSelectedIndex()).toString();
                String month = monthComboBox.getItemAt(monthComboBox.getSelectedIndex()).toString();
                String year = yearComboBox.getItemAt(yearComboBox.getSelectedIndex()).toString();
                String date = day + "-" + month + "-" + year;

                //insert the address first
                mainForm.getCompanyService().getAddressService().add(new Address(countryTextField.getText(),
                        cityTextField.getText(),streetTextField.getText(),phoneTextField.getText()));

                //get inserted address id
                int addressId = mainForm.getCompanyService().getAddressService().getLastIndex();
                Event event = null;
                switch (type){
                    case "Music":
                        event = new Music(organizerId,1,nameTextField.getText(),price,availableTicketsNumber,
                                new Location(date,addressId),genreTextField.getText(),formationTextField.getText());
                        mainForm.getCompanyService().getEventService().add(event);
                        mainForm.getCompanyService().getAuditService().addLogMessage("Created Music event " + event.getName());
                        break;
                    case "Sport":
                         event = new Sport(organizerId,2,nameTextField.getText(),price,availableTicketsNumber,
                                 new Location(date,addressId),homeTextField.getText(),guestTextField.getText());
                        mainForm.getCompanyService().getEventService().add(event);
                        mainForm.getCompanyService().getAuditService().addLogMessage("Created Sport event " + event.getName());
                        break;
                    case "Cultural":
                        event = new Cultural(organizerId,3,nameTextField.getText(),price,availableTicketsNumber,
                                new Location(date,addressId),typeTextField.getText(),guestsTextArea.getText());
                        mainForm.getCompanyService().getEventService().add(event);
                        mainForm.getCompanyService().getAuditService().addLogMessage("Created Cultural event " + event.getName());
                        break;
                }


                if(event != null) {
                    mainForm.getInfoMessageLabel().setVisible(true);
                    mainForm.getInfoMessageLabel().setText("Event " + event.getName() + " created");
                }

                this.setVisible(false);
                mainForm.loadOrganizerEvents();
                mainForm.setEnabled(true);
                mainForm.requestFocus();
            }

        });
        typeComboBox.addActionListener(actionEvent -> {
            String type = typeComboBox.getItemAt(typeComboBox.getSelectedIndex()).toString();

            switch (type){
                case "Select Type":
                    hideFields(1);
                    hideFields(2);
                    hideFields(3);
                    break;
                case "Music":
                    showFields(1);
                    hideFields(2);
                    hideFields(3);
                    break;
                case "Sport":
                    showFields(2);
                    hideFields(1);
                    hideFields(3);
                    break;
                case "Cultural":
                    showFields(3);
                    hideFields(1);
                    hideFields(2);
                    break;
            }

        });
        updateEventButton.addActionListener(actionEvent -> {

            dateErrorLabel.setVisible(false);

            if(checkFields()){
                String tmp = priceSpinner.getValue().toString();
                float price = Float.parseFloat(tmp);

                int availableTicketsNumber = Integer.parseInt(ticketsSpinner.getValue().toString());

                this.sessionEvent.setName(nameTextField.getText());
                this.sessionEvent.setStandardTicketPrice(price);
                this.sessionEvent.setAvailableTicketsNumber(availableTicketsNumber);


                String day = dayComboBox.getItemAt(dayComboBox.getSelectedIndex()).toString();
                String month = monthComboBox.getItemAt(monthComboBox.getSelectedIndex()).toString();
                String year = yearComboBox.getItemAt(yearComboBox.getSelectedIndex()).toString();
                String date = day + "-" + month + "-" + year;

                this.sessionEvent.getLocation().setDate(date);

                Address address =  mainForm.getCompanyService().getAddressService().get(this.sessionEvent.getLocation().getAddressId());
                address.setCity(cityTextField.getText());
                address.setCountry(countryTextField.getText());
                address.setStreet(streetTextField.getText());
                address.setPhoneNumber(phoneTextField.getText());
                mainForm.getCompanyService().getAddressService().update(address);


                String type = typeComboBox.getItemAt(typeComboBox.getSelectedIndex()).toString();
                boolean isUpdated = false;
                switch (type){
                    case "Music":
                        if(this.sessionEvent.getClass() == Music.class) {
                            ((Music) this.sessionEvent).setMusicGenre(genreTextField.getText());
                            ((Music) this.sessionEvent).setFormationName(formationTextField.getText());
                            mainForm.getCompanyService().getAuditService().addLogMessage("Updated Music event " +
                                    this.sessionEvent.getName());
                            isUpdated = true;
                        }
                        break;
                    case "Sport":
                        if(this.sessionEvent.getClass() == Sport.class) {
                            ((Sport) this.sessionEvent).setHome(homeTextField.getText());
                            ((Sport) this.sessionEvent).setGuest(guestTextField.getText());
                            mainForm.getCompanyService().getAuditService().addLogMessage("Updated Sport event " +
                                    this.sessionEvent.getName());
                            isUpdated = true;
                        }
                        break;
                    case "Cultural":
                        if(this.sessionEvent.getClass() == Cultural.class) {
                            ((Cultural) this.sessionEvent).setType(typeTextField.getText());
                            ((Cultural) this.sessionEvent).setGuests(guestsTextArea.getText());
                            mainForm.getCompanyService().getAuditService().addLogMessage("Updated Cultural event " +
                                    this.sessionEvent.getName());
                            isUpdated = true;
                        }
                        break;
                }

                //organizer has changed the type of event
                Event aux = this.sessionEvent;
                if(!isUpdated){
                    switch (type){
                        case "Music":
                            aux = new Music(this.sessionEvent.getOrganizerId(),1,this.sessionEvent.getName(),
                                    this.sessionEvent.getStandardTicketPrice(),this.sessionEvent.getAvailableTicketsNumber(),
                                    new Location(this.sessionEvent.getLocation().getDate(),this.sessionEvent.getLocation().getAddressId()),
                                    genreTextField.getText(),formationTextField.getText());

                            mainForm.getCompanyService().getEventService().remove(this.sessionEvent);
                            mainForm.getCompanyService().getEventService().add(aux);
                            break;
                        case "Sport":
                            aux = new Sport(this.sessionEvent.getOrganizerId(),2,this.sessionEvent.getName(),
                                    this.sessionEvent.getStandardTicketPrice(),this.sessionEvent.getAvailableTicketsNumber(),
                                    new Location(this.sessionEvent.getLocation().getDate(),this.sessionEvent.getLocation().getAddressId()),
                                    homeTextField.getText(),guestTextField.getText());

                            mainForm.getCompanyService().getEventService().remove(this.sessionEvent);
                            mainForm.getCompanyService().getEventService().add(aux);
                            break;
                        case "Cultural":
                            aux = new Cultural(this.sessionEvent.getOrganizerId(),3,this.sessionEvent.getName(),
                                    this.sessionEvent.getStandardTicketPrice(),this.sessionEvent.getAvailableTicketsNumber(),
                                    new Location(this.sessionEvent.getLocation().getDate(),this.sessionEvent.getLocation().getAddressId()),
                                    typeTextField.getText(),guestsTextArea.getText());

                            mainForm.getCompanyService().getEventService().remove(this.sessionEvent);
                            mainForm.getCompanyService().getEventService().add(aux);
                            break;
                    }
                }
                else {
                    mainForm.getCompanyService().getEventService().update(this.sessionEvent);
                }



                this.setVisible(false);
                mainForm.loadOrganizerEvents();
                mainForm.setEventLabels();
                mainForm.setEnabled(true);
                mainForm.requestFocus();

            }
        });

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                mainForm.setEnabled(true);
                mainForm.requestFocus();
            }
        });
    }

    private boolean checkFields(){

        boolean isError = false;

        isError = isErrorCheck(false, nameTextField, nameLabel);
        isError = isErrorCheck(isError, cityTextField, cityLabel);
        isError = isErrorCheck(isError, countryTextField, countryLabel);
        isError = isErrorCheck(isError, streetTextField, streetLabel);
        isError = isErrorCheck(isError, phoneTextField, phoneLabel);

        String tmp = priceSpinner.getValue().toString();
        double price = Double.parseDouble(tmp);

        if(price <= 0){
            priceLabel.setForeground(Color.red);
            isError = true;
        }
        else{
            priceLabel.setForeground(Color.black);
        }

        tmp = ticketsSpinner.getValue().toString();
        int ticketsNr = Integer.parseInt(tmp);

        if(ticketsNr <= 0){
            ticketsNrLabel.setForeground(Color.red);
            isError = true;
        }
        else{
            ticketsNrLabel.setForeground(Color.black);
        }

        //check date combobox
        String daySelected = dayComboBox.getItemAt(dayComboBox.getSelectedIndex()).toString();
        String monthSelected = monthComboBox.getItemAt(monthComboBox.getSelectedIndex()).toString();
        String yearSelected = yearComboBox.getItemAt(yearComboBox.getSelectedIndex()).toString();

        //TODO de verificat cum trebuie daca o data este introdusa corect ( daca pt o luna corespund zilele normale, an bisect,
        // mai mare decat data curenta)
        if(daySelected.equals("-") || monthSelected.equals("-") || yearSelected.equals("-")){
            isError = true;
            dateLabel.setForeground(Color.red);
            dateErrorLabel.setVisible(true);
            dateErrorLabel.setText("Insert a correct date");
        }
        else{
            dateLabel.setForeground(Color.black);
            dateErrorLabel.setVisible(false);
        }

        //check music genres types
        String optionSelected = typeComboBox.getItemAt(typeComboBox.getSelectedIndex()).toString();

        switch (optionSelected){
                case "Select Type":
                    musicTypeLabel.setForeground(Color.red);
                    hideFields(1);
                    hideFields(2);
                    hideFields(3);
                    isError = true;
                    break;
                case "Music":
                    showFields(1);
                    hideFields(2);
                    hideFields(3);
                    musicTypeLabel.setForeground(Color.black);
                    isError = isErrorCheck(isError,genreTextField,genreLabel);
                    isError = isErrorCheck(isError,formationTextField,formationLabel);
                    break;
                case "Sport":
                    showFields(2);
                    hideFields(1);
                    hideFields(3);
                    musicTypeLabel.setForeground(Color.black);
                    isError = isErrorCheck(isError,homeTextField,homeLabel);
                    isError = isErrorCheck(isError,guestTextField,guestLabel);
                    break;
                case "Cultural":
                    showFields(3);
                    hideFields(1);
                    hideFields(2);
                    musicTypeLabel.setForeground(Color.black);
                    isError = isErrorCheck(isError,typeTextField,typeLabel);
                    if(guestsTextArea.getText().isEmpty()){
                        guestsLabel.setForeground(Color.red);
                        isError = true;
                    }
                    else{
                        guestsLabel.setForeground(Color.black);
                    }
                    break;
        }

        return !isError;
    }

    private boolean isErrorCheck(boolean isError, JTextField textField, JLabel label) {

        if(textField.getText().isEmpty()){
            label.setForeground(Color.red);
            isError = true;
        }
        else{
            label.setForeground(Color.black);
        }

        return isError;
    }

    private void hideFields(int option){
        switch (option){
            case 1:
                genreLabel.setVisible(false);
                genreTextField.setVisible(false);
                formationLabel.setVisible(false);
                formationTextField.setVisible(false);
                break;
            case 2:
                homeLabel.setVisible(false);
                homeTextField.setVisible(false);
                guestLabel.setVisible(false);
                guestTextField.setVisible(false);
                break;
            case 3:
                typeLabel.setVisible(false);
                typeTextField.setVisible(false);
                guestsLabel.setVisible(false);
                guestsTextArea.setVisible(false);
                break;
        }
    }

    private void showFields(int option){
        switch (option){
            case 1:
                genreLabel.setVisible(true);
                genreTextField.setVisible(true);
                formationLabel.setVisible(true);
                formationTextField.setVisible(true);
                break;
            case 2:
                homeLabel.setVisible(true);
                homeTextField.setVisible(true);
                guestLabel.setVisible(true);
                guestTextField.setVisible(true);
                break;
            case 3:
                typeLabel.setVisible(true);
                typeTextField.setVisible(true);
                guestsLabel.setVisible(true);
                guestsTextArea.setVisible(true);
                break;
        }
    }

    public void showData(Event event) {
        this.sessionEvent = event;

        nameTextField.setText(event.getName());
        priceSpinner.setValue(event.getStandardTicketPrice());

        String[] date = event.getLocation().getDate().split("-");
        dayComboBox.setSelectedItem(date[0]);
        monthComboBox.setSelectedItem(date[1]);
        yearComboBox.setSelectedItem(date[2]);

        ticketsSpinner.setValue(event.getAvailableTicketsNumber());

        Address address =  mainForm.getCompanyService().getAddressService().get(this.sessionEvent.getLocation().getAddressId());
        cityTextField.setText(address.getCity());
        countryTextField.setText(address.getCountry());
        streetTextField.setText(address.getStreet());
        phoneTextField.setText(address.getPhoneNumber());

        if(event.getClass() == Music.class){
            typeComboBox.setSelectedItem("Music");
            showFields(1);
            hideFields(2);
            hideFields(3);
            genreTextField.setText(((Music) event).getMusicGenre());
            formationTextField.setText(((Music) event).getFormationName());
            return;
        }

        if(event.getClass() == Sport.class){
            typeComboBox.setSelectedItem("Sport");
            showFields(2);
            hideFields(1);
            hideFields(3);
            homeTextField.setText(((Sport) event).getHome());
            guestTextField.setText(((Sport) event).getGuest());
        }

        if(event.getClass() == Cultural.class){
            typeComboBox.setSelectedItem("Cultural");
            showFields(3);
            hideFields(1);
            hideFields(2);
            typeTextField.setText(((Cultural) event).getType());
            guestsTextArea.setText(((Cultural) event).getGuests());
        }

    }

    public JButton getAddEventButton() {
        return addEventButton;
    }

    public JButton getUpdateEventButton() {
        return updateEventButton;
    }
}
