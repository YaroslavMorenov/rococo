package org.rococo.test.web.notAutorized;

import io.qameta.allure.AllureId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.rococo.jupiter.annotation.Artist;
import org.rococo.jupiter.annotation.Museum;
import org.rococo.jupiter.annotation.Painting;
import org.rococo.model.ArtistJson;
import org.rococo.model.MuseumJson;
import org.rococo.model.PaintingJson;
import org.rococo.test.web.BaseWebTest;

import static com.codeborne.selenide.Selenide.open;

@DisplayName("[WEB]: Просмотр страниц без авторизации")
public class WithOutAutorizeTests extends BaseWebTest {

    @Test
    @AllureId("70")
    @DisplayName("WEB: Пользователь может просматривать художника без редактирования")
    public void userCanViewAristWithOutAutorize(@Artist ArtistJson artistJson) {
        open(url);
        String artistName = artistJson.getName();
        mainPage
                .openArtists()
                .fillSearchArtist(artistName)
                .clickButtonSearchArtist()
                .openArtistWithName(artistName)
                .checkArtistAvatarNotEmpty()
                .checkArtistBiography(artistJson.getBiography())
                .checkArtistName(artistName)
                .checkButtonEditNotVisible();
    }

    @Test
    @AllureId("71")
    @DisplayName("WEB: Пользователь может просматривать музей без редактирования")
    public void userCanViewMuseumWithOutAutorize(@Museum MuseumJson museumJson) {
        open(url);
        String museumName = museumJson.getTitle();
        mainPage
                .openMuseums()
                .fillSearchMuseum(museumName)
                .clickButtonSearchMuseum()
                .openMuseumWithName(museumName)
                .checkMuseumAvatarNotEmpty()
                .checkMuseumDescription(museumJson.getDescription())
                .checkMuseumTitle(museumName)
                .checkMuseumCountry(museumJson.getGeo().getCity())
                .checkButtonEditNotVisible();
    }

    @Test
    @AllureId("72")
    @DisplayName("WEB: Пользователь может просматривать картину без редактирования")
    public void userCanViewPaintingWithOutAutorize(@Painting PaintingJson paintingJson) {
        open(url);
        String paintingName = paintingJson.getTitle();
        mainPage
                .openPaintings()
                .fillSearchPainting(paintingName)
                .clickButtonSearchPainting()
                .openPaintingWithName(paintingName)
                .checkPaintingTitle(paintingName)
                .checkPaintingDescription(paintingJson.getDescription())
                .checkPaintingAvatarNotEmpty()
                .checkPaintingArtist(paintingJson.getArtist().getName())
                .checkButtonEditNotVisible();
    }
}
