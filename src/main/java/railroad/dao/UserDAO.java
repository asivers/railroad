package railroad.dao;

import railroad.model.User;

public interface UserDAO {
    User getUserByUsernameSingle(String username);
    int countByUsername(String username);
    int getIdByUsernameSingle(String username);
    void add(User newUser);
}
