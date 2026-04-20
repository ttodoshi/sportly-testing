package com.simple.tests.api.login;

import com.simple.api.models.ErrorResponse;
import com.simple.api.models.login.request.LoginRequest;
import com.simple.api.models.login.request.RegisterRequest;
import com.simple.api.models.login.response.LoginResponse;
import com.simple.api.models.login.response.RegisterResponse;
import com.simple.steps.api.RegisterSteps;
import com.simple.tests.api.BaseApiTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.assertj.core.api.Assertions.assertThat;

public class RegisterTest extends BaseApiTest {
    private final RegisterSteps steps = new RegisterSteps();

    static Stream<Arguments> registerData() {
        return Stream.of(
                Arguments.of("web", "GYM_ADMIN"),
                Arguments.of("mobile", "CLIENT")
        );
    }

    @ParameterizedTest
    @MethodSource("registerData")
    void registerSuccessTest(String subsystem, String role) {
        String password = "password";

        RegisterResponse response = steps.register(subsystem, password, role);

        steps.deleteAccount(subsystem, LoginRequest.builder().email(response.getEmail()).password(password).build());
    }

    static Stream<Arguments> registerTrainerData() {
        return Stream.of(
                Arguments.of("web", "gym@example.com", "password")
        );
    }

    @ParameterizedTest
    @MethodSource("registerTrainerData")
    void trainerRegisterSuccessTest(String subsystem, String email, String password) {
        String trainerPassword = "password";

        LoginResponse login = steps.login(subsystem, LoginRequest.builder().email(email).password(password).build());

        RegisterResponse registerResponse = steps.registerTrainer(login.getAccessToken(), subsystem, trainerPassword);

        steps.deleteAccount("mobile", LoginRequest.builder().email(registerResponse.getEmail()).password(trainerPassword).build());
    }

    static Stream<Arguments> invalidRegisterData() {
        return Stream.of(
                Arguments.of("web", RegisterRequest.builder().email("admin@example.com").password("Admin123!").role("SUPER_ADMIN").build(), 400, "Пользователь с таким email уже существует"),
                Arguments.of("web", RegisterRequest.builder().email("gym@example.com").password("password").role("GYM_ADMIN").build(), 400, "Пользователь с таким email уже существует"),
                Arguments.of("mobile", RegisterRequest.builder().email("trainer.ivan@example.com").password("password").role("TRAINER").build(), 400, "Пользователь с таким email уже существует"),
                Arguments.of("mobile", RegisterRequest.builder().email("client.olga@example.com").password("password").role("CLIENT").build(), 400, "Пользователь с таким email уже существует"),
                Arguments.of("web", RegisterRequest.builder().password("Admin123!").role("SUPER_ADMIN").build(), 403, "Пользователь не может самостоятельно зарегистрироваться как SUPER_ADMIN"),
                Arguments.of("mobile", RegisterRequest.builder().password("password").role("GYM_ADMIN").build(), 400, "Регистрация администратора зала из подсистемы mobile запрещена"),
                Arguments.of("web", RegisterRequest.builder().password("password").role("TRAINER").build(), 403, "Пользователь может быть зарегистрирован как TRAINER только администратором зала"),
                Arguments.of("web", RegisterRequest.builder().password("password").role("CLIENT").build(), 400, "Регистрация клиента из подсистемы web запрещена")
        );
    }

    @ParameterizedTest
    @MethodSource("invalidRegisterData")
    void registerInvalidDataTest(String subsystem, RegisterRequest body, int code, String expectedMessage) {
        String message = given()
                .contentType(JSON)
                .body(body)
                .log().all()
                .header("Subsystem", subsystem)
                .when()
                .post("/auth/register")
                .then()
                .log().all()
                .statusCode(code)
                .extract()
                .as(ErrorResponse.class)
                .getDetail();

        assertThat(message).isEqualTo(expectedMessage);
    }
}