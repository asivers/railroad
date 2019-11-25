package railroad.model.additional;

public class TrainTimeTime {

    /**
     * TrainTimeTime supportive data transfer class.
     */

    private int number;
    private String time1;
    private String time2;

    public TrainTimeTime(int number, String time1, String time2) {
        this.number = number;
        this.time1 = time1;
        this.time2 = time2;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getTime1() {
        return time1;
    }

    public void setTime1(String time1) {
        this.time1 = time1;
    }

    public String getTime2() {
        return time2;
    }

    public void setTime2(String time2) {
        this.time2 = time2;
    }
}

