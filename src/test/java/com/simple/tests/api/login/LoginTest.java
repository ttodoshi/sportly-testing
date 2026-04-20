package com.simple.tests.api.login;

import com.simple.api.models.ErrorResponse;
import com.simple.api.models.login.request.LoginRequest;
import com.simple.tests.api.BaseApiTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.notNullValue;

public class LoginTest extends BaseApiTest {

    static Stream<Arguments> loginData() {
        return Stream.of(
                Arguments.of("web", "admin@example.com", "Admin123!"),
                Arguments.of("web", "gym@example.com", "password"),
                Arguments.of("mobile", "trainer.ivan@example.com", "password"),
                Arguments.of("mobile", "client.olga@example.com", "password")
        );
    }

    @ParameterizedTest
    @MethodSource("loginData")
    void loginSuccessTest(String subsystem, String email, String password) {
        LoginRequest body = LoginRequest.builder()
                .email(email)
                .password(password)
                .build();

        given()
                .contentType(JSON)
                .body(body)
                .log().all()
                .header("Subsystem", subsystem)
                .when()
                .post("/auth/login")
                .then()
                .log().all()
                .statusCode(200)
                .body("access_token", notNullValue());
    }

    static Stream<Arguments> invalidLoginData() {
        return Stream.of(
                Arguments.of("web", "admin@example.com", "123"),
                Arguments.of("web", "gym@example.com", "123"),
                Arguments.of("mobile", "trainer.ivan@example.com", "123"),
                Arguments.of("mobile", "client.olga@example.com", "123")
        );
    }

    @ParameterizedTest
    @MethodSource("invalidLoginData")
    void loginInvalidDataTest(String subsystem, String email, String password) {
        String expectedMessage = "Неверный email или пароль";

        LoginRequest body = LoginRequest.builder()
                .email(email)
                .password(password)
                .build();

        String message = given()
                .contentType(JSON)
                .body(body)
                .log().all()
                .header("Subsystem", subsystem)
                .when()
                .post("/auth/login")
                .then()
                .log().all()
                .statusCode(401)
                .extract()
                .as(ErrorResponse.class)
                .getDetail();

        assertThat(message).isEqualTo(expectedMessage);
    }

    static Stream<Arguments> invalidHeaderLoginData() {
        return Stream.of(
                Arguments.of("mobile", "admin@example.com", "Admin123!", "Вход через mobile разрешен только для CLIENT и TRAINER"),
                Arguments.of("mobile", "gym@example.com", "password", "Вход через mobile разрешен только для CLIENT и TRAINER"),
                Arguments.of("web", "trainer.ivan@example.com", "password", "Вход через web разрешен только для SUPER_ADMIN и GYM_ADMIN"),
                Arguments.of("web", "client.olga@example.com", "password", "Вход через web разрешен только для SUPER_ADMIN и GYM_ADMIN")
        );
    }

    @ParameterizedTest
    @MethodSource("invalidHeaderLoginData")
    void loginHeaderTest(String subsystem, String email, String password, String expectedMessage) {
        LoginRequest body = LoginRequest.builder()
                .email(email)
                .password(password)
                .build();

        String message = given()
                .contentType(JSON)
                .body(body)
                .log().all()
                .header("Subsystem", subsystem)
                .when()
                .post("/auth/login")
                .then()
                .log().all()
                .statusCode(400)
                .extract()
                .as(ErrorResponse.class)
                .getDetail();

        assertThat(message).isEqualTo(expectedMessage);
    }
}