package vitusapotek.pages;

import net.serenitybdd.core.annotations.findby.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.concurrent.TimeUnit;

public class NetAxeptPage extends BasePage{

    public void selectNetAxeptPaymentOption(String optionName) {
        String OPTION=".//form[@id='form1']//div[@id='contentPadding']//a[.='"+optionName+"']";
        scrollIntoView(OPTION);
        $(OPTION).click();
    }

    public void clickOnNextButtonOnNetAxeptPage() {
        try {
            withTimeoutOf(10, TimeUnit.SECONDS).waitFor(ExpectedConditions.elementToBeClickable(By.xpath(".//form[@id='form1']//input[@id='nextButton']")));
        }catch(Exception e){
        }
        moveTo(".//form[@id='form1']//input[@id='nextButton']").click();
    }

    public String getOrderNumber(){
        return $("//form[@id='form1']//span[@id='merchantInformation_orderNumber']").getText();
    }

    public String getOrderTotal(){
        String orderTotal=$(".//form[@id='form1']//span[@id='merchantInformation_amount']").getText();
        return orderTotal.replace("kr ","").replace(" (NOK)","").replace(" ","").replace(",", ".");
    }

    public void enterCardNumber(String cardNumber) {
        withTimeoutOf(5, TimeUnit.SECONDS).waitFor(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//input[@id='cardNumber']")));
        findBy(".//input[@id='cardNumber']").sendKeys(cardNumber);
    }

    public void enterCVV2(String value) {
        withTimeoutOf(5, TimeUnit.SECONDS).waitFor(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//input[@id='securityCode']")));
        findBy(".//input[@id='securityCode']").sendKeys(value);
    }

    public void selectExpirationDate(String month, String year) {
        findBy(".//select[@id='month']").selectByVisibleText(month);
        findBy(".//select[@id='year']").selectByVisibleText(year);
    }

    public void clickOnPayButton() {
        findBy(".//input[@id='okButton']").click();
    }
}
