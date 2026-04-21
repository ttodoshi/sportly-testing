package com.simple.steps.gui.auth;

import com.simple.ui.pages.auth.AuthPage;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class AuthSteps<T extends AuthPage> {
    protected final T authPage;

    public abstract void enterEmail(String email);

    public abstract void enterPassword(String password);
}
