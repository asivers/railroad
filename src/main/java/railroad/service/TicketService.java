package railroad.service;

import railroad.model.Ticket;
import java.util.List;

public interface TicketService {
    List<Ticket> allTickets();
    void add(Ticket ticket);
    Ticket getById(int id);
}