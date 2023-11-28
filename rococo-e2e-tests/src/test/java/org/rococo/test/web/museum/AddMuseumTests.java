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

@DisplayName("[WEB]: Создание музея")
public class AddMuseumTests extends BaseWebTest {

    @Test
    @AllureId("30")
    @DBUser
    @ApiLogin
    @DisplayName("WEB: Создание музея")
    void museumShouldBeAdded() {
        String title = generateRandomTitle();
        String description = generateRandomSentence(30);
        File avatar = new File(FileReaderUtils.getPath(FilesPath.MUSEUM_AVATAR.getFileName()));
        String city = generateRandomCity();
        mainPage
                .openMuseums()
                .clickButtonAddMuseum()
                .fillMuseumTitle(title)
                .fillCity(city)
                .selectCountry(defaultCountry)
                .uploadMuseumAvatar(avatar)
                .fillDescription(description)
                .clickButtonAdd()
                .checkToastIsVisible()
                .checkToastHaveText("Добавлен музей: %s".formatted(title))
                .checkToastShouldBeDisappear();
    }

    @Test
    @AllureId("31")
    @DBUser
    @ApiLogin
    @DisplayName("WEB: Маппинг полей")
    void mappingMuseumFields(@Museum MuseumJson museum) {
        mainPage
                .openMuseums()
                .openMuseumWithName(museum.getTitle())
                .checkMuseumTitle(museum.getTitle())
                .checkMuseumDescription(museum.getDescription())
                .checkMuseumCountry(museum.getGeo().getCity())
                .checkMuseumCountry(museum.getGeo().getCountry().getName())
                .checkMuseumAvatarNotEmpty();
    }

    @Test
    @AllureId("32")
    @DBUser
    @ApiLogin
    @DisplayName("WEB: Музеев с одним названием быть не должно")
    void existingMuseumShouldNotBeCreated(@Museum MuseumJson museum) {
        String description = generateRandomSentence(30);
        File avatar = new File(FileReaderUtils.getPath(FilesPath.MUSEUM_AVATAR.getFileName()));
        String city = generateRandomCity();
        mainPage
                .openMuseums()
                .clickButtonAddMuseum()
                .fillMuseumTitle(museum.getTitle())
                .fillCity(city)
                .selectCountry(defaultCountry)
                .fillDescription(description)
                .uploadMuseumAvatar(avatar)
                .clickButtonAdd()
                .checkToastIsVisible()
                .checkToastHaveText("Cannot read properties of undefined")
                .checkToastShouldBeDisappear();
    }

    @Test
    @AllureId("33")
    @DBUser
    @ApiLogin
    @DisplayName("WEB: Валидация описания")
    void museumDescriptionShouldBeMoreSymbols() {
        String title = generateRandomTitle();
        String description = generateRandomString(9);
        File avatar = new File(FileReaderUtils.getPath(FilesPath.MUSEUM_AVATAR.getFileName()));
        String city = generateRandomCity();
        mainPage
                .openMuseums()
                .clickButtonAddMuseum()
                .fillMuseumTitle(title)
                .fillDescription(description)
                .fillCity(city)
                .selectCountry(defaultCountry)
                .uploadMuseumAvatar(avatar)
                .clickButtonAdd();
        new ModalMuseumPage()
                .checkValidationOnDescription();
    }

    @Test
    @AllureId("34")
    @DBUser
    @ApiLogin
    @DisplayName("WEB: Поиск музея")
    void searchMuseum(@Museum MuseumJson museumJson) {
        mainPage
                .openMuseums()
                .fillSearchMuseum(museumJson.getTitle())
                .clickButtonSearchMuseum()
                .checkNamesMuseum(museumJson.getTitle());
    }
}
