import com.google.common.collect.ImmutableList;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.touch.LongPressOptions;
import io.appium.java_client.touch.offset.ElementOption;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Interaction;
import org.openqa.selenium.interactions.Pause;
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
import java.util.NoSuchElementException;

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
                "Appium",
                "Cannot find search input",
                5

        );

        waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_title'][@text='Appium']"),
                "Cannot find Appium article in search",
                5
        );

        Dimension size = driver.manage().window().getSize();
        int startX = size.width / 2;
        int startY = (int) (size.height * 0.8);
        int endY = (int) (size.height * 0.2);
        Point startPoint = new Point(startX, startY);
        Point endPoint = new Point(startX, endY);

        swipeUpToFindElement(
                By.xpath("//*[@text='View article in browser']"),
                "Cannot find the end of the article",
                20
        );
    }

    @Test

    public void saveFirstArticleToMyList(){

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
                By.id("org.wikipedia:id/page_toolbar_button_show_overflow_menu"),
                "Cannot find button to open article options",
                5
        );

        waitForElementAndClick(
                By.id("org.wikipedia:id/page_save"),
                "Cannot find option to save article",
                5
        );

        waitForElementAndClick(
                By.id("org.wikipedia:id/snackbar_action"),
                "Cannot find button Add to list",
                5
        );

        closeBottomSheetByClickingOutside();

        String name_of_folder = "Learning programming";

        waitForElementAndSendKeys(
                By.id("org.wikipedia:id/text_input"),
                name_of_folder,
                "Cannot put text into articles folder input",
                5
        );

        waitForElementAndClick(
                By.id("android:id/button1"),
                "Cannot press OK button",
                5
        );

        waitForElementAndClick(
                By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']"),
                "Cannot close article, cannot find back button",
                5
        );

        if (isElementPresent(By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']"))) {
            waitForElementAndClick(
                    By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']"),
                    "Cannot close search, cannot find back button",
                    5
            );
        }

        waitForElementAndClick(
                By.xpath("//android.widget.FrameLayout[@content-desc='Saved']"),
                "Cannot find navigation button to saved articles",
                5
        );

        waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/item_title'][@text='"+ name_of_folder +"']"),
                "Cannot find created folder",
                5
        );

        waitForElementAndClick(
                By.xpath("//android.widget.ImageView[@content-desc='More options']"),
                "Cannot find three dots button",
                5
        );

        waitForElementAndClick(
                By.xpath("//android.widget.TextView[@text='Delete list']"),
                "Cannot find delete action",
                5
        );

        waitForElementAndClick(
                By.id("android:id/button1"),
                "Cannot press OK button",
                5
        );

        waitForElementNotPresent(
                By.xpath("//*[@resource-id='org.wikipedia:id/item_title'][@text='Learning programming']"),
                "Cannot delete folder",
                5
        );

    }

    @Test

    public void testAmountOfNotEmptySearch() {

        waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find Search Wikipedia input",
                5
        );

        String search_line = "Linkin Park Discography";

        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                search_line,
                "Cannot find search input",
                5
        );

        String search_result_locator = "//*[@resource-id='org.wikipedia:id/search_results_list']//android.widget.TextView[@text='Linkin Park discography']";

        waitForElementPresent(
                By.xpath(search_result_locator),
                "Cannot find anything by the request " + search_line,
                15
        );

        int amount_of_search_result = getAmountOfElements(
                By.xpath(search_result_locator)
        );

        Assert.assertTrue(
                "We found too few results!",
                amount_of_search_result > 0
        );

    }

    @Test

    public void testAmountOfEmptySearch() {

        waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find Search Wikipedia input",
                5
        );

        String search_line = "nnsdjknsjds";

        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                search_line,
                "Cannot find search input",
                5
        );

        String search_result_locator = "//*[@resource-id='org.wikipedia:id/search_results_list']//android.widget.TextView[@text != 'No results']";
        String empty_result_label = "//*[@text='No results']";

        waitForElementPresent(
                By.xpath(empty_result_label),
                "Cannot find empty result label by the request" + search_line,
                15
        );

        assertElemetNotPresent(
                By.xpath(search_result_locator),
                "We've found some results by request " + search_line
        );

    }

    private void waitForElementNotPresent(By by, String error_message, long timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        boolean isNotPresent = wait.until(ExpectedConditions.invisibilityOfElementLocated(by));

        if (!isNotPresent) {
            throw new AssertionError(error_message);
        }
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

    protected void swipeUpQuick() {
        Dimension size = driver.manage().window().getSize();
        int startX = size.width / 2;
        int startY = (int) (size.height * 0.8);
        int endY = (int) (size.height * 0.2);
        Point startPoint = new Point(startX, startY);
        Point endPoint = new Point(startX, endY);

        swipeUp(startPoint, endPoint, Duration.ofMillis(200));
    }

    protected void swipeUpToFindElement(By by, String error_message, int max_swipes) {
        int already_swiped = 0;
        while (driver.findElements(by).size() == 0) {

            if(already_swiped > max_swipes) {
                waitForElementPresent(by,"Cannot find element by swiping up.\n" + error_message, 0);
                return;
            }

            swipeUpQuick();
            ++already_swiped;
        }

    }

//    protected void longPress(AndroidDriver driver, WebElement element) {
//        Point location = element.getLocation();
//        Dimension size = element.getSize();
//
//        Point centerOfElement = getCenterOfElement(location, size);
//
//        PointerInput finger1 = new PointerInput(PointerInput.Kind.TOUCH, "finger1");
//        Sequence sequence = new Sequence(finger1, 1);
//        sequence.addAction(finger1.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), centerOfElement.x, centerOfElement.y));
//        sequence.addAction(finger1.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
//        sequence.addAction(new Pause(finger1, Duration.ofSeconds(2)));
//        sequence.addAction(finger1.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
//
//        driver.perform(ImmutableList.of(sequence));
//    }

//    private Point getCenterOfElement(Point location, Dimension size) {
//        int centerX = location.x + size.width / 2;
//        int centerY = location.y + size.height / 2;
//        return new Point(centerX, centerY);
//    }

    private void closeBottomSheetByClickingOutside() {
        try {
            WebElement bottomSheetTitle = driver.findElement(By.id("org.wikipedia:id/dialog_title"));

            if (bottomSheetTitle.getText().equals("Move to reading list")) {
                System.out.println("Bottom sheet 'Move to reading list' is present.");

                Point sheetLocation = bottomSheetTitle.getLocation();

                int clickX = sheetLocation.getX() + 10;
                int clickY = sheetLocation.getY() - 50;

                PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger1");
                Sequence tapSequence = new Sequence(finger, 0);

                tapSequence.addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), clickX, clickY));

                tapSequence.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));

                tapSequence.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

                driver.perform(ImmutableList.of(tapSequence));

                System.out.println("Tapped outside bottom sheet to close it.");
            }
        } catch (org.openqa.selenium.NoSuchElementException e) {
            System.out.println("Bottom sheet is not present.");
        }
    }

    public boolean isElementPresent(By by) {
        try {
            driver.findElement(by);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    private int getAmountOfElements(By by) {
        List elements = driver.findElements(by);
        return elements.size();
    }

    private void assertElemetNotPresent(By by, String error_message) {
        int amount_of_elements = getAmountOfElements(by);
        if (amount_of_elements > 0) {
            String default_message = "An element '" + by.toString() + "' supposed to be not present";
            throw new AssertionError(default_message + " " + error_message);
        }
    }
}

