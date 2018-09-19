package lloydsPharmaProject.backofficePages;

import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;
import lloydsPharmaProject.Models.Product;
import lloydsPharmaProject.pages.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class BackOfficePages extends BasePage {
    String USER_NAME = ".//*[@name='j_username']";
    String PASSWORD = ".//*[@name='j_password']";
    String LOGIN_BUTTON = ".//button[.='Login']";

    public void openBackoffice() {
        getDriver().navigate().to(System.getProperty("base.url") + "/backoffice/");
    }
//    public void openBackoffice() {
//        getDriver().navigate().to("https://qa.lloydspharma.be/backoffice/");
//    }

    public void login(String userName, String password) {
        withTimeoutOf(100, TimeUnit.SECONDS).waitFor(ExpectedConditions.visibilityOfElementLocated(By.xpath(USER_NAME)));
        $(USER_NAME).sendKeys(userName);
        $(PASSWORD).sendKeys(password);
        waitABit(500);
        $(LOGIN_BUTTON).click();
    }

    public void selectAuthorityGroup(String group) {
        $(".//div[contains(text(),'Warehouse Administrator Role')]/span").click();
        $(".//button[.='PROCEED']").click();
    }

    public void enterIntoSearchField(String value) {
        String SEARCH_FIELD = ".//input[@class='z-bandbox-input z-bandbox-rightedge']";
        findBy(SEARCH_FIELD).clear();
        waitABit(1000);
        clickOnWebElement(SEARCH_FIELD);
        waitABit(500);
        findBy(SEARCH_FIELD).sendKeys(value);
        waitABit(1000);
    }

    private void waitForInvisabilityLoaderSpinners() {
        try {
            waitABit(1000);
            withTimeoutOf(20, TimeUnit.SECONDS)
                    .waitFor(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[@class='z-loading-indicator']")));
            withTimeoutOf(60, TimeUnit.SECONDS).waitFor(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[@class='z-progressmeter']")));
            waitABit(1000);
        } catch (Exception e) {
        }
    }

    public void clickOnSearchButton() {
        String SEARCH_BUTTON = ".//button[@class='yw-textsearch-searchbutton y-btn-primary z-button']";
        waitForInvisabilityLoaderSpinners();
        moveTo(SEARCH_BUTTON);
        findBy(SEARCH_BUTTON).click();
        waitForInvisabilityLoaderSpinners();
    }

    private Integer getColumnNumber(String columnName) {
        String COLUMN_HEADERS = "(.//tbody/tr[@class='yw-listview-colhead z-listhead']/th/div)";
        withTimeoutOf(100, TimeUnit.SECONDS).waitFor(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath(COLUMN_HEADERS)));
        for (int i = 1; i <= findAll(COLUMN_HEADERS).size(); i++) {
            if ($(COLUMN_HEADERS + "[" + i + "]").getText().contains(columnName)) {
                return i;
            }
        }
        return 0;
    }

    public List<String> getSearchResults(String columnName) {
        List<String> columnValues = new ArrayList<>();
        String SEARCH_RESULT_COLUMN = "(.//tr[contains(@class,'yw-coll-browser-hyperlink z-listitem')]/td[$columnIndex])";
        SEARCH_RESULT_COLUMN = SEARCH_RESULT_COLUMN.replace("$columnIndex", getColumnNumber(columnName).toString());
        waitForInvisabilityLoaderSpinners();

        if (findAll(SEARCH_RESULT_COLUMN).size() != 0) {
            for (int i = 1; i <= findAll(SEARCH_RESULT_COLUMN).size(); i++) {
                columnValues.add($(SEARCH_RESULT_COLUMN + "[" + i + "]").getText());
            }
        } else columnValues.add("");
        return columnValues;
    }

    public void searchForMenuItem(String item) {
        waitForInvisabilityLoaderSpinners();
        findBy(".//input[@placeholder='Filter Tree entries']").clear();
        findBy(".//input[@placeholder='Filter Tree entries']").click();
        findBy(".//input[@placeholder='Filter Tree entries']").sendKeys(item);
        waitForInvisabilityLoaderSpinners();
        clickOnWebElement(".//div[contains(@class,'treeCell')]//span[.='" + item + "']");
        waitForInvisabilityLoaderSpinners();
    }

    public void selectTab(String tabName) {
        String TAB = "(.//ul[@class='z-tabs-content']//span[.='" + tabName + "'])[1]";
        $(TAB).click();
    }

    public Product getProduct(String itemNumber) {
        Product product = new Product();

        return product;
    }

    public void clickOnButton(String buttonName) {
        withTimeoutOf(20, TimeUnit.SECONDS).waitFor(ExpectedConditions.elementToBeClickable(By.xpath(".//span[.='" + buttonName + "']")));
        moveTo(".//span[.='" + buttonName + "']");
        clickOnWebElement(".//span[.='" + buttonName + "']");
        waitForInvisabilityLoaderSpinners();
    }

    public String convertPDF(String fileName) {
        PdfReader reader;
        String textFromPage = "";
        fileName = "./src/test/resources/data/OrderPreview.pdf";
        try {
            reader = new PdfReader(fileName);
            textFromPage = PdfTextExtractor.getTextFromPage(reader, 1);
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return textFromPage;
    }

    public void clickOnOrder(String orderNumber) {
//        withTimeoutOf(30,TimeUnit.SECONDS)
//                .waitFor(ExpectedConditions.elementToBeClickable(By.xpath("//span[.='"+orderNumber+"']/ancestor::tr[1]")));
//        try {
//            withTimeoutOf(5, TimeUnit.SECONDS)
//                    .waitFor(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@class='z-messagebox-button z-button']")));
//            findBy("//button[@class='z-messagebox-button z-button']").click();
//        } catch (Exception e) {
//        }
        waitABit(1000);
        $("//span[.='" + orderNumber + "']/ancestor::tr[1]").click();
    }

    public String getOrderFromPickingList() {
        String orderPreview = getLastOpenedPageSource();
        return orderPreview;
    }

    public String getShippingLabel() {
        String pageSource = getLastOpenedPageSource();
        return pageSource;
    }

    public String getPackSlip() {
        String orderPreview = getLastOpenedPageSource();
        return orderPreview;
    }

    public String getLastOpenedPageSource() {
        String parentHandle = getDriver().getWindowHandle();
        String pageSource = "";
        waitABit(10000);
        for (String winHandle : getDriver().getWindowHandles()) {
            pageSource = getDriver().switchTo().window(winHandle).getPageSource().toString();
        }
        if (getDriver().getWindowHandles().size() > 1) {
            getDriver().close();
            try {
                getDriver().switchTo().window(parentHandle);
            } catch (Exception e) {
            }
        }
        return pageSource;
    }

    public void closePopUp() {
        $(".//i[@class='z-icon-times']").click();
    }

    public Boolean isButtonDisplayed(String buttonName) {
        try {
            waitForInvisabilityLoaderSpinners();
            withTimeoutOf(3, TimeUnit.SECONDS).waitFor(ExpectedConditions
                    .visibilityOfElementLocated(By.xpath(".//div[@class='cng-action-text z-div']/span[.='" + buttonName + "']")));
        } catch (Exception e) {
        }
        return $(".//div[@class='cng-action-text z-div']/span[.='" + buttonName + "']").isDisplayed();
    }

    public String getSplitOrder() {
        String pageSource = getLastOpenedPageSource();
        return pageSource;
    }

    public void clickOnLogoutButton() {
        withTimeoutOf(10, TimeUnit.SECONDS).waitFor(ExpectedConditions.elementToBeClickable(By.xpath(".//img[contains(@src,'icon_system_logout.png')]")));
        findBy(".//img[contains(@src,'icon_system_logout.png')]").click();
    }

    public String getOrderStatus(String orderNumber) {
        String ORDER_STATUS = ".//span[contains(text(),'" + orderNumber + "')]/ancestor::tr/td[10]/div/span";
        withTimeoutOf(30, TimeUnit.SECONDS).waitFor(ExpectedConditions.visibilityOfElementLocated(By.xpath(ORDER_STATUS)));
        return findBy(ORDER_STATUS).getText();
    }

    public void closeLastWindow() {
        String parentHandle = getDriver().getWindowHandle();
        waitABit(10000);
        for (String winHandle : getDriver().getWindowHandles()) {
            getDriver().switchTo().window(winHandle);
        }
        if (getDriver().getWindowHandles().size() > 1) {
            getDriver().close();
            try {
                getDriver().switchTo().window(parentHandle);
            } catch (Exception e) {
            }
        }
    }

    public void clickOnButtonOnPopUp(String buttonName, String popupTitle) {
        String POPUP_BUTTON = ".//div[.='" + popupTitle + "']/following-sibling::div[@class='z-window-content']//button[.='" + buttonName + "']";
        withTimeoutOf(10, TimeUnit.SECONDS).waitFor(ExpectedConditions.visibilityOfElementLocated(By.xpath(POPUP_BUTTON)));
        clickOnWebElement(POPUP_BUTTON);
    }

    public String getAlertMessage() {
        String ALERT_MESSAGE = ".//div[contains(@class,'notification-message success z-div')]/div/span";
        String message;
        withTimeoutOf(30, TimeUnit.SECONDS).waitFor(ExpectedConditions
                .visibilityOfElementLocated(By.xpath(ALERT_MESSAGE)));
        message = findBy(ALERT_MESSAGE).getText();
        withTimeoutOf(30, TimeUnit.SECONDS).waitFor(ExpectedConditions
                .invisibilityOfElementLocated(By.xpath(ALERT_MESSAGE)));
        return message;
    }
}
