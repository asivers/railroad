package railroad.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import railroad.dao.TicketDAO;
import railroad.model.Ticket;
import railroad.service.TicketService;
import java.util.List;

@Service
public class TicketServiceImpl implements TicketService {
    private TicketDAO ticketDAO;

    @Autowired
    public void setTicketDAO(TicketDAO ticketDAO) {
        this.ticketDAO = ticketDAO;
    }

}