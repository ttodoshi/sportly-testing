package com.simple.ui.components;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.CollectionCondition.sizeGreaterThanOrEqual;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$$x;
import static org.assertj.core.api.Assertions.assertThat;

public class Alerts {
    private final ElementsCollection alerts = $$x("//div[contains(@role, 'alert')]");

    public void alertIsDisplayed(String text) {
        SelenideElement alert = alerts
                .shouldHave(sizeGreaterThanOrEqual(1))
                .findBy(text(text));
        alert.shouldBe(visible);
        assertThat(alert.isDisplayed());
    }
}
