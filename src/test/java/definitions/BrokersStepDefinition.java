package definitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pages.BrokersPage;
import utils.Context;


public class BrokersStepDefinition {

    Context context;
    BrokersPage brokersPage;

    public BrokersStepDefinition(Context context) {
        this.context = context;
        brokersPage = context.getBrokersPage();
    }

    @Given("the broker page info is opened")
    public void theBrokerWebsiteIsOpened() {
        brokersPage.handleCookieAlert();
        brokersPage.verifyBrokerPageIsOpened();
    }


    @Then("verify the search results display accurate information on the broker card, confirming it is the only broker listed")
    public void verifyTheSearchResultsDisplayAccurateInformationOnTheBrokerCardConfirmingItIsTheOnlyBrokerListed() {
        brokersPage.searchForEachBrokerInTheSearchEngineAndVerifyInfoIsDisplayed();
    }
}
