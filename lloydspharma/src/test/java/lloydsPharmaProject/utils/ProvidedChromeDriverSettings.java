package lloydsPharmaProject.utils;

import net.thucydides.core.webdriver.DriverSource;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.util.HashMap;

public class ProvidedChromeDriverSettings implements DriverSource {

    @Override
    public WebDriver newDriver() {
        String pathToFile = "/src/test/resources/download";

        HashMap<String, Object> chromePrefs = new HashMap<>();
        chromePrefs.put("profile.default_content_settings.popups", 0);
        chromePrefs.put("download.default_directory", System.getProperty("user.dir") + new File(pathToFile));
        ChromeOptions options = new ChromeOptions();
        options.setExperimentalOption("prefs", chromePrefs);
//        options.addArguments("--incognito");
        options.addArguments("--start-maximized");
//        options.addArguments("ignore-certificate-errors");
// 2 next lines for headless mode
//         options.addArguments("--headless");
//         options.addArguments("--disable-gpu");

        options.addArguments("window-size=1920,1080");
        DesiredCapabilities cap = DesiredCapabilities.chrome();
        cap.setCapability(ChromeOptions.CAPABILITY, options);

        return new ChromeDriver(cap);
    }

    @Override
    public boolean takesScreenshots() {
        return false;
    }
}
