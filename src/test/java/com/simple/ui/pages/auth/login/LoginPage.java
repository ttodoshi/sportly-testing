package com.simple.ui.pages.auth.login;

import com.codeborne.selenide.SelenideElement;
import com.simple.ui.pages.auth.AuthPage;

import static com.codeborne.selenide.Condition.enabled;
import static com.codeborne.selenide.Selenide.$x;
import static org.assertj.core.api.Assertions.assertThat;

// page_url = http://localhost:3000/login
public class LoginPage extends AuthPage {
    private final SelenideElement loginButton = $x("//button[@type='submit']");

    public void checkLoginButtonIsEnabled() {
        loginButton.shouldBe(enabled);
        assertThat(loginButton.isEnabled()).isTrue();
    }

    public void clickLoginButton() {
        loginButton.click();
    }
}