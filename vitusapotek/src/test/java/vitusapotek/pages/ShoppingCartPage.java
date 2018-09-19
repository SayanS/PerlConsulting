package vitusapotek.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import vitusapotek.models.Product;

import java.util.concurrent.TimeUnit;

public class ShoppingCartPage extends BasePage {
    String CHECKOUT_BUTTON = "(.//*[@id='site']//button[@data-checkout-url='/cart/checkout'])[1]";
    String PRODUCT_LINE = "//span[contains(text(),'$itemNumber')]/ancestor::tr[@class='product-item']";

    public void clickOnCheckoutButton() {
        withTimeoutOf(10, TimeUnit.SECONDS).waitFor(ExpectedConditions.presenceOfElementLocated(By.xpath(CHECKOUT_BUTTON)));
        scrollIntoView(CHECKOUT_BUTTON);
        moveTo(CHECKOUT_BUTTON);
        withTimeoutOf(10, TimeUnit.SECONDS).waitFor(ExpectedConditions.elementToBeClickable(By.xpath(CHECKOUT_BUTTON)));
        clickOnJS(CHECKOUT_BUTTON);
    }

    public String getOrderNumber() {
        return $(".//span[@class='cartId']").getText();
    }

    public String getOrderTotal() {
        String TOTAL_CART = ".//table[@class='cart-totals table']/tbody[2]/tr/td[2]";
        scrollIntoView(TOTAL_CART);
        return $(TOTAL_CART).getText();
    }

    public Product getProductFromCart(String itemNumber) {
        String xpathProductLine = PRODUCT_LINE.replace("$itemNumber", itemNumber);
        Product product = new Product();
        scrollIntoView(xpathProductLine);
        product.setProductName($(xpathProductLine + "//h4[@class='name']").getText());
        product.setItemNumber(itemNumber);
        product.setListPrice(Double.valueOf($(xpathProductLine + "//*[contains(text(),'Listepris')]").getText()
                .replace("Listepris:", "").replace(",", ".").replace("-", "0").replace(" ","")));
        product.setQty(Integer.valueOf($(xpathProductLine + "//input[contains(@id,'quantity_')]").getAttribute("value")));

        try {
            product.setDiscountPrice(Double.valueOf(String.format("%8.2f", (product.getListPrice() * product.getQty() - getProductTotalDiscount(itemNumber)) / product.getQty())
                    .replace(",", ".").replace("-", "0").replace(" ", "")));
        } catch (Exception e) {
        }

        if (findBy(xpathProductLine + "//td[2]//span[contains(text(),'nett')]/ancestor::span").getAttribute("class").equals("stock streamline-ok success")) {
            product.setStatusNet(true);
        } else product.setStatusNet(false);

        if (findBy(xpathProductLine + "//td[2]//span[contains(text(),'apotek')]/ancestor::span").getAttribute("class").equals("stock streamline-ok success")) {
            product.setStatusApotek(true);
        } else product.setStatusApotek(false);
        return product;
    }

    public Double getProductTotalPrice(String itemNumber) {
        String xpathProductLine = PRODUCT_LINE.replace("$itemNumber", itemNumber);
        return Double.valueOf($(xpathProductLine + "//span[@class='item-price']").getText()
                .replace(",", ".").replace("-", "").replace(" ", ""));
    }

    public Double getProductTotalDiscount(String itemNumber) {
        String xpathProductLine = PRODUCT_LINE.replace("$itemNumber", itemNumber);
        return Double.valueOf($(xpathProductLine + "//span[@class='product-promotional your-savings']").getText()
                .replace(",", ".").replace("-", "").replace(" ",""));
    }

    public void moveToCartButtonInHeader() {
        moveTo(HEADER_SHOPPING_CART_BUTTON);
    }

    public Product getProductsFromMiniShoppingCart(String itemNumber) {
        String MINI_SHOPPING_CART = ".//div[@class='mini-cart js-mini-cart']";
        String MINI_CART_LINE = ".//li[@class='mini-cart-item']//a[contains(@href,'p/"+itemNumber+"')]/ancestor::div[@class='details']";
        Product product = new Product();
        withTimeoutOf(5, TimeUnit.SECONDS)
                .waitFor(ExpectedConditions.visibilityOfElementLocated(By.xpath(MINI_SHOPPING_CART)));

            product.setProductName(findBy( MINI_CART_LINE + "//a[@class='name']").getText());
            product.setItemNumber(itemNumber);
            product.setQty(Integer.valueOf(findBy(MINI_CART_LINE + "//div[@class='qty']").getText()
                    .replace("Antall: ", "")));
            product.setListPrice(Double.valueOf(findBy(MINI_CART_LINE + "//div[@class='price']").getText()
                    .replace(",", ".").replace("-", "00").replaceAll(" ", "")));
        return product;
    }

    public String getTotalMiniCart() {
        return findBy(".//div[@class='mini-cart-totals']//div[@class='value']").getText();
    }

    public void clickOnCartButtonOnMiniCart() {
        moveTo(".//div[@class='mini-cart-body']//a[@href='/cart']").click();
    }


    public void deleteAllProductsFromTheShoppingCart() {
        String REMOVE_PRODUCT_BUTTON = "(.//table/tbody/tr/td[@class='remove']/button[@class='remove-item remove-entry-button'])";
        Integer itemsCount = findAll(REMOVE_PRODUCT_BUTTON).size();
        for (int i = 0; i < itemsCount; i++) {
            // scrollIntoView(REMOVE_PRODUCT_BUTTON+"[1]");
            findBy(REMOVE_PRODUCT_BUTTON + "[1]").click();
            waitABit(1000);
        }
    }

    public String getMessageFromTheShoppingCart() {
        withTimeoutOf(10, TimeUnit.SECONDS).waitFor(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//h2")));
        return findBy(".//h2").getText();
    }

    public String getCartCount() {
        String CART_COUNT = ".//a[@href='/cart']/span[@class='mini-cart-count js-blink-mini-cart-count']";
        try{
            withTimeoutOf(5,TimeUnit.SECONDS).waitFor(ExpectedConditions.attributeContains(By.xpath(CART_COUNT),"data-count","1"));
        }catch(Exception e){
        }
        return findBy(CART_COUNT).getAttribute("data-count");
    }

    public void clickOnButton(String buttonName) {
        String BUTTON = "(.//button[contains(text(),'" + buttonName + "')])[1]";
        withTimeoutOf(5, TimeUnit.SECONDS).waitFor(ExpectedConditions.visibilityOfElementLocated(By.xpath(BUTTON)));
        moveTo(BUTTON).click();
    }

    public String getProductLineTitle(String productName) {
        String PRODUCT_LINE_TITLE = ".//table[@class='cart-list product-list']/tbody/tr/td[1]//h4[.='" + productName + "']/ancestor::tbody/preceding-sibling::thead[1]//th/span";
        moveTo(PRODUCT_LINE_TITLE);
        return findBy(PRODUCT_LINE_TITLE).getText();
    }

    public Double getValueFromOrderOverview(String fieldName){
        String FIELD=".//table[@class='cart-totals table']//*[contains(text(),'"+fieldName+"')]/ancestor::tr/td[2]";
        scrollIntoView(".//h1[.='Ordreoversikt']");
        return Double.valueOf(findBy(FIELD).getText().replace(",", ".").replace("-", "00").replace(" ", ""));
    }
}
