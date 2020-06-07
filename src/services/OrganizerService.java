package services;

import general.BasicServices;
import domain.Organizer;
import repository.OrganizerRepository;
import java.util.List;


/**
 * Is an intermediate class, between repository(database) and view(GUI).
 */
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


    /**
     * Used when a new organizer account is created or a login is made to check
     * if the organizer exists in database or not. The search is made by username, because
     * username must be unique for every organizer.
     *
     * @return boolean
     */
    public boolean exist(String username){
        return organizerRepository.existOrganizer(username);
    }


    /**
     * Used to return a session organizer when a login is made in GUI interface.
     *
     * @param username organizer username
     * @param password organizer password
     * @return a session organizer
     */
    public Organizer login(String username, String password){
        return organizerRepository.login(username,password);
    }


    /**
     * Make a list and return all organizers from database if necessary.
     *
     * @return List
     */
    @Override
    public List<Organizer> getAll() {
        return organizerRepository.getData();
    }


    /**
     * Return a single organizer from database when the selector is primary key.
     *
     * @return organizer
     */
    @Override
    public Organizer get(int index) {
        return organizerRepository.get(index);
    }


    /**
     * Main function for inserting new organizer into database.
     *
     * @param organizer object which will be inserted into database
     */
    @Override
    public void add(Organizer organizer) {
        organizerRepository.insert(organizer);
    }


    /**
     * Main function for updating existing organizer from database.
     *
     * @param organizer object which will be updated
     */
    @Override
    public void update(Organizer organizer) {
        organizerRepository.update(organizer);
    }


    /**
     * Main function for deleting existing organizer from database.
     *
     * @param organizer object which will be deleted from database
     */
    @Override
    public void remove(Organizer organizer) {
        organizerRepository.delete(organizer);
    }


    /**
     * Debugging purpose.
     */
    @Override
    public void show(){

        List<Organizer> organizerList = organizerRepository.getData();

        for(Organizer organizer: organizerList){
            System.out.println(organizer.toString());
        }
    }

}
