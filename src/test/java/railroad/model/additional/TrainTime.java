package railroad.model.additional;

import javax.persistence.*;

public class TrainTime {

    private int number;
    private String time;

    public TrainTime(int number, String time) {
        this.number = number;
        this.time = time;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}

