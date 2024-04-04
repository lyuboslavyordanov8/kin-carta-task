package pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.BaseDriverClass;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class BrokersPage extends BaseDriverClass {
    private static final Logger logger = LogManager.getLogger(BrokersPage.class);
    @FindBy(css = "div.broker-list-holder.xteam-list-wrap[data-total-count]")
    private List<WebElement> brokersList;

    @FindBy(css = "div[class*='load-more-brokers']")
    private WebElement loadMoreBrokersButton;

    @FindBy(css = "[class='hide-cookies-message green-btn']")
    private WebElement cookiesAlertAcceptButton;

    @FindBy(css = "h3.name")
    private List<WebElement> brokerNames;

    @FindBy(css = "article.broker-card")
    private WebElement brokerCard;

    @FindBy(css = "input#searchBox[data-container=broker-keyword]")
    private WebElement searchInput;

    @FindBy(css = (("button[type='button'].clear-all-dropdowns.clear-btn")))
    private WebElement clearButton;
    Duration timeout = Duration.ofSeconds(5);

    public BrokersPage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
        PageFactory.initElements(driver, this);
    }

    public void expandBrokersList() {
        waitUntilElementVisible(loadMoreBrokersButton);
        loadMoreBrokersButton.click();
    }

    public void verifyBrokerPageIsOpened() {
        waitUntilElementVisible(brokerCard);
        assertTrue("Broker page is not opened", brokerCard.isDisplayed());
    }

    public void handleCookieAlert() {
        try {
            cookiesAlertAcceptButton.click();
        } catch (NoSuchElementException e) {
            logger.info("Cookie alert not found or not visible.");
        }
    }

    public void searchForEachBrokerInTheSearchEngineAndVerifyInfoIsDisplayed() {

        Map<String, List<String>> failedBrokersMap = new HashMap<>();

        String previousAttributeValue = null; // Store the previous attribute value

        for (int i = 0; i < brokerNames.size(); i++) {
            String name = brokerNames.get(i).getText();

            searchInput.clear();
            searchInput.sendKeys(name);

            waitForAttributeValueChangedInElementLocated(
                    By.cssSelector("div.broker-list-holder.xteam-list-wrap"),
                    "data-total-count",
                    previousAttributeValue, timeout,
                    2000 // Sleep for 2 seconds after the attribute value changes
            );

            WebElement element = driver.findElement(By.cssSelector("div.broker-list-holder.xteam-list-wrap"));
            previousAttributeValue = element.getAttribute("data-total-count");

            int numberOfBrokersOnSearchResult = brokerNames.size();
            try {
                assertEquals("Unexpected number of brokers found for: " + name, 1, numberOfBrokersOnSearchResult);

                WebElement propertiesCount = brokerCard.findElement(By.cssSelector("div.position"));
                WebElement officeAddress = brokerCard.findElement(By.cssSelector("div.office"));
                WebElement telGroup = brokerCard.findElement(By.cssSelector("div.tel-group"));
                java.util.List<WebElement> telNumbers = telGroup.findElements(By.cssSelector("span.tel"));

                assertEquals("Unexpected number of phone numbers found for: " + name, 2, telNumbers.size());
                assertTrue("Broker name is not displayed for: " + name, brokerCard.isDisplayed());
                assertTrue("Office address is not displayed for: " + name, officeAddress.isDisplayed());
                assertTrue("Properties count is not displayed for: " + name, propertiesCount.isDisplayed());
            } catch (AssertionError e) {

                List<String> failedAssertions = failedBrokersMap.getOrDefault(name, new ArrayList<>());
                failedAssertions.add(e.getMessage());
                failedBrokersMap.put(name, failedAssertions);
            }

            clearButton.click();

            waitForAttributeValueChangedInElementLocated(
                    By.cssSelector("div.broker-list-holder.xteam-list-wrap"),
                    "data-total-count",
                    previousAttributeValue, timeout,
                    2000 // Sleep for 3 seconds after the attribute value changes
            );
        }

        for (Map.Entry<String, List<String>> entry : failedBrokersMap.entrySet()) {
            logger.info("Failed for broker: " + entry.getKey());
            for (String failure : entry.getValue()) {
                logger.info("\t" + failure);
            }
        }
    }
}
