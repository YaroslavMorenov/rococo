package org.rococo.test.web;

import org.rococo.config.Config;
import org.rococo.jupiter.annotation.WebTest;
import org.rococo.page.MainPage;
import org.rococo.page.RegisterPage;
import org.rococo.page.components.HeaderComponent;
import org.rococo.page.login.LoginPage;

@WebTest
public abstract class BaseWebTest {

    protected static final String url = Config.getInstance().rococoFrontUrl();
    protected LoginPage loginPage = new LoginPage();
    protected RegisterPage registerPage = new RegisterPage();
    protected MainPage mainPage = new MainPage();
    protected HeaderComponent headerComponent = new HeaderComponent();
    protected static final String defaultPassword = "12345";
    protected static final String defaultCountry = "Азербайджан";
}
