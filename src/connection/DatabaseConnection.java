package connection;

import java.sql.*;

public class DatabaseConnection {

    private static DatabaseConnection databaseConnectionInstance = null;
    private static Connection connection;
    private static int connexionAttempts;
    private static final int maximConnexionAttempts = 5;

    private DatabaseConnection() {

        int delay = 5000;

        while(connexionAttempts <= maximConnexionAttempts) {

            connexionAttempts += 1;
            System.out.println();
            System.out.println();
            System.out.println("------------------------------");
            System.out.println("Database connection attempt: " + connexionAttempts);
            System.out.println("------------------------------");

            try {

                Class.forName("com.mysql.cj.jdbc.Driver");

                //set database credentials
                String databaseName = "test_pao";
                String databaseAddress = "localhost";
                String databasePort = "3306";
                String databasePassword = "";
                String databaseUsername = "root";
                String databaseUrl = "jdbc:mysql://" + databaseAddress + ":" + databasePort + "/" + databaseName;

                connection = DriverManager.getConnection(databaseUrl, databaseUsername, databasePassword);

                System.out.println("---------------------------------------------");
                System.out.println("SUCCESS [connexion with database was created]");
                System.out.println("---------------------------------------------");

                connexionAttempts = 0;

                break;

            } catch (SQLException | ClassNotFoundException ex) {
                System.out.println();
                System.out.println("================================================");
                System.out.println("ERROR: [connexion with database was not created]");
                System.out.println("------------------------------------------------");
                System.out.println("SQLException: " + ex.getMessage());

                if (connexionAttempts == maximConnexionAttempts) {
                    System.out.println();
                    System.out.println("=============================================================");
                    System.out.println("ERROR: [Database unreachable --> too many connexion attempts]");
                    System.out.println("=============================================================");
                    System.exit(-1);
                }

                System.out.println("------------------------------------------------");
                System.out.println("Will try again after " + delay + " milliseconds");
                System.out.println("================================================");

                try {
                    Thread.sleep(delay);
                }
                catch (InterruptedException exInterrupted){
                    System.out.println("ERROR: [Unable to sleep]");
                    System.out.println("Exception: " + exInterrupted.getMessage());
                    System.exit(-1);
                }
            }
        }
    }

    private static boolean isConnexion() {

        try {
            String query = String.format("select 1 from clients");
            Statement statement = connection.createStatement();
            statement.executeQuery(query);

        } catch (SQLException ex) {
            System.out.println("=================================================================");
            System.out.println("ERROR: [Database unreachable]");
            System.out.println("-----------------------------------------------------------------");
            System.out.println("Exception: " + ex.getMessage());
            System.out.println("=================================================================");
            return false;
        }

        return true;
    }

    public static DatabaseConnection getDatabaseConnectionInstance()  {

        if(databaseConnectionInstance == null || !isConnexion()){
            databaseConnectionInstance = new DatabaseConnection();
        }

        return databaseConnectionInstance;
    }


    public Connection getConnection() {
        return connection;
    }

    public ResultSet makeQuery(String query){

        ResultSet resultSet;

        try {

            Statement statement = getDatabaseConnectionInstance().getConnection().createStatement();
            resultSet = statement.executeQuery(query);

        } catch (SQLException ex) {
            System.out.println("ERROR [update query]: " + query + " was not executed");
            System.out.println("SQLException: " + ex.getMessage());
            return null;
        }

        return resultSet;
    }


    public void update(PreparedStatement statement){

        try {

            if(statement.executeUpdate() <= 0){
                System.out.println("ERROR [update query]: " + statement.toString() + " was not executed");
            }

        } catch (SQLException ex) {
            System.out.println("Query: " + statement.toString() + " not solved");
            System.out.println("SQLException: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    public void update(String query){

        try {

            Statement statement = getDatabaseConnectionInstance().getConnection().createStatement();

            if(statement.executeUpdate(query) <= 0){
                System.out.println("ERROR [update query]: " + query + " was not executed");
            }

        } catch (SQLException ex) {
            System.out.println("Query: " + query + " not solved");
            System.out.println("SQLException: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

}
