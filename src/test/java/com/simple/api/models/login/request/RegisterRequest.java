package com.simple.api.models.login.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import static com.simple.helper.Generator.generateRandomString;

@Data
@Builder
public class RegisterRequest {
    @Builder.Default
    private String email = generateRandomString(10) + "@example.com";
    @Builder.Default
    @JsonProperty("first_name")
    private String firstName = generateRandomString(10);
    @Builder.Default
    @JsonProperty("last_name")
    private String lastName = generateRandomString(10);
    @Builder.Default
    private String patronymic = generateRandomString(10);
    private String password;
    private String role;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String phone;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String description;
}