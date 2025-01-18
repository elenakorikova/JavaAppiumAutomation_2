package lib;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import junit.framework.TestCase;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.URL;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public class CoreTestCase extends TestCase {

    private static final String PLATFORM_IOS = "ios";
    private static final String PLATFORM_ANDROID = "android";

    protected AppiumDriver driver;
    private static String AppiumURL = "http://127.0.0.1:4723";

    @Override
    protected void setUp() throws Exception {

        super.setUp();

        DesiredCapabilities capabilities = this.getCapabilitiesByPlatformEnv();
        driver = new AndroidDriver(new URL(AppiumURL), capabilities);
        this.rotateScreenPortrait();

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

    protected void rotateScreenPortrait() {
        driver.rotate(ScreenOrientation.PORTRAIT);
    }

    protected void rotateScreenLandscape() {
        driver.rotate(ScreenOrientation.LANDSCAPE);
    }

    protected void backgroundApp(int seconds) {
        driver.runAppInBackground(seconds);
    }

    private DesiredCapabilities getCapabilitiesByPlatformEnv() throws Exception {
        String platform = System.getenv("PLATFORM");
        DesiredCapabilities capabilities = new DesiredCapabilities();

        if (platform.equals(PLATFORM_ANDROID)) {
            capabilities.setCapability("platformName", "Android");
            capabilities.setCapability("deviceName", "Google_Nexus_5");
            capabilities.setCapability("platformVersion", "11");
            capabilities.setCapability("appium:automationName", "UiAutomator2");
            capabilities.setCapability("appPackage", "org.wikipedia");
            capabilities.setCapability("appActivity", ".main.MainActivity");
            capabilities.setCapability("app", "/Users/elenakorikova/Desktop/JavaAppiumAutomation/JavaAppiumAutomation_2/JavaAppiumAutomation_2/apks/org.wikipedia_50467_apps.evozi.com.apk");
        } else if (platform.equals(PLATFORM_IOS)) {
            capabilities.setCapability("platformName", "iOS");
            capabilities.setCapability("deviceName", "iPhone 16 Pro");
            capabilities.setCapability("platformVersion", "18.2");
            capabilities.setCapability("appium:automationName", "XCUITest");
            capabilities.setCapability("app", "/Users/elenakorikova/Desktop/JavaAppiumAutomation/JavaAppiumAutomation_2/JavaAppiumAutomation_2/apks/Wikipedia.app");
            capabilities.setCapability("bundleId", "org.wikimedia.wikipedia");
        } else {
            throw new Exception("Cannot get run platform from env variable. Platform value " + platform);
        }
        return capabilities;
    }
}
