package BaseClass;

import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

public class BaseClass {
    public WebDriver driver;
    public Logger logger;
    public Properties prop;

    @BeforeClass
    public void setUp() throws IOException {
        // Load configuration properties
        prop = new Properties();
        try (FileReader file = new FileReader(".//src/test/resources/config.properties")) {
            prop.load(file);
        }

        // Initialize logger
        logger = LogManager.getLogger(this.getClass());

        // Initialize WebDriver
       // System.setProperty("webdriver.chrome.driver", "path/to/chromedriver"); // Set path to your ChromeDriver
        driver = new ChromeDriver();
        logger.info("Chrome browser is opened");

        // Navigate to the application URL
        driver.get(prop.getProperty("appURL"));
        logger.info("App URL is opened: " + prop.getProperty("appURL"));

        // Maximize the window and set implicit wait
        driver.manage().window().maximize();
        logger.info("Window is maximized");
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
    }

    @AfterClass
    public void closeBrowser() {
        if (driver != null) {
            driver.quit(); // Use quit() to close all windows and terminate the WebDriver session
            logger.info("Browser is closed");
        }
    }
}
