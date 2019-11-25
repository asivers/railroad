package railroad.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "stations")
public class Station implements Serializable {

    /**
     * Station entity class.
     */

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String station_name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStationName() {
        return station_name;
    }

    public void setStationName(String stationName) {
        this.station_name = stationName;
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof Station ? id == (((Station) other).id) : (other == this);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode() + id;
    }

    @Override
    public String toString() {
        return id + " " + station_name;
    }
}

