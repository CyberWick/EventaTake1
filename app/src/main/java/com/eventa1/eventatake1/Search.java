package com.eventa1.eventatake1;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.os.Bundle;
import android.util.ArraySet;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


public class Search extends AppCompatActivity implements RegisterLoad {
    private DatabaseReference mRef;
    private RegisterLoad ifFirebaseLoad;

    private ListView mlistt,Eventlist;
    private CardView cardView;

    private ArrayList<String> events=new ArrayList<>();
    private ArrayAdapter<String> adapter;


    SearchView button;
    GridLayout gid;
    TextView text;
    private TextView noFound;
    CardView card,cardt,cardg,cards,cardf,cardo;
    ListView mlist;
    private final static int ACTIVITY_NUMBER = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        BottomNavigationViewEx bottomNavigationViewEx = findViewById(R.id.botbar);
        BottomNavHelper.enableNavigation(Search.this,bottomNavigationViewEx);
        Menu menu = bottomNavigationViewEx.getMenu();
        MenuItem menuItem = menu.getItem(ACTIVITY_NUMBER);
        menuItem.setChecked(true);

        Eventlist= findViewById(R.id.listview);
        noFound = findViewById(R.id.nofoundmsg);
        noFound.setVisibility(View.INVISIBLE);
        // mysearchview=findViewById(R.id.searchView);

        button = findViewById(R.id.searchView);
        gid= findViewById(R.id.grid);
        text= findViewById(R.id.textGrid);
        card= findViewById(R.id.cardview0) ;
        cardt= findViewById(R.id.cardview1);
        cardg= findViewById(R.id.cardview2);
        cards= findViewById(R.id.cardview3);
        cardf= findViewById(R.id.cardview4);
        cardo= findViewById(R.id.cardview5);
        mlistt= findViewById(R.id.listview);

        ifFirebaseLoad = this;
        adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,events);
        Eventlist.setAdapter(adapter);


        button.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(final String s) {

                mRef = FirebaseDatabase.getInstance().getReference("Register");
                mRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        List<Register> regList = new ArrayList<>();
                        for(DataSnapshot temp : dataSnapshot.getChildren()){
                            //Log.d("searchss","FOUND EVENT : " + temp.getKey());
                            if(temp.getKey().contains(s)){
                                Register regTemp =dataSnapshot.child(temp.getKey()).getValue(Register.class);
                                regList.add(regTemp);
                                Log.d("searchss","ADDED  : " + regTemp.getEve());
                            }
                        }
                        ifFirebaseLoad.onFirebaseLoadSuccess(regList);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                return false;
            }

            @Override
            public boolean onQueryTextChange(final String s) {
                mRef = FirebaseDatabase.getInstance().getReference("Register");
                mRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        List<Register> regList = new ArrayList<>();
                        for(DataSnapshot temp : dataSnapshot.getChildren()){
                            int i=0;
                            if(temp.getKey().contains(s)){
                                Register regTemp =dataSnapshot.child(temp.getKey()).getValue(Register.class);
                                regList.add(regTemp);
                                Log.d("searchss","ADDED  : " + regList.get(i).getEve());
                                i++;
                            }
                        }
                        Log.d("searchss","LIST SIZEE PASSED FROM SEARCHVIEW : " + regList.toString());
                        ifFirebaseLoad.onFirebaseLoadSuccess(regList);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                return false;
            }
        });


        mlistt.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                gid.setVisibility(View.INVISIBLE);
            }
        });
        cardt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                mlistt.setVisibility(View.VISIBLE);
                mRef= FirebaseDatabase.getInstance().getReference("Cultural");
                gid.setVisibility(View.INVISIBLE);
                text.setVisibility(View.INVISIBLE);
                final List<Register> elist=new ArrayList<>();
                final Set<String> value = new ArraySet<>();
                DatabaseReference db= FirebaseDatabase.getInstance().getReference("Register");
                mRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        //String value;


                        for (DataSnapshot temp : dataSnapshot.getChildren()) {

                            value.add(temp.getValue(String.class));
                            Log.d("search", "FOund Technical " + value.toString());
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                db.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        for (DataSnapshot temp:dataSnapshot.getChildren()) {
                            Log.d("search","FOUND IN EGISTER " + temp.getKey());
                            for (String str : value) {
                                if (str.equals(temp.getKey())) {
                                    Log.d("search","MATEHCEd EGISTER " + temp.getKey());
                                    Register evt = dataSnapshot.child(str).getValue(Register.class);
                                    Log.d("flachchat","ADDED : " + evt.getEve());
                                    elist.add(evt);

                                }


                            }
                        }
                        Log.d("search","Elist Size : " + elist.size());
                        ifFirebaseLoad.onFirebaseLoadSuccess(elist);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }



        });

        cardg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                // card.setVisibility(View.INVISIBLE);
                mlistt.setVisibility(View.VISIBLE);
                mRef= FirebaseDatabase.getInstance().getReference("Gaming");
                gid.setVisibility(View.INVISIBLE);
                text.setVisibility(View.INVISIBLE);
                final List<Register> elist=new ArrayList<>();
                final Set<String> value = new ArraySet<>();
                DatabaseReference db= FirebaseDatabase.getInstance().getReference("Register");
                mRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        //String value;


                        for (DataSnapshot temp : dataSnapshot.getChildren()) {

                            value.add(temp.getValue(String.class));
                            Log.d("search", "FOund Technical " + value.toString());
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                db.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        for (DataSnapshot temp:dataSnapshot.getChildren()) {
                            Log.d("search","FOUND IN EGISTER " + temp.getKey());
                            for (String str : value) {
                                if (str.equals(temp.getKey())) {
                                    Log.d("search","MATEHCEd EGISTER " + temp.getKey());
                                    Register evt = dataSnapshot.child(str).getValue(Register.class);
                                    Log.d("flachchat","ADDED : " + evt.getEve());
                                    elist.add(evt);

                                }


                            }
                        }
                        Log.d("search","Elist Size : " + elist.size());
                        ifFirebaseLoad.onFirebaseLoadSuccess(elist);



                        ;
                        //events.add(value);
                        //a//dapter.notifyDataSetChanged();

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }

        });

        cards.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                // card.setVisibility(View.INVISIBLE);
                mlistt.setVisibility(View.VISIBLE);
                mRef= FirebaseDatabase.getInstance().getReference("Sports");
                gid.setVisibility(View.INVISIBLE);
                text.setVisibility(View.INVISIBLE);
                final List<Register> elist=new ArrayList<>();
                final Set<String> value = new ArraySet<>();
                DatabaseReference db= FirebaseDatabase.getInstance().getReference("Register");
                mRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        //String value;


                        for (DataSnapshot temp : dataSnapshot.getChildren()) {

                            value.add(temp.getValue(String.class));
                            Log.d("search", "FOund Technical " + value.toString());
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                db.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        for (DataSnapshot temp:dataSnapshot.getChildren()) {
                            Log.d("search","FOUND IN EGISTER " + temp.getKey());
                            for (String str : value) {
                                if (str.equals(temp.getKey())) {
                                    Log.d("search","MATEHCEd EGISTER " + temp.getKey());
                                    Register evt = dataSnapshot.child(str).getValue(Register.class);
                                    Log.d("flachchat","ADDED : " + evt.getEve());
                                    elist.add(evt);

                                }


                            }
                        }
                        Log.d("search","Elist Size : " + elist.size());
                        ifFirebaseLoad.onFirebaseLoadSuccess(elist);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

        cardf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                // card.setVisibility(View.INVISIBLE);
                mlistt.setVisibility(View.VISIBLE);
                mRef= FirebaseDatabase.getInstance().getReference("Workshops");
                gid.setVisibility(View.INVISIBLE);
                text.setVisibility(View.INVISIBLE);
                final List<Register> elist=new ArrayList<>();
                final Set<String> value = new ArraySet<>();
                DatabaseReference db= FirebaseDatabase.getInstance().getReference("Register");
                mRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        //String value;


                        for (DataSnapshot temp : dataSnapshot.getChildren()) {

                            value.add(temp.getValue(String.class));
                            Log.d("search", "FOund Technical " + value.toString());
                        }
                    }

//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                db.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        for (DataSnapshot temp:dataSnapshot.getChildren()) {
                            Log.d("search","FOUND IN EGISTER " + temp.getKey());
                            for (String str : value) {
                                if (str.equals(temp.getKey())) {
                                    Log.d("search","MATEHCEd EGISTER " + temp.getKey());
                                    Register evt = dataSnapshot.child(str).getValue(Register.class);
                                    Log.d("flachchat","ADDED : " + evt.getEve());
                                    elist.add(evt);

                                }


                            }
                        }
                        Log.d("search","Elist Size : " + elist.size());
                        ifFirebaseLoad.onFirebaseLoadSuccess(elist);



                        ;
                        //events.add(value);
                        //a//dapter.notifyDataSetChanged();

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }

        });


        cardo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // card.setVisibility(View.INVISIBLE);
                mlistt.setVisibility(View.VISIBLE);
                mRef= FirebaseDatabase.getInstance().getReference("Other");
                gid.setVisibility(View.INVISIBLE);
                text.setVisibility(View.INVISIBLE);
                final List<Register> elist=new ArrayList<>();
                final Set<String> value = new ArraySet<>();
                DatabaseReference db= FirebaseDatabase.getInstance().getReference("Register");
                mRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        //String value;


                        for (DataSnapshot temp : dataSnapshot.getChildren()) {

                            value.add(temp.getValue(String.class));
                            Log.d("search", "FOund Technical " + value.toString());
                        }
                    }

//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                db.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        for (DataSnapshot temp:dataSnapshot.getChildren()) {
                            Log.d("search","FOUND IN EGISTER " + temp.getKey());
                            for (String str : value) {
                                if (str.equals(temp.getKey())) {
                                    Log.d("search","MATEHCEd EGISTER " + temp.getKey());
                                    Register evt = dataSnapshot.child(str).getValue(Register.class);
                                    Log.d("flachchat","ADDED : " + evt.getEve());
                                    elist.add(evt);

                                }


                            }
                        }
                        Log.d("search","Elist Size : " + elist.size());
                        ifFirebaseLoad.onFirebaseLoadSuccess(elist);



                        ;
                        //events.add(value);
                        //a//dapter.notifyDataSetChanged();

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

        button.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                Log.i("onFocusChange: ",String.valueOf(b));
                if (b) {
                    gid.setVisibility(View.INVISIBLE);
                    text.setVisibility(View.INVISIBLE);
                } else {
                    gid.setVisibility(View.VISIBLE);
                    text.setVisibility(View.VISIBLE);
                }
            }

        });





        button.setOnClickListener(new View.OnClickListener() {
                                      @Override
                                      public void onClick(View view)
                                      {

                                          button.setVisibility(View.VISIBLE);
                                          gid.setVisibility(View.INVISIBLE);
                                          text.setVisibility(View.INVISIBLE);



                                      }




                                  }

        );









        card.setOnClickListener(new View.OnClickListener(){



            public void onClick(View v)
            {
                // card.setVisibility(View.INVISIBLE);
                mlistt.setVisibility(View.VISIBLE);
                mRef= FirebaseDatabase.getInstance().getReference("Technical");
                gid.setVisibility(View.INVISIBLE);
                text.setVisibility(View.INVISIBLE);
                final List<Register> elist=new ArrayList<>();
                final Set<String> value = new ArraySet<>();
                DatabaseReference db= FirebaseDatabase.getInstance().getReference("Register");
                mRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        //String value;


                        for (DataSnapshot temp : dataSnapshot.getChildren()) {

                            value.add(temp.getValue(String.class));
                            Log.d("search", "FOund Technical " + value.toString());
                        }
                    }

//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                db.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        for (DataSnapshot temp:dataSnapshot.getChildren()) {
                            Log.d("search","FOUND IN EGISTER " + temp.getKey());
                            for (String str : value) {
                                if (str.equals(temp.getKey())) {
                                    Log.d("search","MATEHCEd EGISTER " + temp.getKey());
                                    Register evt = dataSnapshot.child(str).getValue(Register.class);
                                    Log.d("flachchat","ADDED : " + evt.getEve());
                                    elist.add(evt);

                                }


                            }
                        }
                        Log.d("search","Elist Size : " + elist.size());
                        ifFirebaseLoad.onFirebaseLoadSuccess(elist);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }

        });
    }


    @Override
    public void onFirebaseLoadSuccess(List<Register> list) {
        Log.d("searchss","SIZE of LIST PASSED : " + list.toString() + "SIZE : " + list.size());
        if(list.size()>0) {
            mlistt.setVisibility(View.VISIBLE);
            noFound.setVisibility(View.INVISIBLE);
            Eventlist.setAdapter(new CustomAdapter(list, this));
        }else {
            mlistt.setVisibility(View.INVISIBLE);
            noFound.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onFirebaseLoadFail(String message) {

    }
}
