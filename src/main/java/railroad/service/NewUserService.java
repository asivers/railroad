package railroad.service;

public interface NewUserService {
    boolean isInDatabase(String username, String hashedPassword);
}
