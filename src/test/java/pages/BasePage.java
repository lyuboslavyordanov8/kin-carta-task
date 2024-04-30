package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.ConfigLoader;

import java.time.Duration;
import java.util.List;

public class BasePage {
    protected WebDriver driver;
    protected WebDriverWait wait;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        PageFactory.initElements(driver, this);
    }

    public void load(String endPoint){
        driver.get(ConfigLoader.getInstance().getBaseUrl() + endPoint);
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
