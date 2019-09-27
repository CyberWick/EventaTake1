package com.eventa1.eventatake1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class forgot extends AppCompatActivity {

        EditText email;
        Button reset;
        FirebaseAuth auth1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot);

        email=findViewById(R.id.email);
        reset=findViewById(R.id.reset);

        auth1=FirebaseAuth.getInstance();

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mail=email.getText().toString();

                if(mail.equals(""))
                {
                    Toast.makeText(forgot.this,"All fields are compulsory",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    auth1.sendPasswordResetEmail(mail).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful())
                            {
                                Toast.makeText(forgot.this,"Please check your email",Toast.LENGTH_SHORT).show();
                                Intent i=new Intent(forgot.this,LoginActivity.class);
                                startActivity(i);
                            }
                            else
                            {
                                String error=task.getException().getMessage();
                                Toast.makeText(forgot.this,error,Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }
}
