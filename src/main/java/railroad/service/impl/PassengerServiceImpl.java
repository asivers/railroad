package railroad.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import railroad.dao.PassengerDAO;
import railroad.dao.PassengerUserDAO;
import railroad.dao.TicketDAO;
import railroad.dao.TrainDAO;
import railroad.model.Passenger;
import railroad.model.PassengerUser;
import railroad.model.Ticket;
import railroad.model.additional.PassengerInfo;
import railroad.service.PassengerService;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service
public class PassengerServiceImpl implements PassengerService {

    private PassengerDAO passengerDAO;
    @Autowired
    public void setPassengerDAO(PassengerDAO passengerDAO) {
        this.passengerDAO = passengerDAO;
    }

    private PassengerUserDAO passengerUserDAO;
    @Autowired
    public void setPassengerUserDAO(PassengerUserDAO passengerUserDAO) {
        this.passengerUserDAO = passengerUserDAO;
    }

    private TicketDAO ticketDAO;
    @Autowired
    public void setTicketDAO(TicketDAO ticketDAO) { this.ticketDAO = ticketDAO; }

    private TrainDAO trainDAO;
    @Autowired
    public void setTrainDAO(TrainDAO trainDAO) { this.trainDAO = trainDAO; }


    @Override
    @Transactional
    public int passengersByTrainCount(int trainNumber) {
        return passengerDAO.countByTrainNumber(trainNumber);
    }

    @Override
    @Transactional
    public List<PassengerInfo> passengersByTrain(int trainNumber, int page) {
        int onPage = 8;
        List<Integer> passengerIDs = passengerDAO.getIdByTrainNumberList(trainNumber, page, onPage);
        List<PassengerInfo> passengerInfos = new ArrayList<>();
        String passengerFirstName = "";
        String passengerSecondName = "";
        String passengerBirthDate = "";
        for (Integer id : passengerIDs) {
            passengerFirstName = passengerDAO.getFirstNameByIdSingle(id);
            passengerSecondName = passengerDAO.getSecondNameByIdSingle(id);
            passengerBirthDate = passengerDAO.getBirthDateByIdSingle(id);
            passengerInfos.add(new PassengerInfo(passengerFirstName, passengerSecondName, passengerBirthDate));
        }
        return passengerInfos;
    }

    @Override
    @Transactional
    public boolean isOnTrain(String firstName, String secondName, String birthDate, int trainNumber, int currentUserID) {
        java.sql.Timestamp tzBirthDate = Timestamp.valueOf(birthDate + " 03:00:00.0");
        int isNewPassenger = passengerDAO.countByParameters(firstName, secondName, tzBirthDate);
        int passengerID = 0;
        int trainID = trainDAO.getIdByTrainNumberSingle(trainNumber);
        if (isNewPassenger == 0) {
            Passenger newPassenger = new Passenger();
            newPassenger.setFirstName(firstName);
            newPassenger.setSecondName(secondName);
            newPassenger.setBirthDate(tzBirthDate);
            passengerDAO.add(newPassenger);
            passengerID = passengerDAO.getIdByParametersSingle(firstName, secondName, tzBirthDate);
            PassengerUser newPassengerUser = new PassengerUser();
            newPassengerUser.setPassenger_id(passengerID);
            newPassengerUser.setUser_id(currentUserID);
            passengerUserDAO.add(newPassengerUser);
            Ticket newTicket = new Ticket();
            newTicket.setPassengerId(passengerID);
            newTicket.setTrainId(trainID);
            ticketDAO.add(newTicket);
            return false;
        }
        else {
            passengerID = passengerDAO.getIdByParametersSingle(firstName, secondName, tzBirthDate);
            int passengerUserRelation = passengerUserDAO.countRelations(passengerID, currentUserID);
            if (passengerUserRelation == 1) {
                int isOnTrain = ticketDAO.countByPassengerIdAndTrainId(passengerID, trainID);
                if (isOnTrain == 0) {
                    Ticket newTicket = new Ticket();
                    newTicket.setPassengerId(passengerID);
                    newTicket.setTrainId(trainID);
                    ticketDAO.add(newTicket);
                    return false;
                }
                else
                    return true;
            }
            else
                return true;
        }
    }

}