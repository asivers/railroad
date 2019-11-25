package railroad.service;

public interface NewUserService {

    /**
     * Checks if the user already exist.
     * If not, adds it to database.
     *
     * @param username username
     * @param hashedPassword hashed password
     */
    boolean isInDatabase(String username, String hashedPassword);

}
