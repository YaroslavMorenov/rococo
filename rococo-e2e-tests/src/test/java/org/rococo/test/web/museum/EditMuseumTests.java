package org.rococo.test.web.museum;

import io.qameta.allure.AllureId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.rococo.jupiter.annotation.ApiLogin;
import org.rococo.jupiter.annotation.DBUser;
import org.rococo.jupiter.annotation.Museum;
import org.rococo.model.MuseumJson;
import org.rococo.page.museum.ModalMuseumPage;
import org.rococo.test.web.BaseWebTest;
import org.rococo.util.FileReaderUtils;
import org.rococo.util.FilesPath;

import java.io.File;

import static org.rococo.util.FakerUtils.*;

@DisplayName("[WEB]: Редактирование музея")
public class EditMuseumTests extends BaseWebTest {

    @Test
    @AllureId("40")
    @DBUser
    @ApiLogin
    @DisplayName("WEB: Пользователь может отредактировать все поля у музея")
    void museumShouldBeUpdated(@Museum MuseumJson museumJson) {
        String title = generateRandomTitle();
        String description = generateRandomSentence(30);
        File avatar = new File(FileReaderUtils.getPath(FilesPath.MUSEUM_AVATAR.getFileName()));
        String city = generateRandomCity();
        mainPage
                .openMuseums()
                .openMuseumWithName(museumJson.getTitle())
                .clickButtonEdit()
                .fillMuseumTitle(title)
                .fillDescription(description)
                .uploadMuseumAvatar(avatar)
                .fillCity(city)
                .selectCountry(defaultCountry)
                .clickButtonSave()
                .checkToastIsVisible()
                .checkToastHaveText("Обновлен музей: %s".formatted(title))
                .checkToastShouldBeDisappear()
                .checkMuseumCountry(defaultCountry)
                .checkMuseumAvatarNotEmpty()
                .checkMuseumDescription(description);
    }

    @Test
    @AllureId("41")
    @DBUser
    @ApiLogin
    @DisplayName("WEB: Пользователь может закрыть окно редактирования")
    void closeEditMuseumProfilePage(@Museum MuseumJson museumJson) {
        mainPage
                .openMuseums()
                .openMuseumWithName(museumJson.getTitle())
                .clickButtonEdit()
                .clickButtonClose()
                .checkMuseumAvatarNotEmpty()
                .checkMuseumDescription(museumJson.getDescription())
                .checkMuseumTitle(museumJson.getTitle());
    }

    @Test
    @AllureId("42")
    @DBUser
    @ApiLogin
    @DisplayName("WEB: Валидация поля описание")
    void museumDescriptionShouldBeMoreSymbols(@Museum MuseumJson museumJson) {
        String description = generateRandomString(9);
        mainPage
                .openMuseums()
                .openMuseumWithName(museumJson.getTitle())
                .clickButtonEdit()
                .fillDescription(description)
                .clickButtonSave();
        new ModalMuseumPage()
                .checkValidationOnDescription();
    }
}
