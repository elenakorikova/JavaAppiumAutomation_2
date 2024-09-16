import com.google.common.collect.ImmutableList;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;

import java.net.URL;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
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
                "Java",
                "Cannot find search input",
                5
        );

        waitForElementAndClear(
                By.id("org.wikipedia:id/search_src_text"),
                "Cannot find search field",
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

    @Test

    public void testCompareArticleTitle()
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

        waitForElementAndClick(
                By.id("org.wikipedia:id/page_list_item_description"),
                "Cannot find Search Wikipedia input",
                5
        );

        waitForElementAndClick(
                By.id("org.wikipedia:id/page_contents"),
                "Cannot find this button",
                5
        );

        WebElement title_element = waitForElementPresent(
                By.id("org.wikipedia:id/page_toc_item_text"),
                "Cannot find article title",
                15
        );

        String article_title = title_element.getText();
        Assert.assertEquals(
                "We see unexpected title!",
                "Java (programming language)",
                article_title
        );
    }

    @Test

    public void testSearchPlaceholderPresent() {

        waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find Search input field",
                5
        );

        WebElement title_element = waitForElementPresent(
                By.id("org.wikipedia:id/search_src_text"),
                "Cannot find placeholder in search field",
                5
        );

        String placeholder = title_element.getText();
        Assert.assertEquals(
                "We see unexpected placeholder!",
                "Search Wikipedia",
                placeholder
        );
    }

    @Test

    public void testSearchAndCancelSearchOfArticles() {

        waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find Search input field",
                5
        );

        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Baikal",
                "Cannot find search input",
                5

        );

        List<WebElement> articles = driver.findElements(By.id("org.wikipedia:id/page_list_item_title"));
        Assert.assertTrue("Less than 2 articles found!", articles.size() > 1);

        waitForElementAndClick(
                By.id("org.wikipedia:id/search_close_btn"),
                "Cannot find X to cancel search",
                5
        );

        boolean elementNotPresent = isElementNotPresent(
                By.id("org.wikipedia:id/search_close_btn"));
        assertTrue("X is still present on the page", elementNotPresent);

        List<WebElement> articlesAfterCancel = driver.findElements(By.id("org.wikipedia:id/page_list_item_title"));
        Assert.assertTrue(
                "Articles are still displayed after search is canceled!",
                articlesAfterCancel.size() == 0
        );
    }

    @Test

    public void testSearchOfCorrectArticles() {

        waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find Search input field",
                5
        );

        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Angarsk",
                "Cannot find search input",
                5

        );

        List<WebElement> articles = driver.findElements(By.id("org.wikipedia:id/page_list_item_title"));
        Assert.assertTrue("No articles found!", articles.size() > 0);
        WebElement title_article = waitForElementPresent(
                By.id("org.wikipedia:id/page_list_item_title"),
                "Cannot find article title",
                20
        );
        for (WebElement article : articles) {
            String article_title = title_article.getText();
            Assert.assertTrue(
                    "The article title does not contain the expected keyword!" + article_title,
                    article_title.contains("Angarsk")
            );
        }
    }

    @Test

    public void testSwipeArticle()
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

        waitForElementAndClick(
                By.id("org.wikipedia:id/page_list_item_description"),
                "Cannot find Search Wikipedia input",
                5
        );

        Dimension size = driver.manage().window().getSize();
        int startX = size.width / 2;
        int startY = (int) (size.height * 0.8);
        int endY = (int) (size.height * 0.2);
        Point startPoint = new Point(startX, startY);
        Point endPoint = new Point(startX, endY);

        swipeUp(startPoint, endPoint, Duration.ofMillis(1000));
        swipeUp(startPoint, endPoint, Duration.ofMillis(1000));
        swipeUp(startPoint, endPoint, Duration.ofMillis(1000));
        swipeUp(startPoint, endPoint, Duration.ofMillis(1000));
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

    private WebElement waitForElementAndClear(By by, String error_message, long timeOutInSeconds) {
        WebElement element = waitForElementPresent(by, error_message, timeOutInSeconds);
        element.clear();
        return element;
    }

        protected void swipeUp(Point start, Point end, Duration duration) {
        PointerInput input = new PointerInput(PointerInput.Kind.TOUCH,"finger1");
        Sequence swipe = new Sequence(input, 0);
        swipe.addAction(input.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), start.x, start.y));
        swipe.addAction(input.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        swipe.addAction(input.createPointerMove(duration, PointerInput.Origin.viewport(), end.x, end.y));
        swipe.addAction(input.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
        driver.perform(ImmutableList.of(swipe));
    }
}

