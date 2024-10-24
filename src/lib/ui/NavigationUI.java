package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;

public class NavigationUI extends MainPageObject {

    private static final String
        SAVED_LINK = "//android.widget.FrameLayout[@content-desc='Saved']";

    public NavigationUI(AppiumDriver driver) {
        super(driver);
    }

    public void clickSaved() {

        this.waitForElementAndClick(
                By.xpath(SAVED_LINK),
                "Cannot find navigation button to saved articles",
                5
        );
    }
}
