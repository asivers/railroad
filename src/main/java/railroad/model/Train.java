package railroad.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "trains")
public class Train implements Serializable {

    /**
     * Train entity class.
     */

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private int number;

    @Column
    private int seats;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getSeats() {
        return seats;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof Train ? id == (((Train) other).id) : (other == this);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode() + id;
    }

    @Override
    public String toString() {
        return id + " " + number + " " + seats;
    }
}

