package railroad.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "ticket")
public class Ticket implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "train_id")
    private int train_id;

    @Column(name = "passenger_id")
    private int passenger_id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTrainId() {
        return train_id;
    }

    public void setTrainId(int train_id) {
        this.train_id = train_id;
    }

    public int getPassengerId() {
        return passenger_id;
    }

    public void setPassengerId(int passenger_id) {
        this.passenger_id = passenger_id;
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof Ticket ? id == (((Ticket) other).id) : (other == this);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode() + id;
    }

    @Override
    public String toString() {
        return id + " " + train_id + " " + passenger_id;
    }
}

