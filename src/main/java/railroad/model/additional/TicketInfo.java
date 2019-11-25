package railroad.model.additional;

public class TicketInfo {

    /**
     * TicketInfo supportive data transfer class.
     */

    private int ticketID;
    private String firstName;
    private String secondName;
    private String birthDate;
    private int trainNumber;

    public TicketInfo(int ticketID, String firstName, String secondName, String birthDate, int trainNumber) {
        this.ticketID = ticketID;
        this.firstName = firstName;
        this.secondName = secondName;
        this.birthDate = birthDate;
        this.trainNumber = trainNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public int getTicketID() { return ticketID; }

    public void setTicketID(int ticketID) { this.ticketID = ticketID; }

    public int getTrainNumber() { return trainNumber; }

    public void setTrainNumber(int trainNumber) { this.trainNumber = trainNumber; }
}
