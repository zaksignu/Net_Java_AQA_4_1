package ru.netology;

import com.codeborne.selenide.Condition;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Selenide.*;

public class CallBackTests {




    String generateDate(int days) {
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    @BeforeEach
    public void setUP() {
        open("http://localhost:9999");
    }

    @Test
    public void shouldWorkHappyPAth() {
        String planningDate = generateDate(3);
        $("[data-test-id=\"city\"] .input__control").setValue("Петрозаводск");
        $("[data-test-id=\"date\"] .input__control").doubleClick().sendKeys(planningDate);
        $("[data-test-id=\"name\"] .input__control").setValue("Пупкин Василий");
        $("[data-test-id=\"phone\"] .input__control").setValue("+79123456789");
        $("[data-test-id=\"agreement\"] .checkbox__box").click();
        $(".form-field .button__content").click();
        $("[data-test-id=\"notification\"] .notification__title").should(Condition.visible, Duration.ofSeconds(15));
        $("[class='notification__content']").shouldHave(Condition.text("Встреча успешно забронирована на " + planningDate), Duration.ofSeconds(15));
    }

    @Test
    public void shouldWorkDropDownCity() {
        $("[data-test-id=\"city\"] .input__control").setValue("ро");
        $$(".menu-item .menu-item__control").filter(Condition.visible).first().click();
        $("[data-test-id=\"city\"] .input__control").shouldHave(Condition.value("Белгород"));
    }

    @Test
    public void shouldWorkCalendarChoose() {
        String secondPlanningDate = generateDate(30);
        $("[data-test-id=\"city\"] .input__control").setValue("Петрозаводск");
        $("[data-test-id=\"date\"] .input__control").doubleClick().sendKeys(secondPlanningDate);
        $("[data-test-id=\"name\"] .input__control").setValue("Пупкин Василий");
        $("[data-test-id=\"phone\"] .input__control").setValue("+79123456789");
        $("[data-test-id=\"agreement\"] .checkbox__box").click();
        $(".form-field .button__content").click();
        $("[class='notification__content']").shouldHave(Condition.text("Встреча успешно забронирована на " + secondPlanningDate), Duration.ofSeconds(15));

    }
}
