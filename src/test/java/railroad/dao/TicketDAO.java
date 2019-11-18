package railroad.dao;

import railroad.model.additional.TicketInfo;

import java.util.List;

public interface TicketDAO {

    int ticketsByUserCount(int userID);
    List<TicketInfo> ticketsByUser(int userID, int page);

}
