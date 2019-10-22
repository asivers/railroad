package railroad.model;

import javax.persistence.*;
import java.sql.Time;

public class TrainTime {

    private int number;
    private Time time;

    public TrainTime(int number, Time time) {
        this.number = number;
        this.time = time;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }
}

