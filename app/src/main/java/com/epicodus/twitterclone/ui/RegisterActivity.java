package com.epicodus.twitterclone.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.epicodus.twitterclone.R;
import com.epicodus.twitterclone.presenter.ActivityUtil;
import com.epicodus.twitterclone.presenter.RegisterPresenter;
import com.epicodus.twitterclone.presenter.RegisterService;

public class RegisterActivity extends AppCompatActivity implements RegisterView{

    private EditText mNameEdit;
    private Button mLoginButton;
    private SharedPreferences mPreferences;
    private RegisterPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mNameEdit = (EditText) findViewById(R.id.textPersonName);
        mLoginButton = (Button) findViewById(R.id.registerButton);
        mPreferences = getApplicationContext().getSharedPreferences("twitter", Context.MODE_PRIVATE);

        presenter = new RegisterPresenter(this, new RegisterService());

        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onClicked();
                String name = mNameEdit.getText().toString();
                SharedPreferences.Editor editor = mPreferences.edit();
                editor.putString("username", name);
                editor.commit();
                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public String getUsername() {
        return mNameEdit.getText().toString();
    }

    @Override
    public void showUsernameError(int resId) {
        mNameEdit.setError(getString(resId));
    }

    @Override
    public void startMainActivity() {
        new ActivityUtil(this).startMainActivity();
    }

    @Override
    public void showRegisterError(int resId) {
        Toast.makeText(this, getString(resId), Toast.LENGTH_LONG).show();
    }
}
