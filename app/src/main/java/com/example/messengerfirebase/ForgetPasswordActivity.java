package com.example.messengerfirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

public class ForgetPasswordActivity extends AppCompatActivity {

    private static final String EXTRA_Email = "email";

    private MainActivity mainActivity;
    private EditText editTextTextEmailAddressResetPassword;
    private Button buttonResetPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        initViews();
        String email = getIntent().getStringExtra(EXTRA_Email);
        editTextTextEmailAddressResetPassword.setText(email);
        buttonResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = editTextTextEmailAddressResetPassword.getText().toString().trim();

                mainActivity.getAuth().sendPasswordResetEmail(email).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        launchMessenger();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ForgetPasswordActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private void launchMessenger(){
        Intent intent = MessengerActivity.newIntent(this);
        startActivity(intent);
    }

    static Intent newIntent(Context context, String email) {
        Intent intent = new Intent(context, ForgetPasswordActivity.class);
        intent.putExtra(EXTRA_Email, email);
        return intent;
    }

    private void initViews(){
        editTextTextEmailAddressResetPassword = findViewById(R.id.editTextTextEmailAddressResetPassword);
        buttonResetPassword = findViewById(R.id.buttonResetPassword);
    }
}