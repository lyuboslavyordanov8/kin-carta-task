package pages;

import org.openqa.selenium.WebDriver;

public class PageFactoryManager {
    private static BrokersPage brokersPage;

    public static BrokersPage getBrokersPage(WebDriver driver){
        return brokersPage == null ? new BrokersPage(driver) : brokersPage;
    }

}
