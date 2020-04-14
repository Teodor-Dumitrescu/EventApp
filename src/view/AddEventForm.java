package view;

import javax.swing.*;
import java.awt.*;

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
    private JTextField dateTextField;
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

    public AddEventForm(String title) throws HeadlessException {
        super(title);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setContentPane(addEventPanel);
        this.pack();
    }
}
