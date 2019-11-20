package railroad.dao;

import railroad.model.Passenger;
import railroad.model.additional.PassengerInfo;
import java.util.List;

public interface PassengerDAO {

    int countByTrainNumber(int trainNumber);
    List<Integer> getIdByTrainNumberList(int trainNumber, int page, int onPage);
    String getFirstNameByIdSingle(int id);
    String getSecondNameByIdSingle(int id);
    String getBirthDateByIdSingle(int id);
    int countByParameters(String firstName, String secondName, java.sql.Timestamp tzBirthDate);
    int getIdByParametersSingle(String firstName, String secondName, java.sql.Timestamp tzBirthDate);
    String getFirstNameByTicketIdSingle(int id);
    String getSecondNameByTicketIdSingle(int id);
    String getBirthDateByTicketIdSingle(int id);
    void add(Passenger newPassenger);

}