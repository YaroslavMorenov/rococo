package org.rococo.page.museum;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.rococo.page.components.ToastElement;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$x;

public class MuseumProfilePage implements ToastElement<MuseumProfilePage> {

    private final SelenideElement museumTitle = $x("//header[contains (@class, 'card-header')]");
    private final SelenideElement country = $x("//div[@class = 'text-center']");
    private final SelenideElement description = $x("//div[@class = 'w-56 m-3 mx-auto']/following-sibling::div");
    private final SelenideElement avatar = $x("//main//img[@alt]");
    private final SelenideElement editButton = $x("//button[@data-testid = 'edit-museum']");

    @Step("Проверяет, что название музея заполнено значением {val}")
    public MuseumProfilePage checkMuseumTitle(String val) {
        museumTitle.shouldBe(text(val));
        return this;
    }

    @Step("Проверяет, что страна музея заполнено значением {0}")
    public MuseumProfilePage checkMuseumCountry(String val) {
        country.shouldBe(text(val));
        return this;
    }

    @Step("Проверяет, что описание музея заполнено значением {0}")
    public MuseumProfilePage checkMuseumDescription(String val) {
        description.shouldBe(text(val));
        return this;
    }

    @Step("Проверяет, что \"Аватар музея\" не пустой")
    public MuseumProfilePage checkMuseumAvatarNotEmpty() {
        avatar.shouldBe(attributeMatching("src", "data:image*.*"));
        return this;
    }

    @Step("Нажать кнопку \"Редактировать\"")
    public ModalMuseumPage clickButtonEdit() {
        editButton.click();
        return new ModalMuseumPage();
    }

    @Step("Проверяет, что кнопка \"Редактировать\" не отображается")
    public MuseumProfilePage checkButtonEditNotVisible() {
        editButton.shouldNotBe(visible);
        return this;
    }
}
