package repository;

import connection.DatabaseConnection;
import domain.Client;
import general.DatabaseManipulation;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClientRepository implements DatabaseManipulation<Client> {

    private static ClientRepository clientRepositoryInstance = null;

    private ClientRepository() {
    }

    public static ClientRepository getClientRepositoryInstance() {

        if(clientRepositoryInstance == null){
            clientRepositoryInstance = new ClientRepository();
        }
        return clientRepositoryInstance;
    }

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

    public boolean existClient(int clientId) {

        String query = "select * from clients where client_id = " + clientId;

        try {
            return DatabaseConnection.getDatabaseConnectionInstance().makeQuery(query).next();
        } catch (SQLException ex) {
            System.out.println("Exception in existClient by id in client Repository " + ex.getMessage());
            System.out.println("SQLException: " + ex.getMessage());
            return false;
        }

    }


    public Client login(String username, String password){

        String query = "select * from clients where username = '" + username + "' and password = '"
                + password + "'";

        return parseElement(DatabaseConnection.getDatabaseConnectionInstance().makeQuery(query));
    }

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

    @Override
    public Client get(int index){

        String query = "select * from clients where client_id = " + index;
        return parseElement(DatabaseConnection.getDatabaseConnectionInstance().makeQuery(query));
    }

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

    @Override
    public void delete(Client client){
        String query = "delete from clients where client_id =  " + client.getClientId();
        DatabaseConnection.getDatabaseConnectionInstance().update(query);
    }

}
