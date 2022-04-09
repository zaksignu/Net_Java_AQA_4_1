package ru.netology;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.time.*;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Selenide.*;

public class CallBackTests {

    int newDate = 55;
    String secondPlanningDate = generateDate(newDate);
    String parameterString = GeneratetSearchString (newDate);
    String planningDate = generateDate(3);

    String generateDate(int days) {
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }


    public String GeneratetSearchString(long actualMillis){

//        LocalTime time = LocalTime.parse("00:00:00");
//        LocalDate date = LocalDate.now();
//    //    ZoneOffset zone = ZoneOffset.of("00:00");
//        ZoneOffset zone = ZoneOffset.of("+04:00");
//        long millisDate = time.toEpochSecond(date.plusDays(days),zone);
//        System.out.println(millisDate+"____");
        String stringForReturn = ".calendar__row [data-day=\""+ Long.toString(actualMillis) + "\"]";
        return stringForReturn;

    }


    @BeforeEach
    public void setUP() {
        open("http://localhost:9999");
    }

    @Test
    public void shouldWorkHappyPAth() {
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
        $("[data-test-id=\"city\"] .input__control").setValue("горо");

        $$(".menu-item .menu-item__control").first().shouldHave(Condition.text("Белгород"));
        $$(".menu-item .menu-item__control").filter(Condition.visible).first().click();
        $("[data-test-id=\"city\"] .input__control").shouldHave(Condition.value("Белгород"));
        $("[data-test-id=\"date\"] .input__control").doubleClick().sendKeys(planningDate);
        $("[data-test-id=\"name\"] .input__control").setValue("Пупкин Василий");
        $("[data-test-id=\"phone\"] .input__control").setValue("+79123456789");
        $("[data-test-id=\"agreement\"] .checkbox__box").click();
        $(".form-field .button__content").click();
        $("[data-test-id=\"notification\"] .notification__title").should(Condition.visible, Duration.ofSeconds(15));
        $("[class='notification__content']").shouldHave(Condition.text("Встреча успешно забронирована на " + planningDate), Duration.ofSeconds(15));
    }

    @Test
    public void shouldWorkCalendarChoose() {

        $("[data-test-id=\"city\"] .input__control").setValue("Петрозаводск");
        $("[data-test-id=\"name\"] .input__control").setValue("Пупкин Василий");

        $(".input__box .icon-button__content").click();
        //получаем время в millis из даты календаря, выбранного в автомате
        String currentDayPlus3inMill = $(".calendar__row .calendar__day_state_current").getAttribute("data-day");
        // высчитываем, какую дату в millis надо найти исходя из соображений, что сутки это 86400000Мс, и учитывая
        // что currentDayPlus3inMill уже отстоит от текущей даты на 3 дня
        final long  neededDateInMillis = Long.parseLong(currentDayPlus3inMill)+86400000L*(newDate-3);

        while (!($(GeneratetSearchString(neededDateInMillis)).exists())){
            $(".calendar__title .calendar__arrow_direction_right[data-step=\"1\"]").click();
        }
        $(GeneratetSearchString(neededDateInMillis)).click();
        $("[data-test-id=\"date\"] .input__control").shouldHave(Condition.value(secondPlanningDate));

        $("[data-test-id=\"phone\"] .input__control").setValue("+79123456789");
        $("[data-test-id=\"agreement\"] .checkbox__box").click();
        $(".form-field .button__content").click();
        $("[class='notification__content']").shouldHave(Condition.text("Встреча успешно забронирована на " + secondPlanningDate), Duration.ofSeconds(15));

    }
}
