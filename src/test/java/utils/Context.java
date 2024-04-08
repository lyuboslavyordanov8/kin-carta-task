package utils;

import definitions.Hooks;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.BrokersPage;

import java.time.Duration;

public class Context extends Hooks {
    private ThreadLocal<BrokersPage> brokersPageThreadLocal = new ThreadLocal<>();

    public BrokersPage getBrokersPage() {
        if (brokersPageThreadLocal.get() == null) {
            brokersPageThreadLocal.set(new BrokersPage(getDriver(), new WebDriverWait(getDriver(), Duration.ofSeconds(22))));
        }
        return brokersPageThreadLocal.get();
    }

}
