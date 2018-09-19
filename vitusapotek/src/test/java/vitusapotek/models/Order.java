package vitusapotek.models;

import java.util.ArrayList;
import java.util.List;

public class Order {
    private String orderNumber;
    private String creationDate;
    private Address deliveryAddress;
    private String deliveryMethod;
    private Address paymentAddress;
    private String shippingPrice;
    private String totalOrder;
    private List<Product> products;

    public Order(){
        this.deliveryAddress=new Address();
        this.paymentAddress=new Address();
        this.products=new ArrayList<>();
        this.shippingPrice="0";
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public Address getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(Address deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public String getDeliveryMethod() {
        return deliveryMethod;
    }

    public void setDeliveryMethod(String deliveryMethod) {
        this.deliveryMethod = deliveryMethod;
    }

    public Address getPaymentAddress() {
        return paymentAddress;
    }

    public void setPaymentAddress(Address paymentAddress) {
        this.paymentAddress = paymentAddress;
    }

    public String getShippingPrice() {
        return shippingPrice;
    }

    public void setShippingPrice(String shippingPrice) {
        this.shippingPrice = shippingPrice;
    }

    public String getTotalOrder() {
        return totalOrder;
    }

    public void setTotalOrder(String totalOrder) {
        this.totalOrder = totalOrder;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void addProduct(Product product) {
        this.products.add(product);
    }

    public double getOrderTotalWithShippingPrice(){
        return Double.valueOf(String.format("%8.2f", Float.valueOf(totalOrder.replace("-", "00").replace(",", ".").replace(" ","")) + Float.valueOf(shippingPrice.replace(",", ".").replace(" ",""))).replace(",","."));
    }
}
