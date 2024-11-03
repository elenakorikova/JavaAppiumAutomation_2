import lib.CoreTestCase;
import lib.ui.*;
import org.junit.Test;
import org.openqa.selenium.*;
import java.util.List;

public class FirstTest extends CoreTestCase {

    private MainPageObject MainPageObject;

    protected void setUp() throws Exception {
        super.setUp();

        MainPageObject = new MainPageObject(driver);

    }

    @Test

    public void testSearchAndCancelSearchOfArticles() {

        MainPageObject.waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find Search input field",
                5
        );

        MainPageObject.waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Baikal",
                "Cannot find search input",
                5

        );

        List<WebElement> articles = driver.findElements(By.id("org.wikipedia:id/page_list_item_title"));
       assertTrue("Less than 2 articles found!", articles.size() > 1);

        MainPageObject.waitForElementAndClick(
                By.id("org.wikipedia:id/search_close_btn"),
                "Cannot find X to cancel search",
                5
        );

        boolean elementNotPresent = MainPageObject.isElementNotPresent(
                By.id("org.wikipedia:id/search_close_btn"));
        assertTrue("X is still present on the page", elementNotPresent);

        List<WebElement> articlesAfterCancel = driver.findElements(By.id("org.wikipedia:id/page_list_item_title"));
       assertTrue(
                "Articles are still displayed after search is canceled!",
                articlesAfterCancel.size() == 0
        );
    }

    @Test

    public void testSearchOfCorrectArticles() {

        MainPageObject.waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find Search input field",
                5
        );

        MainPageObject.waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Angarsk",
                "Cannot find search input",
                5

        );

        List<WebElement> articles = driver.findElements(By.id("org.wikipedia:id/page_list_item_title"));
        assertTrue("No articles found!", articles.size() > 0);
        WebElement title_article = MainPageObject.waitForElementPresent(
                By.id("org.wikipedia:id/page_list_item_title"),
                "Cannot find article title",
                20
        );
        for (WebElement article : articles) {
            String article_title = title_article.getText();
            assertTrue(
                    "The article title does not contain the expected keyword!" + article_title,
                    article_title.contains("Angarsk")
            );
        }
    }

    @Test

    public void testSaveTwoArticlesInOneFolder() {

        MainPageObject.waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find Search Wikipedia input",
                5
        );

        MainPageObject.waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Java",
                "Cannot find search input",
                5
        );

        MainPageObject.waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Java (programming language)')]"),
                "Cannot find Java article",
                5
        );

        MainPageObject.waitForElementAndClick(
                By.id("org.wikipedia:id/page_toolbar_button_show_overflow_menu"),
                "Cannot find button to open article options",
                5
        );

        MainPageObject.waitForElementAndClick(
                By.id("org.wikipedia:id/page_save"),
                "Cannot find option to save article",
                5
        );

        MainPageObject.waitForElementAndClick(
                By.id("org.wikipedia:id/snackbar_action"),
                "Cannot find button Add to list",
                5
        );

        MainPageObject.closeBottomSheetByClickingOutside();

        String name_of_folder = "Learning programming";

        MainPageObject.waitForElementAndSendKeys(
                By.id("org.wikipedia:id/text_input"),
                name_of_folder,
                "Cannot put text into articles folder input",
                5
        );

        MainPageObject.waitForElementAndClick(
                By.id("android:id/button1"),
                "Cannot press OK button",
                5
        );

        MainPageObject.waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find Search Wikipedia input",
                5
        );

        MainPageObject.waitForElementAndSendKeys(
                By.id("org.wikipedia:id/search_src_text"),
                "Python",
                "Cannot find search input",
                5
        );

        MainPageObject.waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Python (programming language)')]"),
                "Cannot find Search Wikipedia input",
                5
        );

        MainPageObject.waitForElementAndClick(
                By.id("org.wikipedia:id/page_toolbar_button_show_overflow_menu"),
                "Cannot find button to open article options",
                5
        );

        MainPageObject.waitForElementAndClick(
                By.id("org.wikipedia:id/page_save"),
                "Cannot find option to save article",
                5
        );

        MainPageObject.waitForElementAndClick(
                By.id("org.wikipedia:id/snackbar_action"),
                "Cannot find button Add to list",
                5
        );

        MainPageObject.waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Learning programming')]"),
                "Cannot find Search Wikipedia input",
                5
        );

        MainPageObject.waitForElementAndClick(
                By.id("org.wikipedia:id/snackbar_action"),
                "Cannot find 'View list' button",
                5
        );

        MainPageObject.waitForElementPresent(
                By.xpath("//*[contains(@text, 'Python (programming language)')]"),
                "Cannot find the second article",
                5
        );

        MainPageObject.swipeElementToLeft(
                By.xpath("//*[contains(@text, 'Python (programming language)')]"),
                "Cannot swipe the article"
        );

        MainPageObject.waitForElementPresent(
                By.xpath("//*[contains(@text, 'Java (programming language)')]"),
                "Cannot find the second article",
                5
        );

        String title_in_the_list = MainPageObject.waitForElementAndGetText(
                By.xpath("//*[contains(@text, 'Java (programming language)')]"),
                "Cannot find title of article",
                15
        );

        MainPageObject.waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Java (programming language)')]"),
                "Cannot choose the second article",
                5
        );

        String title_in_the_article = MainPageObject.waitForElementAndGetText(
                By.xpath("//*[contains(@text, 'Java (programming language)')]"),
                "Cannot find title of article",
                15
        );

        assertEquals(
                "Article title have been changed",
                title_in_the_list,
                title_in_the_article
        );
    }

}