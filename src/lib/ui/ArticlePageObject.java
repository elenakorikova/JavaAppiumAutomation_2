package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class ArticlePageObject extends MainPageObject {

    private static final String
        TITLE = "org.wikipedia:id/page_toc_item_text",
        CONTENT_BUTTON = "org.wikipedia:id/page_contents",
        FOOTER_ELEMENT = "//*[@text='View article in browser']",
        OPTIONS_BUTTON = "org.wikipedia:id/page_toolbar_button_show_overflow_menu",
        OPTIONS_SAVE_BUTTON = "org.wikipedia:id/page_save",
        OPTIONS_SAVE_TO_MY_LIST_BUTTON = "org.wikipedia:id/snackbar_action",
        MY_LIST_NAME_INPUT = "org.wikipedia:id/text_input",
        MY_LIST_OK_BUTTON = "android:id/button1",
        CLOSE_ARTICLE_BUTTON = "//android.widget.ImageButton[@content-desc='Navigate up']";

    public ArticlePageObject(AppiumDriver driver) {
        super(driver);
    }

    public WebElement waitForTitleElement() {

        return this.waitForElementPresent(By.id(TITLE), "Cannot find article title on page", 15);
    }

    public String getArticleTitle() {
        WebElement title_element = waitForTitleElement();
        return title_element.getText();
    }

    public WebElement clickButtonOnTabbar() {
        return this.waitForElementAndClick(By.id(CONTENT_BUTTON), "Cannot find content button", 5);
    }

    public WebElement clickOnTitle() {
        return this.waitForElementAndClick(By.id(TITLE), "Cannot find title", 5);
    }

    public void swipeToFooter() {
        this.swipeUpToFindElement(
                By.xpath(FOOTER_ELEMENT),
                "Cannot find the end of article",
                20
        );
    }

    public void addArticleToMyList(String name_of_folder) {

        this.waitForElementAndClick(
                By.id(OPTIONS_BUTTON),
                "Cannot find button to open article options",
                5
        );

        this.waitForElementAndClick(
                By.id(OPTIONS_SAVE_BUTTON),
                "Cannot find option to save article",
                5
        );

        this.waitForElementAndClick(
                By.id(OPTIONS_SAVE_TO_MY_LIST_BUTTON),
                "Cannot find button Add to list",
                5
        );

        this.closeBottomSheetByClickingOutside();

        this.waitForElementAndSendKeys(
                By.id(MY_LIST_NAME_INPUT),
                name_of_folder,
                "Cannot put text into articles folder input",
                5
        );

        this.waitForElementAndClick(
                By.id(MY_LIST_OK_BUTTON),
                "Cannot press OK button",
                5
        );
    }

    public void closeArticle() {
        this.waitForElementAndClick(
                By.xpath(CLOSE_ARTICLE_BUTTON),
                "Cannot close article, cannot find back button",
                5
        );

        if (this.isElementPresent(By.xpath(CLOSE_ARTICLE_BUTTON))) {
            this.waitForElementAndClick(
                    By.xpath(CLOSE_ARTICLE_BUTTON),
                    "Cannot close search, cannot find back button",
                    5
            );
        }
    }

}
