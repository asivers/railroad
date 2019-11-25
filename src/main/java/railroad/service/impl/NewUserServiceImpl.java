package railroad.service.impl;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import railroad.dao.AuthorityUserDAO;
import railroad.dao.UserDAO;
import railroad.model.AuthorityUser;
import railroad.model.User;
import railroad.service.NewUserService;

@Service
public class NewUserServiceImpl implements NewUserService {

    private AuthorityUserDAO authorityUserDAO;
    @Autowired
    public void setAuthorityUserDAO(AuthorityUserDAO authorityUserDAO) {
        this.authorityUserDAO = authorityUserDAO;
    }

    private UserDAO userDAO;
    @Autowired
    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    /**
     * Checks if the user already exist.
     * If not, adds it to database.
     *
     * @param username username
     * @param hashedPassword hashed password
     */
    @Override
    @Transactional
    public boolean isInDatabase(String username, String hashedPassword) {
        int isNewUser = userDAO.countByUsername(username);
        if (isNewUser > 0)
            return true;
        else {
            User newUser = new User();
            newUser.setUsername(username);
            newUser.setPassword(hashedPassword);
            userDAO.add(newUser);
            int userID = userDAO.getIdByUsernameSingle(username);
            AuthorityUser newAuthorityUser = new AuthorityUser();
            newAuthorityUser.setAuthority_id(2);
            newAuthorityUser.setUser_id(userID);
            authorityUserDAO.add(newAuthorityUser);
            return false;
        }
    }

}
