

**Libraries and Frameworks**

Version can be found in the POM file.

Selenium - Web automation
Cucumber - Cucumber integration with JUnit and Selenium
Maven - Build and package management
Junit - Test execution and Reporting

**Tools**

Intellij
Eclipse

Please note that during the initial implementation of the automation scenario, I observed that the scenario may fail under certain conditions. Specifically, if there are two users with the same name found through the search engine on the results page, or if one of the two phone numbers (landline and mobile) is not displayed, the scenario could encounter issues.

In light of this, I have revised my approach and created two scenarios to better align with the requirements:

1. **Scenario 1: Iterating through Brokers**
   - For each broker on the page, the script now retrieves the name of the broker and performs a search based on that name.
   - Logging information for each broker, I've implemented handling for missing information or duplication. The script will log relevant details and proceed to the next iteration without halting.
   - This scenario provides full information even if there is missing data for any of the brokers displayed.

2. **Scenario 2: Verifying Broker Attributes**
   - This scenario covers the combination of finding all brokers on the page and verifying, on the result view, that the searched broker is the only one displayed.
   - It ensures that all attributes are present, and if any of them is missing, the scenario is marked as a failure. The second scenario fails every time it encounters missing information for any broker.

I apologize for any inconvenience caused by the change in approach. These adjustments aim to provide a more robust and accurate execution of the task. If you have any further questions or concerns, please feel free to reach out.




