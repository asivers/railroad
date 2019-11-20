package railroad.dao;

import railroad.model.Ticket;
import railroad.model.additional.TicketInfo;

import java.util.List;

public interface TicketDAO {

    int countByUserId(int user_id);
    int countByPassengerIdAndTrainId(int passenger_id, int train_id);
    int countByTrainId(int train_id);
    List<Integer> getIdByUserIdList(int user_id, int page, int onPage);
    void add(Ticket newTicket);

}
