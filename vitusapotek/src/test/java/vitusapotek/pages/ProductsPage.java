package vitusapotek.pages;

import net.serenitybdd.core.annotations.findby.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import vitusapotek.models.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ProductsPage extends BasePage {
    private String PRODUCT_CONTAINER = ".//a[contains(@href,'/p/$ItemNumber')]/ancestor::article";

    public Integer getPagesOfPagination() {
        String PAGINATION_INFO = ".//li[@class='pagination-info']";
        String tmp;
        scrollIntoView(PAGINATION_INFO);
        tmp = $(PAGINATION_INFO).getText();
        tmp = tmp.substring(tmp.indexOf("-") + 1, tmp.indexOf(" sider"));
        return Integer.valueOf(tmp.trim());
    }

    public Product getProduct(String itemNumber) {
        Product product = new Product();
        String xpathProductContainer = PRODUCT_CONTAINER.replace("$ItemNumber", itemNumber);
        withTimeoutOf(5, TimeUnit.SECONDS).waitFor(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathProductContainer)));
        scrollIntoView(xpathProductContainer);
        product.setProductName(findBy(xpathProductContainer + "//a[@class='name']").getText());
        product.setItemNumber(itemNumber);
        if (findBy(xpathProductContainer + "//span[contains(text(),'nett')]").getAttribute("class").equals("stock-status in-stock") == true) {
            product.setStatusNet(true);
        } else {
            product.setStatusNet(false);
        }
        if (findBy(xpathProductContainer + "//span[contains(text(),'apotek')]").getAttribute("class").equals("stock-status in-stock") == true) {
            product.setStatusApotek(true);
        } else {
            product.setStatusApotek(false);
        }
        product.setListPrice(Double.valueOf(findBy(xpathProductContainer + "//div[@class='price-panel']/*[1]").getText()
                .replace(",", ".").replace("-", "00").replace(" ", "").trim()));
        try {
            product.setDiscountPrice(Double.valueOf(findBy(xpathProductContainer + "//div[@class='price-panel']/*[2]").getText()
                    .replace(",", ".").replace("-", "00").replace(" ", "").trim()));
            product.setPromotionalText(xpathProductContainer + "//strong[@class='product-promotional']");
        } catch (Exception e) {
        }
        return product;
    }

    public void addToCartProduct(String itemNumber) {
        String xpathProductContainer = PRODUCT_CONTAINER.replace("$ItemNumber", itemNumber);
        withTimeoutOf(5, TimeUnit.SECONDS).waitFor(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathProductContainer)));
        scrollIntoView(xpathProductContainer);
        moveTo(xpathProductContainer);
        $(xpathProductContainer + "//button[@type='submit']").click();
        waitABit(500);
    }

    public void clickOnProduct(String productName) {
        findBy(".//article[@class='product-item']//a[contains(text(),'" + productName + "')]").click();
    }

    public List<String> getBreadCrumbLinks() {
        List<String> breadcrambLinks = new ArrayList<>();
        for (int i = 1; i <= findAll(".//ol[@class='breadcrumb']/li/a").size(); i++) {
            breadcrambLinks.add(findBy("//ol[@class='breadcrumb']/li/a").getText());
        }
        breadcrambLinks.add(findBy("//ol[@class='breadcrumb']/li[@class='active']").getText());
        return breadcrambLinks;
    }

}
