package services;

import domain.Client;
import general.BasicServices;
import repository.ClientRepository;
import java.util.List;


/**
 * Is an intermediate class, between repository(database) and view(GUI).
 */
public class ClientService implements BasicServices<Client> {

    private static ClientService clientServiceInstance = null;
    private ClientRepository clientRepository;

    private ClientService() {
        clientRepository = ClientRepository.getClientRepositoryInstance();
    }

    public static ClientService getClientServiceInstance() {

        if(clientServiceInstance == null){
            clientServiceInstance = new ClientService();
        }

        return clientServiceInstance;
    }


    /**
     * Used when a new client account is created or a login is made to check
     * if the client exists in database or not. The search is made by username, because
     * username must be unique for every client.
     *
     * @return boolean
     */
    public boolean exist(String username){
        return clientRepository.existClient(username);
    }


    /**
     * Used to return a session client when a login is made in GUI interface.
     *
     * @param username client username
     * @param password client password
     * @return a session client
     */
    public Client login(String username, String password){
        return clientRepository.login(username,password);
    }


    /**
     * Make a list and return all clients from database if necessary.
     *
     * @return List
     */
    @Override
    public List<Client> getAll() {
        return clientRepository.getData();
    }


    /**
     *  Return a single client from database when the selector is primary key.
     *
     * @return client
     */
    @Override
    public Client get(int index) {
        return clientRepository.get(index);
    }


    /**
     *  Main function for inserting new client into database.
     *
     * @param client object which will be inserted into database
     */
    @Override
    public void add(Client client) {
        clientRepository.insert(client);
    }


    /**
     * Main function for updating existing client from database.
     *
     * @param client object which will be updated
     */
    @Override
    public void update(Client client) {
        clientRepository.update(client);
    }


    /**
     * Main function for deleting existing client from database.
     *
     * @param client object which will be deleted from database
     */
    @Override
    public void remove(Client client) {
        clientRepository.delete(client);
    }


    /**
     * Debugging purpose.
     */
    @Override
    public void show(){

        List<Client> clientList = clientRepository.getData();
        for(Client client: clientList){
            System.out.println(client.toString());
        }
    }

}


