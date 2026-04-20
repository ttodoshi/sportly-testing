package com.simple.steps;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import io.cucumber.java.After;
import io.cucumber.java.Before;

import static com.codeborne.selenide.Selenide.closeWebDriver;

public class Hooks {
    @Before("@ui")
    public void setUpUi() {
        Configuration.browser = "chrome";
        Configuration.baseUrl = "http://localhost:3000";
        Configuration.timeout = 10000;
        Configuration.browserSize = "1920x1080";
    }

    @After("@ui")
    public void tearDownUi() {
        Selenide.screenshot(System.currentTimeMillis() + ".png");
        closeWebDriver();
    }
}