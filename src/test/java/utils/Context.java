package utils;
import definitions.Hooks;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.BrokersPage;

import java.time.Duration;

public class Context extends Hooks {

    private BrokersPage brokersPage;

    public BrokersPage getBrokersPage()
    {
        if(brokersPage==null)
        {
            brokersPage = new BrokersPage(driver, new WebDriverWait(driver, Duration.ofSeconds(22)));
        }
        return brokersPage;
    }

}
