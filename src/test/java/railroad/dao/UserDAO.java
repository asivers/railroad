package railroad.dao;

import railroad.model.User;

public interface UserDAO {
    User getUserByUsername(String username);
}
