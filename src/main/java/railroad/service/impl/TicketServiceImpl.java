package railroad.service.impl;

import railroad.dao.TicketDAO;
import railroad.dao.impl.TicketDAOImpl;
import railroad.model.Ticket;
import railroad.service.TicketService;

import java.util.List;

public class TicketServiceImpl implements TicketService {
    private TicketDAO ticketDAO = new TicketDAOImpl();

    @Override
    public List<Ticket> allTickets() {
        return ticketDAO.allTickets();
    }

    @Override
    public void add(Ticket ticket) {
        ticketDAO.add(ticket);
    }

    @Override
    public void delete(Ticket ticket) {
        ticketDAO.delete(ticket);
    }

    @Override
    public void edit(Ticket ticket) {
        ticketDAO.edit(ticket);
    }

    @Override
    public Ticket getById(int id) {
        return ticketDAO.getById(id);
    }
}