package org.rococo.jupiter.extension;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.openqa.selenium.Cookie;
import org.rococo.api.AuthServiceClient;
import org.rococo.api.context.CookieContext;
import org.rococo.api.context.SessionStorageContext;
import org.rococo.config.Config;
import org.rococo.jupiter.annotation.ApiLogin;
import org.rococo.model.UserJson;

import java.io.IOException;

import static org.rococo.jupiter.extension.DbCreateUserExtension.NAMESPACE;
import static org.rococo.jupiter.extension.DbCreateUserExtension.getAllureId;

public class ApiLoginExtension implements BeforeEachCallback, AfterTestExecutionCallback {

    private final AuthServiceClient authServiceClient = new AuthServiceClient();

    @Override
    public void beforeEach(ExtensionContext extensionContext) throws Exception {
        ApiLogin annotation = extensionContext.getRequiredTestMethod().getAnnotation(ApiLogin.class);
        if (annotation != null) {
            if (annotation.username().isEmpty()) {
                UserJson createdUser = extensionContext.getStore(NAMESPACE).get(getAllureId(extensionContext), UserJson.class);
                doLogin(createdUser.getUsername(), createdUser.getPassword());
            } else {
                doLogin(annotation.username(), annotation.password());
            }
        }
    }

    private void doLogin(String username, String password) {
        SessionStorageContext sessionStorageContext = SessionStorageContext.getInstance();
        sessionStorageContext.init();

        try {
            authServiceClient.doLogin(username, password);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Selenide.open(Config.getInstance().rococoFrontUrl());
        Selenide.localStorage().setItem("codeChallenge", sessionStorageContext.getCodeChallenge());
        Selenide.localStorage().setItem("id_token", sessionStorageContext.getToken());
        Selenide.localStorage().setItem("codeVerifier", sessionStorageContext.getCodeVerifier());
        Cookie jsessionIdCookie = new Cookie("JSESSIONID", CookieContext.getInstance().getJSessionIdCookieValue());
        WebDriverRunner.getWebDriver().manage().addCookie(jsessionIdCookie);
    }

    @Override
    public void afterTestExecution(ExtensionContext extensionContext) throws Exception {
        SessionStorageContext.getInstance().clearContext();
        CookieContext.getInstance().clearContext();
    }
}
