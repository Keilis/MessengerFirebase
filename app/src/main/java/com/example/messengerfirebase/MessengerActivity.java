package com.example.messengerfirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class MessengerActivity extends AppCompatActivity {
    private MainActivity mainActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messenger);
    }

    static Intent newIntent(Context context) {
        Intent intent = new Intent(context, MessengerActivity.class);
        return intent;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sign_out_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.signOutMenu) {
            mainActivity.getAuth().signOut();
            launchMainActivity();
        }
        return super.onOptionsItemSelected(item);
    }

    private void launchMainActivity() {
        Intent intent = MainActivity.newIntent(this);
        startActivity(intent);
    }
}