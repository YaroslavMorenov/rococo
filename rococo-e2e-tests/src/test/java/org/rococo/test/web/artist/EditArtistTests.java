package org.rococo.test.web.artist;

import io.qameta.allure.AllureId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.rococo.jupiter.annotation.ApiLogin;
import org.rococo.jupiter.annotation.Artist;
import org.rococo.jupiter.annotation.DBUser;
import org.rococo.model.ArtistJson;
import org.rococo.page.artist.ArtistProfilePage;
import org.rococo.test.web.BaseWebTest;

import static org.rococo.util.FakerUtils.*;

@DisplayName("[WEB]: Редактирование художника")
public class EditArtistTests extends BaseWebTest {

    @Test
    @AllureId("20")
    @DBUser
    @ApiLogin
    @DisplayName("WEB: Пользователь может отредактировать все поля у художника")
    void artistShouldBeUpdated(@Artist ArtistJson artist) {
        String firstName = generateRandomName();
        String biography = generateRandomSentence(30);
        mainPage
                .openArtists()
                .openArtistWithName(artist.getName())
                .clickButtonEdit()
                .fillAristName(firstName)
                .fillAristBiography(biography)
                .clickButtonAdd()
                .checkToastIsVisible()
                .checkToastHaveText("Обновлен художник: %s".formatted(firstName))
                .checkToastShouldBeDisappear();
        new ArtistProfilePage()
                .checkArtistName(firstName)
                .checkArtistBiography(biography);
    }

    @Test
    @AllureId("21")
    @DBUser
    @ApiLogin
    @DisplayName("WEB: Пользователь может закрыть окно редактирования")
    void closeEditProfilePage(@Artist ArtistJson artist) {
        mainPage
                .openArtists()
                .openArtistWithName(artist.getName())
                .clickButtonEdit()
                .clickButtonClose();
        new ArtistProfilePage()
                .checkArtistAvatarNotEmpty()
                .checkArtistBiography(artist.getBiography())
                .checkArtistName(artist.getName());
    }

    @Test
    @AllureId("22")
    @DBUser
    @ApiLogin
    @DisplayName("WEB: Валидация поля биография")
    void artistBiographyShouldBeMoreSymbols(@Artist ArtistJson artist) {
        String biography = generateRandomString(9);
        mainPage
                .openArtists()
                .openArtistWithName(artist.getName())
                .clickButtonEdit()
                .fillAristBiography(biography)
                .clickButtonAdd()
                .checkValidationOnBiography();
    }
}
