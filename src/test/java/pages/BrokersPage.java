package pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.BaseDriverClass;
import java.util.List;


public class BrokersPage extends BaseDriverClass {
    private static final Logger logger = LogManager.getLogger(BrokersPage.class);
    @FindBy(css = "div.broker-list-holder.xteam-list-wrap")
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

    @FindBy (css = (("button[type='button'].clear-all-dropdowns.clear-btn")))
    private WebElement clearButton;

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
        Assert.assertTrue("Broker page is not opened",brokerCard.isDisplayed());
    }

    public void handleCookieAlert() {
        try {
            cookiesAlertAcceptButton.click();
        } catch (NoSuchElementException e) {
            logger.info("Cookie alert not found or not visible.");
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
                    10
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
                    "118",
                    10
            );
        }
    }
}
