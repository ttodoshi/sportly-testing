package com.simple.ui.pages.auth.register;

import com.codeborne.selenide.SelenideElement;
import com.simple.ui.pages.auth.AuthPage;

import static com.codeborne.selenide.Condition.enabled;
import static com.codeborne.selenide.Selenide.$x;
import static org.assertj.core.api.Assertions.assertThat;

// page_url = http://localhost:3000/register
public class RegisterPage extends AuthPage {
    private final SelenideElement lastNameField = $x("//input[@autocomplete='family-name']");
    private final SelenideElement firstNameField = $x("//input[@autocomplete='given-name']");
    private final SelenideElement patronymicField = $x("//input[@autocomplete='additional-name']");
    private final SelenideElement registerButton = $x("//button[@type='submit']");

    public void enterLastName(String lastName) {
        lastNameField.setValue(lastName);
    }

    public void enterFirstName(String firstName) {
        firstNameField.setValue(firstName);
    }

    public void enterPatronymic(String patronymic) {
        patronymicField.setValue(patronymic);
    }

    public void checkRegisterButtonIsEnabled() {
        registerButton.shouldBe(enabled);
        assertThat(registerButton.isEnabled()).isTrue();
    }

    public void clickRegisterButton() {
        registerButton.click();
    }
}