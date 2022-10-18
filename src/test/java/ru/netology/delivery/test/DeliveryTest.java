package ru.netology.delivery.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import ru.netology.delivery.data.DataGenerator;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class DeliveryTest {

    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }

    @Test
    @DisplayName("Should successful plan and replan meeting")
    void shouldSuccessfulPlanAndReplanMeeting() {

        $("[data-test-id=city] input").setValue(DataGenerator.setCity());
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        var daysToAddForFirstMeeting = 4;
        var firstMeetingDate = DataGenerator.setDate(daysToAddForFirstMeeting);
        $("[data-test-id=date] input").setValue(DataGenerator.setDate(daysToAddForFirstMeeting));
        $("[data-test-id=name] input").setValue(DataGenerator.setName());
        $("[data-test-id=phone] input").setValue(DataGenerator.setPhone());
        $("[data-test-id=agreement]").click();
        $(withText("Запланировать")).click();
        $("[data-test-id=success-notification]").shouldBe((visible)).shouldHave(text("Успешно!"));
        String date = $("[data-test-id=date] input").getValue();
        String text = $("[data-test-id='success-notification'] .notification__content").text();
        assertEquals("Встреча успешно запланирована на " + date, text);

        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        var daysToAddForSecondMeeting = 7;
        $("[data-test-id=date] input").setValue(DataGenerator.setDate(daysToAddForSecondMeeting));
        $(withText("Запланировать")).click();
        $("[data-test-id=replan-notification]").shouldBe((visible)).shouldHave(text("Необходимо подтверждение"));
        $(withText("Перепланировать")).click();
        $("[data-test-id=success-notification]").shouldBe((visible)).shouldHave(text("Успешно!"));
        String expected = $("[data-test-id=date] input").getValue();
        String actual = $("[data-test-id='success-notification'] .notification__content").text();
        assertEquals("Встреча успешно запланирована на " + expected, actual);
    }
}