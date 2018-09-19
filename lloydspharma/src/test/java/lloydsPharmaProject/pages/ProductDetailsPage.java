package lloydsPharmaProject.pages;

import lloydsPharmaProject.Locators;
import lloydsPharmaProject.Models.Product;
import net.serenitybdd.core.annotations.findby.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ProductDetailsPage extends BasePage {
    public boolean isBuyNowButtonClickable() {
        try {
            $(Locators.PRODUCT_DETAILS_PAGE_BUY_NOW_BUTTON).click();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Product getProduct(String currentLanguage) throws IOException {
        Product product = new Product();
        product.setProductName(findBy(".//h1").getText());
        product.setProductCode(findBy(".//*[contains(text(),'Code produit') or contains(text(),'Productcode')]/ancestor::tr/td[2]").getText());

        try{
            product.setPrice(findBy(".//span[@class='price-label pb-1']/following-sibling::span[2]").getText());
            product.setDiscountPrice(findBy(".//span[@class='price-label pb-1']/following-sibling::span[1]").getText());
            product.setPromotionalText(findBy(".//strong[@class='product-promotional']").getText());
        }catch(Exception e){
            product.setPrice(findBy(".//span[@class='price-label pb-1']/following-sibling::span[1]").getText());
        }

        if (findBy(".//div[@class='row product-details product-status']/strong[1]").getText()
                .contains(getTranslation("Available online", currentLanguage, DICTIONARY_PRODUCTS))) {
            product.setStatusNet(true);
        } else product.setStatusNet(false);
        product.setStatusApotek(true);
        product.setQty(Integer.valueOf(findBy(".//form[@id='addToCartForm']//input[@id='qty']").getValue()));

        return product;
    }

    public void selectProductQtyOnProductsDetailsPage(String qty) {
        withTimeoutOf(5, TimeUnit.SECONDS)
                .waitFor(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//form[@id='addToCartForm']//input[@id='qty']")));
        findBy(".//form[@id='addToCartForm']//input[@id='qty']").click();
        findBy(".//ul/li[@data-value='" + qty + "']").click();
    }

    public Product clickOnAddToCartButtonOnProductsDetailsPage(String currentLanguage) throws IOException {
        findBy(".//button[@id='addToCartButton']").click();
        return getProduct(currentLanguage);
    }

    public void enterTextIntoSearchPharmaciesField(String text) {
        String SEARCH_PHARMACIES_FIELD = ".//input[@id='locationForSearchProduct']";
        withTimeoutOf(5, TimeUnit.SECONDS).waitFor(ExpectedConditions.presenceOfElementLocated(By.xpath(".//label[@for='locationForSearchProduct']")));
        scrollIntoView(".//label[@for='locationForSearchProduct']");
        waitABit(1000);
        findBy(SEARCH_PHARMACIES_FIELD).click();
        waitABit(1000);
        findBy(SEARCH_PHARMACIES_FIELD).sendKeys(text);
    }

    public void clickOnFindPharmaciesButton() {
        moveTo(".//button[@id='productstore_location_search_button']");
        waitABit(1000);
        clickOnWebElement(".//button[@id='productstore_location_search_button']");
        int i = 0;
    }

    public List<String> getPharmacyAddressesSearchResults() {
        List<String> columnValues = new ArrayList<>();
        withTimeoutOf(10, TimeUnit.SECONDS).waitFor(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("(.//table[@class='product-in-store']/tbody/tr)[1]")));
        for (int i = 1; i <= findAll(".//table[@class='product-in-store']/tbody/tr/td[5]").size(); i++) {
            columnValues.add(findBy("(.//table[@class='product-in-store']/tbody/tr/td[5])[" + i + "]").getText());
        }
        return columnValues;
    }

    public List<String> getPharmacyStoreSearchResults() {
        List<String> columnValues = new ArrayList<>();
        withTimeoutOf(10, TimeUnit.SECONDS).waitFor(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("(.//table[@class='product-in-store']/tbody/tr)[1]")));
        for (int i = 1; i <= findAll(".//table[@class='product-in-store']/tbody/tr/td[2]").size(); i++) {
            columnValues.add(findBy("(.//table[@class='product-in-store']/tbody/tr/td[2])[" + i + "]").getText());
        }
        return columnValues;
    }

    public String getProductName() {
        withTimeoutOf(5, TimeUnit.SECONDS).waitFor(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//h1[@itemprop='name']")));
        return findBy(".//h1[@itemprop='name']").getText();
    }

    public boolean isBadgeDiscountDisplayed() {
        try {
            return findBy(".//span[@class='badge discount']").isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public String getLargerImageLink() {
        withTimeoutOf(5,TimeUnit.SECONDS).waitFor(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//img[@itemprop='image']")));
        return $(".//img[@itemprop='image']").getAttribute("src");
    }

    public void clickOnLargerImageButton() {
        withTimeoutOf(5,TimeUnit.SECONDS).waitFor(ExpectedConditions
                .visibilityOfElementLocated(By.xpath(Locators.PRODUCT_DETAILS_PAGE_LARGER_IMAGE_BUTTON)));
        $(Locators.PRODUCT_DETAILS_PAGE_LARGER_IMAGE_BUTTON).click();
    }


}
