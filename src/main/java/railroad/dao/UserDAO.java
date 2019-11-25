package railroad.dao;

import railroad.model.User;

public interface UserDAO {

    /**
     * Gets user by its username.
     *
     * @param username username
     */
    User getUserByUsernameSingle(String username);

    /**
     * Gets number of users by its username.
     * If user exists, the result will be 1.
     *
     * @param username username
     */
    int countByUsername(String username);

    /**
     * Gets user's id by its username.
     *
     * @param username username
     */
    int getIdByUsernameSingle(String username);

    /**
     * Adds user to database.
     *
     * @param newUser new user
     */
    void add(User newUser);
}
