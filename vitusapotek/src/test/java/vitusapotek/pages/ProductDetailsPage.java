package vitusapotek.pages;

import net.serenitybdd.core.annotations.findby.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import vitusapotek.models.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ProductDetailsPage extends BasePage {

    public Product getProduct() {
        Product product = new Product();
        product.setItemNumber(findBy("//td[strong[contains(text(),'Varenummer:')]]/following-sibling::td").getText());
        product.setProductName(findBy(".//h1").getText());
        product.setListPrice(Double.valueOf(findBy(".//form[@id='addToCartForm']//span[@class='price']").getText().replace(",", ".").replace("-", "00")));
        product.setStatusApotek(true);
        if (findBy(".//div[@class='row product-details product-status']/strong[1]").getText().contains("Tilgjengelig på nett")) {
            product.setStatusNet(true);
        } else product.setStatusNet(false);

        product.setQty(Integer.valueOf(findBy(".//form[@id='addToCartForm']//input[@id='qty']").getValue()));
        return product;
    }

    public void selectQty(Integer qty) {
        String QTY = ".//form[@id='addToCartForm']//span[@class='custom-select-trigger']";
        withTimeoutOf(5, TimeUnit.SECONDS).waitFor(ExpectedConditions.presenceOfElementLocated(By.xpath(QTY)));
        moveTo(QTY).click();
        clickOnJS("(.//form[@id='addToCartForm']//ul/li)[" + qty + "]");
    }

    public void clickOnAddToCartButton() {
        moveTo(".//form[@id='addToCartForm']//button[@id='addToCartButton']").click();
    }

    public void enterTextIntoSearchPharmaciesField(String text) {
        String SEARCH_PHARMACIES_FIELD = ".//input[@id='locationForSearchProduct']";
        withTimeoutOf(5, TimeUnit.SECONDS).waitFor(ExpectedConditions.presenceOfElementLocated(By.xpath("//h1")));
        scrollIntoView(".//label[@for='locationForSearchProduct']");
        findBy(SEARCH_PHARMACIES_FIELD).sendKeys(text);
    }

    public void clickOnFindPharmaciesButton() {
        moveTo(".//button[@id='productstore_location_search_button']").click();
    }

    public List<String> getPharmacySearchResults(String column) {
        String HEADER = "(.//table/thead/tr/th)";
        Integer columnIndex=0;
        List<String> columnValues=new ArrayList<>();
        withTimeoutOf(5,TimeUnit.SECONDS).waitFor(ExpectedConditions.visibilityOfElementLocated(By.xpath(HEADER+"[1]")));
        for(int i=1; i<=findAll(HEADER).size();i++){
            if(findBy(HEADER+"["+i+"]").getText().equals(column)){
                columnIndex=i;
                break;
            }
        }
      for(int i=1;i<=findAll(".//table/tbody/tr/td["+columnIndex+"]").size();i++){
        columnValues.add(findBy("(.//table/tbody/tr/td["+columnIndex+"])["+i+"]").getText());
      }
        return columnValues;
    }
}
