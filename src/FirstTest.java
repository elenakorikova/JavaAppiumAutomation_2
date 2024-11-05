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

}