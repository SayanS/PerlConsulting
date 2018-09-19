package lloydsPharmaProject.pages;

import lloydsPharmaProject.Locators;
import lloydsPharmaProject.Models.Product;
import net.serenitybdd.core.annotations.findby.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class ShoppingCart extends BasePage {
    private String MINI_SHOPPING_CART_ITEMS_PRODUCT_NAME = "(.//div[@class='mini-cart-body']//div[@class='details']/a)";
    private String MINI_SHOPPING_CART_ITEMS_PRODUCT_QTY = "(.//div[@class='mini-cart-body']//div[@class='details']//div[@class='qty'])";
    private String MINI_SHOPPING_CART_ITEMS_PRODUCT_PRICE = "(.//div[@class='mini-cart-body']//div[@class='details']//div[@class='price'])";
    private String MINI_SHOPPING_CART_GOTO_CART_BUTTON = ".//div[@class='mini-cart-body']/a[@href='/cart']";
    private String SHOPPING_CART_REMOVE_PRODUCT_BUTTONS = ".//table[@class='cart-list product-list']//button[contains(@id,'removeEntry')]";

    private String SHOPPING_CART_PRODUCT_CONTAINER = "(.//table/tbody/tr[@class='product-item'])";

    public boolean isMiniShoppingCartDisplayed() {
        try {
            withTimeoutOf(10, TimeUnit.SECONDS).waitFor(ExpectedConditions
                    .visibilityOfElementLocated(org.openqa.selenium.By.xpath(Locators.SHOPPING_CART_MINI_CART_BODY)));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void clickOnGoCartButtonOnMiniCart() {
        WebElement form;
        form = getDriver().findElement(By.id("main-header"));
        form.findElement(By.xpath(MINI_SHOPPING_CART_GOTO_CART_BUTTON)).click();
    }

    public void deleteAllProductsFromTheShoppingCart() {
        String REMOVE_PRODUCT_BUTTON = "(.//table/tbody/tr/td[@class='remove']/button[@class='remove-item remove-entry-button'])";
        Integer itemsCount = findAll(REMOVE_PRODUCT_BUTTON).size();
        for (int i = 0; i < itemsCount; i++) {
            findBy(REMOVE_PRODUCT_BUTTON + "[1]").click();
            waitABit(1000);
        }
    }

    public void clickOnCartButtonInHeader() throws InterruptedException {
        for (int i = 1; i <= 100; i++) {
            try {
                $(".//span[.='Winkelwagen' or .='Panier']").click();
                //moveTo(".//a[@class='mini-cart-link refresh-blink']");
                waitABit(2000);
                withTimeoutOf(1, TimeUnit.SECONDS).waitFor(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h1[@class='h-medium page-title']")));
                if ($("//h1[@class='h-medium page-title']").isDisplayed()) {
                    break;
                }
            } catch (Exception e) {
            }
        }
    }

    public String getCartCount() {
        String CART_COUNT = ".//a[@href='/cart']/span[@class='mini-cart-count js-blink-mini-cart-count']";
        return findBy(CART_COUNT).getAttribute("data-count");
    }

    private void moveToProductContainer(String productCode) {
        String PRODUCT_LINE = "//div[@class='info']//span[contains(text(),'" + productCode + "')][1]/ancestor::tr";
        withTimeoutOf(10, TimeUnit.SECONDS).waitFor(ExpectedConditions.presenceOfElementLocated(By.xpath(PRODUCT_LINE)));
        moveTo(PRODUCT_LINE);
    }

    public String getTopPromotionTextForProduct(String productCode) {
        String PRODUCT_LINE = "//div[@class='info']//span[contains(text(),'" + productCode + "')][1]/ancestor::tr";
        moveToProductContainer(productCode);
        try {
            return $(PRODUCT_LINE + "/ancestor::tbody/preceding-sibling::thead[1]//span").getText();
        } catch (Exception e) {
            return null;
        }
    }

    public String getValueOfOrderSummary(String fieldName, String currentLanguage) throws IOException {
        String translatedProductName = getTranslation(fieldName, currentLanguage, DICTIONARY_CHECKOUT);
        for (int i = 1; i <= findAll(".//table[@class='cart-totals table']//tr/td[1]").size(); i++) {
            if ($("(.//table[@class='cart-totals table']//tr/td[1])[" + i + "]").getText().equals(translatedProductName)) {
                return findBy("(.//table[@class='cart-totals table']//tr/td[1])[" + i + "]/following-sibling::td").getText();
            }
        }
        return "0.00";
    }

    public String getTipsMessage() {
        withTimeoutOf(5, TimeUnit.SECONDS).waitFor(ExpectedConditions.presenceOfElementLocated(By.xpath(".//div[@class='tips-cell']")));
        return $(".//div[@class='tips-cell']").getText();
    }

    public void removeProductFromCart(String productCode) {
        String PRODUCT_CONTAINER = "//div[@class='info']//span[contains(text(),'" + productCode + "')][1]/ancestor::tr";
        String REMOVE_BUTTON = PRODUCT_CONTAINER + "/td/button[contains(@id,'removeEntry')]";
        scrollIntoView(PRODUCT_CONTAINER, -50);
        withTimeoutOf(7, TimeUnit.SECONDS).waitFor(ExpectedConditions.elementToBeClickable(By.xpath(REMOVE_BUTTON)));
        moveTo(REMOVE_BUTTON);
        waitABit(500);
        try {
            findBy(REMOVE_BUTTON).click();
        }catch(Exception e){
            scrollIntoView(PRODUCT_CONTAINER);
            moveTo(REMOVE_BUTTON);
            waitABit(500);
            findBy(REMOVE_BUTTON).click();
        }

    }

    public String getCartEmptyMessage() {
        withTimeoutOf(5, TimeUnit.SECONDS).waitFor(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//h2[@class='error-title']")));
        return findBy(".//h2[@class='error-title']").getText();
    }

    public Product getProductFromMiniCart(String productCode) {
        String PRODUCT_CONTAINER = ".//a[contains(@href,'" + productCode + "')]/ancestor::div[@class='details']";
        String tmpCodeProduct;
        Product product = new Product();
        String qty;
        scrollIntoView(".//nav[@class='tool-navigation']//a[@class='mini-cart-link refresh-blink']", -50);
        waitABit(1000);
        moveTo(".//nav[@class='tool-navigation']//a[@class='mini-cart-link refresh-blink']");
        waitABit(2000);
        tmpCodeProduct=$(PRODUCT_CONTAINER + "/a[@class='name']").getAttribute("href");
        product.setProductCode(tmpCodeProduct.substring(tmpCodeProduct.indexOf("/p/")+3, tmpCodeProduct.length()));
        product.setProductName($(PRODUCT_CONTAINER + "/a").getText());
        qty = $(PRODUCT_CONTAINER + "/div[@class='qty']").getText();
        qty = qty.replace(qty.substring(0, qty.lastIndexOf(":") + 1), "");
        qty = qty.trim();
        product.setQty(Integer.valueOf(qty));
        product.setPrice($(PRODUCT_CONTAINER + "/div[@class='price']").getText());
        return product;
    }

    public Product getProductFromCart(String productCode) {
        String PRODUCT_CONTAINER = "//div[@class='info']//span[contains(text(),'" + productCode + "')][1]/ancestor::tr";
        Double discount;
        Product product = new Product();
        withTimeoutOf(10, TimeUnit.SECONDS)
                .waitFor(ExpectedConditions.visibilityOfElementLocated(By.xpath(PRODUCT_CONTAINER)));
        scrollIntoView(PRODUCT_CONTAINER, -50);
        product.setProductCode($(PRODUCT_CONTAINER + "//div[@class='info']/span[1]").getText()
                .replace(" ", "").replace("cnk:", "").replace(",", ""));
        product.setProductName($(PRODUCT_CONTAINER + "//h4[@class='name']").getText());
        product.setQty(Integer.valueOf($(PRODUCT_CONTAINER + "//input[@name='quantity']").getValue()));

        product.setPrice($(PRODUCT_CONTAINER + "//div[@class='info']/span[2]").getText()
                .replace("artikelprijs", "").replace("Prix de l'article", "").replace(":", "").replace(" ", ""));
        try {
            discount = Double.valueOf($(PRODUCT_CONTAINER + "//span[@class='product-promotional your-savings']/strong").getText()
                    .replace(",", ".").replace("-", "00").replace("€", "").replace(" ", "").trim());
            product.setDiscountPrice((product.getLineTotal() - discount) / product.getQty());
            product.setPromotionalText($(PRODUCT_CONTAINER + "/following-sibling::tr[@class='discount-row']").getText());
        } catch (Exception e) {
        }

        if (findBy(PRODUCT_CONTAINER + "/td[2]/span[1]").getAttribute("class").equals("stock streamline-ok success")) {
            product.setStatusNet(true);
        } else product.setStatusNet(false);

        if (findBy(PRODUCT_CONTAINER + "/td[2]/span[2]").getAttribute("class").equals("stock streamline-ok success")) {
            product.setStatusApotek(true);
        } else product.setStatusApotek(false);

        return product;
    }


}
