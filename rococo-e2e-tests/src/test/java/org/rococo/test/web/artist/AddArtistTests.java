package org.rococo.test.web.artist;

import io.qameta.allure.AllureId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.rococo.jupiter.annotation.ApiLogin;
import org.rococo.jupiter.annotation.Artist;
import org.rococo.jupiter.annotation.DBUser;
import org.rococo.model.ArtistJson;
import org.rococo.page.artist.ArtistPage;
import org.rococo.test.web.BaseWebTest;
import org.rococo.util.FileReaderUtils;
import org.rococo.util.FilesPath;

import java.io.File;

import static org.rococo.util.FakerUtils.*;

@DisplayName("[WEB]: Создание художника")
public class AddArtistTests extends BaseWebTest {

    @Test
    @AllureId("10")
    @DBUser
    @ApiLogin
    @DisplayName("WEB: Создание художника")
    void artistShouldBeAdded() {
        String artistName = generateRandomName();
        String biography = generateRandomSentence(30);
        File avatar = new File(FileReaderUtils.getPath(FilesPath.ARTIST_AVATAR.getFileName()));
        mainPage
                .openArtists()
                .clickButtonAddArtist()
                .fillAristName(artistName)
                .uploadArtistAvatar(avatar)
                .fillAristBiography(biography)
                .clickButtonAdd()
                .openArtistWithName(artistName)
                .checkArtistName(artistName)
                .checkArtistAvatarNotEmpty()
                .checkArtistBiography(biography);
    }

    @Test
    @AllureId("11")
    @DBUser
    @ApiLogin
    @DisplayName("WEB: Валидация биографии")
    void artistBiographyShouldBeMoreSymbols() {
        String artistName = generateRandomName();
        String biography = generateRandomString(9);
        File avatar = new File(FileReaderUtils.getPath(FilesPath.ARTIST_AVATAR.getFileName()));
        mainPage
                .openArtists()
                .clickButtonAddArtist()
                .fillAristName(artistName)
                .uploadArtistAvatar(avatar)
                .fillAristBiography(biography)
                .clickButtonAdd();
        new ArtistPage()
                .checkValidationOnBiography();
    }

    @Test
    @AllureId("12")
    @DBUser
    @ApiLogin
    @DisplayName("WEB: Художников с одним именем быть не должно")
    void existingArtistShouldNotBeCreated(@Artist ArtistJson artist) {
        File avatar = new File(FileReaderUtils.getPath(FilesPath.ARTIST_AVATAR.getFileName()));
        mainPage
                .openArtists()
                .clickButtonAddArtist()
                .fillAristName(artist.getName())
                .uploadArtistAvatar(avatar)
                .fillAristBiography(artist.getBiography())
                .clickButtonAdd();
        mainPage.checkToastIsVisible()
                .checkToastHaveText("Cannot read properties of undefined")
                .checkToastShouldBeDisappear();
    }

    @Test
    @AllureId("13")
    @DBUser
    @ApiLogin
    @DisplayName("WEB: Поиск художника")
    void searchArtist(@Artist ArtistJson artist) {
        mainPage
                .openArtists()
                .fillSearchArtist(artist.getName())
                .clickButtonSearchArtist()
                .checkNamesArtists(artist.getName());
    }
}
