package railroad.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "passengers_users")
public class PassengerUser implements Serializable {

    @Id
    @Column
    private int passenger_id;

    @Id
    @Column
    private int user_id;

    public int getPassenger_id() {
        return passenger_id;
    }

    public void setPassenger_id(int passenger_id) {
        this.passenger_id = passenger_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof StationTrain ? ((passenger_id == (((PassengerUser) other).passenger_id)) && (user_id == (((PassengerUser) other).user_id))) : (other == this);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode() + (passenger_id / user_id) + passenger_id;
    }

    @Override
    public String toString() {
        return passenger_id + " " + user_id;
    }
}

