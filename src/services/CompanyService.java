package services;

import domain.Client;
import domain.Organizer;

/**
 * Is main class which links front-end by back-end functionality.
 */
public class CompanyService {

    private static CompanyService companyServiceInstance = null;
    private OrganizerService organizerService;
    private ClientService clientService;
    private EventService eventService;
    private TicketService ticketService;
    private AddressService addressService;
    private AuditService auditService;
    private Client sessionClient;
    private Organizer sessionOrganizer;

    private CompanyService() {
        organizerService = OrganizerService.getOrganizerServiceInstance();
        clientService = ClientService.getClientServiceInstance();
        eventService = EventService.getEventServiceInstance();
        ticketService = TicketService.getTicketServiceInstance();
        addressService = AddressService.getAddressServiceInstance();
        auditService = AuditService.getAuditServiceInstance();
        sessionClient = null;
        sessionOrganizer = null;
    }

    public static CompanyService getCompanyServiceInstance(){

        if (companyServiceInstance == null) {
            companyServiceInstance = new CompanyService();
        }

        return companyServiceInstance;
    }

    public OrganizerService getOrganizerService() {
        return organizerService;
    }

    public ClientService getClientService() {
        return clientService;
    }

    public EventService getEventService() {
        return eventService;
    }

    public TicketService getTicketService() {
        return ticketService;
    }

    public AddressService getAddressService() {
        return addressService;
    }

    public AuditService getAuditService() {
        return auditService;
    }

    public Client getSessionClient() {
        return sessionClient;
    }

    public Organizer getSessionOrganizer() {
        return sessionOrganizer;
    }

    public void setSessionClient(Client sessionClient) {
        this.sessionClient = sessionClient;
    }

    public void setSessionOrganizer(Organizer sessionOrganizer) {
        this.sessionOrganizer = sessionOrganizer;
    }

}
