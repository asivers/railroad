package railroad.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import railroad.dao.TicketDAO;
import railroad.model.additional.TicketInfo;
import railroad.service.TicketService;

import java.util.List;

@Service
public class TicketServiceImpl implements TicketService {
    private TicketDAO ticketDAO;

    @Autowired
    public void setTicketDAO(TicketDAO ticketDAO) {
        this.ticketDAO = ticketDAO;
    }

    @Override
    @Transactional
    public int ticketsByUserCount(int userID) {
        return ticketDAO.ticketsByUserCount(userID);
    }

    @Override
    @Transactional
    public List<TicketInfo> ticketsByUser(int userID, int page) {
        return ticketDAO.ticketsByUser(userID, page);
    }

}
