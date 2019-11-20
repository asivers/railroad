package railroad.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import railroad.dao.StationTrainDAO;
import railroad.dao.TicketDAO;
import railroad.dao.TrainDAO;
import railroad.messaging.MessageSender;
import railroad.model.Train;
import railroad.model.additional.TrainTime;
import railroad.model.additional.TrainTimeTime;
import railroad.service.TrainService;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import static railroad.service.impl.TimeSupport.TimeToLong;

@Service
public class TrainServiceImpl implements TrainService {

    @Autowired
    MessageSender messageSender;

    private TrainDAO trainDAO;
    @Autowired
    public void setTrainDAO(TrainDAO trainDAO) {
        this.trainDAO = trainDAO;
    }

    private StationTrainDAO stationTrainDAO;
    @Autowired
    public void setStationTrainDAO(StationTrainDAO stationTrainDAO) {
        this.stationTrainDAO = stationTrainDAO;
    }

    private TicketDAO ticketDAO;
    @Autowired
    public void setTicketDAO(TicketDAO ticketDAO) {
        this.ticketDAO = ticketDAO;
    }

    @Override
    @Transactional
    public int allTrainsCount() {
        return trainDAO.countAllTrains();
    }

    @Override
    @Transactional
    public List<Integer> allTrains(int page) {
        int onPage = 7;
        return trainDAO.getTrainNumberList(page, onPage);
    }

    @Override
    @Transactional
    public int trainsByStationCount(String stationName) {
        return trainDAO.countByStationName(stationName);
    }

    @Override
    @Transactional
    public List<TrainTime> trainsByStation(String stationName, int page) {
        int onPage = 7;
        List<Integer> trainIDs = trainDAO.getIdByStationNameList(stationName, page, onPage);
        List<TrainTime> trainsTimes = new ArrayList<>();
        String stopTime = "";
        for (Integer id : trainIDs) {
            int trainNumber = trainDAO.getTrainNumberByIdSingle(id);
            stopTime = stationTrainDAO.getStopTimeByTrainIdAndStationNameSingle(id, stationName);
            stopTime = TimeSupport.LongToTime(TimeToLong(stopTime) - 10800000);
            trainsTimes.add(new TrainTime(trainNumber, stopTime));
        }
        return trainsTimes;
    }

    @Override
    @Transactional
    public int trainsBySearchCount(String departureStationName, String arrivalStationName, Time lowerTime, Time upperTime) {
        return trainDAO.countBySearch(departureStationName, arrivalStationName, lowerTime, upperTime);
    }

    @Override
    @Transactional
    public List<TrainTimeTime> trainsBySearch(String departureStationName, String arrivalStationName, Time lowerTime, Time upperTime, int page) {
        int onPage = 7;
        List<Integer> trainIDs = trainDAO.getIdBySearchList(departureStationName, arrivalStationName, lowerTime, upperTime, page, onPage);
        List<TrainTimeTime> trainsBothTimes = new ArrayList<>();
        String trueDepartureTime = "";
        String trueArrivalTime = "";
        for (Integer id : trainIDs) {
            int tmpNumber = trainDAO.getTrainNumberByIdSingle(id);
            trueDepartureTime = stationTrainDAO.getStopTimeByTrainIdAndStationNameSingle(id, departureStationName);
            trueArrivalTime = stationTrainDAO.getStopTimeByTrainIdAndStationNameSingle(id, arrivalStationName);
            if (TimeSupport.TimeToLong(trueArrivalTime) - 10800000 > TimeSupport.TimeToLong(trueDepartureTime) - 10800000)
                trainsBothTimes.add(new TrainTimeTime(tmpNumber, TimeSupport.LongToTime(TimeToLong(trueDepartureTime) - 10800000), TimeSupport.LongToTime(TimeToLong(trueArrivalTime) - 10800000)));
        }
        return trainsBothTimes;
    }

    @Override
    @Transactional
    public boolean freeSeats(int trainNumber) {
        int trainID = trainDAO.getIdByTrainNumberSingle(trainNumber);
        int tickets = ticketDAO.countByTrainId(trainID);
        int seats = trainDAO.getSeatsByIdSingle(trainID);
        if (seats > tickets)
            return true;
        else
            return false;
    }

    @Override
    @Transactional
    public boolean isExist(int trainNumber, int seats) {
        int isNewTrain = trainDAO.countByTrainNumber(trainNumber);
        if (isNewTrain == 0) {
            Train newTrain = new Train();
            newTrain.setNumber(trainNumber);
            newTrain.setSeats(seats);
            trainDAO.add(newTrain);
            return false;
        }
        else
            return true;
    }

    @Override
    @Transactional
    public void trainsByStationTB(String stationName, int page) {
        int onPage = 7;
        int trainsCount = trainDAO.countByStationName(stationName);
        int pagesCount = (trainsCount + onPage - 1) / onPage;
        List<Integer> trainIDs = trainDAO.getIdByStationNameList(stationName, page, onPage);
        List<TrainTime> trainsTimes = new ArrayList<>();
        String stopTime = "";
        for (Integer id : trainIDs) {
            int trainNumber = trainDAO.getTrainNumberByIdSingle(id);
            stopTime = stationTrainDAO.getStopTimeByTrainIdAndStationNameSingle(id, stationName);
            stopTime = TimeSupport.LongToTime(TimeToLong(stopTime) - 10800000);
            trainsTimes.add(new TrainTime(trainNumber, stopTime));
        }
        String toTimeBoardString = stationName + "/" + pagesCount + "/";
        for (TrainTime trainTime : trainsTimes) {
            toTimeBoardString += trainTime.getNumber();
            toTimeBoardString += "/";
            toTimeBoardString += trainTime.getTime();
            toTimeBoardString += "/";
        }
        messageSender.sendMessage(toTimeBoardString);
    }

}