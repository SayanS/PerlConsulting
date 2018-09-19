package lloydsPharmaProject.steps;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import lloydsPharmaProject.Models.Address;
import lloydsPharmaProject.Models.CustomerProfile;
import lloydsPharmaProject.Models.Product;
import lloydsPharmaProject.steps.serenity.EndUserSteps;
import net.serenitybdd.core.Serenity;
import net.thucydides.core.annotations.Steps;
import org.dom4j.DocumentException;
import org.jdom.JDOMException;
import org.xml.sax.SAXException;

import javax.mail.MessagingException;
import java.awt.*;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;
import java.util.Map;

public class DefinitionSteps {

    @Steps
    EndUserSteps userSteps;

    @Given("^Login page is opened$")
    public void loginPageIsOpened() throws IOException {
        userSteps.openLoginPage();
    }

    @Given("^Language \"([^\"]*)\" is selected$")
    public void languageIsSelected(String language) {
        switch (language) {
            case "FR":
                userSteps.selectLanguage("fr_BE");
                break;
            case "NL":
                userSteps.selectLanguage("nl_BE");
                break;
        }
        Serenity.getCurrentSession().put("currentLanguage", language);
    }

    @When("^Fill the registration form with data$")
    public void fillTheRegistrationFormWithData(List<CustomerProfile> customerProfile) {
        userSteps.fillTheRegistrationFormWithData(customerProfile.get(0));
    }

    @When("^Click on New User/Login button$")
    public void clickOnNewUserLoginButton() throws IOException {
        userSteps.clickOnNewUserLoginButton();
    }


    @Then("^The background of selected fields should be \"([^\"]*)\" and ticked$")
    public void theBackgroundOfSelectedFieldsShouldBeFilledAndTicked(String color, List<CustomerProfile> fields) {
        userSteps.isBackgroundOfSelectedFieldsFilledAndTicked(fields.get(0), color);
    }

    @When("^Click on Create free Account button$")
    public void clickOnCreateFreeAccountButton() {
        userSteps.clickOnCreateFreeAccountButton();
    }

    @Then("^Alert message \"([^\"]*)\" should be displayed$")
    public void alertMessageShouldBeDisplayed(String message) throws IOException {
        userSteps.isAlertDisplayedLoginProfile(message);
    }

    @Then("^My Account page should be opened$")
    public void myAccountPageShouldBeOpened() {
        userSteps.isMyAccountPageOpened();
    }

    @When("^Click on Update Profile button in Header menu$")
    public void selectMyAccountItemInHeaderMenu() {
        userSteps.selectMyAccountItemInHeaderMenu();
    }

    @Then("^Updating personal details of Update Profile Page should be opened with registration data$")
    public void updateProfilePageShouldBeOpenedWithRegistrationData() throws Throwable {
        userSteps.isProfilePageContainsAllRegistrationData();
    }

    @When("^Select Special Interest Group$")
    public void selectSpecialInterestGroup(List<String> specialGroups) {
        userSteps.selectSpecialInterestGroup(specialGroups);
    }

    @When("^Select Update Email item from side menu of MyAccount$")
    public void clickOnUpdateEmailButton() {
        userSteps.clickOnUpdateEmailButton();
    }

    @Then("^Update Email page should be opened with email from registration data$")
    public void updateEmailPageShouldBeOpenedWithEmailFromRegistrationData() {
        userSteps.isUpdateEmailPageContainsEmailFromRegistrationData();
    }

    @When("^Select Manage Delivery Addresses item from side menu of MyAccount$")
    public void selectManageDeliveryAddresesItemFromSideMenuOfMyAccount() {
        userSteps.clickOnManageDeliveryAddresesButton();
    }

    @Then("^Address book should contain registration data$")
    public void addressBookShouldContainRegistrationData() {
        userSteps.isAddressBookContainsRegistrationData();
    }

    @When("^Enter Favorite Pharmacies$")
    public void enterFavoritePharmacies(List<String> favoritePharmacies) {
        userSteps.enterFavoritePharmacies(favoritePharmacies);
    }

    @And("^Sign In with email \"([^\"]*)\" and password \"([^\"]*)\"$")
    public void signInWithEmailAndPassword(String email, String password) {
        userSteps.signIn(email, password);
    }

    @Then("^Logout button should be displayed in Header menu$")
    public void logoutButtonShouldBeDisplayedInHeaderMenu() {
        userSteps.isLogoutButtonDisplayed();
    }

    @Then("^Update Profile button should be displayed in Header menu$")
    public void updateProfileButtonShouldBeDisplayedInHeaderMenu() {
        userSteps.isUpdateProfileButtonDisplayed();
    }

    @And("^Click on Logout button in Header menu$")
    public void clickOnLogoutButtonInHeaderMenu() throws Throwable {
        userSteps.clickOnLogoutButtonInHeaderMenu();
    }

    @Then("^Update Profile button shouldn't be displayed in Header menu$")
    public void updateProfileButtonShouldnTBeDisplayedInHeaderMenu() {
        userSteps.isUpdateProfileButtonNotDisplayed();
    }

    @Then("^The caption of Update Profile button should contains First Name from registration form$")
    public void theCaptionOfUpdateProfileButtonShouldContainsFirstNameFromRegistrationForm() {
        userSteps.isUpdateProfileButtonContainsRegistrationFirstName();
    }

    @Then("^Mail \"([^\"]*)\" password \"([^\"]*)\" should contains letter from \"([^\"]*)\" subject \"([^\"]*)\"$")
    public void emailNotificationShouldBeSentToTheRegistrationEmail(String reciver, String password, String sender, String subject) throws IOException, MessagingException, InterruptedException, GeneralSecurityException {
        userSteps.checkRegistrationNotification(reciver, password, sender, subject);
    }

    @When("^Update Profile email to the new generated one without changing password \"([^\"]*)\"$")
    public void updateProfileEmailWithTheNewGeneratedEmail(String password) {
        userSteps.updateProfileEmailToNewGeneratedEmail(password);
    }

    @Then("^Update Personal Details page should be opened$")
    public void updatePersonalDetailsPageShouldBeOpened() {
        userSteps.isUpdatePersonalDetailsOpened();
    }

    @Then("^Update Phone number to the new generated one$")
    public void updatePhoneNumberToTheNewGeneratedOne() {
        userSteps.updatePhoneNumberToTheNewGeneratedOne();
    }

    @And("^Login with new Updated Email and password \"([^\"]*)\"$")
    public void loginWithNewUpdatedEmail(String password) throws InterruptedException {
        userSteps.loginWithNewUpdatedEmail(password);
    }

    @When("^Enter Title \"([^\"]*)\" into registration form$")
    public void enterTitleIntoRegistrationForm(String title) {
        userSteps.enterTitleIntoRegistrationForm(title);
    }

    @When("^Enter First name \"([^\"]*)\" into registration form$")
    public void enterFirstNameIntoRegistrationForm(String firstName) {
        userSteps.enterFirstNameIntoRegistrationForm(firstName);
    }

    @When("^Enter Last name \"([^\"]*)\" into registration form$")
    public void enterLastNameIntoRegistrationForm(String lastName) {
        userSteps.enterLastNameIntoRegistrationForm(lastName);
    }

    @When("^Enter Phone number \"([^\"]*)\" into registration form$")
    public void enterPhoneNumberIntoRegistrationForm(String phoneNumber) {
        userSteps.enterPhoneNumberIntoRegistrationForm(phoneNumber);
    }

    @When("^Enter Address \"([^\"]*)\" into registration form$")
    public void enterAddressIntoRegistrationForm(String address) {
        userSteps.enterAddressIntoRegistrationForm(address);
    }

    @When("^Enter Building \"([^\"]*)\" into registration form$")
    public void enterBuildingIntoRegistrationForm(String building) {
        userSteps.enterBuildingIntoRegistrationForm(building);
    }

    @When("^Enter Box \"([^\"]*)\" into registration form$")
    public void enterBoxIntoRegistrationForm(String box) {
        userSteps.enterBoxIntoRegistrationForm(box);
    }

    @When("^Enter Post code \"([^\"]*)\" into registration form$")
    public void enterPostCodeIntoRegistrationForm(String posteCode) {
        userSteps.enterPostCodeIntoRegistrationForm(posteCode);
    }

    @When("^Enter City \"([^\"]*)\" into registration form$")
    public void enterCityIntoRegistrationForm(String city) {
        userSteps.enterCityIntoRegistrationForm(city);
    }

    @When("^Enter Email \"([^\"]*)\" into registration form$")
    public void enterEmailIntoRegistrationForm(String email) {
        userSteps.enterEmailIntoRegistrationForm(email);
    }

    @When("^Enter Password \"([^\"]*)\" into registration form$")
    public void enterPasswordIntoRegistrationForm(String password) {
        userSteps.enterPasswordIntoRegistrationForm(password);
    }

    @When("^Enter Password confirmation \"([^\"]*)\" into registration form$")
    public void enterPasswordConfirmationIntoRegistrationForm(String passwordConfirmation) {
        userSteps.enterPasswordConfirmationIntoRegistrationForm(passwordConfirmation);
    }

    @Then("^Error message \"([^\"]*)\" should be displayed below the field$")
    public void errorMessageShouldBeDisplayedBelowTheField(String message) throws IOException {
        userSteps.isErrorMessageDisplayed(message);
    }

    @Then("^Letter should contains text \"([^\"]*)\"$")
    public void letterShouldContainsText(String expectedText) throws IOException, MessagingException {
        userSteps.isLetterContainsText(expectedText);
    }


    @When("^Click on Global Search button$")
    public void clickOnGlobalSearchButton() throws InterruptedException {
        userSteps.clickOnGlobalSearchButton();
    }

    @When("^Search for product$")
    public void searchForProduct(Product product) throws IOException {
        userSteps.searchForProduct(product);
    }

    @Then("^Mini shopping Cart should be appeared for a few seconds$")
    public void miniShoppingCartShouldBeDisplayedForAFewSeconds() {
        userSteps.isMiniShoppingCartDisplayed();
    }

    @Then("^The Product Counter on Shopping Cart button should be \"([^\"]*)\"$")
    public void theProductCounterOnShoppingCartButtonShouldBe(String value) {
        userSteps.isCounterOnShoppingCartEqual(value);
    }

    @Then("^Mini shopping Cart should contains products$")
    public void miniShoppingCartShouldContainsProducts(List<Product> products) throws IOException {
        userSteps.isMiniShoppingCartContains(products);
    }

    @When("^Click on Go Cart button on Mini Cart$")
    public void clickOnGoCartButtonOnMiniCart() {
        userSteps.clickOnGoCartButtonOnMiniCart();
    }

    @When("^Remove All products from the Cart$")
    public void removeAllProductsFromTheCart() throws InterruptedException {
        userSteps.removeAllProductsFromTheCart();
    }

    @When("^Click on Cart button in header$")
    public void clickOnCartButtonInHeader() throws InterruptedException {
        userSteps.clickOnCartButtonInHeader();
    }

    @When("^Select \"([^\"]*)\" catagory and \"([^\"]*)\" subcategory from header Products drop-downn$")
    public void selectCatagoryAndSubcategoryFromHeaderProductsDropDownn(String category, String subCategory) throws IOException {
        userSteps.selectCatagoryFromHeaderProductsDropDownn(category, subCategory);
    }

    @When("^Move to Products drop-down in header$")
    public void moveToProductsDropDownInHeader() {
        userSteps.moveToProductsDropDownInHeader();
    }

    @Then("^Title of the products page should be \"([^\"]*)\"$")
    public void titleOfTheProductsPageShouldBe(String expectedTitle) throws IOException {
        userSteps.isTitleOfTheProductsPage(expectedTitle);
    }

    @When("^Click on Checkout button in the Cart$")
    public void clickOnCheckoutButtonInTheCart() throws InterruptedException {
        userSteps.clickOnCheckoutButtonInTheCart();
    }

    @When("^Select Shipping method Send to address$")
    public void selectShippingMethodSendToAddress() {
        userSteps.selectShippingMethodSendToAddress();
    }

    @When("^Click on Next button on the first Step of Checkout$")
    public void clickOnNextButtonOnTheFirstStepOfCheckout() {
        userSteps.clickOnNextButtonOnTheFirstStepOfCheckout();
    }

    @When("^Select option Get address from your profile$")
    public void selectOptionGetAddressFromYourProfile() {
        userSteps.selectOptionGetAddressFromYourProfile();
    }

    @When("^Enter email \"([^\"]*)\" and password \"([^\"]*)\" on Step 2 of the Checkout$")
    public void enterEmailAndPasswordOnStepOfTheCheckout(String email, String password) {
        userSteps.enterCredentialsOnStep2Checkout(email, password);
    }

    @When("^Click on Further to the shipping and payment button$")
    public void clickOnFurtherToTheShippingAndPaymentButton() throws InterruptedException {
        userSteps.clickOnFurtherToTheShippingAndPaymentButton();
    }

    @Then("^Page \"([^\"]*)\" should be opened$")
    public void isPageOpened(String url) throws Throwable {
        userSteps.isCurrentURL(url);
    }

    @When("^In opened iframe select \"([^\"]*)\" Delivery Method$")
    public void inOpenedIframeSelectDeliveryMethod(String method) throws IOException {
        userSteps.selectDeliveryMethodInIframe(method);
    }

    @When("^Select Delivery address \"([^\"]*)\"$")
    public void selectDeliveryAddress(String address) {
        userSteps.selectDeliveryAddress(address);
    }

    @When("^Click on Further button on Step 2$")
    public void clickOnFurtherButtonOnStep2() {
        userSteps.clickOnFurtherButtonOnStep2();
    }

    @When("^Enter value \"([^\"]*)\" into the \"([^\"]*)\" field on Delivery place step$")
    public void enterValueIntoTheFieldOnDeliveryPlaceStep(String value, String fieldName) {
        userSteps.enterValueIntoTheFieldOnDeliveryPlaceStep(value, fieldName);
    }

    @When("^Click on Next button on Delivery place step$")
    public void clickOnNextButtonOnDeliveryPlaceStep() {
        userSteps.clickOnNextButtonOnDeliveryPlaceStep();
    }

    @When("^Select option Continue without profile for Delivery address$")
    public void selectOptionContinueWithoutProfileForDeliveryAddress() {
        userSteps.selectOptionContinueWithoutProfileForDeliveryAddress();
    }

    @When("^Select Title \"([^\"]*)\" for Profile details for Delivery Address$")
    public void selectTitleForProfileDetailsForDeliveryAddress(String title) throws IOException {
        userSteps.selectTitleForProfileDetailsForDeliveryAddress(title);
    }

    @Then("^Buy now button shouldn't be displayed for Products with status \"([^\"]*)\"$")
    public void buyNowButtonShouldnTBeDisplayedForProductsWithStatus(String status) throws IOException {
        userSteps.buyNowButtonShouldnTBeDisplayedForProductsWithStatus(status);
    }

    @Then("^Inactive Out of Stock button should be displayed instead of Add to Cart$")
    public void inactiveOutOfStockButtonShouldBeDisplayedInsteadOfAddToCart() {
        userSteps.isNotDisplayedOutOfStockButtonOnProductDetailsPage();
    }

    @Then("^Cart should by empty$")
    public void cartShouldByEmpty() throws IOException {
        userSteps.isCartEmpty();
    }

    @When("^Select \"([^\"]*)\" item in Header menu$")
    public void selectItemInHeaderMenu(String itemName) throws IOException {
        userSteps.selectItemInHeaderMenu(itemName);
    }

    @When("^Upload prescription scan \"([^\"]*)\"$")
    public void uploadPrescriptionScan(String filePath) throws AWTException {
        userSteps.uploadPrescriptionScan(filePath);
    }

    @When("^Enter \"([^\"]*)\" into the \"([^\"]*)\" field of My prescription Form$")
    public void enterIntoTheFieldOfMyPrescriptionForm(String value, String fieldName) throws IOException {
        userSteps.enterValueIntoTheFieldOfMyPrescriptionForm(value, fieldName);
    }

    @When("^Select \"([^\"]*)\" option for communication of My prescription Form$")
    public void selectOptionForCommunicationOfMyPrescriptionForm(String optionName) {
        userSteps.selectOptionForCommunicationOfMyPrescriptionForm(optionName);
    }

    @When("^Enter \"([^\"]*)\" value into Card Number field on Ingenico page$")
    public void enterValueIntoCardNumberFieldOnIngenicoPage(String cardNumber) {
        userSteps.enterIntoCardNumberFieldOnIngenicoPage(cardNumber);
    }

    @When("^Select \"([^\"]*)\" value from the Month drop-down of Expiration date on Ingenico page$")
    public void selectValueFromTheMonthDropDownOfExpirationDateOnIngenicoPage(String month) {
        userSteps.selectMonthOfExpirationDateOnIngenicoPage(month);
    }

    @When("^Select \"([^\"]*)\" value from the Year drop-down of Expiration date on Ingenico page$")
    public void selectValueFromTheYearDropDownOfExpirationDateOnIngenicoPage(String year) {
        userSteps.selectYearDropDownOfExpirationDateOnIngenicoPage(year);
    }

    @When("^Click on Confirm my Order button on Ingenico page$")
    public void clickOnConfirmMyOrderButtonOnIngenicoPage() {
        userSteps.clickOnConfirmMyOrderButtonOnIngenicoPage();
    }

    @Then("^Order details page should be displayed with title \"([^\"]*)\"$")
    public void orderDetailsPageShouldBeDisplayedWithTitle(String title) throws IOException {
        userSteps.isOrderDetailsPageDisplayedWithTitle(title);
    }

    @When("^Enter \"([^\"]*)\" into \"([^\"]*)\" field in Profile details for Delivery Address$")
    public void enterIntoFieldInProfileDetailsForDeliveryAddress(String value, String fieldName) throws IOException {
        userSteps.enterValueInProfileDetailsForDeliveryAddress(value, fieldName);
    }

    @When("^Click on Place Order button$")
    public void clickOnPlaceOrderButton() {
        userSteps.clickOnPlaceOrderButton();
    }

    @When("^Click on Next button on Delivery option step$")
    public void clickOnNextButtonOnDeliveryOptionStep() {
        userSteps.clickOnNextButtonOnDeliveryOptionStep();
    }

    @When("^Select Shipping method Get in pharmacy or delivery location$")
    public void selectShippingMethodGetInPharmacyOrDeliveryLocation() {
        userSteps.selectShippingMethodGetInPharmacyOrDeliveryLocation();
    }

    @When("^Search for \"([^\"]*)\" as Desired pharmacy for delivery location$")
    public void searchDesiredPharmacyForDeliveryLocation(String pharmacyName) {
        userSteps.searchDesiredPharmacyForDeliveryLocation(pharmacyName);
    }

    @When("^Select Desired pharmacy \"([^\"]*)\" in search results$")
    public void selectDesiredPharmacyInSearchResults(String pharmacyName) {
        userSteps.selectDesiredPharmacyInSearchResults(pharmacyName);
    }

    @Then("^\"([^\"]*)\" should be displayed in Shipping method section$")
    public void shouldBeDisplayedInShippingMethodSection(String pharmacyName) {
        userSteps.isDisplayedInShippingMethodSection(pharmacyName);
    }

    @When("^Enter \"([^\"]*)\" into the \"([^\"]*)\" field for reminder$")
    public void enterIntoTheFieldForReminder(String value, String fieldName) throws IOException {
        userSteps.enterIntoTheFieldForReminder(value, fieldName);
    }

    @When("^Check Notification type \"([^\"]*)\"$")
    public void checkNotificationType(String notificationType) {
        userSteps.checkNotificationType(notificationType);
    }

    @When("^Enter an Order notes \"([^\"]*)\"$")
    public void enterAnOrderNotes(String notes) {
        userSteps.enterAnOrderNotes(notes);
    }

    @When("^Click on Next button on Step 1 in case Get in pharmacy$")
    public void clickOnNextButtonOnStep1InCaseGetInPharmacy() {
        userSteps.clickOnNextButtonOnStep1InCaseGetInPharmacy();
    }

    @When("^Click on Add new address for Delivery$")
    public void clickOnAddNewAddressForDelivery() {
        userSteps.clickOnAddNewAddressForDelivery();
    }

    @When("^Select \"([^\"]*)\" value from Land field for New Delivery Address$")
    public void selectValueFromLandFieldForNewDeliveryAddress(String value) {
        userSteps.selectValueFromLandFieldForNewDeliveryAddress(value);
    }

    @When("^Enter \"([^\"]*)\" into \"([^\"]*)\" field for New Delivery Address$")
    public void enterIntoFieldForNewDeliveryAddress(String value, String fieldName) throws IOException {
        userSteps.enterIntoFieldForNewDeliveryAddress(value, fieldName);
    }

    @When("^Click on Save Address In My AddressBook checkbox$")
    public void clickOnSaveAddressInMyAddressBookCheckbox() {
        userSteps.clickOnSaveAddressInMyAddressBookCheckbox();
    }

    @Then("^Order Number on Confirmation page should be equal to Order Number which had been displayed in Shopping Cart$")
    public void orderNumberOnConfirmationPageShouldBeEqualToOrderNumberWhichHadBeenDisplayedInShoppingCart() {
        userSteps.isOrderNumberOnConfirmationPageEqualToOrderNumberInShoppingCart();
    }

    @Then("^All added to Cart products with appropriate Qty and Price should be in the Order$")
    public void allAddedToCartProductsWithAppropriateQtyAndPriceShouldBeInTheOrder() throws IOException {
        userSteps.isAllAddedToCartProductsWithAppropriateQtyAndPriceInTheOrder();
    }

    @Then("^Order Total should be the same as in the Shopping Cart$")
    public void orderTotalShouldBeTheSameAsInTheShoppingCart() {
        userSteps.isOrderTotalEqualToTotalOfShoppingCart();
    }

    @When("^Select Order History item from left side menu$")
    public void selectOrderHistoryItemFromLeftSideMenu() {
        userSteps.selectOrderHistoryItemFromLeftSideMenu();
    }

    @Then("^Created order should be in the Order History list$")
    public void createdOrderShouldBeInTheOrderHistoryList() {
        userSteps.isCreatedOrderAppearedInOrderHistoryList();
    }

    @Then("^The Price of Created order in the Order History list should be the same as in the Cart$")
    public void thePriceOfCreatedOrderInTheOrderHistoryListShouldBeTheSameAsInTheCart() {
        userSteps.isPriceOfCreatedOrderInOrderHistoryListEqualToCart();
    }

    @Then("^Uploaded file name \"([^\"]*)\" should be displayed in the Prescription form$")
    public void uploadedFileShouldBeDisplayedInThePrescriptionForm(String fileName) {
        userSteps.isUploadedFileDisplayedInThePrescriptionForm(fileName);
    }

    @When("^Click on Your pharmacy button of My prescription form$")
    public void clickOnYourPharmacyButtonOfMyPrescriptionForm() {
        userSteps.clickOnYourPharmacyButtonOfMyPrescriptionForm();
    }

    @When("^Enter \"([^\"]*)\" into search field in Find your pharmacy pop-up$")
    public void enterIntoSearchFieldInFindYourPharmacyPopUp(String value) {
        userSteps.enterValueIntoSearchFieldInFindYourPharmacyPopUp(value);
    }

    @When("^Click on Find Pharmacy button in Find your pharmacy pop-up$")
    public void clickOnFindPharmacyButtonInFindYourPharmacyPopUp() {
        userSteps.clickOnFindPharmacyButtonInFindYourPharmacyPopUp();
    }

    @When("^Choose Pharmacy number \"([^\"]*)\" from Search results in Find your pharmacy pop-up$")
    public void choosePharmacyNumberFromSearchResultsInFindYourPharmacyPopUp(Integer index) {
        userSteps.choosePharmacyFromSearchResultsInFindYourPharmacyPopUp(index);
    }

    @When("^Close Find your pharmacy pop-up$")
    public void closeFindYourPharmacyPopUp() {
        userSteps.closeFindYourPharmacyPopUp();
    }

    @When("^Click on Send Prescription button$")
    public void clickOnSendPrescriptionButton() {
        userSteps.clickOnSendPrescriptionButton();
    }

    @Then("^\"([^\"]*)\" button should be displayed for Pharmacy number \"([^\"]*)\"$")
    public void buttonShouldBeDisplayedForPharmacyNumber(String buttonName, Integer index) throws IOException {
        userSteps.isButtonDisplayedForPharmacyNumber(buttonName, index);
    }

    @Then("^Chosen Pharmacy should be displayed in Prescription form$")
    public void chosenPharmacyShouldBeDisplayedInPrescriptionForm() {
        userSteps.isChosenPharmacyDisplayedInPrescriptionForm();
    }

    @Then("^E-receipt page should be opened with the Messages$")
    public void eReceiptPageShouldBeOpenedWithTheMessages(List<String> messages) throws IOException {
        userSteps.isEReceiptPageOpenedWithMessages(messages);
    }


    @Then("^Prescription order comment should be \"([^\"]*)\"$")
    public void prescriptionOrderCommentShouldBeDisplayed(String comment) {
        userSteps.isPrescriptionOrderCommentDisplayed(comment);
    }

    @Then("^Pickup at Pharmacy value should be the Pharmacy Name from Prescription form$")
    public void pickupAtPharmacyValueShouldBeThePharmacyNameFromPrescriptionForm() {
        userSteps.isPickupPharmacyEqualToPharmacyFromPrescriptionForm();
    }

    @When("^Download prescription file$")
    public void downloadPrescriptionFile() {
        userSteps.downloadPrescriptionFile();
    }

    @When("^Click on Remove button for Uploaded prescription$")
    public void clickOnRemoveButtonForUploadedPrescription() {
        userSteps.clickOnRemoveButtonForUploadedPrescription();
    }

    @Then("^Uploaded file should be removed from Prescription form$")
    public void uploadedFileShouldBeRemovedFromPrescriptionForm() {
        userSteps.isUploadedFileNameNotDisplayedInThePrescriptionForm();
    }

    @When("^Select Manage Address item from side menu of MyAccount$")
    public void selectManageAddressItemFromSideMenuOfMyAccount() {
        userSteps.selectManageAddressItemFromSideMenuOfMyAccount();
    }

    @Then("^Profile should contains address$")
    public void isProfileContainsAddress(List<Address> address) {
        userSteps.isProfileContainsAddress(address.get(0));
    }

    @Then("^\"([^\"]*)\" file should be in folder \"([^\"]*)\"$")
    public void fileShouldBeInFolder(String fileName, String path) throws IOException, InterruptedException {
        userSteps.isFileExistInFolder(fileName, path);
    }

    @Then("^Each Pharmacy name in Search results should contains \"([^\"]*)\"$")
    public void eachPharmacyNameInSearchResultsShouldContains(String pharmacyName) {
        userSteps.isEachPharmacyNameInSearchResultsContains(pharmacyName);
    }

    @Then("^Delivery address on the Order Confirmation page should be equal to New added Address$")
    public void deliveryAddressOnTheOrderConfirmationPageShouldBeEqualToNewAddedAddress(List<Address> address) {
        userSteps.isDeliveryAddressOnOrderConfirmationPageEqualToNewAddedAddress(address.get(0));
    }

    @Then("^Payment address on the Order Confirmation page should be equal to New added Address$")
    public void paymentAddressOnTheOrderConfirmationPageShouldBeEqualToNewAddedAddress(List<Address> expectedAddress) {
        userSteps.isPaymentAddressOnOrderConfirmationPageEqualToNewAddedAddress(expectedAddress.get(0));
    }

    @When("^Go through Ingenico additional security pages with password \"([^\"]*)\"$")
    public void goThroughIngenicoAdditionalSecurityPagesWithPassword(String password) {
        userSteps.goThroughIngenicoAdditionalSecurityPages(password);
    }

    @When("^Click on Add new address button$")
    public void clickOnAddNewAddressButton() {
        userSteps.clickOnAddNewAddressButton();
    }

    @Then("^Delivery address on the Order Confirmation page should be Default address from Customer profile$")
    public void deliveryAddressOnTheOrderConfirmationPageShouldBeDefaultAddressFromCustomerProfile(List<Address> defaultAddress) {
        userSteps.isDeliveryAddressOnTheOrderConfirmationPageEqualTo(defaultAddress.get(0));
    }

    @Then("^Payment address on the Order Confirmation page should be Default address from Customer profile$")
    public void paymentAddressOnTheOrderConfirmationPageShouldBeDefaultAddressFromCustomerProfile(List<Address> defaultAddress) throws IOException {
        userSteps.isPaymentAddressOnTheOrderConfirmationPageEqualTo(defaultAddress.get(0));
    }

    @Given("^Confirm using cookies$")
    public void confirmUsingCookies() {
        userSteps.confirmUsingCookies();
    }

    @When("^Generate phone number \"([^\"]*)\"$")
    public void generatePhoneNumber(String keyPhone) {
        userSteps.generatePhoneNumberkeyPhone(keyPhone);
    }

    @When("^Enter generated \"([^\"]*)\" into \"([^\"]*)\" field for New Delivery Address$")
    public void enterGeneratedIntoFieldForNewDeliveryAddress(String keyPhone, String fieldName) throws IOException {
        userSteps.enterValueInProfileDetailsForDeliveryAddress((String) Serenity.getCurrentSession().get(keyPhone), fieldName);
    }

    @Then("^Remove address from Customer Profile$")
    public void removeAddressFromCustomerProfile(List<Address> address) {
        userSteps.removeAddressFromCustomerProfile(address.get(0));
    }

    @Then("^Customer Profile shouldn't contains removed address$")
    public void customerProfileShouldnTContainsRemovedAddress(List<Address> address) {
        userSteps.isCustomerProfileNotContainsAddress(address.get(0));
    }

    @When("^Set Address as default for Customer Profile$")
    public void setAddressAsDefaultForCustomerProfile(List<Address> address) {
        userSteps.setAddressAsDefaultForCustomerProfile(address.get(0));
    }

    @Then("^The first address in the addresses list of the Customer Profile should be$")
    public void theFirstAddressInTheAddressesListOfTheCustomerProfileShouldBe(List<Address> address) {
        userSteps.isFirstAddressInTheAddressesListOfTheCustomerProfile(address.get(0));
    }

    @When("^Click on Add new address button for adding Address to the Customer Profile$")
    public void clickOnAddNewAddressButtonForAddingAddressToTheCustomerProfile() {
        userSteps.clickOnAddNewAddressButtonForAddingAddressToTheCustomerProfile();
    }

    @When("^Click on Modify button for Customer Profile address$")
    public void clickOnModifyButtonForCustomerProfileAddress(List<Address> address) {
        userSteps.clickOnModifyButtonForCustomerProfileAddress(address.get(0));
    }

    @When("^Click on Save modified address button$")
    public void clickOnSaveModifiedAddressButton() {
        userSteps.clickOnSaveModifiedAddressButton();
    }

    @When("^Generate New Customer email \"([^\"]*)\"$")
    public void generateNewCustomerEmail(String keyEmail) {
        userSteps.generateNewCustomerEmail(keyEmail);
    }

    @When("^Enter generated \"([^\"]*)\" into \"([^\"]*)\" field in Profile details for Delivery Address$")
    public void enterGeneratedIntoFieldInProfileDetailsForDeliveryAddress(String keyEmail, String fieldName) throws IOException {
        userSteps.enterValueInProfileDetailsForDeliveryAddress((String) Serenity.getCurrentSession().get(keyEmail), fieldName);
    }

    @When("^Select option Create new User$")
    public void selectOptionCreateNewUser() {
        userSteps.selectOptionCreateNewUser();
    }

    @When("^Enter \"([^\"]*)\" into the Password and Confirmation fields for New User Profile$")
    public void enterIntoThePasswordAndConfirmationFieldsForNewUserProfile(String password) {
        userSteps.enterPasswordAndConfirmationForNewUserProfile(password);
    }

    @When("^Click on BPOST button$")
    public void clickOnBPOSTButton() {
        userSteps.clickOnBPOSTButton();
    }

    @When("^Select Payment Method \"([^\"]*)\"$")
    public void selectPaymentMethod(String paymentMethod) {
        userSteps.selectPaymentMethod(paymentMethod);
    }

    @When("^Enter an Order notes \"([^\"]*)\" for Guest Checkout$")
    public void enterAnOrderNotesForGuestCheckout(String notes) {
        userSteps.enterAnOrderNotesForGuestCheckout(notes);
    }

    @Then("^\"([^\"]*)\" section should be displayed with address in Order summary$")
    public void sectionShouldBeDisplayedWithAddressInOrderSummary(String sectionName, List<Address> address) throws IOException {
        userSteps.isSectionDisplayedWithAddressInOrderSummary(sectionName, address.get(0));
    }

    @Then("^\"([^\"]*)\" button should be displayed in Order summary$")
    public void buttonShouldBeDisplayedInOrderSummary(String buttonName) {
        userSteps.isButtonDisplayedInOrderSummary(buttonName);
    }

    @Then("^Delivery method \"([^\"]*)\" should be displayed in Ship To section$")
    public void deliveryMethodShouldBeDisplayedInShipToSection(String expectedDeliveryMethod) {
        userSteps.isDeliveryMethodDisplayedInShipToSection(expectedDeliveryMethod);
    }

    @When("^Select \"([^\"]*)\" step of the Checkout$")
    public void selectStepOfTheCheckout(String stepName) throws IOException {
        userSteps.selectStepOfTheCheckout(stepName);
    }

    @Given("^Clear Customer's shopping cart - email \"([^\"]*)\" password \"([^\"]*)\"$")
    public void clearCustomerSShoppingCartEmailPassword(String email, String password) throws InterruptedException, IOException {
        userSteps.clearCustomerShoppingCart(email, password);
    }

    @When("^Select \"([^\"]*)\" item in Main menu$")
    public void selectItemInMainMenu(String itemName) throws IOException {
        userSteps.selectItemInMainMenu(itemName);
    }

    @Then("^Mail \"([^\"]*)\" password \"([^\"]*)\" should contains order confirmation from \"([^\"]*)\" subject \"([^\"]*)\"$")
    public void emailNotificationForOrderCreationShouldBeSentTo(String email, String password, String sender, String subject) throws IOException, MessagingException, InterruptedException, GeneralSecurityException {
        userSteps.checkEmailNotificationOrderCreation(email, password, sender, subject);
    }

    @Then("^Order total in email Order conformation should be equal to created order$")
    public void orderTotalInEmailOrderConformationShouldBeEqualToCreatedOrder() throws MessagingException, SAXException, JDOMException, DocumentException, IOException {
        userSteps.isOrderTotalInEmailOrderConformationEqualToCreatedOrder();
    }

    @When("^Click on Terms and Conditions button on Checkout$")
    public void clickOnTermsAndConditionsButtonOnCheckout() {
        userSteps.clickOnTermsAndConditionsButtonOnCheckout();
    }

    @Then("^Terms and Conditions pop-up with the title \"([^\"]*)\" should be opened$")
    public void termsAndConditionsPopUpWithTheTitleShouldBeOpened(String title) throws IOException {
        userSteps.isTitleOfTermsAndConditionsPopUp(title);
    }

    @Then("^Terms and Conditions pop-up should contains Headings$")
    public void termsAndConditionsPopUpShouldContainsHeadings(List<String> subtitles) throws IOException {
        userSteps.isTermsAndConditionsPopUpContains(subtitles);

    }

    @When("^Click on Close button of the Terms and Conditions pop-up$")
    public void clickOnCloseButtonOfTheTermsAndConditionsPopUp() {
        userSteps.clickOnCloseButtonOfTheTermsAndConditionsPopUp();
    }

    @Then("^\"([^\"]*)\" page should be opened$")
    public void pageShouldBeOpened(String expectedUrl) {
        userSteps.isPageOpened(expectedUrl);
    }

    @When("^Select Update Password item from side menu of MyAccount$")
    public void selectUpdatePasswordItemFromSideMenuOfMyAccount() {
        userSteps.selectUpdatePasswordItemFromSideMenuOfMyAccount();
    }

    @When("^Enter \"([^\"]*)\" value into Current Password field on Updating Password form$")
    public void enterValueIntoCurrentPasswordFieldOnUpdatingPasswordForm(String password) {
        userSteps.enterValueIntoCurrentPasswordFieldOnUpdatingPasswordForm(password);
    }

    @When("^Enter \"([^\"]*)\" value into New Password field on Updating Password form$")
    public void enterValueIntoNewPasswordFieldOnUpdatingPasswordForm(String password) {
        userSteps.enterValueIntoNewPasswordFieldOnUpdatingPasswordForm(password);
    }

    @When("^Enter \"([^\"]*)\" value into Confirm New Password field on Updating Password form$")
    public void enterValueIntoConfirmNewPasswordFieldOnUpdatingPasswordForm(String newPassword) {
        userSteps.enterValueIntoConfirmNewPasswordFieldOnUpdatingPasswordForm(newPassword);
    }

    @When("^Click on Update Password button$")
    public void clickOnUpdatePasswordButton() {
        userSteps.clickOnUpdatePasswordButton();
    }

    @When("^On Products page click on Product number (\\d+) with statuses$")
    public void onProductsPageClickOnProductNumberWithStatuses(int productNumber, Map<String, Boolean> statuses) {
        userSteps.clickOnProductOnProductsPage(productNumber, statuses);
    }

    @Then("^Product details page should be opened with product$")
    public void productDetailsPageShouldBeOpenedWithProduct(List<Product> product) throws IOException {
        userSteps.isProductDetailsPageOpenedWith(product.get(0));
    }

    @When("^Select value \"([^\"]*)\" from Qty drop-down on Products details page$")
    public void selectValueFromQtyDropDownOnProductsDetailsPage(String qty) {
        userSteps.selectProductQtyOnProductsDetailsPage(qty);
    }

    @Then("^Click on Add to Cart button on Products details page$")
    public void clickOnAddToCartButtonOnProductsDetailsPage() throws IOException {
        userSteps.clickOnAddToCartButtonOnProductsDetailsPage();
    }

    @When("^Enter \"([^\"]*)\" into Search Pharmacies field of Product details page$")
    public void enterIntoSearchPharmaciesFieldOfProductDetailsPage(String text) {
        userSteps.enterValueIntoSearchPharmaciesFieldOnProductDetailsPage(text);
    }

    @When("^Click on Find Pharmacies button on Product details page$")
    public void clickOnFindPharmaciesButtonOnProductDetailsPage() {
        userSteps.clickOnFindPharmaciesButtonOnProductDetailsPage();
    }

    @Then("^First Pharmacy in Search results should contains \"([^\"]*)\" value in Address column$")
    public void firstPharmaciesAddressInSearchResultsContains(String value) {
        userSteps.isFirstPharmacieAddressInSearchResultsContains(value);
    }

    @Then("^Pharmacies in Search results should contains \"([^\"]*)\" value in Store column$")
    public void pharmaciesInSearchResultsShouldContainsValueInStoreColumn(String value) {
        userSteps.isPharmaciesInSearchResultsContainsStore(value);
    }

    @When("^Click on Global Search button in Header$")
    public void clickOnGlobalSearchButtonInHeader() throws Throwable {
        userSteps.clickOnGlobalSearchButton();
    }

    @When("^Enter \"([^\"]*)\" into Global search field$")
    public void enterIntoGlobalSearchField(String text) throws IOException {
        userSteps.enterTextIntoGlobalSearchField(text);
    }

    @Then("^The names of products in Autocomplete results should contains \"([^\"]*)\"$")
    public void theNamesOfProductsInAutocompleteResultsShouldContains(String text) throws IOException {
        userSteps.isProductsInAutocompleteResultsOfGlobalSearchContains(text);
    }

    @When("^Select \"([^\"]*)\" product from Autocomplete results of Global search field$")
    public void selectProductFromAutocompleteResultsOfGlobalSearchField(String productName) throws IOException {
        userSteps.selectProductFromAutocompleteResultsOfGlobalSearchField(productName);
    }

    @Then("^Product details Page should be opened with title \"([^\"]*)\"$")
    public void productDetailsPageShouldBeOpenedWithTitle(String expectedTitle) {
        userSteps.isTitleOfProductDetailsPage(expectedTitle);
    }

    @When("^Click on Show checkout Cart button$")
    public void clickOnShowCheckoutCartButton() {
        userSteps.clickOnShowCheckoutCartButton();
    }

    @Then("^Checkout Cart should contains \"([^\"]*)\" with Qty ([^\"]*)$")
    public void checkoutCartShouldContainsWithQty(String productName, Integer qty) throws IOException {
        userSteps.isCheckoutCartContains(productName, qty);
    }

    @Then("^The price of \"([^\"]*)\" product should be price from Autocomplete results$")
    public void thePriceOfProductShouldBePriceFromAutocompleteResults(String productName) throws IOException {
        userSteps.isPriceOnProductsPageEqualToPriceFromAutocompleteResults(productName);
    }

    @Then("^Discount price should be displayed for \"([^\"]*)\" product$")
    public void discountPriceShouldBeDisplayedForProduct(String productName) throws IOException {
        userSteps.isDiscountPriceDisplayedOnProductsPage(productName);
    }

    @Then("^Promotional text should be displayed for \"([^\"]*)\" product$")
    public void promotionalTextShouldBeDisplayedForProduct(String productName) throws IOException {
        userSteps.isPromotionalTextDisplayedOnProductsPage(productName);
    }

    @Then("^Badge discount should be displayed for \"([^\"]*)\" product$")
    public void badgeDiscountShouldBeDisplayedForProduct(String productName) throws IOException {
        userSteps.isBadgeDiscountDisplayedOnProductsPage(productName);
    }

    @When("^Open Product details page of \"([^\"]*)\" from Products page$")
    public void openProductDetailsPageOfFromProductsPage(String productName) throws IOException {
        userSteps.openProductDetailsPageFromProductsPage(productName);
    }

    @Then("^Discount price on Products details page should be equal to Discount price on Products page$")
    public void discountPriceOnProductsDetailsPageShouldBeEqualToDiscountPriceOnProductsPage() throws IOException {
        userSteps.isDiscountPriceOnProductsDetailsPageEqualToDiscountPriceOnProductsPage();
    }

    @Then("^Price on Products details page should be equal to Price on Products page$")
    public void priceOnProductsDetailsPageShouldBeEqualToPriceOnProductsPage() throws IOException {
        userSteps.isPriceOnProductsDetailsPageEqualToPriceOnProductsPage();
    }

    @Then("^Promotional text on Products details page should be equal to Price on Products page$")
    public void promotionalTextOnProductsDetailsPageShouldBeEqualToPriceOnProductsPage() throws IOException {
        userSteps.isPromotionalTextOnProductsDetailsPageEqualToPriceOnProductsPage();
    }

    @Then("^Badge discount should be displayed on Products details page for promotional product$")
    public void badgeDiscountShouldBeDisplayedOnProductsDetailsPageForPromotionalProduct() {
        userSteps.isBadgeDiscountDisplayedOnProductsDetailsPageForPromotionalProduct();
    }

    @When("^Select \"([^\"]*)\" category from header Products drop-downn$")
    public void selectCategoryFromHeaderProductsDropDownn(String category) throws IOException {
        userSteps.selectCategoryFromHeaderProductsDropDownn(category);
    }

    @Then("^Check that Shopping Cart contains products$")
    public void isShoppingCartContainsProduct(List<Product> products) throws IOException {
        userSteps.isShoppingCartContainsProducts(products);
    }

    @Then("^Check that Shopping Cart contains product$")
    public void isShoppingCartContainsProduct(Map<String, String> expProduct) throws IOException {
        Product product=new Product();
        product.setProductCode(expProduct.get("productCode"));
        product.setProductName(expProduct.get("productName"));
        product.setQty(Integer.valueOf(expProduct.get("qty")));
        product.setPrice(expProduct.get("price"));
        product.setDiscountPrice(expProduct.get("discountPrice"));
        product.setStatusNet(Boolean.valueOf(expProduct.get("statusNet")));
        product.setStatusApotek(Boolean.valueOf(expProduct.get("statusApotek")));
        product.setPromotionalText(expProduct.get("promotionalText"));

        userSteps.isShoppingCartContainsProduct(product);
    }

    @Then("^\"([^\"]*)\" of the Shopping Cart should be \"([^\"]*)\"$")
    public void ShoppingCartOrderSummaryShouldContains(String fieldName, String value) throws IOException {
        userSteps.isShoppingCartOrderSummaryDisplayedWith(fieldName, value);
    }

    @When("^Add product to Shopping Cart$")
    public void addProductToShoppingCart(List<Product> products) throws IOException, InterruptedException {
        userSteps.addProductToShoppingCart(products);
    }

    @Then("^Tips message \"([^\"]*)\" should be displayed in the Shopping Cart$")
    public void tipsMessageShouldBeDisplayedInTheShoppingCart(String message) throws IOException {
        userSteps.isTipsDisplayedInShoppingCart(message);
    }

    @Then("^\"([^\"]*)\" value of Checkout order summary should be ([^\"]*)$")
    public void valueOfCheckoutOrderSummaryShouldBe(String fieldName, Double value) throws IOException {
        userSteps.valueOfCheckoutOrderSummaryShouldBe(fieldName, value);
    }

    @Then("^Delivery cost of the order in the BPOST pop-up should be ([^\"]*)$")
    public void deliveryCostOfTheOrderInTheBPOSTPopUpShouldBe(Double value) {
        userSteps.isOrderDeliveryCostInBPOSTPopUp(value);
    }

    @When("^Remove product from the Cart$")
    public void removeProductFromTheCart(List<Product> products) throws IOException {
        userSteps.removeProductFromTheCart(products);
    }

    @When("^Expand bottom Checkout shopping cart$")
    public void expandBottomCheckoutShoppingCart() {
        userSteps.expandBottomCheckoutShoppingCart();
    }

    @Then("^\"([^\"]*)\" on the Checkout Order Summary should be \"([^\"]*)\"$")
    public void onTheCheckoutOrderSummaryShouldBe(String fieldName, String value) throws IOException {
        userSteps.isFieldOnCheckoutOrderSummaryDisplayedWithValue(fieldName, value);
    }

    @When("^Click on Password Recovery button$")
    public void clickOnPasswordRecoveryButton() {
        userSteps.clickOnPasswordRecoveryButton();
    }

    @When("^Enter \"([^\"]*)\" into restore password field$")
    public void enterIntoRestorePasswordField(String email) {
        userSteps.enterIntoRestorePasswordField(email);
    }

    @When("^Click on Send email button to restore password$")
    public void clickOnSendEmailButtonToRestorePassword() {
        userSteps.clickOnSendEmailButtonToRestorePassword();
    }

    @Then("^\"([^\"]*)\" message should be displayed in Restore password pop-up$")
    public void messageShouldBeDisplayedInRestorePasswordPopUp(String message) throws IOException {
        userSteps.isMessageDisplayedInRestorePasswordPopUp(message);
    }

    @Then("^Close Restore password pop-up$")
    public void closeRestorePasswordPopUp() {
        userSteps.closeRestorePasswordPopUp();
    }

    @Then("^Mail \"([^\"]*)\" password \"([^\"]*)\" should contains recovery letter from \"([^\"]*)\" subject \"([^\"]*)\"$")
    public void mailPasswordShouldContainsRecoveryLetterFromSubject(String email, String password, String emailFrom, String subject) throws GeneralSecurityException, IOException, MessagingException {
        userSteps.isRecoveryLetterSent(email, password, emailFrom, subject);
    }

    @Then("^Check that Update password link in email is opened Update password page with title \"([^\"]*)\"$")
    public void checkThatUpdatePasswordLinkInEmailIsOpenedUpdatePasswordPageWithTitle(String expectedPageTitle) throws MessagingException, SAXException, JDOMException, DocumentException, IOException {
    userSteps.checkEmailUpdatePasswordLink(expectedPageTitle);
    }

    @When("^Enter \"([^\"]*)\" into New password field$")
    public void enterIntoNewPasswordField(String newPassword) {
        userSteps.enterIntoNewPasswordField(newPassword);
    }

    @When("^Enter \"([^\"]*)\" into Confirmation password field$")
    public void enterIntoConfirmationPasswordField(String newPassword) {
      userSteps.enterIntoConfirmationPasswordField(newPassword);
    }

    @When("^Click on Submit Updated password button$")
    public void clickOnSubmitUpdatedPasswordButton(){
      userSteps.clickOnSubmitUpdatedPasswordButton();
    }

    @Then("^\"([^\"]*)\" column of Search results in Back Office should contains All created Orders Number$")
    public void columnOfSearchResultsInBackOfficeShouldContainsAllCreatedOrdersNumber(String columnName) throws IOException {
        userSteps.isColumnOfSearchResultsInBackOfficeContainsCreatedOrders(columnName);
    }

    @When("^Delete all messages from email \"([^\"]*)\" and password \"([^\"]*)\"$")
    public void deleteAllMessagesFromEmailAndPassword(String email, String password) throws MessagingException {
        userSteps.deleteAllMessages(email, password);
    }

    @Then("^Shopping cart should be displayed with the message \"([^\"]*)\"$")
    public void shoppingCartShouldBeDisplayedWithTheMessage(String message) throws Throwable {
       userSteps.isMessageDisplayedInShoppingCart(message);
    }


    @When("^Select \"([^\"]*)\" option of Filter by Price$")
    public void selectOptionOfFilterByPrice(String option) {
        userSteps.selectOptionOfFilterByPrice(option);
    }

    @Then("^\"([^\"]*)\" should be displayed in Applied facets section$")
    public void shouldBeDisplayedInAppliedFacetsSection(String option) {
        userSteps.isOptionDisplayedInAppliedFacetsSection(option);
    }

    @Then("^Products page should contains products with price within (\\d+) - (\\d+)$")
    public void productsPageShouldContainsProductsWithPriceWithin(int minPrice, int maxPrice){
        userSteps.isProductsPageContainsProductsPrice(minPrice, maxPrice);
    }


    @When("^Select payment method Visa card$")
    public void selectPaymentMethodVisaCard() {
       userSteps.selectPaymentMethodVisaCard();
    }

    @When("^Enter card verification code \"([^\"]*)\"$")
    public void enterCardVerificationCode(String code){
        userSteps.enterCardVerificationCode(code);
    }

    @Then("^Check Print picklist for each created Order with shipment method \"([^\"]*)\"$")
    public void checkPrintPicklistForEachCreatedOrderWithShipmentMethod(String shipmentMethod){
        userSteps.checkPrintPicklistForEachCreatedOrderWith(shipmentMethod);
    }

    @Then("^Check Shipping label for each created Order with shipment method \"([^\"]*)\"$")
    public void checkShippingLabelForEachCreatedOrderWith(String shipmentMethod) {
        userSteps.checkShippingLabelForEachCreatedOrderWith(shipmentMethod);
    }

    @Then("^Mail \"([^\"]*)\" password \"([^\"]*)\" should contains order confirmation from \"([^\"]*)\" with receiver \"([^\"]*)\"$")
    public void mailPasswordShouldContainsOrderConfirmationFromWithReceiver(String email, String password, String sender, String receiver) throws GeneralSecurityException, IOException, MessagingException {
        userSteps.checkEmailNotificationOrderCreationByReceiver(email, password, sender, receiver);
    }

    @Then("^Check Split Order for each created Order with shipment method \"([^\"]*)\"$")
    public void checkSplitOrderForEachCreatedOrderWithShipmentMethod(String shipmentMethod) {
        userSteps.checkSplitOrderForEachCreatedOrderWith(shipmentMethod);
    }

    @Then("^Check Pack Slip for each created Order with shipment method \"([^\"]*)\"$")
    public void checkPackSlipForEachCreatedOrderWithShipmentMethod(String shipmentMethod){
       userSteps.checkPackSlipForEachCreatedOrderWith(shipmentMethod);
    }

    @Given("^Save scenario number \"([^\"]*)\" to Order$")
    public void saveScenarioNumberToOrder(String scenarioNumber) {
       userSteps.saveScenarioNumberToOrder(scenarioNumber);
    }


    @Then("^Shopping Cart shouldn't contain products$")
    public void shoppingCartShouldnTContainProducts(List<Product> products){
        userSteps.isShoppingCartNotContainsProducts(products);
    }


    @When("^Open Product details page of product \"([^\"]*)\"$")
    public void openProductDetailsPageOfProduct(String productCode) throws IOException {
        userSteps.openProductDetailsPageOfProduct(productCode);
    }

    @When("^Click On Larger image button$")
    public void clickOnLargerImageButton() {
        userSteps.clickOnLargerImageButton();
    }

    @Then("^Large image of product with code \"([^\"]*)\" should be displayed$")
    public void largeImageOfShouldBeDisplayed(String productCode) {
      userSteps.isLargeImageDisplayedForProduct(productCode);
    }

    @Then("^Send to address option on Delivery method step should be displayed with the message \"([^\"]*)\"$")
    public void sendToAddressOptionOnDeliveryMethodStepShouldBeDisplayedWithTheMessage(String message) throws IOException {
        userSteps.isSendToAddressOptionOnDeliveryMethodStepDisplayedWith(message);
    }

    @Then("^Warning Pop-up should be displayed with the selected Unavailable products$")
    public void warningPopUpShouldBeDisplayedWithTheSelectedUnavailableProducts(List<Product> products) throws IOException {
        userSteps.isWarningPopUpContainsSelectedUnavailableProducts(products);
    }

    @When("^Click on Cancel button on the Warning popup$")
    public void clickOnCancelButtonOnTheWarningPopup() throws Throwable {
        userSteps.clickOnCancelButtonOnWarningPopup();
    }

    @When("^Click on Remove All button on the Warning popup$")
    public void clickOnRemoveAllButtonOnTheWarningPopup() throws Throwable {
        userSteps.clickOnRemoveAllButtonOntheWarningPopup();
    }

    @Then("^Cart count on the Header Cart button should be disappeared$")
    public void cartCountOnTheHeaderCartButtonShouldBeDisappeared() throws Throwable {
        userSteps.isNotDisplayedCartCountOnHeaderCart();
    }

    @Given("^Create Customer profile if not exists via REST API$")
    public void createCustomerProfileIfNotExists(List<CustomerProfile> profile){
       userSteps.createCustomerProfileIfNotExists(profile.get(0));
    }

    @When("^Select option sort by \"([^\"]*)\"$")
    public void selectOptionSortBy(String optionName) throws IOException {
       userSteps.selectOptionSortBy(optionName);
    }

    @Then("^Products should be sorted by \"([^\"]*)\"$")
    public void productsShouldBeSortedBy(String optionName) {
        userSteps.isProductsSortedBy(optionName);
    }

    @When("^Navigate to Products page number \"([^\"]*)\"$")
    public void navigateToProductsPageNumber(Integer pageNumber) {
        userSteps.navigateToProductsPage(pageNumber);
    }


    @Then("^Check that order notes is displayed$")
    public void checkThatOrderNotesIsDisplayed() {
       userSteps.isOrderNotesDisplayed();
    }

    @Then("^Order status of \"([^\"]*)\" in Order History should be \"([^\"]*)\"$")
    public void orderStatusOfInOrderHistoryShouldBe(String testID, String expectedOrderStatus) throws IOException {
      userSteps.isOrderStatusInOrderHistoryEqualTo(testID,expectedOrderStatus);
    }

    @Then("^My prescription form should be prepopulate with the values$")
    public void isMyPrescriptionFormPrepopulateWith(Map<String, String> fieldsValues){
       userSteps.isMyPrescriptionFormPrepopulateWith(fieldsValues);
    }

    @When("^Open Order Details page of created order$")
    public void openOrderDetailsPageOfCreatedOrder() {
      userSteps.openOrderDetailsPageOfCreatedOrder();
    }

    @Then("^Order Details page should be displayed with all the details of created order$")
    public void orderDetailsPageShouldBeDisplayedWithAllTheDetailsOfCreatedOrder(){
        userSteps.isOrderDetailsPageDisplayedWithAllTheDetailsOfCreatedOrder();
    }

    @Then("^\"([^\"]*)\" field for reminder should be \"([^\"]*)\"$")
    public void fieldForReminderShouldBe(String fieldName, String value) throws IOException {
        userSteps.isValueOfFieldForReminder(fieldName, value);
    }

    @Then("^Order Details page should be displayed with all the products of created order$")
    public void orderDetailsPageShouldBeDisplayedWithAllTheProductsOfCreatedOrder(){
        userSteps.isOrderDetailsPageDisplayedWithAllTheProductsOfCreatedOrder();
    }
}
