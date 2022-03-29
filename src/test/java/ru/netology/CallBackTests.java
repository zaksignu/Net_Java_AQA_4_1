package ru.netology;

import com.codeborne.selenide.Condition;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Selenide.*;

public class CallBackTests {
    private WebDriver driver;
    static LocalDate date = LocalDate.now();
    static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

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
        open("http://localhost:9999");
    }

    @AfterEach
    public void finishOne() {
        driver.quit();
        driver = null;

    }

    @Test
    public void shouldWorkHappyPAth() {
        $("[data-test-id=\"city\"] .input__control").setValue("Петрозаводск");
        $("[data-test-id=\"date\"] .input__control").doubleClick().sendKeys(date.plusDays(5).format(formatter));
        $("[data-test-id=\"name\"] .input__control").setValue("Пупкин Василий");
        $("[data-test-id=\"phone\"] .input__control").setValue("+79123456789");
        $("[data-test-id=\"agreement\"] .checkbox__box").click();
        $(".form-field .button__content").click();
        $("[data-test-id=\"notification\"] .notification__title").should(Condition.visible, Duration.ofSeconds(15));
    }

    @Test
    public void shouldWorkDropDownCity() {
        $("[data-test-id=\"city\"] .input__control").setValue("ро");
        $$(".menu-item .menu-item__control").filter(Condition.visible).first().click();
        $("[data-test-id=\"city\"] .input__control").shouldHave(Condition.value("Белгород"));
    }

    @Test
    public void shouldWorkCalendarChoose() {
        $("[data-test-id=\"date\"] .input__control").doubleClick().sendKeys(date.plusDays(7).format(formatter));
        $("[data-test-id=\"city\"] .input__control").click();
        $("[data-test-id=\"date\"] .input__control").shouldHave(Condition.value(date.plusDays(7).format(formatter)), Duration.ofSeconds(1));
    }
}
