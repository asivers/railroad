package railroad.service;

import railroad.model.additional.TicketInfo;

import java.util.List;

public interface TicketService {
    int ticketsByUserCount(int userID);
    List<TicketInfo> ticketsByUser(int userID, int page);
}
