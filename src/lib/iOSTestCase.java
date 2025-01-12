package lib;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.ios.IOSDriver;
import junit.framework.TestCase;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class iOSTestCase extends TestCase {

    protected AppiumDriver driver;
    private static final String AppiumURL = "http://127.0.0.1:4723";

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platformName", "iOS");
        capabilities.setCapability("deviceName", "iPhone 16 Pro");
        capabilities.setCapability("platformVersion", "18.2");
        capabilities.setCapability("appium:automationName", "XCUITest");
        capabilities.setCapability("app", "/Users/elenakorikova/Desktop/JavaAppiumAutomation/JavaAppiumAutomation_2/JavaAppiumAutomation_2/apks/Wikipedia.app");
        capabilities.setCapability("bundleId", "org.wikimedia.wikipedia");
        driver = new IOSDriver(new URL(AppiumURL), capabilities);

        // Запуск приложения
        Map<String, Object> params = new HashMap<>();
        params.put("bundleId", "org.wikimedia.wikipedia");
        driver.executeScript("mobile: activateApp", params);
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();

        if (driver != null) {
            // Завершаем приложение
            Map<String, Object> params = new HashMap<>();
            params.put("bundleId", "org.wikimedia.wikipedia");
            driver.executeScript("mobile: terminateApp", params);

            driver.quit();
        }
    }
}
