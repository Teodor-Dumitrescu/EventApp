package view;

import domain.Address;
import domain.Client;
import domain.Organizer;
import domain.Ticket;
import general.Event;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

public class UpdateAccountForm extends JFrame {
    private JPanel updatePanel;
    private JTextField usernameTextField;
    private JTextField nameTextField;
    private JPasswordField passwordField;
    private JSpinner ageSpinner;
    private JComboBox genderComboBox;
    private JButton updateAccountButton;
    private JButton createClientAccountButton;
    private JButton createOrganizerAccountButton;
    private JButton deleteAccountButton;
    private JTextField cityTextField;
    private JTextField countryTextField;
    private JTextField streetTextField;
    private JTextField phoneTextField;
    private JLabel genderLabel;
    private JLabel ageLabel;
    private JLabel usernameLabel;
    private JLabel passwordLabel;
    private JLabel nameLabel;
    private JLabel cityLabel;
    private JLabel countryLabel;
    private JLabel streetLabel;
    private JLabel phoneLabel;
    private JLabel fixedUsername;
    private JLabel fixedUsernameLabel;
    private JLabel ageErrorLabel;

    private MainForm mainForm;

    public UpdateAccountForm(String title, MainForm mainForm) throws HeadlessException {
        super(title);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setContentPane(updatePanel);
        this.pack();

        this.mainForm = mainForm;

        Client sessionClient =  mainForm.getCompanyService().getSessionClient();
        Organizer sessionOrganizer =  mainForm.getCompanyService().getSessionOrganizer();

        //initial setup
        if (sessionClient == null && sessionOrganizer == null){
            fixedUsername.setVisible(false);
            fixedUsernameLabel.setVisible(false);
            usernameTextField.setVisible(true);
            usernameLabel.setVisible(true);
        }
        else{
            fixedUsername.setVisible(true);
            fixedUsernameLabel.setVisible(true);
            usernameTextField.setVisible(false);
            usernameLabel.setVisible(false);

            if(sessionClient != null){
                fixedUsername.setText(sessionClient.getUsername());
            }
            else{
                fixedUsername.setText(sessionOrganizer.getUsername());
            }
        }


        ageErrorLabel.setVisible(false);

        createClientAccountButton.addActionListener(actionEvent -> {

            if(checkFields(true)){

                String username = usernameTextField.getText();

                //a client is added only if his username does not exist in database
                if(mainForm.getCompanyService().getClientService().exist(username)) {
                        fixedUsername.setVisible(true);
                        fixedUsername.setForeground(Color.red);
                        fixedUsername.setText("Username exist");
                        return;
                }

                String password = new String(passwordField.getPassword());
                String tmp = ageSpinner.getValue().toString();
                int age = Integer.parseInt(tmp);
                String gender = genderComboBox.getItemAt(genderComboBox.getSelectedIndex()).toString();

                //first insert address
                mainForm.getCompanyService().getAddressService().add(new Address(countryTextField.getText(),
                        cityTextField.getText(),streetTextField.getText(),phoneTextField.getText()));

                //get address id
                int addressId = mainForm.getCompanyService().getAddressService().getLastIndex();

                Client client = new Client(username,password,nameTextField.getText(),age,gender,addressId);

                mainForm.getCompanyService().getClientService().add(client);

                mainForm.getInfoMessageLabel().setVisible(true);
                mainForm.getInfoMessageLabel().setText("Client account created");
                mainForm.getCompanyService().getAuditService().addLogMessage("Added client " + client.getUsername());
                this.setVisible(false);
                mainForm.setEnabled(true);
                mainForm.requestFocus();
            }

        });
        createOrganizerAccountButton.addActionListener(actionEvent -> {

            if(checkFields(false)){

                String username = usernameTextField.getText();

                //a organizer is added only if his username does not exist in database
                if(mainForm.getCompanyService().getOrganizerService().exist(username)) {
                    fixedUsername.setVisible(true);
                    fixedUsername.setForeground(Color.red);
                    fixedUsername.setText("Username exist");
                    return;
                }

                String password = new String(passwordField.getPassword());

                //first insert address
                mainForm.getCompanyService().getAddressService().add(new Address(countryTextField.getText(),
                        cityTextField.getText(),streetTextField.getText(),phoneTextField.getText()));

                //get address id
                int addressId = mainForm.getCompanyService().getAddressService().getLastIndex();

                Organizer organizer = new Organizer(username,password,nameTextField.getText(),addressId);
                mainForm.getCompanyService().getOrganizerService().add(organizer);

                mainForm.getInfoMessageLabel().setVisible(true);
                mainForm.getInfoMessageLabel().setText("Organizer account created");
                mainForm.getCompanyService().getAuditService().addLogMessage("Added organizer " + organizer.getUsername());
                this.setVisible(false);
                mainForm.setEnabled(true);
                mainForm.requestFocus();
            }

        });

        updateAccountButton.addActionListener(actionEvent -> {

            if(sessionClient != null) { //update for client

                //check new data
                if (checkFields(true)) {
                    String tmp = ageSpinner.getValue().toString();
                    int age = Integer.parseInt(tmp);

                    sessionClient.setPassword(new String(passwordField.getPassword()));
                    sessionClient.setName(nameTextField.getText());
                    sessionClient.setAge(age);
                    sessionClient.setGender(genderComboBox.getItemAt(genderComboBox.getSelectedIndex()).toString());

                    Address address = mainForm.getCompanyService().getAddressService().
                            get(mainForm.getCompanyService().getSessionClient().getAddressId());

                    address.setCity(cityTextField.getText());
                    address.setCountry(countryTextField.getText());
                    address.setStreet(streetTextField.getText());
                    address.setPhoneNumber(phoneTextField.getText());

                    mainForm.getCompanyService().getClientService().update(sessionClient);
                    mainForm.getCompanyService().getAddressService().update(address);

                    mainForm.getCompanyService().getAuditService().addLogMessage("Update client " +
                            mainForm.getCompanyService().getSessionClient().getUsername());

                    this.setVisible(false);
                    mainForm.getInfoMessageLabel().setText("Account updated !");
                    mainForm.getInfoMessageLabel().setVisible(true);
                    mainForm.setEnabled(true);
                    mainForm.requestFocus();
                }
            }
            else { //update for organizer

                if (checkFields(false)) {

                    assert sessionOrganizer != null;
                    sessionOrganizer.setPassword(new String(passwordField.getPassword()));
                    sessionOrganizer.setName(nameTextField.getText());

                    Address address = mainForm.getCompanyService().getAddressService().
                            get(mainForm.getCompanyService().getSessionOrganizer().getAddressId());

                    address.setCity(cityTextField.getText());
                    address.setCountry(countryTextField.getText());
                    address.setStreet(streetTextField.getText());
                    address.setPhoneNumber(phoneTextField.getText());

                    mainForm.getCompanyService().getOrganizerService().update(sessionOrganizer);
                    mainForm.getCompanyService().getAddressService().update(address);

                    mainForm.getCompanyService().getAuditService().addLogMessage("Update organizer " +
                            mainForm.getCompanyService().getSessionOrganizer().getUsername());

                    this.setVisible(false);
                    mainForm.getInfoMessageLabel().setText("Account updated !");
                    mainForm.getInfoMessageLabel().setVisible(true);
                    mainForm.setEnabled(true);
                    mainForm.requestFocus();
                }
            }

        });
        deleteAccountButton.addActionListener(actionEvent -> {

            if(sessionClient != null){ //delete client account
                //if I delete a client account I delete his tickets only if no organizer exist for tickets

                //TODO eventual sa sterg si ticketele atunci cand sterg un client => trebuie verificat sa nu mai fie
                // nici un organizator caruia sa ii apara ca sold tickets

                int addressId = sessionClient.getAddressId();

                //unlink all tickets from database from current client
                //I link tickets to a admin client
                List<Ticket> clientTickets = mainForm.getCompanyService().getTicketService().getClientTickets(sessionClient.getClientId());
                for(Ticket ticket: clientTickets){
                    ticket.setClientId(1); //1 is admin client id
                    mainForm.getCompanyService().getTicketService().update(ticket);
                }

                //remove client
                mainForm.getCompanyService().getClientService().remove(sessionClient);

                //remove address
                mainForm.getCompanyService().getAddressService().
                        remove(mainForm.getCompanyService().getAddressService().get(addressId));

                //go in offline mode
                mainForm.clientLogout();
                this.setVisible(false);

                mainForm.getInfoMessageLabel().setText("Account deleted !");
                mainForm.getInfoMessageLabel().setVisible(true);
                mainForm.setEnabled(true);
                mainForm.requestFocus();
            }
            else{ //delete organizer account

                //if I delete an organizer account I must delete all his events and all tickets
                //which have no client in database (client account was deleted)

                assert sessionOrganizer != null;
                int addressId = sessionOrganizer.getAddressId();
                int organizerId = sessionOrganizer.getOrganizerId();


                //unlink all sold tickets from database from current organizer
                //I link tickets to a admin organizer
                List<Ticket> organizerTickets = mainForm.getCompanyService().getTicketService().
                        getOrganizerSoldTickets(sessionOrganizer.getOrganizerId());

                for(Ticket ticket: organizerTickets){
                    ticket.setOrganizerId(1); //1 is admin organizer id
                    mainForm.getCompanyService().getTicketService().update(ticket);
                }

                //remove all his events
                List<Event> eventList = mainForm.getCompanyService().getEventService().
                        getOrganizerEvents(organizerId);

                for(Event event: eventList){

                    //remove event
                    mainForm.getCompanyService().getEventService().remove(event);

                    //remove event address
                    mainForm.getCompanyService().getAddressService().remove(mainForm.getCompanyService().
                            getAddressService().get(event.getLocation().getAddressId()));

                }

                //remove organizer
                mainForm.getCompanyService().getOrganizerService().remove(sessionOrganizer);

                //remove organizer address
                mainForm.getCompanyService().getAddressService().remove(mainForm.getCompanyService().
                        getAddressService().get(addressId));


                //go in offline mode
                mainForm.organizerLogout();
                this.setVisible(false);

                mainForm.getInfoMessageLabel().setText("Account deleted !");
                mainForm.getInfoMessageLabel().setVisible(true);
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

    public void showClientData(Client client){
        if(client != null) {
            Address address = mainForm.getCompanyService().getAddressService().
                    get(client.getAddressId());
            showData(true,client.getUsername(), client.getPassword(), client.getName(), client.getAge(),client.getGender(),address);
        }
    }

    public void showOrganizerData(Organizer organizer){
        if(organizer != null) {
            Address address = mainForm.getCompanyService().getAddressService().
                    get(organizer.getAddressId());
            showData(false, organizer.getUsername(), organizer.getPassword(), organizer.getName(), 0, "null", address);
        }
    }

    private void showData(boolean isClient, String username, String password, String name, int age, String gender, Address address) {
            usernameTextField.setText(username);
            passwordField.setText(password);
            nameTextField.setText(name);
            cityTextField.setText(address.getCity());
            countryTextField.setText(address.getCountry());
            streetTextField.setText(address.getStreet());
            phoneTextField.setText(address.getPhoneNumber());

            if(isClient){
                ageSpinner.setValue(age);
                genderComboBox.setSelectedItem(gender);
            }
    }

    private boolean checkFields(boolean isClient){

        fixedUsername.setVisible(false);
        boolean isError = false;

        isError = isErrorCheck(isError, usernameTextField, usernameLabel);
        isError = isErrorCheck(isError, nameTextField, nameLabel);
        isError = isErrorCheck(isError, cityTextField, cityLabel);
        isError = isErrorCheck(isError, countryTextField, countryLabel);
        isError = isErrorCheck(isError, streetTextField, streetLabel);
        isError = isErrorCheck(isError, phoneTextField, phoneLabel);

        if(passwordField.getPassword().length == 0){
            passwordLabel.setForeground(Color.red);
            isError = true;
        }
        else{
            passwordField.setForeground(Color.black);
        }

        if(isClient) {
            String tmp = ageSpinner.getValue().toString();
            int age = Integer.parseInt(tmp);

            if (age < 14) {
                ageLabel.setForeground(Color.red);
                ageErrorLabel.setVisible(true);
                ageErrorLabel.setText("Minimum age is 14");
                isError = true;
            }
            else {
                if(age > 150) {
                    ageLabel.setForeground(Color.red);
                    ageErrorLabel.setVisible(true);
                    ageErrorLabel.setText("Really " + age + " ? You are a zombie?");
                    isError = true;
                }
                else {
                    ageLabel.setForeground(Color.black);
                    ageErrorLabel.setVisible(false);
                }
            }

            if (genderComboBox.getItemAt(genderComboBox.getSelectedIndex()).toString().equals("Select Gender")) {
                genderLabel.setForeground(Color.red);
                isError = true;
            } else {
                genderLabel.setForeground(Color.black);
            }
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

    public JSpinner getAgeSpinner() {
        return ageSpinner;
    }

    public JComboBox getGenderComboBox() {
        return genderComboBox;
    }

    public JButton getUpdateAccountButton() {
        return updateAccountButton;
    }

    public JButton getCreateClientAccountButton() {
        return createClientAccountButton;
    }

    public JButton getCreateOrganizerAccountButton() {
        return createOrganizerAccountButton;
    }

    public JButton getDeleteAccountButton() {
        return deleteAccountButton;
    }

    public JLabel getGenderLabel() {
        return genderLabel;
    }

    public JLabel getAgeLabel() {
        return ageLabel;
    }
}
