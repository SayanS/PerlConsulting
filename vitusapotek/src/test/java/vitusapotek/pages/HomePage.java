package vitusapotek.pages;

public class HomePage extends BasePage {
    public void openHomePage(String baseUrl) {
        getDriver().navigate().to(baseUrl);
    }
}