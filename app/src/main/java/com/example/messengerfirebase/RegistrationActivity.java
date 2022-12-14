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
import com.google.firebase.auth.FirebaseUser;

public class RegistrationActivity extends AppCompatActivity {

    private EditText editTextTextEmailAddressRegistration;
    private EditText editTextTextPasswordRegistration;
    private EditText editTextName;
    private EditText editTextLastName;
    private EditText editTextAge;
    private Button buttonRegistration;

    private RegistrationViewModel registrationViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        initViews();

        registrationViewModel = new ViewModelProvider(this).get(RegistrationViewModel.class);

        observeViewModel();

        buttonRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = editTextTextEmailAddressRegistration.getText().toString().trim();
                String password = editTextTextPasswordRegistration.getText().toString().trim();
                String name = editTextName.getText().toString().trim();
                String lastName = editTextLastName.getText().toString().trim();
                int age = Integer.parseInt(editTextAge.getText().toString().trim());

                if (email.isEmpty() || password.isEmpty() || name.isEmpty() || lastName.isEmpty()) {
                    Toast.makeText(RegistrationActivity.this, "Заполнить нужно все поля", Toast.LENGTH_SHORT).show();
                } else {
                   registrationViewModel.signUp(email,password,name, lastName, age);
                }
            }
        });
    }

    private void observeViewModel (){
        registrationViewModel.getError().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String errorMessage) {
                if(errorMessage != null){
                    Toast.makeText(RegistrationActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                }
            }
        });

        registrationViewModel.getUser().observe(this, new Observer<FirebaseUser>() {
            @Override
            public void onChanged(FirebaseUser firebaseUser) {
              if (firebaseUser != null){
                  launchMessenger();
                  finish();
              }
            }
        });
    }

    private void launchMessenger() {
        Intent intent = MessengerActivity.newIntent(this);
        startActivity(intent);
    }

    public static Intent newIntent(Context context) {
        return new Intent(context, RegistrationActivity.class);
    }

    private void initViews() {
        editTextTextEmailAddressRegistration = findViewById(R.id.editTextTextEmailAddressRegistration);
        editTextTextPasswordRegistration = findViewById(R.id.editTextTextPasswordRegistration);
        editTextName = findViewById(R.id.editTextName);
        editTextLastName = findViewById(R.id.editTextLastName);
        editTextAge = findViewById(R.id.editTextAge);
        buttonRegistration = findViewById(R.id.buttonRegistration);
    }
}