package railroad.dao;

import railroad.model.Ticket;
import java.util.List;

public interface TicketDAO {
    List<Ticket> allTickets();
    void add(Ticket ticket);
    void delete(Ticket ticket);
    void edit(Ticket ticket);
    Ticket getById(int id);
}