Feature: Broker card accuracy verification

  Acceptance Criteria:
  * Information for each broker on the search result page, including broker name, office address, telephone numbers, and properties count, should be visibly displayed.
  * The search result page should accurately confirm that the searched broker is the only one displayed, ensuring exclusivity in the results.
  * The user should be able to utilize the search engine successfully to locate each broker.

  Background:
    Given the broker page info is opened

  Scenario: Verify results from the broker page search engine
    When the user expand the brokers list
    Then verify the search results display accurate information on the broker card, confirming it is the only broker listed
