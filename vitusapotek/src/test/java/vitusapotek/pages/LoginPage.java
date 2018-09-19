package vitusapotek.pages;

import net.serenitybdd.core.annotations.findby.By;
import net.serenitybdd.core.pages.WebElementFacade;
import org.openqa.selenium.Keys;
import org.openqa.selenium.support.ui.ExpectedConditions;
import vitusapotek.models.CustomerProfile;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class LoginPage extends BasePage {
    public void enterNewCustomerIntoField(String value, String fieldName) {
        String FIELD = ".//form[@id='pearlRegisterForm']//input[@placeholder='" + fieldName + "']";
        withTimeoutOf(5, TimeUnit.SECONDS).waitFor(ExpectedConditions.presenceOfElementLocated(By.xpath(FIELD)));
        scrollIntoView(FIELD);
        findBy(FIELD).clear();
        findBy(FIELD).sendKeys(value);
    }

    public boolean isErrorMessageDisplayedForCustomerProfile(String errorMessage) {
        List<WebElementFacade> errors = new ArrayList<>();
        errors = findAll(".//form[@id='pearlRegisterForm']//span[contains(@id,'register.')]");
        for (WebElementFacade error : errors) {
            if (error.getText().contains(errorMessage)) {
                return true;
            }
        }
        return false;

    }

    public Boolean isUpdateProfileButtonNotDisplayed() {
        try {
            $(".//nav[@class='secondary-navigation']//a[@href='/my-account/update-profile']").isDisplayed();
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    public void clickOnCreateAccountButton() {
        scrollIntoView(".//*[@id='pearlRegisterForm']//button[@type='submit']");
        moveTo(".//*[@id='pearlRegisterForm']//button[@type='submit']").click();
    }

    public String getCityValueOfNewCustomer() {
        try{
            withTimeoutOf(5,TimeUnit.SECONDS).waitFor(ExpectedConditions
                    .attributeToBeNotEmpty(findBy(".//form[@id='pearlRegisterForm']//input[@id='register.townCity']"), "value"));
        }catch (Exception e){
        }
        return findBy(".//form[@id='pearlRegisterForm']//input[@id='register.townCity']").getValue();
    }

    private void enterValueIntoFieldOfRegistrationForm(String xpath, String value) {
//        scrollIntoView(xpath);
        try {
            findBy(xpath).clear();
        }catch(Exception e){
        }
        waitABit(500);
        findBy(xpath).sendKeys(value);
    }

    public void fillTheRegistrationFormWithData(CustomerProfile customerProfile) {
        enterValueIntoFieldOfRegistrationForm(".//form[@id='pearlRegisterForm']//input[@id='register.firstName']", customerProfile.getFirstname());
        enterValueIntoFieldOfRegistrationForm(".//form[@id='pearlRegisterForm']//input[@id='register.lastName']", customerProfile.getLastName());
        enterValueIntoFieldOfRegistrationForm(".//form[@id='pearlRegisterForm']//input[@id='register.cellphone']", customerProfile.getPhoneNumber());
        enterValueIntoFieldOfRegistrationForm(".//form[@id='pearlRegisterForm']//input[@id='register.line1']", customerProfile.getAddress());
        enterValueIntoFieldOfRegistrationForm(".//form[@id='pearlRegisterForm']//input[@id='register.postcode']", customerProfile.getPostCode());
        enterValueIntoFieldOfRegistrationForm(".//form[@id='pearlRegisterForm']//input[@id='register.townCity']", customerProfile.getCity());
        enterValueIntoFieldOfRegistrationForm(".//form[@id='pearlRegisterForm']//input[@id='register.email']", customerProfile.getEmail());
        enterValueIntoFieldOfRegistrationForm(".//form[@id='pearlRegisterForm']//input[@id='password']", customerProfile.getPassword());
        enterValueIntoFieldOfRegistrationForm(".//form[@id='pearlRegisterForm']//input[@id='register.checkPwd']", customerProfile.getConfirmPassword());
        scrollIntoView(".//div[@id='password_bar']");
        if (customerProfile.getIWantTobe4() != null)
            if (customerProfile.getIWantTobe4() == true) {
                withTimeoutOf(5, TimeUnit.SECONDS).waitFor(ExpectedConditions.elementToBeClickable(By.xpath(".//input[@id='register.hasMembership']")));
                moveTo(".//label[@for='register.hasMembership']").click();
            }

        if (customerProfile.getNewsLetterFlag() != null)
            if (customerProfile.getNewsLetterFlag() == true) {
                moveTo("(.//*[@id='pearlRegisterForm']//div[@class='form-group'])[1]/div[2]");
                moveTo(".//form[@id='pearlRegisterForm']//label[@for='register.consentForNewsletter']").click();
            }
    }

    public void selectMembershipGroups(List<String> groups) {
        moveTo(".//select[@id='groupSelect']").click();
        for (String group : groups) {
            findBy(".//select[@id='groupSelect']").selectByVisibleText(group);
        }
    }

    public void enterFavoritePharmacies(List<String> pharmacies) {
        scrollIntoView(".//*[@id='site']/footer/div[1]");
        for (String favorite : pharmacies) {
            $(".//form[@id='pearlRegisterForm']//input[@id='pharmacies-autocomplete']").sendKeys(favorite);
            waitABit(1000);
            $(".//form[@id='pearlRegisterForm']//input[@id='pharmacies-autocomplete']").sendKeys(Keys.chord(Keys.ALT, Keys.PAGE_DOWN));
            waitABit(1000);
            getDriver().switchTo().activeElement();
            clickOnJS(".//ul[@id='ui-id-2']/li[1]");
            waitABit(1000);
        }
    }

    public void signInWithEmailAndPassword(String email, String password) {
        findBy(".//form[@id='loginForm']//input[@id='j_username']").click();
        findBy(".//form[@id='loginForm']//input[@id='j_username']").clear();
        findBy(".//form[@id='loginForm']//input[@id='j_username']").sendKeys(email);

        findBy(".//form[@id='loginForm']//input[@id='j_password']").click();
        findBy(".//form[@id='loginForm']//input[@id='j_password']").clear();
        findBy(".//form[@id='loginForm']//input[@id='j_password']").sendKeys(password);

        moveTo(".//form[@id='loginForm']//button[@id='loginBtnCheckout']");
        waitABit(1000);
        $(".//form[@id='loginForm']//button[@id='loginBtnCheckout']").click();
    }


}
