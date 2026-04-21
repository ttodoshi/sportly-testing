package com.simple.steps;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.simple.api.models.login.request.LoginRequest;
import com.simple.context.ScenarioContext;
import com.simple.steps.api.RegisterSteps;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.restassured.RestAssured;
import lombok.RequiredArgsConstructor;

import static com.codeborne.selenide.Selenide.closeWebDriver;

@RequiredArgsConstructor
public class Hooks {
    private final ScenarioContext context;

    @Before("@ui")
    public void setUpUi() {
        Configuration.browser = "chrome";
        Configuration.baseUrl = "http://localhost:3000";
        Configuration.timeout = 10000;
        Configuration.browserSize = "1920x1080";
        RestAssured.baseURI = "http://localhost:8000/api/v1";
    }

    @After("@ui")
    public void tearDownUi() {
        Selenide.screenshot(System.currentTimeMillis() + ".png");
        closeWebDriver();
    }

    @After("@register")
    public void cleanUp() {
        RegisterSteps steps = new RegisterSteps();
        steps.deleteAccount("web", LoginRequest.builder()
                .email(context.get("email"))
                .password(context.get("password"))
                .build()
        );
    }
}