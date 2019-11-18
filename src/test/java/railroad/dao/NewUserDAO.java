package railroad.dao;

public interface NewUserDAO {

    boolean isInDatabase(String username, String hashedPassword);
    void add(String username, String hashedPassword);

}
