package com.example.messengerfirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private static final String LOG_TAG = "MainActivities";

    private FirebaseAuth auth;
    private EditText editTextTextEmailAddress;
    private EditText editTextNumberPassword;
    private Button buttonSignUp;
    private Button buttonSignIn;
    private Button buttonForgotPassword;

    private LoginViewModel loginViewModel;

    public FirebaseAuth getAuth() {
        return auth;
    }

    public void setAuth(FirebaseAuth auth) {
        this.auth = auth;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();

        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);

        observeViewModel();

        setUpClickListeners();

    }

    private void setUpClickListeners(){
        buttonSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = editTextTextEmailAddress.getText().toString().trim();
                String password = editTextNumberPassword.getText().toString().trim();

                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Пожалуйста, заполните все поля", Toast.LENGTH_SHORT).show();
                } else {
                    loginViewModel.login(email, password);
                }
            }
        });

        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchRegistration();
            }
        });

        buttonForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchForgetPassword(editTextTextEmailAddress.getText().toString().trim());
            }
        });
    }

    private void observeViewModel() {
        loginViewModel.getError().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String errorMessage) {
                if (errorMessage != null) {
                    Toast.makeText(LoginActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                }
            }
        });
        loginViewModel.getUser().observe(this, new Observer<FirebaseUser>() {
            @Override
            public void onChanged(FirebaseUser firebaseUser) {
                if (firebaseUser != null) {
                    launchMessenger(firebaseUser);
                    //закрыть экран, чтобы пользователь не мог на него попасть после успешной авторизации
                    finish();
                }
            }
        });
    }

    private void launchMessenger( FirebaseUser firebaseUser) {
        Intent intent = MessengerActivity.newIntent(this, firebaseUser.getUid());
        startActivity(intent);
    }

    private void launchRegistration() {
        Intent intent = RegistrationActivity.newIntent(this);
        startActivity(intent);
    }

    private void launchForgetPassword(String email) {
        Intent intent = ForgetPasswordActivity.newIntent(this, email);
        startActivity(intent);
    }

    static Intent newIntent(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        return intent;
    }

    private void initViews() {
        editTextTextEmailAddress = findViewById(R.id.editTextTextEmailAddress);
        editTextNumberPassword = findViewById(R.id.editTextNumberPassword);
        buttonSignUp = findViewById(R.id.buttonSignUp);
        buttonSignIn = findViewById(R.id.buttonSignIn);
        buttonForgotPassword = findViewById(R.id.buttonForgotPassword);
    }
}