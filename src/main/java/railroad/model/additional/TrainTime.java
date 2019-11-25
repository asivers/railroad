package railroad.model.additional;

public class TrainTime {

    /**
     * TrainTime supportive data transfer class.
     */

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

