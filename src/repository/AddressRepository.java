package repository;

import connection.DatabaseConnection;
import domain.Address;
import general.DatabaseManipulation;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AddressRepository implements DatabaseManipulation<Address> {

    private static AddressRepository addressRepositoryInstance = null;

    private AddressRepository() {
    }

    public static AddressRepository getAddressRepositoryInstance() {

        if(addressRepositoryInstance == null){
            addressRepositoryInstance = new AddressRepository();
        }
        return addressRepositoryInstance;
    }

    @Override
    public List<Address> getData() {

        List<Address> addressList = new ArrayList<>();
        ResultSet resultSet = DatabaseConnection.getDatabaseConnectionInstance().makeQuery("select * from addresses");

        Address address;
        while ((address = parseElement(resultSet)) != null){
            addressList.add(address);
        }

        return  addressList;
    }

    @Override
    public Address get(int index){

        String query = "select * from addresses where address_id = " + index;
        return parseElement(DatabaseConnection.getDatabaseConnectionInstance().makeQuery(query));
    }

    public int getLastIndex(){

        try {
            ResultSet resultSet = DatabaseConnection.getDatabaseConnectionInstance().
                    makeQuery("select max(address_id) as max_id from addresses");

            if (resultSet.next()) {
                return Integer.parseInt(resultSet.getString("max_id"));
            }
            else{
                return -1;
            }
        } catch (SQLException ex) {
            System.out.println("Exception in parseOneElement in address Repository " + ex.getMessage());
            System.out.println("SQLException: " + ex.getMessage());
            return -1;
        }
    }

    @Override
    public Address parseElement(ResultSet resultSet){

        try {

            if(resultSet.next()) {

                int addressId = Integer.parseInt(resultSet.getString("address_id"));
                String country = resultSet.getString("country");
                String city = resultSet.getString("city");
                String street = resultSet.getString("street");
                String phoneNumber = resultSet.getString("phone_number");

                return new Address(addressId,country,city,street,phoneNumber);
            }
            else{
                return null;
            }
        }
        catch (SQLException ex) {
            System.out.println("Exception in parseOneElement in address Repository " + ex.getMessage());
            System.out.println("SQLException: " + ex.getMessage());
            return null;
        }
    }

    @Override
    public void insert(Address address) {

        String insertStatement = String.format("insert into addresses (country, city, street, phone_number) values (?, ?, ?, ?)");

        try (PreparedStatement preparedStatement = DatabaseConnection.getDatabaseConnectionInstance().getConnection().
                prepareStatement(insertStatement)) {

            preparedStatement.setString(1, address.getCountry());
            preparedStatement.setString(2, address.getCity());
            preparedStatement.setString(3, address.getStreet());
            preparedStatement.setString(4, address.getPhoneNumber());

            DatabaseConnection.getDatabaseConnectionInstance().update(preparedStatement);

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error in insert function in Address Repository");
        }

    }

    @Override
    public void update(Address address){

        String updateStatement = String.format("update addresses set country = ?, city = ?, street = ?, phone_number = ? " +
                "where address_id = ?");

        try (PreparedStatement preparedStatement = DatabaseConnection.getDatabaseConnectionInstance().getConnection().
                prepareStatement(updateStatement)) {

            preparedStatement.setString(1, address.getCountry());
            preparedStatement.setString(2, address.getCity());
            preparedStatement.setString(3, address.getStreet());
            preparedStatement.setString(4, address.getPhoneNumber());
            preparedStatement.setString(5, String.valueOf(address.getAddressId()));

            DatabaseConnection.getDatabaseConnectionInstance().update(preparedStatement);

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error in update function in Address Repository");
        }

    }

    @Override
    public void delete(Address address){
        String query = "delete from addresses where address_id =  " + address.getAddressId();
        DatabaseConnection.getDatabaseConnectionInstance().update(query);
    }
}
