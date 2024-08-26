import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URL;

public class FirstTest {

    private AppiumDriver driver;

    @Before
    public void setUp() throws Exception
    {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platformName","Android");
        capabilities.setCapability("deviceName","Google_Nexus_5");
        capabilities.setCapability("platformVersion","11");
        capabilities.setCapability("appium:automationName","UiAutomator2");
        capabilities.setCapability("appPackage","org.wikipedia");
        capabilities.setCapability("appActivity",".main.MainActivity");
        capabilities.setCapability("app","/Users/elenakorikova/Desktop/JavaAppiumAutomation/JavaAppiumAutomation_2/JavaAppiumAutomation_2/apks/org.wikipedia_50467_apps.evozi.com.apk");

        driver = new AndroidDriver(new URL("http://127.0.0.1:4723"), capabilities);
    }
    @After
    public void tearDown()
    {
        driver.quit();
    }

    @Test
    public void firstTest()
    {
        WebElement element_to_init_search = driver.findElementByXPath("//*[contains(@text, 'Search Wikipedia')]");
        element_to_init_search.click();

        WebElement element_to_enter_search_line = waitForElementPresentByXpath(
             "//*[contains(@text, 'Search Wikipedia')]",
                "Cannot find search input",
                5
        );
        element_to_enter_search_line.sendKeys("Appium");
    }

    private WebElement waitForElementPresentByXpath(String xpath, String error_message, long timeoutInSeconds)
    {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(error_message + "\n");
        By by = By.xpath(xpath);
        return wait.until(
                ExpectedConditions.presenceOfElementLocated(by)
        );
    }
}
