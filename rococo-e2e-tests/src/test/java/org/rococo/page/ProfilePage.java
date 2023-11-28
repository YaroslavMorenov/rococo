package org.rococo.page;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import java.io.File;

import static com.codeborne.selenide.Condition.attribute;
import static com.codeborne.selenide.Condition.attributeMatching;
import static com.codeborne.selenide.Selenide.$x;

public class ProfilePage {

    private final SelenideElement firstName = $x("//input[@name = 'firstname']");
    private final SelenideElement lastName = $x("//input[@name = 'surname']");
    private final SelenideElement uploadAvatar = $x("//input[@type= 'file']");
    private final SelenideElement updatedProfileButton = $x("//button[@class = 'btn variant-filled-primary']");
    private final SelenideElement closeProfileButton = $x("//button[@class = 'btn variant-ringed']");
    private final SelenideElement logoutButton = $x("//button[@class = 'btn variant-ghost']");
    private final SelenideElement avatar = $x("//div[@role = 'dialog']//img[contains(@class, 'avatar-image')]");

    @Step("Заполнить поле \"Имя\"")
    public ProfilePage fillFirstName(String name) {
        firstName.val(name);
        return this;
    }

    @Step("Проверяет, что поле \"Имя\" заполнено {value}")
    public ProfilePage checkValueFirstName(String value) {
        firstName.shouldBe(attribute("value", value));
        return this;
    }

    @Step("Заполнить поле \"Фамилия\"")
    public ProfilePage fillLastName(String name) {
        lastName.val(name);
        return this;
    }

    @Step("Проверяет, что поле \"Фамилия\" заполнено {value}")
    public ProfilePage checkValueLastNameName(String value) {
        lastName.shouldBe(attribute("value", value));
        return this;
    }

    @Step("Загрузить \"Аватар\"")
    public ProfilePage uploadAvatar(File file) {
        uploadAvatar.uploadFile(file);
        return this;
    }

    @Step("Проверяет, что \"Аватар\" не пустой")
    public ProfilePage checkAvatarNotEmpty() {
        avatar.shouldBe(attributeMatching("src", "data:image*.*"));
        return this;
    }

    @Step("Нажать кнопку \"Обновить профиль\"")
    public MainPage clickButtonUpdateProfile() {
        updatedProfileButton.click();
        return new MainPage();
    }

    @Step("Нажать кнопку \"Выйти\"")
    public MainPage clickButtonLogout() {
        logoutButton.click();
        return new MainPage();
    }

    @Step("Нажать кнопку \"Закрыть\"")
    public MainPage clickButtonExit() {
        closeProfileButton.click();
        return new MainPage();
    }
}
