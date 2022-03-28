package ru.netology;

import com.codeborne.selenide.Condition;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
public class CallBackTests {
    private WebDriver driver;

    @BeforeAll
    public static void firstSetUp() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void setUP() {
//        ChromeOptions options = new ChromeOptions();
//        options.addArguments("--disable-dev-shm-usage");
//        options.addArguments("--no-sandbox");
//        options.addArguments("--headless");
//        driver = new ChromeDriver(options);
        driver = new ChromeDriver();
    }

    @AfterEach
    public void finishOne() {
        driver.quit();
        driver = null;

    }
    @Test
    void shouldSubmit(){
        open("http://localhost:9999");
        $("[data-test-id=\"city\"] .input__control").setValue("Петрозаводск");
  //      $("[data-test-id=\"date\"] .input__control").click();

        $("[data-test-id=\"date\"] .input__control").clear();
        $("[data-test-id=\"date\"] .input__control").setValue("01.04.2022");
  //      $("[data-test-id=\"date\"] .input__control").click();
        $("[data-test-id=\"name\"] .input__control").setValue("Пупкин Василий");
        $("[data-test-id=\"phone\"] .input__control").setValue("+79123456789");
        $("[data-test-id=\"agreement\"] .checkbox__box").click();
//        $(".form-field .button__content").click();
//        $(".form-field .button__content").wait();

        $("[data-test-id=\"notification\"] .notification__title").shouldHave(Condition.matchText("Успешно"));

        //    $("[data-test-id=\"notification\"] .notification__title").shouldHave("")getText()
     //   data-test-id="notification"
  //      $("[data-test-id=\"city\"] .input__control").click();

        System.out.println();
    }
}
