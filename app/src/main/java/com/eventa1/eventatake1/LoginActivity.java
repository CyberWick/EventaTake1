package com.eventa1.eventatake1;

import android.content.Intent;
import android.content.SharedPreferences;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.ArraySet;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Set;

import static com.eventa1.eventatake1.MainActivity.CHAT_PREFS;
import static com.eventa1.eventatake1.MainActivity.DATE_OF_BIRTH_KEY;
import static com.eventa1.eventatake1.MainActivity.DISPLAY_NAME_KEY;
import static com.eventa1.eventatake1.MainActivity.FAVEVENTS_LIST;
import static com.eventa1.eventatake1.MainActivity.LOG_CHECK_KEY;
import static com.eventa1.eventatake1.MainActivity.PHONE_KEY;
import static com.eventa1.eventatake1.MainActivity.USER_ID;

public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    // UI references.
    private TextView mEmailView,forgotpass;
    private EditText mPasswordView;
    private  SharedPreferences prefs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        forgotpass=findViewById(R.id.forgotpass);
        mEmailView = findViewById(R.id.mailog);
        mPasswordView = findViewById(R.id.pswrdlog);
        mAuth = FirebaseAuth.getInstance();
        forgotpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(LoginActivity.this,forgot.class);
                startActivity(i);
            }
        });
    }
    public void logIn(View v){
        final String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        if (email.isEmpty())
            if (email.equals("") || password.equals("")) return;
        Toast.makeText(this, "Login in progress...", Toast.LENGTH_SHORT).show();

        // TODO: Use FirebaseAuth to sign in with email & password
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                Log.d("FlashChat", "signInWithEmail() onComplete: " + task.isSuccessful());

                if (!task.isSuccessful()) {
                    Log.d("FlashChat", "Problem signing in: " + task.getException());

                } else {
                    //MainActivity m1 = new MainActivity();
                    //prefs = m1.getPrefs();
                    prefs = getSharedPreferences(CHAT_PREFS, MODE_PRIVATE);
//                    prefs.edit().putString(DISPLAY_NAME_KEY, displayName).apply();
//                    prefs.edit().putString(PHONE_KEY, phnno).apply();
//                    prefs.edit().putString(DATE_OF_BIRTH_KEY, dob).apply();

                    prefs.edit().putBoolean(LOG_CHECK_KEY, true).apply();
                    //Set<String>
                    FirebaseUser usr1 = mAuth.getCurrentUser();
                    Log.d("flashchat","USERID FROM USER : " + usr1.getUid());
                    prefs.edit().putString(USER_ID,usr1.getUid()).apply();
                    Log.d("flashchat","LOGIN USRID : " + prefs.getString(USER_ID,null));
                    GetFavourites();
                    Intent intent = new Intent(LoginActivity.this, WelcomeHome.class);
                    intent.putExtra("email",email);
                    intent.putExtra("dbusr",usr1.getUid());
                    finish();
                    startActivity(intent);
                }

            }
        });
    }
    public void signUpBack(View v){
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
    private void GetFavourites() {

        final DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("Favourites");
        final String usrID = prefs.getString(USER_ID,null);
        Log.d("flashchat","In GET FAVOURITES");
        //dbRef = dbRef.child()
        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.hasChild(usrID)) {
                    Log.d("flashchat","FAV AlREADY EXISTS");
                    dbRef.child(usrID).child("EventName").addValueEventListener(new ValueEventListener() {
                        Set<String> favEvents = new ArraySet<>();
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            favEvents = new ArraySet<>();
                            Log.d("flashchat","BEFORE ADDING " + favEvents.size());
                            for(DataSnapshot snapshot1 : dataSnapshot.getChildren()){
                                Log.d("flashchat","FOUND " + snapshot1.getKey());
                                favEvents.add(snapshot1.getKey());
                            }
                            Log.d("flashchat","SIZE OF FAVS IN LOGIN : " + favEvents.size());
                            prefs.edit().putStringSet(FAVEVENTS_LIST,favEvents).apply();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            prefs.edit().putStringSet(FAVEVENTS_LIST,favEvents).apply();
                        }

                    });
                }
                else {
                    Set<String> nullSet = new ArraySet<>();
                    prefs.edit().putStringSet(FAVEVENTS_LIST,nullSet).apply();
//                    List<EventsInfo> favEveList1 = new ArrayList<>();
                    Log.d("flashchatad","NOT IN IF SIZE OF FAVEVELIST : " + nullSet.size());
//                    ifFirebaseLoad.onFirebaseLoadSuccess(favEveList1);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("flashchat","FAV was not PRESENT");
                Set<String> favEvents = new ArraySet<>();
                prefs.edit().putStringSet(FAVEVENTS_LIST,favEvents).apply();
            }
        });

    }
}
