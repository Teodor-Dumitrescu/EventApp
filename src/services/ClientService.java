package services;

import domain.Client;
import general.BasicServices;
import repository.ClientRepository;
import java.util.List;

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

    public boolean exist(String username){
        return clientRepository.existClient(username);
    }

    public Client login(String username, String password){
        return clientRepository.login(username,password);
    }

    @Override
    public List<Client> getAll() {
        return clientRepository.getData();
    }

    @Override
    public Client get(int index) {
        return clientRepository.get(index);
    }

    @Override
    public void add(Client client) {
        clientRepository.insert(client);
    }

    @Override
    public void update(Client client) {
        clientRepository.update(client);
    }

    @Override
    public void remove(Client client) {
        clientRepository.delete(client);
    }

    @Override
    public void show(){

        List<Client> clientList = clientRepository.getData();
        for(Client client: clientList){
            System.out.println(client.toString());
        }
    }

}


