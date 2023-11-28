package org.rococo.test.web;

import io.qameta.allure.AllureId;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.rococo.db.model.user.Authority;
import org.rococo.db.model.user.AuthorityEntity;
import org.rococo.db.model.user.UserEntity;
import org.rococo.db.repository.user.UserRepository;
import org.rococo.db.repository.user.UserRepositoryHibernate;
import org.rococo.jupiter.annotation.DBUser;
import org.rococo.model.UserJson;
import org.rococo.page.MainPage;

import java.util.ArrayList;
import java.util.Arrays;

import static com.codeborne.selenide.Selenide.open;
import static org.rococo.util.FakerUtils.generateRandomPassword;
import static org.rococo.util.FakerUtils.generateRandomUsername;

@DisplayName("[WEB]: Авторизация")
public class LoginPageTests extends BaseWebTest {
    private final UserRepository userRepository = new UserRepositoryHibernate();
    private final UserEntity authUser = new UserEntity();

    @AfterEach
    void deleteUser() {
        if (authUser.getUsername() != null) {
            authUser.setPassword(defaultPassword);
            authUser.setEnabled(true);
            authUser.setAccountNonExpired(true);
            authUser.setAccountNonLocked(true);
            authUser.setCredentialsNonExpired(true);
            authUser.setAuthorities(new ArrayList<>(Arrays.stream(Authority.values())
                    .map(a -> {
                        AuthorityEntity ae = new AuthorityEntity();
                        ae.setAuthority(a);
                        ae.setUser(authUser);
                        return ae;
                    }).toList()));
            userRepository.removeAfterTest(authUser);
        }
    }

    @Test
    @AllureId("1")
    @DisplayName("[WEB]: Авторизация с отсуствующим пользователем")
    void loginWithNotExistingUser () {
        open(url);
        new MainPage()
                .clickLoginBtn()
                .fillLogin(generateRandomUsername())
                .fillPassword(generateRandomPassword())
                .clickSubmitBtn();
        loginPage.checkTextFromError();
    }

    @Test
    @AllureId("2")
    @DisplayName("[WEB]: Регистрация с не идентичными паролями")
    void registrationUserWithNotEqualPassword () {
        open(url);
        new MainPage()
                .clickLoginBtn()
                .clickRegisterBtn()
                .fillUserName(generateRandomUsername())
                .fillPassword(generateRandomPassword())
                .fillConfirmPassword(generateRandomPassword())
                .clickSignUp();
        registerPage.checkTextFromError("Passwords should be equal");
    }

    @Test
    @AllureId("3")
    @DisplayName("[WEB]: Тоаст после регистрации")
    void afterRegistrationShouldBeVisibleAlert () {
        open(url);
        String userName = generateRandomUsername();
        new MainPage()
                .clickLoginBtn()
                .clickRegisterBtn()
                .fillUserName(userName)
                .fillPassword(defaultPassword)
                .fillConfirmPassword(defaultPassword)
                .clickSignUp()
                .checkAlertAfterRegistration();
        authUser.setUsername(userName);
    }

    @Test
    @AllureId("4")
    @DBUser
    @DisplayName("[WEB]: Проверка успешной авторизации")
    void afterLoginShouldBeVisibleAvatar(UserJson user) {
        open(url);
        new MainPage()
                .clickLoginBtn()
                .fillLogin(user.getUsername())
                .fillPassword(user.getPassword())
                .clickSubmitBtn()
                .checkAvatarIsPresent();
    }

    @Test
    @AllureId("5")
    @DBUser
    @DisplayName("[WEB]: Регистрация существующего пользователя")
    void registrationWithExistingUser(UserJson user) {
        open(url);
        new MainPage()
                .clickLoginBtn()
                .clickRegisterBtn()
                .fillUserName(user.getUsername())
                .fillPassword(user.getPassword())
                .fillConfirmPassword(user.getPassword())
                .clickSignUp()
                .checkTextFromError("Username `%s` already exists".formatted(user.getUsername()));
    }
}
