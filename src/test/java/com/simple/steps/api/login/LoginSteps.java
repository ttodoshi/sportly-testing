package com.simple.steps.api.login;

import com.simple.api.models.login.LoginRequest;
import com.simple.context.ApiContext;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.RequiredArgsConstructor;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.notNullValue;

@RequiredArgsConstructor
public class LoginSteps {
    private final ApiContext apiContext;

    @When("Отправить запрос на {string} c хедером Subsystem: {string} с телом {string} и {string}")
    public void sendLoginRequest(String endpoint, String subsystem, String email, String password) {
        LoginRequest body = LoginRequest.builder()
                .email(email)
                .password(password)
                .build();

        apiContext.setLastResponse(
                given()
                        .contentType(JSON)
                        .body(body)
                        .header("Subsystem", subsystem)
                        .when()
                        .post(endpoint)
        );
    }

    @Then("Проверить, что вернулось поле access_token")
    public void checkAccessTokenPresent() {
        apiContext.getLastResponse()
                .then()
                .body("access_token", notNullValue());
    }
}
