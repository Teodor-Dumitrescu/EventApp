package repository;

import connection.DatabaseConnection;
import general.DatabaseManipulation;
import domain.Ticket;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Operates different types of queries in database.
 */
public class TicketRepository implements DatabaseManipulation<Ticket> {

    private static TicketRepository ticketRepositoryInstance = null;

    private TicketRepository() {}

    public static TicketRepository getTicketRepositoryInstance() {

        if(ticketRepositoryInstance == null){
            ticketRepositoryInstance = new TicketRepository();
        }
        return ticketRepositoryInstance;
    }


    /**
     * Used to check if a ticket exists using the ticket identifier.
     * Because ticketIdentifier is random generated and because this must be unique,
     * every time when a ticket is created is generated a unique ticket identifier by checking that a ticket with this
     * ticketIdentifier does not exist in database.
     *
     * @return boolean
     */
    public boolean existTicket(String ticketIdentifier){

        String query = "select * from tickets where ticket_identifier = '" + ticketIdentifier + "'";

        try {
            return DatabaseConnection.getDatabaseConnectionInstance().makeQuery(query).next();
        } catch (SQLException ex) {
            System.out.println("Exception in exist ticket by id in ticket Repository " + ex.getMessage());
            System.out.println("SQLException: " + ex.getMessage());
            return false;
        }
    }


    /**
     * Used to see what tickets the organizer session(identified by organizer id) sold.
     * Is used in GUI when a organizer click on view sold ticket button.
     *
     * @return list of tickets
     */
    public List<Ticket> getOrganizerSoldTickets(int organizerId){

        String query = "select * from tickets where organizer_id = " + organizerId;

        return getSpecificData(query);
    }


    /**
     * Used to see what tickets the client session(identified by client id) bought.
     * Is used in GUI when a client click on view ticket button.
     *
     * @return list of tickets
     */
    public List<Ticket> getClientTickets(int clientId){

        String query = "select * from tickets where client_id = " + clientId;

        return getSpecificData(query);
    }


    /**
     * A function which can return tickets for multiple types of query selections.
     * Avoid code duplicates.
     *
     * @return list of tickets
     */
    private List<Ticket> getSpecificData(String query){

        List<Ticket> ticketList = new ArrayList<>();
        ResultSet resultSet = DatabaseConnection.getDatabaseConnectionInstance().makeQuery(query);
        Ticket ticket;

        while ((ticket = parseElement(resultSet)) != null) {
            ticketList.add(ticket);
        }

        return ticketList;
    }


    /**
     * Make a list and return all tickets from database if necessary.
     *
     * @return List
     */
    @Override
    public List<Ticket> getData() {
        return getSpecificData("select * from tickets");
    }


    /**
     * Return a single ticket from database when the selector is ticket identifier.
     *
     * @return ticket
     */
    public Ticket get(String ticketIdentifier){
        String query = "select * from tickets where ticket_identifier = '" + ticketIdentifier + "'";
        System.out.println(query);
        return parseElement(DatabaseConnection.getDatabaseConnectionInstance().makeQuery(query));
    }


    /**
     * Return a single ticket from database when the selector is primary key.
     *
     * @return ticket
     */
    @Override
    public Ticket get(int index){
        String query = "select * from tickets where ticket_id = " + index;
        return parseElement(DatabaseConnection.getDatabaseConnectionInstance().makeQuery(query));
    }


    /**
     * Function used when must be created a new object from database data.
     * The function receive a row(resultSet) and split this one into columns resulting
     * the data for a new ticket object.
     *
     * @param resultSet a row with data which will be split into columns
     * @return new ticket object
     */
    @Override
    public Ticket parseElement(ResultSet resultSet){

        try {

            if(resultSet.next()) {

                int ticketId = Integer.parseInt(resultSet.getString("ticket_id"));
                int clientId = Integer.parseInt(resultSet.getString("client_id"));
                int organizerId = Integer.parseInt(resultSet.getString("organizer_id"));
                int eventId = Integer.parseInt(resultSet.getString("event_id"));
                int eventType = Integer.parseInt(resultSet.getString("event_type"));
                String ticketIdentifier = resultSet.getString("ticket_identifier");
                String seat = resultSet.getString("seat");
                float price = Float.parseFloat(resultSet.getString("price"));

                return new Ticket(ticketId,clientId,organizerId,eventId,eventType,ticketIdentifier,seat,price);
            }
            else{
                return null;
            }
        }
        catch (SQLException ex) {
            System.out.println("Exception in parseOneElement in ticket Repository " + ex.getMessage());
            System.out.println("SQLException: " + ex.getMessage());
            return null;
        }
    }


    /**
     * Main function for inserting new ticket into database.
     *
     * @param ticket object which will be inserted into database
     */
    @Override
    public void insert(Ticket ticket) {

        String insertStatement = String.format("insert into tickets (client_id, organizer_id, event_id, event_type, " +
                "ticket_identifier, seat, price) values (?, ?, ?, ?, ?, ?, ?)");

        try (PreparedStatement preparedStatement = DatabaseConnection.getDatabaseConnectionInstance().getConnection().
                prepareStatement(insertStatement)) {

            preparedStatement.setString(1, String.valueOf(ticket.getClientId()));
            preparedStatement.setString(2, String.valueOf(ticket.getOrganizerId()));
            preparedStatement.setString(3, String.valueOf(ticket.getEventId()));
            preparedStatement.setString(4, String.valueOf(ticket.getEventType()));
            preparedStatement.setString(5, ticket.getTicketIdentifier());
            preparedStatement.setString(6, ticket.getSeat());
            preparedStatement.setString(7, String.valueOf(ticket.getPrice()));

            DatabaseConnection.getDatabaseConnectionInstance().update(preparedStatement);

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error in insert function in Client Repository");
        }

    }


    /**
     * Main function for updating existing ticket from database.
     *
     * @param ticket object which will be updated
     */
    @Override
    public void update(Ticket ticket){

        String updateStatement = String.format("update tickets set ticket_id = ?, client_id = ?, organizer_id = ?, " +
                "event_id = ?, event_type = ?, ticket_identifier = ?, seat = ?, price = ? where ticket_id = ?");

        try (PreparedStatement preparedStatement = DatabaseConnection.getDatabaseConnectionInstance().getConnection().
                prepareStatement(updateStatement)) {

            preparedStatement.setString(1, String.valueOf(ticket.getTicketId()));
            preparedStatement.setString(2, String.valueOf(ticket.getClientId()));
            preparedStatement.setString(3, String.valueOf(ticket.getOrganizerId()));
            preparedStatement.setString(4, String.valueOf(ticket.getEventId()));
            preparedStatement.setString(5, String.valueOf(ticket.getEventType()));
            preparedStatement.setString(6, ticket.getTicketIdentifier());
            preparedStatement.setString(7, ticket.getSeat());
            preparedStatement.setString(8, String.valueOf(ticket.getPrice()));
            preparedStatement.setString(9, String.valueOf(ticket.getTicketId()));

            DatabaseConnection.getDatabaseConnectionInstance().update(preparedStatement);

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error in update function in Client Repository");
        }

    }


    /**
     * Main function for deleting existing ticket from database.
     *
     * @param ticket object which will be deleted from database
     */
    @Override
    public void delete(Ticket ticket){
        String query = "delete from tickets where ticket_id =  " + ticket.getTicketId();
        DatabaseConnection.getDatabaseConnectionInstance().update(query);
    }


}
