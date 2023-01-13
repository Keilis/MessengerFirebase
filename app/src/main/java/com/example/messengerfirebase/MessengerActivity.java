package com.example.messengerfirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MessengerActivity extends AppCompatActivity {
    private MessengerViewModel messengerViewModel;

    private UsersAdapter usersAdapter;

    private static final String EXTRA_CURRENT_USER_ID = "current_id";
    private String currentUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messenger);

        initViews();

        currentUserId = getIntent().getStringExtra(EXTRA_CURRENT_USER_ID);

        messengerViewModel = new ViewModelProvider(this).get(MessengerViewModel.class);

        observeViewModel();

        usersAdapter.setOnUserClickListener(new UsersAdapter.OnUserClickListener() {
            @Override
            public void onUserClick(User user) {
                Intent intent = ChatActivity.newIntent(MessengerActivity.this, currentUserId, user.getId());
                startActivity(intent);
            }
        });

    }

    private void initViews(){
        RecyclerView recyclerViewUsers = findViewById(R.id.recyclerViewUsers);
        usersAdapter = new UsersAdapter();
        recyclerViewUsers.setAdapter(usersAdapter);
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
        messengerViewModel.getUsers().observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {
                usersAdapter.setUsers(users);
            }
        });
    }

    public static Intent newIntent(Context context, String currentUserId) {
        Intent intent = new Intent(context, MessengerActivity.class);
        intent.putExtra(EXTRA_CURRENT_USER_ID, currentUserId);
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
            messengerViewModel.signOut();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        messengerViewModel.setUserOnline(true);
    }

    @Override
    protected void onPause() {
        super.onPause();
        messengerViewModel.setUserOnline(false);
    }

    private void launchLoginActivity() {
        Intent intent = LoginActivity.newIntent(this);
        startActivity(intent);
    }
}