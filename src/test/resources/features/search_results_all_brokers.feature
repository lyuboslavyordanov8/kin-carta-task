Feature: Broker searchability validation

  Acceptance Criteria:
  * User should be able to view all brokers initial info
  * User should be able to use the search engine to find each broker

  Background:
    Given the broker page info is opened

  Scenario: Verify results from the broker page search engine
    When the user expand the brokers list
    Then verify that each of the brokers on the page can be found through the search engine