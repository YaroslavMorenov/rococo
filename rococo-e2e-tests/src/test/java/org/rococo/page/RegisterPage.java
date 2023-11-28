package org.rococo.page;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.rococo.page.login.LoginPage;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$x;

public class RegisterPage {

    private final SelenideElement userNameFld = $x("//input[@name = 'username']");
    private final SelenideElement passwordFld = $x("//input[@name = 'password']");
    private final SelenideElement confirmPasswordFld = $x("//input[@name = 'passwordSubmit']");
    private final SelenideElement signUpBtn = $x("//button[@type = 'submit']");
    private final SelenideElement formAfterRegister = $x("//p[@class = 'form__subheader']");
    private final SelenideElement formError = $x("//span[@class = 'form__error']");
    private final SelenideElement loginBtn = $x("//a[contains (@href, '/redirect')]");

    @Step("Заполнить поле \"Имя пользователя\"")
    public RegisterPage fillUserName(String userName) {
        userNameFld.setValue(userName);
        return this;
    }

    @Step("Заполнить поле \"Пароль\"")
    public RegisterPage fillPassword(String password) {
        passwordFld.setValue(password);
        return this;
    }

    @Step("Заполнить поле \"Повторите пароль\"")
    public RegisterPage fillConfirmPassword(String password) {
        confirmPasswordFld.setValue(password);
        return this;
    }

    @Step("Нажать кнопку \"Зарегестрироваться\"")
    public RegisterPage clickSignUp() {
        signUpBtn.click();
        return this;
    }

    @Step("Нажать кнопку \"Войти\"")
    public LoginPage clickLogin() {
        loginBtn.click();
        return new LoginPage();
    }

    @Step("Проверяет, что отображается подтверждение регистрации")
    public RegisterPage checkAlertAfterRegistration() {
        formAfterRegister.shouldBe(text("Добро пожаловать в Rococo"));
        return this;
    }

    @Step("Проверяет, что отображается сообщение об ошибке")
    public RegisterPage checkTextFromError(String error) {
        formError.shouldBe(text(error));
        return this;
    }
}
