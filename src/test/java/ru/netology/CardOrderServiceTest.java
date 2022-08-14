package ru.netology;


import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;


public class CardOrderServiceTest {

    @BeforeEach
    void setUp() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999/");
    }

    @AfterEach
    void memoryClear() {
        clearBrowserCookies();
        clearBrowserLocalStorage();
    }

    @Test
    void shouldTestNameFieldUsingRussianY() {
        $("input[name='name']").setValue("Йорик Кнаус");
        $("input[type='tel']").setValue("+79995551122");
        $("[data-test-id=agreement]").click();
        $x("//button").click();
        $("[data-test-id=order-success]").shouldHave(exactText("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время."));
    }

    @Test
    void shouldTestNameFieldUsingRussianYo() {
        $("input[name='name']").setValue("Алёна Мирова");
        $("input[type='tel']").setValue("+79995551122");
        $("[data-test-id=agreement]").click();
        $x("//button").click();
        $("[data-test-id=order-success]").shouldHave(exactText("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время."));
        screenshot("shouldTestNameFieldUsingRussianYo");
    }

    @Test
    void shouldTestNameFieldUsingDash() {
        $("input[name='name']").setValue("Дарья Мирова-Мирченко");
        $("input[type='tel']").setValue("+79995551122");
        $("[data-test-id=agreement]").click();
        $x("//button").click();
        $("[data-test-id=order-success]").shouldHave(exactText("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время."));
    }

    @Test
    void shouldTestEmptyNameField() {
        $("input[name='name']").setValue("");
        $("input[type='tel']").setValue("+79995551122");
        $("[data-test-id=agreement]").click();
        $x("//button").click();
        $("[data-test-id=name].input_invalid span.input__sub").shouldHave(exactText("Поле обязательно для заполнения"));
    }

    @Test
    void shouldTestCamelCaseName() {
        $("input[name='name']").setValue("АнДрЕй СмИРНОв");
        $("input[type='tel']").setValue("+79995551122");
        $("[data-test-id=agreement]").click();
        $x("//button").click();
        $("[data-test-id=order-success]").shouldHave(exactText("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время."));
    }

    @Test
    void shouldTestNameWithNumbers() {
        $("input[name='name']").setValue("АР2Д2");
        $("input[type='tel']").setValue("+79995551122");
        $("[data-test-id=agreement]").click();
        $x("//button").click();
        $("[data-test-id=name].input_invalid span.input__sub").shouldHave(exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    void shouldTestLatinNames() {
        $("input[name='name']").setValue("JuanMoralezDeMariaLuizaGonzelez");
        $("input[type='tel']").setValue("+79995551122");
        $("[data-test-id=agreement]").click();
        $x("//button").click();
        $("[data-test-id=name].input_invalid span.input__sub").shouldHave(exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    void shouldTestSpacesInNames() {
        $("input[name='name']").setValue("  Пашка   Фейсконтроль  ");
        $("input[type='tel']").setValue("+79995551122");
        $("[data-test-id=agreement]").click();
        $x("//button").click();
        $("[data-test-id=order-success]").shouldHave(exactText("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время."));
    }

    @Test
    void shouldTestSymbols() {
        $("input[name='name']").setValue("О!Р=И(АНН*А");
        $("input[type='tel']").setValue("+79995551122");
        $("[data-test-id=agreement]").click();
        $x("//button").click();
        $("[data-test-id=name].input_invalid span.input__sub").shouldHave(exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    void shouldTestEmptyЕTel() {
        $("input[name='name']").setValue("Привет");
        $("input[type='tel']").setValue("");
        $("[data-test-id=agreement]").click();
        $x("//button").click();
        $("[data-test-id=phone].input_invalid span.input__sub").shouldHave(exactText("Поле обязательно для заполнения"));

    }

    @Test
    void shouldTestTelStartsFrom8() {
        $("input[name='name']").setValue("Привет");
        $("input[type='tel']").setValue("88005002321");
        $("[data-test-id=agreement]").click();
        $x("//button").click();
        $("[data-test-id=phone].input_invalid span.input__sub").shouldHave(exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));

    }

    @Test
    void shouldTestTelLessThan10Numbers() {
        $("input[name='name']").setValue("Привет");
        $("input[type='tel']").setValue("+7123456789");
        $("[data-test-id=agreement]").click();
        $x("//button").click();
        $("[data-test-id=phone].input_invalid span.input__sub").shouldHave(exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));

    }

    @Test
    void shouldTestTel1Number() {
        $("input[name='name']").setValue("Привет");
        $("input[type='tel']").setValue("7");
        $("[data-test-id=agreement]").click();
        $x("//button").click();
        $("[data-test-id=phone].input_invalid span.input__sub").shouldHave(exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));

    }

    @Test
    void shouldTestTelWithCommas() {
        $("input[name='name']").setValue("Привет");
        $("input[type='tel']").setValue("+7.915.123.78.89");
        $("[data-test-id=agreement]").click();
        $x("//button").click();
        $("[data-test-id=phone].input_invalid span.input__sub").shouldHave(exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));

    }

    @Test
    void shouldTestTelWithDashes() {
        $("input[name='name']").setValue("Привет");
        $("input[type='tel']").setValue("+7-915-123-78-89");
        $("[data-test-id=agreement]").click();
        $x("//button").click();
        $("[data-test-id=phone].input_invalid span.input__sub").shouldHave(exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));

    }

    @Test
    void shouldTestTelWithLetter() {
        $("input[name='name']").setValue("Привет");
        $("input[type='tel']").setValue("+7один23456789QA");
        $("[data-test-id=agreement]").click();
        $x("//button").click();
        $("[data-test-id=phone].input_invalid span.input__sub").shouldHave(exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));

    }

    @Test
    void shouldTestAgreementBox() {
        $("input[name='name']").setValue("Олды оглы");
        $("input[type='tel']").setValue("+79876541232");
        $("[data-test-id=agreement]").click();
        $("[data-test-id=agreement].checkbox_checked").isEnabled();
        $x("//button").click();
        $("[data-test-id=order-success]").shouldHave(exactText("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время."));
    }

    @Test
    void shouldTestAgreementBoxUnchecked() {
        $("input[name='name']").setValue("Олды оглы");
        $("input[type='tel']").setValue("+79876541232");
        $("[data-test-id=agreement]").doubleClick();
        $x("//button").click();
        $("label.input_invalid").shouldBe(visible);
    }

}
