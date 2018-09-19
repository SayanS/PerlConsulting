package lloydsPharmaProject.pages;

import java.io.IOException;

public class SearchResultsPage extends BasePage {
    String SEARCH_RESULTS_ITEM = ".//a[contains(@href,'$ProductCode')]/ancestor::article[@class='product-item']";

    public void addToCartProduct(String productCode) throws IOException {
        scrollIntoView(SEARCH_RESULTS_ITEM.replace("$ProductCode", productCode),-50);
        moveTo(SEARCH_RESULTS_ITEM.replace("$ProductCode", productCode));
        $(SEARCH_RESULTS_ITEM.replace("$ProductCode", productCode)+"//button[@type='submit']").click();
    }


}
