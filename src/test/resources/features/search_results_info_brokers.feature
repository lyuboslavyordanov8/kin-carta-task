Feature: Broker information cards

  Acceptance Criteria:
  * User should be able to view all brokers initial info
  * User should be able to use the search engine to find each broker
  * The info for each broker on the search result page is showed

  Background:
    Given the broker page info is opened

Scenario: Verify results from the broker page search engine
  When the user expand the brokers list
  Then the search results on the result page must contain the correct information
