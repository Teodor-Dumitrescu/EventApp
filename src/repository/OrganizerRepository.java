package repository;

import connection.DatabaseConnection;
import domain.Organizer;
import general.DatabaseManipulation;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrganizerRepository implements DatabaseManipulation<Organizer> {

    private static OrganizerRepository organizerRepositoryInstance = null;

    private OrganizerRepository() {

    }

    public static OrganizerRepository getOrganizerRepositoryInstance() {

        if(organizerRepositoryInstance == null){
            organizerRepositoryInstance = new OrganizerRepository();
        }
        return organizerRepositoryInstance;
    }

    public boolean existOrganizer(String username) {

        String query = "select * from organizers where username = '" + username + "'";

        try {
            return DatabaseConnection.getDatabaseConnectionInstance().makeQuery(query).next();
        } catch (SQLException ex) {
            System.out.println("Exception in existClient in client Repository " + ex.getMessage());
            System.out.println("SQLException: " + ex.getMessage());
            return false;
        }

    }


    public Organizer login(String username, String password){

        String query = "select * from organizers where username = '" + username + "' and password = '"
                + password + "'";

        return parseElement(DatabaseConnection.getDatabaseConnectionInstance().makeQuery(query));
    }

    @Override
    public List<Organizer> getData() {

        List<Organizer> organizerList = new ArrayList<>();
        ResultSet resultSet = DatabaseConnection.getDatabaseConnectionInstance().makeQuery("select * from organizers");

        Organizer organizer;
        while((organizer = parseElement(resultSet)) != null){
            organizerList.add(organizer);
        }

        return organizerList;
    }

    @Override
    public Organizer get(int index){

        String query = "select * from organizers where organizer_id = " + index;
        return parseElement(DatabaseConnection.getDatabaseConnectionInstance().makeQuery(query));
    }

    @Override
    public Organizer parseElement(ResultSet resultSet){

        try {

            if(resultSet.next()) {

                int organizerId = Integer.parseInt(resultSet.getString("organizer_id"));
                String username = resultSet.getString("username");
                String password = resultSet.getString("password");
                String name = resultSet.getString("name");
                int addressId = Integer.parseInt(resultSet.getString("address_id"));

                return new Organizer(organizerId, username, password, name, addressId);
            }
            else{
                return null;
            }
        }
        catch (SQLException ex) {
            System.out.println("Exception in parseOneElement in client Repository " + ex.getMessage());
            System.out.println("SQLException: " + ex.getMessage());
            return null;
        }
    }

    @Override
    public void insert(Organizer organizer) {

        String insertStatement = String.format("insert into organizers (username, password, name, address_id) " +
                " values (?, ?, ?, ?)");

        try (PreparedStatement preparedStatement = DatabaseConnection.getDatabaseConnectionInstance().getConnection().
                prepareStatement(insertStatement)) {

            preparedStatement.setString(1, organizer.getUsername());
            preparedStatement.setString(2, organizer.getPassword());
            preparedStatement.setString(3, organizer.getName());
            preparedStatement.setString(4, String.valueOf(organizer.getAddressId()));

            DatabaseConnection.getDatabaseConnectionInstance().update(preparedStatement);

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error in insert function in Organizer Repository");
        }

    }

    @Override
    public void update(Organizer organizer){

        String updateStatement = String.format("update organizers set password = ?, name = ?, address_id = ? " +
                "where organizer_id = ?");

        try (PreparedStatement preparedStatement = DatabaseConnection.getDatabaseConnectionInstance().getConnection().
                prepareStatement(updateStatement)) {

            preparedStatement.setString(1, organizer.getPassword());
            preparedStatement.setString(2, organizer.getName());
            preparedStatement.setString(3, String.valueOf(organizer.getAddressId()));
            preparedStatement.setString(4, String.valueOf(organizer.getOrganizerId()));

            DatabaseConnection.getDatabaseConnectionInstance().update(preparedStatement);

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error in update function in Organizer Repository");
        }

    }

    @Override
    public void delete(Organizer organizer){
        //delete organizer value
        String query = "delete from organizers where organizer_id =  " + organizer.getOrganizerId();
        DatabaseConnection.getDatabaseConnectionInstance().update(query);
    }

}
