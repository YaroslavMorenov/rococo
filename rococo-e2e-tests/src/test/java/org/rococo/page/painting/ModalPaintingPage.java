package org.rococo.page.painting;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import java.io.File;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$x;

public class ModalPaintingPage {

    private final SelenideElement paintingTitle = $x("//input[@name = 'title']");
    private final SelenideElement uploadPaintingAvatarField = $x("//input[@type = 'file']");
    private final SelenideElement selectArtist = $x("//select[@name = 'authorId']");
    private final SelenideElement descriptionPainting = $x("//textarea[@name = 'description']");
    private final SelenideElement selectMuseum = $x("//select[@name = 'museumId']");
    private final SelenideElement addButton = $x("//button[text() = 'Добавить']");
    private final SelenideElement saveButton = $x("//button[text() = 'Сохранить']");
    private final SelenideElement closeButton = $x("//button[text() = 'Закрыть']");

    @Step("Заполнить поле \"Название картины\" {0}")
    public ModalPaintingPage fillPaintingTitle(String title) {
        paintingTitle.val(title);
        return this;
    }

    @Step("Загрузить \"Изображение картины\"")
    public ModalPaintingPage uploadPaintingAvatar(File file) {
        uploadPaintingAvatarField.uploadFile(file);
        return this;
    }

    @Step("Выбрать \"Автора картины\" {0}")
    public ModalPaintingPage selectArtist(String authorName) {
        selectArtist.selectOption(authorName);
        return this;
    }

    @Step("Выбрать \"Где хранится оригинал\" {0}")
    public ModalPaintingPage selectMuseum(String museumName) {
        selectMuseum.selectOption(museumName);
        return this;
    }

    @Step("Заполнить поле \"Описание картины\"")
    public ModalPaintingPage fillDescription(String description) {
        descriptionPainting.val(description);
        return this;
    }

    @Step("Проверяет ошибку валидации под полем \"Описание\"")
    public ModalPaintingPage checkValidationOnDescription() {
        descriptionPainting.$x("./following-sibling::span").shouldBe(text("Описание не может быть короче 10 символов"));
        return this;
    }

    @Step("Нажать кнопку \"Добавить\"")
    public PaintingPage clickButtonAdd() {
        addButton.click();
        return new PaintingPage();
    }

    @Step("Нажать кнопку \"Сохранить\"")
    public PaintingProfilePage clickButtonSave() {
        saveButton.click();
        return new PaintingProfilePage();
    }

    @Step("Нажать кнопку \"Закрыть\"")
    public PaintingProfilePage clickButtonClose() {
        closeButton.click();
        return new PaintingProfilePage();
    }
}
