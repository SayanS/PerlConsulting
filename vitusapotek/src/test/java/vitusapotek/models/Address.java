package vitusapotek.models;

public class Address {
    private String land;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String address1;
    private String address2;
    private String postCode;
    private String city;
    private Boolean defaultAddressFlag;

    public Address() {
    }

    public Address(Address address) {
        this.land=address.land;
        this.firstName = address.firstName;
        this.lastName = address.lastName;
        this.phoneNumber = address.phoneNumber;
        this.address1 = address.address1;
        this.address2 = address.address2;
        this.postCode = address.postCode;
        this.city = address.city;
        this.defaultAddressFlag=false;
    }

    public void clear(){
        this.land=null;
        this.firstName = null;
        this.lastName = null;
        this.phoneNumber = null;
        this.address1 = null;
        this.address2 = null;
        this.postCode = null;
        this.city = null;
        this.defaultAddressFlag=false;
    }

    public Boolean getDefaultAddressFlag() {
        return defaultAddressFlag;
    }

    public void setLand(Boolean defaultAddressFlag) {
        this.defaultAddressFlag = defaultAddressFlag;
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

    public void setAddress1(String address) {
        this.address1 = address;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address) {
        this.address2 = address;
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
        return this.getFirstName() + " " + this.getLastName() + ", "+this.getPhoneNumber()+", "+ this.getAddress1()+this.getAddress2()+
                ", " + this.getPostCode() + " " + this.getCity();
    }
}
