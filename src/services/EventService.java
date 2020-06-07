package services;

import general.BasicServices;
import general.Event;
import repository.EventRepository;
import java.util.*;

/**
 * Is an intermediate class, between repository(database) and view(GUI).
 */
public class EventService implements BasicServices<Event> {

    private static EventService eventServiceInstance = null;
    private EventRepository eventRepository;
    private List<Event> eventList;

    private EventService() {
        eventRepository = EventRepository.getEventRepositoryInstance();
        eventList = new ArrayList<>();

        loadAllEvents();
    }

    public static EventService getEventServiceInstance() {

        if(eventServiceInstance == null){
            eventServiceInstance = new EventService();
        }

        return eventServiceInstance;
    }


    /**
     * Make a list and return all events type from database if necessary.
     *
     */
    private void loadAllEvents(){
        eventList = eventRepository.getData();
    }


    /**
     * Used in filter zone in GUI. So, using this function can be selected only music events or only cultural events
     * or only sport events or other combinations by calling this function multiple times.
     *
     * @return list of events of same type
     */
    public <T> List<Event> getEventsByType(T event){

        List<Event> aux = new ArrayList<>();

        for (Event eventAux: eventList) {
            if (event.getClass() == eventAux.getClass()) {
                aux.add(eventAux);
            }
        }

        return aux;
    }


    /**
     * Make a list and return all events for one organizer if necessary.
     * This helps when a organizer is logged in because he must see only his events.
     *
     * @return List
     */
    public List<Event> getOrganizerEvents(int organizerId){
        return eventRepository.getOrganizerEvents(organizerId);
    }


    /**
     * Get all events from database if necessary.
     *
     * @return List
     */
    @Override
    public List<Event> getAll() {
        return eventList;
    }


    /**
     * Get a specific event from database identified in a unique mode by id and table type.
     *
     * @param index primary key
     * @param type table for events (1 for music, 2 for sport , 3 for cultural)
     * @return event
     */
    @Override
    public Event get(int index, int type) {
        return eventRepository.get(index,type);
    }


    /**
     * Main function for inserting new event into database.
     *
     * @param event object which will be inserted into database
     */
    @Override
    public void add(Event event) {
        eventRepository.insert(event);
        loadAllEvents();
    }


    /**
     * Main function for updating existing event from database.
     *
     * @param event object which will be updated
     */
    @Override
    public void update(Event event) {
        eventRepository.update(event);
        loadAllEvents();
    }


    /**
     *  Main function for deleting existing event from database.
     *
     * @param event object which will be deleted from database
     */
    @Override
    public void remove(Event event) {
        eventRepository.delete(event);
        loadAllEvents();
    }


    /**
     * Debugging purpose.
     */
    @Override
    public void show(){
        for(Event event: eventList){
            System.out.println(event.toString());
        }
    }

}
