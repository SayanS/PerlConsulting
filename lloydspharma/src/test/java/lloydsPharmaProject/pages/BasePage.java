package lloydsPharmaProject.pages;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lloydsPharmaProject.Locators;
import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.pages.WebElementFacade;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;


public class BasePage extends PageObject {
    public String DICTIONARY_PATH = "./src/test/resources/dictionary/base_home.json";
    public String DICTIONARY_PRODUCTS = "./src/test/resources/dictionary/products.json";
    public String DICTIONARY_CHECKOUT = "./src/test/resources/dictionary/checkout.json";
    public String DICTIONARY_LOGIN_PROFILE = "./src/test/resources/dictionary/login_profile.json";
    public String DICTIONARY_MY_PRESCRIPTION = "./src/test/resources/dictionary/myprescription.json";
    public String DICTIONARY_ORDER = "./src/test/resources/dictionary/order.json";
    public String DICTIONARY_ALERT_MESSAGES = "./src/test/resources/dictionary/alert_messages.json";
    public String DICTIONARY_EMAIL = "./src/test/resources/dictionary/email.json";

    public void selectLanguage(String language) {

        clickOnWebElement(Locators.BASE_PAGE_SELECT_LANGUAGE.replace("$Language", language));
    }

    public void clickOnNewUserLoginButton() {
        moveTo(Locators.BASE_PAGE_HEADER_MENU_NEW_USER_LOGIN_BUTTON).click();
    }

    protected void clickOnWebElement(String xpath) {
        ((JavascriptExecutor) getDriver()).executeScript("arguments[0].click();", findBy(xpath));
    }

    public void scrollIntoView(String xpath) {
        ((JavascriptExecutor) getDriver()).executeScript("arguments[0].scrollIntoView(true);", findBy(xpath));
    }

    public void scrollIntoView(String xpath, int offset_y){
        int y = getDriver().findElement(net.serenitybdd.core.annotations.findby.By.xpath(xpath)).getLocation().getY() + offset_y;
        ((JavascriptExecutor) getDriver()).executeScript("window.scrollTo(0, " + y + ")");
    }

    public String getBackgroundColorOf(String xpathWebelement) {
        return $(xpathWebelement).getCssValue("background-color");
    }

    public String getTranslatedMessage(String message, String language, String dictionary) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Map<String, String>> messages;
        messages = mapper.readValue(new File(dictionary), new TypeReference<Map<String, Map<String, String>>>() {
        });
        return messages.get(message).get(language);
    }

    public Boolean isAlertDisplayed(String message) throws IOException {
        List<WebElementFacade> alerts = new ArrayList<>();
        waitABit(1000);
        withTimeoutOf(15, TimeUnit.SECONDS).
                waitFor(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath(Locators.BASE_PAGE_ALERTS)));
        alerts = findAll(Locators.BASE_PAGE_ALERTS);
        for (WebElementFacade alert : alerts) {
            if (alert.getText().contains(message)) {
                alert.find(By.xpath(".//button[@data-dismiss='alert']")).click();
                return true;
            }
        }
        return false;
    }

    public Boolean getCurrentUrl(String expectedUrl) {
        withTimeoutOf(20, TimeUnit.SECONDS).waitFor(ExpectedConditions.urlContains(expectedUrl));
        return getDriver().getCurrentUrl().equals(expectedUrl);
    }

    public void clickOn(String xpath, Integer waitValue) {
        withTimeoutOf(10, TimeUnit.SECONDS).waitFor(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
      //  scrollIntoView(xpath);
        moveTo(xpath).click();
    }

    public void clickOn(String xpath, Integer waitValue, Integer scrollValue) {
        withTimeoutOf(waitValue, TimeUnit.SECONDS).waitFor(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath(xpath)));
        scrollIntoView(xpath, scrollValue);
        waitABit(1000);
        moveTo(xpath);
        waitABit(500);
        findBy(xpath).click();
    }

    public String getTextValueOfElement(String xpath) {
        return $(xpath).getText();
    }

    public String getAttributeValueOf(String xpath, String attribute) {
        return $(xpath).getAttribute(attribute);
    }

    public List<String> getTextValuesOfElement(String xpath) {
        List<String> textValues = new ArrayList<>();
        for (WebElementFacade we : findAll(By.xpath(xpath))) {
            textValues.add(we.getText());
        }
        return textValues;
    }

    public boolean isWebElementDisplayed(String xpath) {
        return $(xpath).isDisplayed();
    }


    public boolean isUpdateProfileButtonDisplayed() {
        try {
            $(Locators.BASE_PAGE_HEADER_MENU_MY_ACCOUNT_BUTTON).isDisplayed();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void enterTextIntoField(String xpath, String text) {
        withTimeoutOf(10, TimeUnit.SECONDS).waitFor(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath)));
        $(xpath).clear();
        $(xpath).sendKeys(text);
    }

    public void searchForProduct(String searchText) throws IOException {
        WebElementFacade form;
        withTimeoutOf(10, TimeUnit.SECONDS).waitFor(ExpectedConditions.presenceOfElementLocated(By.xpath(Locators.BASE_PAGE_GLOBAL_SEARCH_FIELD)));
        moveTo(Locators.BASE_PAGE_GLOBAL_SEARCH_FIELD).click();
        form = findBy(".//form[@id='autocomplete-form']");
        form.findBy(Locators.BASE_PAGE_GLOBAL_SEARCH_FIELD).sendKeys(searchText);
        waitABit(1000);
        moveTo(Locators.BASE_PAGE_GLOBAL_SEARCH_BUTTON);
        findBy(Locators.BASE_PAGE_GLOBAL_SEARCH_BUTTON).click();
    }

    public String getCounterOfShoppingCart() {
        try {
            scrollIntoView(Locators.BASE_PAGE_HEADER_COUNTER_ON_SHOPPING_CART_BUTTON);
            waitABit(500);
            return $(Locators.BASE_PAGE_HEADER_COUNTER_ON_SHOPPING_CART_BUTTON).getText();
        }catch (Exception e){
            return "";
        }
    }

    private void selectProductCategoryFromLevel(String language, String category, String xpathLevel) throws IOException {
        String PRODUCTS_CATEGORY = ".//ul[contains(@class,'cd-dropdown')]//a";
        String translatedCategory;
        translatedCategory = getTranslatedMessage(category, language, DICTIONARY_PATH);
        for (int i = 1; i <= findAll(PRODUCTS_CATEGORY).size(); i++) {
            moveTo(xpathLevel + "[" + i + "]");
            if (findBy(xpathLevel + "[" + i + "]").getAttribute("title").equals(translatedCategory)) {
                moveTo(xpathLevel + "[" + i + "]").click();
                break;
            }
        }
    }

    public void selectProductCategoryFromHeaderDropDown(String language, String category, String subCategory) throws IOException {
        String CATEGORY_LEVEL = "((.//ul[contains(@class,'cd-dropdown')])[1]/li[@class='has-children menu-item']/a)";
        String SUB_CATEGORY_LEVEL = "(.//ul[@class='cd-dropdown cd-secondary-dropdown']/li/a)";
        selectProductCategoryFromLevel(language, category, CATEGORY_LEVEL);
        selectProductCategoryFromLevel(language, subCategory, SUB_CATEGORY_LEVEL);
    }

    public void selectProductCategoryFromHeaderDropDown(String language, String category) throws IOException {
        String CATEGORY_LEVEL = "((.//ul[contains(@class,'cd-dropdown')])[1]/li[@class='has-children menu-item']/a)";
        selectProductCategoryFromLevel(language, category, CATEGORY_LEVEL);
    }

    public void moveToProductsDropDownInHeader() {
        withTimeoutOf(10, TimeUnit.SECONDS)
                .waitFor(ExpectedConditions.visibilityOfElementLocated(By.xpath(Locators.BASE_PAGE_HEADER_PRODACTS_DROP_DOWN)));
        moveTo(Locators.BASE_PAGE_HEADER_PRODACTS_DROP_DOWN);
    }

    public String getTranslation(String text, String language) throws IOException {
        return getTranslatedMessage(text, language, DICTIONARY_PATH);
    }

    public String getTranslation(String text, String language, String dictionaryPath) throws IOException {
        return getTranslatedMessage(text, language, dictionaryPath);
    }

    public void selectItemInHeaderMenu(String itemName, String language) throws IOException {
        String translatedItemName = getTranslation(itemName, language, DICTIONARY_PATH);
        moveTo(".//nav[@class='main-navigation js-navigation']/ul/li/a[.='$Item']/ancestor::li".replace("$Item", translatedItemName));
        withTimeoutOf(5, TimeUnit.SECONDS).
                waitFor(ExpectedConditions.elementToBeClickable(By.xpath(Locators.BASE_PAGE_HEADER_MENU.replace("$Item", translatedItemName))));
        $(Locators.BASE_PAGE_HEADER_MENU.replace("$Item", translatedItemName)).click();
    }

    public void confirmUsingCookies() {
        try {
            withTimeoutOf(5, TimeUnit.SECONDS).waitFor(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//a[@class='cc_btn cc_btn_accept_all']")));
            moveTo(".//a[@class='cc_btn cc_btn_accept_all']");
            clickOnWebElement(".//a[@class='cc_btn cc_btn_accept_all']");
        } catch (Exception e) {

        }
    }

    public void clickOnMyAccountItemInHeaderMenu() {
        withTimeoutOf(5, TimeUnit.SECONDS).waitFor(ExpectedConditions.visibilityOfElementLocated(By.xpath(Locators.BASE_PAGE_HEADER_MENU_MY_ACCOUNT_BUTTON)));
        scrollIntoView(Locators.BASE_PAGE_HEADER_MENU_MY_ACCOUNT_BUTTON);
        moveTo(Locators.BASE_PAGE_HEADER_MENU_MY_ACCOUNT_BUTTON);
        waitABit(1000);
        findBy(Locators.BASE_PAGE_HEADER_MENU_MY_ACCOUNT_BUTTON).click();
    }

    public void selectMainMenuItem(String itemName, String language) throws IOException {
        String MAIN_MENU_ITEMS = "(.//*[@class='main-navigation js-navigation']/ul/li/a)";
        String translatedItemName = getTranslation(itemName, language, DICTIONARY_PATH);
        withTimeoutOf(10, TimeUnit.SECONDS).waitFor(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath(MAIN_MENU_ITEMS)));
        for (int i = 1; i < findAll(MAIN_MENU_ITEMS).size(); i++) {
            if (findBy(MAIN_MENU_ITEMS + "[" + i + "]").getText().equals(translatedItemName)) {
                findBy(MAIN_MENU_ITEMS + "[" + i + "]").click();
                break;
            }
        }
    }

    public String waitForUrl(String expectedUrl) {
        try {
            withTimeoutOf(10, TimeUnit.SECONDS).waitFor(ExpectedConditions.urlContains(expectedUrl));
            return expectedUrl;
        } catch (Exception e) {
            return getDriver().getCurrentUrl();
        }
    }

    public void enterTextIntoGlobalSearchField(String text) throws IOException {
        WebElementFacade form;
        withTimeoutOf(10, TimeUnit.SECONDS).waitFor(ExpectedConditions.presenceOfElementLocated(By.xpath(Locators.BASE_PAGE_GLOBAL_SEARCH_FIELD)));
        moveTo(Locators.BASE_PAGE_GLOBAL_SEARCH_FIELD).click();
        form = findBy(".//form[@id='autocomplete-form']");
        form.findBy(Locators.BASE_PAGE_GLOBAL_SEARCH_FIELD).clear();
        form.findBy(Locators.BASE_PAGE_GLOBAL_SEARCH_FIELD).sendKeys(text);
    }

    public List<String> getAllProductsNameFromAutocompleteResultsOfGlobalSearch() {
        String PRODUCT_NAME = ".//div[@id='autocomplete-results']/ul/li[@class='products-item item ui-menu-item' ]//h4[@class='name']";
        List<String> productsName = new ArrayList<>();
        for (WebElementFacade productName : findAll(PRODUCT_NAME)) {
            productsName.add(productName.getText());
        }
        return productsName;
    }

    public String selectProductFromAutocompleteResultsOfGlobalSearchField(String productName, String currentLanguage) throws IOException {
        String price;
        String PRODUCT_LINK = ".//div[@id='autocomplete-results']/ul/li//h4[.='" + getTranslation(productName, currentLanguage, DICTIONARY_PRODUCTS) + "']/ancestor::a[1]";
        withTimeoutOf(5, TimeUnit.SECONDS).waitFor(ExpectedConditions.visibilityOfElementLocated(By.xpath(PRODUCT_LINK)));
        price = findBy(PRODUCT_LINK + "//span[@class='item-price']").getText();
        moveTo(PRODUCT_LINK).click();
        return price;
    }

    public String selectProductFromAutocompleteResultsOfGlobalSearchField(String productCode) {
        String price;
        String PRODUCT_LINK = "//a[contains(@href,'"+productCode+"')]//ancestor::li[@class='products-item item ui-menu-item']";
        withTimeoutOf(5, TimeUnit.SECONDS).waitFor(ExpectedConditions.visibilityOfElementLocated(By.xpath(PRODUCT_LINK)));
        $(Locators.BASE_PAGE_GLOBAL_SEARCH_FIELD).click();
        price = findBy(PRODUCT_LINK + "//span[@class='item-price']").getText();
        clickOnWebElement(PRODUCT_LINK);
        return price;
    }

    public String getPriceFromAutocompleteResults(String productName) {
        return findBy(".//h4[.='" + productName + "']/ancestor::div[1]/following-sibling::div//span[@class='item-price']").getText();
    }

    public void gotoUrl(String url) {
        getDriver().navigate().to(url);
    }



// In case FireFox -> override moveTo()
//    @Override
//    public WebElementFacade moveTo(String xpath) {
//        //(new Actions(getDriver())).moveToElement(getDriver().findElement(By.xpath(xpath))).build().perform();
//        Point coordinates = getDriver().findElement(By.xpath(xpath)).getLocation();
//        Robot robot = null;
//        try {
//            robot = new Robot();
//        } catch (AWTException e) {
//            e.printStackTrace();
//        }
//        robot.mouseMove(coordinates.getX(), coordinates.getY() + 120);
//        return $(xpath);
//    }
}
