package com.epicodus.twitterclone.presenter;


import com.epicodus.twitterclone.R;
import com.epicodus.twitterclone.ui.RegisterView;

public class RegisterPresenter {

    private final RegisterView view;
    private final RegisterService service;

    public RegisterPresenter(RegisterView view, RegisterService service) {
        this.view = view;
        this.service = service;

    }

    public void onClicked() {
        String username = view.getUsername();
        if (username.isEmpty()) {
            view.showUsernameError(R.string.username_error);
            return;
        }

        boolean loginSucceeded = service.register(username);
        if (loginSucceeded) {
            view.startMainActivity();
            return;
        }
        view.showRegisterError(R.string.register_failed);
    }
}
