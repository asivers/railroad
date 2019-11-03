package railroad.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "authority_users")
public class AuthorityUser implements Serializable {

    @Id
    @Column
    private int authority_id;

    @Id
    @Column
    private int user_id;

    public int getAuthority_id() {
        return authority_id;
    }

    public void setAuthority_id(int authority_id) {
        this.authority_id = authority_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof StationTrain ? ((authority_id == (((AuthorityUser) other).authority_id)) && (user_id == (((AuthorityUser) other).user_id))) : (other == this);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode() + (authority_id / user_id) + authority_id;
    }

    @Override
    public String toString() {
        return authority_id + " " + user_id;
    }
}

