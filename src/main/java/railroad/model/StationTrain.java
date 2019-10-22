package railroad.model;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Time;

@Entity
@Table(name = "stations_trains")
public class StationTrain implements Serializable {

    @Id
    @Column(name = "station_id")
    private int station_id;

    @Id
    @Column(name = "station_id")
    private int train_id;

    @Column(name = "time")
    private Time time;

    public int getStationId() {
        return station_id;
    }

    public void setStationId(int station_id) {
        this.station_id = station_id;
    }

    public int getTrainId() {
        return train_id;
    }

    public void setTrainId(int train_id) {
        this.train_id = train_id;
    }

    public Time getTrainTime() {
        return time;
    }

    public void setTrainTime(Time time) {
        this.time = time;
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof StationTrain ? ((station_id == (((StationTrain) other).station_id)) && (train_id == (((StationTrain) other).train_id))) : (other == this);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode() + (station_id / train_id) + station_id;
    }

    @Override
    public String toString() {
        return station_id + " " + train_id + " " + time;
    }
}

