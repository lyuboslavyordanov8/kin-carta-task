package definitions;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;


import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;


import javax.imageio.ImageIO;

public class Hooks {
    private static ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();
    public static WebDriver getDriver() {
        return driverThreadLocal.get();
    }

    @Before
    public void openBrowser() {
        String browserType = System.getProperty("browserType", "chrome").toLowerCase();

        WebDriver driver;
        switch (browserType) {
            case "chrome":
                WebDriverManager.chromedriver().setup();
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.addArguments("--window-size=1440,768", "--disable-gpu");
                driver = new ChromeDriver(chromeOptions);
                break;
            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                driver = new FirefoxDriver(firefoxOptions);
                break;
            case "edge":
                WebDriverManager.edgedriver().setup();
                EdgeOptions edgeOptions = new EdgeOptions();
                driver = new EdgeDriver(edgeOptions);
                break;
            default:
                throw new IllegalArgumentException("Unsupported browser type: " + browserType);
        }
        driver.manage().deleteAllCookies();
        driver.manage().window().maximize();
        driver.get("https://www.yavlena.com/broker/");
        driverThreadLocal.set(driver);
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
    public void tearDown(Scenario scenario) {

        if (scenario.isFailed()) {
            try {
                final byte[] screenshot = takeScreenshot();
                scenario.attach(screenshot, "image/png", scenario.getName());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
            getDriver().quit();
        }
    }
