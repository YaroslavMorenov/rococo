package org.rococo.page.components;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.rococo.page.ProfilePage;

import static com.codeborne.selenide.Selenide.$x;

public class HeaderComponent {

    private final SelenideElement avatar = $x("//figure[@data-testid = 'avatar']");

    @Step("Нажать на \"Аватар\"")
    public ProfilePage clickAvatar() {
        avatar.click();
        return new ProfilePage();
    }
}
