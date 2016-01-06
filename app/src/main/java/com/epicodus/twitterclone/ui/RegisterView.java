package com.epicodus.twitterclone.ui;


public interface RegisterView {
    String getUsername();

    void showUsernameError(int resId);

    void startMainActivity();

    void showRegisterError(int resId);

}
