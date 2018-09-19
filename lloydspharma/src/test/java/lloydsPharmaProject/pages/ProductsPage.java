package lloydsPharmaProject.pages;

import lloydsPharmaProject.Models.Product;
import net.serenitybdd.core.annotations.findby.By;
import net.serenitybdd.core.pages.WebElementFacade;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class ProductsPage extends BasePage {
    private String PRODUCT_CONTAINER = ".//article[@class='product-item']";

    public Integer getPagesOfPagination() {
        String PAGINATION_INFO = ".//li[@class='pagination-info']";
        String tmp;
        scrollIntoView(PAGINATION_INFO, -50);
        tmp = $(PAGINATION_INFO).getText();
        tmp = tmp.split(" ")[3];
        return Integer.valueOf(tmp.trim());
    }

    public void clickOnNextPage() {
        withTimeoutOf(5, TimeUnit.SECONDS).waitFor(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//h1")));
        scrollIntoView(".//a[@rel='next']", -50);
        findBy(".//a[@rel='next']").click();
    }

    public void navigateToProductsPage(Integer pageNumber) {
        for(int i=1;i<=pageNumber;i++){
            clickOnNextPage();
        }
    }

    public Double getProductPrice(Integer productIndex) {
        withTimeoutOf(10, TimeUnit.SECONDS).waitFor(ExpectedConditions.visibilityOfElementLocated(By.xpath(PRODUCT_CONTAINER + "[" + productIndex + "]//a[@class='name']")));
        scrollIntoView(PRODUCT_CONTAINER + "[" + productIndex + "]//a[@class='name']", -50);
        return  Double.valueOf(findBy(PRODUCT_CONTAINER + "[" + productIndex + "]//div[@class='price-panel']/*[1]").getText()
                        .replace(",", ".").replace("-", "00").replace("€", "").replace(" ", "").trim());
    }

    public String getProductName(Integer productIndex) {
        withTimeoutOf(10, TimeUnit.SECONDS).waitFor(ExpectedConditions.visibilityOfElementLocated(By.xpath(PRODUCT_CONTAINER + "[" + productIndex + "]//a[@class='name']")));
        scrollIntoView(PRODUCT_CONTAINER + "[" + productIndex + "]//a[@class='name']", -50);
        return findBy(PRODUCT_CONTAINER + "[" + productIndex + "]//a[@class='name']").getText();
    }

    public Product getProduct(String productName) {
        String PRODUCT_CONTAINER = ".//a[@title='" + productName + "']/ancestor::article[@class='product-item']";
        Product product = new Product();
        withTimeoutOf(10, TimeUnit.SECONDS).waitFor(ExpectedConditions.visibilityOfElementLocated(By.xpath(PRODUCT_CONTAINER + "//a[@class='name']")));
        scrollIntoView(PRODUCT_CONTAINER);
        product.setProductName(findBy(PRODUCT_CONTAINER + "//a[@class='name']").getText());
        if (findBy(PRODUCT_CONTAINER + "//h2/following-sibling::span[1]").getAttribute("class").equals("stock-status in-stock")) {
            product.setStatusNet(true);
        } else {
            product.setStatusNet(false);
        }
        if (findBy(PRODUCT_CONTAINER + "//h2/following-sibling::span[2]").getAttribute("class").equals("stock-status in-stock")) {
            product.setStatusApotek(true);
        } else {
            product.setStatusApotek(false);
        }
        if (findBy(PRODUCT_CONTAINER + "//h2/following-sibling::*[1]").getAttribute("class").equals("product-promotional") == true) {
            product.setPromotionalText(findBy(PRODUCT_CONTAINER + "//h2/following-sibling::*[@class='product-promotional']").getText());
        }

        if (findBy(PRODUCT_CONTAINER + "//div[@class='price-panel']/*[2]") != null) {
            product.setDiscountPrice(findBy(PRODUCT_CONTAINER + "//div[@class='price-panel']/*[2]").getText());
        }
        product.setPrice(findBy(PRODUCT_CONTAINER + "//div[@class='price-panel']/*[1]").getText());
        return product;
    }

    public List<String> getBreadCrumbLinks() {
        List<String> breadcrambLinks = new ArrayList<>();
        for (int i = 1; i <= findAll(".//ol[@class='breadcrumb']/li/a").size(); i++) {
            breadcrambLinks.add(findBy("//ol[@class='breadcrumb']/li/a").getText());
        }
        breadcrambLinks.add(findBy("//ol[@class='breadcrumb']/li[@class='active']").getText());
        return breadcrambLinks;
    }

    private String getProductStatuses(Integer productNumber) {
        String productStatuses = "";

        if (findBy("((" + PRODUCT_CONTAINER + ")[" + productNumber + "]//h2/following-sibling::span)[1]").getAttribute("class").equals("stock-status in-stock") == true) {
            productStatuses = productStatuses + "true";//online status
        } else {
            productStatuses = productStatuses + "false";
        }

        if (findBy("((" + PRODUCT_CONTAINER + ")[" + productNumber + "]//h2/following-sibling::span)[2]").getAttribute("class").equals("stock-status in-stock") == true) {
            productStatuses = productStatuses + "true";//apotheek
        } else {
            productStatuses = productStatuses + "false";
        }
        return productStatuses;
    }

    public Product clickOnProductOnProductsPage(int productNumber, Map<String, Boolean> statuses) {
        Product product = new Product();
        String currentProductStatuses;
        String expectedProductStatuses = "";
        Boolean flagProductAdded = false;
        Integer counterProducts = 0;

        for (Map.Entry<String, Boolean> entry : statuses.entrySet()) {
            expectedProductStatuses = expectedProductStatuses + entry.getValue();
        }

        for (int i = 1; i <= getPagesOfPagination(); i++) {
            for (int j = 1; j <= findAll(PRODUCT_CONTAINER).size(); j++) {
                scrollIntoView(PRODUCT_CONTAINER + "[" + j + "]");
                currentProductStatuses = getProductStatuses(j);
                if ((currentProductStatuses.equals(expectedProductStatuses) && ((counterProducts + 1) == productNumber))) {
                    moveTo(PRODUCT_CONTAINER + "[" + j + "]");
                    product.setProductName(findBy(PRODUCT_CONTAINER + "[" + j + "]//a[@class='thumb']").getAttribute("Title"));
                    product.setStatusNet(statuses.get("Online"));
                    product.setStatusApotek(statuses.get("Apotheek"));
                    if (findBy(PRODUCT_CONTAINER + "[" + j + "]//h2/following-sibling::*[1]").getAttribute("class").equals("product-promotional") == true) {
                        product.setPromotionalText(findBy(PRODUCT_CONTAINER + "[" + j + "]//h2/following-sibling::*[@class='product-promotional']").getText());
                    }
                    product.setQty(1);
                    product.setPrice(findBy(PRODUCT_CONTAINER + "[" + j + "]//div[@class='price-panel']/*[1]").getText());
                    findBy(PRODUCT_CONTAINER + "[" + j + "]//a[@class='name']").click();
                    flagProductAdded = true;
                    break;
                } else {
                    if (currentProductStatuses.equals(expectedProductStatuses)) {
                        counterProducts++;
                    }
                }
            }
            if (flagProductAdded == true) {
                break;
            } else {
                scrollIntoView(".//a[@rel='next']");
                findBy(".//a[@rel='next']").click();
            }
        }
        return product;

    }

    public boolean isBadgeDiscountDisplayed(String productName) {
        String PRODUCT_CONTAINER = ".//a[@title='" + productName + "']//span[@class='badge discount']";
        try {
            withTimeoutOf(10, TimeUnit.SECONDS).waitFor(ExpectedConditions.visibilityOfElementLocated(By.xpath(PRODUCT_CONTAINER)));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void openProductsDetailsPageOf(String translatedProductName) {
        withTimeoutOf(5, TimeUnit.SECONDS).waitFor(ExpectedConditions.presenceOfElementLocated(By.xpath(".//article[@class='product-item']//h2/a[.='" + translatedProductName + "']")));
        moveTo(".//article[@class='product-item']//h2/a[.='" + translatedProductName + "']");
        findBy(".//article[@class='product-item']//h2/a[.='" + translatedProductName + "']").click();
    }

    public void selectOptionOfFilterByPrice(String option) {
        String OPTION = ".//form[input[@value=':relevance:price:" + option + "']]/label";
        withTimeoutOf(5, TimeUnit.SECONDS).waitFor(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath(".//span[@class='trigger facet-name js-facet-name']")));
        scrollIntoView(".//span[@class='trigger facet-name js-facet-name']", -50);
        findBy(OPTION).click();
    }

    public Boolean isOptionDisplayedInAppliedFacetsSection(String option) {
        String OPTION = ".//ul[@class='list active-facets']/li/span[.='" + option + "']";
        try {
            return $(OPTION).isVisible();
        } catch (Exception e) {
            return false;
        }
    }

    public Integer getQtyOfProductsOnPage() {
        return findAll(PRODUCT_CONTAINER).size();
    }

    public void selectOptionSortBy(String optionName) {
        String SORT_BY_DROP_DOWN=".//select[@id='sortOptions1']";
        withTimeoutOf(10, TimeUnit.SECONDS).waitFor(ExpectedConditions.presenceOfElementLocated(By.xpath(SORT_BY_DROP_DOWN)));
        scrollIntoView(SORT_BY_DROP_DOWN,-50);
        $(SORT_BY_DROP_DOWN).selectByVisibleText(optionName);
    }

    public List<String> getParameterOfAllVisibleProduct(String parameterXpath) {
        List<String> productsName=new ArrayList<>();
        for(WebElementFacade productName:findAll(parameterXpath)){
            productsName.add(productName.getText());
        }
        return productsName;
    }

}
