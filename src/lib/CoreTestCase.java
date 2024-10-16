package lib;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import junit.framework.TestCase;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class CoreTestCase extends TestCase {

    protected AppiumDriver driver;
    private static String AppiumURL = "http://127.0.0.1:4723";

    @Override
    protected void setUp() throws Exception {

        super.setUp();

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("deviceName", "Google_Nexus_5");
        capabilities.setCapability("platformVersion", "11");
        capabilities.setCapability("appium:automationName", "UiAutomator2");
        capabilities.setCapability("appPackage", "org.wikipedia");
        capabilities.setCapability("appActivity", ".main.MainActivity");
        capabilities.setCapability("app", "/Users/elenakorikova/Desktop/JavaAppiumAutomation/JavaAppiumAutomation_2/JavaAppiumAutomation_2/apks/org.wikipedia_50467_apps.evozi.com.apk");

        driver = new AndroidDriver(new URL(AppiumURL), capabilities);
        if (driver != null) {
            Map<String, Object> params = new HashMap<>();
            params.put("appId", "org.wikipedia");
            driver.executeScript("mobile: terminateApp", params);

            driver.executeScript("mobile: activateApp", params);
            Thread.sleep(5000);
        }
    }

    @Override
    protected void tearDown() throws Exception {

        super.tearDown();

        if (driver != null) {

            try {
                driver.rotate(ScreenOrientation.PORTRAIT);
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                driver.hideKeyboard();
            } catch (Exception e) {
                e.printStackTrace();
            }
            Map<String, Object> params = new HashMap<>();
            params.put("appId", "org.wikipedia");
            driver.executeScript("mobile: terminateApp", params);
            driver.quit();
        }
    }
}