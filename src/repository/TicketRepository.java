package repository;

import connection.DatabaseConnection;
import general.DatabaseManipulation;
import domain.Ticket;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TicketRepository implements DatabaseManipulation<Ticket> {

    private static TicketRepository ticketRepositoryInstance = null;

    private TicketRepository() {

    }

    public static TicketRepository getTicketRepositoryInstance() {

        if(ticketRepositoryInstance == null){
            ticketRepositoryInstance = new TicketRepository();
        }
        return ticketRepositoryInstance;
    }

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

    public List<Ticket> getOrganizerSoldTickets(int organizerId){

        String query = "select * from tickets where organizer_id = " + organizerId;

        return getSpecificData(query);
    }

    public List<Ticket> getClientTickets(int clientId){

        String query = "select * from tickets where client_id = " + clientId;

        return getSpecificData(query);
    }


    public List<Ticket> getEventTickets(int eventId, int eventType) {

        String query = "select * from tickets where event_id = " + eventId + " and event_type = " + eventType;

        return getSpecificData(query);
    }

    private List<Ticket> getSpecificData(String query){

        List<Ticket> ticketList = new ArrayList<>();
        ResultSet resultSet = DatabaseConnection.getDatabaseConnectionInstance().makeQuery(query);
        Ticket ticket;

        while ((ticket = parseElement(resultSet)) != null) {
            ticketList.add(ticket);
        }

        return ticketList;
    }

    @Override
    public List<Ticket> getData() {
        return getSpecificData("select * from tickets");
    }

    public Ticket get(String ticketIdentifier){
        String query = "select * from tickets where ticket_identifier = '" + ticketIdentifier + "'";
        System.out.println(query);
        return parseElement(DatabaseConnection.getDatabaseConnectionInstance().makeQuery(query));
    }

    @Override
    public Ticket get(int index){

        String query = "select * from tickets where ticket_id = " + index;
        return parseElement(DatabaseConnection.getDatabaseConnectionInstance().makeQuery(query));
    }

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

    @Override
    public void delete(Ticket ticket){
        //delete ticket value
        String query = "delete from tickets where ticket_id =  " + ticket.getTicketId();
        DatabaseConnection.getDatabaseConnectionInstance().update(query);
    }


}
