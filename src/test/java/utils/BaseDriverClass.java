package utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class BaseDriverClass {
    public WebDriver driver;
    private static WebDriverWait wait;

    public BaseDriverClass(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    protected void waitUntilElementVisible(WebElement element) {
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    protected boolean waitForAttributeValueChangedInElementLocated(
            WebDriver driver, By locator, String attributeName, String previousValue, Duration timeout, long sleepDuration) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, timeout);

            return wait.until((ExpectedCondition<Boolean>) webDriver -> {
                List<WebElement> elements = driver.findElements(locator);
                int currentElementCount = elements.size();

                if (previousValue == null || previousValue.isEmpty()) {
                    return true;
                }

                int previousCount = Integer.parseInt(previousValue);
                return currentElementCount != previousCount;
            });
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                Thread.sleep(sleepDuration);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
