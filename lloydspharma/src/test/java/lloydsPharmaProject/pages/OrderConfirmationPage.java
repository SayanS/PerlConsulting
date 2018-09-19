package lloydsPharmaProject.pages;

import lloydsPharmaProject.Models.Address;
import lloydsPharmaProject.Models.Order;
import lloydsPharmaProject.Models.Product;
import net.serenitybdd.core.annotations.findby.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.concurrent.TimeUnit;

public class OrderConfirmationPage extends BasePage {
    private String ORDER_TITLE = ".//header/h1";
    private String ORDER_NUMBER = ".//section[@class='container top-container']/div/div[3]/p[1]";
    private String ORDER_CREATION_DATE = ".//section[@class='container top-container']/div/div[3]/p[2]";
    private String DELIVERY_ADDRESS = "(.//h4[1]/following-sibling::ul)[1]/li";
    private String PAYMENT_ADDRESS = "(.//h4[1]/following-sibling::ul)[2]/li";
    private String ORDER_TOTAL = ".//li[@class='total']//span";
    private String ORDER_SHIPPING_PRICE = ".//div[@class='order-pricetable order-box']/ul/li[1]/span";
    private String DELIVERY_ADDRESS_PHARMACY = ".//section[@class='container top-container']//div/h4/ancestor::div[1]/ul";
    private String PAYMENT_ADDRESS_TITLE = ".//section//div/h4/following-sibling::p";

    private Order order = new Order();

    public String getOrderConfirmationTitle() {
        withTimeoutOf(60, TimeUnit.SECONDS).waitFor(ExpectedConditions.presenceOfElementLocated(By.xpath(ORDER_TITLE)));
        return $(ORDER_TITLE).getText();
    }

    public Order getOrderDetails() {
        String ORDER_NUMBER = ".//p[@class='lead']";
        Address address = new Address();
        String orderNumber;
        String creationDate;

        orderNumber = $(ORDER_NUMBER).getText();
        orderNumber = orderNumber.substring(orderNumber.indexOf("LPBE-"), orderNumber.indexOf("LPBE-") + 15);
        order.setOrderNumber(orderNumber);

        order.setTotalOrder($(ORDER_TOTAL).getText());
        order.setShippingPrice($(ORDER_SHIPPING_PRICE).getText().replace("-", "").replace("€", ""));

        creationDate = $(ORDER_CREATION_DATE).getText();
        creationDate = creationDate.substring(creationDate.indexOf(":") + 1, creationDate.length()).trim();
        order.setCreationDate(creationDate);

        if (findAll(DELIVERY_ADDRESS).size() == 3) {
            address.setFirstName($(DELIVERY_ADDRESS + "[1]").getText().split(" ")[0]);
            address.setLastName($(DELIVERY_ADDRESS + "[1]").getText().split(" ")[1]);
            address.setAddress1($(DELIVERY_ADDRESS + "[2]").getText());
            address.setPostCode($(DELIVERY_ADDRESS + "[3]").getText().split(", ")[0]);
            address.setCity($(DELIVERY_ADDRESS + "[3]").getText().split(", ")[1]);
            order.setDeliveryAddress(address);

            address.setFirstName($(PAYMENT_ADDRESS + "[1]").getText().split(" ")[0]);
            address.setLastName($(PAYMENT_ADDRESS + "[1]").getText().split(" ")[1]);
            address.setAddress1($(PAYMENT_ADDRESS + "[2]").getText());
            address.setPostCode($(PAYMENT_ADDRESS + "[3]").getText().split(", ")[0]);
            address.setCity($(PAYMENT_ADDRESS + "[3]").getText().split(", ")[1]);
            order.setPaymentAddress(address);
        }

        if (findAll(DELIVERY_ADDRESS).size() == 4) {
            address.setFirstName($(DELIVERY_ADDRESS_PHARMACY + "/li/a").getText());
            address.setLastName(null);
            address.setAddress1($(DELIVERY_ADDRESS_PHARMACY + "/li[2]").getText() + " " + $(DELIVERY_ADDRESS_PHARMACY + "/li[3]").getText());
            address.setPostCode($(DELIVERY_ADDRESS_PHARMACY + "/li[4]").getText().split(", ")[0]);
            address.setCity($(DELIVERY_ADDRESS_PHARMACY + "/li[4]").getText().split(", ")[1]);
            order.setDeliveryAddress(address);

            address = new Address();
            address.setFirstName($(PAYMENT_ADDRESS_TITLE).getText());
//            address.setLastName("");
//            address.setAddress1("");
//            address.setPostCode("");
//            address.setCity("");
            order.setPaymentAddress(address);
        }
        return order;
    }

    public Product getProduct(String productCode) {
        String PRODUCT_ITEM = "//div[@class='info']//span[contains(text(),'" + productCode + "')][1]/ancestor::tr";
        Double discount;
        Product product = new Product();
        scrollIntoView(PRODUCT_ITEM, -50);
        product.setProductCode($("//div[@class='info']//span[contains(text(),'"+productCode+"')]").getText()
                .replace(" ","").replace("cnk:",""));
        product.setProductName($(PRODUCT_ITEM + "//h4[@class='name']").getText());
        product.setQty(Integer.valueOf($(PRODUCT_ITEM + "//span[@class='qty']").getText()));
        product.setPrice($(PRODUCT_ITEM + "//div[@class='info']/span[2]").getText()
                .replace("artikelprijs ", "").replace("\tPrix \u200B\u200Bde l'article ", ""));
        try {
            discount = Double.valueOf($(PRODUCT_ITEM + "//span[@class='product-promotional your-savings']/strong").getText()
                    .replace(",", ".").replace("-", "00").replace("€", "").replace(" ", "").trim());
            product.setDiscountPrice((product.getLineTotal() - discount) / product.getQty());
            product.setPromotionalText($(PRODUCT_ITEM + "/following-sibling::tr[@class='discount-row']").getText());
        } catch (Exception e) {
        }
        return product;
    }

    public Integer getProductsAmount() {
        return findAll("//div[@class='info'][1]/ancestor::tr").size();
    }

    public String getOrderNote() {
        String ORDER_NOTE=".//h4[contains(text(),'Aantekening:') or contains(text(),'Commentaire:')]/ancestor::div[1]//ul/li";
        scrollIntoView(ORDER_NOTE,-50);
        return $(ORDER_NOTE).getText();
    }
}
