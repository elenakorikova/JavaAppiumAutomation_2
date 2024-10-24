package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;

public class MyListsPageObject extends MainPageObject {

    private static final String
    MORE_OPTIONS = "//android.widget.ImageView[@content-desc='More options']",
    DELETE_LIST = "//android.widget.TextView[@text='Delete list']",
    OK_BUTTON = "android:id/button1";

    public static final String
        FOLDER_BY_NAME_TPL = "//*[@resource-id='org.wikipedia:id/item_title'][@text='{FOLDER_NAME}']";
    private static String getFolderXpathByName(String name_of_folder) {
        return FOLDER_BY_NAME_TPL.replace("{FOLDER_NAME}", name_of_folder);
    }

    public MyListsPageObject(AppiumDriver driver) {
        super(driver);
    }

    public void openFolderByName(String name_of_folder) {

        String folder_name_xpath = getFolderXpathByName(name_of_folder);
        this.waitForElementAndClick(
                By.xpath(folder_name_xpath),
                "Cannot find folder by name " + name_of_folder,
                5
        );
    }

    public void clickMoreOptions() {

        this.waitForElementAndClick(
                By.xpath(MORE_OPTIONS),
                "Cannot find three dots button",
                5
        );
    }

    public void clickToDeleteList() {

        this.waitForElementAndClick(
                By.xpath(DELETE_LIST),
                "Cannot find delete action",
                5
        );

        this.waitForElementAndClick(
                By.id(OK_BUTTON),
                "Cannot press OK button",
                5
        );
    }
}
