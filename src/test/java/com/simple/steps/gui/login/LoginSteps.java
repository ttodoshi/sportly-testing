package com.simple.steps.gui.login;

import com.simple.ui.pages.login.LoginPage;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class LoginSteps {
    private final LoginPage loginPage = new LoginPage();

    @Given("Открыта страница {string}")
    public void openPage(String url) {
        loginPage.open(url);
    }

    @When("В поле Email ввести значение {string}")
    public void enterEmail(String email) {
        loginPage.enterEmail(email);
    }

    @And("В поле Пароль ввести значение {string}")
    public void enterPassword(String password) {
        loginPage.enterPassword(password);
    }

    @Then("Проверить, что кнопка Войти активна")
    public void checkButtonLoginIsEnabled() {
        loginPage.checkLoginButtonIsEnabled();
    }

    @And("Нажать кнопку Войти")
    public void clickLoginButton() {
        loginPage.clickLoginButton();
    }
}
