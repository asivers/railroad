package railroad.model.additional;

public class PassengerInfo {

    /**
     * PassengerInfo supportive data transfer class.
     */

    private String firstName;
    private String secondName;
    private String birthDate;

    public PassengerInfo(String firstName, String secondName, String birthDate) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.birthDate = birthDate;
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
}
