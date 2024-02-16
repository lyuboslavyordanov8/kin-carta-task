

**Libraries and Frameworks**

Version can be found in the POM file.

Selenium - Web automation
Cucumber - Cucumber integrationo with Junit and Selenium
Maven - Build and package management
Junit - Test execution and Reporting

**Tools**

Intellij
Eclipse

Please note that during the implementation of the automation scenario, I observed that the scenario may fail under certain conditions (as its expected) . Specifically, if there are two users with the same name found through the search engine on the results page, the scenario will encounter issues. Additionally, the scenario fails if one of the two phone numbers (landline and mobile) is not displayed.

In order to ensure each iteration of the for loop can go through and show every broker on the page, I have implemented handling for the following cases:
1. Duplicate Names: I have addressed scenarios where the search results show more than one broker with the given name. The script will handle this situation and log a message.
2. Missing Phone Numbers: The script is now equipped to handle cases where either the landline or mobile phone number is not displayed. A corresponding message will be logged in such instances.

Please note that these handling mechanisms have been incorporated to prevent the script from stopping at the very first iterations and ensure it cycles through all the names. If you wish to remove these handling features, please let me know.

P.S.: Feel free to contact me if you have any further questions or need additional clarification.




