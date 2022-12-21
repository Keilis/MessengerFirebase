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

public class ForgetPasswordActivity extends AppCompatActivity {

    private static final String EXTRA_Email = "email";

    private ResetPasswordViewModel resetPasswordViewModel;

    private EditText editTextTextEmailAddressResetPassword;
    private Button buttonResetPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        initViews();

        resetPasswordViewModel = new ViewModelProvider(this).get(ResetPasswordViewModel.class);

        observeViewModel();

        String email = getIntent().getStringExtra(EXTRA_Email);
        editTextTextEmailAddressResetPassword.setText(email);
        buttonResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = editTextTextEmailAddressResetPassword.getText().toString().trim();
                resetPasswordViewModel.resetPassword(email);
            }
        });
    }

    private void observeViewModel(){
        resetPasswordViewModel.getError().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String errorMessage) {
                if(errorMessage != null) {
                    Toast.makeText(ForgetPasswordActivity.this, errorMessage,Toast.LENGTH_SHORT).show();
                }
            }
        });

        resetPasswordViewModel.getSuccess().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean success) {
                if (success){
                    Toast.makeText(ForgetPasswordActivity.this, R.string.success_reset_email,Toast.LENGTH_SHORT).show();
                }
            }
        });
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