package com.simple.ui.pages.login;

import com.codeborne.selenide.SelenideElement;
import com.simple.ui.pages.BasePage;

import static com.codeborne.selenide.Condition.enabled;
import static com.codeborne.selenide.Selenide.$x;
import static org.assertj.core.api.Assertions.assertThat;

// page_url = http://localhost:3000/login
public class LoginPage extends BasePage {
    private final SelenideElement emailField = $x("//input[@type='email']");
    private final SelenideElement passwordField = $x("//input[@type='password']");
    private final SelenideElement loginButton = $x("//button[@type='submit']");

    public void enterEmail(String email) {
        emailField.setValue(email);
    }

    public void enterPassword(String password) {
        passwordField.setValue(password);
    }

    public void checkLoginButtonIsEnabled() {
        loginButton.shouldBe(enabled);
        assertThat(loginButton.isEnabled()).isTrue();
    }

    public void clickLoginButton() {
        loginButton.click();
    }
}