package com.simple.steps.api;

import com.simple.api.models.ErrorResponse;
import com.simple.context.ApiContext;
import io.cucumber.java.en.Then;
import lombok.RequiredArgsConstructor;

import static org.assertj.core.api.Assertions.assertThat;

@RequiredArgsConstructor
public class ResponseSteps {
    private final ApiContext context;

    @Then("Проверить, что код ответа {int}")
    public void checkStatusCode(int statusCode) {
        context.getLastResponse()
                .then()
                .statusCode(statusCode);
    }

    @Then("Проверить, что вернулось сообщение об ошибке {string}")
    public void checkErrorMessage(String errorMessage) {
        String message = context.getLastResponse()
                .then()
                .extract()
                .as(ErrorResponse.class)
                .getDetail();
        assertThat(message).isEqualTo(errorMessage);
    }
}
