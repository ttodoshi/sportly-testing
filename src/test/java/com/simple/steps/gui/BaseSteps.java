package com.simple.steps.gui;

import com.codeborne.selenide.Selenide;
import io.cucumber.java.en.Given;

public class BaseSteps {

    @Given("Открыта страница {string}")
    public void openPage(String url) {
        Selenide.open(url);
    }
}
