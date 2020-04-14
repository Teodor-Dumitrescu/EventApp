package view;

import javax.swing.*;
import java.awt.*;

public class UpdateAccountForm extends JFrame {
    private JPanel updatePanel;
    private JTextField usernameTextField;
    private JTextField nameTextField;
    private JPasswordField passwordField;
    private JSpinner ageSpinner;
    private JComboBox genderComboBox;
    private JButton updateClientButton;
    private JButton createClientAccountButton;
    private JButton createOrganizerAccountButton;
    private JButton updateOrganizerButton;
    private JTextField cityTextField;
    private JTextField countryTextField;
    private JTextField streetTextField;
    private JTextField phoneTextField;
    private JLabel genderLabel;
    private JLabel ageLabel;

    public UpdateAccountForm(String title) throws HeadlessException {
        super(title);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setContentPane(updatePanel);
        this.pack();
    }

    public JSpinner getAgeSpinner() {
        return ageSpinner;
    }

    public JComboBox getGenderComboBox() {
        return genderComboBox;
    }

    public JButton getUpdateClientButton() {
        return updateClientButton;
    }

    public JButton getCreateClientAccountButton() {
        return createClientAccountButton;
    }

    public JButton getCreateOrganizerAccountButton() {
        return createOrganizerAccountButton;
    }

    public JButton getUpdateOrganizerButton() {
        return updateOrganizerButton;
    }

    public JLabel getGenderLabel() {
        return genderLabel;
    }

    public JLabel getAgeLabel() {
        return ageLabel;
    }
}
