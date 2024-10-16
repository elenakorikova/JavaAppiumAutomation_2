package lib.ui;

import com.google.common.collect.ImmutableList;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.NoSuchElementException;

public class MainPageObject {

    protected AppiumDriver driver;

    public MainPageObject(AppiumDriver driver) {
        this.driver = driver;
    }

    public void waitForElementNotPresent(By by, String error_message, long timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        boolean isNotPresent = wait.until(ExpectedConditions.invisibilityOfElementLocated(by));

        if (!isNotPresent) {
            throw new AssertionError(error_message);
        }
    }

    public WebElement waitForElementPresent(By by, String error_message, long timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(error_message + "\n");
        return wait.until(
                ExpectedConditions.presenceOfElementLocated(by)
        );
    }

    public WebElement waitForElementPresent(By by, String error_message) {
        return waitForElementPresent(by, error_message, 5);
    }

    public WebElement waitForElementAndClick(By by, String error_message, long timeOutInSeconds) {
        WebElement element = waitForElementPresent(by, error_message, timeOutInSeconds);
        element.click();
        return element;
    }

    public WebElement waitForElementAndSendKeys(By by, String value, String error_message, long timeOutInSeconds) {
        WebElement element = waitForElementPresent(by, error_message, timeOutInSeconds);
        element.sendKeys(value);
        return element;
    }

    public boolean isElementNotPresent(By by) {
        try {
            driver.findElement(by);
            return false;
        } catch (org.openqa.selenium.NoSuchElementException e) {
            return true;
        }

    }

    public WebElement waitForElementAndClear(By by, String error_message, long timeOutInSeconds) {
        WebElement element = waitForElementPresent(by, error_message, timeOutInSeconds);
        element.clear();
        return element;
    }

    public void swipeUp(Point start, Point end, Duration duration) {
        PointerInput input = new PointerInput(PointerInput.Kind.TOUCH, "finger1");
        Sequence swipe = new Sequence(input, 0);
        swipe.addAction(input.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), start.x, start.y));
        swipe.addAction(input.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        swipe.addAction(input.createPointerMove(duration, PointerInput.Origin.viewport(), end.x, end.y));
        swipe.addAction(input.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
        driver.perform(ImmutableList.of(swipe));
    }

    public void swipeUpQuick() {
        Dimension size = driver.manage().window().getSize();
        int startX = size.width / 2;
        int startY = (int) (size.height * 0.8);
        int endY = (int) (size.height * 0.2);
        Point startPoint = new Point(startX, startY);
        Point endPoint = new Point(startX, endY);

        swipeUp(startPoint, endPoint, Duration.ofMillis(200));
    }

    public void swipeUpToFindElement(By by, String error_message, int max_swipes) {
        int already_swiped = 0;
        while (driver.findElements(by).size() == 0) {

            if (already_swiped > max_swipes) {
                waitForElementPresent(by, "Cannot find element by swiping up.\n" + error_message, 0);
                return;
            }

            swipeUpQuick();
            ++already_swiped;
        }

    }

//    public void longPress(AndroidDriver driver, WebElement element) {
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

//    public Point getCenterOfElement(Point location, Dimension size) {
//        int centerX = location.x + size.width / 2;
//        int centerY = location.y + size.height / 2;
//        return new Point(centerX, centerY);
//    }

    public void closeBottomSheetByClickingOutside() {
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

    public int getAmountOfElements(By by) {
        List elements = driver.findElements(by);
        return elements.size();
    }

    public void assertElemetNotPresent(By by, String error_message) {
        int amount_of_elements = getAmountOfElements(by);
        if (amount_of_elements > 0) {
            String default_message = "An element '" + by.toString() + "' supposed to be not present";
            throw new AssertionError(default_message + " " + error_message);
        }
    }

    public String waitForElementAndGetText(By by, String error_message, long timeOutInSeconds) {
        WebElement element = waitForElementPresent(by, error_message, timeOutInSeconds);
        return element.getText();
    }

    public void swipeLeft(Point start, Point end, Duration duration) {
        PointerInput input = new PointerInput(PointerInput.Kind.TOUCH, "finger1");
        Sequence swipe = new Sequence(input, 0);
        swipe.addAction(input.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), start.x, start.y));
        swipe.addAction(input.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        swipe.addAction(input.createPointerMove(duration, PointerInput.Origin.viewport(), end.x, end.y));
        swipe.addAction(input.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
        driver.perform(ImmutableList.of(swipe));
    }

    public void swipeLeftQuick() {
        Dimension size = driver.manage().window().getSize();
        int startX = (int) (size.width * 0.8);
        int endX = (int) (size.width * 0.2);
        int startY = size.height / 2;

        Point startPoint = new Point(startX, startY);
        Point endPoint = new Point(endX, startY);

        swipeLeft(startPoint, endPoint, Duration.ofMillis(200));
    }

    public void swipeElementToLeft(By by, String error_message) {
        WebElement element = waitForElementPresent(by, error_message, 20);

        int leftX = element.getLocation().getX();
        int rightX = leftX + element.getSize().getWidth();
        int upperY = element.getLocation().getY();
        int lowerY = upperY + element.getSize().getHeight();
        int middleY = (upperY + lowerY) / 2;

        swipeLeft(new Point(rightX, middleY), new Point(leftX, middleY), Duration.ofMillis(300));
    }

    public void assertElementPresent(By by, String error_message) {
        try {
            driver.findElement(by);
        } catch (org.openqa.selenium.NoSuchElementException e) {
            throw new AssertionError(error_message + ": Элемент не найден");
        }
    }
}
