package railroad.model.additional;

import javax.persistence.*;

public class StationTime {

    private String station_name;
    private String time;

    public StationTime(String station_name, String time) {
        this.station_name = station_name;
        this.time = time;
    }

    public String getStationName() {
        return station_name;
    }

    public void setStation_name(String station_name) {
        this.station_name = station_name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}

