package finalproject.utils;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

public class TestUtils {
    public static void setUp() {

        Configuration.browser = "chrome";
        Configuration.browserSize = "1920x1080";
        Configuration.headless = false;

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        options.addArguments("--disable-infobars");
        options.addArguments("--disable-popup-blocking");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");

        RemoteWebDriver driver = new ChromeDriver(options);
        WebDriverRunner.setWebDriver(driver);

        driver.manage().window().maximize();
    }

    public static void tearDown() {
        Selenide.closeWebDriver();
    }
}