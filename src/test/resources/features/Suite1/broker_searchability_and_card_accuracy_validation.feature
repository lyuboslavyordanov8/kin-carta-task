Feature: Broker searchability and card accuracy validation

  Acceptance Criteria:

  * User should be able to view all brokers' initial info.
  * User should be able to use the search engine to find each broker.
  * Information for each broker on the search result page, including broker name, office address, telephone numbers, and properties count, should be visibly displayed.
  * The search result page should accurately confirm that the searched broker is the only one displayed, ensuring exclusivity in the results.
  * The user should be able to utilize the search engine successfully to locate each broker.

  Background:
    Given the broker page info is opened

  Scenario: Verify accurate information on each broker card in search result
    Then verify the search results display accurate information on the broker card, confirming it is the only broker listed