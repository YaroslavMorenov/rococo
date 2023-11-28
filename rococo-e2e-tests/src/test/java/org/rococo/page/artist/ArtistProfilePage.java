package org.rococo.page.artist;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$x;

public class ArtistProfilePage {

    private final SelenideElement artistName = $x("//header[contains (@class, 'card-header')]");
    private final SelenideElement biography = $x("//p[contains (@class, 'col-span')]");
    private final SelenideElement avatar = $x("//main//img[contains(@class, 'avatar-image')]");
    private final SelenideElement editButton = $x("//button[@data-testid = 'edit-artist']");

    @Step("Проверяет, что имя художника заполнено значением {val}")
    public ArtistProfilePage checkArtistName(String val) {
        artistName.shouldBe(text(val));
        return this;
    }

    @Step("Проверяет, что биография художника заполнено значением {val}")
    public ArtistProfilePage checkArtistBiography(String val) {
        biography.shouldBe(text(val));
        return this;
    }

    @Step("Проверяет, что \"Аватар хуоджника\" не пустой")
    public ArtistProfilePage checkArtistAvatarNotEmpty() {
        avatar.shouldBe(attributeMatching("src", "data:image*.*"));
        return this;
    }

    @Step("Нажать кнопку \"Редактировать\"")
    public ArtistPage clickButtonEdit() {
        editButton.click();
        return new ArtistPage();
    }

    @Step("Проверяет, что кнопка \"Редактировать\" не отображается")
    public ArtistProfilePage checkButtonEditNotVisible() {
        editButton.shouldNotBe(visible);
        return this;
    }
}
