package org.rococo.page.components;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$x;
import static java.time.Duration.ofSeconds;

public interface ToastElement<T> {

    SelenideElement toast = $x("//div[@data-testid = 'toast']");

    @Step("Проверяет, что тост отображается")
    default T checkToastIsVisible() {
        toast.shouldBe(visible);
        return (T)this;
    }

    @Step("Проверяет, что тост содержит текст {value}")
    default T checkToastHaveText(String value) {
        toast.shouldBe(text(value));
        return (T)this;
    }

    @Step("Проверяет, что тост пропал")
    default T checkToastShouldBeDisappear() {
        toast.shouldBe(disappear, ofSeconds(10));
        return (T)this;
    }
}
