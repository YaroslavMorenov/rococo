package org.rococo.page.painting;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.rococo.page.components.ToastElement;

import static com.codeborne.selenide.CollectionCondition.texts;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$$x;
import static com.codeborne.selenide.Selenide.$x;

public class PaintingPage implements ToastElement<PaintingPage> {

    private final SelenideElement addPaintingButton = $x("//button[text() = 'Добавить картину']");
    private final ElementsCollection paintingList = $$x("//a[contains (@href, 'painting/')]");
    private final SelenideElement searchPaintingField = $x("//input[@title= 'Искать картины...']");
    private final SelenideElement searchButton = $x("//img[@alt = 'Иконка поиска']");

    @Step("Нажать кнопку \"Добавить картину\"")
    public ModalPaintingPage clickButtonAddPainting() {
        addPaintingButton.click();
        return new ModalPaintingPage();
    }

    @Step("Открыть картину с именем {0}")
    public PaintingProfilePage openPaintingWithName(String paintingName) {
        paintingList.find(text(paintingName)).click();
        return new PaintingProfilePage();
    }

    @Step("Заполнить поле \"Поиск картины\"")
    public PaintingPage fillSearchPainting(String name) {
        searchPaintingField.val(name);
        return this;
    }

    @Step("Нажать кнопку \"Поиск картины\"")
    public PaintingPage clickButtonSearchPainting() {
        searchButton.click();
        return this;
    }

    @Step("Проверяет, что отображаются картины с именами {0}")
    public PaintingPage checkNamesMuseum(String... names) {
        paintingList.shouldBe(texts(names));
        return this;
    }
}
