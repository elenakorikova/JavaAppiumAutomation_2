import lib.CoreTestCase;
import lib.ui.*;
import org.junit.Assert;
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
    public void testSearch() {

        SearchPageObject SearchPageObject = new SearchPageObject(driver);

        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Java");
        SearchPageObject.waitForSearchResult("Object-oriented programming language");
    }

    @Test

    public void testCancelSearch() {

        SearchPageObject SearchPageObject = new SearchPageObject(driver);

        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Java");
        SearchPageObject.waitForCancelButtonToAppear();
        SearchPageObject.clickCancelSearch();
        SearchPageObject.waitForCancelButtonToDisppear();
    }

    @Test

    public void testCompareArticleTitle() {

        SearchPageObject SearchPageObject = new SearchPageObject(driver);

        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Java");
        SearchPageObject.clickByArticleWithSubString("Object-oriented programming language");

        ArticlePageObject ArticlePageObject = new ArticlePageObject(driver);
        ArticlePageObject.clickButtonOnTabbar();
        String article_title = ArticlePageObject.getArticleTitle();

        Assert.assertEquals(
                "We see unexpected title!",
                "Java (programming language)",
                article_title
        );
    }

    @Test

    public void testSearchPlaceholderPresent() {

        MainPageObject.waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find Search input field",
                5
        );

        WebElement title_element = MainPageObject.waitForElementPresent(
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
        Assert.assertTrue("Less than 2 articles found!", articles.size() > 1);

        MainPageObject.waitForElementAndClick(
                By.id("org.wikipedia:id/search_close_btn"),
                "Cannot find X to cancel search",
                5
        );

        boolean elementNotPresent = MainPageObject.isElementNotPresent(
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
        Assert.assertTrue("No articles found!", articles.size() > 0);
        WebElement title_article = MainPageObject.waitForElementPresent(
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

    public void testSwipeArticle() {

        SearchPageObject SearchPageObject = new SearchPageObject(driver);

        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Appium");
        SearchPageObject.clickByArticleWithSubString("Automation for Apps");

        ArticlePageObject ArticlePageObject = new ArticlePageObject(driver);
        ArticlePageObject.clickButtonOnTabbar();
        ArticlePageObject.waitForTitleElement();
        ArticlePageObject.clickOnTitle();

        ArticlePageObject.swipeToFooter();
    }

    @Test

    public void testSaveFirstArticleToMyList() {

        SearchPageObject SearchPageObject = new SearchPageObject(driver);

        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Java");
        SearchPageObject.clickByArticleWithSubString("Object-oriented programming language");

        ArticlePageObject ArticlePageObject = new ArticlePageObject(driver);
        ArticlePageObject.clickButtonOnTabbar();
        ArticlePageObject.waitForTitleElement();
        String article_title = ArticlePageObject.getArticleTitle();
        ArticlePageObject.clickBackButton();
        String name_of_folder = "Learning programming";
        ArticlePageObject.addArticleToMyList(name_of_folder);
        ArticlePageObject.closeArticle();

        NavigationUI NavigationUI = new NavigationUI(driver);
        NavigationUI.clickSaved();

        MyListsPageObject MyListsPageObject = new MyListsPageObject(driver);
        MyListsPageObject.openFolderByName(name_of_folder);
        MyListsPageObject.swipeByArticleToDelete(article_title);
    }

    @Test

    public void testAmountOfNotEmptySearch() {

        SearchPageObject SearchPageObject = new SearchPageObject(driver);

        SearchPageObject.initSearchInput();
        String search_line = "Linkin Park Discography";
        SearchPageObject.typeSearchLine(search_line);
        int amount_of_search_result = SearchPageObject.getAmountOfFoundArticles();

        Assert.assertTrue(
                "We found too few results!",
                amount_of_search_result > 0
        );

    }

    @Test

    public void testAmountOfEmptySearch() {

        SearchPageObject SearchPageObject = new SearchPageObject(driver);

        SearchPageObject.initSearchInput();
        String search_line = "nnsdjknsjds";
        SearchPageObject.typeSearchLine(search_line);
        SearchPageObject.waitForEmptyResultsLabel();
        SearchPageObject.assertThereIsNoResultOfSearch();
    }

    @Test

    public void testChangeScreenOrientationOnSearchResults() {

        MainPageObject.waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find Search Wikipedia input",
                5
        );

        String search_line = "Java";

        MainPageObject.waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                search_line,
                "Cannot find search input",
                5
        );

        MainPageObject.waitForElementAndClick(
                By.id("org.wikipedia:id/page_list_item_description"),
                "Cannot find Search Wikipedia input",
                15
        );

        String tittle_before_rotation = MainPageObject.waitForElementAndGetText(
                By.xpath("//*[contains(@text, 'Java (programming language)')]"),
                "Cannot find title of article",
                15
        );

        driver.rotate(ScreenOrientation.LANDSCAPE);

        String tittle_after_rotation = MainPageObject.waitForElementAndGetText(
                By.xpath("//*[contains(@text, 'Java (programming language)')]"),
                "Cannot find title of article",
                15
        );

        Assert.assertEquals(
                "Article title have been changed after screen rotation",
                tittle_before_rotation,
                tittle_after_rotation
        );

        driver.rotate(ScreenOrientation.PORTRAIT);

        String tittle_after_second_rotation = MainPageObject.waitForElementAndGetText(
                By.xpath("//*[contains(@text, 'Java (programming language)')]"),
                "Cannot find title of article",
                15
        );

        Assert.assertEquals(
                "Article title have been changed after screen rotation",
                tittle_before_rotation,
                tittle_after_second_rotation
        );
    }

    @Test

    public void testCheckSearchArticleInBackground() {

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

        MainPageObject.waitForElementPresent(
                By.id("org.wikipedia:id/page_list_item_description"),
                "Cannot find Search Wikipedia input",
                5
        );

        driver.runAppInBackground(2);

        MainPageObject.waitForElementPresent(
                By.id("org.wikipedia:id/page_list_item_description"),
                "Cannot find article after returning from background",
                5
        );

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

        Assert.assertEquals(
                "Article title have been changed",
                title_in_the_list,
                title_in_the_article
        );
    }

    @Test

    public void testArticleHaveTitle() {

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
                By.id("org.wikipedia:id/page_contents"),
                "Cannot find Contents button",
                5
        );

        MainPageObject.assertElementPresent(
                By.id("org.wikipedia:id/page_toc_item_text"),
                "Элемент заголовка не найден на странице"
        );

    }
}