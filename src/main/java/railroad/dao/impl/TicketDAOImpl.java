package railroad.dao.impl;

import org.springframework.stereotype.Repository;
import railroad.dao.TicketDAO;
import railroad.model.Ticket;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class TicketDAOImpl implements TicketDAO {
    private static final AtomicInteger AUTO_ID = new AtomicInteger(0);
    private static Map<Integer, Ticket> tickets = new HashMap<>();

    static {
        Ticket ticket1 = new Ticket();
        ticket1.setId(AUTO_ID.getAndIncrement());
        ticket1.setTrainId(1);
        ticket1.setPassengerId(1);
        tickets.put(ticket1.getId(), ticket1);
    }

    @Override
    public List<Ticket> allTickets() {
        return new ArrayList<>(tickets.values());
    }

    @Override
    public void add(Ticket ticket) {
        ticket.setId(AUTO_ID.getAndIncrement());
        tickets.put(ticket.getId(), ticket);
    }

    @Override
    public void delete(Ticket ticket) {
        tickets.remove(ticket.getId());
    }

    @Override
    public void edit(Ticket ticket) {
        tickets.put(ticket.getId(), ticket);
    }

    @Override
    public Ticket getById(int id) {
        return tickets.get(id);
    }
}