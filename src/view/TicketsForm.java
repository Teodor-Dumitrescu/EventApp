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

        showTicketsButton.addActionListener(actionEvent -> {
            setTicketDescribeLabels();
            showAllTickets(mainForm);
        });
        cancelTicketButton.addActionListener(actionEvent -> {

            int[] indexList = ticketList.getSelectedIndices();

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

            List<Ticket> tmpList = new ArrayList<>();
            for(int idx: indexList){
                String tmp = ticketList.getModel().getElementAt(idx).toString();
                int ticketId = Integer.parseInt(tmp.split("#")[1].split(" ")[0]);
                tmpList.add(mainForm.getCompanyService().getTicketService().get(ticketId));
            }

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

            showAllTickets(mainForm);
            setTicketDescribeLabels();
            mainForm.setEventLabels();
        });

        searchTicketTextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);

                showAllTickets(mainForm);

                String searchValue = searchTicketTextField.getText().toLowerCase();
                if(searchValue.equals("search ticket")){
                    return;
                }

                //System.out.println(searchValue);

                //get tickets model
                DefaultListModel<String> tmpLoadModel = new DefaultListModel<>();
                int sizeModel = ticketList.getModel().getSize();

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

                    if(searchString.toLowerCase().contains(searchValue)){
                        tmpLoadModel.addElement(ticketList.getModel().getElementAt(idx).toString());
                    }
                }

                ticketList.setModel(tmpLoadModel);
                setTicketDescribeLabels();
            }
        });

        ticketList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                setTicketLabels();
            }
        });
        ticketList.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                setTicketLabels();
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

    private void showAllTickets(MainForm mainForm){

        DefaultListModel<String> loadModel = new DefaultListModel<>();

        if(mainForm.getCompanyService().getSessionClient() != null) {
            //show client tickets
            int clientId = mainForm.getCompanyService().getSessionClient().getClientId();
            List<Ticket> ticketListLoad = mainForm.getCompanyService().getTicketService().getClientTickets(clientId);

            for (Ticket ticket : ticketListLoad) {
                    loadModel.addElement(ticket.presentationPrint());
            }

            ticketList.setModel(loadModel);
            return;
        }


        //get all sold Tickets for this organizer
        int organizerId = mainForm.getCompanyService().getSessionOrganizer().getOrganizerId();
        List<Ticket> ticketListLoad = mainForm.getCompanyService().getTicketService().getOrganizerSoldTickets(organizerId);

        for (Ticket ticket : ticketListLoad) {
            loadModel.addElement(ticket.presentationPrint());
        }

        ticketList.setModel(loadModel);
    }

    private void setTicketDescribeLabels(){

        List<JLabel> labelList = new ArrayList<>();
        addLabels(labelList,ticketIdentifierLabel,eventNameLabel,organizerLabel,seatLabel,priceLabel,dateLabel,cityLabel);

        for(JLabel label: labelList){
            label.setFont(new Font("", Font.BOLD, 16));
        }

        labelList.clear();
        addLabels(labelList, ticketIdentifierValueLabel,eventNameValueLabel,organizerValueLabel,seatValueLabel,
                priceValueLabel,dateValueLabel,cityValueLabel);

        for(JLabel label: labelList){
            label.setFont(new Font("", Font.BOLD, 12));
            label.setText("");
        }
    }

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

    private void setTicketLabels(){

        int[] selectedValues = ticketList.getSelectedIndices();

        if(selectedValues.length < 1){
            //System.out.println("no value set in set event label in main form");
            unsetValuesForTicketLabels();
            return;
        }

        String ticketString = ticketList.getModel().getElementAt(selectedValues[0]).toString();

        int ticketId = Integer.parseInt(ticketString.split("#")[1].split(" ")[0]);
        //System.out.println("Ticket id = " + ticketId);

        Ticket ticket = mainForm.getCompanyService().getTicketService().get(ticketId);
        setValuesForEventLabels(ticket);
    }

    private void unsetValuesForTicketLabels(){
        ticketIdentifierValueLabel.setText("");
        seatValueLabel.setText("");
        priceValueLabel.setText("");
        eventNameValueLabel.setText("");
        dateValueLabel.setText("");
        cityValueLabel.setText("");
        organizerValueLabel.setText("");
    }

    private void setValuesForEventLabels(Ticket ticket){
        Event event = mainForm.getCompanyService().getEventService().get(ticket.getEventId(),ticket.getEventType());
        Organizer organizer;

        ticketIdentifierValueLabel.setText(ticket.getTicketIdentifier());
        seatValueLabel.setText(ticket.getSeat());
        priceValueLabel.setText(String.valueOf(ticket.getPrice()));

        //add the event values only if event exist
        if(event != null){
            eventNameValueLabel.setText(event.getName());
            dateValueLabel.setText(event.getLocation().getDate());
            Address address = mainForm.getCompanyService().getAddressService().get(event.getLocation().getAddressId());
            cityValueLabel.setText(address.getCity());
        }
        else{
            eventNameValueLabel.setText("ERROR [event deleted]");
            dateValueLabel.setText("");
            //Address address = mainForm.getCompanyService().getAddressService().get(event.getAddressId());
            cityValueLabel.setText("");
        }

        //add the organizer values only if organizer exist
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
