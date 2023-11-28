package org.rococo.page.museum;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.rococo.page.components.ToastElement;

import static com.codeborne.selenide.CollectionCondition.texts;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$$x;
import static com.codeborne.selenide.Selenide.$x;

public class MuseumPage implements ToastElement<MuseumPage> {

    private final SelenideElement addMuseum = $x("//button[text() = 'Добавить музей']");
    private final ElementsCollection museumList = $$x("//a[contains (@href, 'museum/')]");
    private final SelenideElement searchMuseumField = $x("//input[@title= 'Искать музей...']");
    private final SelenideElement searchButton = $x("//img[@alt = 'Иконка поиска']");

    @Step("Нажать кнопку \"Добавить музей\"")
    public ModalMuseumPage clickButtonAddMuseum() {
        addMuseum.click();
        return new ModalMuseumPage();
    }

    @Step("Открыть музей с именем {0}")
    public MuseumProfilePage openMuseumWithName(String museumName) {
        museumList.find(text(museumName)).click();
        return new MuseumProfilePage();
    }

    @Step("Проверяет, что отображаются музеи с именами {0}")
    public MuseumPage checkNamesMuseum(String... names) {
        museumList.shouldBe(texts(names));
        return this;
    }

    @Step("Заполнить поле \"Поиск музея\"")
    public MuseumPage fillSearchMuseum(String name) {
        searchMuseumField.val(name);
        return this;
    }

    @Step("Нажать кнопку \"Поиск музея\"")
    public MuseumPage clickButtonSearchMuseum() {
        searchButton.click();
        return this;
    }
}
