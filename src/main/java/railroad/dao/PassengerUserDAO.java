package railroad.dao;

import railroad.model.PassengerUser;

public interface PassengerUserDAO {

    /**
     * Adds new passenger-user relation to database.
     *
     * @param newPassengerUser new passenger-user relation
     */
    void add(PassengerUser newPassengerUser);

    /**
     * Gets number of new passenger-user relations by their ids.
     *
     * @param passenger_id passenger's id
     * @param user_id user's id
     */
    int countRelations(int passenger_id, int user_id);

}