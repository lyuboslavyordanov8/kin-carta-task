package utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class BaseDriverClass {
	private static WebDriver driver;
    private static WebDriverWait wait;
    private static final Logger logger = LogManager.getLogger(BaseDriverClass.class);

    public BaseDriverClass(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    protected void waitUntilElementVisible(WebElement element) {
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    protected boolean waitForAttributeValueToBePresentInElementLocated(
            By locator, String attributeName, String expectedValue, Duration timeout) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, timeout);

            return wait.until((ExpectedCondition<Boolean>) webDriver -> {
                WebElement element = webDriver.findElement(locator);
                String actualValue = element.getAttribute(attributeName);
                return actualValue != null && actualValue.equals(expectedValue);
            });
        } catch (TimeoutException e) {
            logger.info("Timeout waiting for attribute value.");
            return false;
        }
    }

}
