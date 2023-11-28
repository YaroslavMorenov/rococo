package org.rococo.test.web;

import io.qameta.allure.AllureId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.rococo.jupiter.annotation.ApiLogin;
import org.rococo.jupiter.annotation.DBUser;
import org.rococo.util.FileReaderUtils;
import org.rococo.util.FilesPath;

import java.io.File;

import static org.rococo.util.FakerUtils.generateRandomName;
import static org.rococo.util.FakerUtils.generateRandomSurname;

@DisplayName("[WEB]: Редактирование профиля")
public class EditProfileTests extends BaseWebTest {

    @Test
    @AllureId("6")
    @DBUser
    @ApiLogin
    @DisplayName("[WEB] : Редактирование профиля")
    void userShouldBeUpdated() {
        String firstName = generateRandomName();
        String lastName = generateRandomSurname();
        File avatar = new File(FileReaderUtils.getPath(FilesPath.USER_AVATAR.getFileName()));
        headerComponent
                .clickAvatar()
                .fillFirstName(firstName)
                .fillLastName(lastName)
                .uploadAvatar(avatar)
                .clickButtonUpdateProfile()
                .checkToastIsVisible()
                .checkToastHaveText("Профиль обновлен")
                .checkToastShouldBeDisappear();
        headerComponent
                .clickAvatar()
                .checkValueFirstName(firstName)
                .checkValueLastNameName(lastName)
                .checkAvatarNotEmpty();
    }

    @Test
    @AllureId("7")
    @DBUser
    @ApiLogin
    @DisplayName("[WEB]: Выход из профиля")
    void userLogout() {
        headerComponent
                .clickAvatar()
                .clickButtonLogout()
                .checkToastHaveText("Сессия завершена")
                .checkToastShouldBeDisappear()
                .loginButtonIsVisible();
    }

    @Test
    @AllureId("8")
    @DBUser
    @ApiLogin
    @DisplayName("[WEB]: Закрытие формы профиля")
    void closeEditProfilePage() {
        headerComponent
                .clickAvatar()
                .clickButtonExit();
        headerComponent
                .clickAvatar()
                .checkValueFirstName("")
                .checkValueLastNameName("");
    }
}
