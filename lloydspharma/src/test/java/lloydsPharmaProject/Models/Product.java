package lloydsPharmaProject.Models;

import java.util.Comparator;

public class Product implements Comparable {
    private String productCode;
    private String productName;
    private Boolean statusNet;
    private Boolean statusApotek;
    private String promotionalText;
    private Double price;
    private Double discountPrice;
    private Integer qty = 1;

    public Product() {
    }

    public Product(Product product) {
        this.productCode = product.productCode;
        this.productName = product.productName;
        this.statusNet = product.statusNet;
        this.statusApotek = product.statusApotek;
        this.price = product.price;
        this.discountPrice = product.discountPrice;
        this.promotionalText = product.promotionalText;
        this.qty = product.qty;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
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

    public String getPromotionalText() {
        return promotionalText;
    }

    public void setPromotionalText(String promotionalText) {
        this.promotionalText = promotionalText;
    }

    public Double getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(String discountPrice) {
        if ((discountPrice != null) && (discountPrice.equals("") == false)) {
            this.discountPrice = Double.valueOf(discountPrice.replace(",", ".").replace("-", "00").replace("€", "").replace(" ", "").trim());
        }
    }

    public void setDiscountPrice(Double discountPrice) {
        if (discountPrice != null) {
            this.discountPrice = discountPrice;
        }
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = Double.valueOf(price.replace(",", ".").replace("-", "00").replace("€", "").replace(" ", "").trim());
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public Double getLineTotal() {
        if (this.discountPrice == null) {
            return Double.valueOf(String.format("%.2f", this.qty * this.price).replace(",", "."));
        }
        return Double.valueOf(String.format("%.2f", this.qty * this.discountPrice).replace(",", "."));
    }

    @Override
    public String toString() {
        if (discountPrice == null) {
            return productCode + " - " + productName + " - " + qty + " X " + price + " - " + statusNet + " - " + statusApotek;
        }
        return productCode + " - " + productName + " - " + qty + " X " + price + " - " + discountPrice + " - "
                + statusNet + " - " + statusApotek + " - " + promotionalText;
    }

    @Override
    public int compareTo(Object product) {
        if (this.getDiscountPrice() == null) {
            return Comparator.comparing(Product::getProductCode)
                    .thenComparing(Product::getProductName)
                    .thenComparing(Product::getQty)
                    .thenComparing(Product::getPrice)
                    .compare(this, (Product) product);
        } else {
            return Comparator.comparing(Product::getProductCode)
                    .thenComparing(Product::getProductName)
                    .thenComparing(Product::getQty)
                    .thenComparing(Product::getPrice)
                    .thenComparing(Product::getDiscountPrice)
                    .thenComparing(Product::getPromotionalText)
                    .compare(this, (Product) product);
        }
    }

}
