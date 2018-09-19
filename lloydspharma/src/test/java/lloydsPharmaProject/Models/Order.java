package lloydsPharmaProject.Models;

import java.util.ArrayList;
import java.util.List;

public class Order {
    private  String testNumber;
    private String orderNumber;
    private String creationDate;
    private Address deliveryAddress;
//    private String deliveryMethod;
    private Address paymentAddress;
    private String shippingPrice;
    private String totalOrder;
    private String totalOrderDiscount;
    private String shipmentMethod;
    private String orderNote;
    private List<Product> products=new ArrayList<>();

    public Order(){
        this.deliveryAddress=new Address();
        this.paymentAddress=new Address();
        this.products=new ArrayList<>();
    }

    public Order(Order order){
        this.testNumber=order.testNumber;
       this.orderNumber=order.getOrderNumber();
        this.creationDate=order.getCreationDate();
        this.deliveryAddress=new Address();
        this.deliveryAddress=order.getDeliveryAddress();
//        this.deliveryMethod=order.getDeliveryMethod();
        this.paymentAddress=new Address();
        this.paymentAddress=order.getPaymentAddress();
        this.shippingPrice=order.getShippingPrice();
        this.totalOrder=order.getTotalOrder();
        this.totalOrderDiscount=order.getTotalOrderDiscount();
        this.shipmentMethod=order.getShipmentMethod();
        this.orderNote=order.getOrderNote();
        this.products=new ArrayList<>();
        this.products=order.getProducts();
    }

    public String getTestNumber() {
        return testNumber;
    }

    public void setTestNumber(String testNumber) {
        this.testNumber = testNumber;
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

//    public String getDeliveryMethod() {
//        return deliveryMethod;
//    }

//    public void setDeliveryMethod(String deliveryMethod) {
//        this.deliveryMethod = deliveryMethod;
//    }

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

    public String getTotalOrderDiscount() {
        return totalOrderDiscount;
    }

    public String getTotalOrder() {
        return totalOrder;
    }

    public void setTotalOrder(String totalOrder) {
        this.totalOrder = totalOrder.replace(" ","").replace("€","").replace("-","0").replace(",",".");
    }

    public String getShipmentMethod() {
        return shipmentMethod;
    }

    public void setShipmentMethod(String shipmentMethod) {
        this.shipmentMethod = shipmentMethod;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void addProduct(Product product) {
        this.products.add(new Product(product));
    }

    public double getOrderTotalWithShippingPrice(){
        return Double.valueOf(String.format("%8.2f", Float.valueOf(totalOrder.replace("-", "00").replace(",", ".").replace(" €","")) + Float.valueOf(shippingPrice.replace(",", "."))));
    }

    public String getOrderNote() {
        return orderNote;
    }

    public void setOrderNote(String orderNote) {
        this.orderNote = orderNote;
    }

    public Double calculateTotalProducts(){
        Double totalProducts=0.0;
        for(Product product:products){
            totalProducts=totalProducts+product.getLineTotal();
        }
        return  Double.valueOf(String.format("%8.2f",totalProducts).replace(",","."));
    }

    public Double calculateTotalDiscountProducts(){
        Double totalDiscountProducts=0.0;
        for(Product product:products){
            if(product.getDiscountPrice()!=null){
                totalDiscountProducts = totalDiscountProducts + (product.getPrice() - product.getDiscountPrice()) * product.getQty();
            }
        }
        return  Double.valueOf(String.format("%8.2f",totalDiscountProducts).replace(",","."));
    }

    public Double calculateTotalProductsWithoutDiscount(){
        Double totalDiscountProducts=0.0;
        for(Product product:products){
                totalDiscountProducts = totalDiscountProducts + product.getPrice() * product.getQty();
        }
        return  Double.valueOf(String.format("%8.2f",totalDiscountProducts).replace(",","."));
    }

    public void removePrtoduct(String productCode) {
        for(int i=0; i<products.size();i++){
            if(products.get(i).getProductCode().equals(productCode)){
                products.remove(i);
            }
        }
    }
}
