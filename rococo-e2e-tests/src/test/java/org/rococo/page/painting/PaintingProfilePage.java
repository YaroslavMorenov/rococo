package org.rococo.page.painting;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.rococo.page.components.ToastElement;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$x;

public class PaintingProfilePage implements ToastElement<PaintingProfilePage> {

    private final SelenideElement paintingTitle = $x("//header[contains (@class, 'card-header')]");
    private final SelenideElement artistName = $x("//div[@class = 'text-center']");
    private final SelenideElement description = $x("//div[@class = 'm-4']");
    private final SelenideElement avatar = $x("//main//img[@alt]");
    private final SelenideElement editButton = $x("//button[@data-testid = 'edit-painting']");

    @Step("Проверяет, что название картины заполнено значением {val}")
    public PaintingProfilePage checkPaintingTitle(String val) {
        paintingTitle.shouldBe(text(val));
        return this;
    }

    @Step("Проверяет, что художник картины заполнено значением {0}")
    public PaintingProfilePage checkPaintingArtist(String val) {
        artistName.shouldBe(text(val));
        return this;
    }

    @Step("Проверяет, что описание музея заполнено значением {0}")
    public PaintingProfilePage checkPaintingDescription(String val) {
        description.shouldBe(text(val));
        return this;
    }

    @Step("Проверяет, что \"Аватар картины\" не пустой")
    public PaintingProfilePage checkPaintingAvatarNotEmpty() {
        avatar.shouldBe(attributeMatching("src", "data:image*.*"));
        return this;
    }

    @Step("Нажать кнопку \"Редактировать\"")
    public ModalPaintingPage clickButtonEdit() {
        editButton.click();
        return new ModalPaintingPage();
    }

    @Step("Проверяет, что кнопка \"Редактировать\" не отображается")
    public PaintingProfilePage checkButtonEditNotVisible() {
        editButton.shouldNotBe(visible);
        return this;
    }
}
