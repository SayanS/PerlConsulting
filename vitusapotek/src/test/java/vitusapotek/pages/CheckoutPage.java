package vitusapotek.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.ExpectedConditions;
import vitusapotek.models.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class CheckoutPage extends BasePage {

    public void selectShippingMethodSendByEmail() {
        String SEND_BY_EMAIL_OPTION = ".//input[@id='deliveryMethodShipment']/following-sibling::label";
        withTimeoutOf(10, TimeUnit.SECONDS).
                waitFor(ExpectedConditions.visibilityOfElementLocated(By.xpath(SEND_BY_EMAIL_OPTION)));
        $(SEND_BY_EMAIL_OPTION).click();
    }

    public void clickOnSkipToShippingAddressButton() {
        waitABit(3000);
        scrollIntoView("//div[contains(text(),'Leveringsmåte')]");
        moveTo(".//button[@id='deliveryMethodSubmit']");
        withTimeoutOf(5, TimeUnit.SECONDS).waitFor(ExpectedConditions.elementToBeClickable(By.xpath(".//button[@id='deliveryMethodSubmit']")));
        findBy(".//button[@id='deliveryMethodSubmit']").click();
    }

    public void enterCredentialsForGettingAddressFromProfile(String email, String password) {
        withTimeoutOf(10, TimeUnit.SECONDS).
                waitFor(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//input[@id='j_username']")));
        $(".//input[@id='j_username']").sendKeys(email);
        $(".//input[@id='j_password']").sendKeys(password);

    }

    public void clickOnFurtherToTheShippingAndPaymentButton() {
        scrollIntoView(".//button[contains(text(),'Videre til frakt og betaling')]");
        withTimeoutOf(6, TimeUnit.SECONDS).waitFor(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//button[contains(text(),'Videre til frakt og betaling')]")));
        moveTo(".//button[contains(text(),'Videre til frakt og betaling')]");
        withTimeoutOf(4, TimeUnit.SECONDS).waitFor(ExpectedConditions.elementToBeClickable(By.xpath(".//button[contains(text(),'Videre til frakt og betaling')]")));
        clickOnJS((".//button[contains(text(),'Videre til frakt og betaling')]"));
    }

    public void selectDeliveryAddress(String address) {
        String ADDRESSES = "(.//label[contains(@for,'saved-address')])";
        withTimeoutOf(10, TimeUnit.SECONDS).waitFor(ExpectedConditions.visibilityOfElementLocated(By.xpath(ADDRESSES + "[1]")));
        for (int i = 1; i <= findAll(ADDRESSES).size(); i++) {
            scrollIntoView(ADDRESSES + "[" + i + "]");
            if (findBy(ADDRESSES + "[" + i + "]").getText().contains(address)) {
                try {
                    findBy(ADDRESSES + "[" + i + "]").click();
                } catch (Exception e) {
                }
                break;
            }
        }
    }

    public void selectValueFromLandFieldForNewDeliveryAddress(String land) {
        withTimeoutOf(15, TimeUnit.SECONDS).waitFor(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath(".//select[@id='address.country']")));
        scrollIntoView(".//select[@id='address.country']");
        clickOnJS(".//select[@id='address.country']");
        waitABit(1000);
        $(".//select[@id='address.country']").selectByVisibleText(land);
    }

    public void enterIntoFieldForNewDeliveryAddress(String value, String fieldName) {
        scrollIntoView(".//label[contains(text(),'" + fieldName + "')]/ancestor::div[1]/div/input");
        findBy(".//label[contains(text(),'" + fieldName + "')]/ancestor::div[1]/div/input").clear();
        findBy(".//label[contains(text(),'" + fieldName + "')]/ancestor::div[1]/div/input").sendKeys(value);
        waitABit(1000);
    }

    public String getCityFromNewDeliveryAddress() {
        scrollIntoView(".//input[@id='address.townCity']");
        return $(".//input[@id='address.townCity']").getText();
    }

    public void clickOnAddNewAddressButton() {
        scrollIntoView(".//button[@id='new-delivery-address-btn']");
        moveTo(".//button[@id='new-delivery-address-btn']").click();
    }

    public void selectShippingMethodGetInPharmacy() {
        String GET_IN_PHARMACY_OPTION = ".//input[@id='input-pickupInStore']/preceding-sibling::label";
        withTimeoutOf(10, TimeUnit.SECONDS).
                waitFor(ExpectedConditions.visibilityOfElementLocated(By.xpath(GET_IN_PHARMACY_OPTION)));
        $(GET_IN_PHARMACY_OPTION).click();
    }

    public String selectFirstPharmacyWithStatus(String status) {
        String PHARMACY_CONTAINERS = "(.//form[@id='add_to_cart_storepickup_form']//li[contains(@id,'pickup-entry')])";
        Integer index = 1;
        String pharmacyName;
        withTimeoutOf(15, TimeUnit.SECONDS).waitFor(ExpectedConditions.visibilityOfElementLocated(By.xpath(PHARMACY_CONTAINERS + "[1]")));
        while (true) {
            try {
                scrollIntoView(PHARMACY_CONTAINERS + "[" + index + "]");
                waitABit(500);
            } catch (Exception e) {
                return null;
            }
            if ($(PHARMACY_CONTAINERS + "[" + index + "]" + "//div[@class='pickup-store-list-entry-stock-summary']").getText().contains(status)) {
                pharmacyName = $(PHARMACY_CONTAINERS + "[" + index + "]" + "//span[@title='Velg dette apoteket']").getText();
                moveTo(PHARMACY_CONTAINERS + "[" + index + "]" + "//button[.='Velg']").click();
                return pharmacyName;
            }
            index++;
        }
    }

    public String getPharmacyNameFromSelectPharmacySection() {
        withTimeoutOf(20, TimeUnit.SECONDS).waitFor(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//form[@id='pickupInStore']//div[@class='content']/div/div/div[1]")));
        return findBy(".//form[@id='pickupInStore']//div[@class='content']/div/div/div[1]").getText();
    }

    public void enterIntoTheFieldForReminder(String value, String fieldName) {
        $(".//label[contains(text(), '" + fieldName + "')]/following-sibling::div/input").clear();
        $(".//label[contains(text(), '" + fieldName + "')]/following-sibling::div/input").sendKeys(value);
    }

    public void enterAnOrderNotes(String orderNote) {
        scrollIntoView(".//textarea[@id='comment']");
        $(".//textarea[@id='comment']").sendKeys(orderNote);
    }

    public void clickOnNextButtonOnStepInCaseGetInPharmacy() {
        clickOnJS(".//button[@id='pickupInPosOptionsSubmit']");
    }


    public String selectDeliveryMethodForAnOrder(String deliveryMethod) {
        String DELIVERY_METHODS = "(.//form[@id='placeOrderForm']//label[contains(@for,'selectedExtDeliveryModeCode')])";
        String tmp;
        for (int i = 1; i <= findAll(DELIVERY_METHODS).size(); i++) {
            if (findBy(DELIVERY_METHODS + "[" + i + "]").getText().contains(deliveryMethod)) {
                moveTo(DELIVERY_METHODS + "[" + i + "]");
                clickOnJS(DELIVERY_METHODS + "[" + i + "]");
                tmp = findBy(DELIVERY_METHODS + "[" + i + "]").getText();
                tmp = tmp.substring(tmp.indexOf(") - kr ") + 7, tmp.indexOf("\n"));
                return tmp;
            }
        }
        return null;
    }

    public String getAddressFromDeliverySection() {
        withTimeoutOf(10, TimeUnit.SECONDS).waitFor(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//div[@class='address']")));
        return findBy(".//div[@class='address']").getText().split("\n")[0].replace(" ", "  ") + ",  " +
                findBy(".//div[@class='address']").getText().split("\n")[1] + ", " +
                (findBy(".//div[@class='address']").getText().split("\n")[2].replace("  ", " ")).
                        substring(0, findBy(".//div[@class='address']").getText().split("\n")[2].replace("  ", " ").length() - 1);
    }

    public void selectPaymentMethod(String paymentMethod) {
        String PAYMENT_METHOD = ".//p[.='" + paymentMethod + "']/ancestor::label[@for='new-payment-method']";
        scrollIntoView(".//form[@id='placeOrderForm']//h3[contains(text(),'Betalingsmåte')]");
        clickOnJS(PAYMENT_METHOD);
    }

    public void selectPaymentOption(String optionName) {
        scrollIntoView(".//h3[contains(text(),'Betalingsmåte')]");
        waitABit(500);
        findBy("//span[contains(text(),'" + optionName + "')]/ancestor::label[1]").click();
    }

    public void clickOnButtonOnStep3(String buttonName) {
        moveTo(".//button[contains(text(),'" + buttonName + "')]").click();
    }

    public void selectOptionOnStep2(String optionName) {
        scrollIntoView(".//form[@id='guestRegisterForm']//span[.='Jeg vil opprette bruker']/ancestor::label");
        clickOnJS(".//form[@id='guestRegisterForm']//span[.='Jeg vil opprette bruker']/ancestor::label");
    }

    public void enterPasswordAndRepeatPassword(String value) {
        scrollIntoView(".//form[@id='guestRegisterForm']//input[@id='password']");
        findBy(".//form[@id='guestRegisterForm']//input[@id='password']").sendKeys(value);
        scrollIntoView(".//form[@id='guestRegisterForm']//input[@id='guestRegisterForm.checkPwd']");
        findBy(".//form[@id='guestRegisterForm']//input[@id='guestRegisterForm.checkPwd']").sendKeys(value);
    }

    public void clickOnAddNewAddressForDelivery() {
        moveTo(".//form[@id='selectAddressForm']//label[@for='new-address']");
        waitABit(1000);
        findBy(".//form[@id='selectAddressForm']//label[@for='new-address']").click();
        waitABit(1000);
    }

    public void selectOptionSaveAsNewAddress() {
        scrollIntoView(".//form[@id='addressForm']//label[@for='saveAddressInMyAddressBook']");
        findBy(".//form[@id='addressForm']//label[@for='saveAddressInMyAddressBook']").click();
    }

    public void clickOnAddAddressButton() {
        scrollIntoView(".//form[@id='addressForm']//button[@id='new-delivery-address-btn']");
        moveTo(".//form[@id='addressForm']//button[@id='new-delivery-address-btn']");
        waitABit(500);
        findBy(".//form[@id='addressForm']//button[@id='new-delivery-address-btn']").click();
    }

    public String getAdditionalMessageForSendByEmailOption() {
        return findBy(".//*[@id='pickupInStore']//label[@for='deliveryMethodShipment']/span[@ class='red']").getText();
    }

    public void clickOnPaymentLinkNets() {
        moveTo(".//form[@id='placeOrderForm']//label[@for='saveInAccount']").click();
    }

    public void clickOnCreateYourAccountButton() {
        moveTo(".//*[@id='pearlRegisterForm']//button[@type='submit']").click();
    }

    public Product getUnavailableProductsFromWarningPopUp(String itemNumber) {
        String PRODUCT_LINE = "//div[@id='removePickupOnlyModal']//span[contains(text(),'$itemNumber')]/ancestor::tr[@class='product-item']";
        Product product = new Product();
        String xpathProductLine=PRODUCT_LINE.replace("$itemNumber",itemNumber);
        scrollIntoView(xpathProductLine);
        product.setProductName($(xpathProductLine+"//h4[@class='name']").getText());
        product.setItemNumber(itemNumber);
        product.setListPrice(Double.valueOf($(xpathProductLine+"//span[contains(text(),'Listepris')]").getText()
                .replace("Listepris","").replace(",", ".").replace("-", "")));
        product.setQty(Integer.valueOf($(xpathProductLine+"//span[@class='qty']").getText()));
        return product;
    }

    public void clickOnRemoveAllButtonOnWarningPopup() {
        withTimeoutOf(5, TimeUnit.SECONDS).waitFor(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//button[@id='removePickupItemsButton']")));
        scrollIntoView(".//button[@id='removePickupItemsButton']");
        moveTo(".//button[@id='removePickupItemsButton']").click();
    }

    public void clickOnCancelButtonOnWarningPopup() {
        scrollIntoView(".//div[@id='removePickupOnlyModal']//button[.='Avbryt']");
        moveTo(".//div[@id='removePickupOnlyModal']//button[.='Avbryt']").click();
        withTimeoutOf(5, TimeUnit.SECONDS).waitFor(ExpectedConditions.invisibilityOfElementLocated(By.xpath(".//div[@class='modal-content']")));
    }

    public void enterPersonalCodeToVerifyAge(String code) {
        scrollIntoView(".//input[@id='SocialSecurityNumber']");
        findBy(".//input[@id='SocialSecurityNumber']").sendKeys(code);
    }

    public void scrollIntoView(String xpath, int offset_y){
        int y = getDriver().findElement(net.serenitybdd.core.annotations.findby.By.xpath(xpath)).getLocation().getY() + offset_y;
        ((JavascriptExecutor) getDriver()).executeScript("window.scrollTo(0, " + y + ")");
    }

    public void clickOnTermsAndConditionsButtonOnCheckout() {
        String TERMS_BUTTON = ".//a[@class='termsAndConditionsLink']";
        withTimeoutOf(10, TimeUnit.SECONDS).waitFor(ExpectedConditions.presenceOfElementLocated(By.xpath(TERMS_BUTTON)));
        scrollIntoView(TERMS_BUTTON, -50);
        withTimeoutOf(10, TimeUnit.SECONDS).waitFor(ExpectedConditions.elementToBeClickable(By.xpath(TERMS_BUTTON)));
        clickOnJS(TERMS_BUTTON);
    }

    public String getTitleOfTermsAndConditionsPopUp() {
        withTimeoutOf(5, TimeUnit.SECONDS).waitFor(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//div[@ id='cboxContent']//h1")));
        return findBy(".//div[@ id='cboxContent']//h1").getText();
    }

    public List<String> getSubTitlesOfTermsAndConditionsPopUp() {
        String SUBTITLES = "(.//div[@id='cboxLoadedContent']//h2)";
        List<String> subTitles = new ArrayList<>();
        for (int i = 1; i <= findAll(SUBTITLES).size(); i++) {
            scrollIntoView(SUBTITLES + "[" + i + "]");
            subTitles.add(findBy(SUBTITLES + "[" + i + "]").getText().trim());
        }
        return subTitles;
    }

    public void clickOnCloseButtonOfTheTermsAndConditionsPopUp() {
        moveTo(".//div[@ id='cboxContent']//a[contains(text(),'Lukk')]").click();
    }

    public void clickOnShowShoppingCartDropDown() {
        scrollIntoView(".//div[@class='header trigger']");
        findBy(".//div[@class='header trigger']").click();
    }

    public void selectShippingMethodGetAddressFromProfile() {
        withTimeoutOf(10, TimeUnit.SECONDS).waitFor(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//input[@id='checkoutLoginTypeLogin']")));
        findBy(".//input[@id='checkoutLoginTypeLogin']").click();
    }

    public void selectDeliveryAddressContinueWithoutProfile() {
        withTimeoutOf(10, TimeUnit.SECONDS).waitFor(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//input[@id='checkoutLoginTypeGuest']")));
        scrollIntoView(".//div[contains(text(),'Leveringsadresse')]");
        findBy(".//input[@id='checkoutLoginTypeGuest']").click();
    }
}
