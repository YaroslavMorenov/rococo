package org.rococo.page.login;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.rococo.page.MainPage;
import org.rococo.page.RegisterPage;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public class LoginPage {

    private final SelenideElement loginFld = $x("//input[@name = 'username']");
    private final SelenideElement passwordFld = $x("//input[@name = 'password']");
    private final SelenideElement submitBtn = $("button[type='submit']");
    private final SelenideElement formError = $x("//p[@class = 'form__error']");
    private final SelenideElement registerBtn = $x("//a[contains (@href, '/register')]");

    @Step("Логин")
    public MainPage login(String username, String password) {
        fillLogin(username)
                .fillPassword(password)
                .clickSubmitBtn();
        return new MainPage();
    }

    @Step("Заполнить поле \"Логин\"")
    public LoginPage fillLogin(String username) {
        loginFld.setValue(username);
        return this;
    }

    @Step("Заполнить поле \"Пароль\"")
    public LoginPage fillPassword(String password) {
        passwordFld.setValue(password);
        return this;
    }

    @Step("Нажать кнопку \"Войти\"")
    public MainPage clickSubmitBtn() {
        submitBtn.click();
        return new MainPage();
    }

    @Step("Нажать кнопку \"Зарегистрироваться\"")
    public RegisterPage clickRegisterBtn() {
        registerBtn.click();
        return new RegisterPage();
    }

    @Step("Проверяет, что отображается сообщение об ошибке")
    public LoginPage checkTextFromError() {
        formError.shouldBe(exactText("Неверные учетные данные пользователя"));
        return this;
    }
}
