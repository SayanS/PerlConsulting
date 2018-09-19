package lloydsPharmaProject.steps.serenity;

import lloydsPharmaProject.Models.Order;
import lloydsPharmaProject.backofficePages.BackOfficePages;
import lloydsPharmaProject.utils.Helpers;
import net.serenitybdd.core.Serenity;
import net.thucydides.core.annotations.Step;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Assert;
import us.codecraft.xsoup.Xsoup;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import java.io.IOException;
import java.util.Map;

public class BackOfficeSteps {
    private BackOfficePages backOfficePage;
    private Order currentOrder = new Order();

    @Step
    public void openLoginPage() {
        backOfficePage.openBackoffice();
    }

    @Step
    public void loginToBackOfficeUserAndPassword(String login, String password) {
        backOfficePage.login(login, password);
//        JSON MockUp of created Order
//        List<String> createdOrderListJson = new ArrayList<>();
//        Order order=new Order();
//        Gson gson = new Gson();
//        order.setOrderNumber("LPBE-1000023017");
//        order.setShipmentMethod("To Be Picked Up");
//        createdOrderListJson.add(gson.toJson(order));
//        order.setOrderNumber("LPBE-1000023013");
//        order.setShipmentMethod("To Be Picked Up");
//        createdOrderListJson.add(gson.toJson(order));
//        Serenity.getCurrentSession().put("CreatedOrderListJson",createdOrderListJson);
    }

    @Step
    public void SearchForMenuItem(String item) {
        backOfficePage.searchForMenuItem(item);
    }

    @Step
    public void selectTab(String tabName) {
        backOfficePage.selectTab(tabName);
    }

    @Step
    public void clickOnButton(String buttonName) {
        backOfficePage.clickOnButton(buttonName);
    }

    @Step
    public void getOrderFromPickingList() {
        backOfficePage.getOrderFromPickingList();
        Serenity.getCurrentSession().put("OrderPickingList", backOfficePage.getOrderFromPickingList());
    }

    @Step
    public void searchForOrderCreatedBy(String testID) {
        currentOrder = Helpers.getOrderFromCreatedOrderListJsonBy(testID);
        backOfficePage.enterIntoSearchField(currentOrder.getOrderNumber());
        backOfficePage.clickOnSearchButton();
    }

    @Step
    public void clickOnOrderCreatedBy() {
        backOfficePage.clickOnOrder(currentOrder.getOrderNumber());
    }

    @Step
    public void isPrintPicklistDocumentContainsOrderNumberFromTest() {
        Document document = Jsoup.parse(backOfficePage.getOrderFromPickingList());
        Assert.assertEquals("Mismatch order number. Expected - " + currentOrder.getOrderNumber(), currentOrder.getOrderNumber(),
                Xsoup.compile("//table[@class='packInfo']//tbody/tr[2]/td[2]").evaluate(document).getElements().get(0).ownText());
    }

    @Step
    public void isPackSlipDocumentContainsOrderNumberFromTest() {
        Document document = Jsoup.parse(backOfficePage.getOrderFromPickingList());
        Assert.assertEquals("Mismatch order number. Expected - " + currentOrder.getOrderNumber(), currentOrder.getOrderNumber(),
                Xsoup.compile("//table[@class='packInfo']//table/tbody/tr[4]/td[2]").evaluate(document).getElements().get(0).ownText());
    }

    @Step
    public void isShippingLabelDocumentContainsOrderNumberCreatedByTest() {
        Document document = Jsoup.parse(backOfficePage.getShippingLabel());
        Assert.assertTrue("No media for shipping label" + Xsoup.compile("//img").evaluate(document).getElements().get(0).attr("src"),
                Xsoup.compile("//img").evaluate(document).getElements().get(0).attr("src").contains("/medias/?context="));
    }

    @Step
    public void isSplitOrderFormContainsOrderNumberCreatedByTest() {
        Document document = Jsoup.parse(backOfficePage.getSplitOrder());
        backOfficePage.closePopUp();
        Assert.assertEquals("Mismatch order number. Expected - " + currentOrder.getOrderNumber(), currentOrder.getOrderNumber(),
                Xsoup.compile("//input[contains(@value,'CLPBE-')]").evaluate(document).getElements().get(0).attr("value").replace("_0", "").replace("C", ""));
    }

    @Step
    public void logout() {
        backOfficePage.clickOnLogoutButton();
    }

    @Step
    public void isOrderStatusEqualTo(String testID, String expectedStatus) {
        currentOrder = Helpers.getOrderFromCreatedOrderListJsonBy(testID);
        Assert.assertEquals(expectedStatus, backOfficePage.getOrderStatus(currentOrder.getOrderNumber()));
    }

    @Step
    public void closePrintPopup() {
        backOfficePage.closeLastWindow();
    }

    @Step
    public void clickOnButtonOnPopUp(String buttonName, String popupTitle) {
        backOfficePage.clickOnButtonOnPopUp(buttonName, popupTitle);
    }

    @Step
    public void isAlertMessageContainsText(String expectedText) {
        String alertMessage = backOfficePage.getAlertMessage();
        Assert.assertTrue(alertMessage + " isn't contains " + expectedText, alertMessage.contains(expectedText));
    }

    @Step
    public void isLetterContainsOrderNumber(String testID) throws IOException, MessagingException {
        String orderNumber = Helpers.getOrderFromCreatedOrderListJsonBy(testID).getOrderNumber();
        Message notifMsg;
        notifMsg = (Message) Serenity.getCurrentSession().get("emailMessage");
        Assert.assertTrue("Mismatch content", (((Multipart) notifMsg.getContent()).getBodyPart(0).getContent().toString()).contains(orderNumber));
    }

    @Step
    public void isDisplayedAppropriateActionButtons(Map<String, Boolean> buttonsVisibility) {
        Map<String, Boolean> expectedButtonsVisibility;
        expectedButtonsVisibility=buttonsVisibility;
        expectedButtonsVisibility.forEach((buttonName, expectedVisibility)
               ->Assert.assertEquals("Button " + buttonName, expectedVisibility,backOfficePage.isButtonDisplayed(buttonName)));
    }
}
