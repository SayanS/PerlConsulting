package lloydsPharmaProject.pages;


import lloydsPharmaProject.Models.Address;
import net.serenitybdd.core.pages.WebElementFacade;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class MyPrescriptionPage extends BasePage {
    private String BROWSE_BUTTON = ".//div[@class='buttons-container']";
    private String COMMUNICATION_METHOD_CHECKBOX = ".//input[@value='$Option']/ancestor::label[1]";
    private String FORM_FIELD = ".//*[@placeholder='$FieldName']";
    private String FORM_BUTTONS = ".//fieldset//button";
    private String SEARCH_RESULT_CONTAINER = ".//ul[@class='store-finder-navigation-list js-store-finder-navigation-list']";
    private String SEARCH_RESULTS_PHARMACY_CONTAINERS = "(.//li[@class='store-finder-navigation-list-entry'])";

    public void enterValueIntoTheField(String value, String fieldName, String language) throws IOException {
        String translatedFieldName = getTranslation(fieldName, language, DICTIONARY_MY_PRESCRIPTION);
        $(FORM_FIELD.replace("$FieldName", translatedFieldName)).sendKeys(value);
    }

    public String getFieldValue(String fieldName) {
        return $(FORM_FIELD.replace("$FieldName", fieldName)).getValue();
    }

    public void clickOnBrowseButton() {
        moveTo(BROWSE_BUTTON).click();
    }


    public void uploadPrescriptionScan(String filePath) throws AWTException {
        moveTo(BROWSE_BUTTON);
        upload(filePath).to(findBy(".//input[@id='prescriptionFile']"));
        //findBy(".//input[@id='prescriptionFile']").sendKeys(filePath);
//        for (String handle1 : getDriver().getWindowHandles()) {
//            getDriver().switchTo().window(handle1);
//        }
//        Robot robot = new Robot();
//        String keys = filePath;
//        for (char c : keys.toCharArray()) {
//            int keyCode = KeyEvent.getExtendedKeyCodeForChar(c);
//            robot.keyPress(keyCode);
//            robot.delay(100);
//            robot.keyRelease(keyCode);
//            robot.delay(100);
//        }
//        robot.keyPress(KeyEvent.VK_ENTER);
//        robot.keyRelease(KeyEvent.VK_ENTER);
    }

    public void selectCommunicationOption(String optionName) {
        withTimeoutOf(5, TimeUnit.SECONDS).waitFor(ExpectedConditions
                .visibilityOfElementLocated(By.xpath(COMMUNICATION_METHOD_CHECKBOX.replace("$Option", optionName))));
        $(COMMUNICATION_METHOD_CHECKBOX.replace("$Option", optionName)).click();
        waitABit(5000);
    }

    public String getUploadedPrescriptionFileName() {
        String FILE_NAME_CONTAINER = ".//span[@class='uploaded-name']";
        try {
            withTimeoutOf(10, TimeUnit.SECONDS).waitFor(ExpectedConditions
                    .visibilityOfElementLocated(By.xpath(FILE_NAME_CONTAINER)));
            return findBy(FILE_NAME_CONTAINER).getText();
        } catch (Exception e) {
            return null;
        }
    }

    public void clickOnYourPharmacyButton() {
        String FIND_PHARMACY_BUTTON = ".//fieldset//button[@type='button']";
        moveTo(FIND_PHARMACY_BUTTON).click();
    }

    public void enterValueIntoPharmacySearchField(String value) {
        String PHARMACY_SEARCH_FIELD = ".//form[@id='storeFinderForm']//input[@id='storelocator-query']";
        withTimeoutOf(10, TimeUnit.SECONDS)
                .waitFor(ExpectedConditions.visibilityOfElementLocated(By.xpath(PHARMACY_SEARCH_FIELD)));
        $(PHARMACY_SEARCH_FIELD).sendKeys(value);
    }

    public void clickOnFindPharmacyButton() {
        String FIND_PHARMACY_BUTTON = " .//form[@id='storeFinderForm']//button[@id='findStoreBtn']";
        withTimeoutOf(10, TimeUnit.SECONDS).waitFor(ExpectedConditions.visibilityOfElementLocated(By.xpath(FIND_PHARMACY_BUTTON)));
        moveTo(FIND_PHARMACY_BUTTON).click();
    }

    public Address choosePharmacyFromSearchResults(Integer index) {
        Address chosenPharmacy=new Address();
        moveTo(SEARCH_RESULT_CONTAINER);
        withTimeoutOf(10, TimeUnit.SECONDS).waitFor(ExpectedConditions
                .presenceOfAllElementsLocatedBy(By.xpath(SEARCH_RESULTS_PHARMACY_CONTAINERS)));
        for (int i = 1; i <= findAll(SEARCH_RESULTS_PHARMACY_CONTAINERS).size(); i++) {
            scrollIntoView(SEARCH_RESULTS_PHARMACY_CONTAINERS + "[" + i + "]");
            if (i == index) {
                withTimeoutOf(5, TimeUnit.SECONDS).waitFor(ExpectedConditions.visibilityOfElementLocated(By.xpath(SEARCH_RESULTS_PHARMACY_CONTAINERS + "[" + i + "]" + "//h1/a")));
                chosenPharmacy.setFirstName(moveTo(SEARCH_RESULTS_PHARMACY_CONTAINERS + "[" + i + "]" + "//h1/a").getText());
                chosenPharmacy.setAddress1($("("+SEARCH_RESULTS_PHARMACY_CONTAINERS + "[" + i + "]"+"/label/span/span)[2]").getText().split(", ")[0]);
                chosenPharmacy.setPostCode($("("+SEARCH_RESULTS_PHARMACY_CONTAINERS + "[" + i + "]"+"/label/span/span)[2]").getText().split(", ")[1].split(" ")[0]);
                chosenPharmacy.setCity($("("+SEARCH_RESULTS_PHARMACY_CONTAINERS + "[" + i + "]"+"/label/span/span)[2]").getText().split(", ")[1].split(" ")[1]);
                moveTo(SEARCH_RESULTS_PHARMACY_CONTAINERS + "[" + i + "]" + "//span[@class='choosePharmacy-btn']").click();
                return chosenPharmacy;
            }
        }
        return null;
    }

    public void closeFindYourPharmacyPopUp() {
        moveTo(".//button[@id='cboxClose']").click();
        withTimeoutOf(5, TimeUnit.SECONDS).waitFor(ExpectedConditions
                .invisibilityOfElementLocated(By.xpath(".//div[@id='cboxLoadedContent']")));
    }

    public void clickOnSendPrescriptionButton() {
        scrollIntoView(".//fieldset//button[@type='submit']");
        clickOnWebElement(".//fieldset//button[@type='submit']");
    }

    public String getChoosePharmacyButtonTextOfPharmacyNumber(Integer index) {
        String SEARCH_RESULTS_PHARMACY_CONTAINERS = "(.//li[@class='store-finder-navigation-list-entry'])";
        scrollIntoView("(" + SEARCH_RESULTS_PHARMACY_CONTAINERS + "[" + index + "]" + "//span[contains(@class,'choosePharmacy-btn')])[2]");
        return $("(" + SEARCH_RESULTS_PHARMACY_CONTAINERS + "[" + index + "]" + "//span[contains(@class,'choosePharmacy-btn')])[2]").getText();
    }

    public String getChosenPharmacyFromPrescriptionForm() {
        String CHOSEN_PHARMACY_NAME = ".//form[@id='ebookingForm']//span[@id='pharmacy-name']";
        return $(CHOSEN_PHARMACY_NAME).getText();
    }

    public List<String> getMessagesOrderPlaced() {
        String ALERT_MESSAGES = ".//div[@class='alert alert-success']/p/span";
        List<String> messages = new ArrayList<>();
        withTimeoutOf(10, TimeUnit.SECONDS).waitFor(ExpectedConditions.
                visibilityOfAllElementsLocatedBy(By.xpath(ALERT_MESSAGES)));
        for (WebElementFacade message : findAll(ALERT_MESSAGES)) {
            messages.add(message.getText());
        }
        return messages;
    }

    public String getOrderComment() {
        return $("(.//h4/following-sibling::div)[1]").getText();
    }

    public String getPickUpOrderPharmacy() {
        return $("(.//h4/following-sibling::div)[2]").getText();
    }

    public void clickOnDownloadPrescriptionButton() {
        moveTo(".//h4/following-sibling::a").click();
    }

    public void clickOnRemoveButtonForUploadedPrescription() {
        String REMOVE_UPLOADED_PRESCRIPTION_BUTTON = ".//a[@class='delete']";
        withTimeoutOf(5, TimeUnit.SECONDS).waitFor(ExpectedConditions
                .visibilityOfElementLocated(By.xpath(REMOVE_UPLOADED_PRESCRIPTION_BUTTON)));
        moveTo(REMOVE_UPLOADED_PRESCRIPTION_BUTTON).click();
    }

    public List<String> getAllPharmacyNamesFromSearchResults(){
        List<String> pharmacyNames=new ArrayList<>();
        withTimeoutOf(10, TimeUnit.SECONDS).waitFor(ExpectedConditions
                .presenceOfAllElementsLocatedBy(By.xpath(SEARCH_RESULTS_PHARMACY_CONTAINERS)));
        for(int i=1;i<=findAll(SEARCH_RESULTS_PHARMACY_CONTAINERS).size();i++){
            scrollIntoView(SEARCH_RESULTS_PHARMACY_CONTAINERS+ "[" + i + "]" + "//h1/a");
            pharmacyNames.add($(SEARCH_RESULTS_PHARMACY_CONTAINERS+ "[" + i + "]" + "//h1/a").getText());
        }
        return pharmacyNames;
    }

}
