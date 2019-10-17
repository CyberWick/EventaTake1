package com.eventa1.eventatake1;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.media.Image;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.collection.ArraySet;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Set;

public class MainActivity extends AppCompatActivity {
    static final String CHAT_PREFS = "ChatPrefs";
    static final String LOG_CHECK_KEY = "logged";
    static final String FAVEVENTS_LIST = "FavEventsList";
    static final String DISPLAY_NAME_KEY = "username";
    static final String COLLEGE_NAME_KEY = "college";
    static final String DATE_OF_BIRTH_KEY = "DOB";
    static final String PHONE_KEY = "Phone";
    private FirebaseAuth mAuth;
    static final String USER_ID = "user_ID";
    FirebaseAuth.AuthStateListener mAuthListener;
    protected SharedPreferences prefs;
    private DatabaseReference dbRef;
    private TextView mEmailView;
    private TextView mUsernameView;
    private EditText mPasswordView;
    private EditText mConfirmPasswordView;
//    private TextView mCollege;
    private TextView mPhone;
    DatePickerDialog datePickerDialog;
    private EditText mDOB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
//        mAuthListener = new FirebaseAuth.AuthStateListener(){
//            @Override
//            public  void  onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth){
//                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//                if(user!=null){
//                    Intent intent = new Intent(MainActivity.this, WelcomeHome.class);
//                    startActivity(intent);
//                    finish();
//                }
//            }
//
//
//        };
        prefs = getSharedPreferences(CHAT_PREFS,MODE_PRIVATE);
        Log.d("flashchat","CHECKING IF ALREADY LOGGED IN");
        if(prefs.getBoolean(LOG_CHECK_KEY,false)){
            Log.d("flashchat","GOOD ALREADY LOGGED ONCE");
            Intent intent = new Intent(MainActivity.this, WelcomeHome.class);
            intent.putExtra("email","");
            startActivity(intent);
            finish();
        }
        setContentView(R.layout.activity_main);
        mEmailView = (TextView) findViewById(R.id.email);
        mPasswordView = (EditText) findViewById(R.id.pswrd);
        mConfirmPasswordView = (EditText) findViewById(R.id.pswrd);
        mUsernameView = (TextView) findViewById(R.id.usrnamehome);
        mPhone = findViewById(R.id.phone);
        mDOB = findViewById(R.id.dob);
        mConfirmPasswordView = findViewById(R.id.conpswrd);
        Log.d("flashchat","STARTED APP");
        dbRef = FirebaseDatabase.getInstance().getReference();

        mDOB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                // date picker dialog
                datePickerDialog = new DatePickerDialog(MainActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // set day of month , month and year value in the edit text
                                mDOB.setText(dayOfMonth + "/"+ (monthOfYear + 1) + "/" + year);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });
    }
    @Override
    public void onStart(){
        super.onStart();
        Log.d("flashchat","OnSTART");
        //FirebaseUser currUser = mAuth.getCurrentUser();

    }

    public void updateUI(FirebaseUser currUser) {
        if(!currUser.equals(null)) {
            Intent intent = new Intent(MainActivity.this, WelcomeHome.class);
            finish();
            startActivity(intent);
        }
    }
    private boolean isEmailValid(String email) {
        // You can add more checking logic here.
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Add own logic to check for a valid password (minimum 6 characters)
        String confirmPass = mConfirmPasswordView.getText().toString();

        return password.equals(confirmPass) && confirmPass.length()>5;
    }
    public void signUp(View v){
        Log.d("flashchat","ATTEMPTING REGISTRATION");
        attemptRegistration();
    }
    private void attemptRegistration(){
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(password) || !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // TODO: Call create FirebaseUser() here
            createFirebaseUser();
        }
    }
    private void createFirebaseUser() {
        Log.d("flashchat","GETTING DONE WITH USER");
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();
//        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//            @Override
//            public void onComplete(@NonNull Task<AuthResult> task) {
//                Log.d("FlashChat","CREATING USER" + task.isSuccessful());
//                if(!task.isSuccessful()){
//                    Log.d("FlashChat","Registration failed");
//                    ShowErrorDialog();
//                }
//            }
//        });
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("flashchat", "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            saveData();
                            Intent intent = new Intent(MainActivity.this, WelcomeHome.class);
                            intent.putExtra("email","");
                            finish();
                            startActivity(intent);

                            //updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("flashchat", "createUserWithEmail:failure", task.getException());
                            //Toast.makeText(EmailPasswordActivity.this, "Authentication failed.",
                                    //Toast.LENGTH_SHORT).show();
                            ShowErrorDialog();
                        }

                        // ...
                    }
                });
        Log.d("flashchat","Done WHETEREVR");

    }
    public void logIn(View v){
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
    private void ShowErrorDialog() {
        new AlertDialog.Builder(this)
                .setTitle("OOPS")
                .setMessage("Something went wrong!")
                .setPositiveButton(android.R.string.ok,null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
    private void saveData(){Log.d("flashchat","STARTING STORE");
        Log.d("flashchat","STARTING STORE");
        String displayName = mUsernameView.getText().toString();
        String phnno = mPhone.getText().toString();
        String dob = mDOB.getText().toString();
        String email = mEmailView.getText().toString();
        prefs = getSharedPreferences(CHAT_PREFS, 0);
        prefs.edit().putString(DISPLAY_NAME_KEY, displayName).apply();
        prefs.edit().putString(PHONE_KEY, phnno).apply();
        prefs.edit().putString(DATE_OF_BIRTH_KEY, dob).apply();
        prefs.edit().putBoolean(LOG_CHECK_KEY, true).apply();
        Set<String> favEvents = new ArraySet<>();
        prefs.edit().putStringSet(FAVEVENTS_LIST,favEvents);
        Log.d("flashchat","DONE SREF");
        UserInfo1 usr1 = new UserInfo1(displayName,dob,phnno);
        Log.d("flashchat","STARTING TO STORE IN DB");
        FirebaseUser usr = mAuth.getCurrentUser();

        try {
            dbRef.child("users").child(usr.getUid()).setValue(usr1);

        prefs.edit().putString(USER_ID,usr.getUid()).apply();
        Log.d("flashchat","ID : " + prefs.getString(USER_ID,null));
        }catch(Exception e){
            Log.d("flashchat",e.toString());
        }
        Log.d("flashchat","db UPDATED");
    }
    public SharedPreferences getPrefs(){
        return prefs;
    }
}
