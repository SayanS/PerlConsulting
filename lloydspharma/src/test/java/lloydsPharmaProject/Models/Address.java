package lloydsPharmaProject.Models;


public class Address {
    private String title;
    private String firstName;
    private String lastName;
    private String address1;
    private String building;
    private String box;
    private String address2;
    private String phoneNumber;
    private String postCode;
    private String city;
    private String land;

    public Address() {
    }

    public Address(Address address) {
        this.title = address.title;
        this.firstName = address.firstName;
        this.lastName = address.lastName;
        this.address1 = address.address1;
        this.building = address.building;
        this.box = address.box;
        this.address2 = address.address2;
        this.phoneNumber = address.phoneNumber;
        this.postCode = address.postCode;
        this.city = address.city;
        this.land = address.land;
    }

    public String getLand() {
        return land;
    }

    public void setLand(String land) {
        this.land = land;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
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

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
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

    @Override
    public String toString() {
        return this.getFirstName() + " " + this.getLastName() + ", " + this.getAddress1() + " " + this.getBuilding() + ", " + this.getAddress2() +
                ", " + this.getPostCode() + " " + this.getCity() + ", " + this.getLand() + ", " + this.getPhoneNumber();
    }


}
