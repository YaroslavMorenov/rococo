package org.rococo.test.web.painting;

import io.qameta.allure.AllureId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.rococo.jupiter.annotation.ApiLogin;
import org.rococo.jupiter.annotation.DBUser;
import org.rococo.jupiter.annotation.Painting;
import org.rococo.model.PaintingJson;
import org.rococo.page.painting.ModalPaintingPage;
import org.rococo.test.web.BaseWebTest;
import org.rococo.util.FileReaderUtils;
import org.rococo.util.FilesPath;

import java.io.File;

import static org.rococo.util.FakerUtils.*;

@DisplayName("[WEB]: Создание картин")
public class AddPaintingTest extends BaseWebTest {

    @Test
    @AllureId("50")
    @DBUser
    @ApiLogin
    @DisplayName("WEB: Создание картины")
    void paintingShouldBeAdded(@Painting(isNeedToCreatePainting = false, isNeedToCreateMuseum = true)
                               PaintingJson paintingJson) {
        String title = generateRandomTitle();
        String description = generateRandomSentence(30);
        File avatar = new File(FileReaderUtils.getPath(FilesPath.PAINTING_AVATAR.getFileName()));
        mainPage
                .openPaintings()
                .clickButtonAddPainting()
                .fillPaintingTitle(title)
                .fillDescription(description)
                .uploadPaintingAvatar(avatar)
                .selectArtist(paintingJson.getArtist().getName())
                .selectMuseum(paintingJson.getMuseum().getTitle())
                .clickButtonAdd()
                .checkToastIsVisible()
                .checkToastHaveText("Добавлена картины: %s".formatted(title))
                .checkToastShouldBeDisappear();
    }

    @Test
    @AllureId("51")
    @DBUser
    @ApiLogin
    @DisplayName("WEB: Маппинг полей")
    void mappingPaintingFields(@Painting(isNeedToCreateMuseum = true)
                             PaintingJson paintingJson) {
        mainPage
                .openPaintings()
                .openPaintingWithName(paintingJson.getTitle())
                .checkPaintingTitle(paintingJson.getTitle())
                .checkPaintingDescription(paintingJson.getDescription())
                .checkPaintingAvatarNotEmpty()
                .checkPaintingArtist(paintingJson.getArtist().getName());
    }

    @Test
    @AllureId("52")
    @DBUser
    @ApiLogin
    @DisplayName("WEB: Валидация описания")
    void paintingDescriptionShouldBeMoreSymbols(@Painting(isNeedToCreatePainting = false) PaintingJson paintingJson) {
        String title = generateRandomTitle();
        String description = generateRandomString(9);
        File avatar = new File(FileReaderUtils.getPath(FilesPath.PAINTING_AVATAR.getFileName()));
        mainPage
                .openPaintings()
                .clickButtonAddPainting()
                .fillPaintingTitle(title)
                .fillDescription(description)
                .uploadPaintingAvatar(avatar)
                .selectArtist(paintingJson.getArtist().getName())
                .clickButtonAdd();
        new ModalPaintingPage()
                .checkValidationOnDescription();
    }

    @Test
    @AllureId("53")
    @DBUser
    @ApiLogin
    @DisplayName("WEB: Поиск музея")
    void searchPainting(@Painting PaintingJson paintingJson) {
        mainPage
                .openPaintings()
                .fillSearchPainting(paintingJson.getTitle())
                .clickButtonSearchPainting()
                .checkNamesMuseum(paintingJson.getTitle());
    }
}
