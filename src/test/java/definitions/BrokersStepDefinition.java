package definitions;

import constants.MyConstants;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import pages.BrokersPage;
import pages.PageFactoryManager;

public class BrokersStepDefinition {

    BrokersPage brokersPage;

    public BrokersStepDefinition(MyHooks hooks) {
        brokersPage = PageFactoryManager.getBrokersPage(hooks.getDriver());
    }

    @Given("the broker page info is opened")
    public void theBrokerWebsiteIsOpened() {
        brokersPage.load(MyConstants.BROKER_PAGE);
        brokersPage.handleCookieAlert();
    }

    @Then("verify the search results display accurate information on the broker card, confirming it is the only broker listed")
    public void verifyTheSearchResultsDisplayAccurateInformationOnTheBrokerCardConfirmingItIsTheOnlyBrokerListed() {
        brokersPage.searchForEachBrokerInTheSearchEngineAndVerifyInfoIsDisplayed();
    }
}
