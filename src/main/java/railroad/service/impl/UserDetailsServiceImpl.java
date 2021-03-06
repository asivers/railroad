package railroad.service.impl;

import railroad.model.login.RailroadUserDetails;
import railroad.model.User;
import railroad.dao.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

    /**
     * Spring Security User details service.
     */

    @Autowired
    private UserDAO userDAO;

    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDAO.getUserByUsernameSingle(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found.");
        }
        return new RailroadUserDetails(user);
    }

}