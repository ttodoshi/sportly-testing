package com.simple.ui.pages.auth;

import com.codeborne.selenide.SelenideElement;
import com.simple.ui.pages.BasePage;

import static com.codeborne.selenide.Selenide.$x;

public class AuthPage extends BasePage {
    private final SelenideElement emailField = $x("//input[@type='email']");
    private final SelenideElement passwordField = $x("//input[@type='password']");

    public void enterEmail(String email) {
        emailField.setValue(email);
    }

    public void enterPassword(String password) {
        passwordField.setValue(password);
    }
}
