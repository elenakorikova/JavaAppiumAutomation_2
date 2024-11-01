package tests;

import lib.CoreTestCase;
import lib.ui.ArticlePageObject;
import lib.ui.SearchPageObject;
import org.junit.Test;

public class ChangeAppConditionTests extends CoreTestCase {

    @Test

    public void testChangeScreenOrientationOnSearchResults() {

        SearchPageObject SearchPageObject = new SearchPageObject(driver);
        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Java");
        SearchPageObject.clickByArticleWithSubString("Object-oriented programming language");

        ArticlePageObject ArticlePageObject = new ArticlePageObject(driver);
        ArticlePageObject.clickButtonOnTabbar();
        ArticlePageObject.waitForTitleElement();
        String tittle_before_rotation = ArticlePageObject.getArticleTitle();
        ArticlePageObject.clickBackButton();
        this.rotateScreenLandscape();
        ArticlePageObject.clickButtonOnTabbar();
        ArticlePageObject.waitForTitleElement();
        String tittle_after_rotation = ArticlePageObject.getArticleTitle();
        ArticlePageObject.clickBackButton();

        assertEquals(
                "Article title have been changed after screen rotation",
                tittle_before_rotation,
                tittle_after_rotation
        );

        this.rotateScreenPortrait();
        ArticlePageObject.clickButtonOnTabbar();
        ArticlePageObject.waitForTitleElement();
        String tittle_after_second_rotation = ArticlePageObject.getArticleTitle();
        ArticlePageObject.clickBackButton();

        assertEquals(
                "Article title have been changed after screen rotation",
                tittle_before_rotation,
                tittle_after_second_rotation
        );
    }

    @Test

    public void testCheckSearchArticleInBackground() {

        SearchPageObject SearchPageObject = new SearchPageObject(driver);
        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Java");
        SearchPageObject.waitForSearchResult("Object-oriented programming language");
        this.backgroundApp(2);
        SearchPageObject.waitForSearchResult("Object-oriented programming language");
    }
}
