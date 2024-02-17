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
        brokersPage.expandBrokersList();
    }

    @Then("verify that each of the brokers on the page can be found through the search engine")
    public void theUserSearchForEachBrokerInTheSearchEngine() {
        brokersPage.searchForEachBrokerInTheSearchEngine();
    }

    @Then("verify the search results display accurate information on the broker card, confirming it is the only broker listed")
    public void verifyTheSearchResultsDisplayAccurateInformationOnTheBrokerCardConfirmingItIsTheOnlyBrokerListed() {
        brokersPage.searchForEachBrokerInTheSearchEngineAndVerifyInfoIsDisplayed();
    }
}
