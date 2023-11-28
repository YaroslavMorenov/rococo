package org.rococo.page.artist;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.rococo.page.components.ToastElement;

import java.io.File;

import static com.codeborne.selenide.CollectionCondition.texts;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$$x;
import static com.codeborne.selenide.Selenide.$x;

public class ArtistPage implements ToastElement<ArtistPage> {

    private final SelenideElement addArtist = $x("//button[text() = 'Добавить художника']");
    private final SelenideElement artistNameField = $x("//input[@name = 'name']");
    private final SelenideElement uploadArtistAvatarField = $x("//input[@type = 'file']");
    private final SelenideElement biographyArtistField = $x("//textarea[@name = 'biography']");
    private final SelenideElement addArtistButton = $x("//button[@class = 'btn variant-filled-primary']");
    private final SelenideElement closeAddArtistButton = $x("//button[@class = 'btn variant-ringed']");
    private final SelenideElement searchArtistField = $x("//input[@title= 'Искать художников...']");
    private final SelenideElement searchButton = $x("//img[@alt = 'Иконка поиска']");
    private final ElementsCollection artistsList = $$x("//a[contains (@href, 'artist/')]");


    @Step("Нажать кнопку \"Добавить художника\"")
    public ArtistPage clickButtonAddArtist() {
        addArtist.click();
        return this;
    }

    @Step("Заполнить поле \"Имя\"")
    public ArtistPage fillAristName(String name) {
        artistNameField.val(name);
        return this;
    }

    @Step("Загрузить \"Изображение художника\"")
    public ArtistPage uploadArtistAvatar(File file) {
        uploadArtistAvatarField.uploadFile(file);
        return this;
    }

    @Step("Заполнить поле \"Биография\"")
    public ArtistPage fillAristBiography(String name) {
        biographyArtistField.val(name);
        return this;
    }

    @Step("Заполнить поле \"Поиск художника\"")
    public ArtistPage fillSearchArtist(String name) {
        searchArtistField.val(name);
        return this;
    }

    @Step("Проверяет ошибку валидации под полем \"Биография\"")
    public ArtistPage checkValidationOnBiography() {
        biographyArtistField.$x("./following-sibling::span").shouldBe(text("Биография не может быть короче 10 символов"));
        return this;
    }

    @Step("Нажать кнопку \"Добавить\"")
    public ArtistPage clickButtonAdd() {
        addArtistButton.click();
        return this;
    }

    @Step("Нажать кнопку \"Закрыть\"")
    public ArtistPage clickButtonClose() {
        closeAddArtistButton.click();
        return this;
    }

    @Step("Нажать кнопку \"Поиск хужожников\"")
    public ArtistPage clickButtonSearchArtist() {
        searchButton.click();
        return this;
    }

    @Step("Открыть художника с именем {artistName}")
    public ArtistProfilePage openArtistWithName(String artistName) {
        artistsList.find(text(artistName)).click();
        return new ArtistProfilePage();
    }

    @Step("Проверяет, что отображаются художники с именами {names}")
    public ArtistPage checkNamesArtists(String... names) {
        artistsList.shouldBe(texts(names));
        return this;
    }
}
