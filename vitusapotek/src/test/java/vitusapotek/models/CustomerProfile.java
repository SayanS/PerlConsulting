package vitusapotek.models;

import java.util.ArrayList;
import java.util.List;

public class CustomerProfile {
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String address;
    private String postCode;
    private String city;
    private String email;
    private String password;
    private String confirmPassword;
    private Boolean iWantTobe4;
    private Boolean newsLetter;
    private List<String> memberShip;

    public CustomerProfile() {
        this.memberShip=new ArrayList<>();
    }


    public String getFirstname() {
        return firstName;
    }

    public void setFirstname(String firstname) {
        this.firstName = firstname;
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

    public Boolean getIWantTobe4() {
        return iWantTobe4;
    }

    public void setIWantTobe4(Boolean IWantTobe4) {
        this.iWantTobe4 = IWantTobe4;
    }

    public Boolean getNewsLetterFlag() {
        return newsLetter;
    }

    public void setNewsLetterFlag(Boolean newsLetter) {
        this.newsLetter = newsLetter;
    }
}
