package com.eventa1.eventatake1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

import static com.eventa1.eventatake1.MainActivity.CHAT_PREFS;
import static com.eventa1.eventatake1.MainActivity.USER_ID;

//import com.google.firebase.database.DatabaseReference;

public class regEvent extends AppCompatActivity {

    DatePickerDialog datePickerDialog,datePickerDialog1;
    EditText college,eventname,descrip,yourEditText,date,enddate,contact;
    int mYear,mMonth,mDay;
    TextView tvw;
    CheckBox tech,cultural,workshops,seminar,sports,gaming;
    Button addevent,upload,choose;
    ImageView imgView;
    private Uri filePath;
    private final int PICK_IMAGE_REQUEST = 71;
    DatabaseReference reference,ref1,ref2,ref3,ref4,ref5,ref6;
    Register register;
    List<String> mList=new ArrayList<>();
    FirebaseStorage storage;
    StorageReference storageReference;
    //FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();


    /*@Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK)
            Toast.makeText(getApplicationContext(), "CAN'T GO BACK FROM HERE",
                    Toast.LENGTH_LONG).show();
        return false;
        // Disable back button..............
    }
*/
    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Closing Activity")
                .setMessage("Are you sure you want to close this activity?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent b=new Intent(regEvent.this, Profile.class);
                        startActivity(b);
                        finish();
                    }

                })
                .setNegativeButton("No", null)
                .show();
    }

    private void uploadimage() {

        if(filePath != null)
        {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            final StorageReference ref = storageReference.child("images/"+ UUID.randomUUID().toString());
            ref.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    Log.d("PATH",uri.toString());
                                    register.setImage_url(uri.toString());
                                }
                            });
                            progressDialog.dismiss();
                            Toast.makeText(regEvent.this, "Uploaded", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(regEvent.this, "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot
                                    .getTotalByteCount());
                            progressDialog.setMessage("Uploaded "+(int)progress+"%");
                        }
                    });
        }
    }

    @SuppressLint("WrongViewCast")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg_event);
        upload=findViewById(R.id.upload);
        choose=findViewById(R.id.choose);
        imgView=findViewById(R.id.imgView);
        contact=findViewById(R.id.contact);
        tech=findViewById(R.id.tech);
        cultural=findViewById(R.id.cultural);
        workshops=findViewById(R.id.workshops);
        seminar=findViewById(R.id.seminar);
        sports=findViewById(R.id.sports);
        gaming=findViewById(R.id.gaming);
        college=findViewById(R.id.college);
        eventname=findViewById(R.id.eventname);
        // TextView date = findViewById(R.id.tvSelectedDate);
        // catego=findViewById(R.id.catego);
        descrip=findViewById(R.id.descrip);
        // tvw= findViewById(R.id.editText1);
        addevent=findViewById(R.id.addevent);
        ref1= FirebaseDatabase.getInstance().getReference().child("Technical");
        ref2= FirebaseDatabase.getInstance().getReference().child("Cultural");
        ref6= FirebaseDatabase.getInstance().getReference().child("Sports");
        ref3= FirebaseDatabase.getInstance().getReference().child("Workshops");
        ref4= FirebaseDatabase.getInstance().getReference().child("Seminar");
        ref5= FirebaseDatabase.getInstance().getReference().child("Gaming");
        register=new Register();
        reference= FirebaseDatabase.getInstance().getReference().child("Unconfirmed");
        yourEditText =  findViewById(R.id.date);
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        SharedPreferences prefs = getSharedPreferences(CHAT_PREFS, MODE_PRIVATE);
        final String usrID = prefs.getString(USER_ID,null);

      /*  college.addTextChangedListener(loginTextWatcher);
        eventname.addTextChangedListener(loginTextWatcher);
       // enddate.addTextChangedListener(loginTextWatcher);
        descrip.addTextChangedListener(loginTextWatcher);
        //date.addTextChangedListener(loginTextWatcher);
        contact.addTextChangedListener(loginTextWatcher);*/
        // initiate the date picker and a button
        date =  findViewById(R.id.date);
        enddate=findViewById(R.id.enddate);
        // perform click event on edit text
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // calender class's instance and get current date , month and year from calender
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                // date picker dialog
                datePickerDialog = new DatePickerDialog(regEvent.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // set day of month , month and year value in the edit text
                                date.setText(dayOfMonth + "/"+ (monthOfYear + 1) + "/" + year);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        enddate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // calender class's instance and get current date , month and year from calender
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                // date picker dialog
                datePickerDialog1 = new DatePickerDialog(regEvent.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // set day of month , month and year value in the edit text
                                enddate.setText(dayOfMonth + "/"+ (monthOfYear + 1) + "/" + year);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog1.show();
            }
        });

        /*selectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(regEvent.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                //eText.setText(datePicker.getDayOfMonth()+ "/" + (datePicker.getMonth() + 1) + "/" + year);
                            }
                        }, 0, 0, 0);
                datePickerDialog.show();
            }
        });*/
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadimage();
            }
        });
        choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
            }
        });

        addevent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // register.setCat(catego.getText().toString().trim());


                String col1=college.getText().toString().trim();
                String eve1= eventname.getText().toString().trim();
                String da=date.getText().toString().trim();
                String ed=enddate.getText().toString().trim();
                String de=descrip.getText().toString().trim();
                String con=contact.getText().toString().trim();
                register.setCol(college.getText().toString().trim());
                register.setEve(eventname.getText().toString().trim());
                register.setDate(date.getText().toString().trim());
                register.setEndDate(enddate.getText().toString().trim());
                register.setDes(descrip.getText().toString().trim());
                register.setContact_number(contact.getText().toString().trim());
                if(tech.isChecked()){
                    //reference.child("1").setValue("Technical");
                    mList.add("Technical");
                    //ref1.child(register.getEve()).setValue(eve1);
                }
                if(cultural.isChecked()){
                    mList.add("Cultural");
                    //ref2.child(register.getEve()).setValue(eve1);
                }
                if(workshops.isChecked())
                {
                    mList.add("Workshops");
                    //ref3.child(register.getEve()).setValue(eve1);
                }
                if(seminar.isChecked())
                {
                    mList.add("Seminar");
                    //ref4.child(register.getEve()).setValue(eve1);
                }
                if(gaming.isChecked())
                {
                    mList.add("Gaming");
                    //ref5.child(register.getEve()).setValue(eve1);
                }
                if(sports.isChecked())
                {
                    mList.add("Sports");
                    //ref6.child(register.getEve()).setValue(eve1);
                }
                register.setmList(mList);
                if(!col1.isEmpty() && !eve1.isEmpty() && !de.isEmpty() && !con.isEmpty() && !da.isEmpty() && !ed.isEmpty() && (sports.isChecked() || gaming.isChecked() || seminar.isChecked() || workshops.isChecked() || tech.isChecked() || cultural.isChecked()))
                {

                    reference.child(usrID).child(register.getEve()).setValue(register);
                    Intent k=new Intent(regEvent.this,Competition.class);
                    k.putExtra("eventnam",  eventname.getText().toString());
                    startActivity(k);
                    finish();
                }
                else
                {
                    if(sports.isChecked() || gaming.isChecked() || seminar.isChecked() || workshops.isChecked() || tech.isChecked() || cultural.isChecked()) {
                        Toast.makeText(regEvent.this, "All fields are compulsory", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(regEvent.this, "Atleast one category must be checked", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });

    }

    //.addTextChangedListener(loginTextWatcher);
    //editTextPassword.addTextChangedListener(loginTextWatcher);

    /* private TextWatcher loginTextWatcher = new TextWatcher() {
         @Override
         public void beforeTextChanged(CharSequence s, int start, int count, int after) {
             addevent.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {

                 }
             });
         }

         @Override
         public void onTextChanged(CharSequence s, int start, int before, int count) {
            // String usernameInput = editTextUsername.getText().toString().trim();
             //String passwordInput = editTextPassword.getText().toString().trim();
             String col1=college.getText().toString().trim();
             String eve1= eventname.getText().toString().trim();
             //String da=date.getText().toString().trim();
             //String ed=enddate.getText().toString().trim();
             String de=descrip.getText().toString().trim();
             String con=contact.getText().toString().trim();

             addevent.setEnabled(!col1.isEmpty() && !eve1.isEmpty() && !de.isEmpty() && !con.isEmpty() );
         }

         @Override
         public void afterTextChanged(Editable s) {

         }
     };*/
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null )
        {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                imgView.setImageBitmap(bitmap);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }
}
