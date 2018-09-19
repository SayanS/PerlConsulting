package lloydsPharmaProject.pages;

import lloydsPharmaProject.Locators;
import lloydsPharmaProject.Models.Address;
import lloydsPharmaProject.Models.Product;
import net.serenitybdd.core.pages.WebElementFacade;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Checkout extends BasePage {
    private String IFRAME_DELIVERY_METHOD = "//iframe[contains(@id, 'shm-if')]";
    private String IFRAME_DELIVERY_METHOD_CONTAINER = "//*[@id='body-content']//*[contains(text(), '$Method')]";
    private String IFRAME_DELIVERY_DELIVERY_PLACE_FIELD = ".//label[.='$FieldName']/following-sibling::div/input";
    private String IFRAME_DELIVERY_DELIVERY_PLACE_NEXT_BUTTON = ".//button[@id='wizardButtonNext']";

    private String CHECKOUT_DELIVERY_ADDRESSES = ".//label[contains(@for,'saved-address')]";
    public String CHECKOUT_DELIVERY_ADDRESS_WITHOUT_PROFILE = ".//*[@id='checkoutLoginTypeGuest']/ancestor::div[1]/label";
    private String CHECKOUT_STEP_2_FURTHER_BUTTON = ".//button[@id='select-address-btn']";

    private String MAP_TAG_PHARMACY = "//*[@id='pickup-map']//*[@title='Willemsplein']/img";

    public void clickOnNextOnFirstStep() {
        withTimeoutOf(10, TimeUnit.SECONDS).waitFor(ExpectedConditions.visibilityOfElementLocated(By.xpath(Locators.CHECKOUT_NEXT_BUTTON)));
        moveTo(Locators.CHECKOUT_NEXT_BUTTON);
        $(Locators.CHECKOUT_NEXT_BUTTON).click();
    }

    public void selectOptionGetAddressFromYourProfile() {
        moveTo(Locators.CHECKOUT_GET_ADDRESS_FROM_PROFILE_CHECKBOX);
        if (getAttributeValueOf(".//input[@id='checkoutLoginTypeLogin']", "checked").equals("checked") != true) {
            $(Locators.CHECKOUT_GET_ADDRESS_FROM_PROFILE_CHECKBOX).click();
        }
    }

    public void selectDeliveryMethodInIframe(String method) {
        withTimeoutOf(100, TimeUnit.SECONDS).waitFor(ExpectedConditions.presenceOfElementLocated(By.xpath(IFRAME_DELIVERY_METHOD)));
        WebElement iframeElement = $(IFRAME_DELIVERY_METHOD);
        getDriver().switchTo().frame(iframeElement);
        withTimeoutOf(100, TimeUnit.SECONDS).
                waitFor(ExpectedConditions.elementToBeClickable(By.xpath(IFRAME_DELIVERY_METHOD_CONTAINER.replace("$Method", method))));
        $(IFRAME_DELIVERY_METHOD_CONTAINER.replace("$Method", method)).click();
    }

    public void selectDeliveryAddress(String address) {
        List<WebElementFacade> addresses = new ArrayList<>();
        addresses = findAll(CHECKOUT_DELIVERY_ADDRESSES);
        for (int i = 0; i < addresses.size(); i++) {
            if (addresses.get(i).getText().contains(address)) {
                scrollIntoView("(" + CHECKOUT_DELIVERY_ADDRESSES + ")[" + (i + 1) + "]");
                moveTo("(" + CHECKOUT_DELIVERY_ADDRESSES + ")[" + (i + 1) + "]");
                try {
                    if ($("(" + CHECKOUT_DELIVERY_ADDRESSES + "/input)[" + (i + 1) + "]").getAttribute("checked") != "checked") {
                        $("(" + CHECKOUT_DELIVERY_ADDRESSES + ")[" + (i + 1) + "]").click();
                    }
                } catch (Exception e) {
                }
            }
        }
    }

    public void clickOnFurtherButtonOnStep2() {
        scrollIntoView(CHECKOUT_STEP_2_FURTHER_BUTTON);
        withTimeoutOf(10, TimeUnit.SECONDS).waitFor(ExpectedConditions.elementToBeClickable(By.xpath(CHECKOUT_STEP_2_FURTHER_BUTTON)));
        moveTo(CHECKOUT_STEP_2_FURTHER_BUTTON);
        waitABit(1000);
        findBy(CHECKOUT_STEP_2_FURTHER_BUTTON).click();
    }

    public void enterValueIntoTheFieldOnDeliveryPlaceStep(String value, String fieldName) {
        withTimeoutOf(10, TimeUnit.SECONDS).waitFor(ExpectedConditions
                .visibilityOfElementLocated(By.xpath(IFRAME_DELIVERY_DELIVERY_PLACE_FIELD.replace("$FieldName", fieldName))));
        $(IFRAME_DELIVERY_DELIVERY_PLACE_FIELD.replace("$FieldName", fieldName)).sendKeys(value);
    }

    public void clickOnNextButtonOnDeliveryPlaceStep() {
        withTimeoutOf(10, TimeUnit.SECONDS).waitFor(ExpectedConditions.presenceOfElementLocated(By.xpath(IFRAME_DELIVERY_DELIVERY_PLACE_NEXT_BUTTON)));
        clickOnWebElement(IFRAME_DELIVERY_DELIVERY_PLACE_NEXT_BUTTON);
    }

    public void selectTitleForProfileDetailsForDeliveryAddress(String title, String language) throws IOException {
        withTimeoutOf(5, TimeUnit.SECONDS).waitFor(ExpectedConditions.elementToBeClickable(By.xpath(".//select[@id='profile.title']")));
        $(".//select[@id='profile.title']").click();
        $(".//select[@id='profile.title']").selectByVisibleText(getTranslatedMessage(title, language, DICTIONARY_CHECKOUT));
    }

    public boolean isCartEmpty(String language) throws IOException {
        String translatedMessage = getTranslation("Your basket is empty", language, DICTIONARY_CHECKOUT);
        return $(".//h2[.='" + translatedMessage + "']").isDisplayed();
    }

    public void enterValueIntoDeliveryAdressField(String value, String fieldName, String language) throws IOException {
        String translatedFieldName = getTranslation(fieldName, language, DICTIONARY_CHECKOUT);
        scrollIntoView(".//*[@placeholder='" + translatedFieldName + "']");
        $(".//*[@placeholder='" + translatedFieldName + "']").sendKeys(value);
    }

    public void clickOnPlaceOrderButton() {
        scrollIntoView(".//*[@id='placeOrderSubmit']");
        moveTo(".//*[@id='placeOrderSubmit']").click();
    }

    public void clickOnNextButtonOnDeliveryOptionStep() {
        try {
            withTimeoutOf(3, TimeUnit.SECONDS)
                    .waitFor(ExpectedConditions.visibilityOfElementLocated(By.xpath(IFRAME_DELIVERY_DELIVERY_PLACE_NEXT_BUTTON)));
            moveTo(IFRAME_DELIVERY_DELIVERY_PLACE_NEXT_BUTTON).click();
        } catch (Exception e) {
        }
    }

    public void selectShippingMethodGetInPharmacyOrDeliveryLocation() {
        moveTo(".//label[@for='input-pickupInStore']");
        waitABit(1000);
        findBy(".//label[@for='input-pickupInStore']").click();
        withTimeoutOf(10, TimeUnit.SECONDS).waitFor(ExpectedConditions.presenceOfElementLocated(By.xpath(".//input[@id='locationForSearch']")));
    }

    public void searchDesiredPharmacyForDeliveryLocation(String pharmacyName) {
        $(".//input[@id='locationForSearch']").sendKeys(pharmacyName);
        waitABit(1000);
        findBy(".//*[@id='pickupstore_location_search_button']").click();
        withTimeoutOf(5, TimeUnit.SECONDS).
                waitFor(ExpectedConditions.visibilityOfElementLocated(By.xpath("((.//span[contains(@class,'pickup-store-list-entry-name')]/ancestor::div[1]))[1]/span")));
    }

    public String scrollToDesiredPharmacyInSearchResults(String pharmacyName) {
        String PHARMACY_CONTAINER = "(.//span[contains(@class,'pickup-store-list-entry-name')]/ancestor::div[1])";
        String PHARMACY_NAME = "(.//span[contains(@class,'pickup-store-list-entry-name')])";
        Integer index = 0;
        List<WebElementFacade> pharmacyContainers = new ArrayList<>();
        withTimeoutOf(10, TimeUnit.SECONDS).waitFor(ExpectedConditions.presenceOfElementLocated(By.xpath("(.//ul[contains(@class,'pickup-store-list')])[2]")));
        getDriver().switchTo().activeElement();
        while (index < 1000) {
            index++;
            scrollIntoView(PHARMACY_CONTAINER + "[" + index + "]");
            waitABit(1000);
            withTimeoutOf(3, TimeUnit.SECONDS).waitFor(ExpectedConditions.visibilityOfElementLocated(By.xpath(PHARMACY_CONTAINER + "[" + index + "]")));
            if (findBy(PHARMACY_NAME + "[" + index + "]").getText().contains(pharmacyName)) {
                return PHARMACY_CONTAINER + "[" + index + "]";
            }
        }
        return null;
    }

    public Boolean selectFirstDesiredPharmacyInSearchResultsWithStatus(String status, String language) throws IOException {
        String PHARMACY_CONTAINER = "(.//form[@id='add_to_cart_storepickup_form']//li[contains(@id,'pickup-entry')])";
        String PHARMACY_STATUS = "//div[@class='pickup-store-list-entry-stock-summary']";
        String translatedStatus = getTranslation(status, language, DICTIONARY_CHECKOUT);
        Integer index = 0;
        List<WebElementFacade> pharmacyContainers = new ArrayList<>();
        withTimeoutOf(10, TimeUnit.SECONDS).waitFor(ExpectedConditions.presenceOfElementLocated(By.xpath("(.//ul[contains(@class,'pickup-store-list')])[2]")));
        getDriver().switchTo().activeElement();
        while (index < 1000) {
            index++;
            scrollIntoView(PHARMACY_CONTAINER + "[" + index + "]");
            if (findBy(PHARMACY_CONTAINER + "[" + index + "]" + PHARMACY_STATUS).getText().contains(status)) {
                moveTo(PHARMACY_CONTAINER + "[" + index + "]//button").click();
                return true;
            }
        }
        return false;
    }

    public Address selectDesiredPharmacyInSearchResults(String pharmacyName) {
        String pharmacyContainerXpath;
        Address pharmacyAddress=new Address();
        String address;
        pharmacyContainerXpath = scrollToDesiredPharmacyInSearchResults(pharmacyName);

        address=$(".//span[.='Eeklo']/following-sibling::div//a").getText();
        pharmacyAddress.setFirstName(address.split(", ")[1].split(" ")[1]);
        pharmacyAddress.setAddress1(address.split(", ")[0]);
        pharmacyAddress.setPostCode(address.split(", ")[1].split(" ")[0]);
        pharmacyAddress.setCity(address.split(", ")[1].split(" ")[1]);

        moveTo(pharmacyContainerXpath + "//button[contains(@class,'btn-choose-store')]");
        waitABit(3000);
        findBy(pharmacyContainerXpath + "//button[contains(@class,'btn-choose-store')]").click();
        return pharmacyAddress;
    }

    public void clickOnNearestPharmacyButton() {
        moveTo(".//button[contains(@class,'btn-find-closest')]").click();
    }

    public void selectMapViewDesiredPharmacy() {
        moveTo(".//a[contains(@class,'pickup-map-tab')]").click();
    }

    public String getSelectedPharmacyForShippingMethod() {
        return $("//div[@class='content']/h4/following-sibling::div/div/div[1]").getText();
    }

    public void enterIntoTheFieldForReminder(String value, String fieldName) {
        scrollIntoView(".//label[@for='deliveryMethodShipment']");
        $(".//label[contains(text(), '" + fieldName + "')]/following-sibling::div/input").sendKeys(value);
    }

    public String getValueOfFieldForReminder(String fieldName) {
        scrollIntoView(".//label[@for='deliveryMethodShipment']");
        return $(".//label[contains(text(), '" + fieldName + "')]/following-sibling::div/input").getValue();
    }

    public void checkNotificationType(String notificationType) {
        String checked;
        scrollIntoView(".//*[@id='placeOrderForm']/h4[4]");
        waitABit(1000);
        checked = $(".//form[@id='placeOrderForm']//input[@value='" + notificationType + "']").getAttribute("checked");
        if (checked == null) {
            clickOn(".//form[@id='placeOrderForm']//input[@value='" + notificationType + "']",10,-70);
        }
    }

    public void enterAnOrderNotes(String notes) {
        scrollIntoView(".//button[@id='pickupInPosOptionsSubmit']");
        $(".//textarea[@id='comment']").sendKeys(notes);
    }

    public void clickOnNextButtonOnStep1InCaseGetInPharmacy() {
        clickOnWebElement(".//button[@id='pickupInPosOptionsSubmit']");
    }

    public void clickOnAddNewDeliveryAddressButton() {
        withTimeoutOf(5, TimeUnit.SECONDS)
                .waitFor(ExpectedConditions.presenceOfElementLocated(By.xpath(".//form[@id='selectAddressForm']//label[@for='new-address']")));
        scrollIntoView(".//form[@id='selectAddressForm']//label[@for='new-address']");
        waitABit(1000);
//        moveTo(".//form[@id='selectAddressForm']//label[@for='new-address']");
        withTimeoutOf(5, TimeUnit.SECONDS)
                .waitFor(ExpectedConditions.elementToBeClickable(By.xpath(".//form[@id='selectAddressForm']//label[@for='new-address']")));
        moveTo(".//form[@id='selectAddressForm']//label[@for='new-address']").click();
        waitABit(1000);
    }

    public void selectLandForNewDeliveryAddress(String land) {
        withTimeoutOf(15, TimeUnit.SECONDS).waitFor(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath(".//select[@id='address.country']")));
        scrollIntoView(".//select[@id='address.country']");
        clickOnWebElement(".//select[@id='address.country']");
        waitABit(1000);
        $(".//select[@id='address.country']").selectByVisibleText(land);

    }

    public void enterFieldValueOfNewDeliveryAddress(String value, String fieldName, String language) throws IOException {
        String translatedFieldName = getTranslation(fieldName, language, DICTIONARY_CHECKOUT);
        String xpathFieldContainer = "(.//form[@id='addressForm']//div[contains(@class,'form-group')])";
        waitABit(1000);
        for (int i = 1; i <= findAll(xpathFieldContainer).size(); i++) {
            if ($(xpathFieldContainer + "[" + i + "]/label").getText().contains(translatedFieldName)) {
                $(xpathFieldContainer + "[" + i + "]//input").clear();
                $(xpathFieldContainer + "[" + i + "]//input").sendKeys(value);
                break;
            }
        }
    }

    public void clickOnSaveAddressInMyAddressBookCheckbox() {
        scrollIntoView(".//label[@for='address.phone']");
        withTimeoutOf(10, TimeUnit.SECONDS).
                waitFor(ExpectedConditions.elementToBeClickable(By.xpath(".//label[@for='saveAddressInMyAddressBook']")));
        moveTo(".//label[@for='saveAddressInMyAddressBook']").click();
    }

    public String getOrderNumber() {
        return $(".//span[@class='cartId']").getText();
    }

    public String getOrderTotal() {
        String TOTAL_CART = ".//table[@class='cart-totals table']/tbody[2]/tr/td[2]";
        return $(TOTAL_CART).getText();
    }

    public void clickOnMapViewToSelectPharmacy() {
        String MAP_VIEW_BUTTON = ".//form[@id='add_to_cart_storepickup_form']//ul/li[2]/a";
        moveTo(MAP_VIEW_BUTTON).click();
    }

    public void clickOnAddNewAddressButton() {
        String ADD_NEW_ADDRESS_BUTTON = ".//form[@id='addressForm']//button[@id='new-delivery-address-btn']";
        try {
            withTimeoutOf(5, TimeUnit.SECONDS).waitFor(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//div[@class='global-alerts']//button")));
            findBy(".//div[@class='global-alerts']//button").click();
        } catch (Exception e) {
        }
        scrollIntoView(ADD_NEW_ADDRESS_BUTTON);
        waitABit(1000);
        moveTo(ADD_NEW_ADDRESS_BUTTON).click();
    }

    public List<String> getAllDeliveryAddressAvailableToChoose() {
        List<String> addresses = new ArrayList<>();
        for (int i = 1; i <= findAll(CHECKOUT_DELIVERY_ADDRESSES).size(); i++) {
            scrollIntoView("(" + CHECKOUT_DELIVERY_ADDRESSES + ")[" + i + "]");
            addresses.add(findBy("(" + CHECKOUT_DELIVERY_ADDRESSES + ")[" + i + "]").getText());
        }
        return addresses;
    }

    public void selectCreateNewUserOption() {
        String CREATE_NEW_USER_OPTION = ".//form[@id='guestRegisterForm']//label[@for='guestRegisterForm.createAccount']";
        scrollIntoView(CREATE_NEW_USER_OPTION);
        moveTo(CREATE_NEW_USER_OPTION).click();
    }

    public void enterPasswordAndConfirmation(String password) {
        String PASSWORD = ".//form[@id='guestRegisterForm']//input[@id='password']";
        String CONFIRMATION = ".//form[@id='guestRegisterForm']//input[@id='guestRegisterForm.checkPwd']";
        withTimeoutOf(10, TimeUnit.SECONDS).waitFor(ExpectedConditions.presenceOfElementLocated(By.xpath(PASSWORD)));
        scrollIntoView(PASSWORD);
        findBy(PASSWORD).sendKeys(password);
        scrollIntoView(CONFIRMATION);
        findBy(CONFIRMATION).sendKeys(password);
    }

    public void clickOnBPOSTButton() {
        String BPOST_BUTTON = "(.//form[@id='placeOrderForm']//a)[1]";
        withTimeoutOf(10, TimeUnit.SECONDS).waitFor(ExpectedConditions.presenceOfElementLocated(By.xpath(BPOST_BUTTON)));
        scrollIntoView(BPOST_BUTTON);
        moveTo(BPOST_BUTTON);
        clickOnWebElement(BPOST_BUTTON);
    }

    public void selectPaymentMethod(String paymentMethod) {
        withTimeoutOf(25, TimeUnit.SECONDS).waitFor(ExpectedConditions.invisibilityOfElementLocated(By.xpath(IFRAME_DELIVERY_METHOD)));
        try {
            moveTo(".//form[@id='placeOrderForm']//label[@for='new-payment-method']").click();
        } catch (Exception e) {
        }
    }

    public void enterAnOrderNotesForGuestCheckout(String notes) {
        scrollIntoView(".//textarea[@id='comment']");
        $(".//textarea[@id='comment']").sendKeys(notes);
    }

    public Address getAddressFromShipToSection() {
        String SHIP_TO_ADDRESS = ".//div[@class='address js-bpost-delivery-method']/div[@class='address']";
        String tmp;
        Address address = new Address();
        waitABit(2000);
        withTimeoutOf(30, TimeUnit.SECONDS).waitFor(ExpectedConditions.visibilityOfElementLocated(By.xpath(SHIP_TO_ADDRESS)));
        tmp = $(SHIP_TO_ADDRESS).getText();
        address.setFirstName(tmp.split("\n")[0].split(" ")[0]);
        address.setLastName(tmp.split("\n")[0].split(" ")[1]);
        address.setAddress1(tmp.split("\n")[1].replace("  ", " "));
        address.setPostCode(tmp.split("\n")[2].split(" ")[0]);
        address.setCity(tmp.split("\n")[2].split("  ")[1].replaceAll(" ", "").trim());
        return address;
    }

    public boolean isButtonDisplayedInOrderSummary(String buttnonName) {
        return $(".//ul[@ class='checkout-order-summary-list js-bpost-chosen-method']/li//a").isVisible();
    }

    public String isTitleShipToDisplayed() {
        withTimeoutOf(60, TimeUnit.SECONDS).waitFor(ExpectedConditions.
                visibilityOfElementLocated(By.xpath(".//ul[@ class='checkout-order-summary-list js-bpost-chosen-method']/li//h4")));
        return $(".//ul[@ class='checkout-order-summary-list js-bpost-chosen-method']/li//h4").getText();
    }

    public String getDeliveryMethod() {
        return $(".//form[@id='placeOrderForm']//span[@id='js-bpost-delivery-name']").getText();
    }

    public void selectStepOfCheckout(String stepName, String language) throws IOException {
        String STEP = ".//div[@class='title']";
        String tanslatedStepName = getTranslation(stepName, language, DICTIONARY_CHECKOUT);
        withTimeoutOf(5, TimeUnit.SECONDS).waitFor(ExpectedConditions.presenceOfElementLocated(By.xpath("(" + STEP + ")[1]")));
        for (int i = 1; i <= findAll(STEP).size(); i++) {
            scrollIntoView("(" + STEP + ")[" + i + "]");
            waitABit(1000);
            ((JavascriptExecutor) getDriver()).executeScript("scroll(0,-100);");
            waitABit(1000);
            if ($("(" + STEP + ")[" + i + "]").getText().contains(tanslatedStepName)) {
                $("(" + STEP + "/ancestor::a)[" + i + "]").click();
                break;
            }
        }
    }

    public String getOrderTotalWithPriceDeliveryBpost() {
        String DELIVERY_PRICE_CONTAINER = ".//div[@id='shopping-cart-title']";
        withTimeoutOf(15, TimeUnit.SECONDS).waitFor(ExpectedConditions.visibilityOfElementLocated(By.xpath(DELIVERY_PRICE_CONTAINER)));
        findBy(DELIVERY_PRICE_CONTAINER).click();
        return findBy(".//div[@id='shopping-cart']/div[5]/div[2]").getText().replace(" € ", "");
    }

    public Double getOrderDeliveryValueFromBpost() {
        String DELIVERY_PRICE_CONTAINER = ".//div[@id='shopping-cart-title']";
        withTimeoutOf(5, TimeUnit.SECONDS).waitFor(ExpectedConditions.visibilityOfElementLocated(By.xpath(DELIVERY_PRICE_CONTAINER)));
        findBy(DELIVERY_PRICE_CONTAINER).click();
        return Double.valueOf(findBy(".//div[@id='shopping-cart']/div[3]/div[2]").getText()
                .replace(",", ".").replace("-", "00").replace("€", "").replace(" ", ""));
    }


    public void clickOnTermsAndConditionsButtonOnCheckout() {
        String TERMS_BUTTON = ".//a[@class='lloydsBeTermsAndConditionsLink']";
        withTimeoutOf(5, TimeUnit.SECONDS).waitFor(ExpectedConditions.presenceOfElementLocated(By.xpath(TERMS_BUTTON)));
        scrollIntoView(TERMS_BUTTON);
        moveTo(TERMS_BUTTON);
        withTimeoutOf(15, TimeUnit.SECONDS).waitFor(ExpectedConditions.visibilityOfElementLocated(By.xpath(TERMS_BUTTON)));
        clickOnWebElement(TERMS_BUTTON);
    }

    public String getTitleOfTermsAndConditionsPopUp() {
        withTimeoutOf(5, TimeUnit.SECONDS).waitFor(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//div[@id='cboxContent']//div[@class='content']")));
        return findBy("(.//div[@id='cboxContent']//div[@class='content']/p)[1]").getText().trim();
    }

    public Boolean isTermsAndConditionsPopUpContains(String expectedSubTitle) {
        try {
            scrollIntoView(".//div[@class='content']//b[.='" + expectedSubTitle + "']");
            findBy(".//div[@class='content']//b[.='" + expectedSubTitle + "']").getText();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void clickOnCloseButtonOfTheTermsAndConditionsPopUp() {
        scrollIntoView(".//a[@class='btn btn-primary btn-block']");
        moveTo(".//a[@class='btn btn-primary btn-block']").click();
    }

    public void clickOnShowCheckoutCartButton() {
        withTimeoutOf(5, TimeUnit.SECONDS).waitFor(ExpectedConditions.presenceOfElementLocated(By.xpath(".//div[@class='checkout-cart bg-white border js-droplist']")));
        scrollIntoView(".//div[@class='checkout-cart bg-white border js-droplist']");
        waitABit(500);
        moveTo(".//div[@class='checkout-cart bg-white border js-droplist']").click();
    }

    public List<Product> getCheckoutCartProducts() {
        String SHOPPING_CART_PRODUCT_CONTAINER = "(.//table/tbody/tr[@class='product-item'])";
        List<Product> products = new ArrayList<>();
        Product product = new Product();
        withTimeoutOf(5, TimeUnit.SECONDS).waitFor(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath(SHOPPING_CART_PRODUCT_CONTAINER + "/td[1]//a/h4")));
        for (int i = 1; i <= findAll(SHOPPING_CART_PRODUCT_CONTAINER).size(); i++) {
            scrollIntoView(SHOPPING_CART_PRODUCT_CONTAINER + "[" + i + "]");
            product.setProductName(findBy("(" + SHOPPING_CART_PRODUCT_CONTAINER + ")[" + i + "]/td[1]//a/h4").getText());
            product.setQty(Integer.valueOf(
                    findBy(SHOPPING_CART_PRODUCT_CONTAINER + "[" + i + "]/td[3]//span[@class='qty']").getText()));
            product.setPrice(findBy(SHOPPING_CART_PRODUCT_CONTAINER + "[" + i + "]//td[1]//*[contains(text(),'€')]").getText()
                    .replace("Prix de l'article", "").replace("artikelprijs ", "").replace(" ",""));
            if (findBy("(" + SHOPPING_CART_PRODUCT_CONTAINER + ")[" + i + "]/td[2]/span[1]").getAttribute("class")
                    .equals("stock streamline-ok success")) {
                product.setStatusNet(true);
            } else product.setStatusNet(false);
            product.setStatusApotek(true);

            products.add(product);
        }
        return products;
    }

    public Double getValueFromOrderSummary(String translatedFieldName) {
        withTimeoutOf(60, TimeUnit.SECONDS).waitFor(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//div[contains(text(),'" + translatedFieldName + "')]/span")));
        waitABit(5000);
        return Double.valueOf(findBy(".//div[contains(text(),'" + translatedFieldName + "')]/span").getText().replace(",", ".").replace("-", "00").replace("€", "").replace(" ", ""));
    }

    public void expandBottomCheckoutShoppingCart() {
        withTimeoutOf(10, TimeUnit.SECONDS).waitFor(ExpectedConditions.presenceOfElementLocated(By.xpath(".//div[@class='checkout-cart bg-white border js-droplist']")));
        waitABit(1000);
        scrollIntoView(".//div[@class='checkout-cart bg-white border js-droplist']",-50);
        withTimeoutOf(5, TimeUnit.SECONDS).waitFor(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//div[@class='checkout-cart bg-white border js-droplist']")));
        try {
            moveTo(".//div[@class='checkout-cart bg-white border js-droplist']");
            waitABit(500);
            $(".//div[@class='checkout-cart bg-white border js-droplist']").click();
        }catch (Exception e){
            scrollIntoView(".//div[@class='checkout-cart bg-white border js-droplist']");
            moveTo(".//div[@class='checkout-cart bg-white border js-droplist']");
            waitABit(500);
            $(".//div[@class='checkout-cart bg-white border js-droplist']").click();
        }
    }

    public Double getValueFromCheckoutOrderSummary(String fieldName) {
        for (int i = 1; i <= findAll(".//div[contains(@class,'subtotals')]/div").size(); i++) {
            if ($("(.//div[contains(@class,'subtotals')]/div)[" + i + "]").getText().contains(fieldName)) {
                scrollIntoView("(.//div[contains(@class,'subtotals')]/div)[" + i + "]//span",-50);
                return Double.valueOf($("(.//div[contains(@class,'subtotals')]/div)[" + i + "]//span").getText()
                        .replace(",", ".").replace("-", "00").replace("€", "").replace(" ", ""));
            }
        }
        return null;
    }

    public void clickOnPasswordRecoveryButton() {
        withTimeoutOf(5,TimeUnit.SECONDS).waitFor(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//a[@href='/login/pw/request']")));
        moveTo(".//a[@href='/login/pw/request']");
        waitABit(500);
        clickOnWebElement(".//a[@href='/login/pw/request']");
    }

    public void enterIntoRestorePasswordField(String email) {
        withTimeoutOf(5,TimeUnit.SECONDS).waitFor(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//*[@id='forgottenPwd.email']")));
        $(".//*[@id='forgottenPwd.email']").sendKeys(email);
    }

    public void clickOnSendEmailButtonToRestorePassword() {
        $(".//*[@id='forgottenPwdForm']/button").click();
    }

    public String getMessageDisplayedInRestorePasswordPopUp() {
        try{
            waitABit(1000);
            return $(".//*[@id='cboxLoadedContent']/div").getText();
        }catch (Exception e){
            return null;
        }
    }

    public void closeRestorePasswordPopUp() {
        $(".//*[@id='cboxClose']").click();
    }

    public boolean isDisplayedUpdatePasswordForm() {
        try {
            withTimeoutOf(5,TimeUnit.SECONDS).waitFor(ExpectedConditions.visibilityOfElementLocated(By.xpath("//form[@id='pearlUpdatePwdForm']")));
            return true;
        }catch(Exception e){
            return false;
        }
    }

    public void enterIntoNewPasswordField(String newPassword) {
        findBy(".//*[@id='updatePwd.pwd']").sendKeys(newPassword);
    }

    public void enterIntoConfirmationPasswordField(String newPassword) {
        findBy(".//*[@id='updatePwd.checkPwd']").sendKeys(newPassword);
    }

    public  void clickonSubmitUpdatedPassword(){
        moveTo(".//form[@id='pearlUpdatePwdForm']/button").click();
    }

    public String getAdditionalMessageForSendToAddressOption() {
        return  findBy(".//*[@id='pickupInStore']//label[@for='deliveryMethodShipment']/span[@ class='red']").getText();
    }

    public Product getUnavailableProductsFromWarningPopUp(String productCode, String currentLanguage) throws IOException {
        String PRODUCT_LINE = "//div[@id='removePickupOnlyModal']//span[contains(text(),'$itemNumber')]/ancestor::tr[@class='product-item']";
        Product product = new Product();
        String priceLabel=getTranslation("Price item",currentLanguage,DICTIONARY_CHECKOUT);
        String xpathProductLine=PRODUCT_LINE.replace("$itemNumber",productCode);
        scrollIntoView(xpathProductLine);
        product.setProductName($(xpathProductLine+"//h4[@class='name']").getText());
        product.setProductCode(productCode);
        product.setPrice($(xpathProductLine+"//span[contains(text(),'"+priceLabel+"')]").getText()
        .replace("artikelprijs","").replace("Prix \u200B\u200Bde l'article",""));
        product.setQty(Integer.valueOf($(xpathProductLine+"//span[@class='qty']").getText()));
        return product;
    }

    public void clickOnCancelButtonOnWarningPopup(String currentLanguage) throws IOException {
        String translatedButtonName=getTranslation("cancel",currentLanguage, DICTIONARY_CHECKOUT);
        scrollIntoView(".//div[@id='removePickupOnlyModal']//button[.='"+translatedButtonName+"']");
        moveTo(".//div[@id='removePickupOnlyModal']//button[.='"+translatedButtonName+"']").click();
        withTimeoutOf(5, TimeUnit.SECONDS).waitFor(ExpectedConditions.invisibilityOfElementLocated(By.xpath(".//div[@class='modal-content']")));
    }

    public void clickOnRemoveAllButtonOnWarningPopup() {
        withTimeoutOf(5, TimeUnit.SECONDS).waitFor(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//button[@id='removePickupItemsButton']")));
        scrollIntoView(".//button[@id='removePickupItemsButton']");
        moveTo(".//button[@id='removePickupItemsButton']").click();
    }


}
