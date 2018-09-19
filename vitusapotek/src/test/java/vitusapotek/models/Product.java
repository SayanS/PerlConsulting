package vitusapotek.models;

public class Product {
    private String itemNumber;
    private String productName;
    private Boolean statusNet;
    private Boolean statusApotek;
    private String promotionalText="Produkter";
    private Double discountPrice;
    private Double listPrice;
    private Double price;
    private Integer qty = 1;
    private Boolean notEnoughQty=false;

    public Product() {
    }

    public Product(Product product) {
        this.itemNumber=product.itemNumber;
        this.productName = product.productName;
        this.statusNet = product.statusNet;
        this.statusApotek = product.statusApotek;
        this.promotionalText=product.promotionalText;
        this.discountPrice =product.discountPrice;
        this.listPrice=product.listPrice;
        this.price = product.price;
        this.qty = product.qty;
    }

    public String getItemNumber() {
        return itemNumber;
    }

    public void setItemNumber(String itemNumber) {
        this.itemNumber = itemNumber;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Boolean getStatusNet() {
        return statusNet;
    }

    public void setStatusNet(Boolean status) {
        this.statusNet = status;
    }

    public void setStatusApotek(Boolean status) {
        this.statusApotek = status;
    }

    public Boolean getStatusApotek() {
        return statusApotek;
    }

    public void setPromotionalText(String promotionalText) {
        this.promotionalText = promotionalText;
    }

    public String getPromotionalText() {
        return promotionalText;
    }


    public Double getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(Double discountPrice) {
        this.discountPrice = discountPrice;
    }

    public Double getListPrice() {
        return listPrice;
    }

    public void setListPrice(Double listPrice) {
        this.listPrice = listPrice;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public Double getTotal() {
        if(this.discountPrice==null){
            return Double.valueOf(this.qty * this.listPrice);
        }
        return Double.valueOf(this.qty * this.discountPrice);
    }

    public Boolean getNotEnoughQty() {
        return notEnoughQty;
    }

    public void setNotEnoughQty(Boolean notEnoughQty) {
        this.notEnoughQty = notEnoughQty;
    }


    @Override
    public String toString() {
        return itemNumber+" "+productName + ": " + qty + " X " + listPrice +" discountPrice="+ discountPrice +" nett=" + statusNet + " apotek=" + statusApotek;
    }

}
