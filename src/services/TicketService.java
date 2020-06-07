package services;

import general.BasicServices;
import domain.Ticket;
import repository.TicketRepository;

import java.util.*;


/**
 * Is an intermediate class, between repository(database) and view(GUI).
 */
public class TicketService implements BasicServices<Ticket> {

    private static TicketService ticketServiceInstance = null;
    private TicketRepository ticketRepository;

    private TicketService() {
        ticketRepository = TicketRepository.getTicketRepositoryInstance();
    }

    public static TicketService getTicketServiceInstance() {

        if(ticketServiceInstance == null){
            ticketServiceInstance = new TicketService();
        }

        return ticketServiceInstance;
    }


    /**
     * Used to check if a ticket exists using the ticket identifier.
     * Because ticketIdentifier is random generated and because this must be unique,
     * every time when a ticket is created is generated a unique ticket identifier by checking that a ticket with this
     * ticketIdentifier does not exist in database.
     *
     * @return boolean
     */
    public boolean existTicket(String ticketIdentifier){
        return ticketRepository.existTicket(ticketIdentifier);
    }


    /**
     * Used to see what tickets the client session(identified by client id) bought.
     * Is used in GUI when a client click on view ticket button.
     *
     * @return list of tickets
     */
    public List<Ticket> getClientTickets(int clientId){
        return ticketRepository.getClientTickets(clientId);
    }


    /**
     * Used to see what tickets the organizer session(identified by organizer id) sold.
     * Is used in GUI when a organizer click on view sold ticket button.
     *
     * @return list of tickets
     */
    public List<Ticket> getOrganizerSoldTickets(int organizerId){
        return ticketRepository.getOrganizerSoldTickets(organizerId);
    }


    /**
     * Make a list and return all tickets from database if necessary.
     *
     * @return List
     */
    @Override
    public List<Ticket> getAll() {
        return ticketRepository.getData();
    }


    /**
     * Return a single ticket from database when the selector is ticket identifier.
     *
     * @return ticket
     */
    public Ticket get(String ticketIdentifier) {
        return ticketRepository.get(ticketIdentifier);
    }


    /**
     * Return a single ticket from database when the selector is primary key.
     *
     * @return ticket
     */
    @Override
    public Ticket get(int index) {
        return ticketRepository.get(index);
    }


    /**
     * Main function for inserting new ticket into database.
     *
     * @param ticket object which will be inserted into database
     */
    @Override
    public void add(Ticket ticket) {
        ticketRepository.insert(ticket);
    }


    /**
     * Main function for updating existing ticket from database.
     *
     * @param ticket object which will be updated
     */
    @Override
    public void update(Ticket ticket) {
        ticketRepository.update(ticket);
    }


    /**
     * Main function for deleting existing ticket from database.
     *
     * @param ticket object which will be deleted from database
     */
    @Override
    public void remove(Ticket ticket) {
        ticketRepository.delete(ticket);
    }


    /**
     * Debugging purpose.
     */
    @Override
    public void show(){

        List<Ticket> ticketList = ticketRepository.getData();
        for(Ticket ticket: ticketList){
            System.out.println(ticket.toString());
        }
    }

}
