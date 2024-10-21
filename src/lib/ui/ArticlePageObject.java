package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class ArticlePageObject extends MainPageObject {

    private static final String
        TITLE = "org.wikipedia:id/page_toc_item_text",
        CONTENT_BUTTON = "org.wikipedia:id/page_contents",
        FOOTER_ELEMENT = "//*[@text='View article in browser']";

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

}
