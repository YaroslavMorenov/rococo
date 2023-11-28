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

@DisplayName("[WEB]: Редактирование картины")
public class EditPaintingTest extends BaseWebTest {

    @Test
    @AllureId("60")
    @DBUser
    @ApiLogin
    @DisplayName("WEB: Пользователь может отредактировать все поля у картины")
    void paintingShouldBeUpdated(@Painting PaintingJson paintingJson) {
        String title = generateRandomTitle();
        String description = generateRandomSentence(30);
        File avatar = new File(FileReaderUtils.getPath(FilesPath.PAINTING_AVATAR.getFileName()));
        mainPage
                .openPaintings()
                .openPaintingWithName(paintingJson.getTitle())
                .clickButtonEdit()
                .fillPaintingTitle(title)
                .uploadPaintingAvatar(avatar)
                .fillDescription(description)
                .clickButtonSave()
                .checkToastIsVisible()
                .checkToastHaveText("Обновлена картина: %s".formatted(title))
                .checkToastShouldBeDisappear()
                .checkPaintingTitle(title)
                .checkPaintingDescription(description)
                .checkPaintingArtist(paintingJson.getArtist().getName())
                .checkPaintingAvatarNotEmpty();
    }

    @Test
    @AllureId("61")
    @DBUser
    @ApiLogin
    @DisplayName("WEB: Пользователь может закрыть окно редактирования")
    void closeEditPaintingProfilePage(@Painting PaintingJson paintingJson) {
        mainPage
                .openPaintings()
                .openPaintingWithName(paintingJson.getTitle())
                .clickButtonEdit()
                .clickButtonClose()
                .checkPaintingAvatarNotEmpty()
                .checkPaintingTitle(paintingJson.getTitle())
                .checkPaintingDescription(paintingJson.getDescription())
                .checkPaintingArtist(paintingJson.getArtist().getName());
    }

    @Test
    @AllureId("62")
    @DBUser
    @ApiLogin
    @DisplayName("WEB: Валидация поля описание")
    void paintingDescriptionShouldBeMoreSymbols(@Painting PaintingJson paintingJson) {
        String description = generateRandomString(9);
        mainPage
                .openPaintings()
                .openPaintingWithName(paintingJson.getTitle())
                .clickButtonEdit()
                .fillDescription(description)
                .clickButtonSave();
        new ModalPaintingPage()
                .checkValidationOnDescription();
    }
}
