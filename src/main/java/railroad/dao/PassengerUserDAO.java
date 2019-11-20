package railroad.dao;

import railroad.model.PassengerUser;

public interface PassengerUserDAO {
    int countRelations(int passenger_id, int user_id);
    void add(PassengerUser newPassengerUser);
}