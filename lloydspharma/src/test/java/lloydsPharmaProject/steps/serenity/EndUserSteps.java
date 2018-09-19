package lloydsPharmaProject.steps.serenity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import io.restassured.http.Cookies;
import io.restassured.response.Response;
import lloydsPharmaProject.Locators;
import lloydsPharmaProject.Models.Address;
import lloydsPharmaProject.Models.CustomerProfile;
import lloydsPharmaProject.Models.Order;
import lloydsPharmaProject.Models.Product;
import lloydsPharmaProject.backofficePages.BackOfficePages;
import lloydsPharmaProject.pages.*;
import lloydsPharmaProject.utils.EmailUtilities;
import lloydsPharmaProject.utils.Helpers;
import net.serenitybdd.core.Serenity;
import net.thucydides.core.annotations.Step;
import org.apache.commons.collections.list.TreeList;
import org.dom4j.DocumentException;
import org.jdom.JDOMException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.Assert;
import org.xml.sax.SAXException;
import us.codecraft.xsoup.Xsoup;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import java.awt.*;
import java.io.*;
import java.nio.file.Files;
import java.security.GeneralSecurityException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

import static io.restassured.RestAssured.given;
import static java.util.Arrays.sort;

public class EndUserSteps {

    private BasePage basePage;
    private LoginPage loginPage;
    private MyAccountPage myAccountPage;
    private HomePage homePage;
    private SearchResultsPage searchResultsPage;
    private ShoppingCart shoppingCart;
    private Checkout checkoutPage;
    private ProductDetailsPage productDetailsPage;
    private MyPrescriptionPage myPrescriptionPage;
    private InjenicoPage injenicoPage;
    private OrderConfirmationPage orderConfirmationPage;
    private OrderDetailsPage orderDetailsPage;
    private ProductsPage productsPage;
    private BackOfficePages backOfficePages;

    private String BASE_URL = System.getProperty("base.url");
//    private String BASE_URL = "https://qa.lloydspharma.be";

    private String currentLanguage;

    public CustomerProfile expectedCustomerProfile;
    public List<String> expectedSpecialGroupOfCustomerProfile = new ArrayList<>();
    public List<String> expectedFavoritePharmacies = new TreeList();
    public List<String> createdOrdersNumber = new ArrayList<>();


    public Order expectedOrder = new Order();
    public Order createdOrder = new Order();
    public Address deliveryAddress = new Address();

    @Step
    public void openLoginPage() throws IOException {
        loginPage.openLoginPage(BASE_URL);
    }

    @Step
    public void selectLanguage(String language) {
        basePage.selectLanguage(language);
        currentLanguage = language;
        Serenity.getCurrentSession().put("carrentLanguage", language);
    }

    @Step
    public void fillTheRegistrationFormWithData(CustomerProfile customerProfile) {
        loginPage.selectTitleRegistration(customerProfile.getTitle());
        loginPage.enterFirstNameRegistration(customerProfile.getFirstName());
        loginPage.enterLastNameRegistration(customerProfile.getLastName());
        loginPage.enterPhoneNumberRegistration(customerProfile.getPhoneNumber());
        loginPage.enterAddressRegistration(customerProfile.getAddress());
        loginPage.enterBuildingRegistration(customerProfile.getBuilding());
        loginPage.enterPostCodeRegistration(customerProfile.getPostCode());
        loginPage.enterCityRegistration(customerProfile.getCity());
        loginPage.enterEmailRegistration(customerProfile.getEmail());
        loginPage.enterPasswordRegistration(customerProfile.getPassword());
        Serenity.getCurrentSession().put("password", customerProfile.getPassword());
        loginPage.enterConfirmPasswordRegistration(customerProfile.getConfirmPassword());
        loginPage.enterBirthDayRegistration(customerProfile.getBirthDate());
        loginPage.enterAdditionalInfoRegistration(customerProfile.getAdditionalInfo());
        loginPage.setConsentForNewsLettersCheckBoxRegistration(customerProfile.getiWantToResiveNewsLetter());
        expectedCustomerProfile = customerProfile;
    }

    @Step
    public void clickOnNewUserLoginButton() throws IOException {
        basePage.clickOnNewUserLoginButton();
    }

    @Step
    public void isBackgroundOfSelectedFieldsFilledAndTicked(CustomerProfile fields, String expectedColor) {
        if (expectedColor.equals("Green")) expectedColor = "rgba(223, 240, 216, 1)";
        else expectedColor = "rgba(255, 229, 229, 1)";

        if (fields.getFirstName().equals("Selected"))
            Assert.assertEquals("Background of " + fields.getFirstName(), loginPage.getBackgroundColorOf(Locators.LOGIN_PAGE_REGISTRATION_FORM_FIRST_NAME), expectedColor);
        if (fields.getLastName().equals("Selected"))
            Assert.assertEquals("Background of " + fields.getLastName(), loginPage.getBackgroundColorOf(Locators.LOGIN_PAGE_REGISTRATION_FORM_LAST_NAME), expectedColor);
        if (fields.getLastName().equals("Selected"))
            Assert.assertEquals("Background of " + fields.getAddress(), loginPage.getBackgroundColorOf(Locators.LOGIN_PAGE_REGISTRATION_FORM_ADDRESS), expectedColor);
        if (fields.getLastName().equals("Selected"))
            Assert.assertEquals("Background of " + fields.getPostCode(), loginPage.getBackgroundColorOf(Locators.LOGIN_PAGE_REGISTRATION_FORM_POST_CODE), expectedColor);
        if (fields.getLastName().equals("Selected"))
            Assert.assertEquals("Background of " + fields.getEmail(), loginPage.getBackgroundColorOf(Locators.LOGIN_PAGE_REGISTRATION_FORM_EMAIL), expectedColor);
        if (fields.getLastName().equals("Selected"))
            Assert.assertEquals("Background of " + fields.getPhoneNumber(), loginPage.getBackgroundColorOf(Locators.LOGIN_PAGE_REGISTRATION_FORM_PHONE_NUMBER), expectedColor);
    }

    @Step
    public void clickOnCreateFreeAccountButton() {
        loginPage.clickOnCreateFreeAccountButton();
        Serenity.getCurrentSession().put("customerProfile", expectedCustomerProfile);
    }

    @Step
    public void isAlertDisplayedLoginProfile(String message) throws IOException {
        Assert.assertTrue("Alert with the " + message + " isn't displayed",
                loginPage.isAlertDisplayed(message, currentLanguage));
    }

    @Step
    public void isMyAccountPageOpened() {
        Assert.assertTrue("My account page isn't opened", myAccountPage.getCurrentUrl("https://qa-lloydspharma-be:9002/my-account"));
    }

    @Step
    public void selectMyAccountItemInHeaderMenu() {
        myAccountPage.clickOnMyAccountItemInHeaderMenu();
    }

    @Step
    public void isProfilePageContainsAllRegistrationData() {
        Assert.assertEquals("Mismatch title ", expectedCustomerProfile.getTitle(), myAccountPage.getTitleValue(Locators.MY_ACCOUNT_PAGE_UPDATE_PERSONAL_DETAILS_TITLE_DROP_DOWN));
        Assert.assertEquals("Mismatch first name ", expectedCustomerProfile.getFirstName(), myAccountPage.getAttributeValueOf(Locators.MY_ACCOUNT_PAGE_UPDATE_PERSONAL_DETAILS_FIRST_NAME, "value"));
        Assert.assertEquals("Mismatch last name ", expectedCustomerProfile.getLastName(), myAccountPage.getAttributeValueOf(Locators.MY_ACCOUNT_PAGE_UPDATE_PERSONAL_DETAILS_LAST_NAME, "value"));
        Assert.assertEquals("Mismatch phone number ", expectedCustomerProfile.getPhoneNumber(), myAccountPage.getAttributeValueOf(Locators.MY_ACCOUNT_PAGE_UPDATE_PERSONAL_DETAILS_PHONE_NUMBER, "value"));
        Assert.assertEquals("Mismatch birthday ", expectedCustomerProfile.getBirthDate(), myAccountPage.getAttributeValueOf(Locators.MY_ACCOUNT_PAGE_UPDATE_PERSONAL_DETAILS_BIRTHDAY, "value"));
        Assert.assertEquals("Mismatch additional information ", expectedCustomerProfile.getAdditionalInfo(), myAccountPage.getTextValueOfElement(Locators.MY_ACCOUNT_PAGE_UPDATE_PERSONAL_ADDITIONAL_INFO));
        Assert.assertEquals("Wrong value of CONSENT_FORNEWSLETTER_CHECKBOX", expectedCustomerProfile.getiWantToResiveNewsLetter().toString(), myAccountPage.getAttributeValueOf(Locators.MY_ACCOUNT_PAGE_UPDATE_PERSONAL_DETAILS_CONSENT_FORNEWSLETTER_CHECKBOX, "value"));

        String[] expectedFavoritePharm = expectedFavoritePharmacies.toArray(new String[0]);
        sort(expectedFavoritePharm);
//        Assert.assertTrue("Mismatch Favorite Pharmacies ", Arrays.equals(expectedFavoritePharm, myAccountPage.getFavoritePharmacies()));
    }

    @Step
    public void selectSpecialInterestGroup(List<String> specialGroups) {
        for (String specialGroup : specialGroups) {
            loginPage.selectSpecialInteressegroepRegistration(specialGroup);
        }
        expectedSpecialGroupOfCustomerProfile = specialGroups;
    }

    @Step
    public void clickOnUpdateEmailButton() {
        myAccountPage.clickOnUpdateEmailButton();
    }

    @Step
    public void isUpdateEmailPageContainsEmailFromRegistrationData() {
        Assert.assertEquals("Mismatch emails ", expectedCustomerProfile.getEmail(), myAccountPage.getAttributeValueOf(Locators.MY_ACCOUNT_PAGE_EMAIL, "value"));
    }

    @Step
    public void clickOnManageDeliveryAddresesButton() {
        myAccountPage.clickOn(Locators.MY_ACCOUNT_PAGE_SIDE_MENU_MANAGE_DELIVERY_ADDRESS_ITEM, 10, 0);
    }

    @Step
    public void isAddressBookContainsRegistrationData() {
        String newAddress = myAccountPage.getTextValuesOfElement(Locators.MY_ACCOUNT_PAGE_ADDRESSES).get(0);
        Assert.assertTrue("First Name ", newAddress.contains(expectedCustomerProfile.getFirstName()));
        Assert.assertTrue("Last Name ", newAddress.contains(expectedCustomerProfile.getLastName()));
        Assert.assertTrue("Address ", newAddress.contains(expectedCustomerProfile.getAddress()));
//        Assert.assertTrue("Building ", newAddress.contains(expectedCustomerProfile.getBuilding()));
//        Assert.assertTrue("Box ", newAddress.contains(expectedCustomerProfile.getBox()));
        Assert.assertTrue("Post Code ", newAddress.contains(expectedCustomerProfile.getPostCode()));
        Assert.assertTrue("City " + expectedCustomerProfile.getCity() + " isn't found ", newAddress.contains(expectedCustomerProfile.getCity()));
        Assert.assertTrue("Phone number ", newAddress.contains(expectedCustomerProfile.getPhoneNumber()));
    }

    @Step
    public void enterFavoritePharmacies(List<String> favoritePharmacie) {
        loginPage.enterFavoritePharmaciesRegistration(favoritePharmacie);
        expectedFavoritePharmacies = favoritePharmacie;
    }

    @Step
    public void signIn(String email, String password) {
        loginPage.enterLogin(email);
        loginPage.enterLoginPassword(password);
        loginPage.clickOnSignInButton();
    }

    @Step
    public void isLogoutButtonDisplayed() {
        Assert.assertTrue("Logout button isn't displayed", basePage.isWebElementDisplayed(Locators.BASE_PAGE_HEADER_MENU_LOGOUT_BUTTON));
    }

    @Step
    public void isUpdateProfileButtonDisplayed() {
        Assert.assertTrue("Update Profile button isn't displayed", basePage.isWebElementDisplayed(Locators.BASE_PAGE_HEADER_MENU_MY_ACCOUNT_BUTTON));
    }

    @Step
    public void clickOnLogoutButtonInHeaderMenu() {
        basePage.clickOn(Locators.BASE_PAGE_HEADER_MENU_LOGOUT_BUTTON, 10, 0);
    }

    @Step
    public void isUpdateProfileButtonNotDisplayed() {
        Assert.assertTrue("Update Profile button shouldn't be displayed", !basePage.isUpdateProfileButtonDisplayed());
    }

    @Step
    public void isUpdateProfileButtonContainsRegistrationFirstName() {
        String updateProfileButtonCaption = basePage.getTextValueOfElement(Locators.BASE_PAGE_HEADER_MENU_MY_ACCOUNT_BUTTON);
        Assert.assertTrue("User First Name isn't displayed on button ", updateProfileButtonCaption.contains(expectedCustomerProfile.getFirstName()));
    }

    @Step
    public void checkRegistrationNotification(String reciver, String password, String sender, String subject) throws IOException, MessagingException, InterruptedException, GeneralSecurityException {
        String translatedSubject = basePage.getTranslatedMessage(subject, currentLanguage, basePage.DICTIONARY_EMAIL);
        Message notifMsg;
        notifMsg = EmailUtilities.getEmailMessage(reciver, password, sender, translatedSubject);
        Assert.assertTrue("Email notification isn't found", !notifMsg.equals(null));
        Serenity.getCurrentSession().put("emailMessage", notifMsg);
    }

    @Step
    public void isLetterContainsText(String expectedText) throws IOException, MessagingException {
        String translatedText = basePage.getTranslatedMessage(expectedText, currentLanguage, basePage.DICTIONARY_EMAIL);
        translatedText = translatedText.replace("BaseURL", BASE_URL);
        Message notifMsg;
        notifMsg = (Message) Serenity.getCurrentSession().get("emailMessage");
        Assert.assertTrue("Mismatch content", (((Multipart) notifMsg.getContent()).getBodyPart(0).getContent().toString()).contains(translatedText));
    }

    @Step
    public void updateProfileEmailToNewGeneratedEmail(String password) {
        String generatedEmail;
        String textDate;
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        textDate = dateFormat.format(date).replace("/", "");
        textDate = textDate.replace(" ", "");
        textDate = textDate.replace(":", "");
        generatedEmail = "lp" + textDate + "@gmail.com";
        Serenity.getCurrentSession().put("generatedEmail", generatedEmail);
        myAccountPage.enterTextIntoFieldInUpdateEmailForm(Locators.MY_ACCOUNT_PAGE_UPDATE_EMAIL_EMAIL, generatedEmail);
        myAccountPage.enterTextIntoFieldInUpdateEmailForm(Locators.MY_ACCOUNT_PAGE_UPDATE_EMAIL_REENTER_EMAIL, generatedEmail);
        //myAccountPage.enterTextIntoFieldInUpdateEmailForm(Locators.MY_ACCOUNT_PAGE_UPDATE_EMAIL_PASSWORD, expectedCustomerProfile.getPassword());
        myAccountPage.enterTextIntoFieldInUpdateEmailForm(Locators.MY_ACCOUNT_PAGE_UPDATE_EMAIL_PASSWORD, password);
        myAccountPage.clickOnUpDateEmailButton(Locators.MY_ACCOUNT_PAGE_UPDATE_EMAIL_CHANGE_EMAIL_BUTTON);
    }

    @Step
    public void isUpdatePersonalDetailsOpened() {
        Assert.assertTrue("Updating persanal details page isn't displayed", myAccountPage.isDisplayedUpdatePersonalDetailsForm());
    }

    @Step
    public void updatePhoneNumberToTheNewGeneratedOne() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        String generatedPhoneNumber = "+" + dateFormat.format(date).replace("/", "");
        generatedPhoneNumber = generatedPhoneNumber.replace(" ", "");
        generatedPhoneNumber = generatedPhoneNumber.replace(":", "");
        Serenity.getCurrentSession().put("generatedPhoneNumber", generatedPhoneNumber);
        myAccountPage.enterTextIntoField(Locators.MY_ACCOUNT_PAGE_UPDATE_PERSONAL_DETAILS_PHONE_NUMBER, generatedPhoneNumber);
        myAccountPage.clickOnSaveButton();
    }

    @Step
    public void loginWithNewUpdatedEmail(String password) throws InterruptedException {
        loginPage.enterLogin((String) Serenity.getCurrentSession().get("generatedEmail"));
        for (int i = 1; i <= 10; i++) {
            Thread.sleep(5000);
            try {
                loginPage.enterLoginPassword(password);
                loginPage.clickOnSignInButton();
                break;
            } catch (Exception e) {
            }
        }
    }

    @Step
    public void enterTitleIntoRegistrationForm(String title) {
        loginPage.selectTitleRegistration(title);
    }

    @Step
    public void enterFirstNameIntoRegistrationForm(String firstName) {
        loginPage.enterFirstNameRegistration(firstName);
    }

    @Step
    public void enterLastNameIntoRegistrationForm(String lastName) {
        loginPage.enterLastNameRegistration(lastName);
    }

    @Step
    public void enterPhoneNumberIntoRegistrationForm(String phoneNumber) {
        loginPage.enterPhoneNumberRegistration(phoneNumber);
    }

    @Step
    public void enterAddressIntoRegistrationForm(String address) {
        loginPage.enterAddressRegistration(address);
    }

    @Step
    public void enterBuildingIntoRegistrationForm(String building) {
        loginPage.enterBuildingRegistration(building);
    }

    @Step
    public void enterBoxIntoRegistrationForm(String box) {
        loginPage.enterBoxRegistration(box);
    }

    @Step
    public void enterPostCodeIntoRegistrationForm(String posteCode) {
        loginPage.enterPostCodeRegistration(posteCode);
    }

    @Step
    public void enterCityIntoRegistrationForm(String city) {
        loginPage.enterCityRegistration(city);
    }

    @Step
    public void enterEmailIntoRegistrationForm(String email) {
        loginPage.enterEmailRegistration(email);
    }

    @Step
    public void enterPasswordIntoRegistrationForm(String password) {
        loginPage.enterPasswordRegistration(password);
    }

    @Step
    public void enterPasswordConfirmationIntoRegistrationForm(String passwordConfirmation) {
        loginPage.enterConfirmPasswordRegistration(passwordConfirmation);
    }

    @Step
    public void isErrorMessageDisplayed(String message) throws IOException {
        loginPage.isErrorDisplayed(message, (String) Serenity.getCurrentSession().get("carrentLanguage"));
    }

    @Step
    public void clickOnGlobalSearchButton() throws InterruptedException {
        basePage.clickOn(Locators.BASE_PAGE_GLOBAL_SEARCH_BUTTON, 10, 0);
    }

    @Step
    public void searchForProduct(Product product) throws IOException {
        basePage.searchForProduct(product.getProductName());
    }

    @Step
    public void isMiniShoppingCartDisplayed() {
        Assert.assertTrue("Mini shopping cart isn't appeared", shoppingCart.isMiniShoppingCartDisplayed());
    }

    @Step
    public void isCounterOnShoppingCartEqual(String value) {
        Assert.assertEquals(value, basePage.getCounterOfShoppingCart());
    }

    @Step
    public void isMiniShoppingCartContains(List<Product> expectedProducts) throws IOException {
        for (Product expectedProduct : expectedProducts) {
            expectedProduct.setProductName(basePage.getTranslation(expectedProduct.getProductName(), currentLanguage, basePage.DICTIONARY_PRODUCTS));
            Assert.assertEquals(expectedProduct.toString(), shoppingCart.getProductFromMiniCart(expectedProduct.getProductCode()).toString());
        }
    }

    @Step
    public void clickOnGoCartButtonOnMiniCart() {
        shoppingCart.clickOnGoCartButtonOnMiniCart();
    }

    @Step
    public void clickOnCartButtonInHeader() throws InterruptedException {
        shoppingCart.clickOnCartButtonInHeader();
    }

    @Step
    public void selectCatagoryFromHeaderProductsDropDownn(String category, String subCategory) throws IOException {
        basePage.selectProductCategoryFromHeaderDropDown(currentLanguage, category, subCategory);
    }

    @Step
    public void moveToProductsDropDownInHeader() {
        basePage.moveToProductsDropDownInHeader();
    }

    @Step
    public void isTitleOfTheProductsPage(String expectedTitle) throws IOException {
        Assert.assertEquals(searchResultsPage.getTranslation(expectedTitle, currentLanguage),
                searchResultsPage.getTextValueOfElement(Locators.SEARCH_RESULTS_PAGE_TITLE));
    }

    @Step
    public void clickOnCheckoutButtonInTheCart() throws InterruptedException {
        expectedOrder.setOrderNumber(checkoutPage.getOrderNumber());
        expectedOrder.setTotalOrder(checkoutPage.getOrderTotal());
        shoppingCart.clickOn(Locators.SHOPPING_CART_CHECKOUT_BUTTON, 10, -100);
    }

    @Step
    public void selectShippingMethodSendToAddress() {
        checkoutPage.clickOn(Locators.CHECKOUT_SEND_BY_MAIL_CHECKBOX, 10);
        expectedOrder.setShipmentMethod("To Be Shipped");
    }

    @Step
    public void clickOnNextButtonOnTheFirstStepOfCheckout() {
        checkoutPage.clickOnNextOnFirstStep();
    }

    @Step
    public void selectOptionGetAddressFromYourProfile() {
        checkoutPage.selectOptionGetAddressFromYourProfile();
    }

    @Step
    public void enterCredentialsOnStep2Checkout(String email, String password) {
        checkoutPage.enterTextIntoField(Locators.CHECKOUT_EMAIL, email);
        checkoutPage.enterTextIntoField(Locators.CHECKOUT_EMAIL_PASSWORD, password);
    }

    @Step
    public void clickOnFurtherToTheShippingAndPaymentButton() throws InterruptedException {
        checkoutPage.clickOn(Locators.CHECKOUT_FURTHER_SHIPPING_PAYMENT_BUTTON, 20, -100);
    }

    @Step
    public void isCurrentURL(String url) throws InterruptedException {
        Assert.assertTrue(basePage.getCurrentUrl(url.replace("BASE_URL", BASE_URL)));
    }

    @Step
    public void selectDeliveryMethodInIframe(String method) throws IOException {
        String translatedMethod = checkoutPage.getTranslation(method, currentLanguage, checkoutPage.DICTIONARY_CHECKOUT);
        checkoutPage.selectDeliveryMethodInIframe(translatedMethod);
//        expectedOrder.setDeliveryMethod(translatedMethod);
    }

    @Step
    public void selectDeliveryAddress(String address) {
        checkoutPage.selectDeliveryAddress(address);
    }

    @Step
    public void clickOnFurtherButtonOnStep2() {
        expectedOrder.setDeliveryAddress(deliveryAddress);
        checkoutPage.clickOnFurtherButtonOnStep2();
    }

    @Step
    public void enterValueIntoTheFieldOnDeliveryPlaceStep(String value, String fieldName) {
        Address address = new Address();
        checkoutPage.enterValueIntoTheFieldOnDeliveryPlaceStep(value, fieldName);
        if (fieldName.equals("House number")) {
            address = expectedOrder.getDeliveryAddress();
            address.setAddress1(address.getAddress1() + " " + value);
            expectedOrder.setDeliveryAddress(address);
        }
    }

    @Step
    public void clickOnNextButtonOnDeliveryPlaceStep() {
        checkoutPage.clickOnNextButtonOnDeliveryPlaceStep();
        expectedOrder.setTotalOrder(checkoutPage.getOrderTotalWithPriceDeliveryBpost());
    }

    @Step
    public void selectOptionContinueWithoutProfileForDeliveryAddress() {
        checkoutPage.clickOn(checkoutPage.CHECKOUT_DELIVERY_ADDRESS_WITHOUT_PROFILE, 10);
    }

    @Step
    public void selectTitleForProfileDetailsForDeliveryAddress(String title) throws IOException {
        checkoutPage.selectTitleForProfileDetailsForDeliveryAddress(title, currentLanguage);
    }

    @Step
    public void buyNowButtonShouldnTBeDisplayedForProductsWithStatus(String status) throws IOException {
        String translatedStatus = basePage.getTranslation(status, currentLanguage, basePage.DICTIONARY_CHECKOUT);
        String PRODUCT_CONTAINERS = "(.//span[contains(text(),'" + translatedStatus + "')]/ancestor::article)";
        Boolean buttonDisplayed;
        for (int i = 1; i < searchResultsPage.findAll(PRODUCT_CONTAINERS).size(); i++) {
            searchResultsPage.scrollIntoView(PRODUCT_CONTAINERS + "[" + i + "]");
            searchResultsPage.moveTo(PRODUCT_CONTAINERS + "[" + i + "]");
            try {
                searchResultsPage.clickOn(PRODUCT_CONTAINERS + "[" + i + "]" + ".//input/following-sibling::button[@type='submit']", 10, 0);
                buttonDisplayed = true;
            } catch (Exception e) {
                buttonDisplayed = false;
            }
            Assert.assertTrue("Buy now button is displayed for product with status " + status, !buttonDisplayed);
        }
    }

    @Step
    public void isNotDisplayedOutOfStockButtonOnProductDetailsPage() {
        Assert.assertTrue(!productDetailsPage.isBuyNowButtonClickable());
    }

    @Step
    public void isCartEmpty() throws IOException {
        Assert.assertTrue(checkoutPage.isCartEmpty(currentLanguage));
    }

    @Step
    public void selectItemInHeaderMenu(String itemName) throws IOException {
        basePage.selectItemInHeaderMenu(itemName, currentLanguage);
    }

    @Step
    public void uploadPrescriptionScan(String filePath) throws AWTException {
        myPrescriptionPage.uploadPrescriptionScan(filePath);
    }

    @Step
    public void enterValueIntoTheFieldOfMyPrescriptionForm(String value, String fieldName) throws IOException {
        myPrescriptionPage.enterValueIntoTheField(value, fieldName, currentLanguage);
    }

    @Step
    public void selectOptionForCommunicationOfMyPrescriptionForm(String optionName) {
        myPrescriptionPage.selectCommunicationOption(optionName);
    }

    @Step
    public void enterIntoCardNumberFieldOnIngenicoPage(String cardNumber) {
        injenicoPage.enterCardNumber(cardNumber);
    }

    @Step
    public void selectMonthOfExpirationDateOnIngenicoPage(String month) {
        injenicoPage.selectMonthOfExpirationDate(month);
    }

    @Step
    public void selectYearDropDownOfExpirationDateOnIngenicoPage(String year) {
        injenicoPage.selectYearOfExpirationDate(year);
    }

    @Step
    public void clickOnConfirmMyOrderButtonOnIngenicoPage() {
        injenicoPage.clickOnConfirmMyOrderButton();
    }

    @Step
    public void isOrderDetailsPageDisplayedWithTitle(String title) throws IOException {
        List<String> createdOrderListJson = new ArrayList<>();
        String expectedTitle = basePage.getTranslation(title, currentLanguage, basePage.DICTIONARY_ORDER);
        Assert.assertEquals(expectedTitle, orderConfirmationPage.getOrderConfirmationTitle());
        createdOrder = orderConfirmationPage.getOrderDetails();
        createdOrder.setShipmentMethod(expectedOrder.getShipmentMethod());
        createdOrder.setTestNumber(expectedOrder.getTestNumber());

        Gson gson = new Gson();
        if ((List<String>) Serenity.getCurrentSession().get("CreatedOrderListJson") == null) {
            createdOrderListJson.add(gson.toJson(createdOrder));
        } else {
            createdOrderListJson = (List<String>) Serenity.getCurrentSession().get("CreatedOrderListJson");
            createdOrderListJson.add(gson.toJson(createdOrder));
        }
        Serenity.getCurrentSession().put("CreatedOrderListJson", createdOrderListJson);

        try {
            String filename = "./src/test/resources/data/createdOrdersNumbers.txt";
            FileWriter fw = new FileWriter(filename, true); //true-append
            fw.write(createdOrder.getOrderNumber() + "\n");
            fw.close();
        } catch (IOException ioe) {
        }
    }

    @Step
    public void enterValueInProfileDetailsForDeliveryAddress(String value, String fieldName) throws IOException {
        checkoutPage.enterValueIntoDeliveryAdressField(value, fieldName, currentLanguage);
    }

    @Step
    public void clickOnPlaceOrderButton() {
        checkoutPage.clickOnPlaceOrderButton();
    }

    @Step
    public void clickOnNextButtonOnDeliveryOptionStep() {
        checkoutPage.clickOnNextButtonOnDeliveryOptionStep();
    }

    @Step
    public void selectShippingMethodGetInPharmacyOrDeliveryLocation() {
        checkoutPage.selectShippingMethodGetInPharmacyOrDeliveryLocation();
        expectedOrder.setShipmentMethod("To Be Picked Up");
    }

    @Step
    public void searchDesiredPharmacyForDeliveryLocation(String pharmacyName) {
        checkoutPage.searchDesiredPharmacyForDeliveryLocation(pharmacyName);
    }

    @Step
    public void selectDesiredPharmacyInSearchResults(String pharmacyName) {
        expectedOrder.setDeliveryAddress(checkoutPage.selectDesiredPharmacyInSearchResults(pharmacyName));
    }

    @Step
    public void isDisplayedInShippingMethodSection(String pharmacyName) {
        Assert.assertEquals(pharmacyName, checkoutPage.getSelectedPharmacyForShippingMethod());

    }

    @Step
    public void enterIntoTheFieldForReminder(String value, String fieldName) throws IOException {
        String translatedFieldName = basePage.getTranslation(fieldName, currentLanguage, basePage.DICTIONARY_CHECKOUT);
        checkoutPage.enterIntoTheFieldForReminder(value, translatedFieldName);
    }

    @Step
    public void checkNotificationType(String notificationType) {
        checkoutPage.checkNotificationType(notificationType);
    }

    @Step
    public void enterAnOrderNotes(String notes) {
        checkoutPage.enterAnOrderNotes(notes);
        expectedOrder.setOrderNote(notes);
    }

    @Step
    public void clickOnNextButtonOnStep1InCaseGetInPharmacy() {
        checkoutPage.clickOnNextButtonOnStep1InCaseGetInPharmacy();
    }

    @Step
    public void clickOnAddNewAddressForDelivery() {
        checkoutPage.clickOnAddNewDeliveryAddressButton();
    }

    @Step
    public void selectValueFromLandFieldForNewDeliveryAddress(String value) {
        checkoutPage.selectLandForNewDeliveryAddress(value);
    }

    @Step
    public void enterIntoFieldForNewDeliveryAddress(String value, String fieldName) throws IOException {
        checkoutPage.enterFieldValueOfNewDeliveryAddress(value, fieldName, currentLanguage);
        switch (fieldName) {
            case "First Name *":
                deliveryAddress.setFirstName(value);
                break;
            case "Last Name *":
                deliveryAddress.setLastName(value);
                break;
            case "Addresss 1 *":
                deliveryAddress.setAddress1(value);
                break;
            case "Building *":
                deliveryAddress.setBuilding(value);
                break;
            case "Box":
                deliveryAddress.setBox(value);
                break;
            case "Addresss 2":
                deliveryAddress.setAddress2(value);
                break;
            case "Mobile number *":
                deliveryAddress.setPhoneNumber(value);
                break;
            case "Post code *":
                deliveryAddress.setPostCode(value);
                break;
            case "City":
                deliveryAddress.setCity(value);
                break;
        }
    }

    @Step
    public void clickOnSaveAddressInMyAddressBookCheckbox() {
        checkoutPage.clickOnSaveAddressInMyAddressBookCheckbox();
    }

    @Step
    public void isOrderNumberOnConfirmationPageEqualToOrderNumberInShoppingCart() {
        Assert.assertEquals("Mismatch expectedOrder numbers", expectedOrder.getOrderNumber(), createdOrder.getOrderNumber());
    }

    @Step
    public void isAllAddedToCartProductsWithAppropriateQtyAndPriceInTheOrder() throws IOException {
        Assert.assertEquals("Mismatch amount of products.", (Integer) expectedOrder.getProducts().size(), orderConfirmationPage.getProductsAmount());
        for (Product expectedProduct : expectedOrder.getProducts()) {
            Assert.assertEquals(expectedProduct.toString(), orderConfirmationPage.getProduct(expectedProduct.getProductCode()).toString());
        }
    }

    @Step
    public void isOrderTotalEqualToTotalOfShoppingCart() {
        Assert.assertEquals("Total in Order isn't equal to Total in the Cart", expectedOrder.getTotalOrder()
                .replace("€", "").trim(), createdOrder.getTotalOrder().replace("€", "").trim());
    }

    @Step
    public void selectOrderHistoryItemFromLeftSideMenu() {
        myAccountPage.selectOrderHistory();
    }

    @Step
    public void isCreatedOrderAppearedInOrderHistoryList() {
        Assert.assertTrue("Created Order isn't found in History" + expectedOrder.getOrderNumber(), myAccountPage.searchForOrderInOrderHistory(expectedOrder.getOrderNumber()));
    }

    @Step
    public void isPriceOfCreatedOrderInOrderHistoryListEqualToCart() {
        Assert.assertEquals(expectedOrder.getTotalOrder().replace("€", "").replace(" ", ""), myAccountPage.getTotalOfOrderFromHistoryOrder(expectedOrder.getOrderNumber()));
    }

    @Step
    public void isUploadedFileDisplayedInThePrescriptionForm(String fileName) {
        Assert.assertEquals(fileName, myPrescriptionPage.getUploadedPrescriptionFileName());
    }

    @Step
    public void clickOnYourPharmacyButtonOfMyPrescriptionForm() {
        myPrescriptionPage.clickOnYourPharmacyButton();
    }

    @Step
    public void enterValueIntoSearchFieldInFindYourPharmacyPopUp(String value) {
        myPrescriptionPage.enterValueIntoPharmacySearchField(value);
    }

    @Step
    public void clickOnFindPharmacyButtonInFindYourPharmacyPopUp() {
        myPrescriptionPage.clickOnFindPharmacyButton();
    }

    @Step
    public void choosePharmacyFromSearchResultsInFindYourPharmacyPopUp(Integer index) {
        Address address = new Address();
        address = myPrescriptionPage.choosePharmacyFromSearchResults(index);
        expectedOrder.setDeliveryAddress(address);
        Serenity.getCurrentSession().put("chosenPharmacy", address.getFirstName());

    }

    @Step
    public void closeFindYourPharmacyPopUp() {
        myPrescriptionPage.closeFindYourPharmacyPopUp();
    }

    @Step
    public void clickOnSendPrescriptionButton() {
        myPrescriptionPage.clickOnSendPrescriptionButton();
    }

    @Step
    public void isButtonDisplayedForPharmacyNumber(String buttonName, Integer index) throws IOException {
        Assert.assertEquals(basePage.getTranslation(buttonName, currentLanguage, basePage.DICTIONARY_MY_PRESCRIPTION),
                myPrescriptionPage.getChoosePharmacyButtonTextOfPharmacyNumber(index));
    }

    @Step
    public void isChosenPharmacyDisplayedInPrescriptionForm() {
        Assert.assertEquals("Chosen Pharmacy should be " + Serenity.getCurrentSession().get("chosenPharmacy") + " :",
                Serenity.getCurrentSession().get("chosenPharmacy"), myPrescriptionPage.getChosenPharmacyFromPrescriptionForm());
    }

    @Step
    public void isEReceiptPageOpenedWithMessages(List<String> messages) throws IOException {
        Assert.assertEquals(basePage.getTranslation(messages.get(0), currentLanguage, basePage.DICTIONARY_ALERT_MESSAGES), myPrescriptionPage.getMessagesOrderPlaced().get(0));
        Assert.assertEquals(basePage.getTranslation(messages.get(1), currentLanguage, basePage.DICTIONARY_ALERT_MESSAGES), myPrescriptionPage.getMessagesOrderPlaced().get(1));
    }

    @Step
    public void isPrescriptionOrderCommentDisplayed(String comment) {
        Assert.assertEquals(comment, myPrescriptionPage.getOrderComment());
        expectedOrder.setOrderNote(comment);
    }

    @Step
    public void isPickupPharmacyEqualToPharmacyFromPrescriptionForm() {
        Assert.assertEquals(Serenity.getCurrentSession().get("chosenPharmacy"), myPrescriptionPage.getPickUpOrderPharmacy());
    }

    @Step
    public void downloadPrescriptionFile() {
        myPrescriptionPage.clickOnDownloadPrescriptionButton();
    }

    @Step
    public void clickOnRemoveButtonForUploadedPrescription() {
        myPrescriptionPage.clickOnRemoveButtonForUploadedPrescription();
    }

    @Step
    public void isUploadedFileNameNotDisplayedInThePrescriptionForm() {
        Assert.assertTrue(myPrescriptionPage.getUploadedPrescriptionFileName() == null);
    }

    @Step
    public void selectManageAddressItemFromSideMenuOfMyAccount() {
        myAccountPage.selectManageAddressItemFromLeftSideMenu();
    }

    @Step
    public void isProfileContainsAddress(Address expectedAddress) {
        Boolean flag = false;
        for (int i = 0; i < myAccountPage.getAllProfileAddresses().size(); i++) {
            if ((myAccountPage.getAllProfileAddresses().get(i).toString()).equals(expectedAddress.toString())) {
                flag = true;
                break;
            }
        }
        Assert.assertTrue("Profile isn't contained address " + expectedAddress.toString(), flag);
    }


    @Step
    public void isFileExistInFolder(String fileName, String path) throws IOException, InterruptedException {
        File file;
        file = new File(path + fileName);
        Thread.sleep(5000);
        Assert.assertTrue(file.exists());
        Files.deleteIfExists(file.toPath());
    }

    @Step
    public void isEachPharmacyNameInSearchResultsContains(String pharmacyName) {
        Assert.assertTrue(myPrescriptionPage.getAllPharmacyNamesFromSearchResults().contains(pharmacyName));
    }

    @Step
    public void isDeliveryAddressOnOrderConfirmationPageEqualToNewAddedAddress(Address expectedAddress) {
        expectedOrder.setDeliveryAddress(expectedAddress);
        Assert.assertEquals(expectedOrder.getDeliveryAddress().getFirstName(), orderConfirmationPage.getOrderDetails().getDeliveryAddress().getFirstName());
        Assert.assertEquals(expectedOrder.getDeliveryAddress().getLastName(), orderConfirmationPage.getOrderDetails().getDeliveryAddress().getLastName());
        Assert.assertEquals(expectedOrder.getDeliveryAddress().getAddress1() + expectedOrder.getDeliveryAddress().getAddress2(),
                orderConfirmationPage.getOrderDetails().getDeliveryAddress().getAddress1());
        Assert.assertEquals(expectedOrder.getDeliveryAddress().getPostCode(), orderConfirmationPage.getOrderDetails().getDeliveryAddress().getPostCode());
        Assert.assertEquals(expectedOrder.getDeliveryAddress().getCity(), orderConfirmationPage.getOrderDetails().getDeliveryAddress().getCity());
        //Assert.assertEquals(expectedOrder.getDeliveryAddress().getPhoneNumber(), orderConfirmationPage.getOrderDetails(false).getDeliveryAddress().getPhoneNumber());
    }

    @Step
    public void isPaymentAddressOnOrderConfirmationPageEqualToNewAddedAddress(Address expectedAddress) {
        expectedOrder.setPaymentAddress(expectedAddress);
        Assert.assertEquals(expectedOrder.getPaymentAddress().getFirstName(), orderConfirmationPage.getOrderDetails().getPaymentAddress().getFirstName());
        Assert.assertEquals(expectedOrder.getPaymentAddress().getLastName(), orderConfirmationPage.getOrderDetails().getPaymentAddress().getLastName());
        Assert.assertEquals(expectedOrder.getPaymentAddress().getAddress1() + expectedOrder.getDeliveryAddress().getAddress2(),
                orderConfirmationPage.getOrderDetails().getPaymentAddress().getAddress1());
        Assert.assertEquals(expectedOrder.getPaymentAddress().getPostCode(), orderConfirmationPage.getOrderDetails().getPaymentAddress().getPostCode());
        Assert.assertEquals(expectedOrder.getPaymentAddress().getCity(), orderConfirmationPage.getOrderDetails().getPaymentAddress().getCity());
        // Assert.assertEquals(expectedOrder.getPaymentAddress().getPhoneNumber(), orderConfirmationPage.getOrderDetails(false).getPaymentAddress().getPhoneNumber());
    }

    @Step
    public void goThroughIngenicoAdditionalSecurityPages(String password) {
        injenicoPage.goThroughAdditionalSecurity(password);
    }

    @Step
    public void clickOnAddNewAddressButton() {
        expectedOrder.setDeliveryAddress(deliveryAddress);
        checkoutPage.clickOnAddNewAddressButton();
    }

    @Step
    public void isDeliveryAddressOnTheOrderConfirmationPageEqualTo(Address defaultAddress) {
        Assert.assertEquals(defaultAddress.getFirstName(), orderConfirmationPage.getOrderDetails().getDeliveryAddress().getFirstName());
        Assert.assertEquals(defaultAddress.getLastName(), orderConfirmationPage.getOrderDetails().getDeliveryAddress().getLastName());
        Assert.assertEquals(defaultAddress.getAddress1(), orderConfirmationPage.getOrderDetails().getDeliveryAddress().getAddress1());
        Assert.assertEquals(defaultAddress.getPostCode(), orderConfirmationPage.getOrderDetails().getDeliveryAddress().getPostCode());
        Assert.assertEquals(defaultAddress.getCity(), orderConfirmationPage.getOrderDetails().getDeliveryAddress().getCity());
    }

    @Step
    public void isPaymentAddressOnTheOrderConfirmationPageEqualTo(Address expectedAddress) throws IOException {
        if (expectedOrder.getShipmentMethod().equals("To Be Picked Up")) {
            expectedAddress.setFirstName(basePage.getTranslation(expectedAddress.getFirstName(), currentLanguage, basePage.DICTIONARY_ORDER));
            Assert.assertEquals(expectedAddress.getFirstName(), orderConfirmationPage.getOrderDetails().getPaymentAddress().getFirstName());
        } else {
            Assert.assertEquals(expectedAddress.getFirstName(), orderConfirmationPage.getOrderDetails().getPaymentAddress().getFirstName());
            Assert.assertEquals(expectedAddress.getLastName(), orderConfirmationPage.getOrderDetails().getPaymentAddress().getLastName());
            Assert.assertEquals(expectedAddress.getAddress1(), orderConfirmationPage.getOrderDetails().getPaymentAddress().getAddress1());
            Assert.assertEquals(expectedAddress.getPostCode(), orderConfirmationPage.getOrderDetails().getPaymentAddress().getPostCode());
            Assert.assertEquals(expectedAddress.getCity(), orderConfirmationPage.getOrderDetails().getPaymentAddress().getCity());
        }
        expectedOrder.setPaymentAddress(expectedAddress);
    }

    @Step
    public void confirmUsingCookies() {
        basePage.confirmUsingCookies();
    }

    @Step
    public void generatePhoneNumberkeyPhone(String keyPhone) {
        String generatedEmail;
        String textDate;
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        textDate = dateFormat.format(date).replace("/", "");
        textDate = textDate.replace(" ", "");
        textDate = textDate.replace(":", "");
        generatedEmail = "+" + textDate;
        Serenity.getCurrentSession().put(keyPhone, generatedEmail);
    }

    @Step
    public void removeAddressFromCustomerProfile(Address address) {
        myAccountPage.removeAddressFromCustomerProfile(address);
    }

    @Step
    public void isCustomerProfileNotContainsAddress(Address address) {
        for (int i = 0; i < myAccountPage.getAllProfileAddresses().size(); i++) {
            Assert.assertTrue("Profile contains removed address: " + address.toString(), !myAccountPage.getAllProfileAddresses().get(i).toString().equals(address.toString()));
        }
    }

    @Step
    public void setAddressAsDefaultForCustomerProfile(Address address) {
        myAccountPage.setAddressAsDefaultForCustomerProfile(address);
    }

    @Step
    public void isFirstAddressInTheAddressesListOfTheCustomerProfile(Address address) {
        Assert.assertEquals(address.toString(), myAccountPage.getAllProfileAddresses().get(0).toString());
    }

    @Step
    public void clickOnAddNewAddressButtonForAddingAddressToTheCustomerProfile() {
        myAccountPage.clickOnAddNewAddressButton();
    }

    @Step
    public void clickOnModifyButtonForCustomerProfileAddress(Address address) {
        myAccountPage.clickOnModifyButtonForCustomerProfileAddress(address);
    }

    @Step
    public void clickOnSaveModifiedAddressButton() {
        myAccountPage.clickOnSaveModifiedAddressButton();
    }

    @Step
    public void generateNewCustomerEmail(String keyEmail) {
        String generatedEmail;
        String textDate;
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        textDate = dateFormat.format(date).replace("/", "");
        textDate = textDate.replace(" ", "");
        textDate = textDate.replace(":", "");
        generatedEmail = "lp" + textDate + "@gmail.com";
        Serenity.getCurrentSession().put(keyEmail, generatedEmail);
    }

    @Step
    public void selectOptionCreateNewUser() {
        checkoutPage.selectCreateNewUserOption();
    }

    @Step
    public void enterPasswordAndConfirmationForNewUserProfile(String password) {
        checkoutPage.enterPasswordAndConfirmation(password);
    }

    @Step
    public void clickOnBPOSTButton() {
        checkoutPage.clickOnBPOSTButton();
    }

    @Step
    public void selectPaymentMethod(String paymentMethod) {
        checkoutPage.selectPaymentMethod(paymentMethod);
    }

    @Step
    public void enterAnOrderNotesForGuestCheckout(String notes) {
        checkoutPage.enterAnOrderNotesForGuestCheckout(notes);
        expectedOrder.setOrderNote(notes);
    }

    @Step
    public void isSectionDisplayedWithAddressInOrderSummary(String sectionName, Address expectedAddress) throws IOException {
        String expdAddr = expectedAddress.getFirstName() + " " + expectedAddress.getLastName() + ", " + expectedAddress.getAddress1() + " "
                + expectedAddress.getBuilding() + " " + expectedAddress.getBox() + ", " + expectedAddress.getAddress2() + ", "
                + expectedAddress.getPostCode() + " " + expectedAddress.getCity() + ", " + expectedAddress.getLand() + ", "
                + expectedAddress.getPhoneNumber();
        expectedOrder.setDeliveryAddress(expectedAddress);
        Assert.assertEquals(basePage.getTranslatedMessage(sectionName, currentLanguage, basePage.DICTIONARY_CHECKOUT), checkoutPage.isTitleShipToDisplayed());

        Assert.assertEquals(expdAddr.toUpperCase(), checkoutPage.getAddressFromShipToSection().toString().toUpperCase());
    }

    @Step
    public void isButtonDisplayedInOrderSummary(String buttonName) {
        Assert.assertTrue(checkoutPage.isButtonDisplayedInOrderSummary(buttonName));
    }

    @Step
    public void isDeliveryMethodDisplayedInShipToSection(String expectedDeliveryMethod) {
        Assert.assertTrue(checkoutPage.getDeliveryMethod().contains(expectedDeliveryMethod));
    }

    @Step
    public void selectStepOfTheCheckout(String stepName) throws IOException {
        checkoutPage.selectStepOfCheckout(stepName, currentLanguage);
    }

    @Step
    public void clearCustomerShoppingCart(String email, String password) throws InterruptedException, IOException {
        loginRestApi(email, password);
//        homePage.openHomePage(BASE_URL);
//        try {
//            loginPage.clickOnNewUserLoginButton();
//            signIn(email, password);
//        } catch (Exception e) {
//        }
//        if (!shoppingCart.getCartCount().equals("")) {
//            shoppingCart.clickOnCartButtonInHeader();
//            shoppingCart.deleteAllProductsFromTheShoppingCart();
//        }
//        basePage.clickOn(Locators.BASE_PAGE_HEADER_MENU_LOGOUT_BUTTON);
    }

    @Step
    public void selectItemInMainMenu(String itemName) throws IOException {
        homePage.selectMainMenuItem(itemName, currentLanguage);
    }

    @Step
    public void removeAllProductsFromTheCart() throws InterruptedException {
        if (!shoppingCart.getCartCount().equals("")) {
            shoppingCart.clickOnCartButtonInHeader();
            shoppingCart.deleteAllProductsFromTheShoppingCart();
        }
    }

    @Step
    public void checkEmailNotificationOrderCreation(String email, String password, String sender, String subject) throws IOException, MessagingException, InterruptedException, GeneralSecurityException {
        Message notifMsg;
        String subjectContext = subject;
        subjectContext = subjectContext.replace("CreatedOrderNumber", expectedOrder.getOrderNumber());
        notifMsg = EmailUtilities.getEmailMessage(email, password, sender, subjectContext);
        Assert.assertTrue("Email notification of the Order creationisn't found", !notifMsg.equals(null));
        Serenity.getCurrentSession().put("emailMessage", notifMsg);
    }

    @Step
    public void isOrderTotalInEmailOrderConformationEqualToCreatedOrder() throws MessagingException, SAXException, DocumentException, JDOMException, IOException {
        Message notifMsg;
        String emailOrderTotal;
        notifMsg = (Message) Serenity.getCurrentSession().get("emailMessage");
        emailOrderTotal = EmailUtilities.getOrderTotalFromEmail(notifMsg).replace(",", ".").replace("-", "0").replace("€", "").trim();
        Assert.assertEquals(Double.valueOf(expectedOrder.getTotalOrder().replace("-", "00").replace(",", ".").replace("€", "").trim()), Double.valueOf(emailOrderTotal));
    }

    @Step
    public void clickOnTermsAndConditionsButtonOnCheckout() {
        checkoutPage.clickOnTermsAndConditionsButtonOnCheckout();
    }

    @Step
    public void isTitleOfTermsAndConditionsPopUp(String expectedTitle) throws IOException {
        String translatedExpectedTitle = basePage.getTranslation(expectedTitle, currentLanguage, basePage.DICTIONARY_CHECKOUT);
        Assert.assertEquals(translatedExpectedTitle, checkoutPage.getTitleOfTermsAndConditionsPopUp());
    }

    @Step
    public void isTermsAndConditionsPopUpContains(List<String> expectedSubTitles) throws IOException {
        String translatedExpectedSubTitle;
        List<String> actualSubTitles = new ArrayList<>();
        for (int i = 0; i < expectedSubTitles.size(); i++) {
            translatedExpectedSubTitle = basePage.getTranslation(expectedSubTitles.get(i), currentLanguage, basePage.DICTIONARY_CHECKOUT);
            Assert.assertTrue(checkoutPage.isTermsAndConditionsPopUpContains(translatedExpectedSubTitle));
        }
    }

    @Step
    public void clickOnCloseButtonOfTheTermsAndConditionsPopUp() {
        checkoutPage.clickOnCloseButtonOfTheTermsAndConditionsPopUp();
    }

    @Step
    public void isPageOpened(String expectedUrl) {
        Assert.assertTrue("URL " + basePage.waitForUrl(expectedUrl) + " - isn't contained " + expectedUrl,
                basePage.waitForUrl(expectedUrl).contains(expectedUrl.replace("BaseUrl", BASE_URL)));
    }

    @Step
    public void selectUpdatePasswordItemFromSideMenuOfMyAccount() {
        myAccountPage.selectUpdatePasswordItemFromSideMenuOfMyAccount();
    }

    @Step
    public void enterValueIntoCurrentPasswordFieldOnUpdatingPasswordForm(String password) {
        myAccountPage.enterCurrentPasswordOnUpdatingPasswordForm(password);
    }

    @Step
    public void enterValueIntoNewPasswordFieldOnUpdatingPasswordForm(String password) {
        myAccountPage.enterNewPasswordOnUpdatingPasswordForm(password);
    }

    @Step
    public void enterValueIntoConfirmNewPasswordFieldOnUpdatingPasswordForm(String newPassword) {
        myAccountPage.enterConfirmNewPasswordOnUpdatingPasswordForm(newPassword);
    }

    @Step
    public void clickOnUpdatePasswordButton() {
        myAccountPage.clickOnUpdatePasswordButton();
    }

    @Step
    public void clickOnProductOnProductsPage(int productNumber, Map<String, Boolean> statuses) {
        Serenity.getCurrentSession().put("expectedSelectedProduct", productsPage.clickOnProductOnProductsPage(productNumber, statuses));
    }

    @Step
    public void isProductDetailsPageOpenedWith(Product product) throws IOException {
        product.setProductName(basePage.getTranslation(product.getProductName(), currentLanguage, basePage.DICTIONARY_PRODUCTS));
        Assert.assertEquals(product.toString().toUpperCase(),
                productDetailsPage.getProduct(currentLanguage).toString().toUpperCase());
    }

    @Step
    public void selectProductQtyOnProductsDetailsPage(String qty) {
        productDetailsPage.selectProductQtyOnProductsDetailsPage(qty);
    }

    @Step
    public void clickOnAddToCartButtonOnProductsDetailsPage() throws IOException {
        Serenity.getCurrentSession().put("addedProductfromPDP", productDetailsPage.clickOnAddToCartButtonOnProductsDetailsPage(currentLanguage));
    }

    @Step
    public void enterValueIntoSearchPharmaciesFieldOnProductDetailsPage(String text) {
        productDetailsPage.enterTextIntoSearchPharmaciesField(text);
    }

    @Step
    public void clickOnFindPharmaciesButtonOnProductDetailsPage() {
        productDetailsPage.clickOnFindPharmaciesButton();
    }

    @Step
    public void isFirstPharmacieAddressInSearchResultsContains(String value) {
        Assert.assertTrue("First product isn't comtained " + value + " " + productDetailsPage.getPharmacyAddressesSearchResults().get(0),
                productDetailsPage.getPharmacyAddressesSearchResults().get(0).contains(value));
    }

    @Step
    public void isPharmaciesInSearchResultsContainsStore(String value) {
        List<String> searchResults = new ArrayList<>();
        searchResults = productDetailsPage.getPharmacyStoreSearchResults();
        for (int i = 0; i < searchResults.size(); i++) {
            Assert.assertTrue("Search results isn't contained " + value + "in" + searchResults.get(i), searchResults.get(i).contains(value));
        }
    }

    @Step
    public void enterTextIntoGlobalSearchField(String productName) throws IOException {
        String translatedProductName = basePage.getTranslation(productName, currentLanguage, basePage.DICTIONARY_PRODUCTS);
        basePage.enterTextIntoGlobalSearchField(translatedProductName);
        try {
            Serenity.getCurrentSession().put("priceAutocomplete",
                    Double.valueOf(basePage.getPriceFromAutocompleteResults(translatedProductName).replace(",", ".").replace("-", "00").replace("€", "").trim()));
        } catch (Exception e) {
            Serenity.getCurrentSession().put("priceAutocomplete", "Error");
        }
    }

    @Step
    public void isProductsInAutocompleteResultsOfGlobalSearchContains(String text) throws IOException {
        for (String productName : basePage.getAllProductsNameFromAutocompleteResultsOfGlobalSearch()) {
            Assert.assertTrue(productName + " isn't contains " + text,
                    productName.contains(basePage.getTranslation(text, currentLanguage, basePage.DICTIONARY_PRODUCTS)));
        }
    }

    @Step
    public void selectProductFromAutocompleteResultsOfGlobalSearchField(String productName) throws IOException {
        Serenity.getCurrentSession().put("priceAutocomplete", basePage.selectProductFromAutocompleteResultsOfGlobalSearchField(productName, currentLanguage));
    }

    @Step
    public void isTitleOfProductDetailsPage(String expectedTitle) {
        Assert.assertEquals(expectedTitle.toUpperCase(), productDetailsPage.getProductName().toUpperCase());
    }

    @Step
    public void clickOnShowCheckoutCartButton() {
        checkoutPage.clickOnShowCheckoutCartButton();
    }

    @Step
    public void isCheckoutCartContains(String expectedProductName, Integer expectedQty) throws IOException {
        List<Product> productsCart = new ArrayList<>();
        productsCart = checkoutPage.getCheckoutCartProducts();
        Assert.assertEquals("Checkout Cart contain more then one product" + productsCart, 1, productsCart.size());
        Assert.assertEquals(basePage.getTranslation(expectedProductName, currentLanguage, basePage.DICTIONARY_PRODUCTS).toUpperCase(),
                productsCart.get(0).getProductName().toUpperCase());
        Assert.assertEquals(expectedQty, productsCart.get(0).getQty());
    }

    @Step
    public void isPriceOnProductsPageEqualToPriceFromAutocompleteResults(String productName) throws IOException {
        String translatedProductName = basePage.getTranslation(productName, currentLanguage, basePage.DICTIONARY_PRODUCTS);
        Assert.assertEquals((Double) Serenity.getCurrentSession().get("priceAutocomplete"), productsPage.getProduct(translatedProductName).getPrice());
    }

    @Step
    public void isDiscountPriceDisplayedOnProductsPage(String productName) throws IOException {
        String translatedProductName = basePage.getTranslation(productName, currentLanguage, basePage.DICTIONARY_PRODUCTS);
        Assert.assertTrue("Discount price isn't displayed ", productsPage.getProduct(translatedProductName).getDiscountPrice() != null);
    }

    @Step
    public void isPromotionalTextDisplayedOnProductsPage(String productName) throws IOException {
        String translatedProductName = basePage.getTranslation(productName, currentLanguage, basePage.DICTIONARY_PRODUCTS);
        Assert.assertTrue("Promotional text isn't displayed ", productsPage.getProduct(translatedProductName).getPromotionalText().isEmpty() == false);
    }

    @Step
    public void isBadgeDiscountDisplayedOnProductsPage(String productName) throws IOException {
        String translatedProductName = basePage.getTranslation(productName, currentLanguage, basePage.DICTIONARY_PRODUCTS);
        Assert.assertTrue("Badge Discount isn't displayed ", productsPage.isBadgeDiscountDisplayed(translatedProductName) == true);
    }

    @Step
    public void openProductDetailsPageFromProductsPage(String productName) throws IOException {
        String translatedProductName = basePage.getTranslation(productName, currentLanguage, basePage.DICTIONARY_PRODUCTS);
        productsPage.openProductsDetailsPageOf(translatedProductName);
    }

    @Step
    public void isDiscountPriceOnProductsDetailsPageEqualToDiscountPriceOnProductsPage() throws IOException {
        Assert.assertEquals(((Product) (Serenity.getCurrentSession().get("expectedPromotionalProduct"))).getDiscountPrice(),
                productDetailsPage.getProduct(currentLanguage).getPrice());//on PDP Discount price displayed as Price, and Price as Discount price
    }

    @Step
    public void isPriceOnProductsDetailsPageEqualToPriceOnProductsPage() throws IOException {
        Assert.assertEquals(((Product) (Serenity.getCurrentSession().get("expectedPromotionalProduct"))).getPrice(),
                productDetailsPage.getProduct(currentLanguage).getDiscountPrice());
    }

    @Step
    public void isPromotionalTextOnProductsDetailsPageEqualToPriceOnProductsPage() throws IOException {
        Assert.assertEquals(((Product) (Serenity.getCurrentSession().get("expectedPromotionalProduct"))).getPromotionalText(),
                productDetailsPage.getProduct(currentLanguage).getPromotionalText());
    }

    @Step
    public void isBadgeDiscountDisplayedOnProductsDetailsPageForPromotionalProduct() {
        Assert.assertTrue("Badge Discount isn't displayed", productDetailsPage.isBadgeDiscountDisplayed() == true);
    }

    @Step
    public void selectCategoryFromHeaderProductsDropDownn(String category) throws IOException {
        basePage.selectProductCategoryFromHeaderDropDown(currentLanguage, category);
    }

    @Step
    public void isShoppingCartContainsProducts(List<Product> expectedProducts) throws IOException {
        for (Product expectedProduct : expectedProducts) {
            isShoppingCartContainsProduct(expectedProduct);
        }
    }

    @Step
    public void isShoppingCartContainsProduct(Product expectedProduct) throws IOException {
        expectedProduct.setProductName(basePage.getTranslation(expectedProduct.getProductName(), currentLanguage, basePage.DICTIONARY_PRODUCTS));
        Assert.assertEquals(expectedProduct.toString(), shoppingCart.getProductFromCart(expectedProduct.getProductCode()).toString());
        if (expectedProduct.getPromotionalText() != null) {
            Assert.assertTrue("Top promotional text isn't contains promotional text for product" + expectedProduct.getProductName(),
                    shoppingCart.getTopPromotionTextForProduct(expectedProduct.getProductCode()).contains(expectedProduct.getPromotionalText()));
        }
    }


    @Step
    public void isShoppingCartOrderSummaryDisplayedWith(String fieldName, String expectedValue) throws IOException {
        switch (expectedValue) {
            case "sum(priceLineWithDiscount)":
                Assert.assertEquals(fieldName, expectedOrder.calculateTotalProducts(),
                        Double.valueOf(shoppingCart.getValueOfOrderSummary(fieldName, currentLanguage)
                                .replace(",", ".").replace("-", "00").replace("€", "").replace(" ", "")));
                break;
            case "sum(priceLine)":
                Assert.assertEquals(fieldName, expectedOrder.calculateTotalProductsWithoutDiscount(),
                        Double.valueOf(shoppingCart.getValueOfOrderSummary(fieldName, currentLanguage)
                                .replace(",", ".").replace("-", "00").replace("€", "").replace(" ", "")));
                break;
            case "sum((price-discountPrice)*qty)":
                Assert.assertEquals(fieldName, expectedOrder.calculateTotalDiscountProducts(),
                        Double.valueOf(shoppingCart.getValueOfOrderSummary(fieldName, currentLanguage)
                                .replace(",", ".").replace("-", "00").replace("€", "").replace(" ", "")));
                break;
            case "Total products-Discount":
                Assert.assertEquals(fieldName, Double.valueOf(expectedOrder.calculateTotalProductsWithoutDiscount() - expectedOrder.calculateTotalDiscountProducts()),
                        Double.valueOf(shoppingCart.getValueOfOrderSummary(fieldName, currentLanguage)
                                .replace(",", ".").replace("-", "00").replace("€", "").replace(" ", "")));
                break;
        }
    }

    @Step
    public void addProductToShoppingCart(List<Product> products) throws IOException, InterruptedException {
        for (Product product : products) {
            basePage.clickOn(Locators.BASE_PAGE_GLOBAL_SEARCH_BUTTON, 10, 0);
            basePage.searchForProduct(product.getProductCode());
            for (int i = 1; i <= product.getQty(); i++) {
                searchResultsPage.addToCartProduct(product.getProductCode());
            }
            product.setProductName(basePage.getTranslation(product.getProductName(), currentLanguage, basePage.DICTIONARY_PRODUCTS));
            expectedOrder.addProduct(new Product(product));
            Thread.sleep(500);
        }
    }

    @Step
    public void isTipsDisplayedInShoppingCart(String expectedMessage) throws IOException {
        String translatedExpectedMessage = basePage.getTranslation(expectedMessage, currentLanguage, basePage.DICTIONARY_CHECKOUT);
        Assert.assertEquals(translatedExpectedMessage, shoppingCart.getTipsMessage());
    }

    @Step
    public void valueOfCheckoutOrderSummaryShouldBe(String fieldName, Double expectedValue) throws IOException {
        String translatedFieldName = basePage.getTranslation(fieldName, currentLanguage, basePage.DICTIONARY_CHECKOUT);
        Assert.assertEquals(fieldName, expectedValue, checkoutPage.getValueFromOrderSummary(translatedFieldName));
    }

    @Step
    public void isOrderDeliveryCostInBPOSTPopUp(Double expectedValue) {
        Assert.assertEquals(expectedValue, checkoutPage.getOrderDeliveryValueFromBpost());
    }

    @Step
    public void removeProductFromTheCart(List<Product> products) throws IOException {
        for (Product product : products) {
            shoppingCart.removeProductFromCart(product.getProductCode());
            expectedOrder.removePrtoduct(product.getProductCode());
        }
    }

    @Step
    public void expandBottomCheckoutShoppingCart() {
        checkoutPage.expandBottomCheckoutShoppingCart();
    }

    @Step
    public void isFieldOnCheckoutOrderSummaryDisplayedWithValue(String fieldName, String expectedValue) throws IOException {
        String translatedFieldName = basePage.getTranslation(fieldName, currentLanguage, basePage.DICTIONARY_CHECKOUT);
        switch (expectedValue) {
            case "sum(LineTotal)":
                Assert.assertEquals(expectedOrder.calculateTotalProducts(), checkoutPage.getValueFromCheckoutOrderSummary(translatedFieldName));
                break;
        }
    }

    @Step
    public void clickOnPasswordRecoveryButton() {
        checkoutPage.clickOnPasswordRecoveryButton();
    }

    @Step
    public void enterIntoRestorePasswordField(String email) {
        checkoutPage.enterIntoRestorePasswordField(email);
    }

    @Step
    public void clickOnSendEmailButtonToRestorePassword() {
        checkoutPage.clickOnSendEmailButtonToRestorePassword();
    }

    @Step
    public void isMessageDisplayedInRestorePasswordPopUp(String expectedMessage) throws IOException {
        Assert.assertEquals(basePage.getTranslation(expectedMessage, currentLanguage, basePage.DICTIONARY_CHECKOUT),
                checkoutPage.getMessageDisplayedInRestorePasswordPopUp());
    }

    @Step
    public void closeRestorePasswordPopUp() {
        checkoutPage.closeRestorePasswordPopUp();
    }

    @Step
    public void isRecoveryLetterSent(String email, String password, String emailFrom, String subject) throws GeneralSecurityException, MessagingException, IOException {
        Message notifMsg;
        String translatedSubject = basePage.getTranslation(subject, currentLanguage, basePage.DICTIONARY_CHECKOUT);
        notifMsg = EmailUtilities.getEmailMessage(email, password, emailFrom, translatedSubject);
        Assert.assertTrue("Recovery letter isn't sent", !notifMsg.equals(null));
        Serenity.getCurrentSession().put("emailMessage", notifMsg);
    }

    @Step
    public void checkEmailUpdatePasswordLink(String expectedPageTitle) throws MessagingException, SAXException, DocumentException, JDOMException, IOException {
        Message notifMsg;
        String url;
        notifMsg = (Message) Serenity.getCurrentSession().get("emailMessage");
        url = EmailUtilities.getPasswordRecoveryLink(notifMsg);
        basePage.gotoUrl(url);
        Assert.assertTrue(checkoutPage.isDisplayedUpdatePasswordForm());
    }

    @Step
    public void enterIntoNewPasswordField(String newPassword) {
        checkoutPage.enterIntoNewPasswordField(newPassword);
    }

    @Step
    public void enterIntoConfirmationPasswordField(String newPassword) {
        checkoutPage.enterIntoConfirmationPasswordField(newPassword);
    }

    @Step
    public void clickOnSubmitUpdatedPasswordButton() {
        checkoutPage.clickonSubmitUpdatedPassword();
    }

    @Step
    public void isColumnOfSearchResultsInBackOfficeContainsCreatedOrders(String columnName) throws IOException {
        List<String> missedOrders = new ArrayList<>();
        Boolean containsOrder = true;

        BufferedReader br = new BufferedReader(new FileReader(".//src/test/resources/data/createdOrdersNumbers.txt"));
        try {
            String line = br.readLine();
            while (line != null) {
                createdOrdersNumber.add(line);
                line = br.readLine();
            }
        } finally {
            br.close();
            PrintWriter writer = new PrintWriter(".//src/test/resources/data/createdOrdersNumbers.txt");
            writer.print("");
            writer.close();
        }

        if (createdOrdersNumber.size() > 0) {
            for (String expectedOrderNumber : createdOrdersNumber) {
                backOfficePages.enterIntoSearchField(expectedOrderNumber);
                backOfficePages.clickOnSearchButton();
                if (backOfficePages.getSearchResults(columnName).get(0).equals(expectedOrderNumber) == false) {
                    missedOrders.add(expectedOrderNumber);
                    containsOrder = false;
                }
            }
            Assert.assertTrue("Created orders are't appeared in Back Office - " + String.join(" ", missedOrders), containsOrder == true);
        } else
            Assert.assertTrue("No one order had been created", false);
    }

    @Step
    public void deleteAllMessages(String email, String password) throws MessagingException {
        EmailUtilities.deleteAllInboxMessages(email, password);
    }

    @Step
    public void isMessageDisplayedInShoppingCart(String message) throws IOException {
        Assert.assertEquals(basePage.getTranslatedMessage(message, currentLanguage, basePage.DICTIONARY_CHECKOUT), shoppingCart.getCartEmptyMessage());
    }

    @Step
    public void loginRestApi(String login, String password) throws IOException {
//        keytool -import -trustcacerts -keystore /usr/lib/jvm/java-8-openjdk-amd64/jre/lib/security/caserts \-storepass changeit -noprompt -alias lloydPharmSert -file /home/user/Downloads/qalloydspharmabe.crt
        String CSRFToken;
        Cookies cookies;
        Response response = given().relaxedHTTPSValidation()
                .header("Content-Type", "application/x-www-form-urlencoded")
                .get(BASE_URL);
        Assert.assertTrue(response.then().extract().statusCode() == 200);

        Document document = Jsoup.parse(response.asString());
        CSRFToken = Xsoup.compile("//*[@name='CSRFToken']").evaluate(document).getElements().get(0).attr("value");
        Serenity.getCurrentSession().put("CSRFToken", CSRFToken);

        response = given().relaxedHTTPSValidation()
                .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8")
                .header("Content-Type", "application/x-www-form-urlencoded")
                .header("Accept-Encoding", "gzip, deflate, br")
                .header("Cache-Control", "max-age=0")
                .header("Connection", "keep-alive")
                .header("Host", "qa.lloydspharma.be")
                .header("Origin", "https://qa.lloydspharma.be")
                .header("Referer", "https://qa.lloydspharma.be/login")
                .header("Upgrade-Insecure-Requests", "1")
                .formParam("CSRFToken", CSRFToken)
                .formParam("j_password", password)
                .formParam("j_username", login)
                .post(BASE_URL + "/j_spring_security_check");

        cookies = response.getDetailedCookies();

        String url3 = response.then().extract().header("Location");
        response = given().relaxedHTTPSValidation()
                .header("Connection", "keep-alive")
                .cookies(cookies)
                .get(url3);
        //   Assert.assertTrue(response.then().extract().body().asString().contains("Welkom Dmitriy"));
        cookies = response.getDetailedCookies();

        response = given().relaxedHTTPSValidation()
                .header("Connection", "keep-alive")
                .header("Upgrade-Insecure-Requests", "1")
                .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8")
                //.header("Referer", BASE_URL + "/cart")
                .cookies(cookies)
                .get(BASE_URL + "/cart");

        document = Jsoup.parse(response.asString());
        CSRFToken = Xsoup.compile("//*[@name='CSRFToken']").evaluate(document).getElements().get(0).attr("value");

        String productCode;
        Document document1 = Jsoup.parse(response.asString());
        List<Element> elements = new ArrayList<>();
        elements = Xsoup.compile("//tr[@class='product-item']//div[@class='info']/span[1]").evaluate(document1).getElements();

        for (int i = elements.size(); i >= 1; i--) {
            productCode = elements.get(i - 1).text().substring(
                    (elements.get(i - 1).text().indexOf(":") + 1),
                    (elements.get(i - 1).text().indexOf(",")));
            productCode = productCode.trim().replace(" ", "");

            response = given().relaxedHTTPSValidation()
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .header("Referer", BASE_URL + "/cart")
                    .formParam("CSRFToken", CSRFToken)
                    .formParam("entryNumber", "0")
                    .formParam("initialQuantity", "0")
                    .formParam("productCode", productCode)
                    .formParam("quantity", "0")
                    .cookies(cookies)
                    .post(BASE_URL + "/cart/update");
            // Assert.assertTrue(response.then().extract().statusCode() == 302);
            cookies = response.getDetailedCookies();
        }
        response = given().relaxedHTTPSValidation()
                .header("Upgrade-Insecure-Requests", "1")
                .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8")
                .cookies(cookies)
                .get(BASE_URL + "/logout");
        //    Assert.assertTrue(response.then().extract().statusCode() == 200);
    }


    @Step
    public void selectOptionOfFilterByPrice(String option) {
        productsPage.selectOptionOfFilterByPrice(option);
    }

    @Step
    public void isOptionDisplayedInAppliedFacetsSection(String option) {
        productsPage.isOptionDisplayedInAppliedFacetsSection(option);
    }

    @Step
    public void isProductsPageContainsProductsPrice(int minPrice, int maxPrice) {
        Double price;
        for (int i = 1; i <= productsPage.getQtyOfProductsOnPage(); i++) {
            price = productsPage.getProductPrice(i);
            Assert.assertTrue(productsPage.getProductName(i), (price >= minPrice) && (price <= maxPrice));
        }
        if (productsPage.getPagesOfPagination() > 1) {
            basePage.gotoUrl(BASE_URL + "/producten/c/l0?q=%3Arelevance%3Aprice%3A" + minPrice + "+-+" + maxPrice + "&page=" + (productsPage.getPagesOfPagination() - 1));
            for (int i = 1; i <= productsPage.getQtyOfProductsOnPage(); i++) {
                price = productsPage.getProductPrice(i);
                Assert.assertTrue(productsPage.getProductName(i), (price >= minPrice) && (price <= maxPrice));
            }
        }

    }

    @Step
    public void selectPaymentMethodVisaCard() {
        injenicoPage.selectPaymentMethodVisaCard();
    }

    @Step
    public void enterCardVerificationCode(String code) {
        injenicoPage.enterCardVerificationCode(code);
    }

    @Step
    public void checkPrintPicklistForEachCreatedOrderWith(String shipmentMethod) {
        Gson gson = new Gson();
        Order order = new Order();

        List<String> orderListJson = new ArrayList<>();
        orderListJson = (List<String>) Serenity.getCurrentSession().get("CreatedOrderListJson");

        for (String orderJson : orderListJson) {
            order = gson.fromJson(orderJson, new TypeToken<Order>() {
            }.getType());
            if (order.getShipmentMethod().equals(shipmentMethod)) {
                backOfficePages.enterIntoSearchField(order.getOrderNumber());
                backOfficePages.clickOnSearchButton();
                backOfficePages.clickOnOrder(order.getOrderNumber());
                backOfficePages.clickOnButton("Print picklist");
                Document document = Jsoup.parse(backOfficePages.getOrderFromPickingList());
                Assert.assertEquals(order.getOrderNumber(),
                        Xsoup.compile("//table[@class='packInfo']//tbody/tr[2]/td[2]").evaluate(document).getElements().get(0).ownText());
            }
        }
    }

    @Step
    public void checkShippingLabelForEachCreatedOrderWith(String shipmentMethod) {
        Gson gson = new Gson();
        Order order = new Order();

        List<String> orderListJson = new ArrayList<>();
        orderListJson = (List<String>) Serenity.getCurrentSession().get("CreatedOrderListJson");

        for (String orderJson : orderListJson) {
            order = gson.fromJson(orderJson, new TypeToken<Order>() {
            }.getType());
            if (order.getShipmentMethod().equals(shipmentMethod)) {
                backOfficePages.enterIntoSearchField(order.getOrderNumber());
                backOfficePages.clickOnSearchButton();
                backOfficePages.clickOnOrder(order.getOrderNumber());
                backOfficePages.clickOnButton("Shipping label");
                Document document = Jsoup.parse(backOfficePages.getShippingLabel());
                Assert.assertTrue("No media for shipping label" + Xsoup.compile("//img").evaluate(document).getElements().get(0).attr("src"),
                        Xsoup.compile("//img").evaluate(document).getElements().get(0).attr("src").contains("/medias/?context="));
            }
        }
    }

    @Step
    public void checkEmailNotificationOrderCreationByReceiver(String email, String password, String sender, String receiver) throws GeneralSecurityException, MessagingException, IOException {
        List<String> createdOrderListJson = new ArrayList<>();
        Gson gson = new Gson();
        Address address = new Address();

        Message notifMsg;
        notifMsg = EmailUtilities.getEmailMessageByReceiver(email, password, sender, receiver);
        Assert.assertTrue("Email notification of the Order creationisn't found", !notifMsg.equals(null));

        createdOrder.setTestNumber(expectedOrder.getTestNumber());
        createdOrder.setOrderNumber(notifMsg.getSubject()
                .replace("Bevestiging bestelling", "").replace("Confirmation de commande", "").replace(" ", ""));
        expectedOrder.setOrderNumber(createdOrder.getOrderNumber());
        expectedOrder.setTotalOrder("0.0");
        expectedOrder.setShipmentMethod("Prescription reserve");
        address.setFirstName(basePage.getTranslation("Payment at pickup", currentLanguage, basePage.DICTIONARY_ORDER));
        expectedOrder.setPaymentAddress(address);
        createdOrder.setShipmentMethod("Prescription reserve");

        if ((List<String>) Serenity.getCurrentSession().get("CreatedOrderListJson") == null) {
            createdOrderListJson.add(gson.toJson(createdOrder));
        } else {
            createdOrderListJson = (List<String>) Serenity.getCurrentSession().get("CreatedOrderListJson");
            createdOrderListJson.add(gson.toJson(createdOrder));
        }
        Serenity.getCurrentSession().put("CreatedOrderListJson", createdOrderListJson);

        try {
            String filename = "./src/test/resources/data/createdOrdersNumbers.txt";
            FileWriter fw = new FileWriter(filename, true); //true-append
            fw.write(createdOrder.getOrderNumber() + "\n");
            fw.close();
        } catch (IOException ioe) {
        }


    }

    @Step
    public void checkSplitOrderForEachCreatedOrderWith(String shipmentMethod) {
        Gson gson = new Gson();
        Order order = new Order();

        List<String> orderListJson = new ArrayList<>();
        orderListJson = (List<String>) Serenity.getCurrentSession().get("CreatedOrderListJson");

        for (String orderJson : orderListJson) {
            order = gson.fromJson(orderJson, new TypeToken<Order>() {
            }.getType());
            if (order.getShipmentMethod().equals(shipmentMethod)) {
                backOfficePages.enterIntoSearchField(order.getOrderNumber());
                backOfficePages.clickOnSearchButton();
                backOfficePages.clickOnOrder(order.getOrderNumber());
                backOfficePages.clickOnButton("Split Order");
                Document document = Jsoup.parse(backOfficePages.getSplitOrder());
                backOfficePages.closePopUp();
                Assert.assertEquals("Mismatch order number ", order.getOrderNumber(),
                        Xsoup.compile("//input[contains(@value,'CLPBE-')]").evaluate(document).getElements().get(0).attr("value").replace("_0", "").replace("C", ""));
            }
        }
    }

    @Step
    public void checkPackSlipForEachCreatedOrderWith(String shipmentMethod) {
        Gson gson = new Gson();
        Order order = new Order();

        List<String> orderListJson = new ArrayList<>();
        orderListJson = (List<String>) Serenity.getCurrentSession().get("CreatedOrderListJson");

        for (String orderJson : orderListJson) {
            order = gson.fromJson(orderJson, new TypeToken<Order>() {
            }.getType());
            if (order.getShipmentMethod().equals(shipmentMethod)) {
                backOfficePages.enterIntoSearchField(order.getOrderNumber());
                backOfficePages.clickOnSearchButton();
                backOfficePages.clickOnOrder(order.getOrderNumber());
                backOfficePages.clickOnButton("Pack Slip");
                Document document = Jsoup.parse(backOfficePages.getPackSlip());
                // backOfficePages.closePopUp();
                Assert.assertEquals("Mismatch order number ", order.getOrderNumber(),
                        Xsoup.compile("//table[@class='packInfo']//table/tbody/tr[4]/td[2]").evaluate(document).getElements().get(0).ownText());
            }
        }
    }

    @Step
    public void saveScenarioNumberToOrder(String testID) {
        expectedOrder.setTestNumber(testID);
    }

    @Step
    public void isShoppingCartNotContainsProducts(List<Product> products) {
        Boolean flagNotContains;
        for (Product product : products) {
            try {
                shoppingCart.getProductFromCart(product.getProductCode());
                flagNotContains = false;
            } catch (Exception e) {
                flagNotContains = true;
            }
            Assert.assertTrue("Shopping cart contained product" + product.toString(), flagNotContains);
        }
    }

    @Step
    public void openProductDetailsPageOfProduct(String productCode) throws IOException {
        basePage.clickOn(Locators.BASE_PAGE_GLOBAL_SEARCH_BUTTON, 10, 0);
        basePage.enterTextIntoGlobalSearchField(productCode);
        basePage.selectProductFromAutocompleteResultsOfGlobalSearchField(productCode);
    }

    @Step
    public void clickOnLargerImageButton() {
        productDetailsPage.clickOnLargerImageButton();
    }

    @Step
    public void isLargeImageDisplayedForProduct(String productCode) {
        Assert.assertTrue(productDetailsPage.getLargerImageLink().contains(productCode));
    }

    @Step
    public void isSendToAddressOptionOnDeliveryMethodStepDisplayedWith(String message) throws IOException {
        String translatedMessage = basePage.getTranslatedMessage(message, currentLanguage, basePage.DICTIONARY_CHECKOUT);
        Assert.assertEquals(translatedMessage, checkoutPage.getAdditionalMessageForSendToAddressOption());
    }

    @Step
    public void isWarningPopUpContainsSelectedUnavailableProducts(List<Product> expectedProducts) throws IOException {
        for (Product expectedProduct : expectedProducts) {
            Assert.assertEquals(expectedProduct.toString(), checkoutPage.getUnavailableProductsFromWarningPopUp(expectedProduct.getProductCode(), currentLanguage).toString());
        }
    }

    @Step
    public void clickOnCancelButtonOnWarningPopup() throws IOException {
        checkoutPage.clickOnCancelButtonOnWarningPopup(currentLanguage);
    }

    @Step
    public void clickOnRemoveAllButtonOntheWarningPopup() {
        checkoutPage.clickOnRemoveAllButtonOnWarningPopup();
    }

    @Step
    public void isNotDisplayedCartCountOnHeaderCart() {
        Assert.assertEquals("", shoppingCart.getCartCount());
    }

    @Step
    public void createCustomerProfileIfNotExists(CustomerProfile profile) {
        String CSRFToken;
        Cookies cookies;
        Document document;

        Response response = given().relaxedHTTPSValidation()
                .header("User-Agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/62.0.3202.94 Safari/537.36")
                .header("Upgrade-Insecure-Requests", "1")
                .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8")
                .header("Accept-Encoding", "gzip, deflate, br")
                .header("Accept-Language", "en-GB,en;q=0.9,en-US;q=0.8,ru;q=0.7,uk;q=0.6,no;q=0.5,fr-FR;q=0.4,fr;q=0.3,nl;q=0.2")
                .header("Content-Type", "application/x-www-form-urlencoded")
                .get(BASE_URL);
//        Assert.assertTrue(response.then().extract().statusCode() == 200);

        document = Jsoup.parse(response.asString());
//        System.out.println(document);
        CSRFToken = Xsoup.compile("//*[@name='CSRFToken']").evaluate(document).getElements().get(0).attr("value");

        response = given().relaxedHTTPSValidation()
                .header("Content-Type", "application/x-www-form-urlencoded")
                .formParam("CSRFToken", CSRFToken)
                .formParam("j_password", profile.getPassword())
                .formParam("j_username", profile.getEmail())
                .post(BASE_URL + "/j_spring_security_check");
        cookies = response.getDetailedCookies();
        String url3 = response.then().extract().header("Location");
        response = given().relaxedHTTPSValidation()
                .header("Connection", "keep-alive")
                .cookies(cookies)
                .get(url3);

        if ((response.then().extract().body().asString().contains("Welkom " + profile.getFirstName()) == false)
                && (response.then().extract().body().asString().contains("Bienvenue " + profile.getFirstName()) == false)) {
            response = given().relaxedHTTPSValidation()
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .get(BASE_URL);
            document = Jsoup.parse(response.asString());
            CSRFToken = Xsoup.compile("//*[@name='CSRFToken']").evaluate(document).getElements().get(0).attr("value");

            response = given().relaxedHTTPSValidation()
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .formParam("CSRFToken", CSRFToken)
                    .formParam("j_password", "newPassword")
                    .formParam("j_username", profile.getEmail())
                    .post(BASE_URL + "/j_spring_security_check");
            cookies = response.getDetailedCookies();

            response = given().relaxedHTTPSValidation()
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .cookies(cookies)
                    .get(BASE_URL + "/my-account/update-password");
            document = Jsoup.parse(response.asString());
            CSRFToken = Xsoup.compile("//*[@name='CSRFToken']").evaluate(document).getElements().get(0).attr("value");

            if ((response.then().extract().body().asString().contains("Welkom " + profile.getFirstName()) == true)
                    || (response.then().extract().body().asString().contains("Bienvenue " + profile.getFirstName()) == true)) {
                response = given().relaxedHTTPSValidation()
                        .header("Content-Type", "application/x-www-form-urlencoded")
                        .formParam("CSRFToken", CSRFToken)
                        .formParam("checkNewPassword", profile.getPassword())
                        .formParam("currentPassword", "newPassword")
                        .formParam("newPassword", profile.getPassword())
                        .cookies(cookies)
                        .post(BASE_URL + "/my-account/update-password");
            } else {
                response = given().relaxedHTTPSValidation()
                        .header("Content-Type", "application/x-www-form-urlencoded")
                        .get(BASE_URL + "/login");
                cookies = response.getDetailedCookies();
                document = Jsoup.parse(response.asString());
                CSRFToken = Xsoup.compile("//*[@name='CSRFToken']").evaluate(document).getElements().get(0).attr("value");

                response = given().relaxedHTTPSValidation()
                        .header("Content-Type", "application/x-www-form-urlencoded")
                        .formParam("CSRFToken", CSRFToken)
                        .formParam("building", profile.getBuilding())
                        .formParam("checkPwd", profile.getPassword())
                        .formParam("email", profile.getEmail())
                        .formParam("firstName", profile.getFirstName())
                        .formParam("lastName", profile.getLastName())
                        .formParam("line1", profile.getAddress())
                        .formParam("mobileNumber", profile.getPhoneNumber())
                        .formParam("poBox", profile.getBox())
                        .formParam("postcode", profile.getPostCode())
                        .formParam("pwd", profile.getPassword())
                        .formParam("titleCode", "F1")
                        .formParam("townCity", profile.getCity())
                        .formParam("additionalInfo", profile.getAdditionalInfo())
                        .formParam("birthDate", profile.getBirthDate())
                        .cookies(cookies)
                        .post(BASE_URL + "/login/process-register");
            }
        }
        response = given().relaxedHTTPSValidation()
                .header("Upgrade-Insecure-Requests", "1")
                .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8")
                .cookies(cookies)
                .get(BASE_URL + "/logout");
    }

    @Step
    public void selectOptionSortBy(String optionName) throws IOException {
        String translatedOptionName = basePage.getTranslation(optionName, currentLanguage, basePage.DICTIONARY_CHECKOUT);
        productsPage.selectOptionSortBy(translatedOptionName);
    }

    private List<Double> listStringToListDouble(List<String> listString) {
        List<Double> listDouble = new ArrayList<>();
        for (int i = 0; i < listString.size(); i++) {
            if (listString.get(i).equals("")) {
                listDouble.add(0.00);
            } else {
                listDouble.add(Double.valueOf(listString.get(i)
                        .replace("€", "").replace(" ", "").replace(",", ".").replace("-", "")));
            }
        }
        return listDouble;
    }

    @Step
    public void isProductsSortedBy(String optionName) {
        List<String> expectedListString = new ArrayList<>();
        List<Double> expectedListDouble = new ArrayList<>();
        switch (optionName) {
            case "Price high - low":
                expectedListString = productsPage.getParameterOfAllVisibleProduct(Locators.PRODUCTS_PAGE_PRODUCTS_PRICE);
                expectedListDouble = listStringToListDouble(expectedListString);
                Collections.sort(expectedListDouble);
                Collections.reverse(expectedListDouble);
                Assert.assertEquals(expectedListDouble,
                        listStringToListDouble(productsPage.getParameterOfAllVisibleProduct(Locators.PRODUCTS_PAGE_PRODUCTS_PRICE)));
                break;
            case "Price low - high":
                expectedListString = productsPage.getParameterOfAllVisibleProduct(Locators.PRODUCTS_PAGE_PRODUCTS_PRICE);
                expectedListDouble = listStringToListDouble(expectedListString);
                Collections.sort(expectedListDouble);
                Assert.assertEquals(expectedListDouble,
                        listStringToListDouble(productsPage.getParameterOfAllVisibleProduct(Locators.PRODUCTS_PAGE_PRODUCTS_PRICE)));
                break;
            case "Alphabetical":
                expectedListString = productsPage.getParameterOfAllVisibleProduct(Locators.PRODUCTS_PAGE_PRODUCTS_NAME);
                Collections.sort(expectedListString);
                Assert.assertEquals(expectedListString, productsPage.getParameterOfAllVisibleProduct(Locators.PRODUCTS_PAGE_PRODUCTS_NAME));
                break;
            case "Descending":
                expectedListString = productsPage.getParameterOfAllVisibleProduct(Locators.PRODUCTS_PAGE_PRODUCTS_NAME);
                Collections.sort(expectedListString);
                Collections.reverse(expectedListString);
                Assert.assertEquals(expectedListString, productsPage.getParameterOfAllVisibleProduct(Locators.PRODUCTS_PAGE_PRODUCTS_NAME));
                break;
        }
    }

    @Step
    public void navigateToProductsPage(Integer pageNumber) {
        productsPage.navigateToProductsPage(pageNumber);
    }

    @Step
    public void isOrderNotesDisplayed() {
        Assert.assertEquals(expectedOrder.getOrderNote(), orderConfirmationPage.getOrderNote());
    }

    @Step
    public void isOrderStatusInOrderHistoryEqualTo(String testID, String expectedOrderStatus) throws IOException {
        String translatedExpectedOrderStatus = basePage.getTranslation(expectedOrderStatus, currentLanguage, basePage.DICTIONARY_ORDER);
        String orderNumber = Helpers.getOrderFromCreatedOrderListJsonBy(testID).getOrderNumber();
        Assert.assertEquals(translatedExpectedOrderStatus, myAccountPage.getOrderStatus(orderNumber));
    }

    @Step
    public void isMyPrescriptionFormPrepopulateWith(Map<String, String> fieldsValues) {
        fieldsValues.forEach((fieldName, value) -> {
            try {
                Assert.assertEquals(value, myPrescriptionPage.getFieldValue(basePage.getTranslation(fieldName, currentLanguage, basePage.DICTIONARY_MY_PRESCRIPTION)));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    @Step
    public void openOrderDetailsPageOfCreatedOrder() {
        myAccountPage.searchForOrderInOrderHistory(expectedOrder.getOrderNumber());
        myAccountPage.clickOn(myAccountPage.ORDER_HISTORY_ORDER_LINK.replace("$OrderNumber", expectedOrder.getOrderNumber()), 10);
    }

    @Step
    public void isOrderDetailsPageDisplayedWithAllTheDetailsOfCreatedOrder() {
        Address actualAddress = new Address();
        Assert.assertTrue("Title " + orderDetailsPage.getOrderTitle() + "isn't contained " + expectedOrder.getOrderNumber(),
                orderDetailsPage.getOrderTitle().contains(expectedOrder.getOrderNumber()));
        actualAddress = orderDetailsPage.getDeliveryAddress(expectedOrder.getShipmentMethod());
        Assert.assertEquals("Mismatch first name in shipment address", expectedOrder.getDeliveryAddress().getFirstName(), actualAddress.getFirstName());
        Assert.assertEquals("Mismatch last name in shipment address", expectedOrder.getDeliveryAddress().getLastName(), actualAddress.getLastName());
        if (expectedOrder.getDeliveryAddress().getAddress2() != null) {
            Assert.assertEquals("Mismatch address ", expectedOrder.getDeliveryAddress().getAddress1() + expectedOrder.getDeliveryAddress().getAddress2(),
                    actualAddress.getAddress1());
        } else {
            Assert.assertEquals("Mismatch address in shipment address", expectedOrder.getDeliveryAddress().getAddress1(), actualAddress.getAddress1());
        }
        Assert.assertEquals("Mismatch post code in shipment address", expectedOrder.getDeliveryAddress().getPostCode(), actualAddress.getPostCode());
        Assert.assertEquals("Mismatch city in shipment address", expectedOrder.getDeliveryAddress().getCity(), actualAddress.getCity());

        actualAddress = orderDetailsPage.getPaymentAddress(expectedOrder.getShipmentMethod());
        if (expectedOrder.getShipmentMethod().equals("To Be Shipped") == false) {
            Assert.assertTrue("Expected " + expectedOrder.getPaymentAddress().toString() + "\n but was " + actualAddress.toString(),
                    expectedOrder.getPaymentAddress().toString().equals(actualAddress.toString()));
        } else {
            if (expectedOrder.getPaymentAddress().getAddress2() != null) {
                Assert.assertEquals("Mismatch Address in payment address ", expectedOrder.getPaymentAddress().getAddress1() + expectedOrder.getPaymentAddress().getAddress2(),
                        actualAddress.getAddress1());
            } else {
                Assert.assertEquals("Mismatch Address in payment address ", expectedOrder.getPaymentAddress().getAddress1(),
                        actualAddress.getAddress1());
            }
            Assert.assertEquals("Mismatch PostCode in payment address ", expectedOrder.getPaymentAddress().getPostCode(), actualAddress.getPostCode());
            Assert.assertEquals("Mismatch City in payment address ", expectedOrder.getPaymentAddress().getCity(), actualAddress.getCity());
        }
        Assert.assertEquals("Mismatch order total", expectedOrder.getTotalOrder(), orderDetailsPage.getOrderTotal());
        if (expectedOrder.getTotalOrderDiscount() != null) {
            Assert.assertEquals("Mismatch total order discount", expectedOrder.getTotalOrderDiscount(), orderDetailsPage.getTotalOrderDiscount());
        }
        if (expectedOrder.getShipmentMethod().equals("Prescription reserve") == false) {
            Assert.assertEquals("Mismatch order note ", expectedOrder.getOrderNote(), orderDetailsPage.getOrderNote(expectedOrder.getShipmentMethod()));
        }
    }

    @Step
    public void isValueOfFieldForReminder(String fieldName, String expectedValue) throws IOException {
        String translatedFieldName = basePage.getTranslation(fieldName, currentLanguage, basePage.DICTIONARY_CHECKOUT);
        Assert.assertEquals(expectedValue, checkoutPage.getValueOfFieldForReminder(translatedFieldName));
    }

    @Step
    public void isOrderDetailsPageDisplayedWithAllTheProductsOfCreatedOrder() {
        expectedOrder.getProducts().forEach((Product product) -> {
            Product productOrderDetailsPage = orderDetailsPage.getProduct(product.getProductCode());
            Assert.assertTrue("Expected " + product.toString() + "but was " + productOrderDetailsPage.toString(),
                    product.compareTo(productOrderDetailsPage) == 0);
        });
    }
}