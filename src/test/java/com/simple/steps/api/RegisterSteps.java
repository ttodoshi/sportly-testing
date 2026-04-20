package com.simple.steps.api;

import com.simple.api.models.login.request.LoginRequest;
import com.simple.api.models.login.request.RegisterRequest;
import com.simple.api.models.login.response.LoginResponse;
import com.simple.api.models.login.response.RegisterResponse;
import io.qameta.allure.Step;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;

public class RegisterSteps {
    @Step("Успешная регистрация")
    public RegisterResponse register(String subsystem, String password, String role) {
        RegisterRequest body = RegisterRequest.builder().password(password).role(role).build();

        return given()
                .contentType(JSON)
                .body(body)
                .log().all()
                .header("Subsystem", subsystem)
                .when()
                .post("/auth/register")
                .then()
                .log().all()
                .statusCode(201)
                .extract()
                .as(RegisterResponse.class);
    }

    @Step("Успешная регистрация")
    public RegisterResponse registerTrainer(String token, String subsystem, String password) {
        RegisterRequest body = RegisterRequest.builder().password(password).phone("phone").description("description").role("TRAINER").build();

        return given()
                .contentType(JSON)
                .body(body)
                .log().all()
                .header("Subsystem", subsystem)
                .auth()
                .oauth2(token)
                .when()
                .post("/auth/register")
                .then()
                .log().all()
                .statusCode(201)
                .extract()
                .as(RegisterResponse.class);
    }

    @Step("Удалить аккаунт")
    public void deleteAccount(String subsystem, LoginRequest loginRequest) {
        LoginResponse login = login(subsystem, loginRequest);
        given()
                .contentType(JSON)
                .body(loginRequest)
                .log().all()
                .header("Subsystem", subsystem)
                .auth()
                .oauth2(login.getAccessToken())
                .when()
                .delete("/auth/me")
                .then()
                .log().all()
                .statusCode(204);
    }

    @Step("Вход")
    public LoginResponse login(String subsystem, LoginRequest loginRequest) {
        return given()
                .contentType(JSON)
                .body(loginRequest)
                .log().all()
                .header("Subsystem", subsystem)
                .when()
                .post("/auth/login")
                .then()
                .log().all()
                .statusCode(200)
                .extract()
                .as(LoginResponse.class);
    }
}
