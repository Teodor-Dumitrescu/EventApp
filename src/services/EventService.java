package services;

import general.BasicServices;
import general.Event;
import repository.EventRepository;
import java.util.*;

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

    private void loadAllEvents(){
        eventList = eventRepository.getData();
    }

    public <T> List<Event> getEventsByType(T event){
        //for example I can return all Sport events
        List<Event> aux = new ArrayList<>();

        for (Event eventAux: eventList) {
            if (event.getClass() == eventAux.getClass()) {
                aux.add(eventAux);
            }
        }

        return aux;
    }

    public List<Event> getOrganizerEvents(int organizerId){
        return eventRepository.getOrganizerEvents(organizerId);
    }

    @Override
    public List<Event> getAll() {
        return eventList;
    }

    @Override
    public Event get(int index, int type) {
        return eventRepository.get(index,type);
    }

    @Override
    public void add(Event event) {
        eventRepository.insert(event);
        loadAllEvents();
    }

    @Override
    public void update(Event event) {
        eventRepository.update(event);
        loadAllEvents();
    }

    @Override
    public void remove(Event event) {
        eventRepository.delete(event);
        loadAllEvents();
    }

    @Override
    public void show(){
        for(Event event: eventList){
            System.out.println(event.toString());
        }
    }

}
