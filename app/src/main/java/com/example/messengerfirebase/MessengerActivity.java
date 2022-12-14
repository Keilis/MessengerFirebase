package com.example.messengerfirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseUser;

public class MessengerActivity extends AppCompatActivity {
    private MessengerViewModel messengerViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messenger);

        messengerViewModel = new ViewModelProvider(this).get(MessengerViewModel.class);

        observeViewModel();
    }

    private void observeViewModel() {
        messengerViewModel.getUser().observe(this, new Observer<FirebaseUser>() {
            @Override
            public void onChanged(FirebaseUser firebaseUser) {
                if (firebaseUser == null) {
                    launchLoginActivity();
                    finish();
                }
            }
        });
    }

    static Intent newIntent(Context context) {
        return new Intent(context, MessengerActivity.class);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sign_out_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.signOutMenu) {
            messengerViewModel.signOut();
        }
        return super.onOptionsItemSelected(item);
    }

    private void launchLoginActivity() {
        Intent intent = LoginActivity.newIntent(this);
        startActivity(intent);
    }
}