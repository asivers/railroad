package railroad.dao;

import railroad.model.Ticket;
import railroad.model.additional.TicketInfo;

import java.util.List;

public interface TicketDAO {

    /**
     * Gets number of tickets by user's id.
     *
     * @param user_id user's id
     */
    int countByUserId(int user_id);

    /**
     * Gets number of tickets by passenger's id and train's id.
     * If the passenger is already on the train, the result will be 1.
     *
     * @param passenger_id passenger's id
     * @param train_id train's id
     */
    int countByPassengerIdAndTrainId(int passenger_id, int train_id);

    /**
     * Gets number of tickets by train's id.
     *
     * @param train_id train's id
     */
    int countByTrainId(int train_id);

    /**
     * Gets ticket's id list by user's id.
     *
     * @param user_id user's id
     * @param page page number
     * @param onPage number of tickets on the page
     */
    List<Integer> getIdByUserIdList(int user_id, int page, int onPage);

    /**
     * Adds new ticket to database.
     *
     * @param newTicket new ticket
     */
    void add(Ticket newTicket);
}
