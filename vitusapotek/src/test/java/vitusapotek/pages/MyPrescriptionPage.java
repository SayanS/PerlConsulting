package vitusapotek.pages;

import net.serenitybdd.core.annotations.findby.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.concurrent.TimeUnit;

public class MyPrescriptionPage extends BasePage {

    public void clickOnButtonForOrderDrugsOnline(String buttonName) {
        withTimeoutOf(5, TimeUnit.SECONDS).waitFor(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//div/a[.='" + buttonName + "']")));
        moveTo(".//div/a[.='" + buttonName + "']").click();
    }

    public void enterEPrescriptionProduct(String productName) {
        withTimeoutOf(5, TimeUnit.SECONDS).waitFor(ExpectedConditions.presenceOfElementLocated(By.xpath(".//textarea[@id='medicine']")));
        scrollIntoView("(.//legend)[1]");
        findBy(".//textarea[@id='medicine']").sendKeys(productName);

    }

    public void clickOnAddProductToEprescription() {
        scrollIntoView("(.//legend)[1]");
        moveTo(".//button[@ng-click='vm.addMedicine()']").click();
    }

    public void enterAdditionalInfoForEPrescriptionOrder(String info) {
        scrollIntoView(".//span[.='Tilleggsinformasjon']");
        findBy(".//textarea[@id='orderText']").sendKeys(info);
    }

    public String[] getMedicineListOfEprescriptionOrder() {
        String[] medecineListProducts = new String[findAll(".//table[@id='medicineList']/tbody/tr/td[1]").size()];
        for (int i = 0; i < medecineListProducts.length; i++) {
            medecineListProducts[i] = findBy("(.//table[@id='medicineList']/tbody/tr/td[1])[" + (i + 1) + "]").getText();
        }
        return medecineListProducts;
    }

    public void enterCustomerInformationIntoField(String value, String fieldName) {
        scrollIntoView("(.//legend)[2]");
        findBy(".//span[.='" + fieldName + "']/ancestor::div[@class='form-group js-fieldfocus']//input").sendKeys(value);
    }


    public void selectPharmacyRegionForEPrescriptionOrder(String regionName) {
        scrollIntoView("(.//legend)[3]");
        findBy(".//select[@id='area']").selectByVisibleText(regionName);
    }

    public void selectPharmacyForEPrescriptionOrder(String pharmacyName) {
        scrollIntoView("(.//legend)[3]");
        findBy(".//select[@id='pharmacy']").selectByVisibleText(pharmacyName);
    }

    public void clickOnSendEPrescriptionButton() {
        scrollIntoView("(.//legend)[3]");
        findBy(".//button[@id='psBookingBtnSendOrder']").click();
    }

    public String getTitleOfEPrescriptionConfirmationPage() {
        withTimeoutOf(30, TimeUnit.SECONDS).waitFor(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//div[@class='alert alert-success ng-scope']")));
        return findBy(".//h1").getText();
    }

    public String[] getProductsFromEprescriptionConfirmationPage() {
        String[] productsList = new String[findAll(".//table[@id='medicineList']/tbody/tr/td[1]").size()];
        for (int i = 0; i < productsList.length; i++) {
            productsList[i] = findBy("(.//table[@id='medicineList']/tbody/tr/td[1])[" + (i + 1) + "]").getText();
        }
        return productsList;
    }

    public String getValueOfCustomerInformationField(String fieldName) {
        scrollIntoView("(.//legend)[1]");
        return findBy(".//span[.='" + fieldName + "']/ancestor::div[@class='form-group js-fieldfocus']//input").getValue();
    }

    public String getAdditionalCommentFromEPrescriptionConfirmation() {
        scrollIntoView("(.//div[@id='vm.orderDetails']/h4)[2]");
        return findBy("((.//*[@id='vm.orderDetails']/h4)[2]/following-sibling::div/span)[1]").getText();
    }

    public String getPharmacyFromEPrescriptionConfirmation() {
        scrollIntoView("(.//div[@id='vm.orderDetails']/h4)[2]");
        return findBy("((.//*[@id='vm.orderDetails']/h4)[2]/following-sibling::div/span)[2]").getText();
    }

    public void enterValueIntoFieldForMyPrescription(String value, String fieldName) {
        withTimeoutOf(5, TimeUnit.SECONDS).waitFor(ExpectedConditions.presenceOfElementLocated(By.xpath(".//span[.='" + fieldName + "']/ancestor::label/following-sibling::input")));
        findBy(".//span[.='" + fieldName + "']/ancestor::label/following-sibling::input").click();
        findBy(".//span[.='" + fieldName + "']/ancestor::label/following-sibling::input").sendKeys(value);
    }

    public void clickOnOKButtonForMyPrescriptionLoginForm() {
        moveTo(".//button[@ng-click='vm.authenticateClick()']").click();
    }

    public String getCustomerSocialNumberFromMyPrescriptionsPage() {
        withTimeoutOf(30, TimeUnit.SECONDS).waitFor(ExpectedConditions.presenceOfElementLocated(By.xpath("(.//div[@class='ps-list-title']/span)[1]")));
        return findBy("(.//div[@class='ps-list-title']/span)[1]").getText();
    }

    public void clickOnLogoutButtonOnMyPrescriptionsPage() {
        findBy(".//*[@id='logOutBtn']").click();
    }

    public void enterReseptposeIDOfMyPrescription(String value) {
        findBy(".//input[@id='cartTrackId']").sendKeys(value);
    }
}
