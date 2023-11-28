package org.rococo.page;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.rococo.page.artist.ArtistPage;
import org.rococo.page.components.ToastElement;
import org.rococo.page.login.LoginPage;
import org.rococo.page.museum.MuseumPage;
import org.rococo.page.painting.PaintingPage;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$x;

public class MainPage implements ToastElement<MainPage> {

    private final SelenideElement loginBtn = $x("//*[@class = 'btn variant-filled-primary']");
    private final SelenideElement avatar = $x("//figure[@data-testid = 'avatar']");
    private final SelenideElement artist = $x("//img[@alt = 'Ссылка на художников']");
    private final SelenideElement paintings = $x("//img[@alt = 'Ссылка на картины']");
    private final SelenideElement museums = $x("//img[@alt = 'Ссылка на музеи']");

    @Step("Нажать кнопку \"Войти\"")
    public LoginPage clickLoginBtn() {
        loginBtn.click();
        return new LoginPage();
    }

    @Step("Отображается кнопка \"Войти\"")
    public LoginPage loginButtonIsVisible() {
        loginBtn.shouldBe(visible);
        return new LoginPage();
    }

    @Step("Проверяет, что \"Аватар\" отображается")
    public MainPage checkAvatarIsPresent() {
        avatar.shouldBe(visible);
        return this;
    }

    @Step("Отккрыть раздел \"Картины\"")
    public PaintingPage openPaintings() {
        paintings.click();
        return new PaintingPage();
    }

    @Step("Отккрыть раздел \"Художники\"")
    public ArtistPage openArtists() {
        artist.click();
        return new ArtistPage();
    }

    @Step("Отккрыть раздел \"Музеи\"")
    public MuseumPage openMuseums() {
        museums.click();
        return new MuseumPage();
    }
}
