package definitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.BrokersPage;

import static definitions.Hooks.driver;

public class BrokersStepDefinition {
    private BrokersPage brokersPage;
    private WebDriverWait wait;

    public BrokersStepDefinition() throws Exception {
        wait = new WebDriverWait(driver, 5);
        brokersPage = new BrokersPage(driver, wait);
    }

    @Given("the broker page info is opened")
    public void theBrokerWebsiteIsOpened() {
        brokersPage.handleCookieAlert();
        brokersPage.verifyBrokerPageIsOpened();
    }

    @When("the user expand the brokers list")
    public void theUserExpandTheBrokerList() {
        brokersPage.handleCookieAlert();
        brokersPage.expandBrokersList();
    }

    @Then("the search results on the result page must contain the correct information")
    public void theUserSearchForEachBrokerInTheSearchEngine() {
        brokersPage.searchForEachBrokerInTheSearchEngine();
    }
}
