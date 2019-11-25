package railroad.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "passengers")
public class Passenger implements Serializable {

    /**
     * Passenger entity class.
     */

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String first_name;

    @Column
    private String second_name;

    @Column
    private java.sql.Timestamp birth_date;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return first_name;
    }

    public void setFirstName(String firstName) {
        this.first_name = firstName;
    }

    public String getSecondName() {
        return second_name;
    }

    public void setSecondName(String secondName) {
        this.second_name = secondName;
    }

    public java.sql.Timestamp getBirthDate() {
        return birth_date;
    }

    public void setBirthDate(java.sql.Timestamp birthDate) {
        this.birth_date = birthDate;
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof Passenger ? id == (((Passenger) other).id) : (other == this);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode() + id;
    }

    @Override
    public String toString() {
        return id + " " + first_name + " " + second_name + " " + birth_date;
    }
}

