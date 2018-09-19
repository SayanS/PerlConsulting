package lloydsPharmaProject.Models;

public class CustomerProfile {

    private String title;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String address;
    private String building;
    private String box;
    private String postCode;
    private String city;
    private String email;
    private String password;
    private String confirmPassword;
    private String iWantToBeFour;
    private String iWantToResiveNewsLetter;
    private String additionalInfo;
    private String birthDate;

    public CustomerProfile() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public String getBox() {
        return box;
    }

    public void setBox(String box) {
        this.box = box;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getIWantToBeFour() {
        return iWantToBeFour;
    }

    public void setiWantToBeFour(String iWantToBeFour) {
        this.iWantToBeFour = iWantToBeFour;
    }

    public String getiWantToResiveNewsLetter() {
        return iWantToResiveNewsLetter;
    }

    public void setiWantToResiveNewsLetter(String iWantToResiveNewsLetter) {
        this.iWantToResiveNewsLetter =iWantToResiveNewsLetter;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }
}
