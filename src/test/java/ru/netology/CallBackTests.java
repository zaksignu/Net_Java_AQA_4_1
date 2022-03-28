package ru.netology;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static com.codeborne.selenide.Selenide.open;

public class CallBackTests {
    private WebDriver driver;

    @BeforeAll
    public static void firstSetUp() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void setUP() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
    }

    @AfterEach
    public void finishOne() {
        driver.quit();
        driver = null;

    }
    @Test
    void shouldSubmit(){
        open("http://localhost:9999");


    }
}
