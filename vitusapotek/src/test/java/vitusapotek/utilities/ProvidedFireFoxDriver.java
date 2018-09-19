package vitusapotek.utilities;

import net.thucydides.core.webdriver.DriverSource;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;

public class ProvidedFireFoxDriver implements DriverSource {
    public static WebDriver webDriver;

    @Override
    public WebDriver newDriver() {

        String pathToFile = "/src/test/resources/download";

        FirefoxProfile profile = new FirefoxProfile();
        DesiredCapabilities dc = DesiredCapabilities.firefox();
        profile.setAcceptUntrustedCertificates(true);
        profile.setAssumeUntrustedCertificateIssuer(true);
        profile.setPreference("browser.download.folderList", 2);
        profile.setPreference("browser.helperApps.alwaysAsk.force", false);
        profile.setPreference("browser.download.manager.showWhenStarting", false);
        profile.setPreference("browser.download.dir", System.getProperty("user.dir") + new File(pathToFile));
        profile.setPreference("browser.download.downloadDir", System.getProperty("user.dir") + new File(pathToFile));
        profile.setPreference("browser.download.defaultFolder", System.getProperty("user.dir") + new File(pathToFile));
        profile.setPreference("browser.helperApps.neverAsk.saveToDisk", "text/anytext ,text/plain,text/html,application/plain, image/jpeg, image/jpg");
        dc.setCapability(FirefoxDriver.PROFILE, profile);
        webDriver = new FirefoxDriver(dc);
        webDriver.manage().window().maximize();
        return webDriver;
    }

    public void getIgnoreAlert() {
        DesiredCapabilities caps = DesiredCapabilities.chrome();
        caps.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.ACCEPT);
    }

    @Override
    public boolean takesScreenshots() {
        return false;
    }
}
