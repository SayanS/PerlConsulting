package lloydsPharmaProject;

import cucumber.api.CucumberOptions;
import net.serenitybdd.cucumber.CucumberWithSerenity;
import org.junit.runner.RunWith;

@RunWith(CucumberWithSerenity.class)
@CucumberOptions(features={"src/test/resources/features/1_CreateProfile.feature"}, tags = {"~@ignore"})

public class DefinitionTestSuiteCreateProfile {
}
