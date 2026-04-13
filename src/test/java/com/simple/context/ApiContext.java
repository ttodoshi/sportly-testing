package com.simple.context;

import io.restassured.response.Response;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiContext {
    private Response lastResponse;
}