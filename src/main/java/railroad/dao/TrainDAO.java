package railroad.dao;

import railroad.model.Train;
import railroad.model.additional.TrainTime;
import railroad.model.additional.TrainTimeTime;
import java.sql.Time;
import java.util.List;

public interface TrainDAO {

    int countAllTrains();
    List<Integer> getTrainNumberList(int page, int onPage);
    int countByStationName(String stationName);
    List<Integer> getIdByStationNameList(String stationName, int page, int onPage);
    int getTrainNumberByIdSingle(int id);
    int countBySearch(String departureStationName, String arrivalStationName, Time lowerTime, Time upperTime);
    List<Integer> getIdBySearchList(String departureStationName, String arrivalStationName, Time lowerTime, Time upperTime, int page, int onPage);
    int getSeatsByIdSingle(int id);
    int countByTrainNumber(int trainNumber);
    int getIdByTrainNumberSingle(int trainNumber);
    int getTrainNumberByTicketIdSingle(int id);
    void add(Train newTrain);

}