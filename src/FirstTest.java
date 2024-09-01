import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;



import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertTrue;

public class FirstTest {

    private AppiumDriver driver;

    @Before
    public void setUp() throws Exception {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("deviceName", "Google_Nexus_5");
        capabilities.setCapability("platformVersion", "11");
        capabilities.setCapability("appium:automationName", "UiAutomator2");
        capabilities.setCapability("appPackage", "org.wikipedia");
        capabilities.setCapability("appActivity", ".main.MainActivity");
        capabilities.setCapability("app", "/Users/elenakorikova/Desktop/JavaAppiumAutomation/JavaAppiumAutomation_2/JavaAppiumAutomation_2/apks/org.wikipedia_50467_apps.evozi.com.apk");

        driver = new AndroidDriver(new URL("http://127.0.0.1:4723"), capabilities);
        if (driver != null) {
        Map<String, Object> params = new HashMap<>();
        params.put("appId", "org.wikipedia");
        driver.executeScript("mobile: terminateApp", params);

        driver.executeScript("mobile: activateApp", params);
        Thread.sleep(5000);
    }
    }

    @After
    public void tearDown() {
        if (driver != null) {
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

    @Test
    public void firstTest()
    {
        waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find Search Wikipedia input",
                5
        );

        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Java",
                "Cannot find search input",
                5

        );

        waitForElementPresent(
                By.xpath("//*[@bounds='[0,488][1080,712]']//*[@text='Object-oriented programming language']"),
                "Cannot find 'Object-oriented programming language' topic search by Java",
                15
        );
    }

    @Test

    public void testCancelSearch() {
        waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find Search Wikipedia input",
                5
        );

        waitForElementAndSendKeys(
                By.id("org.wikipedia:id/search_src_text"),
                "J",
                "Cannot find search input",
                5
        );

        waitForElementAndClick(
                By.id("org.wikipedia:id/search_close_btn"),
                "Cannot find X to cancel search",
                5
        );

        boolean elementNotPresent = isElementNotPresent(
                By.id("org.wikipedia:id/search_close_btn"));
        assertTrue("X is still present on the page", elementNotPresent);
    }

    private WebElement waitForElementPresent(By by, String error_message, long timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(error_message + "\n");
        return wait.until(
                ExpectedConditions.presenceOfElementLocated(by)
        );
    }

    private WebElement waitForElementPresent(By by, String error_message) {
        return waitForElementPresent(by, error_message, 5);
    }

    private WebElement waitForElementAndClick(By by, String error_message, long timeOutInSeconds) {
        WebElement element = waitForElementPresent(by, error_message, timeOutInSeconds);
        element.click();
        return element;
    }

    private WebElement waitForElementAndSendKeys(By by, String value, String error_message, long timeOutInSeconds) {
        WebElement element = waitForElementPresent(by, error_message, timeOutInSeconds);
        element.sendKeys(value);
        return element;
    }

    private boolean isElementNotPresent(By by) {
        try {
            driver.findElement(by);
            return false;
        } catch (org.openqa.selenium.NoSuchElementException e) {
            return true;
        }
    }
}

