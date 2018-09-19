package vitusapotek.pages;

import net.serenitybdd.core.annotations.findby.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import vitusapotek.models.Address;
import vitusapotek.models.Order;
import vitusapotek.models.Product;

import java.util.concurrent.TimeUnit;

public class OrderConfirmationPage extends BasePage {
    String ORDER_NUMBER = ".//section[@class='container top-container']/div/div[3]/p[1]";
    String ORDER_CREATION_DATE = ".//section[@class='container top-container']/div/div[3]/p[2]";
    String DELIVERY_ADDRESS = "(.//h4[.='Leveringsinformasjon:']/ancestor::div/ul/li)";
    String PAYMENT_ADDRESS = ".//h4[.='Betalingsinformasjon:']/ancestor::div[1]/ul/li";
    String PRODUCT_ITEM = "(//table[@class='cart-list product-list cart-list--compact']//tr[@class='product-item'])";
    String ORDER_TOTAL = ".//li[@class='total']//span";
    String ORDER_SHIPPING_PRICE = ".//div[@class='order-pricetable order-box']/ul/li[1]/span";

    String DELIVERY_ADDRESS_PHARMACY = ".//section[@class='container top-container']//div/h4/ancestor::div[1]/ul";
    String PAYMENT_ADDRESS_TITLE = ".//section//div/h4/following-sibling::p";
    String PRODUCT_LINE = "//span[contains(text(),'$itemNumber')]/ancestor::tr[@class='product-item']";

    public Product getProduct(String itemNumber){
        String xpathProductLine=PRODUCT_LINE.replace("$itemNumber",itemNumber);
        Product product=new Product();
        scrollIntoView(xpathProductLine);
        product.setProductName($(xpathProductLine+"//h4[@class='name']").getText());
        product.setItemNumber(itemNumber);
        product.setListPrice(Double.valueOf($(xpathProductLine+"//span[contains(text(),'Listepris')]").getText()
                .replace("Listepris","").replace(",", ".").replace("-", "0").replace(" ","")));
        product.setQty(Integer.valueOf($(xpathProductLine+"//span[@class='qty']").getText()));
        try {
            product.setDiscountPrice(Double.valueOf(String.format("%8.2f",(product.getListPrice() * product.getQty() - getProductTotalDiscount(itemNumber))/product.getQty())
                    .replace(",",".").replace("-","0").replace(" ","")));
        }catch(Exception e){
        }
        return product;
    }
    public Double getProductTotalPrice(String itemNumber){
        String xpathProductLine=PRODUCT_LINE.replace("$itemNumber",itemNumber);
        return Double.valueOf($(xpathProductLine+"//span[@class='item-price']").getText()
                .replace(",", ".").replace("-", "").replace(" ",""));
    }

    public Double getProductTotalDiscount(String itemNumber){
        String xpathProductLine=PRODUCT_LINE.replace("$itemNumber",itemNumber);
        return Double.valueOf($(xpathProductLine+"//span[@class='product-promotional your-savings']").getText()
                .replace(",", ".").replace("-", "").replace(" ",""));
    }

    public Order getOrderDetails() {
        Order order = new Order();
        Product product = new Product();
        Address address = new Address();
        String orderNumber;
        String creationDate;
        String tmp;

        orderNumber = $(ORDER_NUMBER).getText();
        orderNumber = orderNumber.substring(orderNumber.indexOf("V-"), orderNumber.indexOf("V-") + 12);
        order.setOrderNumber(orderNumber);

        order.setTotalOrder($(ORDER_TOTAL).getText().replace("-", "00").replace(",", ".").replace(" ", ""));
        order.setShippingPrice($(ORDER_SHIPPING_PRICE).getText().replace("-", "").replace(" ", ""));

        creationDate = $(ORDER_CREATION_DATE).getText();
        creationDate = creationDate.substring(creationDate.indexOf(":") + 1, creationDate.length()).trim();
        order.setCreationDate(creationDate);

        if (findAll(DELIVERY_ADDRESS).size() == 3) {
            tmp = $(DELIVERY_ADDRESS + "[1]").getText();

            address.setFirstName(tmp.substring(0, tmp.indexOf(" ")));
            address.setLastName(tmp.substring(tmp.indexOf(" ") + 1, tmp.length()));

            address.setFirstName(tmp);
            address.setLastName("");


            address.setAddress1($(DELIVERY_ADDRESS + "[2]").getText());
            address.setAddress2("");

            tmp = $(DELIVERY_ADDRESS + "[3]").getText();
            address.setPostCode(tmp.substring(0, tmp.indexOf(",")));

            tmp = $(PAYMENT_ADDRESS + "[3]").getText();
            address.setCity(tmp.substring(tmp.indexOf(",") + 2, tmp.length()));

            order.setDeliveryAddress(address);

            tmp = $(PAYMENT_ADDRESS + "[1]").getText();
            address.setFirstName(tmp.substring(0, tmp.indexOf(" ")));
            address.setLastName(tmp.substring(tmp.indexOf(" ") + 1, tmp.length()));

            address.setAddress1($(PAYMENT_ADDRESS + "[2]").getText());

            tmp = $(PAYMENT_ADDRESS + "[3]").getText();
            address.setPostCode(tmp.substring(0, tmp.indexOf(",")));

            tmp = $(PAYMENT_ADDRESS + "[3]").getText();
            address.setCity(tmp.substring(tmp.indexOf(",") + 2, tmp.length()));

            order.setPaymentAddress(address);
        }

        if (findAll(DELIVERY_ADDRESS).size() == 4) {
            address.setFirstName($(DELIVERY_ADDRESS_PHARMACY + "/li/a").getText());
            address.setLastName(null);
            address.setAddress1($(DELIVERY_ADDRESS_PHARMACY + "/li[2]").getText());

            tmp = $(DELIVERY_ADDRESS_PHARMACY + "/li[4]").getText();
            address.setPostCode(tmp.substring(0, tmp.indexOf(",")));
            tmp = $(DELIVERY_ADDRESS_PHARMACY + "/li[4]").getText();
            address.setCity(tmp.substring(tmp.indexOf(",") + 2, tmp.length()));
            order.setDeliveryAddress(address);

            address = new Address();
            address.setFirstName($(PAYMENT_ADDRESS_TITLE).getText());
            address.setLastName("");
            address.setAddress1("");
            address.setPostCode("");
            address.setCity("");
            order.setPaymentAddress(address);
        }
        return order;
    }

    public void clickOnLoginButton() {
        withTimeoutOf(10, TimeUnit.SECONDS).waitFor(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//form[@id='loginForm']//button[@id='loginBtnCheckout']")));
        moveTo(".//form[@id='loginForm']//button[@id='loginBtnCheckout']").click();
    }

    public String getOrderNumber() {
        String orderNumber = $(ORDER_NUMBER).getText();
        scrollIntoView(ORDER_NUMBER);
        return orderNumber.substring(orderNumber.indexOf("V-"), orderNumber.indexOf("V-") + 12);
    }

    public void clickOnSaveOrderButton() {
        findBy(".//button[@id='loginBtnCheckout']").click();
    }
}
