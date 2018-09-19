package lloydsPharmaProject.pages;

import lloydsPharmaProject.Models.Address;

public class OrderDetailsPage extends OrderConfirmationPage {

    public String getOrderTitle() {
        return findBy(".//header/h1").getText();
    }

    public Address getDeliveryAddress(String shippingMethod) {
        Address address = new Address();
        switch (shippingMethod) {
            case "To Be Picked Up":
                address.setFirstName($(".//h4/ancestor::div/ul/li/a").getText());
                address.setAddress1($(".//h4/ancestor::div/ul/li[2]").getText() + " " + $(".//h4/ancestor::div/ul/li[3]").getText());
                address.setPostCode($(".//h4/ancestor::div/ul/li[4]").getText().split(", ")[0]);
                address.setCity($(".//h4/ancestor::div/ul/li[4]").getText().split(", ")[1]);
                break;
            case "Prescription reserve":
                address.setFirstName($(".//h4/ancestor::div/ul/li/a").getText());
                address.setAddress1($(".//h4/ancestor::div/ul/li[2]").getText() + " " + $(".//h4/ancestor::div/ul/li[3]").getText());
                address.setPostCode($(".//h4/ancestor::div/ul/li[4]").getText().split(", ")[0]);
                address.setCity($(".//h4/ancestor::div/ul/li[4]").getText().split(", ")[1]);
                break;
            case "To Be Shipped":
                address.setFirstName($("(.//h4/ancestor::div/ul)[1]/li[1]").getText().split(" ")[0]);
                address.setLastName($("(.//h4/ancestor::div/ul)[1]/li[1]").getText().split(" ")[1]);
                address.setAddress1($("(.//h4/ancestor::div/ul)[1]/li[2]").getText());
                address.setPostCode($("(.//h4/ancestor::div/ul)[1]/li[3]").getText().split(", ")[0]);
                address.setCity($("(.//h4/ancestor::div/ul)[1]/li[3]").getText().split(", ")[1]);
                break;
        }
        return address;
    }

    public Address getPaymentAddress(String shippingMethod) {
        Address address = new Address();
        switch (shippingMethod) {
            case "To Be Picked Up":
                address.setFirstName($("(.//div/h4/following-sibling::p)[2]").getText());
                break;
            case "Prescription reserve":
                address.setFirstName($("(.//div/h4/following-sibling::p)[2]").getText());
                break;
            case "To Be Shipped":
                address.setFirstName($("(.//h4/ancestor::div/ul)[2]/li[1]").getText());
                address.setAddress1($("(.//h4/ancestor::div/ul)[2]/li[2]").getText());
                address.setAddress2(null);
                address.setPostCode($("(.//h4/ancestor::div/ul)[2]/li[3]").getText().split(", ")[0]);
                address.setCity($("(.//h4/ancestor::div/ul)[2]/li[3]").getText().split(", ")[1]);
                break;
        }
        return address;
    }


    public String getOrderTotal() {
        scrollIntoView(".//li[@class='total']/span", -100);
        return $(".//li[@class='total']/span").getText().replace(" ", "").replace("€", "").replace("-", "0").replace(",", ".");
    }

    public String getTotalOrderDiscount() {
        scrollIntoView(".//li[@class='savings']/span", -100);
        return $(".//li[@class='savings']/span").getText().replace(" ", "").replace("€", "").replace("-", "").replace(",", ".");
    }

    public String getOrderNote(String shippingMethod) {
        String ORDER_NOTE = ".//h4[contains(text(),'Aantekening') or contains(text(),'Commentaire')]/ancestor::div[1]//ul/li";
        scrollIntoView(ORDER_NOTE, -50);
        return $(ORDER_NOTE).getText();
    }

}
