package railroad.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import railroad.dao.NewUserDAO;
import railroad.service.NewUserService;

@Service
public class NewUserServiceImpl implements NewUserService {
    private NewUserDAO newUserDAO;

    @Autowired
    public void setNewUserDAO(NewUserDAO newUserDAO) {
        this.newUserDAO = newUserDAO;
    }

    @Override
    @Transactional
    public boolean isInDatabase(String username, String hashedPassword) {
        return newUserDAO.isInDatabase(username, hashedPassword);
    }

    @Override
    @Transactional
    public void add(String username, String hashedPassword) { newUserDAO.add(username, hashedPassword); }

}
