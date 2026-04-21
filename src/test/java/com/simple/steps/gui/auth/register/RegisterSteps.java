package com.simple.steps.gui.auth.register;

import com.simple.context.ScenarioContext;
import com.simple.steps.gui.auth.AuthSteps;
import com.simple.ui.pages.auth.register.RegisterPage;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class RegisterSteps extends AuthSteps<RegisterPage> {
    private final ScenarioContext context;

    public RegisterSteps(ScenarioContext context) {
        super(new RegisterPage());
        this.context = context;
    }

    @When("В поле Фамилия ввести значение {string}")
    public void enterLastName(String lastName) {
        authPage.enterLastName(lastName);
    }

    @And("В поле Имя ввести значение {string}")
    public void enterFirstName(String firstName) {
        authPage.enterFirstName(firstName);
    }

    @And("В поле Отчество ввести значение {string}")
    public void enterPatronymic(String patronymic) {
        authPage.enterPatronymic(patronymic);
    }

    @Override
    @And("В поле Email при регистрации ввести значение {string}")
    public void enterEmail(String email) {
        authPage.enterEmail(email);
        context.set("email", email);
    }

    @Override
    @And("В поле Пароль при регистрации ввести значение {string}")
    public void enterPassword(String password) {
        authPage.enterPassword(password);
        context.set("password", password);
    }

    @Then("Проверить, что кнопка Зарегистрироваться активна")
    public void checkButtonRegisterIsEnabled() {
        authPage.checkRegisterButtonIsEnabled();
    }

    @And("Нажать кнопку Зарегистрироваться")
    public void clickRegisterButton() {
        authPage.clickRegisterButton();
    }
}
