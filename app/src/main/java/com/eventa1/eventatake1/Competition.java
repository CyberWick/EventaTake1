package com.eventa1.eventatake1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class Competition extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Spinner categories;
    DatabaseReference reference1;
    EditText price,eventname2,descrip2;
    Compete compete;
    Button another,finish;
    private String eventnam;

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK)
            Toast.makeText(getApplicationContext(), "PLEASE CLICK FINISH TO GO BACK",
                    Toast.LENGTH_LONG).show();

        return false;
        // Disable back button..............
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_competition);
        categories=findViewById(R.id.categories);
        price=findViewById(R.id.price);
        Bundle bundle=getIntent().getExtras();
        eventnam=bundle.getString("eventnam");
        eventname2=findViewById(R.id.eventname2);
        descrip2=findViewById(R.id.descrip2);
        another=findViewById(R.id.another);
        finish=findViewById(R.id.finish);
        List<String> cate=new ArrayList<>();
        cate.add("Technical");
        cate.add("Cultural");
        cate.add("Sports");
        cate.add("Workshops");
        cate.add("Seminar");
        cate.add("Gaming");
        ArrayAdapter<String>dataAdapter=new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,cate);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categories.setAdapter(dataAdapter);
        compete=new Compete();
        reference1= FirebaseDatabase.getInstance().getReference().child("Register");

        another.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                compete.setDes2(descrip2.getText().toString().trim());
                compete.setEvename2(eventname2.getText().toString().trim());
                compete.setPric(Integer.parseInt(price.getText().toString().trim()));
                compete.setText(categories.getSelectedItem().toString());
                reference1.child(eventnam).child("Compete").child(compete.getEvename2()).setValue(compete);

                Intent k=new Intent(Competition.this, Competition.class);
                k.putExtra("eventnam",eventnam);
                startActivity(k);
                finish();
            }
        });

        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent k=new Intent(Competition.this, Profile.class);
                startActivity(k);
                finish();
            }
        });
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        Toast.makeText(getApplicationContext(),"Selected"+adapterView.getItemAtPosition(i).toString(),Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
