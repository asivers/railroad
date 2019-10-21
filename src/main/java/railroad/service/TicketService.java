package railroad.service;

import railroad.model.Ticket;
import java.util.List;

public interface TicketService {
    List<Ticket> allTickets();
    void add(Ticket ticket);
    void delete(Ticket ticket);
    void edit(Ticket ticket);
    Ticket getById(int id);
}