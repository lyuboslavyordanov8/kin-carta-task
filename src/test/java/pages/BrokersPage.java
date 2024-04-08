package pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;
import utils.BaseDriverClass;

import java.time.Duration;
import java.util.List;


public class BrokersPage extends BaseDriverClass {

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

    SoftAssert softAssert = new SoftAssert();
    private static final Logger logger = LogManager.getLogger(BrokersPage.class);

    public BrokersPage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
        PageFactory.initElements(driver, this);
    }


    public void verifyBrokerPageIsOpened() {
        waitUntilElementVisible(brokerCard);
        Assert.assertTrue(brokerCard.isDisplayed(),"Broker page is not opened");
    }

    public void handleCookieAlert() {
        try {
            cookiesAlertAcceptButton.click();
        } catch (NoSuchElementException e) {
            logger.info("Cookie alert not found or not visible.");
        }
    }

    public void searchForEachBrokerInTheSearchEngineAndVerifyInfoIsDisplayed() {
        String previousAttributeValue = null;

        for (int i = 0; i < brokerNames.size(); i++) {
            String name = brokerNames.get(i).getText();
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
                softAssert.assertEquals(numberOfBrokersOnSearchResult, 1, "Unexpected number of brokers found for: " + name);
                WebElement propertiesCount = brokerCard.findElement(By.cssSelector("a.MuiTypography-root.MuiTypography-inherit.MuiLink-root.MuiLink-underlineHover.mui-style-1ya14h1"));
                expandDetails.click();
                WebElement officeAddress = brokerCard.findElement(By.cssSelector("span.MuiTypography-root.MuiTypography-textSmallRegular.mui-style-14x3no9"));
                WebElement telGroup = brokerCard.findElement(By.cssSelector("div.MuiStack-root.mui-style-1o9h2dc"));
                java.util.List<WebElement> telNumbers = telGroup.findElements(By.cssSelector("a.MuiTypography-root.MuiTypography-inherit.MuiLink-root.MuiLink-underlineNone.mui-style-1ktzac6"));

                softAssert.assertEquals(telNumbers.size(), 2, "Unexpected number of phone numbers found for: " + name);
                softAssert.assertTrue(brokerCard.isDisplayed(), "Broker name is not displayed for: " + name);
                softAssert.assertTrue(officeAddress.isDisplayed(), "Office address is not displayed for: " + name);
                softAssert.assertTrue(propertiesCount.isDisplayed(), "Properties count is not displayed for: " + name);
            } catch (AssertionError e) {
                softAssert.fail(e.getMessage());
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
        softAssert.assertAll();
    }

}
