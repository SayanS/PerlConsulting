package lloydsPharmaProject.pages;

import net.serenitybdd.core.annotations.findby.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.concurrent.TimeUnit;

public class InjenicoPage extends BasePage{
    
    String CARD_NUMBER=".//input[@id='Ecom_Payment_Card_Number']";
    String EXPIRATION_DATE_MONTH_DROP_DOWN=".//select[@id='Ecom_Payment_Card_ExpDate_Month']";
    String EXPIRATION_DATE_YEAR_DROP_DOWN=".//select[@id='Ecom_Payment_Card_ExpDate_Year']";

    public void enterCardNumber(String cardNumber){
        $(CARD_NUMBER).sendKeys(cardNumber);
    }

    public void clickOnConfirmMyOrderButton(){
        moveTo(".//input[@id='submit3']").click();
    }

    public void selectMonthOfExpirationDate(String month){
        $(EXPIRATION_DATE_MONTH_DROP_DOWN).selectByValue(month);
    }

    public void selectYearOfExpirationDate(String year){
        $(EXPIRATION_DATE_YEAR_DROP_DOWN).selectByValue(year);
    }

    public void goThroughAdditionalSecurity(String password) {
        String ADDITIONAL_PASSWORD_FIELD = ".//form[@name='njnj']//table[@class='ncoltable1']//tr/td/input[@name='Login']";
        String GO_BUTTON=".//form[@name='njnj']//table[@class='ncoltable1']//tr/td/input[@id='btn_Continue']";
        String BACK_TO_PAYMENT=".//form[@name='njnj']//table[@class='ncoltable1']//tr/td/input[@id='btn_Back']";
        try {
            withTimeoutOf(10, TimeUnit.SECONDS).waitFor(ExpectedConditions.urlContains("ncol/test/Test_3D_ACS.asp"));
            withTimeoutOf(10, TimeUnit.SECONDS).waitFor(ExpectedConditions.visibilityOfElementLocated(By.xpath(ADDITIONAL_PASSWORD_FIELD)));
            $(ADDITIONAL_PASSWORD_FIELD).sendKeys(password);
            moveTo(GO_BUTTON).click();
            withTimeoutOf(10, TimeUnit.SECONDS).waitFor(ExpectedConditions.visibilityOfElementLocated(By.xpath(BACK_TO_PAYMENT)));
            moveTo(BACK_TO_PAYMENT).click();
            withTimeoutOf(10, TimeUnit.SECONDS).waitFor(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//*[@id='submit1']")));
            moveTo(".//*[@id='submit1']").click();
        }catch (Exception e){
        }
    }

    public void selectPaymentMethodVisaCard() {
        withTimeoutOf(30, TimeUnit.SECONDS).waitFor(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@name='VISA_brand']")));
        $("//input[@name='VISA_brand']").click();
    }

    public void enterCardVerificationCode(String code) {
        $(".//input[@id='Ecom_Payment_Card_Verification']").sendKeys(code);
    }
}
