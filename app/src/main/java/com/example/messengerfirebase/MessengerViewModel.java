package com.example.messengerfirebase;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MessengerViewModel extends ViewModel {
    private FirebaseAuth auth;
    private MutableLiveData<FirebaseUser> user = new MutableLiveData<>();
    private MutableLiveData<List<User>> users = new MutableLiveData<>();

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference usersReference;

    public LiveData<List<User>> getUsers() {
        return users;
    }

    public LiveData<FirebaseUser> getUser() {
        return user;
    }

    public MessengerViewModel() {
        auth = FirebaseAuth.getInstance();

        auth.addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                user.setValue(firebaseAuth.getCurrentUser());
            }
        });

        firebaseDatabase = FirebaseDatabase.getInstance();
        usersReference = firebaseDatabase.getReference("Users");

        usersReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                FirebaseUser currentUser = auth.getCurrentUser();
                if (currentUser == null) {
                    return;
                }

                List<User> usersFromDB = new ArrayList<>();

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    User user = dataSnapshot.getValue(User.class);
                    if (user == null) {
                        return;
                    }
                    if (!user.getId().equals(currentUser.getUid())) {
                        usersFromDB.add(user);
                    }
                }
                users.setValue(usersFromDB);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void signOut() {
        auth.signOut();
    }
}
