package vitusapotek;

import cucumber.api.CucumberOptions;
import net.serenitybdd.cucumber.CucumberWithSerenity;
import org.junit.runner.RunWith;

@RunWith(CucumberWithSerenity.class)
@CucumberOptions(features={"src/test/resources/features/1_CreateProfile.feature",
                           "src/test/resources/features/2_Checkout.feature",
                           "src/test/resources/features/3_ShoppingCart.feature",
                           "src/test/resources/features/4_Products.feature",
                           "src/test/resources/features/5_ProductDetails.feature",
                           "src/test/resources/features/6_MyPrescription.feature"}, tags = {"~@ignore"})
public class DefinitionTestSuiteAll {}
