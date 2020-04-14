package view;

import javax.swing.*;
import java.awt.*;

public class TicketsForm extends JFrame {
    private JButton searchButton;
    private JTextField searchTextField;
    private JPanel ticketsPanel;
    private JPanel clientTicketsPanel;
    private JPanel organizerTicketsPanel;

    public TicketsForm(String title) throws HeadlessException {
        super(title);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setContentPane(ticketsPanel);
        this.pack();
    }

    public JPanel getClientTicketsPanel() {
        return clientTicketsPanel;
    }

    public JPanel getOrganizerTicketsPanel() {
        return organizerTicketsPanel;
    }
}
