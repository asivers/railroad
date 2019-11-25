package railroad.dao;

import railroad.model.Passenger;
import railroad.model.additional.PassengerInfo;
import java.util.List;

public interface PassengerDAO {

    /**
     * Gets number of passengers by train number
     *
     * @param trainNumber train number
     */
    int countByTrainNumber(int trainNumber);

    /**
     * Gets passenger's id list by train number.
     *
     * @param trainNumber train number
     * @param page page number
     * @param onPage number of passengers on the page
     */
    List<Integer> getIdByTrainNumberList(int trainNumber, int page, int onPage);

    /**
     * Gets passenger's first name by id.
     *
     * @param id passenger's id
     */
    String getFirstNameByIdSingle(int id);

    /**
     * Gets passenger's second name by id.
     *
     * @param id passenger's id
     */
    String getSecondNameByIdSingle(int id);

    /**
     * Gets passenger's birth date by id.
     *
     * @param id passenger's id
     */
    String getBirthDateByIdSingle(int id);

    /**
     * Gets number of passengers by their first name, second name, and birth date.
     *
     * @param firstName first name
     * @param secondName second name
     * @param tzBirthDate birth date
     */
    int countByParameters(String firstName, String secondName, java.sql.Timestamp tzBirthDate);

    /**
     * Gets passenger's id by their first name, second name, and birth date.
     *
     * @param firstName first name
     * @param secondName second name
     * @param tzBirthDate birth date
     */
    int getIdByParametersSingle(String firstName, String secondName, java.sql.Timestamp tzBirthDate);

    /**
     * Gets passenger's first name by their id.
     *
     * @param id passenger's id
     */
    String getFirstNameByTicketIdSingle(int id);

    /**
     * Gets passenger's second name by their id.
     *
     * @param id passenger's id
     */
    String getSecondNameByTicketIdSingle(int id);

    /**
     * Gets passenger's birth date by their id.
     *
     * @param id passenger's id
     */
    String getBirthDateByTicketIdSingle(int id);

    /**
     * Adds new passenger to database.
     *
     * @param newPassenger new passenger
     */
    void add(Passenger newPassenger);
}