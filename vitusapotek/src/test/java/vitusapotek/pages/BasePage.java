package vitusapotek.pages;

import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.pages.WebElementFacade;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class BasePage extends PageObject {
    public String MAIN_MENU_ITEMS = "(.//nav[@class='main-navigation js-navigation']/ul/li/a)";
    public String HEADER_SHOPPING_CART_BUTTON = ".//li[@class='cart js-blink-mini-cart-link']/a[@href='/cart']";
    public String GLOBAL_SEARCH_FIELD=".//input[@id='autocomplete-search']";
    public String GLOBAL_SEARCH_BUTTON="//li[@class='global-search']/button[@class='submit toggle-search']";

    public void scrollIntoView(String xpath) {
        ((JavascriptExecutor) getDriver()).executeScript("arguments[0].scrollIntoView(true);", findBy(xpath));
    }

    public void clearCoockies(){
        getDriver().manage().deleteAllCookies();
        waitABit(5000);
    }

    public void selectMainMenuItem(String itemName) {
        withTimeoutOf(5,TimeUnit.SECONDS).waitFor(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath(MAIN_MENU_ITEMS)));
        for (int i = 1; i < findAll(MAIN_MENU_ITEMS).size(); i++) {
            if (findBy(MAIN_MENU_ITEMS + "[" + i + "]").getText().equals(itemName)) {
                findBy(MAIN_MENU_ITEMS + "[" + i + "]").click();
                break;
            }
        }
    }

    public void moveToMainMenuItem(String itemName) {
        for (int i = 1; i < findAll(MAIN_MENU_ITEMS).size(); i++) {
            if (findBy(MAIN_MENU_ITEMS + "[" + i + "]").getText().equals(itemName)) {
                moveTo(MAIN_MENU_ITEMS + "[" + i + "]" + "//a");
                break;
            }
        }
    }

    private void selectProductCategoryFromLevel(String category, String xpathLevel) throws IOException {
        String PRODUCTS_CATEGORY=".//ul[contains(@class,'cd-dropdown')]//a";
        for(int i=1;i<=findAll(PRODUCTS_CATEGORY).size();i++){
            moveTo(xpathLevel+"["+i+"]");
            if(findBy(xpathLevel+"["+i+"]").getAttribute("title").equals(category)){
                moveTo(xpathLevel+"["+i+"]").click();
                break;
            }
        }
    }

    public void selectProductCategoryFromMainMenu(String category, String subCategory) throws IOException {
        String CATEGORY_LEVEL="((.//ul[contains(@class,'cd-dropdown')])[1]/li[@class='has-children menu-item']/a)";
        String SUB_CATEGORY_LEVEL="(.//ul[@class='cd-dropdown cd-secondary-dropdown']/li/a)";
        selectProductCategoryFromLevel(category, CATEGORY_LEVEL);
        selectProductCategoryFromLevel(subCategory, SUB_CATEGORY_LEVEL);
    }

    public void clickOnCartButtonInHeader() {
        withTimeoutOf(10, TimeUnit.SECONDS).waitFor(ExpectedConditions.presenceOfElementLocated(By.xpath(HEADER_SHOPPING_CART_BUTTON)));
        scrollIntoView(HEADER_SHOPPING_CART_BUTTON);
        waitABit(1000);
        moveTo(HEADER_SHOPPING_CART_BUTTON);
        waitABit(500);
        findBy(HEADER_SHOPPING_CART_BUTTON).click();
    }

    protected void clickOnJS(String xpath) {
        ((JavascriptExecutor) getDriver()).executeScript("arguments[0].click();", findBy(xpath));
    }

    public void confirmUsingCookies() {
        try {
            withTimeoutOf(15, TimeUnit.SECONDS).waitFor(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//a[@class='cc_btn cc_btn_accept_all']")));
            moveTo(".//a[@class='cc_btn cc_btn_accept_all']");
            clickOnJS(".//a[@class='cc_btn cc_btn_accept_all']");
        } catch (Exception e) {
        }
    }

    public Boolean isAlertMessageDisplayed(String message) {
        String ALERTS = "(.//div[contains(@class,'alert')]//div[@class='container'])";
        List<WebElementFacade> alerts = new ArrayList<>();
        waitABit(1000);
        try {
            withTimeoutOf(5, TimeUnit.SECONDS).
                    waitFor(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath(ALERTS + "[1]")));
            alerts = findAll(ALERTS);
            for (WebElementFacade alert : alerts) {
                if (alert.getText().contains(message)) {
                    alert.find(By.xpath(".//button[@data-dismiss='alert']")).click();
                    return true;
                }
            }
        }catch(Exception e){
        }
        return false;
    }

    public void clickOnNewUserLoginButton() {
        withTimeoutOf(10, TimeUnit.SECONDS).waitFor(ExpectedConditions.visibilityOfElementLocated(By.xpath("(.//a[@href='/login'])[1]")));
        moveTo("(.//a[@href='/login'])[1]").click();
    }

    public String getCaptionOfUpdateProfileButton() {
        return findBy(".//a[@href='/my-account/update-profile']").getText();
    }

    public void clickOnUpdateProfileButtonInHeaderMenu() {
        scrollIntoView(".//a[@href='/my-account/update-profile']");
        findBy(".//a[@href='/my-account/update-profile']").click();
    }

    public void clickOnLogoutButtonInHeaderMenu() {
        scrollIntoView("(.//a[@href='/logout'])[1]");
        moveTo("(.//a[@href='/logout'])[1]").click();
    }

    public boolean isLogoutButtonDisplayedInHeaderMenu() {
            withTimeoutOf(10, TimeUnit.SECONDS).waitFor(ExpectedConditions.presenceOfElementLocated(By.xpath("(.//a[@href='/logout'])[1]")));
            scrollIntoView("(.//a[@href='/logout'])[1]");
           return $("(.//a[@href='/logout'])[1]").isDisplayed();
    }

    public boolean isUpdateProfileButtonDisplayedInHeaderMenu() {
        try {
            withTimeoutOf(10, TimeUnit.SECONDS).waitFor(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//a[@href='/my-account/update-profile']")));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String waitForUrl(String expectedUrl) {
        try {
            withTimeoutOf(10, TimeUnit.SECONDS).waitFor(ExpectedConditions.urlContains(expectedUrl));
            return expectedUrl;
        }catch(Exception e){
            return getDriver().getCurrentUrl();
        }
    }

    public void moveToItemInMainMenu(String itemName) {
        for (int i = 1; i < findAll(MAIN_MENU_ITEMS).size(); i++) {
            if (findBy(MAIN_MENU_ITEMS + "[" + i + "]").getText().equals(itemName)) {
                moveTo(MAIN_MENU_ITEMS + "[" + i + "]");
                withTimeoutOf(5, TimeUnit.SECONDS).waitFor(ExpectedConditions.presenceOfElementLocated(By.xpath(".//a[@title='Produkter']/following-sibling::ul")));
                break;
            }
        }
    }

    public String waitForURL(String expectedUrl) {
        try {
            withTimeoutOf(10, TimeUnit.SECONDS).waitFor(ExpectedConditions.urlToBe(expectedUrl));
        }catch(Exception e){
        }
        return getDriver().getCurrentUrl();
    }

    public void clickOnHeaderSearchButton() {
        moveTo(".//li[@class='global-search']/button[@class='submit toggle-search']").click();
    }

    public void enterTextIntoHeaderSearchField(String searchText) {
        String SEARCH_FIELD=".//form[@id='autocomplete-form']//input[@id='autocomplete-search']";
        withTimeoutOf(5, TimeUnit.SECONDS).waitFor(ExpectedConditions.visibilityOfElementLocated(By.xpath(SEARCH_FIELD)));
        findBy(SEARCH_FIELD).sendKeys(searchText);
//        withTimeoutOf(5, TimeUnit.SECONDS).waitFor(ExpectedConditions.textToBePresentInElementLocated(By.xpath(SEARCH_FIELD),searchText));
    }

    public void navigateToPreviousPage() {
        waitABit(5000);
        getDriver().navigate().back();
    }

    public void searchForProduct(String productName){
        WebElementFacade form;
        withTimeoutOf(10, TimeUnit.SECONDS).waitFor(ExpectedConditions.presenceOfElementLocated(By.xpath(GLOBAL_SEARCH_FIELD)));
        moveTo(GLOBAL_SEARCH_FIELD).click();
        form = findBy(".//form[@id='autocomplete-form']");
        form.findBy(GLOBAL_SEARCH_FIELD).sendKeys(productName);
        waitABit(1000);
        moveTo(GLOBAL_SEARCH_BUTTON);
        findBy(GLOBAL_SEARCH_BUTTON).click();
    }

    public void selectFromMainMenu(String menuItem, String option) {
        moveToItemInMainMenu(menuItem);
        moveTo("//ul/li/a[.='"+option+"']").click();
    }
}
