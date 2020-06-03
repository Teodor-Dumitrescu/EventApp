package services;

import general.BasicServices;
import domain.Ticket;
import repository.TicketRepository;

import java.util.*;

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

    public boolean existTicket(String ticketIdentifier){
        return ticketRepository.existTicket(ticketIdentifier);
    }

    public List<Ticket> getClientTickets(int clientId){
        return ticketRepository.getClientTickets(clientId);
    }

    public List<Ticket> getOrganizerSoldTickets(int organizerId){
        return ticketRepository.getOrganizerSoldTickets(organizerId);
    }

    @Override
    public List<Ticket> getAll() {
        return ticketRepository.getData();
    }

    public Ticket get(String ticketIdentifier) {
        return ticketRepository.get(ticketIdentifier);
    }

    @Override
    public Ticket get(int index) {
        return ticketRepository.get(index);
    }

    @Override
    public void add(Ticket ticket) {
        ticketRepository.insert(ticket);
    }

    @Override
    public void update(Ticket ticket) {
        ticketRepository.update(ticket);
    }

    @Override
    public void remove(Ticket ticket) {
        ticketRepository.delete(ticket);
    }

    @Override
    public void show(){

        List<Ticket> ticketList = ticketRepository.getData();
        for(Ticket ticket: ticketList){
            System.out.println(ticket.toString());
        }
    }

}
