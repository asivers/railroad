package railroad.service.impl;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import railroad.dao.PassengerDAO;
import railroad.dao.TicketDAO;
import railroad.dao.TrainDAO;
import railroad.model.additional.TicketInfo;
import railroad.service.TicketService;

import java.util.ArrayList;
import java.util.List;

@Service
public class TicketServiceImpl implements TicketService {

    private TicketDAO ticketDAO;
    @Autowired
    public void setTicketDAO(TicketDAO ticketDAO) { this.ticketDAO = ticketDAO; }

    private TrainDAO trainDAO;
    @Autowired
    public void setTrainDAO(TrainDAO trainDAO) { this.trainDAO = trainDAO; }

    private PassengerDAO passengerDAO;
    @Autowired
    public void setPassengerDAO(PassengerDAO passengerDAO) { this.passengerDAO = passengerDAO; }

    @Override
    @Transactional
    public int ticketsByUserCount(int userID) {
        return ticketDAO.countByUserId(userID);
    }

    @Override
    @Transactional
    public List<TicketInfo> ticketsByUser(int userID, int page) {
        int onPage = 8;
        List<Integer> ticketIDs = ticketDAO.getIdByUserIdList(userID, page, onPage);
        List<TicketInfo> ticketInfos = new ArrayList<>();
        String passengerFirstName = "";
        String passengerSecondName = "";
        String passengerBirthDate = "";
        int trainNumber = 0;
        for (Integer id : ticketIDs) {
            passengerFirstName = passengerDAO.getFirstNameByTicketIdSingle(id);
            passengerSecondName = passengerDAO.getSecondNameByTicketIdSingle(id);
            passengerBirthDate = passengerDAO.getBirthDateByTicketIdSingle(id);
            trainNumber = trainDAO.getTrainNumberByTicketIdSingle(id);
            ticketInfos.add(new TicketInfo(id, passengerFirstName, passengerSecondName, passengerBirthDate, trainNumber));
        }
        return ticketInfos;
    }

}
