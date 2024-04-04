package pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.BaseDriverClass;

import java.time.Duration;
import java.util.List;

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
        for (int i = 0; i < brokerNames.size(); i++) {
            String name = brokerNames.get(i).getText();

            searchInput.clear();
            searchInput.sendKeys(name);

            waitForAttributeValueToBePresentInElementLocated(
                    By.cssSelector("div.broker-list-holder.xteam-list-wrap"),
                    "data-total-count",
                    "1",timeout
            );

            int numberOfBrokersOnSearchResult = brokerNames.size();
            assertEquals("Unexpected number of brokers found for: " + name, 1, numberOfBrokersOnSearchResult);

            WebElement propertiesCount = brokerCard.findElement(By.cssSelector("div.position"));
            WebElement officeAddress = brokerCard.findElement(By.cssSelector("div.office"));
            WebElement telGroup = brokerCard.findElement(By.cssSelector("div.tel-group"));
            java.util.List<WebElement> telNumbers = telGroup.findElements(By.cssSelector("span.tel"));

            assertEquals("Unexpected number of phone numbers found for: " + name, 2, telNumbers.size());
            assertTrue("Broker name is not displayed for: " + name, brokerCard.isDisplayed());
            assertTrue("Office address is not displayed for: " + name, officeAddress.isDisplayed());
            assertTrue("Properties count is not displayed for: " + name, propertiesCount.isDisplayed());

            clearButton.click();
            waitForAttributeValueToBePresentInElementLocated(
                    By.cssSelector("div.broker-list-holder.xteam-list-wrap"),
                    "data-total-count",
                    "117",
                    timeout
            );
        }
    }

    public void searchForEachBrokerInTheSearchEngine() {
        for (int i = 0; i < brokerNames.size(); i++) {

            String name = brokerNames.get(i).getText();
            searchInput.clear();
            searchInput.sendKeys(name);

            waitForAttributeValueToBePresentInElementLocated(
                    By.cssSelector("div.broker-list-holder.xteam-list-wrap"),
                    "data-total-count",
                    "1",
                    timeout
            );

            if (brokersList.size() == 1) {

                WebElement officeAddress = brokerCard.findElement(By.cssSelector("div.office"));
                WebElement telGroup = brokerCard.findElement(By.cssSelector("div.tel-group"));

                java.util.List<WebElement> telNumbers = telGroup.findElements(By.cssSelector("span.tel"));

                logger.info("Broker Name: " + name);
                logger.info("Office Address: " + officeAddress.getText());

                if (telNumbers.size() > 0) {
                    logger.info("Landline Number: " + telNumbers.get(0).getText());
                }

                if (telNumbers.size() > 1) {
                    logger.info("Mobile Number: " + telNumbers.get(1).getText());
                } else {
                    logger.info("Mobile Number: N/A");
                }

                 WebElement propertiesCount = brokerCard.findElement(By.cssSelector("div.position"));
                logger.info("Properties Count: " + propertiesCount.getText());
            } else {
                logger.info("No search result or multiple results found for broker: " + name);
            }
            clearButton.click();
            waitForAttributeValueToBePresentInElementLocated(
                    By.cssSelector("div.broker-list-holder.xteam-list-wrap"),
                    "data-total-count",
                    "117",
                    timeout
            );
        }
    }
}
