package ru.netology;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CardOrderServiceTest {

    @BeforeEach
    void setUp() {
        Configuration.holdBrowserOpen = true;
    }

    @AfterEach
    void memoryClear() {
        clearBrowserCookies();
        clearBrowserLocalStorage();
    }

    @Test
    void shouldTestNameFieldUsingRussianY() {
        open("http://localhost:9999/");

        $("input[name='name']").setValue("Йорик Кнаус");
        //$('.input__sub') - баг не проходит
        $("input[type='tel']").setValue("+79995551122");
        $x("//span[@class='checkbox__box']").click();
        $x("//button").click();
        String expected = ("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.");
        String actual = $("[data-test-id]").getText().trim();
        assertEquals(expected, actual);

    }

    @Test
    void shouldTestNameFieldUsingRussianYo() {
        open("http://localhost:9999/");

        $("input[name='name']").setValue("Алёна Мирова");
        $("input[type='tel']").setValue("+79995551122");
        $x("//span[@class='checkbox__box']").click();
        $x("//button").click();
        boolean actual = $(".paragraph[data-test-id]").isDisplayed();

        assertEquals(true, actual, "Не валидирует букву Ё в поле имя, баг");

    }

    @Test
    void shouldTestNameFieldUsingDash() {
        open("http://localhost:9999/");

        $("input[name='name']").setValue("Дарья Мирова-Мирченко");
        $("input[type='tel']").setValue("+79995551122");
        $x("//span[@class='checkbox__box']").click();
        $x("//button").click();
        boolean actual = $(".paragraph[data-test-id]").isDisplayed();

        assertEquals(true, actual);

    }

    @Test
    void shouldTestEmptyNameField() {
        open("http://localhost:9999/");

        $("input[name='name']").setValue("");

        $("input[type='tel']").setValue("+79995551122");
        $x("//span[@class='checkbox__box']").click();
        $x("//button").click();
        boolean actual = $(".input__sub").exists();

        assertEquals(true, actual);

    }

    @Test
    void shouldTestCamelCaseName() {
        open("http://localhost:9999/");

        $("input[name='name']").setValue("АнДрЕй СмИРНОв");

        $("input[type='tel']").setValue("+79995551122");
        $x("//span[@class='checkbox__box']").click();
        $x("//button").click();
        boolean actual = $(".paragraph[data-test-id]").isDisplayed();

        assertEquals(true, actual);

    }

    @Test
    void shouldTestNameWithNumbers() {
        open("http://localhost:9999/");

        $("input[name='name']").setValue("АР2Д2");
        boolean actualAdditional = $(".input__sub").exists();
        assertEquals(true, actualAdditional);

        $("input[type='tel']").setValue("+79995551122");
        $x("//span[@class='checkbox__box']").click();
        $x("//button").click();
        boolean actual = $(".paragraph[data-test-id]").isDisplayed();

        assertEquals(false, actual);

    }

    @Test
    void shouldTestLatinNames() {
        open("http://localhost:9999/");

        $("input[name='name']").setValue("JuanMoralezDeMariaLuizaGonzelez");
        boolean actualAdditional = $(".input__sub").exists();
        assertEquals(true, actualAdditional);

        $("input[type='tel']").setValue("+79995551122");
        $x("//span[@class='checkbox__box']").click();
        $x("//button").click();
        boolean actual = $(".paragraph[data-test-id]").isDisplayed();

        assertEquals(false, actual);

    }

    @Test
    void shouldTestSpacesInNames() {
        open("http://localhost:9999/");

        $("input[name='name']").sendKeys("  Пашка   Фейсконтроль  ");

        $("input[type='tel']").setValue("+79995551122");
        $x("//span[@class='checkbox__box']").click();
        $x("//button").click();

        boolean actual = $(".paragraph[data-test-id]").isDisplayed();
        assertEquals(true, actual);

    }

    @Test
    void shouldTestSymbols() {
        open("http://localhost:9999/");

        $("input[name='name']").sendKeys("О!Р=И(АНН*А");
        boolean actualAdditional = $(".input__sub").exists();
        assertEquals(true, actualAdditional);

        $("input[type='tel']").setValue("+79995551122");
        $x("//span[@class='checkbox__box']").click();
        $x("//button").click();
        boolean actual = $(".paragraph[data-test-id]").isDisplayed();

        assertEquals(false, actual);

    }

    @Test
    void shouldTestEmptyЕTel() {
        open("http://localhost:9999/");

        $("input[name='name']").sendKeys("Привет");

        $("input[type='tel']").setValue("");
        $x("//button").click();
        $$(".input__sub").get(1).shouldHave(exactText("Поле обязательно для заполнения"));

    }

    @Test
    void shouldTestTelStartsFrom8() {
        open("http://localhost:9999/");

        $("input[name='name']").sendKeys("Привет");

        $("input[type='tel']").setValue("88005002321");
        $x("//button").click();
        $$(".input__sub").get(1).shouldHave(exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));

    }

    @Test
    void shouldTestTelLessThan10Numbers() {
        open("http://localhost:9999/");

        $("input[name='name']").sendKeys("Привет");

        $("input[type='tel']").setValue("+7123456789");
        $x("//button").click();
        $$(".input__sub").get(1).shouldHave(exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));

    }

    @Test
    void shouldTestTel1Number() {
        open("http://localhost:9999/");

        $("input[name='name']").sendKeys("Привет");

        $("input[type='tel']").setValue("7");
        $x("//button").click();
        $$(".input__sub").get(1).shouldHave(exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));

    }

    @Test
    void shouldTestTelWithCommas() {
        open("http://localhost:9999/");

        $("input[name='name']").sendKeys("Привет");

        $("input[type='tel']").setValue("+7.915.123.78.89");
        $x("//button").click();
        $$(".input__sub").get(1).shouldHave(exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));

    }

    @Test
    void shouldTestTelWithletter() {
        open("http://localhost:9999/");

        $("input[name='name']").sendKeys("Привет");

        $("input[type='tel']").setValue("+7один23456789QA");
        $x("//button").click();
        $$(".input__sub").get(1).shouldHave(exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));

    }

    @Test
    void shouldTestAgreementBox() {
        open("http://localhost:9999/");

        $x("//span[@class='checkbox__box']").click();
        boolean actual = $(".checkbox_checked").isDisplayed();
        assertEquals(true, actual);
    }
}
