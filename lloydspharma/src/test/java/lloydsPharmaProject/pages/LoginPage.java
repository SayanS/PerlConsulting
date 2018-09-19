package lloydsPharmaProject.pages;

import lloydsPharmaProject.Locators;
import net.serenitybdd.core.annotations.findby.By;
import net.serenitybdd.core.pages.WebElementFacade;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class LoginPage extends BasePage {

    public void openLoginPage(String base_url) {
        getDriver().navigate().to(base_url+"/login");
    }
    public void selectTitleRegistration(String title) {
        if(!title.equals(""))
        selectFromDropdown(findBy(Locators.LOGIN_PAGE_REGISTRATION_FORM_TITLE_DROP_DOWN), title);
    }

    public void enterFirstNameRegistration(String firstname) {
        $(Locators.LOGIN_PAGE_REGISTRATION_FORM_FIRST_NAME).sendKeys(firstname);
    }

    public void enterLastNameRegistration(String lastName) {
        $(Locators.LOGIN_PAGE_REGISTRATION_FORM_LAST_NAME).sendKeys(lastName);
    }

    public void enterPhoneNumberRegistration(String phoneNumber) {
        $(Locators.LOGIN_PAGE_REGISTRATION_FORM_PHONE_NUMBER).sendKeys(phoneNumber);
    }

    public void enterAddressRegistration(String address) {
        $(Locators.LOGIN_PAGE_REGISTRATION_FORM_ADDRESS).sendKeys(address);
    }

    public void enterBuildingRegistration(String building) {
        $(Locators.LOGIN_PAGE_REGISTRATION_FORM_BUILDING).sendKeys(building);
    }

    public void enterBoxRegistration(String box) {
        $(Locators.LOGIN_PAGE_REGISTRATION_FORM_POBOX).sendKeys(box);
    }

    public void enterPostCodeRegistration(String postCode) {
        $(Locators.LOGIN_PAGE_REGISTRATION_FORM_POST_CODE).sendKeys(postCode);
    }

    public void enterCityRegistration(String city) {
        $(Locators.LOGIN_PAGE_REGISTRATION_FORM_CITY).sendKeys(city);
    }

    public void enterEmailRegistration(String email) {
        $(Locators.LOGIN_PAGE_REGISTRATION_FORM_EMAIL).sendKeys(email);
    }

    public void enterPasswordRegistration(String password) {
        $(Locators.LOGIN_PAGE_REGISTRATION_FORM_PASSWORD).clear();
        $(Locators.LOGIN_PAGE_REGISTRATION_FORM_PASSWORD).sendKeys(password);
    }

    public void enterConfirmPasswordRegistration(String confirmPassword) {
        $(Locators.LOGIN_PAGE_REGISTRATION_FORM_CONFIRM_PASSWORD).sendKeys(confirmPassword);
    }

    public void enterBirthDayRegistration(String birthDay) {
        $(Locators.LOGIN_PAGE_REGISTRATION_FORM_BIRTHDAY).sendKeys(birthDay);
    }

    public void enterAdditionalInfoRegistration(String additionalInfo) {
        $(Locators.LOGIN_PAGE_REGISTRATION_FORM_ADDITIONAL_INFO).sendKeys(additionalInfo);
    }

    public void setConsentForNewsLettersCheckBoxRegistration(String iWantToResiveNewsLetter) {
        if (iWantToResiveNewsLetter.equals(true)) {
            scrollIntoView(Locators.LOGIN_PAGE_REGISTRATION_FORM_CONSENT_FORNEWSLETTER_CHECKBOX,-50);
            $(Locators.LOGIN_PAGE_REGISTRATION_FORM_CONSENT_FORNEWSLETTER_CHECKBOX).click();
        }
    }

    public void selectSpecialInteressegroepRegistration(String specialInteressegroep) {
        selectFromDropdown(findBy(Locators.LOGIN_PAGE_REGISTRATION_FORM_SPECIAL_INTERESSEGROEP_DROP_DOWN), specialInteressegroep);
    }

    public void enterFavoritePharmaciesRegistration(List<String> favoritePharmacies) {
//        scrollIntoView(".//*[@id='site']/footer/div[1]");
//        for(String favorite:favoritePharmacies) {
//            $(Locators.LOGIN_PAGE_REGISTRATION_FORM_FAVORITE_PHARMACIES).sendKeys(favorite);
//            waitABit(1000);
//           $(Locators.LOGIN_PAGE_REGISTRATION_FORM_FAVORITE_PHARMACIES).sendKeys(Keys.chord(Keys.ALT, Keys.PAGE_DOWN));
//            waitABit(1000);
//            getDriver().switchTo().activeElement();
//            clickOnWebElement(".//ul[@id='ui-id-2']/li[1]");
//            waitABit(1000);
//        }
        scrollIntoView(Locators.LOGIN_PAGE_REGISTRATION_FORM_FAVORITE_PHARMACIES);
        $(Locators.LOGIN_PAGE_REGISTRATION_FORM_FAVORITE_PHARMACIES).sendKeys(favoritePharmacies.get(0));
        waitABit(1000);
    }

    public void clickOnCreateFreeAccountButton() {
        scrollIntoView(Locators.LOGIN_PAGE_REGISTRATION_FORM_CREATE_ACCOUNT_BUTTON);
        $(Locators.LOGIN_PAGE_REGISTRATION_FORM_CREATE_ACCOUNT_BUTTON).click();
    }

    public void enterLogin(String email) {
        WebElementFacade form;
        form=findBy(".//form[@id='loginForm']");
        withTimeoutOf(10, TimeUnit.SECONDS)
                .waitFor(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//form[@id='loginForm']")));
        form.findBy(Locators.LOGIN_PAGE_LOGIN_FORM_EMAIL).clear();
        form.findBy(Locators.LOGIN_PAGE_LOGIN_FORM_EMAIL).sendKeys(email);
    }

    public void enterLoginPassword(String password) {
        WebElementFacade form;
        form=findBy(".//form[@id='loginForm']");
        form.findBy(Locators.LOGIN_PAGE_LOGIN_FORM_PASSWORD).clear();
        form.findBy(Locators.LOGIN_PAGE_LOGIN_FORM_PASSWORD).sendKeys(password);
    }

    public Boolean isErrorDisplayed(String message, String language) throws IOException {
        String translatedMessage;
        List<WebElementFacade> errors = new ArrayList<>();
        translatedMessage=getTranslatedMessage(message, language, DICTIONARY_LOGIN_PROFILE);
        errors = findAll(Locators.BASE_PAGE_ALERTS);
        for (WebElementFacade error : errors) {
            if (error.getText().contains(translatedMessage)) {
                return true;
            }
        }
        return false;
    }

    public Boolean isAlertDisplayed(String message, String language) throws IOException {
        String translatedMessage;
        translatedMessage=getTranslatedMessage(message, language, DICTIONARY_ALERT_MESSAGES);
        return isAlertDisplayed(translatedMessage);
    }

    public void clickOnSignInButton() {
        WebElementFacade form;
        form=findBy(".//form[@id='loginForm']");
        form.findBy(Locators.LOGIN_PAGE_LOGIN_FORM_SIGN_IN_BUTTON).click();
    }

}
