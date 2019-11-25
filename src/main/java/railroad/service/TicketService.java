package railroad.service;

import railroad.model.additional.TicketInfo;

import java.util.List;

public interface TicketService {

    /**
     * Gets number of tickets by user's id.
     *
     * @param userID user's id
     */
    int ticketsByUserCount(int userID);

    /**
     * Gets ticket's info list by user's id.
     *
     * @param userID train number
     * @param page page number
     */
    List<TicketInfo> ticketsByUser(int userID, int page);

}
