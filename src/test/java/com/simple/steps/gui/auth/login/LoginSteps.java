package com.simple.steps.gui.auth.login;

import com.simple.steps.gui.auth.AuthSteps;
import com.simple.ui.pages.auth.login.LoginPage;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class LoginSteps extends AuthSteps<LoginPage> {
    public LoginSteps() {
        super(new LoginPage());
    }

    @Override
    @When("В поле Email при логине ввести значение {string}")
    public void enterEmail(String email) {
        authPage.enterEmail(email);
    }

    @Override
    @And("В поле Пароль при логине ввести значение {string}")
    public void enterPassword(String password) {
        authPage.enterPassword(password);
    }

    @Then("Проверить, что кнопка Войти активна")
    public void checkButtonLoginIsEnabled() {
        authPage.checkLoginButtonIsEnabled();
    }

    @And("Нажать кнопку Войти")
    public void clickLoginButton() {
        authPage.clickLoginButton();
    }
}
