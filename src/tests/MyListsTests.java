package tests;

import lib.CoreTestCase;
import lib.ui.ArticlePageObject;
import lib.ui.MyListsPageObject;
import lib.ui.NavigationUI;
import lib.ui.SearchPageObject;
import org.junit.Test;

public class MyListsTests extends CoreTestCase {

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

    public void testSaveTwoArticlesInOneFolder() {

        SearchPageObject SearchPageObject = new SearchPageObject(driver);

        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Java");
        SearchPageObject.clickByArticleWithSubString("Object-oriented programming language");

        ArticlePageObject ArticlePageObject = new ArticlePageObject(driver);
        ArticlePageObject.clickButtonOnTabbar();
        ArticlePageObject.waitForTitleElement();
        ArticlePageObject.clickBackButton();
        String name_of_folder = "Learning programming";
        ArticlePageObject.addArticleToMyList(name_of_folder);

        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Python");
        SearchPageObject.clickByArticleWithSubString("General-purpose programming language");

        ArticlePageObject.addArticleToExistingList();

        MyListsPageObject MyListsPageObject = new MyListsPageObject(driver);
        MyListsPageObject.waitForArticleToAppearByTitle("Python (programming language)");
        MyListsPageObject.swipeByArticleToDelete("Python (programming language");
        MyListsPageObject.waitForArticleToAppearByTitle("Java (programming language)");

        String title_in_the_list = ArticlePageObject.getArticleTitleInTheList();
        SearchPageObject.clickByArticleWithSubString("Object-oriented programming language");
        ArticlePageObject.clickButtonOnTabbar();
        ArticlePageObject.waitForTitleElement();
        String title_in_the_article = ArticlePageObject.getArticleTitle();

        assertEquals(
                "Article title have been changed",
                title_in_the_list,
                title_in_the_article
        );
    }
}
