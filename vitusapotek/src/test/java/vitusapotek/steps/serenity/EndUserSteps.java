package vitusapotek.steps.serenity;

import io.restassured.http.Cookies;
import io.restassured.response.Response;
import net.serenitybdd.core.Serenity;
import net.thucydides.core.annotations.Step;
import org.dom4j.DocumentException;
import org.jdom.JDOMException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Assert;
import org.xml.sax.SAXException;
import us.codecraft.xsoup.Xsoup;
import vitusapotek.models.*;
import vitusapotek.pages.*;
import vitusapotek.utilities.EmailUtilities;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static io.restassured.RestAssured.given;

public class EndUserSteps {
    private BasePage basePage;
    private HomePage homePage;
    private LoginPage loginPage;
    private MyAccountPage myAccountPage;
    private ProductsPage productsPage;
    private ProductDetailsPage productDetailsPage;
    private ShoppingCartPage shoppingCartPage;
    private CheckoutPage checkoutPage;
    private OrderConfirmationPage orderConfirmationPage;
    private MyPrescriptionPage myPrescriptionPage;
    private NetAxeptPage netAxeptPage;

    public String BASE_URL = System.getProperty("base.url");
//   public String BASE_URL = "https://qa.vitusapotek.no";
//    public String BASE_URL = "https://qa2.vitusapotek.no";

    public Order expectedOrder = new Order();
    public Order createdOrder = new Order();
    public CustomerProfile profile = new CustomerProfile();

    @Step
    public void selectItemInMainMenu(String itemName) {
        homePage.selectMainMenuItem(itemName);
    }

    @Step
    public void openHomePage() {
        homePage.openHomePage(BASE_URL);
    }

    @Step
    public void clickOnCartButtonInHeader() {
        homePage.clickOnCartButtonInHeader();
    }

    @Step
    public void clickOnCheckoutButtonInTheCart() {
        expectedOrder.setOrderNumber(shoppingCartPage.getOrderNumber());
        expectedOrder.setTotalOrder(shoppingCartPage.getOrderTotal().replace(" ", ""));
        shoppingCartPage.clickOnCheckoutButton();
    }

    @Step
    public void selectShippingMethodSendByEmail() {
        checkoutPage.selectShippingMethodSendByEmail();
    }

    @Step
    public void clickOnSkipToShippingAddressButton() {
        checkoutPage.clickOnSkipToShippingAddressButton();
    }

    @Step
    public void enterCredentialsForGettingAddressFromProfile(String email, String password) {
        checkoutPage.enterCredentialsForGettingAddressFromProfile(email, password);
    }

    @Step
    public void clickOnFurtherToTheShippingAndPaymentButton() {
        checkoutPage.clickOnFurtherToTheShippingAndPaymentButton();
    }

    @Step
    public void selectDeliveryAddress(String address) {
        checkoutPage.selectDeliveryAddress(address);
    }

    @Step
    public void selectValueFromLandFieldForNewDeliveryAddress(String land) {
        checkoutPage.selectValueFromLandFieldForNewDeliveryAddress(land);
    }

    @Step
    public void enterIntoFieldForNewDeliveryAddress(String value, String fieldName) {
        Address deliveryAddress = new Address();
        checkoutPage.enterIntoFieldForNewDeliveryAddress(value, fieldName);
        switch (fieldName) {
            case "Fornavn":
                deliveryAddress.setFirstName(value);
                profile.setFirstname(value);
                break;
            case "Etternavn":
                deliveryAddress.setLastName(value);
                profile.setLastName(value);
                break;
            case "Adresselinje 1":
                deliveryAddress.setAddress1(value);
                profile.setAddress(value);
                break;
            case "Adresselinje 2":
                deliveryAddress.setAddress2(value);
                break;
            case "Telefonnummer":
                deliveryAddress.setPhoneNumber(value);
                profile.setPhoneNumber(value);
                break;
            case "Postnummer":
                deliveryAddress.setPostCode(value);
                profile.setPostCode(value);
                break;
            case "Poststed":
                deliveryAddress.setCity(checkoutPage.getCityFromNewDeliveryAddress());
                profile.setCity(value);
                break;
        }
        expectedOrder.setDeliveryAddress(deliveryAddress);
    }

    @Step
    public void clickOnAddNewAddressButton() {
        checkoutPage.clickOnAddNewAddressButton();
    }

    @Step
    public void selectShippingMethodGetInPharmacy() {
        checkoutPage.selectShippingMethodGetInPharmacy();
    }

    @Step
    public void selectFirstPharmacyWithStatusForProductsInTheCart(String status) {
        Address address = new Address();
        address.setFirstName(checkoutPage.selectFirstPharmacyWithStatus(status));
        expectedOrder.setDeliveryAddress(address);
    }

    @Step
    public void isSelectedPharmacyDisplayedInSelectPharmacySection() {
        Assert.assertTrue(expectedOrder.getDeliveryAddress().getFirstName() + " isn't contained " + checkoutPage.getPharmacyNameFromSelectPharmacySection(),
                expectedOrder.getDeliveryAddress().getFirstName().contains(checkoutPage.getPharmacyNameFromSelectPharmacySection()));
    }

    @Step
    public void enterIntoTheFieldForReminder(String value, String fieldName) {
        checkoutPage.enterIntoTheFieldForReminder(value, fieldName);
    }

    @Step
    public void enterAnOrderNotes(String orderNote) {
        checkoutPage.enterAnOrderNotes(orderNote);
    }

    @Step
    public void clickOnNextButtonOnStepInCaseGetInPharmacy() {
        checkoutPage.clickOnNextButtonOnStepInCaseGetInPharmacy();
    }

    @Step
    public void isOrderNumberOnConfirmationPageEqualToOrderNumberInShoppingCart() {
        createdOrder = orderConfirmationPage.getOrderDetails();
        Assert.assertEquals(expectedOrder.getOrderNumber(), createdOrder.getOrderNumber());
    }

    @Step
    public void isDeliveryInformationOnTheOrderConfirmationPageContainSelectedPharmacy() {
        Assert.assertTrue(expectedOrder.getDeliveryAddress().getFirstName() + " isn't contained " + createdOrder.getDeliveryAddress().getFirstName(),
                expectedOrder.getDeliveryAddress().getFirstName().contains(createdOrder.getDeliveryAddress().getFirstName()));
    }

    @Step
    public void isPaymentInformationOnTheOrderConfirmationPageContains(String payymentInfo) {
        Assert.assertEquals(payymentInfo, createdOrder.getPaymentAddress().getFirstName());
    }

    @Step
    public void isAllAddedToCartProductsWithAppropriateQtyAndPriceInTheOrder() {
        for (Product expProduct : expectedOrder.getProducts()) {
            Assert.assertEquals(expProduct.toString(), orderConfirmationPage.getProduct(expProduct.getItemNumber()).toString());
            Assert.assertEquals(expProduct.getTotal(), orderConfirmationPage.getProductTotalPrice(expProduct.getItemNumber()));
        }
    }

    @Step
    public void isOrderTotalTheSameAsInShoppingCart() {
        Assert.assertEquals("Total in Order isn't equal to Total in the Cart", expectedOrder.getTotalOrder().replace(",", ".").replace("-", "00"), createdOrder.getTotalOrder());
    }

    @Step
    public void confirmUsingCookies() {
        checkoutPage.confirmUsingCookies();
    }

    @Step
    public void selectDeliveryMethodForAnOrder(String deliveryMethod) {
        expectedOrder.setShippingPrice(checkoutPage.selectDeliveryMethodForAnOrder(deliveryMethod));
        expectedOrder.setDeliveryMethod(deliveryMethod);
    }

    @Step
    public void isAddressDisplayedInDeliverySection(String deliveryAddress) {
        Assert.assertEquals(deliveryAddress, checkoutPage.getAddressFromDeliverySection());
    }

    @Step
    public void selectPaymentMethod(String paymentMethod) {
        checkoutPage.selectPaymentMethod(paymentMethod);
    }

    @Step
    public void selectPaymentOption(String optionName) {
        checkoutPage.selectPaymentOption(optionName);
    }

    @Step
    public void clickOnButtonOnStep3(String buttonName) {
        checkoutPage.clickOnButtonOnStep3(buttonName);
    }

    @Step
    public void selectOptionOnStep2(String optionName) {
        checkoutPage.selectOptionOnStep2(optionName);
    }

    @Step
    public void enterPasswordAndRepeatPassword(String value) {
        checkoutPage.enterPasswordAndRepeatPassword(value);
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
    public void generatePhoneNumberkeyPhone(String keyPhone) {
        String generatedPhone;
        String textDate;
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        textDate = dateFormat.format(date).replace("/", "");
        textDate = textDate.replace(" ", "");
        textDate = textDate.replace(":", "");
        generatedPhone = "+" + textDate;
        Serenity.getCurrentSession().put(keyPhone, generatedPhone.substring(6, 14));
    }

    @Step
    public void enterGeneratedValueIntoFieldForNewCustomer(String keyValue, String fieldName) {
        Address deliveryAddress = new Address();
        checkoutPage.enterIntoFieldForNewDeliveryAddress((String) Serenity.getCurrentSession().get(keyValue), fieldName);
        deliveryAddress = expectedOrder.getDeliveryAddress();
        switch (keyValue) {
            case "uniquePhone":
                deliveryAddress.setPhoneNumber((String) Serenity.getCurrentSession().get(keyValue));
                expectedOrder.setDeliveryAddress(deliveryAddress);
                profile.setPhoneNumber((String) Serenity.getCurrentSession().get(keyValue));
                break;
            case "uniqueEmail":
                profile.setEmail((String) Serenity.getCurrentSession().get(keyValue));
                break;
        }

    }

    @Step
    public void clickOnAddNewAddressForDelivery() {
        checkoutPage.clickOnAddNewAddressForDelivery();
    }

    @Step
    public void selectOptionSaveAsNewAddress() {
        checkoutPage.selectOptionSaveAsNewAddress();
    }

    @Step
    public void clickOnAddAddressButton() {
        checkoutPage.clickOnAddAddressButton();
    }

    @Step
    public void clickOnLoginButtonOnOrderConfirmationPage() {
        orderConfirmationPage.clickOnLoginButton();
    }

    @Step
    public void isAlertMessageDisplayed(String message) {
        Assert.assertTrue("Alert isn't displayed (Expected) " + message, checkoutPage.isAlertMessageDisplayed(message));
    }

    @Step
    public void clickOnButtonForOrderDrugsOnline(String buttonName) {
        myPrescriptionPage.clickOnButtonForOrderDrugsOnline(buttonName);
    }

    @Step
    public void enterNewCustomerIntoField(String value, String fieldName) {
        loginPage.enterNewCustomerIntoField(value, fieldName);
    }

    @Step
    public void isErrorMessageDisplayedForCustomerProfile(String errorMessage) {
        loginPage.isErrorMessageDisplayedForCustomerProfile(errorMessage);
    }

    @Step
    public void isUpdateProfileButtonNotDisplayed() {
        loginPage.isUpdateProfileButtonNotDisplayed();
    }

    @Step
    public void clickOnNewUserLoginButton() {
        loginPage.clickOnNewUserLoginButton();
    }

    @Step
    public void clickOnCreateAccountButton() {
        loginPage.clickOnCreateAccountButton();
    }

    @Step
    public void isValueDisplayedInThePoststedField(String expectedValue) {
        Assert.assertEquals(expectedValue, loginPage.getCityValueOfNewCustomer());
    }

    @Step
    public void fillTheRegistrationFormWithData(CustomerProfile customerProfile) {
        loginPage.fillTheRegistrationFormWithData(customerProfile);
    }

    @Step
    public void selectMembershipGroups(List<String> groups) {
        loginPage.selectMembershipGroups(groups);
    }

    @Step
    public void enterFavoritePharmacies(List<String> pharmacies) {
        loginPage.enterFavoritePharmacies(pharmacies);
    }

    @Step
    public void isUpdateProfileButtonDisplayedWith(String caption) {
        Assert.assertEquals(caption, loginPage.getCaptionOfUpdateProfileButton());
    }

    @Step
    public void clickOnUpdateProfileButtonInHeaderMenu() {
        loginPage.clickOnUpdateProfileButtonInHeaderMenu();
    }

    @Step
    public void selectItemFromProfileDetails(String itemName) {
        myAccountPage.selectItemFromProfileDetails(itemName);
    }

    @Step
    public void isPersonalInformationDisplayedInProfile(CustomerProfile expectedPersonalInfo) {
        CustomerProfile actualPersonalInfo = new CustomerProfile();
        actualPersonalInfo = myAccountPage.getPersonalInfoFromProfile();
        Assert.assertEquals(expectedPersonalInfo.getFirstname(), actualPersonalInfo.getFirstname());
        Assert.assertEquals(expectedPersonalInfo.getFirstname(), actualPersonalInfo.getFirstname());
        Assert.assertEquals(expectedPersonalInfo.getPhoneNumber(), actualPersonalInfo.getPhoneNumber());
        Assert.assertEquals(expectedPersonalInfo.getIWantTobe4(), actualPersonalInfo.getIWantTobe4());
        Assert.assertEquals(expectedPersonalInfo.getNewsLetterFlag(), actualPersonalInfo.getNewsLetterFlag());
    }

    @Step
    public void isProfilePersonalDetailsDisplayedWithSelectedMembershipGroups(List<String> expectedGroups) {
        String[] expectedGroupsArr = expectedGroups.toArray(new String[0]);
        Arrays.sort(expectedGroupsArr);
        Assert.assertTrue("Mismatch Membership groups ", Arrays.equals(expectedGroupsArr, myAccountPage.getProfileMembershipGroups()));
    }

    @Step
    public void isPersonalDetailsDisplayedWithSelectedPharmacies(List<String> expectedPharmacies) {
        String[] expectedPharmaciesArr = expectedPharmacies.toArray(new String[0]);
        Arrays.sort(expectedPharmaciesArr);
        Assert.assertTrue("Mismatch Pharmacies ", Arrays.equals(expectedPharmaciesArr, myAccountPage.getProfilePharmacies()));
    }

    @Step
    public void isUpdateEmailAddressSectionContains(String fieldName, String expectedValue) {
        Assert.assertEquals(expectedValue, myAccountPage.getFieldValueFromEmailAddressSection(fieldName));
    }

    @Step
    public void isFirstAddressinAddressBook(Address expectedAddress) {
        Assert.assertEquals(expectedAddress.toString(), myAccountPage.getProfileAddresses().get(0).toString());
    }

    @Step
    public void isAddressBookContainAddress(Address expectedAddress) {
        Assert.assertTrue(isCustomerProfileContainAddress(expectedAddress));
    }

    @Step
    public void clickOnLogoutButtonInHeaderMenu() {
        myAccountPage.clickOnLogoutButtonInHeaderMenu();
    }

    @Step
    public void checkEmailNotificationOrderCreation(String email, String password, String sender, String subject) throws IOException, MessagingException, InterruptedException {
        Message notifMsg;
        String subjectContext = subject;
        subjectContext = subjectContext.replace("CreatedOrderNumber", expectedOrder.getOrderNumber());
        notifMsg = EmailUtilities.getEmailMessage(email, password, sender, subjectContext);
        Assert.assertTrue("Email notification of the Order creationisn't found", !notifMsg.equals(null));
        Serenity.getCurrentSession().put("emailMessage", notifMsg);
    }

    @Step
    public void isLetterContainsText(String expectedText) throws IOException, MessagingException {
        Message notifMsg;
        notifMsg = (Message) Serenity.getCurrentSession().get("emailMessage");
        Assert.assertTrue("Mismatch content", (((Multipart) notifMsg.getContent()).getBodyPart(0).getContent().toString()).contains(">" + expectedText + "<"));
    }

    @Step
    public void signInWithEmailAndPassword(String email, String password) {
        loginPage.signInWithEmailAndPassword(email, password);
    }

    @Step
    public void isLogoutButtonDisplayedInHeaderMenu() {
        Assert.assertTrue("Logout button isn't displayed", loginPage.isLogoutButtonDisplayedInHeaderMenu());
    }

    @Step
    public void isUpdateProfileButtonDisplayedInHeaderMenu() {
        Assert.assertTrue("Update Profile button isn't displayed", myAccountPage.isUpdateProfileButtonDisplayedInHeaderMenu());
    }

    @Step
    public void updateProfilePersonalDetails(CustomerProfile newPersonalDetails) {
        myAccountPage.updateProfilePersonalDetails(newPersonalDetails);
    }

    @Step
    public void clickOnSaveButtonProfilePersonalDetails() {
        myAccountPage.clickOnSaveButtonProfilePersonalDetails();
    }

    @Step
    public void updateProfileEmailTo(String keyEmail) {
        myAccountPage.updateProfileEmailTo((String) Serenity.getCurrentSession().get(keyEmail));
    }

    @Step
    public void enterPasswordForUpdatingEmail(String password) {
        myAccountPage.enterPasswordForUpdatingEmail(password);
    }

    @Step
    public void clickOnChangeEmailButtonForUpdatingEmail() {
        myAccountPage.clickOnChangeEmailButtonForUpdatingEmail();
    }

    @Step
    public void loginWithNewUpdatedEmail(String generatedEmail, String password) {
        loginPage.signInWithEmailAndPassword((String) Serenity.getCurrentSession().get(generatedEmail), password);
    }

    @Step
    public void clickOnAddNewAddressButtonForCustomerProfile() {
        myAccountPage.clickOnAddNewAddressButtonForCustomerProfile();
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
    public Boolean isCustomerProfileContainAddress(Address address) {
        Boolean flag = false;
        for (int i = 0; i < myAccountPage.getProfileAddresses().size(); i++) {
            if (myAccountPage.getProfileAddresses().get(i).toString().equals(address.toString())) {
                flag = true;
                break;
            }
        }
        return flag;
    }

    @Step
    public void isCustomerProfileNotContainRemovedAddress(Address address) {
        Assert.assertTrue("Address isn't removed: " + address.toString(), !isCustomerProfileContainAddress(address));
    }

    @Step
    public void clickOnRemoveButtonForCustomerProfileAddress(Address removingAddress) {
        myAccountPage.clickOnRemoveButtonForCustomerProfileAddress(removingAddress);
    }

    @Step
    public void clickOnButtonOnThePopUp(String buttonName, String popupName) {
        myAccountPage.clickOnButtonOnThePopUp(buttonName, popupName);
    }

    @Step
    public void clickOnSetAsDefaultButtonForCustomerProfileAddress(Address newDefaultAdress) {
        myAccountPage.clickOnSetAsDefaultButtonForCustomerProfileAddress(newDefaultAdress);
    }

    @Step
    public void enterValueIntoFieldOnUpdatingPassswordForm(String value, String fieldName) {
        myAccountPage.enterValueIntoFieldOnUpdatingPassswordForm(value, fieldName);
    }

    @Step
    public void clickOnToChangeCustomerPassword(String buttonName) {
        myAccountPage.clickOnToChangeCustomerPassword(buttonName);
    }

    @Step
    public void isPageOpened(String expectedUrl) {
        expectedUrl = expectedUrl.replace("BaseURL", BASE_URL);
        Assert.assertTrue("URL " + netAxeptPage.waitForUrl(expectedUrl) + "isn't contained " + expectedUrl,
                netAxeptPage.waitForUrl(expectedUrl).contains(expectedUrl));
    }

    @Step
    public void selectNetAxeptPaymentOption(String optionName) {
        netAxeptPage.selectNetAxeptPaymentOption(optionName);
    }

    @Step
    public void clickOnNextButtonOnNetAxeptPage() {
        netAxeptPage.clickOnNextButtonOnNetAxeptPage();
    }

    @Step
    public void enterCardNumberOnNetAxeptPage(String cardNumber) {
        netAxeptPage.enterCardNumber(cardNumber);
    }

    @Step
    public void enterValueIntoCVV2OnTheNetAxeptPage(String value) {
        netAxeptPage.enterCVV2(value);
    }

    @Step
    public void selectMonthAndYearForExpirationDateOnTheNetAxeptPage(String month, String year) {
        netAxeptPage.selectExpirationDate(month, year);
    }

    @Step
    public void clickOnPayButtonOnTheNetAxeptPage() {
        netAxeptPage.clickOnPayButton();
    }

    @Step
    public void isOrderNumberOnNetAxeptPageEqualToNumberInShoppingCart() {
        Assert.assertEquals(expectedOrder.getOrderNumber(), netAxeptPage.getOrderNumber());
    }

    @Step
    public void isOrderTotalOnNetAxeptPageEqualToSumShoppingCartTotalAndPriceDelivery() {
        Float expectedSum = Float.valueOf(expectedOrder.getTotalOrder().replace("-", "00").replace(",", ".")) + Float.valueOf(expectedOrder.getShippingPrice().replace(",", "."));
        Assert.assertEquals((String.format("%8.2f", expectedSum)).trim().replace(",", "."), netAxeptPage.getOrderTotal());
    }

    @Step
    public void isShoppingCartContainsProducts() {
        for (Product expectedProduct : expectedOrder.getProducts()) {
            Assert.assertEquals("Cart isn't contained product line: ", expectedProduct.toString(), shoppingCartPage.getProductFromCart(expectedProduct.getItemNumber()).toString());
        }
    }

    @Step
    public void onProductDetailsPageSelectQty(Integer qty) {
        productDetailsPage.selectQty(qty);
    }

    @Step
    public void clickOnAddToCartButtonOnProductDetailsPage() {
        productDetailsPage.clickOnAddToCartButton();
    }


    @Step
    public void isDeliveryInformationOnOrderConfirmationPage(Address expectedAddress) {

        Assert.assertEquals(expectedAddress.toString(), orderConfirmationPage.getOrderDetails().getDeliveryAddress().toString());
    }

    @Step
    public void isPaymentInformationOnOrderConfirmationPage(Address expectedAddress) {
        Assert.assertEquals(expectedAddress.toString(), orderConfirmationPage.getOrderDetails().getPaymentAddress().toString());
    }

    @Step
    public void isCorrectOrderTotalOnOrderConfirmationPage() {
        Assert.assertEquals((String.format("%8.2f", expectedOrder.getOrderTotalWithShippingPrice())).trim().replace(",", "."), orderConfirmationPage.getOrderDetails().getTotalOrder());
    }

    @Step
    public void isOrderHistoryListContainsCreatedOrder() {
        OrderHistoryLine expectedOrderLine = new OrderHistoryLine();
        expectedOrderLine.setOrderNumber(expectedOrder.getOrderNumber());
        expectedOrderLine.setOrderTotal(expectedOrder.getOrderTotalWithShippingPrice());
        expectedOrderLine.setStatus("Under arbeid");
        Assert.assertEquals("Created Order isn't found in History", expectedOrderLine.orderLinetoString(),
                myAccountPage.searchForOrderInOrderHistory(expectedOrder.getOrderNumber()).orderLinetoString());
    }

    @Step
    public void moveToCartButtonInHeader() {
        shoppingCartPage.moveToCartButtonInHeader();
    }

    @Step
    public void isMiniShoppingCartContainAddedToCartProducts() {
        Product cartProduct = new Product();
        for (Product expectedProduct : expectedOrder.getProducts()) {
            cartProduct = shoppingCartPage.getProductsFromMiniShoppingCart(expectedProduct.getItemNumber());
            Assert.assertEquals(expectedProduct.getProductName() + " " + expectedProduct.getQty() + " X " + expectedProduct.getTotal(),
                    cartProduct.getProductName() + " " + cartProduct.getQty() + " X " + cartProduct.getListPrice());
        }
    }

    @Step
    public void moveToItemInMainMenu(String itemName) {
        productsPage.moveToItemInMainMenu(itemName);
    }

    @Step
    public void selectProductCategoryAndSubcategory(String category, String subCategory) throws IOException {
        productsPage.selectProductCategoryFromMainMenu(category, subCategory);
    }

    @Step
    public void isTitleOfProductsPageContains(String title) {
        Assert.assertTrue("Title isn't contained " + title, productsPage.getTitle().contains(title));
    }

    @Step
    public void isLastBreadcrumbLinkContain(String text) {
        Assert.assertTrue("Last link of the BreadCrumb isn't contained" + text, productsPage.getBreadCrumbLinks().get(productsPage.getBreadCrumbLinks().size() - 1).contains(text));
    }

    @Step
    public void isSendByEmailOptionOnDeliveryMethodStepDisplayedWith(String message) {
        Assert.assertEquals(message, checkoutPage.getAdditionalMessageForSendByEmailOption());
    }

    @Step
    public void clickOnPaymentLinkNets() {
        checkoutPage.clickOnPaymentLinkNets();
    }

    @Step
    public void clickOnCreateYourAccountButton() {
        checkoutPage.clickOnCreateYourAccountButton();
    }

    @Step
    public void deleteAllProductsFromTheShoppingCart() {
        shoppingCartPage.deleteAllProductsFromTheShoppingCart();
    }

    @Step
    public void isMessageDisplayedInTheShoppingCart(String message) {
        Assert.assertEquals(message, shoppingCartPage.getMessageFromTheShoppingCart());
    }

    @Step
    public void isNotDisplayedCartCountOnHeaderCart() {
        Assert.assertEquals("", shoppingCartPage.getCartCount());
    }

    @Step
    public void isClickOnButtonOpenURL(String buttonName, String expectedUrl) {
        shoppingCartPage.clickOnButton(buttonName);
        Assert.assertEquals(BASE_URL + expectedUrl, shoppingCartPage.waitForURL(BASE_URL + expectedUrl));
    }

    @Step
    public void clearCustomerShoppingCart(String email, String password) {
        homePage.openHomePage(BASE_URL);
        try {
            loginPage.clickOnNewUserLoginButton();
            loginPage.signInWithEmailAndPassword(email, password);
        } catch (Exception e) {
        }
        if (!shoppingCartPage.getCartCount().equals("")) {
            homePage.clickOnCartButtonInHeader();
            shoppingCartPage.deleteAllProductsFromTheShoppingCart();
        }
        myAccountPage.clickOnLogoutButtonInHeaderMenu();
    }

    @Step
    public void isWarningPopUpContainsSelectedUnavailableProducts(List<Product> expectedProducts) {
        for (Product expectedProduct : expectedProducts) {
            Assert.assertEquals(expectedProduct.toString(), checkoutPage.getUnavailableProductsFromWarningPopUp(expectedProduct.getItemNumber()).toString());
        }
    }

    @Step
    public void clickOnRemoveAllButtonOntheWarningPopup() {
        checkoutPage.clickOnRemoveAllButtonOnWarningPopup();
    }

    @Step
    public void clickOnCancelButtonOnWarningPopup() {
        checkoutPage.clickOnCancelButtonOnWarningPopup();
    }

    @Step
    public void clickOnHeaderSearchButton() {
        checkoutPage.clickOnHeaderSearchButton();
    }

    @Step
    public void enterTextIntoHeaderSearchField(String searchText) {
        checkoutPage.enterTextIntoHeaderSearchField(searchText);
    }

    @Step
    public void enterPersonalCodeToVerifyAge(String code) {
        checkoutPage.enterPersonalCodeToVerifyAge(code);
    }

    @Step
    public void isShoppingCartCounterEqualTo(String expectedValue) {
        Assert.assertEquals(expectedValue, shoppingCartPage.getCartCount());
    }

    @Step
    public void clickOnTermsAndConditionsButtonOnCheckout() {
        checkoutPage.clickOnTermsAndConditionsButtonOnCheckout();
    }

    @Step
    public void isTitleOfTermsAndConditionsPopUp(String expectedTitle) {
        Assert.assertEquals(expectedTitle, checkoutPage.getTitleOfTermsAndConditionsPopUp());
    }

    @Step
    public void isTermsAndConditionsPopUpContains(List<String> expectedSubTitles) {
        List<String> actualSubTitles = new ArrayList<>();
        actualSubTitles = checkoutPage.getSubTitlesOfTermsAndConditionsPopUp();
        for (int i = 0; i < expectedSubTitles.size(); i++) {
            Assert.assertEquals(expectedSubTitles.get(i), actualSubTitles.get(i));
        }
    }

    @Step
    public void clickOnCloseButtonOfTheTermsAndConditionsPopUp() {
        checkoutPage.clickOnCloseButtonOfTheTermsAndConditionsPopUp();
    }

    @Step
    public void navigateToPreviousPage() {
        checkoutPage.navigateToPreviousPage();
    }

    @Step
    public void clickOnShowShoppingCartDropDown() {
        checkoutPage.clickOnShowShoppingCartDropDown();
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
    public void isFirstPharmacyInSearchResultsContains(String value, String column) {
        Assert.assertTrue("First product isn't comtained " + value + " " + productDetailsPage.getPharmacySearchResults(column).get(0),
                productDetailsPage.getPharmacySearchResults(column).get(0).contains(value));
    }

    @Step
    public void isPharmaciesInSearchResultsContains(String value, String column) {
        List<String> searchResults = new ArrayList<>();
        searchResults = productDetailsPage.getPharmacySearchResults(column);
        for (int i = 0; i < searchResults.size(); i++) {
            Assert.assertTrue("Search results isn't contained " + value + "in" + searchResults.get(i), searchResults.get(i).contains(value));
        }
    }

    @Step
    public void isOrderTotalInEmailOrderConformationEqualToCreatedOrder() throws MessagingException, SAXException, DocumentException, JDOMException, IOException {
        Message notifMsg;
        String emailOrderTotal;
        notifMsg = (Message) Serenity.getCurrentSession().get("emailMessage");
        emailOrderTotal = EmailUtilities.getOrderTotalFromEmail(notifMsg).replace(",", ".").replace("-", "0");
        Assert.assertEquals((Double) expectedOrder.getOrderTotalWithShippingPrice(), Double.valueOf(emailOrderTotal));
    }

    @Step
    public void selectDeliveryAddressGetAddressFromProfile() {
        checkoutPage.selectShippingMethodGetAddressFromProfile();
    }

    @Step
    public void selectDeliveryAddressContinueWithoutProfile() {
        checkoutPage.selectDeliveryAddressContinueWithoutProfile();
    }

    @Step
    public void clickOnDeleteButtonOnDeleteAddressPopUp() {
        myAccountPage.clickOnDeleteButtonOnDeleteAddressPopUp();
    }

    @Step
    public void clickOnSaveOrderButton() {
        orderConfirmationPage.clickOnSaveOrderButton();
    }

    @Step
    public void addToEPrescriptionOrder(List<String> products) {
        for (String product : products) {
            myPrescriptionPage.enterEPrescriptionProduct(product);
            myPrescriptionPage.clickOnAddProductToEprescription();
        }

    }

    @Step
    public void enterAdditionalInfoForEPrescriptionOrder(String info) {
        myPrescriptionPage.enterAdditionalInfoForEPrescriptionOrder(info);
    }

    @Step
    public void isMedicineListOfEprescriptionOrderContains(List<String> productsName) {
        String[] expectedProducts = productsName.toArray(new String[0]);
        String[] actualProducts = myPrescriptionPage.getMedicineListOfEprescriptionOrder();
        Arrays.sort(expectedProducts);
        Arrays.sort(actualProducts);
        Assert.assertArrayEquals(expectedProducts, actualProducts);
    }

    @Step
    public void enterCustomerInformationIntoField(String value, String fieldName) {
        myPrescriptionPage.enterCustomerInformationIntoField(value, fieldName);
    }

    @Step
    public void selectPharmacyRegionForEPrescriptionOrder(String regionName) {
        myPrescriptionPage.selectPharmacyRegionForEPrescriptionOrder(regionName);
    }

    @Step
    public void selectPharmacyForEPrescriptionOrder(String pharmacyName) {
        myPrescriptionPage.selectPharmacyForEPrescriptionOrder(pharmacyName);
    }

    @Step
    public void clickOnSendEPrescriptionButton() {
        myPrescriptionPage.clickOnSendEPrescriptionButton();
    }

    @Step
    public void isTitleOfEPrescriptionConfirmationPage(String expectedTitle) {
        Assert.assertEquals(expectedTitle, myPrescriptionPage.getTitleOfEPrescriptionConfirmationPage());
    }

    @Step
    public void isEprescriptionConfirmationPageContains(List<String> expectedProductsList) {
        String[] expectedProducts = expectedProductsList.toArray(new String[0]);
        String[] actualProducts = myPrescriptionPage.getProductsFromEprescriptionConfirmationPage();
        Arrays.sort(expectedProducts);
        Arrays.sort(actualProducts);
        Assert.assertArrayEquals(expectedProducts, actualProducts);
    }

    @Step
    public void isCustomerInformationFieldContains(String fieldName, String expectedValue) {
        Assert.assertEquals(expectedValue, myPrescriptionPage.getValueOfCustomerInformationField(fieldName));
    }

    @Step
    public void isAdditionalCommentOnEPrescriptionConfirmation(String expectedComment) {
        Assert.assertEquals(expectedComment, myPrescriptionPage.getAdditionalCommentFromEPrescriptionConfirmation());
    }

    @Step
    public void isRetrievedByPharmacyOnEPrescriptionConfirmationPage(String expectedPharmacy) {
        Assert.assertEquals(expectedPharmacy, myPrescriptionPage.getPharmacyFromEPrescriptionConfirmation());
    }

    @Step
    public void uncheckOptionIWantToBeVICustomer() {
        myAccountPage.uncheckOptionIWantToBeVICustomer();
    }

    @Step
    public void isNotSelectedOptionIWantToBeVICustomer() {
        Assert.assertTrue("I Want To Be VI+Customer shouldn't be selected", myAccountPage.getOptionIWantToBeVICustomerValue() == false);
    }

    @Step
    public void isTitleOfProductLineInCartContainsAppropriatePromotionalText() {
        for (Product expectedProduct : expectedOrder.getProducts()) {
            Assert.assertTrue("Mismatch Promotional product title", shoppingCartPage.getProductLineTitle(expectedProduct.getProductName())
                    .contains(expectedProduct.getPromotionalText()));
        }
    }

    @Step
    public void enterValueIntoFieldForMyPrescription(String value, String fieldName) {
        myPrescriptionPage.enterValueIntoFieldForMyPrescription(value, fieldName);
    }

    @Step
    public void clickOnOKButtonForMyPrescriptionLoginForm() {
        myPrescriptionPage.clickOnOKButtonForMyPrescriptionLoginForm();
    }

    @Step
    public void isCustomerMyPrescriptionsPageOpened(String customerSocialNumber) {
        Assert.assertTrue(myPrescriptionPage.getCustomerSocialNumberFromMyPrescriptionsPage().contains(customerSocialNumber));
    }

    @Step
    public void clickOnLogoutButtonOnMyPrescriptionsPage() {
        myPrescriptionPage.clickOnLogoutButtonOnMyPrescriptionsPage();
    }

    @Step
    public void enterReseptposeIDOfMyPrescription(String value) {
        myPrescriptionPage.enterReseptposeIDOfMyPrescription(value);
    }

    @Step
    public void addProductToShoppingCart(List<Product> products) throws IOException, InterruptedException {
        for (Product product : products) {
            basePage.clickOnHeaderSearchButton();
            basePage.searchForProduct(product.getItemNumber());
            for (int i = 1; i <= product.getQty(); i++) {
                productsPage.addToCartProduct(product.getItemNumber());
            }
            expectedOrder.addProduct(new Product(product));
            Thread.sleep(500);
        }
    }

    @Step
    public void globalSearchForProduct(Product product) {
        basePage.clickOnHeaderSearchButton();
        basePage.searchForProduct(product.getItemNumber());
    }

    @Step
    public void clickOnProductName(String productName) {
        productsPage.clickOnProduct(productName);
    }

    @Step
    public void isProductDetailsPageOpenedWith(Product expectedProduct) {
        Assert.assertEquals(expectedProduct.toString(), productDetailsPage.getProduct().toString());
    }

    @Step
    public void globalSearchFor(String value) {
        basePage.clickOnHeaderSearchButton();
        basePage.searchForProduct(value);
    }

    @Step
    public void searchResultsShouldBeOpenedWithProducts(List<Product> expectedProducts) {
        for (Product expectedProduct : expectedProducts) {
            Assert.assertEquals(expectedProduct.toString(), productsPage.getProduct(expectedProduct.getItemNumber()).toString());
        }
    }

    @Step
    public void checkValueInOrderOverviewSection(String fieldName) {
        Double productsSum = 0.0;
        Double discountSum = 0.0;
        Double total = 0.0;
        for (Product product : expectedOrder.getProducts()) {
            productsSum = productsSum + product.getQty() * product.getListPrice();
            if (product.getDiscountPrice() != null) {
                discountSum = discountSum + product.getQty() * (product.getListPrice() - product.getDiscountPrice());
            }
            total = total + product.getTotal();
        }
        switch (fieldName) {
            case "Produkter":
                Assert.assertEquals("Mismatch " + fieldName, Double.valueOf(String.format("%8.2f", productsSum).replace(",", ".")), shoppingCartPage.getValueFromOrderOverview(fieldName));
                break;
            case "Rabatt:":
                Assert.assertEquals("Mismatch " + fieldName, Double.valueOf(String.format("%8.2f", discountSum).replace(",", ".")), shoppingCartPage.getValueFromOrderOverview(fieldName));
                break;
            case "Total":
                Assert.assertEquals("Mismatch " + fieldName, Double.valueOf(String.format("%8.2f", total).replace(",", ".")), shoppingCartPage.getValueFromOrderOverview(fieldName));
                break;
        }
    }

    @Step
    public void selectFromMainMenu(String menuItem, String option) {
        basePage.selectFromMainMenu(menuItem, option);
    }

    @Step
    public void createCustomerProfileIfNotExists(CustomerProfile profile) {
        String CSRFToken;
        Cookies cookies;
        Document document;

        Response response = given().relaxedHTTPSValidation()
                .header("Content-Type", "application/x-www-form-urlencoded")
                .get(BASE_URL);
//        Assert.assertTrue(response.then().extract().statusCode() == 200);
        document = Jsoup.parse(response.asString());
        CSRFToken = Xsoup.compile("//*[@name='CSRFToken']").evaluate(document).getElements().get(0).attr("value");
        Serenity.getCurrentSession().put("CSRFToken", CSRFToken);

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

        if (response.then().extract().body().asString().contains("Velkommen ") == false) {
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
                    .get(BASE_URL+"/my-account/update-password");
            document = Jsoup.parse(response.asString());
            CSRFToken = Xsoup.compile("//*[@name='CSRFToken']").evaluate(document).getElements().get(0).attr("value");

            if (response.then().extract().body().asString().contains("Velkommen ") == true) {
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
                        .header("Upgrade-Insecure-Requests","1")
                        .header("Referer",BASE_URL+"/login/register")
                        .header("Origin",BASE_URL)
                        .header("Accept-Encoding","gzip, deflate, br")
                        .formParam("CSRFToken", CSRFToken)
                        .formParam("_consentForNewsletter", "on")
                        .formParam("_favuoritePharmaciesCodes", "on")
                        .formParam("_hasMembership", "on")
                        .formParam("_isResponsibleForChildren", "on")
                        .formParam("_memberClubCodes", "on")
                        .formParam("_specialInterestGroupCodes", "on")
                        .formParam("bornYear", "2017")
                        .formParam("checkPwd", profile.getPassword())
                        .formParam("email", profile.getEmail())
                        .formParam("firstName", profile.getFirstname())
                        .formParam("lastName", profile.getLastName())
                        .formParam("line1", profile.getAddress())
                        .formParam("mobileNumber", profile.getPhoneNumber())
                        .formParam("postcode", profile.getPostCode())
                        .formParam("pwd", profile.getPassword())
                        .formParam("townCity", profile.getCity())
                        .cookies(cookies)
                        .post(BASE_URL + "/login/register");
            }
        }
        cookies = response.getDetailedCookies();
        response = given().relaxedHTTPSValidation()
                .header("Upgrade-Insecure-Requests", "1")
                .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8")
                .cookies(cookies)
                .get(BASE_URL + "/logout");
    }
}

