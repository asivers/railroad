package railroad.dao;

import railroad.model.AuthorityUser;

public interface AuthorityUserDAO {

    /**
     * Adds authority-user relation to database.
     *
     * @param newAuthorityUser new authority-user relation
     */
    void add(AuthorityUser newAuthorityUser);

}
