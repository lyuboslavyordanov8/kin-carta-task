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
    @FindBy(css = "div.MuiGrid-root.MuiGrid-item.MuiGrid-grid-xs-1.MuiGrid-grid-sm-1.MuiGrid-grid-md-1.MuiGrid-grid-tablet-1.MuiGrid-grid-lg-1.MuiGrid-grid-xl-1.mui-style-rstqa8")
    private List<WebElement> brokerList;

    @FindBy(css = "button.MuiButtonBase-root.MuiButton-root.MuiButton-text.MuiButton-textDarkBlue.MuiButton-sizeMedium.MuiButton-textSizeMedium.mui-style-mc33y5")
    private WebElement expandDetails;

    @FindBy(css = "[class='hide-cookies-message green-btn']")
    private WebElement cookiesAlertAcceptButton;

    @FindBy(css = ".MuiTypography-root.MuiTypography-h6.mui-style-crk47i")
    private List<WebElement> brokerNames;

    @FindBy(css = "div.MuiCardContent-root.mui-style-q8glis")
    private WebElement brokerCard;

    @FindBy(id = "broker-keyword")
    private WebElement searchInput;

    @FindBy(css = "button.MuiButtonBase-root.MuiButton-root.MuiButton-text.MuiButton-textDarkGray.MuiButton-sizeMedium.MuiButton-textSizeMedium.mui-style-1550dmo")
    private WebElement clearButton;
    Duration timeout = Duration.ofSeconds(5);

    public BrokersPage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
        PageFactory.initElements(driver, this);
    }

    public void scrollToBottomOfBrokersList(WebDriver driver) {

        WebElement lastBroker = brokerList.get(brokerList.size() - 1);

        JavascriptExecutor js = (JavascriptExecutor) driver;

        js.executeScript("arguments[0].scrollIntoView(true);", lastBroker);
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
            scrollToBottomOfBrokersList(driver);
            searchInput.clear();
            searchInput.sendKeys(name);

            waitForAttributeValueChangedInElementLocated(
                    driver,
                    By.cssSelector("div.MuiGrid-root.MuiGrid-item.MuiGrid-grid-xs-1.MuiGrid-grid-sm-1.MuiGrid-grid-md-1.MuiGrid-grid-tablet-1.MuiGrid-grid-lg-1.MuiGrid-grid-xl-1.mui-style-rstqa8"),
                    "class",
                    previousAttributeValue,
                    Duration.ofSeconds(10),
                    1000
            );

            List<WebElement> elements = driver.findElements(By.cssSelector("div.MuiGrid-root.MuiGrid-item.MuiGrid-grid-xs-1.MuiGrid-grid-sm-1.MuiGrid-grid-md-1.MuiGrid-grid-tablet-1.MuiGrid-grid-lg-1.MuiGrid-grid-xl-1.mui-style-rstqa8"));
            previousAttributeValue = Integer.toString(elements.size());

            int numberOfBrokersOnSearchResult = brokerNames.size();

            try {
                assertEquals("Unexpected number of brokers found for: " + name, 1, numberOfBrokersOnSearchResult);
                WebElement propertiesCount = brokerCard.findElement(By.cssSelector("a.MuiTypography-root.MuiTypography-inherit.MuiLink-root.MuiLink-underlineHover.mui-style-1ya14h1"));
                expandDetails.click();
                WebElement officeAddress = brokerCard.findElement(By.cssSelector("span.MuiTypography-root.MuiTypography-textSmallRegular.mui-style-14x3no9"));
                WebElement telGroup = brokerCard.findElement(By.cssSelector("div.MuiStack-root.mui-style-1o9h2dc"));
                java.util.List<WebElement> telNumbers = telGroup.findElements(By.cssSelector("a.MuiTypography-root.MuiTypography-inherit.MuiLink-root.MuiLink-underlineNone.mui-style-1ktzac6"));

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
                    driver,
                    By.cssSelector("div.MuiGrid-root.MuiGrid-item.MuiGrid-grid-xs-1.MuiGrid-grid-sm-1.MuiGrid-grid-md-1.MuiGrid-grid-tablet-1.MuiGrid-grid-lg-1.MuiGrid-grid-xl-1.mui-style-rstqa8"),
                    "class",
                    previousAttributeValue,
                    Duration.ofSeconds(10),
                    1000
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
