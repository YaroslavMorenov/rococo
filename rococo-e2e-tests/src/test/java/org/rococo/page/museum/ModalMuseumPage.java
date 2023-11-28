package org.rococo.page.museum;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import java.io.File;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$x;

public class ModalMuseumPage {

    private final SelenideElement museumTitle = $x("//input[@name = 'title']");
    private final SelenideElement museumCity = $x("//input[@name = 'city']");
    private final SelenideElement uploadMuseumAvatarField = $x("//input[@type = 'file']");
    private final SelenideElement selectCountry = $x("//select[@name = 'countryId']");
    private final SelenideElement descriptionMuseum = $x("//textarea[@name = 'description']");
    private final SelenideElement addButton = $x("//button[text() = 'Добавить']");
    private final SelenideElement saveButton = $x("//button[text() = 'Сохранить']");
    private final SelenideElement closeButton = $x("//button[text() = 'Закрыть']");

    @Step("Заполнить поле \"Название музея\" {0}")
    public ModalMuseumPage fillMuseumTitle(String title) {
        museumTitle.val(title);
        return this;
    }

    @Step("Заполнить поле \"Город\" {0}")
    public ModalMuseumPage fillCity(String city) {
        museumCity.val(city);
        return this;
    }

    @Step("Выбрать \"Страну\" {0}")
    public ModalMuseumPage selectCountry(String countryName) {
        selectCountry.selectOption(countryName);
        return this;
    }

    @Step("Загрузить \"Изображение музея\"")
    public ModalMuseumPage uploadMuseumAvatar(File file) {
        uploadMuseumAvatarField.uploadFile(file);
        return this;
    }

    @Step("Заполнить поле \"О музее\"")
    public ModalMuseumPage fillDescription(String description) {
        descriptionMuseum.val(description);
        return this;
    }

    @Step("Проверяет ошибку валидации под полем \"Описание\"")
    public ModalMuseumPage checkValidationOnDescription() {
        descriptionMuseum.$x("./following-sibling::span").shouldBe(text("Описание не может быть короче 10 символов"));
        return this;
    }

    @Step("Нажать кнопку \"Добавить\"")
    public MuseumPage clickButtonAdd() {
        addButton.click();
        return new MuseumPage();
    }

    @Step("Нажать кнопку \"Сохранить\"")
    public MuseumProfilePage clickButtonSave() {
        saveButton.click();
        return new MuseumProfilePage();
    }

    @Step("Нажать кнопку \"Закрыть\"")
    public MuseumProfilePage clickButtonClose() {
        closeButton.click();
        return new MuseumProfilePage();
    }
}
