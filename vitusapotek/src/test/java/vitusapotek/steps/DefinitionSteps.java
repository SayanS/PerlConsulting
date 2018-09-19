package vitusapotek.steps;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import net.thucydides.core.annotations.Steps;
import org.dom4j.DocumentException;
import org.jdom.JDOMException;
import org.xml.sax.SAXException;
import vitusapotek.models.Address;
import vitusapotek.models.CustomerProfile;
import vitusapotek.models.Product;
import vitusapotek.steps.serenity.EndUserSteps;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;

public class DefinitionSteps {
    @Steps
    EndUserSteps userSteps;

    @When("^Select \"([^\"]*)\" item in Main menu$")
    public void selectItemInMainMenu(String itemName) {
        userSteps.selectItemInMainMenu(itemName);
    }

    @Given("^Home page is opened$")
    public void homePageIsOpened() {
        userSteps.openHomePage();
    }

    @When("^Click on Cart button in Header$")
    public void clickOnCartButtonInHeader() {
        userSteps.clickOnCartButtonInHeader();
    }

    @When("^Click on Checkout button in the Cart$")
    public void clickOnCheckoutButtonInTheCart() {
        userSteps.clickOnCheckoutButtonInTheCart();
    }

    @When("^Select Shipping method Send by email$")
    public void selectShippingMethodSendByEmail() {
        userSteps.selectShippingMethodSendByEmail();
    }

    @When("^Click on Skip to Shipping address button$")
    public void clickOnSkipToShippingAddressButton() {
        userSteps.clickOnSkipToShippingAddressButton();
    }

    @When("^Enter email \"([^\"]*)\" and password \"([^\"]*)\" for Getting address from profile$")
    public void enterEmailAndPasswordForGettingAddressFromProfile(String email, String password) {
        userSteps.enterCredentialsForGettingAddressFromProfile(email, password);
    }

    @When("^Click on Further to the shipping and payment button$")
    public void clickOnFurtherToTheShippingAndPaymentButton() {
        userSteps.clickOnFurtherToTheShippingAndPaymentButton();
    }

    @When("^Select \"([^\"]*)\" as Delivery address$")
    public void selectAsDeliveryAddress(String address) {
        userSteps.selectDeliveryAddress(address);
    }

    @When("^Select \"([^\"]*)\" value from Land field for New Delivery Address$")
    public void selectValueFromLandFieldForNewDeliveryAddress(String land) {
        userSteps.selectValueFromLandFieldForNewDeliveryAddress(land);
    }

    @When("^Enter \"([^\"]*)\" into \"([^\"]*)\" field for New Delivery Address$")
    public void enterIntoFieldForNewDeliveryAddress(String value, String fieldName) {
        userSteps.enterIntoFieldForNewDeliveryAddress(value, fieldName);
    }

    @When("^Click on Add new address button$")
    public void clickOnAddNewAddressButton() {
        userSteps.clickOnAddNewAddressButton();
    }

    @When("^Select Shipping method Get in Pharmacy$")
    public void selectShippingMethodGetInPharmacy() {
        userSteps.selectShippingMethodGetInPharmacy();
    }

    @When("^Select first pharmacy with status \"([^\"]*)\" for products in the Cart$")
    public void selectFirstPharmacyWithStatusForProductsInTheCart(String status) {
        userSteps.selectFirstPharmacyWithStatusForProductsInTheCart(status);
    }

    @Then("^Selected Pharmacy should be displayed in Select pharmacy section$")
    public void selectedPharmacyShouldBeDisplayedInSelectPharmacySection() {
        userSteps.isSelectedPharmacyDisplayedInSelectPharmacySection();
    }

    @When("^Enter \"([^\"]*)\" into the \"([^\"]*)\" field for reminder$")
    public void enterIntoTheFieldForReminder(String value, String fieldName) {
        userSteps.enterIntoTheFieldForReminder(value, fieldName);
    }

    @When("^Enter an Order notes \"([^\"]*)\"$")
    public void enterAnOrderNotes(String orderNote) {
        userSteps.enterAnOrderNotes(orderNote);
    }

    @When("^Click on Next button on Step 1 in case Get in pharmacy$")
    public void clickOnNextButtonOnStepInCaseGetInPharmacy() {
        userSteps.clickOnNextButtonOnStepInCaseGetInPharmacy();
    }

    @Then("^Order Number on Confirmation page should be equal to Order Number which had been displayed in Shopping Cart$")
    public void orderNumberOnConfirmationPageShouldBeEqualToOrderNumberWhichHadBeenDisplayedInShoppingCart() {
        userSteps.isOrderNumberOnConfirmationPageEqualToOrderNumberInShoppingCart();
    }

    @Then("^Delivery information on the Order Confirmation page should contain selected Pharmacy$")
    public void deliveryInformationOnTheOrderConfirmationPageShouldContainSelectedPharmacy() {
        userSteps.isDeliveryInformationOnTheOrderConfirmationPageContainSelectedPharmacy();
    }

    @Then("^Payment information on the Order Confirmation page should contain text \"([^\"]*)\"$")
    public void paymentInformationOnTheOrderConfirmationPageShouldContainText(String payymentInfo) {
        userSteps.isPaymentInformationOnTheOrderConfirmationPageContains(payymentInfo);
    }

    @Then("^All added to Cart products with appropriate Qty and Price should be in the Order$")
    public void allAddedToCartProductsWithAppropriateQtyAndPriceShouldBeInTheOrder() {
        userSteps.isAllAddedToCartProductsWithAppropriateQtyAndPriceInTheOrder();
    }

    @Then("^Order Total should be the same as in the Shopping Cart$")
    public void orderTotalShouldBeTheSameAsInTheShoppingCart() {
        userSteps.isOrderTotalTheSameAsInShoppingCart();
    }

    @Given("^Confirm using cookies$")
    public void confirmUsingCookies() {
        userSteps.confirmUsingCookies();
    }

    @When("^Select \"([^\"]*)\" as Delivery method for an Order$")
    public void selectDeliveryMethodForAnOrder(String deliveryMethod) {
        userSteps.selectDeliveryMethodForAnOrder(deliveryMethod);
    }

    @Then("^Delivery section should be displayed with address \"([^\"]*)\"$")
    public void deliverySectionShouldBeDisplayedWithAddress(String deliveryAddress) {
        userSteps.isAddressDisplayedInDeliverySection(deliveryAddress);
    }

    @When("^Select Payment method \"([^\"]*)\"$")
    public void selectPaymentMethod(String paymentMethod) {
        userSteps.selectPaymentMethod(paymentMethod);
    }

    @When("^Select Payment option \"([^\"]*)\"$")
    public void selectPaymentOption(String optionName) {
        userSteps.selectPaymentOption(optionName);
    }

    @When("^Click on \"([^\"]*)\" button on Step_3$")
    public void clickOnButtonOnStep3(String buttonName) {
        userSteps.clickOnButtonOnStep3(buttonName);
    }

    @When("^Select option \"([^\"]*)\" on Step2$")
    public void selectOptionOnStep2(String optionName) {
        userSteps.selectOptionOnStep2(optionName);
    }

    @When("^Enter \"([^\"]*)\" value into Password and Repeat password fields$")
    public void enterPasswordAndRepeatPassword(String value) {
        userSteps.enterPasswordAndRepeatPassword(value);
    }

    @When("^Generate phone number \"([^\"]*)\"$")
    public void generatePhoneNumber(String keyPhone) {
        userSteps.generatePhoneNumberkeyPhone(keyPhone);
    }

    @When("^Generate New Customer email \"([^\"]*)\"$")
    public void generateNewCustomerEmail(String keyEmail) {
        userSteps.generateNewCustomerEmail(keyEmail);
    }


    @When("^Enter generated \"([^\"]*)\" into \"([^\"]*)\" field for New Customer$")
    public void enterGeneratedIntoFieldForNewCustomer(String keyValue, String fieldName) {
        userSteps.enterGeneratedValueIntoFieldForNewCustomer(keyValue, fieldName);
    }

    @When("^Click on Add new address for Delivery$")
    public void clickOnAddNewAddressForDelivery() {
        userSteps.clickOnAddNewAddressForDelivery();
    }

    @When("^Select option Save as new address$")
    public void selectOptionSaveAsNewAddress() {
        userSteps.selectOptionSaveAsNewAddress();
    }

    @When("^Click on Add Address button$")
    public void clickOnAddAddressButton() {
        userSteps.clickOnAddAddressButton();
    }

    @When("^Click on Login button on Order Confirmation page$")
    public void clickOnLoginButtonOnOrderConfirmationPage() {
        userSteps.clickOnLoginButtonOnOrderConfirmationPage();
    }

    @Then("^Alert message \"([^\"]*)\" should be displayed$")
    public void alertMessageShouldBeDisplayed(String message) {
        userSteps.isAlertMessageDisplayed(message);
    }

    @When("^Click on \"([^\"]*)\" button for Order drugs online$")
    public void clickOnButtonForOrderDrugsOnline(String buttonName) {
        userSteps.clickOnButtonForOrderDrugsOnline(buttonName);
    }

    @When("^Enter new Customer's \"([^\"]*)\" into \"([^\"]*)\" field$")
    public void enterNewCustomerSIntoField(String value, String fieldName) {
        userSteps.enterNewCustomerIntoField(value, fieldName);
    }

    @Then("^Error message \"([^\"]*)\" should be displayed for Customer profile$")
    public void errorMessageShouldBeDisplayedForCustomerprofile(String errorMessage) {
        userSteps.isErrorMessageDisplayedForCustomerProfile(errorMessage);
    }

    @Then("^Update Profile button shouldn't be displayed in Header menu$")
    public void updateProfileButtonShouldnTBeDisplayedInHeaderMenu() {
        userSteps.isUpdateProfileButtonNotDisplayed();
    }

    @When("^Click on New User/Login button$")
    public void clickOnNewUserLoginButton() {
        userSteps.clickOnNewUserLoginButton();
    }

    @When("^Click on Create Account button$")
    public void clickOnCreateAccountButton() {
        userSteps.clickOnCreateAccountButton();
    }

    @Then("^The value \"([^\"]*)\" should be displayed in the Poststed field$")
    public void theValueShouldBeDisplayedInThePoststedField(String value) {
        userSteps.isValueDisplayedInThePoststedField(value);
    }


    @When("^Fill the registration form with data$")
    public void fillTheRegistrationFormWithData(List<CustomerProfile> customerProfile) {
        userSteps.fillTheRegistrationFormWithData(customerProfile.get(0));
    }

    @When("^Select membership groups$")
    public void selectMembershipGroups(List<String> groups) {
        userSteps.selectMembershipGroups(groups);
    }

    @When("^Enter Favorite Pharmacies$")
    public void enterFavoritePharmacies(List<String> pharmacies) {
        userSteps.enterFavoritePharmacies(pharmacies);
    }

    @Then("^Update Profile button \"([^\"]*)\" should be displayed in Header menu$")
    public void updateProfileButtonShouldBeDisplayedInHeaderMenu(String caption) {
        userSteps.isUpdateProfileButtonDisplayedWith(caption);
    }

    @When("^Click on Update Profile button in Header menu$")
    public void clickOnUpdateProfileButtonInHeaderMenu() {
        userSteps.clickOnUpdateProfileButtonInHeaderMenu();
    }

    @When("^Select \"([^\"]*)\" item from Profile details$")
    public void selectItemFromProfileDetails(String itemName) {
        userSteps.selectItemFromProfileDetails(itemName);
    }

    @Then("^Din informasjon section of the Profile should be opened with registration data$")
    public void dinInformasjonSectionOfTheProfileShouldBeOpenedWithRegistrationData(List<CustomerProfile> personalInfo) {
        userSteps.isPersonalInformationDisplayedInProfile(personalInfo.get(0));
    }

    @Then("^Personal details of the Profile should be displayed with selected Membership groups$")
    public void personalDetailsOfTheProfileShouldBeDisplayedWithSelectedMembershipGroups(List<String> groups) {
        userSteps.isProfilePersonalDetailsDisplayedWithSelectedMembershipGroups(groups);
    }

    @Then("^Personal details of the Profile should be displayed with selected Pharmacies$")
    public void personalDetailsOfTheProfileShouldBeDisplayedWithSelectedPharmacies(List<String> pharmacies) {
        userSteps.isPersonalDetailsDisplayedWithSelectedPharmacies(pharmacies);
    }

    @Then("^The value of \"([^\"]*)\" field in Update email address section should be \"([^\"]*)\"$")
    public void theValueOfFieldInUpdateEmailAddressSectionShouldBe(String fieldName, String value) {
        userSteps.isUpdateEmailAddressSectionContains(fieldName, value);
    }

    @Then("^First Address in the Address book should be$")
    public void isFirstAddressinAddressBook(List<Address> address) {
        userSteps.isFirstAddressinAddressBook(address.get(0));
    }

    @And("^Click on Logout button in Header menu$")
    public void clickOnLogoutButtonInHeaderMenu() {
        userSteps.clickOnLogoutButtonInHeaderMenu();
    }

    @Then("^Mail \"([^\"]*)\" password \"([^\"]*)\" should contains order confirmation from \"([^\"]*)\" subject \"([^\"]*)\"$")
    public void emailNotificationForOrderCreationShouldBeSentTo(String email, String password, String sender, String subject) throws IOException, MessagingException, InterruptedException {
        userSteps.checkEmailNotificationOrderCreation(email, password, sender, subject);
    }

    @Then("^Letter should contains text \"([^\"]*)\"$")
    public void letterShouldContainsText(String expectedText) throws IOException, MessagingException {
        userSteps.isLetterContainsText(expectedText);
    }

    @And("^Sign In with email \"([^\"]*)\" and password \"([^\"]*)\"$")
    public void signInWithEmailAndPassword(String email, String password) {
        userSteps.signInWithEmailAndPassword(email, password);
    }

    @Then("^Logout button should be displayed in Header menu$")
    public void logoutButtonShouldBeDisplayedInHeaderMenu() {
        userSteps.isLogoutButtonDisplayedInHeaderMenu();
    }

    @Then("^Update Profile button should be displayed in Header menu$")
    public void updateProfileButtonShouldBeDisplayedInHeaderMenu() {
        userSteps.isUpdateProfileButtonDisplayedInHeaderMenu();
    }

    @When("^Update Personal details of the Profile with new ones$")
    public void updatePersonalDetailsOfTheProfileWithNewOnes(List<CustomerProfile> newPersonalDetails) {
        userSteps.updateProfilePersonalDetails(newPersonalDetails.get(0));
    }

    @When("^Click on Save button for Updated Personal details of the profile$")
    public void clickOnSaveButtonForUpdatedPersonalDetailsOfTheProfile() {
        userSteps.clickOnSaveButtonProfilePersonalDetails();
    }

    @When("^Update Profile email to \"([^\"]*)\"$")
    public void updateProfileEmailTo(String keyEmail) {
        userSteps.updateProfileEmailTo(keyEmail);
    }

    @When("^Enter \"([^\"]*)\" value into Password field for Updating email$")
    public void enterValueIntoPasswordFieldForUpdatingEmail(String password) {
        userSteps.enterPasswordForUpdatingEmail(password);
    }

    @When("^Click on Change Email button for Updating email$")
    public void clickOnChangeEmailButtonForUpdatingEmail() {
        userSteps.clickOnChangeEmailButtonForUpdatingEmail();
    }

    @And("^Login with new Updated Email \"([^\"]*)\" and password \"([^\"]*)\"$")
    public void loginWithNewUpdatedEmail(String keyEmail, String password) {
        userSteps.loginWithNewUpdatedEmail(keyEmail, password);
    }

    @When("^Click on Add new address button for adding Address to the Customer Profile$")
    public void clickOnAddNewAddressButtonForAddingAddressToTheCustomerProfile() {
        userSteps.clickOnAddNewAddressButtonForCustomerProfile();
    }

    @When("^Click on Modify button for Customer Profile address$")
    public void clickOnModifyButtonForCustomerProfileAddress(List<Address> address) {
        userSteps.clickOnModifyButtonForCustomerProfileAddress(address.get(0));
    }

    @When("^Click on Save modified address button$")
    public void clickOnSaveModifiedAddressButton() {
        userSteps.clickOnSaveModifiedAddressButton();
    }

    @Then("^Customer Profile shouldn't contain removed address$")
    public void customerProfileShouldnTContainRemovedAddress(List<Address> address) {
        userSteps.isCustomerProfileNotContainRemovedAddress(address.get(0));
    }

    @Then("^Address book should contain address$")
    public void addressBookShouldContainAddress(List<Address> address) {
        userSteps.isAddressBookContainAddress(address.get(0));
    }

    @When("^Click on Remove button for Customer Profile address$")
    public void clickOnRemoveButtonForCustomerProfileAddress(List<Address> address) {
        userSteps.clickOnRemoveButtonForCustomerProfileAddress(address.get(0));
    }

    @When("^Click on \"([^\"]*)\" button on the \"([^\"]*)\" pop-up$")
    public void clickOnButtonOnThePopUp(String buttonName, String popupName) {
        userSteps.clickOnButtonOnThePopUp(buttonName, popupName);
    }

    @When("^Click on Set as default button for Customer Profile address$")
    public void clickOnSetAsDefaultButtonForCustomerProfileAddress(List<Address> address) {
        userSteps.clickOnSetAsDefaultButtonForCustomerProfileAddress(address.get(0));
    }

    @When("^Enter \"([^\"]*)\" value into \"([^\"]*)\" field on Updating Passsword form$")
    public void enterValueIntoFieldOnUpdatingPassswordForm(String value, String fieldName) {
        userSteps.enterValueIntoFieldOnUpdatingPassswordForm(value, fieldName);
    }

    @When("^Click on \"([^\"]*)\" to Change Customer password$")
    public void clickOnToChangeCustomerPassword(String buttonName) {
        userSteps.clickOnToChangeCustomerPassword(buttonName);
    }

    @Then("^\"([^\"]*)\" page should be opened$")
    public void pageShouldBeOpened(String expectedUrl) {
        userSteps.isPageOpened(expectedUrl);
    }

    @When("^Select \"([^\"]*)\" from NetAxept Payment options$")
    public void selectFromNetAxeptPaymentOptions(String optionName) {
        userSteps.selectNetAxeptPaymentOption(optionName);
    }

    @When("^Click on Next button on the NetAxept page$")
    public void clickOnNextButtonOnNetAxeptPage() {
        userSteps.clickOnNextButtonOnNetAxeptPage();
    }

    @When("^Enter card number \"([^\"]*)\" on the NetAxept page$")
    public void enterCardNumberOnTheNetAxeptPage(String cardNumber) {
        userSteps.enterCardNumberOnNetAxeptPage(cardNumber);
    }

    @When("^Enter \"([^\"]*)\" value into the CVV2 field on the NetAxept page$")
    public void enterValueIntoTheCVV2FieldOnTheNetAxeptPage(String value) {
      userSteps.enterValueIntoCVV2OnTheNetAxeptPage(value);
    }

    @When("^Select \"([^\"]*)\" month and \"([^\"]*)\" year for Expiration date on the NetAxept page$")
    public void selectMonthAndYearForExpirationDateOnTheNetAxeptPage(String month, String year) {
       userSteps.selectMonthAndYearForExpirationDateOnTheNetAxeptPage(month, year);
    }

    @When("^Click on Pay button on the NetAxept page$")
    public void clickOnPayButtonOnTheNetAxeptPage() {
       userSteps.clickOnPayButtonOnTheNetAxeptPage();
    }

    @Then("^Order Number on NetAxept page should be the same as in the Shopping Cart$")
    public void orderNumberOnNetAxeptPageShouldBeTheSameAsInTheShoppingCart(){
       userSteps.isOrderNumberOnNetAxeptPageEqualToNumberInShoppingCart();
    }

    @Then("^Order Total on NetAxept page should be the sum of Shopping Cart Total and Price Delivery$")
    public void orderTotalOnNetAxeptPageShouldBeTheSumOfShoppingCartTotalAndPriceDelivery() {
        userSteps.isOrderTotalOnNetAxeptPageEqualToSumShoppingCartTotalAndPriceDelivery();
    }

    @Then("^Shopping Cart should contain all added products$")
    public void shoppingCartShouldContainAllAddedProducts() {
       userSteps.isShoppingCartContainsProducts();
    }

//    @When("^Open Product details page of product number (\\d+) with statuses$")
//    public void openProductDetailsPageOfProductNumberWithStatusAvailable(int productNumber, Map<String,String> statuses) {
//        userSteps.openProductDetailsPageOfProductWithStatuses(productNumber, statuses);
//    }

    @When("^On Product details page select qty \"([^\"]*)\"$")
    public void onProductDetailsPageSelectQty(Integer qty){
        userSteps.onProductDetailsPageSelectQty(qty);
    }

    @When("^On Product details page click on Add to cart button$")
    public void onProductDetailsPageClickOnAddToCartButton(){
        userSteps.clickOnAddToCartButtonOnProductDetailsPage();
    }

    @Then("^Delivery information on the Order Confirmation page should be address$")
    public void deliveryInformationOnTheOrderConfirmationPageShouldBeAddress(List<Address> expectedAddress) {
        userSteps.isDeliveryInformationOnOrderConfirmationPage(expectedAddress.get(0));
    }

    @Then("^Payment information on the Order Confirmation page should be address$")
    public void paymentInformationOnTheOrderConfirmationPageShouldBeAddress(List<Address> expectedAddress) {
        userSteps.isPaymentInformationOnOrderConfirmationPage(expectedAddress.get(0));
    }

    @Then("^Order Total on the Order Confirmation page should be sum of the Cart Order total and Shipping price$")
    public void orderTotalOnTheOrderConfirmationPageShouldBeSumOfTheCartOrderTotalAndShippingPrice() {
       userSteps.isCorrectOrderTotalOnOrderConfirmationPage();
    }

    @Then("^Created order should be in the Order History list$")
    public void createdOrderShouldBeInTheOrderHistoryList()  {
      userSteps.isOrderHistoryListContainsCreatedOrder();
    }

    @When("^Move to Cart button in Header$")
    public void moveToCartButtonInHeader() {
       userSteps.moveToCartButtonInHeader();
    }

    @Then("^Mini shopping cart should contain added to Cart products$")
    public void miniShoppingCartShouldContainAddedToCartProducts(){
       userSteps.isMiniShoppingCartContainAddedToCartProducts();
    }

    @When("^Move to \"([^\"]*)\" item in Main menu$")
    public void moveToItemInMainMenu(String itemName) {
      userSteps.moveToItemInMainMenu(itemName);
    }

    @When("^Select product category \"([^\"]*)\" and subcategory \"([^\"]*)\"$")
    public void selectProductCategoryAndSubcategory(String category, String subCategory) throws IOException {
       userSteps.selectProductCategoryAndSubcategory(category, subCategory);
    }

    @Then("^The title of Products page should contain \"([^\"]*)\"$")
    public void theTitleOfProductsPageShouldContain(String title){
      userSteps.isTitleOfProductsPageContains(title);
    }

    @Then("^The last breadcrumb link should contain \"([^\"]*)\"$")
    public void theLastBreadcrumbLinkShouldContain(String text) {
       userSteps.isLastBreadcrumbLinkContain(text);
    }

    @Then("^Send by email option on Delivery method step should be displayed with the message \"([^\"]*)\"$")
    public void sendByEmailOptionOnDeliveryMethodStepShouldBeDisplayedWithTheMessage(String message){
       userSteps.isSendByEmailOptionOnDeliveryMethodStepDisplayedWith(message);
    }

    @When("^Click on Payment link Nets$")
    public void clickOnPaymentLinkNets(){
      userSteps.clickOnPaymentLinkNets();
    }

    @When("^Click on Create Your Account button$")
    public void clickOnCreateYourAccountButton()  {
      userSteps.clickOnCreateYourAccountButton();
    }

    @When("^Delete all products from the Shopping Cart$")
    public void deleteAllProductsFromTheShoppingCart() {
       userSteps.deleteAllProductsFromTheShoppingCart();
    }

    @Then("^\"([^\"]*)\" message should be displayed in the Shopping Cart$")
    public void messageShouldBeDisplayedInTheShoppingCart(String message)  {
      userSteps.isMessageDisplayedInTheShoppingCart(message);
    }

    @Then("^Cart count on the Header Cart button should be disappeared$")
    public void cartCountOnTheHeaderCartButtonShouldBeDisappeared() {
      userSteps.isNotDisplayedCartCountOnHeaderCart();
    }

    @Then("^Click on \"([^\"]*)\" button should open \"([^\"]*)\" page$")
    public void clickOnButtonShouldOpenPage(String buttonName, String expectedUrl){
     userSteps.isClickOnButtonOpenURL(buttonName, expectedUrl);
    }

    @Given("^Clear Customer's shopping cart - email \"([^\"]*)\" password \"([^\"]*)\"$")
    public void clearCustomerSShoppingCartEmailPassword(String email, String password)  {
       userSteps.clearCustomerShoppingCart(email, password);
    }

    @Then("^Warning Pop-up should be displayed with the selected Unavailable products$")
    public void warningPopUpShouldBeDisplayedWithTheSelectedUnavailableProducts(List<Product> products) {
       userSteps.isWarningPopUpContainsSelectedUnavailableProducts(products);
    }

    @When("^Click on Remove All button on the Warning popup$")
    public void clickOnRemoveAllButtonOnTheWarningPopup(){
      userSteps.clickOnRemoveAllButtonOntheWarningPopup();
    }

    @When("^Click on Cancel button on the Warning popup$")
    public void clickOnCancelButtonOnTheWarningPopup() {
     userSteps.clickOnCancelButtonOnWarningPopup();
    }

    @When("^Click on Search button on the Header$")
    public void clickOnSearchButtonOnTheHeader(){
       userSteps.clickOnHeaderSearchButton();
    }

    @When("^Enter \"([^\"]*)\" value into Header Search field$")
    public void enterValueIntoHeaderSearchField(String searchText) {
        userSteps.enterTextIntoHeaderSearchField(searchText);
    }

    @When("^Enter \"([^\"]*)\" value into Personal identification code field for Verifying age$")
    public void enterValueIntoPersonalIdentificationCodeFieldForVerifyingAge(String code) {
       userSteps.enterPersonalCodeToVerifyAge(code);
    }

    @Then("^Product Counter on the Shopping Cart button should be \"([^\"]*)\"$")
    public void isShoppingCartCounterEqualTo(String expectedValue) {
        userSteps.isShoppingCartCounterEqualTo(expectedValue);
    }

    @When("^Click on Terms and Conditions button on Checkout$")
    public void clickOnTermsAndConditionsButtonOnCheckout(){
     userSteps.clickOnTermsAndConditionsButtonOnCheckout();
    }

    @Then("^Terms and Conditions pop-up with the title \"([^\"]*)\" should be opened$")
    public void termsAndConditionsPopUpWithTheTitleShouldBeOpened(String title){
       userSteps.isTitleOfTermsAndConditionsPopUp(title);
    }

    @Then("^Terms and Conditions pop-up should contains Headings$")
    public void termsAndConditionsPopUpShouldContainsHeadings(List<String> subtitles) {
        userSteps.isTermsAndConditionsPopUpContains(subtitles);

    }

    @When("^Click on Close button of the Terms and Conditions pop-up$")
    public void clickOnCloseButtonOfTheTermsAndConditionsPopUp(){
       userSteps.clickOnCloseButtonOfTheTermsAndConditionsPopUp();
    }

    @When("^Navigate to previous page$")
    public void navigateToPreviousPage(){
      userSteps.navigateToPreviousPage();
    }

    @When("^Click on Show Shopping Cart drop-down$")
    public void clickOnShowShoppingCartDropDown() {
     userSteps.clickOnShowShoppingCartDropDown();
    }

    @When("^Enter \"([^\"]*)\" into Search Pharmacies field of Product details page$")
    public void enterIntoSearchPharmaciesFieldOfProductDetailsPage(String text) {
        userSteps.enterValueIntoSearchPharmaciesFieldOnProductDetailsPage(text);
    }

    @When("^Click on Find Pharmacies button on Product details page$")
    public void clickOnFindPharmaciesButtonOnProductDetailsPage(){
        userSteps.clickOnFindPharmaciesButtonOnProductDetailsPage();
    }

    @Then("^First Pharmacy in Search results should contains \"([^\"]*)\" value in \"([^\"]*)\" column$")
    public void firstPharmacyInSearchResultsShouldContainsValueInColumn(String value, String column)  {
        userSteps.isFirstPharmacyInSearchResultsContains(value, column);
    }

    @Then("^Pharmacies in Search results should contains \"([^\"]*)\" value in \"([^\"]*)\" column$")
    public void pharmaciesInSearchResultsShouldContainsValueInColumn(String value, String column) {
        userSteps.isPharmaciesInSearchResultsContains(value, column);
    }

    @Then("^Order total in email Order conformation should be equal to created order$")
    public void orderTotalInEmailOrderConformationShouldBeEqualToCreatedOrder() throws MessagingException, SAXException, JDOMException, DocumentException, IOException {
       userSteps.isOrderTotalInEmailOrderConformationEqualToCreatedOrder();
    }

    @When("^Select Delivery address option Get Address from profile$")
    public void selectShippingMethodGetAddressFromProfile() {
      userSteps.selectDeliveryAddressGetAddressFromProfile();
    }


    @When("^Select Delivery address option Continue without profile$")
    public void selectDeliveryAddressContinueWithoutProfile()  {
       userSteps.selectDeliveryAddressContinueWithoutProfile();
    }

    @When("^Click on Delete button on Delete Address pop-up$")
    public void clickOnDeleteButtonOnDeleteAddressPopUp()  {
       userSteps.clickOnDeleteButtonOnDeleteAddressPopUp();
    }

    @When("^Click on Save order button$")
    public void clickOnSaveOrderButton(){
       userSteps.clickOnSaveOrderButton();
    }

    @When("^Add Products name to e-prescription order$")
    public void addToEPrescriptionOrder(List<String> products) {
        userSteps.addToEPrescriptionOrder(products);
    }

    @When("^Enter Additional Information \"([^\"]*)\" for e-prescription order$")
    public void enterAdditionalInformationForEPrescriptionOrder(String info){
      userSteps.enterAdditionalInfoForEPrescriptionOrder(info);
    }

    @Then("^Medicine list should be displayed with products$")
    public void medicineListShouldBeDisplayedWithProducts(List<String> productsName){
       userSteps.isMedicineListOfEprescriptionOrderContains(productsName);
    }

    @When("^Enter Customer information \"([^\"]*)\" into \"([^\"]*)\" field$")
    public void enterCustomerInformationIntoField(String value, String fieldName) {
       userSteps.enterCustomerInformationIntoField(value, fieldName);
    }

    @When("^Select Pharmacy region \"([^\"]*)\" for e-prescription order$")
    public void selectPharmacyRegionForEPrescriptionOrder(String regionName){
       userSteps.selectPharmacyRegionForEPrescriptionOrder(regionName);
    }

    @When("^Select Pharmacy \"([^\"]*)\" for e-prescription order$")
    public void selectPharmacyForEPrescriptionOrder(String pharmacyName) {
        userSteps.selectPharmacyForEPrescriptionOrder(pharmacyName);
    }

    @When("^Click on Send e-prescription button$")
    public void clickOnSendEPrescriptionButton(){
       userSteps.clickOnSendEPrescriptionButton();
    }

    @Then("^E-prescription confirmation page should be opened with title \"([^\"]*)\"$")
    public void ePrescriptionConfirmationPageShouldBeOpenedWithTitle(String expectedTitle) {
       userSteps.isTitleOfEPrescriptionConfirmationPage(expectedTitle);
    }

    @Then("^E-prescription confirmation page should contain all added products$")
    public void ePrescriptionConfirmationPageShouldContainAllAddedProducts(List<String> productsList){
       userSteps.isEprescriptionConfirmationPageContains(productsList);
    }

    @Then("^\"([^\"]*)\" field of the Customer information should be \"([^\"]*)\"$")
    public void fieldOfTheCustomerInformationShouldBe(String fieldName, String value){
        userSteps.isCustomerInformationFieldContains(fieldName, value);
    }

    @Then("^Additional Comment on E-prescription confirmation page should be \"([^\"]*)\"$")
    public void additionalCommentOnEPrescriptionConfirmationPageShouldBe(String expectedComment)  {
        userSteps.isAdditionalCommentOnEPrescriptionConfirmation(expectedComment);
    }

    @Then("^Retrieved by Pharmacy on E-prescription confirmation page should be \"([^\"]*)\"$")
    public void retrievedByPharmacyOnEPrescriptionConfirmationPageShouldBe(String expectedPharmacy){
       userSteps.isRetrievedByPharmacyOnEPrescriptionConfirmationPage(expectedPharmacy);
    }

    @When("^Uncheck option I want to be VI Customer$")
    public void uncheckOptionIWantToBeVICustomer() {
        userSteps.uncheckOptionIWantToBeVICustomer();
    }

    @Then("^Option I want to be VI Customer shouldn't be checked$")
    public void optionIWantToBeVICustomerShouldnTBeChecked() {
        userSteps.isNotSelectedOptionIWantToBeVICustomer();
    }

    @Then("^Title of Product Line in Cart should contains appropriate Promotional text$")
    public void productLineTitleInCartShouldContainsAppropriatePromotionalText(){
       userSteps.isTitleOfProductLineInCartContainsAppropriatePromotionalText();
    }

    @When("^Enter \"([^\"]*)\" value into \"([^\"]*)\" field for My prescription$")
    public void enterValueIntoFieldForMyPrescription(String value, String fieldName){
       userSteps.enterValueIntoFieldForMyPrescription(value,fieldName);
    }

    @When("^Click on OK button for My prescription login form$")
    public void clickOnOKButtonForMyPrescriptionLoginForm() {
       userSteps.clickOnOKButtonForMyPrescriptionLoginForm();
    }

    @Then("^My prescriptions page should be opened for customer \"([^\"]*)\"$")
    public void myPrescriptionsPageShouldBeOpenedForCustomer(String customerSocialNumber) {
        userSteps.isCustomerMyPrescriptionsPageOpened(customerSocialNumber);
    }

    @When("^Click on Logout button on My prescriptions page$")
    public void clickOnLogoutButtonOnMyPrescriptionsPage() {
       userSteps.clickOnLogoutButtonOnMyPrescriptionsPage();
    }

    @When("^Enter \"([^\"]*)\" value into Reseptpose-ID field for My prescription$")
    public void enterValueIntoReseptposeIDFieldForMyPrescription(String value){
        userSteps.enterReseptposeIDOfMyPrescription(value);
    }

    @When("^Add product to Shopping Cart$")
    public void addProductToShoppingCart(List<Product> products) throws IOException, InterruptedException {
        userSteps.addProductToShoppingCart(products);
    }

    @When("^Global Search for product$")
    public void globalSearchForProduct(List<Product> products) {
       userSteps.globalSearchForProduct(products.get(0));
    }

    @When("^Click on Product name \"([^\"]*)\"$")
    public void clickOnProductName(String productName){
       userSteps.clickOnProductName(productName);
    }

    @Then("^Product details page should be be opened with$")
    public void productDetailsPageShouldBeBeOpenedWith(List<Product> products) {
       userSteps.isProductDetailsPageOpenedWith(products.get(0));
    }

    @When("^Global Search for \"([^\"]*)\"$")
    public void globalSearchFor(String value) {
       userSteps.globalSearchFor(value);
    }

    @Then("^Search results should be opened with products$")
    public void searchResultsShouldBeOpenedWithProducts(List<Product> products){
       userSteps.searchResultsShouldBeOpenedWithProducts(products);
    }

    @Then("^Check value of \"([^\"]*)\" field in Order Overview section$")
    public void checkValueOfFieldInOrderOverviewSection(String fieldName) {
        userSteps.checkValueInOrderOverviewSection(fieldName);
    }

    @When("^Select \"([^\"]*)\" form \"([^\"]*)\" item of Main menu$")
    public void selectFormItemOfMainMenu(String option, String menuItem){
       userSteps.selectFromMainMenu(menuItem, option);
    }

    @Given("^Create Customer profile if not exists via REST API$")
    public void createCustomerProfileIfNotExists(List<CustomerProfile> profile){
        userSteps.createCustomerProfileIfNotExists(profile.get(0));
    }
}
