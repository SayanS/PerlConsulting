package lloydsPharmaProject;

import cucumber.api.CucumberOptions;
import net.serenitybdd.cucumber.CucumberWithSerenity;
import org.junit.runner.RunWith;

@RunWith(CucumberWithSerenity.class)
@CucumberOptions(features={"src/test/resources/features/1_CreateProfile.feature",
                           "src/test/resources/features/2_Checkout.feature",
                           "src/test/resources/features/3_MyPrescription.feature",
                           "src/test/resources/features/4_ProductDetailsPage.feature",
                           "src/test/resources/features/5_ProductsPage.feature",
                           "src/test/resources/features/6_ShoppingCart.feature",
                           "src/test/resources/features/7_BackOfficeOrders.feature"}, tags = {"~@ignore"} )

public class DefinitionTestSuiteAll {}
