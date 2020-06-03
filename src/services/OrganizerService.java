package services;

import general.BasicServices;
import domain.Organizer;
import repository.OrganizerRepository;
import java.util.List;

public class OrganizerService implements BasicServices<Organizer> {

    private static OrganizerService organizerServiceInstance = null;
    private OrganizerRepository organizerRepository;

    private OrganizerService() {
        organizerRepository = OrganizerRepository.getOrganizerRepositoryInstance();
    }

    public static OrganizerService getOrganizerServiceInstance() {

        if(organizerServiceInstance == null){
            organizerServiceInstance = new OrganizerService();
        }

        return organizerServiceInstance;
    }

    public boolean exist(String username){
        return organizerRepository.existOrganizer(username);
    }

    public Organizer login(String username, String password){
        return organizerRepository.login(username,password);
    }

    @Override
    public List<Organizer> getAll() {
        return organizerRepository.getData();
    }

    @Override
    public Organizer get(int index) {
        return organizerRepository.get(index);
    }

    @Override
    public void add(Organizer organizer) {
        organizerRepository.insert(organizer);
    }

    @Override
    public void update(Organizer organizer) {
        organizerRepository.update(organizer);
    }

    @Override
    public void remove(Organizer organizer) {
        organizerRepository.delete(organizer);
    }

    @Override
    public void show(){

        List<Organizer> organizerList = organizerRepository.getData();

        for(Organizer organizer: organizerList){
            System.out.println(organizer.toString());
        }
    }

}
