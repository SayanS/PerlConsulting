package lloydsPharmaProject.pages;

import lloydsPharmaProject.Locators;
import lloydsPharmaProject.Models.Address;
import net.serenitybdd.core.annotations.findby.By;
import net.serenitybdd.core.pages.WebElementFacade;
import org.apache.commons.collections.list.TreeList;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class MyAccountPage extends BasePage {
    public String MENU_ORDER_HISTORY_ITEM = ".//a[@href='/my-account/orders']";
    public String ORDER_HISTORY_ORDER_LINE = ".//a[@href='/my-account/order/$OrderNumber']";
    public String UPDATE_EMAIL_FORM = ".//form[@id='updateEmailForm']";
    public String ORDER_HISTORY_ORDER_LINK=".//a[@href='/my-account/order/$OrderNumber']//div[@class='order-id']";
    public String ADDRESS_CONTAINERS = "(.//ul[@class='account-addressbook-list']/li)";

    public String getTitleValue(String myAccountPageTitleDropDown) {
        return $(myAccountPageTitleDropDown).getSelectedVisibleTextValue();
    }

    public String[] getFavoritePharmacies() {
        List<String> favoritePharm = new TreeList();
        WebElement form = getDriver().findElement(By.id("pearlUpdateProfileForm"));
        for (WebElement we : form.findElements(By.xpath(Locators.MY_ACCOUNT_PAGE_UPDATE_PERSONAL_DETAILS_SPECIAL_FAVORITE_PHARMACIES))) {
            favoritePharm.add(we.getText());
        }
        String[] expFavoritePharm = favoritePharm.toArray(new String[0]);
        return expFavoritePharm;
    }

    public void clickOnSaveButton() {
        scrollIntoView(Locators.MY_ACCOUNT_PAGE_UPDATE_PERSONAL_DETAILS_SAVE_BUTTON,-50);
        findBy(Locators.MY_ACCOUNT_PAGE_UPDATE_PERSONAL_DETAILS_SAVE_BUTTON).click();
    }

    public void selectOrderHistory() {
        withTimeoutOf(5, TimeUnit.SECONDS).waitFor(ExpectedConditions.presenceOfElementLocated(By.xpath(MENU_ORDER_HISTORY_ITEM)));
        scrollIntoView(MENU_ORDER_HISTORY_ITEM);
        waitABit(2000);
        ((JavascriptExecutor) getDriver()).executeScript("scroll(0,-200);");
        waitABit(2000);
        withTimeoutOf(5, TimeUnit.SECONDS).waitFor(ExpectedConditions.elementToBeClickable(By.xpath(MENU_ORDER_HISTORY_ITEM)));
        clickOnWebElement(MENU_ORDER_HISTORY_ITEM);
    }

    public boolean searchForOrderInOrderHistory(String orderNumber) {
        String NEXT_BUTTON = ".//a[@rel='next']";
        while (true) {
            try {
                withTimeoutOf(10, TimeUnit.SECONDS).waitFor(ExpectedConditions.presenceOfElementLocated(
                        By.xpath(ORDER_HISTORY_ORDER_LINE.replace("$OrderNumber", orderNumber)+"/ancestor::li")));
                return true;
            } catch (Exception e) {
                try {
                    $(NEXT_BUTTON).click();
                    waitABit(1000);
                } catch (Exception ex) {
                    return false;
                }
            }
        }
    }

    public String getTotalOfOrderFromHistoryOrder(String orderNumber) {
        if (searchForOrderInOrderHistory(orderNumber) == true) {
            return findBy(ORDER_HISTORY_ORDER_LINE.replace("$OrderNumber", orderNumber) + "//div[@class='price-items']")
                    .getText().replace("€", "").replace(",",".").replace("-","").replace(" ","");
        }
        return null;
    }

    public String getOrderStatus(String orderNumber){
        if(searchForOrderInOrderHistory(orderNumber)==true){
            scrollIntoView(ORDER_HISTORY_ORDER_LINE.replace("$OrderNumber", orderNumber),-80);
            return findBy(ORDER_HISTORY_ORDER_LINE.replace("$OrderNumber", orderNumber)+"//div[@class='status']").getText();
        }
        return null;
    }

    public void clickOnUpdateEmailButton() {
        withTimeoutOf(10, TimeUnit.SECONDS).waitFor(ExpectedConditions
                .presenceOfElementLocated(By.xpath(Locators.MY_ACCOUNT_PAGE_SIDE_MENU_UPDATE_EMAIL_ITEM)));
        moveTo(Locators.MY_ACCOUNT_PAGE_SIDE_MENU_UPDATE_EMAIL_ITEM).click();
    }

    public void enterTextIntoFieldInUpdateEmailForm(String xpathField, String value) {
        WebElementFacade form;
        form = findBy(UPDATE_EMAIL_FORM);
        form.findBy(xpathField).clear();
        form.findBy(xpathField).sendKeys(value);
    }

    public void clickOnUpDateEmailButton(String xpath) {
        moveTo(UPDATE_EMAIL_FORM + xpath).click();
    }

    public boolean isDisplayedUpdatePersonalDetailsForm() {
        try {
            int i = 0;
            withTimeoutOf(10, TimeUnit.SECONDS).waitFor(ExpectedConditions
                    .visibilityOfAllElementsLocatedBy(By.xpath(".//form[@id='pearlLloydsBeUpdateProfileForm']")));
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    public List<Address> getAllProfileAddresses() {
        List<Address> addresses = new ArrayList<>();
        Address address = new Address();
        for (int i = 1; i <= findAll(ADDRESS_CONTAINERS).size(); i++) {
            scrollIntoView(ADDRESS_CONTAINERS);
            if (($(ADDRESS_CONTAINERS + "[" + i + "]").getText().split("\n")).length == 6) {
                address.setFirstName($(ADDRESS_CONTAINERS + "[" + i + "]").getText().split("\n")[0].split(" ")[0]);
                address.setLastName($(ADDRESS_CONTAINERS + "[" + i + "]").getText().split("\n")[0].split(" ")[1]);
                address.setAddress1($(ADDRESS_CONTAINERS + "[" + i + "]").getText().split("\n")[1]);
                address.setPostCode($(ADDRESS_CONTAINERS + "[" + i + "]").getText().split("\n")[2].split(" ")[0]);
                address.setCity($(ADDRESS_CONTAINERS + "[" + i + "]").getText().split("\n")[2].split(" ")[1]);
                address.setLand($(ADDRESS_CONTAINERS + "[" + i + "]").getText().split("\n")[3]);
                address.setPhoneNumber($(ADDRESS_CONTAINERS + "[" + i + "]").getText().split("\n")[4]);
            }

            if (($(ADDRESS_CONTAINERS + "[" + i + "]").getText().split("\n")).length == 7) {
                address.setFirstName($(ADDRESS_CONTAINERS + "[" + i + "]").getText().split("\n")[0].split(" ")[0]);
                address.setLastName($(ADDRESS_CONTAINERS + "[" + i + "]").getText().split("\n")[0].split(" ")[1]);
                address.setAddress1($(ADDRESS_CONTAINERS + "[" + i + "]").getText().split("\n")[1]);
                address.setAddress2($(ADDRESS_CONTAINERS + "[" + i + "]").getText().split("\n")[2]);
                address.setPostCode($(ADDRESS_CONTAINERS + "[" + i + "]").getText().split("\n")[3].split(" ")[0]);
                address.setCity($(ADDRESS_CONTAINERS + "[" + i + "]").getText().split("\n")[3].split(" ")[1]);
                address.setLand($(ADDRESS_CONTAINERS + "[" + i + "]").getText().split("\n")[4]);
                address.setPhoneNumber($(ADDRESS_CONTAINERS + "[" + i + "]").getText().split("\n")[5]);
            }

            addresses.add(new Address(address));
        }
        return addresses;
    }

    public void selectManageAddressItemFromLeftSideMenu() {
        waitABit(1000);
        withTimeoutOf(10, TimeUnit.SECONDS).waitFor(ExpectedConditions.
                visibilityOfElementLocated(By.xpath(".//a[@href='/my-account/address-book']")));
        moveTo(".//a[@href='/my-account/address-book']").click();
    }

    public void removeAddressFromCustomerProfile(Address address) {
        String POP_UP_REMOVE_ADDRESS_BUTTON = ".//div[@id='colorbox']//a[contains(@href,'remove-address')]";
        String currAddress;
        String defAddress = address.getFirstName() + " " +
                address.getLastName() + ", " +
                address.getAddress1() + " " +
                address.getAddress2() + ", " +
                address.getPostCode() + " " +
                address.getCity() + " , " +
                address.getLand() + ", " +
                address.getPhoneNumber();
        withTimeoutOf(10, TimeUnit.SECONDS).waitFor(ExpectedConditions
                .visibilityOfElementLocated(By.xpath(ADDRESS_CONTAINERS + "[1]")));
        for (int i = 2; i <= findAll(ADDRESS_CONTAINERS).size(); i++) {
            scrollIntoView(ADDRESS_CONTAINERS + "[" + i + "]");
            currAddress = findBy(ADDRESS_CONTAINERS + "[" + i + "]").getText().split("\n")[0] + ", " +
                    findBy(ADDRESS_CONTAINERS + "[" + i + "]").getText().split("\n")[1] + " " +
                    findBy(ADDRESS_CONTAINERS + "[" + i + "]").getText().split("\n")[2] + ", " +
                    findBy(ADDRESS_CONTAINERS + "[" + i + "]").getText().split("\n")[3] + ", " +
                    findBy(ADDRESS_CONTAINERS + "[" + i + "]").getText().split("\n")[4] + ", " +
                    findBy(ADDRESS_CONTAINERS + "[" + i + "]").getText().split("\n")[5];
            if (currAddress.equals(defAddress)) {
                findBy(ADDRESS_CONTAINERS + "[" + i + "]//a[@class='removeAddressButton']").click();
                withTimeoutOf(10, TimeUnit.SECONDS).waitFor(ExpectedConditions
                        .visibilityOfElementLocated(By.xpath(POP_UP_REMOVE_ADDRESS_BUTTON)));
                findBy(POP_UP_REMOVE_ADDRESS_BUTTON).click();
            }
        }
    }

    public void setAddressAsDefaultForCustomerProfile(Address address) {
        String currAddress;
        String defAddress = address.getFirstName() + " " +
                address.getLastName() + ", " +
                address.getAddress1() + ", " +
                address.getAddress2() + ", " +
                address.getPostCode() + " " +
                address.getCity() + " , " +
                address.getLand() + ", " +
                address.getPhoneNumber();
        defAddress = defAddress.replace(", , ", ", ");
        withTimeoutOf(10, TimeUnit.SECONDS).waitFor(ExpectedConditions
                .visibilityOfElementLocated(By.xpath(ADDRESS_CONTAINERS + "[1]")));
        for (int i = 2; i <= findAll(ADDRESS_CONTAINERS).size(); i++) {
            scrollIntoView(ADDRESS_CONTAINERS + "[" + i + "]");
            currAddress = findBy(ADDRESS_CONTAINERS + "[" + i + "]").getText().split("\n")[0] + ", " +
                    findBy(ADDRESS_CONTAINERS + "[" + i + "]").getText().split("\n")[1] + ", " +
                    findBy(ADDRESS_CONTAINERS + "[" + i + "]").getText().split("\n")[2] + ", " +
                    findBy(ADDRESS_CONTAINERS + "[" + i + "]").getText().split("\n")[3] + ", " +
                    findBy(ADDRESS_CONTAINERS + "[" + i + "]").getText().split("\n")[4] + ", " +
                    findBy(ADDRESS_CONTAINERS + "[" + i + "]").getText().split("\n")[5];
            if ((currAddress.replace(", Instellen als standaard wijzigen Verwijderen", "")).equals(defAddress)) {
                findBy(ADDRESS_CONTAINERS + "[" + i + "]//a[contains(@href,'set-default-address')]").click();
                break;
            }
        }
    }

    public void clickOnAddNewAddressButton() {
        String ADD_NEW_ADDRESS_BUTTON = ".//a[@href='add-new-address']";
        withTimeoutOf(10, TimeUnit.SECONDS).waitFor(ExpectedConditions.visibilityOfElementLocated(By.xpath(ADD_NEW_ADDRESS_BUTTON)));
        scrollIntoView("(.//ul[@class='account-addressbook-list']/li)[" + findAll(".//ul[@class='account-addressbook-list']/li").size() + "]");
        waitABit(2000);
        moveTo(ADD_NEW_ADDRESS_BUTTON);
        waitABit(1000);
        findBy(ADD_NEW_ADDRESS_BUTTON).click();
    }

    public void clickOnModifyButtonForCustomerProfileAddress(Address address) {
        withTimeoutOf(10, TimeUnit.SECONDS).waitFor(ExpectedConditions
                .visibilityOfElementLocated(By.xpath(ADDRESS_CONTAINERS + "[1]")));
        for (int i = 1; i <= findAll(ADDRESS_CONTAINERS).size(); i++) {
            scrollIntoView(ADDRESS_CONTAINERS + "[" + i + "]");
            if (findBy(ADDRESS_CONTAINERS + "[" + i + "]").getText().contains(address.getAddress1())) {
                findBy(ADDRESS_CONTAINERS + "[" + i + "]//a[contains(@href,'edit-address')]").click();
                break;
            }
        }
    }

    public void clickOnSaveModifiedAddressButton() {
        String SAVE_BUTTON = ".//form[@id='addressForm']//button[@type='submit']";
        scrollIntoView("(.//ul[@class='account-navigation-list sidebar-navigation-list js-droplist']/li)[3]");
        moveTo(SAVE_BUTTON).click();
    }

    public void selectUpdatePasswordItemFromSideMenuOfMyAccount() {
        withTimeoutOf(5, TimeUnit.SECONDS).waitFor(ExpectedConditions.
                visibilityOfElementLocated(By.xpath(".//a[@href='/my-account/update-password']")));
        findBy(".//a[@href='/my-account/update-password']").click();
    }

    public void enterCurrentPasswordOnUpdatingPasswordForm(String password) {
        withTimeoutOf(5, TimeUnit.SECONDS).waitFor(ExpectedConditions.
                visibilityOfElementLocated(By.xpath(".//*[@id='profile.currentPassword']")));
        findBy(".//*[@id='profile.currentPassword']").sendKeys(password);
    }

    public void enterNewPasswordOnUpdatingPasswordForm(String newPassword) {
        findBy(".//*[@id='new-password']").sendKeys(newPassword);
    }

    public void enterConfirmNewPasswordOnUpdatingPasswordForm(String newPassword) {
        findBy(".//*[@id='profile.checkNewPassword']").sendKeys(newPassword);
    }

    public void clickOnUpdatePasswordButton() {
        findBy(".//*[@id='updatePasswordForm']//button[1]").click();
    }
}
