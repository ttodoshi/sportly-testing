package com.simple.steps.gui;

import com.simple.ui.components.Alerts;
import io.cucumber.java.en.Then;

public class AlertSteps {
    private final Alerts alerts = new Alerts();

    @Then("Проверить, что появился алерт с текстом {string}")
    public void checkAlert(String text) {
        alerts.alertIsDisplayed(text);
    }
}
