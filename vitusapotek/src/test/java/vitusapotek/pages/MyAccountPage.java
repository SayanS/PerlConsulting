package vitusapotek.pages;

import net.serenitybdd.core.annotations.findby.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import vitusapotek.models.Address;
import vitusapotek.models.CustomerProfile;
import vitusapotek.models.OrderHistoryLine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class MyAccountPage extends BasePage {
    String ORDER_HISTORY_ORDER_LINE = ".//a[@href='/my-account/order/$OrderNumber']";

    public String getValueFromProfileDetails(String fieldName) {
        return findBy(".//input[@placeholder='" + fieldName + "']").getText();
    }

    public void selectItemFromProfileDetails(String itemName) {
        String MENU_ITEMS="((.//ul[@class='account-navigation-list sidebar-navigation-list js-droplist']/li)/ul/li)";
        String ITEM = ".//a[contains(text(),'" + itemName + "')]";
        Integer itemIndex=0;
        withTimeoutOf(10, TimeUnit.SECONDS).waitFor(ExpectedConditions.visibilityOfElementLocated(By.xpath(ITEM)));
        for(int i=1;i<=findAll(MENU_ITEMS).size();i++) {
            if(findBy(MENU_ITEMS + "[" + i + "]").getText().equals(itemName)) {
               itemIndex++;
                break;
            }
        }
        if(itemIndex>=4){
            scrollIntoView(MENU_ITEMS + "[" + itemIndex + "]");
        }
        moveTo(ITEM).click();
    }

    public CustomerProfile getPersonalInfoFromProfile() {
        CustomerProfile personalInfo = new CustomerProfile();
        personalInfo.setFirstname($(".//input[@placeholder='Fornavn']").getValue());
        personalInfo.setLastName($(".//input[@placeholder='Etternavn']").getValue());
        personalInfo.setPhoneNumber($(".//input[@placeholder='Telefonnummer til hentemelding']").getValue());
        if (findBy(".//input[@id='_hasMembership']").getValue().equals("true")) {
            personalInfo.setIWantTobe4(true);
        }
        if (findBy(".//input[@id='_consentForNewsletter']").getValue().equals("true")) {
            personalInfo.setNewsLetterFlag(true);
        }
        return personalInfo;
    }

    public String[] getProfileMembershipGroups() {
        String MEMBERSHIP_CONTAINER = "(.//form[@id='pearlUpdateProfileForm']//ul[@id='groups-list']/li)";
        List<String> groupsList = new ArrayList<>();
        for (int i = 1; i <= findAll(MEMBERSHIP_CONTAINER).size(); i++) {
            if (findBy(MEMBERSHIP_CONTAINER + "[" + i + "]//input[contains(@id,'specialInterestGroupCodes')]").getAttribute("checked").equals("true")) {
                groupsList.add(findBy(MEMBERSHIP_CONTAINER + "[" + i + "]//label").getText());
            }
        }
        String[] actualGroups = groupsList.toArray(new String[0]);
        Arrays.sort(actualGroups);
        return actualGroups;
    }

    public Object[] getProfilePharmacies() {
        String PHARMACIES_CONTAINER = "(.//form[@id='pearlUpdateProfileForm']//ul[@id='favorite-pharmacies']/li)";
        List<String> pharmaciesList = new ArrayList<>();
        for (int i = 1; i <= findAll(PHARMACIES_CONTAINER).size(); i++) {
            if (findBy(PHARMACIES_CONTAINER + "[" + i + "]//input[contains(@id,'favuoritePharmaciesCodes')]").getAttribute("checked").equals("true")) {
                pharmaciesList.add(findBy(PHARMACIES_CONTAINER + "[" + i + "]//label").getText());
            }
        }
        String[] actualPharmacies = pharmaciesList.toArray(new String[0]);
        Arrays.sort(actualPharmacies);
        return actualPharmacies;

    }

    public String getFieldValueFromEmailAddressSection(String fieldName) {
        String FIELD = ".//form[@id='updateEmailForm']//label/span[.='" + fieldName + "']/ancestor::div/div/input";
        withTimeoutOf(5, TimeUnit.SECONDS).waitFor(ExpectedConditions.visibilityOfElementLocated(By.xpath(FIELD)));
        return moveTo(FIELD).getValue();
    }

    public List<Address> getProfileAddresses() {
        String ADDRESS_CONTAINER = "(.//ul[@class='account-addressbook-list']/li)";
        List<Address> addresses = new ArrayList<>();
        Address address = new Address();
        String tmp;
        for (int i = 1; i <= findAll(ADDRESS_CONTAINER).size(); i++) {
            tmp = findBy(ADDRESS_CONTAINER + "[" + i + "]").getText();
            try {
                tmp = tmp.substring(0, tmp.indexOf("\nRediger"));
            } catch (Exception e) {
                tmp = tmp.substring(0, tmp.indexOf("\nSett som standard"));
            }

            if (tmp.split("\n").length == 4) {
                address.setFirstName((tmp.split("\n")[0]).split(" ")[0]);
                address.setLastName((tmp.split("\n")[0]).split(" ")[1]);
                address.setAddress1((tmp.split("\n")[1]));
                address.setPostCode((tmp.split("\n")[2]).split(" ")[0]);
                address.setCity((tmp.split("\n")[2]).split(" ")[1]);
                address.setLand((tmp.split("\n")[3]));
            } else {
                address.setFirstName((tmp.split("\n")[0]).split(" ")[0]);
                address.setLastName((tmp.split("\n")[0]).split(" ")[1]);
                address.setAddress1((tmp.split("\n")[1]));
                address.setAddress2((tmp.split("\n")[2]));
                address.setPostCode((tmp.split("\n")[3]).split(" ")[0]);
                address.setCity((tmp.split("\n")[3]).split(" ")[1]);
                address.setLand((tmp.split("\n")[4]));
                address.setPhoneNumber((tmp.split("\n")[5]));
            }
            addresses.add(new Address(address));
        }
        return addresses;
    }

    public void updateProfilePersonalDetails(CustomerProfile newPersonalDetails) {
        findBy(".//form[@id='pearlUpdateProfileForm']//input[@id='profile.firstName']").clear();
        findBy(".//form[@id='pearlUpdateProfileForm']//input[@id='profile.firstName']").sendKeys(newPersonalDetails.getFirstname());
       // scrollIntoView(".//form[@id='pearlUpdateProfileForm']//input[@id='profile.lastName']");
        findBy(".//form[@id='pearlUpdateProfileForm']//input[@id='profile.lastName']").clear();
        findBy(".//form[@id='pearlUpdateProfileForm']//input[@id='profile.lastName']").sendKeys(newPersonalDetails.getLastName());
       // scrollIntoView(".//form[@id='pearlUpdateProfileForm']//input[@id='profile.mobileNumber']");
        findBy(".//form[@id='pearlUpdateProfileForm']//input[@id='profile.mobileNumber']").clear();
        findBy(".//form[@id='pearlUpdateProfileForm']//input[@id='profile.mobileNumber']").sendKeys(newPersonalDetails.getPhoneNumber());
        if ((newPersonalDetails.getIWantTobe4() == true) && ($(".//form[@id='pearlUpdateProfileForm']//input[@id='_hasMembership']").getAttribute("checked") == null))
            findBy(".//form[@id='pearlUpdateProfileForm']//label[@for='_hasMembership']").click();
        if ((newPersonalDetails.getNewsLetterFlag() == true) && ($(".//form[@id='pearlUpdateProfileForm']//input[@id='_consentForNewsletter']").getAttribute("checked") == null))
            findBy(".//form[@id='pearlUpdateProfileForm']//label[@for='_consentForNewsletter']").click();
    }

    public void clickOnSaveButtonProfilePersonalDetails() {
        scrollIntoView(".//form[@id='pearlUpdateProfileForm']//button[contains(text(),'Lagre')]");
        moveTo(".//form[@id='pearlUpdateProfileForm']//button[contains(text(),'Lagre')]").click();
    }

    public void updateProfileEmailTo(String newEmail) {
        withTimeoutOf(5, TimeUnit.SECONDS).waitFor(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//form[@id='updateEmailForm']//input[@id='profile.email']")));
        findBy(".//form[@id='updateEmailForm']//input[@id='profile.email']").clear();
        findBy(".//form[@id='updateEmailForm']//input[@id='profile.email']").sendKeys(newEmail);
        findBy(".//form[@id='updateEmailForm']//input[@id='profile.checkEmail']").clear();
        findBy(".//form[@id='updateEmailForm']//input[@id='profile.checkEmail']").sendKeys(newEmail);
    }

    public void enterPasswordForUpdatingEmail(String password) {
        findBy(".//form[@id='updateEmailForm']//input[@id='profile.pwd']").clear();
        findBy(".//form[@id='updateEmailForm']//input[@id='profile.pwd']").sendKeys(password);
    }

    public void clickOnChangeEmailButtonForUpdatingEmail() {
        moveTo(".//form[@id='updateEmailForm']//button[.='Endre e-postadresse']").click();
    }

    public Integer getAmountOfProfileAddreses(){
        return findAll("(.//ul[@class='account-addressbook-list']/li)").size();
    }

    public void clickOnAddNewAddressButtonForCustomerProfile() {
        withTimeoutOf(5, TimeUnit.SECONDS).waitFor(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//a[contains(text(),'Opprett ny adresse')]")));
        if (getAmountOfProfileAddreses()>1) {
            scrollIntoView(".//ul[@class='account-addressbook-list']/li" + "[" + (getAmountOfProfileAddreses() - 1) + "]");
        }
        moveTo(".//a[contains(text(),'Opprett ny adresse')]").click();
    }

    public void clickOnModifyButtonForCustomerProfileAddress(Address address) {
        List<Address> currentAddresses = new ArrayList<>();
        currentAddresses = getProfileAddresses();
        for (int i = 0; i <= currentAddresses.size(); i++) {
            if (currentAddresses.get(i).toString().equals(address.toString())) {
                if (i>1) {
                    scrollIntoView(".//ul[@class='account-addressbook-list']/li" + "[" + (i - 1) + "]");
                }
                moveTo("(.//a[contains(@href,'edit-address/')])" + "[" + (i + 1) + "]").click();
                break;
            }
        }
    }

    public void clickOnSaveModifiedAddressButton() {
        waitABit(500);
        moveTo(".//form[@id='addressForm']//button[contains(text(),'Lagre adressen')]").click();
    }

    public void clickOnRemoveButtonForCustomerProfileAddress(Address removingAddress) {
        List<Address> currentAddresses = new ArrayList<>();
        currentAddresses = getProfileAddresses();
        for (int i = 0; i < currentAddresses.size(); i++) {
            if (currentAddresses.get(i).toString().equals(removingAddress.toString())) {
                if (i>1) {
                    scrollIntoView(".//ul[@class='account-addressbook-list']/li" + "[" + (i - 1) + "]");
                }
                moveTo("(.//a[@class='removeAddressButton'])" + "[" + (i + 1) + "]");
                waitABit(1000);
                findBy("(.//a[@class='removeAddressButton'])" + "[" + (i + 1) + "]").click();
                withTimeoutOf(10,TimeUnit.SECONDS).waitFor(ExpectedConditions.invisibilityOfElementLocated(By.xpath(".//div[@id='cboxContent']")));
                break;
            }
        }
    }

    public void clickOnButtonOnThePopUp(String buttonName, String popupName) {
        withTimeoutOf(5, TimeUnit.SECONDS).waitFor(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//span[.='" + popupName + "']/ancestor::div[@id='cboxContent']//a[contains(text(),'" + buttonName + "')]")));
        moveTo(".//span[.='" + popupName + "']/ancestor::div[@id='cboxContent']//a[contains(text(),'" + buttonName + "')]").click();
    }

    public void clickOnSetAsDefaultButtonForCustomerProfileAddress(Address newDefaultAddress) {
        List<Address> currentAddresses = new ArrayList<>();
        currentAddresses = getProfileAddresses();
        for (int i = 1; i < currentAddresses.size(); i++) {
            if (currentAddresses.get(i).toString().equals(newDefaultAddress.toString())) {
                if(i>1){
                    scrollIntoView(".//ul[@class='account-addressbook-list']/li" + "[" + (getAmountOfProfileAddreses() - 1) + "]");
                }
                moveTo("(.//a[contains(@href, 'set-default-address/')])" + "[" + i + "]").click();
                break;
            }
        }
    }

    public void enterValueIntoFieldOnUpdatingPassswordForm(String value, String fieldName) {
        String FIELD = ".//form[@id='updatePasswordForm']//label/span[.='" + fieldName + "']/ancestor::div[1]//input";
        withTimeoutOf(5, TimeUnit.SECONDS).waitFor(ExpectedConditions.visibilityOfElementLocated(org.openqa.selenium.By.xpath(FIELD)));
        findBy(FIELD).sendKeys(value);
    }

    public void clickOnToChangeCustomerPassword(String buttonName) {
        String CHANGE_BUTTON = ".//form[@id='updatePasswordForm']//button[.='" + buttonName + "']";
        moveTo(CHANGE_BUTTON).click();
    }

    public OrderHistoryLine searchForOrderInOrderHistory(String orderNumber) {
        OrderHistoryLine orderHistoryline = new OrderHistoryLine();
        String NEXT_BUTTON = ".//a[@rel='next']";
        while (true) {
            try {
                findBy(ORDER_HISTORY_ORDER_LINE.replace("$OrderNumber", orderNumber));
                orderHistoryline.setOrderNumber(findBy(ORDER_HISTORY_ORDER_LINE
                        .replace("$OrderNumber", orderNumber) + "//div[@class='order-id']").getText().replace("Bestilling ",""));
//                orderHistoryline.setOrderDate();
                orderHistoryline.setOrderTotal(Double.valueOf(findBy(ORDER_HISTORY_ORDER_LINE
                        .replace("$OrderNumber", orderNumber) + "//div[@class='price-items']").getText()
                        .replace(",",".").replace("kr ","").replace(" ","").replace("-","")));
                orderHistoryline.setStatus(findBy(ORDER_HISTORY_ORDER_LINE
                        .replace("$OrderNumber", orderNumber) + "//div[@class='status']").getText());
                return orderHistoryline;
            } catch (Exception e) {
                try {
                    $(NEXT_BUTTON).click();
                } catch (Exception ex) {
                    return null;
                }
            }
        }

    }

    public void clickOnDeleteButtonOnDeleteAddressPopUp() {
        withTimeoutOf(5, TimeUnit.SECONDS).waitFor(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//div[@id='colorbox']//div/div/a")));
        findBy(".//div[@id='colorbox']//div/div/a").click();
        withTimeoutOf(5, TimeUnit.SECONDS).waitFor(ExpectedConditions.invisibilityOfElementLocated(By.xpath(".//div[@id='colorbox']//div/div/a")));
    }

    public void uncheckOptionIWantToBeVICustomer() {
        if(findBy(".//input[@id='_hasMembership']").isSelected()){
            findBy(".//input[@id='_hasMembership']").click();
        }
    }

    public void checkOptionIWantToBeVICustomer() {
        if(findBy(".//input[@id='_hasMembership']").isSelected()==false){
            findBy(".//input[@id='_hasMembership']").click();
        }
    }

    public boolean getOptionIWantToBeVICustomerValue() {
        return findBy(".//input[@id='_hasMembership']").isSelected();
    }
}
