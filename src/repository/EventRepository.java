package repository;

import connection.DatabaseConnection;
import domain.Cultural;
import domain.Location;
import domain.Music;
import domain.Sport;
import general.DatabaseManipulation;
import general.Event;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EventRepository implements DatabaseManipulation<Event> {

    private static EventRepository eventRepositoryInstance = null;
    private String[] EVENTS_TABLES = {"music_events","sport_events","cultural_events"};

    private EventRepository(){
    }

    public static EventRepository getEventRepositoryInstance() {

        if(eventRepositoryInstance == null){
            eventRepositoryInstance = new EventRepository();
        }
        return eventRepositoryInstance;
    }

    public List<Event> getOrganizerEvents(int organizerId) {

        List<Event> eventList = new ArrayList<>();
        ResultSet resultSet;
        Event event;

        for (int i = 0; i < 3; i++){

            if(i == 0) {
                resultSet = DatabaseConnection.getDatabaseConnectionInstance().makeQuery("select * from " + EVENTS_TABLES[i] +
                        " where organizer_id = " + organizerId + " and event_id != 1"); //not select admin event
            }
            else{
                resultSet = DatabaseConnection.getDatabaseConnectionInstance().makeQuery("select * from " + EVENTS_TABLES[i] +
                        " where organizer_id = " + organizerId);
            }

            while((event = parseElement(resultSet)) != null){
                eventList.add(event);
            }
        }

        return eventList;
    }

    @Override
    public List<Event> getData() {

        List<Event> eventList = new ArrayList<>();

        for(int i = 1; i <= 3; i++){
            List<Event> tmpEvents = getData(i);
            eventList.addAll(tmpEvents);
        }

        return eventList;
    }

    @Override
    public List<Event> getData(int type) {

        List<Event> eventList = new ArrayList<>();
        ResultSet resultSet;

        switch (type){
            case 1:
                resultSet = DatabaseConnection.getDatabaseConnectionInstance().makeQuery("select * from " + EVENTS_TABLES[0] +
                        " where event_id != 1"); //not select admin event
                break;
            case 2:
                resultSet = DatabaseConnection.getDatabaseConnectionInstance().makeQuery("select * from " + EVENTS_TABLES[1]);
                break;
            case 3:
                resultSet = DatabaseConnection.getDatabaseConnectionInstance().makeQuery("select * from " + EVENTS_TABLES[2]);
                break;
            default:
                System.out.println("Wrong type [" + type + "] in getData function in event repository");
                return null;
        }

        Event event;
        while((event = parseElement(resultSet)) != null){
            eventList.add(event);
        }

        return eventList;
    }

    @Override
    public Event get(int index, int type){

        if(type == 1){ //search in music events table
            String query = "select * from " + EVENTS_TABLES[0] + " where event_id = " + index;
            return parseElement(DatabaseConnection.getDatabaseConnectionInstance().makeQuery(query));
        }

        if(type == 2){ //search in sport events table
            String query = "select * from " +EVENTS_TABLES[1] + " where event_id = " + index;
            return parseElement(DatabaseConnection.getDatabaseConnectionInstance().makeQuery(query));
        }

        if(type == 3){ //search in cultural events table
            String query = "select * from " + EVENTS_TABLES[2] + " where event_id = " + index;
            return parseElement(DatabaseConnection.getDatabaseConnectionInstance().makeQuery(query));
        }

        System.out.println("Wrong type [" + type + "] in get function in event repository");
        return null;
    }

    @Override
    public Event parseElement(ResultSet resultSet){

        try {

            if(resultSet.next()) {

                //Extract event general data
                int eventId = Integer.parseInt(resultSet.getString("event_id"));
                int organizerId = Integer.parseInt(resultSet.getString("organizer_id"));
                int addressId = Integer.parseInt(resultSet.getString("address_id"));
                String name = resultSet.getString("name");
                float standardTicketPrice = Float.parseFloat(resultSet.getString("standard_price"));
                String date = resultSet.getString("date");
                int eventType = Integer.parseInt(resultSet.getString("event_type"));
                int availableTicketsNumber = Integer.parseInt(resultSet.getString("available_tickets_number"));

                //extract event specific data
                if(eventType == 1){ //music event
                    String musicGenre = resultSet.getString("music_genre");
                    String formationName = resultSet.getString("formation_name");

                    return new Music(eventId,organizerId,eventType,name,standardTicketPrice,availableTicketsNumber,
                            new Location(date,addressId),musicGenre,formationName);
                }

                if(eventType == 2){ //sport event
                    String home = resultSet.getString("home");
                    String guest = resultSet.getString("guest");

                    return new Sport(eventId,organizerId,eventType,name,standardTicketPrice,availableTicketsNumber,
                            new Location(date,addressId),home,guest);
                }

                if(eventType == 3){ //cultural event
                    String type = resultSet.getString("type");
                    String guests = resultSet.getString("guests");

                    return new Cultural(eventId,organizerId,eventType,name,standardTicketPrice,availableTicketsNumber,
                            new Location(date,addressId),type,guests);
                }

                System.out.println("Error at finding event type in parse element event repository");
                return null;
            }
            return null;
        }
        catch (SQLException ex) {
            System.out.println("Exception in parseOneElement in event Repository " + ex.getMessage());
            System.out.println("SQLException: " + ex.getMessage());
            return null;
        }
    }

    private void modifyBasicEvent(Event event, PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setString(1, String.valueOf(event.getOrganizerId()));
        preparedStatement.setString(2, String.valueOf(event.getLocation().getAddressId()));
        preparedStatement.setString(3, String.valueOf(event.getEventType()));
        preparedStatement.setString(4, event.getName());
        preparedStatement.setString(5, String.valueOf(event.getStandardTicketPrice()));
        preparedStatement.setString(6, event.getLocation().getDate());
        preparedStatement.setString(7, String.valueOf(event.getAvailableTicketsNumber()));
    }

    @Override
    public void insert(Event event){

            String basicEventInsertQuery = " (organizer_id, address_id, event_type, name, standard_price, date, " +
                    "available_tickets_number";

            if (event.getEventType() == 1) { //insert in music events table

                String insertStatement = String.format("insert into " + EVENTS_TABLES[0] + basicEventInsertQuery +
                        ", music_genre, formation_name) values (?, ?, ?, ?, ?, ?, ?, ?, ?)");

                try (PreparedStatement preparedStatement = DatabaseConnection.getDatabaseConnectionInstance().getConnection().
                        prepareStatement(insertStatement)) {

                    modifyBasicEvent(event, preparedStatement);
                    preparedStatement.setString(8, ((Music) event).getMusicGenre());
                    preparedStatement.setString(9, ((Music) event).getFormationName());

                    DatabaseConnection.getDatabaseConnectionInstance().update(preparedStatement);

                    return;

                } catch (SQLException e) {
                    e.printStackTrace();
                    System.out.println("Error in insert function in Event Repository in event type == 1");
                    return;
                }
            }

            if (event.getEventType() == 2) { //insert in sport events table

                String insertStatement = String.format("insert into " + EVENTS_TABLES[1] + basicEventInsertQuery +
                        ", home, guest) values (?, ?, ?, ?, ?, ?, ?, ?, ?)");

                try (PreparedStatement preparedStatement = DatabaseConnection.getDatabaseConnectionInstance().getConnection().
                        prepareStatement(insertStatement)) {

                    modifyBasicEvent(event, preparedStatement);
                    preparedStatement.setString(8, ((Sport) event).getHome());
                    preparedStatement.setString(9, ((Sport) event).getGuest());

                    DatabaseConnection.getDatabaseConnectionInstance().update(preparedStatement);

                    return;

                } catch (SQLException e) {
                    e.printStackTrace();
                    System.out.println("Error in insert function in Event Repository in event type == 2");
                    return;
                }

            }

            if (event.getEventType() == 3) { //insert in cultural events table

                String insertStatement = String.format("insert into " + EVENTS_TABLES[2] + basicEventInsertQuery +
                        ", type, guests) values (?, ?, ?, ?, ?, ?, ?, ?, ?)");

                try (PreparedStatement preparedStatement = DatabaseConnection.getDatabaseConnectionInstance().getConnection().
                        prepareStatement(insertStatement)) {

                    modifyBasicEvent(event, preparedStatement);
                    preparedStatement.setString(8, ((Cultural) event).getType());
                    preparedStatement.setString(9, ((Cultural) event).getGuests());

                    DatabaseConnection.getDatabaseConnectionInstance().update(preparedStatement);

                    return;

                } catch (SQLException e) {
                    e.printStackTrace();
                    System.out.println("Error in insert function in Event Repository in event type == 3");
                    return;
                }
            }

            System.out.println("Wrong type [" + event.getEventType() + "] in insert function in event repository");

    }


    private String getBasicUpdateQuery(Event event){
        return " set " + "organizer_id = " + event.getOrganizerId() + ",address_id = " + event.getLocation().getAddressId() +
                ",event_type = " + event.getEventType() + ",name = '" + event.getName() + "',standard_price = "
                + event.getStandardTicketPrice() + ", date = '"+ event.getLocation().getDate() + "', available_tickets_number = " +
                event.getAvailableTicketsNumber();
    }

    @Override
    public void update(Event event){

        String basicEventUpdateQuery = String.format(" set organizer_id = ?, address_id = ?, event_type = ?, name = ?, standard_price = ?," +
                "date = ?, available_tickets_number = ?");

        if(event.getEventType() == 1){ //update in music events table

            String updateStatement = String.format("update " + EVENTS_TABLES[0] + basicEventUpdateQuery +
                    ", music_genre = ?, formation_name = ? where event_id = ?");

            try (PreparedStatement preparedStatement = DatabaseConnection.getDatabaseConnectionInstance().getConnection().
                    prepareStatement(updateStatement)) {

                modifyBasicEvent(event,preparedStatement);
                preparedStatement.setString(8, ((Music) event).getMusicGenre());
                preparedStatement.setString(9, ((Music) event).getFormationName());
                preparedStatement.setString(10, String.valueOf(event.getEventId()));

                DatabaseConnection.getDatabaseConnectionInstance().update(preparedStatement);
                return;

            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Error in update function in Event Repository where type == 1");
                return;
            }
        }

        if(event.getEventType() == 2){ //update in sport events table

            String updateStatement = String.format("update " + EVENTS_TABLES[1] + basicEventUpdateQuery +
                    ", home = ?, guest = ? where event_id = ?");

            try (PreparedStatement preparedStatement = DatabaseConnection.getDatabaseConnectionInstance().getConnection().
                    prepareStatement(updateStatement)) {

                modifyBasicEvent(event,preparedStatement);
                preparedStatement.setString(8, ((Sport)event).getHome());
                preparedStatement.setString(9, ((Sport)event).getGuest());
                preparedStatement.setString(10, String.valueOf(event.getEventId()));

                DatabaseConnection.getDatabaseConnectionInstance().update(preparedStatement);
                return;

            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Error in update function in Event Repository where type == 2");
                return;
            }
        }

        if(event.getEventType() == 3){ //update in cultural events table

            String updateStatement = String.format("update " + EVENTS_TABLES[1] + basicEventUpdateQuery +
                    ", type = ?, guests = ? where event_id = ?");

            try (PreparedStatement preparedStatement = DatabaseConnection.getDatabaseConnectionInstance().getConnection().
                    prepareStatement(updateStatement)) {

                modifyBasicEvent(event,preparedStatement);
                preparedStatement.setString(8, ((Cultural)event).getType());
                preparedStatement.setString(9, ((Cultural)event).getGuests());
                preparedStatement.setString(10, String.valueOf(event.getEventId()));

                DatabaseConnection.getDatabaseConnectionInstance().update(preparedStatement);
                return;

            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Error in update function in Event Repository where type == 3");
                return;
            }
        }

        System.out.println("Wrong type [" + event.getEventType() + "] in update function in event repository");

    }

    @Override
    public void delete(Event event){
        //delete event value

        if(event.getEventType() == 1){ //delete in music events table
            String query = "delete from " + EVENTS_TABLES[0] + " where event_id =  " + event.getEventId();

            DatabaseConnection.getDatabaseConnectionInstance().update(query);
            return;
        }

        if(event.getEventType() == 2){ //delete in music events table
            String query = "delete from " + EVENTS_TABLES[1] + " where event_id =  " + event.getEventId();

            DatabaseConnection.getDatabaseConnectionInstance().update(query);
            return;
        }

        if(event.getEventType() == 3){ //delete in music events table
            String query = "delete from " + EVENTS_TABLES[2] + " where event_id =  " + event.getEventId();

            DatabaseConnection.getDatabaseConnectionInstance().update(query);
            return;
        }

        System.out.println("Wrong type [" + event.getEventType() + "] in delete function in event repository");
    }

}
