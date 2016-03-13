package com.mirego.rebelchat.controllers;

import android.content.Context;

public interface LoginController {

    interface LoginCallback {
        void onLoginSuccess(String userId);
        void onLoginFail();
    }

    interface RegisterCallback {
        void onRegisterSuccess(String userId);
        void onRegisterFail();
    }

    void login(Context context, String username, String password, LoginCallback loginCallback);

    void register(Context context, String username, String password, String email, RegisterCallback registerCallback);
}
