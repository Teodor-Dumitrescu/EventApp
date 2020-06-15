package connection;

import java.sql.*;

public class DatabaseConnection {

    private static DatabaseConnection databaseConnectionInstance = null;
    private static Connection connection;
    private static int connexionAttempts;
    private static final int maximConnexionAttempts = 5;

    /**
     * Main function where the connection is made.
     * If connection is not made after a fixed number of attempts the program will exit.
     * Every time when the connection is successful, the connection attempts reset to 0 to be prepared
     * for the next time when there is a problem with the connection.
     */
    private DatabaseConnection() {

        //try for a limited time to make or remake the connection
        while(connexionAttempts <= maximConnexionAttempts) {

            connexionAttempts += 1;
            int delay = 5000;

            System.out.println();
            System.out.println();
            System.out.println("------------------------------");
            System.out.println("Database connection attempt: " + connexionAttempts);
            System.out.println("------------------------------");

            try {

                Class.forName("com.mysql.cj.jdbc.Driver");
		
				/*
                //remote testing 1
                String databaseName = "KgoRso6laM";
                String databaseAddress = "remotemysql.com";
                String databasePort = "3306";
                String databaseUsername = "KgoRso6laM";
                String databasePassword = "l8lhFBAtWv";
                */

                
                //remote testing 2
                String databaseName = "sql7347239";
                String databaseAddress = "sql7.freesqldatabase.com";
                String databasePort = "3306";
                String databaseUsername = "sql7347239";
                String databasePassword = "ASDmrLWXqp";

                 /*

                //set database credentials
                String databaseName = "database_mds";
                String databaseAddress = "localhost";
                String databasePort = "3306";
                String databasePassword = "";
                String databaseUsername = "root";

                */
                String databaseUrl = "jdbc:mysql://" + databaseAddress + ":" + databasePort + "/" + databaseName;

                connection = DriverManager.getConnection(databaseUrl, databaseUsername, databasePassword);

                System.out.println("---------------------------------------------");
                System.out.println("SUCCESS [connection with database was created]");
                System.out.println("---------------------------------------------");

                connexionAttempts = 0;

                break;

            } catch (SQLException | ClassNotFoundException ex) {
                System.out.println();
                System.out.println("================================================");
                System.out.println("ERROR: [connection with database was not created]");
                System.out.println("------------------------------------------------");
                System.out.println("SQLException: " + ex.getMessage());

                if (connexionAttempts >= maximConnexionAttempts) {
                    System.out.println();
                    System.out.println("=============================================================");
                    System.out.println("ERROR: [Database unreachable --> too many connection attempts]");
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


    /**
     * Check if the database is reachable by sending a test query to be executed.
     * If the result of the query is an error, it means that the database is unreachable.
     *
     * @return boolean
     */
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


    /**
     * The database should always be reachable, beacause the program always gets data from the database
     * (to have recent data).
     * So every time an update, add or delete operation is made, first it must be checked that a valid connection exists.
     * If the connection does not exist a valid connection must be made.
     *
     * @return instance for connection
     */
    public static DatabaseConnection getDatabaseConnectionInstance()  {

        if(databaseConnectionInstance == null || !isConnexion()){
            databaseConnectionInstance = new DatabaseConnection();
        }

        return databaseConnectionInstance;
    }


    public Connection getConnection() {
        return connection;
    }


    /**
     * This function is a basic function for making GET requests in database. All repositories will call
     * this function to get their results.
     * By GET requests we understand a request which will return some data.
     *
     * @param query - is the query which will be executed by database
     * @return result from query
     */
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


    /**
     * This function is a basic function for making UPDATE requests in the database. All repositories will call
     * this function to make their updates.
     * By UPDATE requests we understand a request like add, update or delete something from the database.
     *
     * @param statement - is the statement which is made in every repository. This is the query which will be executed
     *                  by the database
     * @return void
     */
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


    /**
     * This function is a basic function for making UPDATE requests in the database. All repositories will call
     * this function to make their updates.
     * By UPDATE requests we understand a request like add, update or delete something from the database.
     *
     * @param query - This is the query which will be executed by the database
     * @return void
     */
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
