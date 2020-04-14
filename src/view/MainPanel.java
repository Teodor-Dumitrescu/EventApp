package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class MainPanel extends JFrame{
    private JButton loginAsClientButton;
    private JPasswordField loginPasswordField;
    private JTextField usernameTextField;
    private JCheckBox musicCheckBox;
    private JCheckBox culturalCheckBox;
    private JTextField dateTextField;
    private JTextField cityTextField;
    private JCheckBox sportCheckBox;
    private JPanel mainPanel;
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

    public MainPanel(String title) throws HeadlessException {
        super(title);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        clientLoginPanel.setVisible(false);
        organizerLoginPanel.setVisible(false);

        addEventButton.setVisible(false);
        removeEventButton.setVisible(false);
        this.setContentPane(mainPanel);
        this.pack();


        loginAsClientButton.addActionListener(actionEvent -> {
            loginPanel.setVisible(false);
            organizerLoginPanel.setVisible(false);
            clientLoginPanel.setVisible(true);
        });
        loginAsOrganizerButton.addActionListener(actionEvent -> {

            //hide some filters
            sortByPriceCheckBox.setVisible(false);
            sortByDateCheckBox.setVisible(false);
            dateTextField.setVisible(false);
            cityTextField.setVisible(false);
            startingPriceLabel.setVisible(false);
            priceSpinner.setVisible(false);
            resetFiltersButton.setVisible(false);

            //show specific fields
            addEventButton.setVisible(true);
            removeEventButton.setVisible(true);


            loginPanel.setVisible(false);
            clientLoginPanel.setVisible(false);
            organizerLoginPanel.setVisible(true);
        });
        updateAccountOrganizerButton.addActionListener(actionEvent -> {
            UpdateAccountForm updateFrame= new UpdateAccountForm("Update Organizer Account");

            //hide form fields
            updateFrame.getAgeSpinner().setVisible(false);
            updateFrame.getAgeLabel().setVisible(false);
            updateFrame.getGenderComboBox().setVisible(false);
            updateFrame.getGenderLabel().setVisible(false);
            updateFrame.getUpdateClientButton().setVisible(false);
            updateFrame.getCreateClientAccountButton().setVisible(false);
            updateFrame.getCreateOrganizerAccountButton().setVisible(false);
            updateFrame.setSize(330,450);
            updateFrame.setVisible(true);
        });
        newOrganizerAccountButton.addActionListener(actionEvent -> {
            UpdateAccountForm updateFrame= new UpdateAccountForm("Create Organizer Account");

            //hide form fields
            updateFrame.getAgeSpinner().setVisible(false);
            updateFrame.getAgeLabel().setVisible(false);
            updateFrame.getGenderComboBox().setVisible(false);
            updateFrame.getGenderLabel().setVisible(false);
            updateFrame.getUpdateOrganizerButton().setVisible(false);
            updateFrame.getUpdateClientButton().setVisible(false);
            updateFrame.getCreateClientAccountButton().setVisible(false);
            updateFrame.setSize(330,450);
            updateFrame.setVisible(true);
        });
        updateAccountClientButton.addActionListener(actionEvent -> {
            UpdateAccountForm updateFrame = new UpdateAccountForm("Update Client Account");
            updateFrame.getUpdateOrganizerButton().setVisible(false);
            updateFrame.getCreateClientAccountButton().setVisible(false);
            updateFrame.getCreateOrganizerAccountButton().setVisible(false);
            updateFrame.setSize(330,450);
            updateFrame.setVisible(true);
        });
        newClientAccountButton.addActionListener(actionEvent -> {
            UpdateAccountForm updateFrame = new UpdateAccountForm("Create Client Account");
            updateFrame.getUpdateOrganizerButton().setVisible(false);
            updateFrame.getUpdateClientButton().setVisible(false);
            updateFrame.getCreateOrganizerAccountButton().setVisible(false);
            updateFrame.setSize(330,450);
            updateFrame.setVisible(true);
        });
        viewSoldTicketsButton.addActionListener(actionEvent -> {
            TicketsForm ticketsFrame = new TicketsForm("Tickets");
            ticketsFrame.getClientTicketsPanel().setVisible(false);
            ticketsFrame.setSize(280,450);
            ticketsFrame.setVisible(true);
        });
        viewTicketsButton.addActionListener(actionEvent -> {
            TicketsForm ticketsFrame = new TicketsForm("Tickets");
            ticketsFrame.getOrganizerTicketsPanel().setVisible(false);
            ticketsFrame.setSize(280,450);
            ticketsFrame.setVisible(true);
        });
        organizerLogoutButton.addActionListener(actionEvent -> {

            //show hided filters
            sortByPriceCheckBox.setVisible(true);
            sortByDateCheckBox.setVisible(true);
            dateTextField.setVisible(true);
            cityTextField.setVisible(true);
            startingPriceLabel.setVisible(true);
            priceSpinner.setVisible(true);
            resetFiltersButton.setVisible(true);

            //hide specific fields
            addEventButton.setVisible(false);
            removeEventButton.setVisible(false);

            clientLoginPanel.setVisible(false);
            organizerLoginPanel.setVisible(false);
            loginPanel.setVisible(true);
        });
        clientLogoutButton.addActionListener(actionEvent -> {
            clientLoginPanel.setVisible(false);
            organizerLoginPanel.setVisible(false);
            loginPanel.setVisible(true);
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
                loginPasswordField.setText("");
            }

            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                if(loginPasswordField.getPassword().length == 0) {
                    loginPasswordField.setText("password");
                }
            }
        });
        dateTextField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                if(dateTextField.getText().equals("Date")) {
                    dateTextField.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                if(dateTextField.getText().isEmpty()) {
                    dateTextField.setText("Date");
                }
            }
        });
        cityTextField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
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
        addEventButton.addActionListener(actionEvent -> {
            AddEventForm addEventForm = new AddEventForm("Add Event");
            addEventForm.setSize(300,480);
            addEventForm.setVisible(true);
        });
    }

}
