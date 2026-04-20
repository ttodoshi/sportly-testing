package com.simple.tests.api;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;

public class BaseApiTest {
    @BeforeAll
    static void setup() {
        RestAssured.baseURI = "http://localhost:8000/api/v1";
    }
}
