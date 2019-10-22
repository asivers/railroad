package railroad.dao;

import railroad.model.Ticket;
import java.util.List;

public interface TicketDAO {
    List<Ticket> allTickets();
    void add(Ticket ticket);
    Ticket getById(int id);
}