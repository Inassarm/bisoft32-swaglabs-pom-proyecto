package proyecto.base;

import java.io.InputStream;
import java.time.Duration;
import java.util.Properties;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class BaseTest {
    protected WebDriver driver;
    protected Properties config = new Properties();
    protected long timeoutSeconds = 10;

    @BeforeEach
    void setUp() throws Exception {
        try (InputStream in = getClass().getClassLoader().getResourceAsStream("config.properties")) {
            if (in != null) {
                config.load(in);
            }
        }
        timeoutSeconds = Long.parseLong(config.getProperty("timeout.seconds", "10"));
        String browser = config.getProperty("browser", "chrome").toLowerCase();

        switch (browser) {
            case "firefox" -> {
                driver = new FirefoxDriver(new FirefoxOptions());
                driver.manage().window().maximize();
            }
            case "edge" -> {
                driver = new EdgeDriver(new EdgeOptions());
                driver.manage().window().maximize();
            }
            default -> {
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.addArguments("--start-maximized");
                driver = new ChromeDriver(chromeOptions);
            }
        }

        driver.manage().timeouts().implicitlyWait(Duration.ZERO);
        driver.get(config.getProperty("base.url", "https://www.saucedemo.com/"));
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}