import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import static java.lang.Thread.sleep;
import static org.assertj.core.api.Assertions.assertThat;

class SimpleTest {
    WebDriver driver;

    WebDriverManager wdm = WebDriverManager.chromedriver().browserInDocker()
            .enableVnc().enableRecording();
    @BeforeAll
    static void setupAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setup() {
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    @AfterEach
    void teardown() {
        driver.quit();
    }

    @Test
    void test() throws InterruptedException {
        int sleep = 1000;
        driver.get("http://localhost:3000/");
        driver.findElement(By.cssSelector("[aria-label=\"Close Welcome Banner\"]")).click();
        driver.findElement(By.id("navbarAccount")).click();
        driver.findElement(By.id("navbarLoginButton")).click();
        driver.findElement(By.id("email")).sendKeys("user@test.com");
        driver.findElement(By.id("password")).sendKeys("12345");
        driver.findElement(By.id("loginButton")).click();
        driver.findElement(By.id("navbarAccount")).click();
        assertThat(driver.findElement(By.id("navbarLogoutButton")).isDisplayed()).isTrue();
        assertThat(driver.getTitle()).contains("OWASP Juice Shop");
    }
}