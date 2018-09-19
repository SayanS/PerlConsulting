package lloydsPharmaProject.steps;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import lloydsPharmaProject.steps.serenity.BackOfficeSteps;
import net.thucydides.core.annotations.Step;
import net.thucydides.core.annotations.Steps;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.Map;

public class DefinitionStepsBackOffice {

    @Steps
    BackOfficeSteps backOfficeSteps;

    @Step
    @Given("^Back Office Login page is opened$")
    public void backOfficeLoginPageIsOpened() {
        backOfficeSteps.openLoginPage();
    }

    @When("^Login to Back Office user \"([^\"]*)\" and password \"([^\"]*)\"$")
    public void loginToBackOfficeUserAndPassword(String login, String password) {
        backOfficeSteps.loginToBackOfficeUserAndPassword(login, password);
    }

    @Then("^In Back Office search for menu item \"([^\"]*)\"$")
    public void inBackOfficeSearchForMenuItem(String item) {
        backOfficeSteps.SearchForMenuItem(item);
    }

    @Then("^Select \"([^\"]*)\" tab$")
    public void selectTab(String tabName) {
        backOfficeSteps.selectTab(tabName);
    }

    @When("^Click on \"([^\"]*)\" button in Back Office$")
    public void clickOnButton(String buttonName) {
        backOfficeSteps.clickOnButton(buttonName);
    }

    @When("^Get Order from Picking list$")
    public void getOrderFromPickingList() {
        backOfficeSteps.getOrderFromPickingList();
    }

//    @Then("Order number in Picking list should the same as in Search results$")
//    public void inPickingListShouldTheSameAsInSearchResults() {
//        backOfficeSteps.isOrderNumberInPickingListEqualToSearchResultsOrder();
//    }

    @When("^Search for Order created by \"([^\"]*)\"$")
    public void searchForOrderCreatedBy(String testID) {
        backOfficeSteps.searchForOrderCreatedBy(testID);
    }

    @When("^Click on Order in Search Results$")
    public void clickOnOrderInSearchResults() {
        backOfficeSteps.clickOnOrderCreatedBy();
    }

    @Then("^Print picklist document should contains Order Number created by Test$")
    public void printPicklistDocumentShouldContainsOrderNumberCreatedBy() {
        backOfficeSteps.isPrintPicklistDocumentContainsOrderNumberFromTest();
    }

//    @Then("^\"([^\"]*)\" button should be displayed$")
//    public void buttonShouldBeDisplayed(String buttonName) {
//        backOfficeSteps.isButtonDisplayed(buttonName);
//    }

    @Then("^Pack Slip document should contains Order Number created by Test$")
    public void packSlipDocumentShouldContainsOrderNumberCreatedByTest(){
       backOfficeSteps.isPackSlipDocumentContainsOrderNumberFromTest();
    }

    @Then("^Shipping label document should contains Order Number created by Test$")
    public void shippingLabelDocumentShouldContainsOrderNumberCreatedByTest(){
        backOfficeSteps.isShippingLabelDocumentContainsOrderNumberCreatedByTest();
    }

    @Then("^Split Order form should contains Order Number created by Test$")
    public void splitOrderFormShouldContainsOrderNumberCreatedByTest() {
        backOfficeSteps.isSplitOrderFormContainsOrderNumberCreatedByTest();
    }

    @When("^Back Office Logout$")
    public void backOfficeLogout() throws Throwable {
        backOfficeSteps.logout();
    }

    @Then("^Status of \"([^\"]*)\" order in Back Office should be \"([^\"]*)\"$")
    public void statusOfOrderInBackOfficeShouldBe(String testID, String expectedStatus) {
        backOfficeSteps.isOrderStatusEqualTo(testID,expectedStatus);
    }

    @And("^Close Print popup in Back Office$")
    public void closePrintPopupInBackOffice(){
        backOfficeSteps.closePrintPopup();
    }

    @When("^Click on \"([^\"]*)\" button on \"([^\"]*)\" pop-up$")
    public void clickOnButtonOnPopUp(String buttonName, String popupTitle) {
    backOfficeSteps.clickOnButtonOnPopUp(buttonName, popupTitle);
    }

    @Then("^Alert message should contains text \"([^\"]*)\"$")
    public void alertMessageShouldContainsText(String text) {
        backOfficeSteps.isAlertMessageContainsText(text);
    }

    @Then("^Letter should contains order number \"([^\"]*)\"$")
    public void letterShouldContainsOrderNumber(String testID) throws IOException, MessagingException {
        backOfficeSteps.isLetterContainsOrderNumber(testID);
    }

    @Then("^In Back Office appropriate Action buttons should be displayed$")
    public void inBackOfficeAppropriateActionButtonsShouldBeDisplayed(Map<String,Boolean> buttonsVisability) {
      backOfficeSteps.isDisplayedAppropriateActionButtons(buttonsVisability);
    }
}
