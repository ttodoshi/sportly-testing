package com.simple.context;

import lombok.Getter;
import lombok.Setter;
import org.openqa.selenium.WebDriver;

@Getter
@Setter
public class UiContext {
    private WebDriver driver;
}