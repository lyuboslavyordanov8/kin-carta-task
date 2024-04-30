package definitions;

import context.TestContext;
import factory.DriverFactory;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;

public class MyHooks {
    private WebDriver driver;
    private final TestContext context;
    private static final Logger logger = LogManager.getLogger(MyHooks.class);

    public MyHooks(TestContext context) {
        this.context = context;
    }
    @Before
    public void before(Scenario scenario) {
        logger.info("BEFORE: THREAD ID : " + Thread.currentThread().threadId() + "," +
                "SCENARIO NAME: " + scenario.getName());
        driver = DriverFactory.initializeDriver(System.getProperty("browser", "chrome"));
        context.driver = driver;
    }

    public static byte[] takeScreenshot() {
        try {
            Robot robot = new Robot();
            BufferedImage image = robot.createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ImageIO.write(image, "png", outputStream);
            return outputStream.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @After
    public void after(Scenario scenario) {
        logger.info("AFTER: THREAD ID : " + Thread.currentThread().threadId() + "," +
                "SCENARIO NAME: " + scenario.getName());
        if (scenario.isFailed()) {
            try {
                final byte[] screenshot = takeScreenshot();
                scenario.attach(screenshot, "image/png", scenario.getName());
            } catch (Exception e) {
                e.printStackTrace();
            }
        throw new RuntimeException("Scenario failed: " + scenario.getName());
        }

        driver.quit();
    }
}
