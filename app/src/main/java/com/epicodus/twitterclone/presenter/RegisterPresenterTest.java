package com.epicodus.twitterclone.presenter;

import com.epicodus.twitterclone.R;
import com.epicodus.twitterclone.ui.RegisterView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RegisterPresenterTest {

    private RegisterView view;
    private RegisterService service;
    private RegisterPresenter presenter;

    @Before
    public void setUp() throws Exception{
        presenter = new RegisterPresenter(view, service);

    }

    @Test
    public void shouldShowErrorMessageWhenUserNameIsEmpty() throws Exception{
        when(view.getUsername()).thenReturn("");
        presenter.onClicked();

        verify(view).showUsernameError(R.string.username_error);
    }

    @Test
    public void shouldStartMainActivityWhenUsernameIsCorrect() throws Exception {
        when(view.getUsername()).thenReturn("john");
        presenter.onClicked();

        verify(view).startMainActivity();
    }

    @Test
    public void shouldShowLoginErrorWhenUsernameIsInvalid() throws Exception {
        when(view.getUsername()).thenReturn("john");
        presenter.onClicked();

        verify(view).showLoginError(R.string.register_failed);
    }

}