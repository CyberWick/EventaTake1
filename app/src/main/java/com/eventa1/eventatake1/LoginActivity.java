package com.eventa1.eventatake1;

import android.content.Intent;
import android.content.SharedPreferences;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
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

import static com.eventa1.eventatake1.MainActivity.CHAT_PREFS;
import static com.eventa1.eventatake1.MainActivity.DATE_OF_BIRTH_KEY;
import static com.eventa1.eventatake1.MainActivity.DISPLAY_NAME_KEY;
import static com.eventa1.eventatake1.MainActivity.LOG_CHECK_KEY;
import static com.eventa1.eventatake1.MainActivity.PHONE_KEY;

public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    // UI references.
    private TextView mEmailView;
    private EditText mPasswordView;
    private  SharedPreferences prefs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mEmailView = findViewById(R.id.mailog);
        mPasswordView = findViewById(R.id.pswrdlog);
        mAuth = FirebaseAuth.getInstance();
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
                    MainActivity m1 = new MainActivity();
                    prefs = m1.getPrefs();
                    prefs = getSharedPreferences(CHAT_PREFS, 0);
//                    prefs.edit().putString(DISPLAY_NAME_KEY, displayName).apply();
//                    prefs.edit().putString(PHONE_KEY, phnno).apply();
//                    prefs.edit().putString(DATE_OF_BIRTH_KEY, dob).apply();
                    prefs.edit().putBoolean(LOG_CHECK_KEY, true).apply();
                    FirebaseUser usr1 = mAuth.getCurrentUser();
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
}
