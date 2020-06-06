package repository;

import connection.DatabaseConnection;
import domain.Client;
import general.DatabaseManipulation;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Operates different types of queries in the database.
 */
public class ClientRepository implements DatabaseManipulation<Client> {

    private static ClientRepository clientRepositoryInstance = null;

    private ClientRepository() {}

    public static ClientRepository getClientRepositoryInstance() {

        if(clientRepositoryInstance == null){
            clientRepositoryInstance = new ClientRepository();
        }
        return clientRepositoryInstance;
    }


    /**
     * Used when a new client account is created or a login is made to check
     * if the client exists in the database or not. The search is made by username, because
     * the username must be unique for every client.
     *
     * @return boolean
     */
    public boolean existClient(String username) {

        String query = "select * from clients where username = '" + username + "'";

        try {
            return DatabaseConnection.getDatabaseConnectionInstance().makeQuery(query).next();
        } catch (SQLException ex) {
            System.out.println("Exception in existClient by username in client Repository " + ex.getMessage());
            System.out.println("SQLException: " + ex.getMessage());
            return false;
        }

    }


    /**
     * Used to return a session client when a login is made in GUI interface.
     *
     * @param username client username
     * @param password client password
     * @return a session client
     */
    public Client login(String username, String password){

        String query = "select * from clients where username = '" + username + "' and password = '"
                + password + "'";

        return parseElement(DatabaseConnection.getDatabaseConnectionInstance().makeQuery(query));
    }


    /**
     * Make a list and return all clients from database if necessary.
     *
     * @return List
     */
    @Override
    public List<Client> getData() {

        List<Client> clientList = new ArrayList<>();
        ResultSet resultSet = DatabaseConnection.getDatabaseConnectionInstance().makeQuery("select * from clients");
        Client client;

        while ((client = parseElement(resultSet)) != null) {
            clientList.add(client);
        }

        return clientList;
    }


    /**
     *  Return a single client from the database when the selector is the primary key.
     *
     * @return client
     */
    @Override
    public Client get(int index){

        String query = "select * from clients where client_id = " + index;
        return parseElement(DatabaseConnection.getDatabaseConnectionInstance().makeQuery(query));
    }


    /**
     * Function used to create a new object from database data.
     * The function receives a row(resultSet) and splits this one into columns resulting
     * the data for a new client object.
     *
     * @param resultSet a row with data which will be split into columns
     * @return new client object
     */
    @Override
    public Client parseElement(ResultSet resultSet){

        try {

            if(resultSet.next()) {

                int clientId = Integer.parseInt(resultSet.getString("client_id"));
                String username = resultSet.getString("username");
                String password = resultSet.getString("password");
                String name = resultSet.getString("name");
                int age = Integer.parseInt(resultSet.getString("age"));
                String gender = resultSet.getString("gender");
                int addressId = Integer.parseInt(resultSet.getString("address_id"));

                return new Client(clientId, username, password, name, age, gender, addressId);
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


    /**
     *  Main function for inserting a new client into the database.
     *
     * @param client object which will be inserted into the database
     */
    @Override
    public void insert(Client client) {

        String insertStatement = String.format("insert into clients(username, password, name, age, gender, address_id)" +
                " values (?, ?, ?, ?, ?, ?)");

        try (PreparedStatement preparedStatement = DatabaseConnection.getDatabaseConnectionInstance().getConnection().
                prepareStatement(insertStatement)) {

            preparedStatement.setString(1, client.getUsername());
            preparedStatement.setString(2, client.getPassword());
            preparedStatement.setString(3, client.getName());
            preparedStatement.setString(4, String.valueOf(client.getAge()));
            preparedStatement.setString(5, client.getGender());
            preparedStatement.setString(6, String.valueOf(client.getAddressId()));

            DatabaseConnection.getDatabaseConnectionInstance().update(preparedStatement);

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error in insert function in Client Repository");
        }

    }


    /**
     * Main function for updating an existing client from the database.
     *
     * @param client object which will be updated
     */
    @Override
    public void update(Client client){

        String updateStatement = String.format("update clients set password = ?, name = ?, age = ?, gender = ? ," +
                "address_id = ? where client_id = ?");

        try (PreparedStatement preparedStatement = DatabaseConnection.getDatabaseConnectionInstance().getConnection().
                prepareStatement(updateStatement)) {

            preparedStatement.setString(1, client.getPassword());
            preparedStatement.setString(2, client.getName());
            preparedStatement.setString(3, String.valueOf(client.getAge()));
            preparedStatement.setString(4, client.getGender());
            preparedStatement.setString(5, String.valueOf(client.getAddressId()));
            preparedStatement.setString(6, String.valueOf(client.getClientId()));

            DatabaseConnection.getDatabaseConnectionInstance().update(preparedStatement);

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error in update function in Client Repository");
        }

    }


    /**
     * Main function for deleting an existing client from the database.
     *
     * @param client object which will be deleted from the database
     */
    @Override
    public void delete(Client client){
        String query = "delete from clients where client_id =  " + client.getClientId();
        DatabaseConnection.getDatabaseConnectionInstance().update(query);
    }

}
