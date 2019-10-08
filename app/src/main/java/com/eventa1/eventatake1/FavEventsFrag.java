package com.eventa1.eventatake1;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.ArraySet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static android.content.Context.MODE_PRIVATE;
import static com.eventa1.eventatake1.MainActivity.CHAT_PREFS;
import static com.eventa1.eventatake1.MainActivity.FAVEVENTS_LIST;
import static com.eventa1.eventatake1.MainActivity.USER_ID;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FavEventsFrag.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FavEventsFrag#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FavEventsFrag extends Fragment implements HostFirebase{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private HostFirebase mIfFirebaseLoad;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private List<Register> favList= new ArrayList<>();
    private OnFragmentInteractionListener mListener;
    private SharedPreferences prefs;
    private ListView mListView;
    private String usrID;
    private View rootView;
    private Context mContext;
    private TextView textView;
    public FavEventsFrag() {
        // Required empty public constructor
        Log.d("flashchat" ,"IN FAVOURITES NOW");
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FavEventsFrag.
     */
    // TODO: Rename and change types and number of parameters
    public static FavEventsFrag newInstance(String param1, String param2) {
        FavEventsFrag fragment = new FavEventsFrag();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        SharedPreferences prefs = this.getActivity().getSharedPreferences(CHAT_PREFS, MODE_PRIVATE);
        usrID = prefs.getString(USER_ID,null);
        mIfFirebaseLoad = this;


        //mListView.setAdapter(new FavEventsAdapter());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_fav_events, container, false);
        textView = rootView.findViewById(R.id.favText);
        Log.d("flashchat","CURR TEXT : " + textView.getText().toString());
        //textView.setText("WELL GOT IN");
        mListView = rootView.findViewById(R.id.favlist);
//        List<String> eveList = new Array
        prefs = this.getActivity().getSharedPreferences(CHAT_PREFS,MODE_PRIVATE);
        getFavEvents();
        Log.d("flashchatad","ListView should be set");
        mContext = container.getContext();
        return rootView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
    private void getFavEvents() {
        DatabaseReference RegdbRef = FirebaseDatabase.getInstance().getReference("Register");
//        final DatabaseReference favRef = FirebaseDatabase.getInstance().getReference("Favourites");
//        favRef.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                final List<EventsInfo> favEveList = new ArrayList<>();
//                if(dataSnapshot.hasChild(usrID)){
//                    dataSnapshot = dataSnapshot.child(usrID).child("EventName");
//                    final Iterable<DataSnapshot> favList = dataSnapshot.getChildren();
//                    final Set<String > favSet = new ArraySet<>();
//                    for(DataSnapshot ds : favList){
//                        Log.d("flashchatad","FAVLIST : " + ds.getKey());
//                        favSet.add(ds.getKey());
//                    }
//
//                    DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("Events");
//                    dbRef.addValueEventListener(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//                            for(DataSnapshot dataSnapshot1 :dataSnapshot.getChildren()) {
//                                Log.d("flashchatad","CHILDREN : "  + dataSnapshot1.getKey());
//                                //
//                                for(String temp2 : favSet) {
//                                    Log.d("flashchatad","2 CHILDREN : "  + dataSnapshot1.getKey() + "        " + temp2);
//                                    if(temp2.equals(dataSnapshot1.getKey())){
//                                        EventsInfo temp = dataSnapshot1.getValue(EventsInfo.class);
//                                        Log.d("flashchatad","MATCHED EVENT : " + temp.getTitile());
//                                        favEveList.add(temp);
//                                    }
//                                }
//                                Log.d("flashchatad","SIZE OF FAVEVELIST : " + favEveList.size());
//                            }
//                            mIfFirebaseLoad.onFirebaseLoadSuccess(favEveList);
//                        }
//
//                        @Override
//                        public void onCancelled(@NonNull DatabaseError databaseError) {
//                            List<EventsInfo> favEveList = new ArrayList<>();
//                            Log.d("flashchatad","FAILED SIZE OF FAVEVELIST : " + favEveList.size());
//                            mIfFirebaseLoad.onFirebaseLoadSuccess(favEveList);
//                        }
//                    });
//
//                }else {
//                    List<EventsInfo> favEveList1 = new ArrayList<>();
//                    Log.d("flashchatad","NOT IN IF SIZE OF FAVEVELIST : " + favEveList.size());
//                    mIfFirebaseLoad.onFirebaseLoadSuccess(favEveList1);
//                }
//
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//                List<EventsInfo> favEveList = new ArrayList<>();
//                Log.d("flashchatad","FAILED CHILD SIZE OF FAVEVELIST : " + favEveList.size());
//                mIfFirebaseLoad.onFirebaseLoadSuccess(favEveList);
//            }
//        });

        final String usrID = prefs.getString(USER_ID,null);
        final DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("Favourites");
//        final String usrID = prefs.getString(USER_ID,null);
        Log.d("flashchat","In GET FAVOURITES");
        //dbRef = dbRef.child()
        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.hasChild(usrID)) {
                    Log.d("flashchatLOG","FAV AlREADY EXISTS");
                    dbRef.child(usrID).child("EventName").addValueEventListener(new ValueEventListener() {
                        Set<String> favEvents = new ArraySet<>();
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            favEvents = new ArraySet<>();
                            Log.d("flashchatLOG","BEFORE ADDING " + favEvents.size());
                            for(DataSnapshot snapshot1 : dataSnapshot.getChildren()){
                                Log.d("flashchatLOG","FOUND " + snapshot1.getKey());
                                favEvents.add(snapshot1.getKey());
                            }
                            Log.d("flashchatLOG","SIZE OF FAVS IN LOGIN : " + favEvents.size());
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
                    Log.d("flashchatfav","NOT IN IF SIZE OF FAVEVELIST : " + nullSet.size());
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
        final Set<String> favrList = prefs.getStringSet(FAVEVENTS_LIST,null);
        RegdbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                favList = new ArrayList<>();
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    Log.d("flashchatfav", postSnapshot.getKey());
                    for(String temp : favrList){
                        if(temp.equals(postSnapshot.getKey())){
                            Log.d("flashchatfav","MATCHED" + temp);
                            Register Etemp = dataSnapshot.child(postSnapshot.getKey()).getValue(Register.class);
                            favList.add(Etemp);
                        }
                    }
                    Log.d("flashchatfav", "size of favList : " + favList);
//                    Log.d("flashchat","with IMAGE url  : " + temp.getImage());

                }
                mIfFirebaseLoad.onFirebaseLoadSuccess(favList);

            //}
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
//        Set<String> favEvents = new ArraySet<>();
//        favEvents = prefs.getStringSet(FAVEVENTS_LIST,null);
//        Log.d("flashchat1","FAVEVENTS : " + favEvents.size());
//        //dbRef = dbRef.child()
//        final Set<String> finalFavEvents = favEvents;
//        dbRef.addValueEventListener(new ValueEventListener() {
//
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                Log.d("flashchat","Searching for EVENTS");
//                Log.d("flashchatfav",finalFavEvents.toString());
//                favList = new ArrayList<>();
//                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
//                    Log.d("flashchatfav", postSnapshot.getKey());
//                    for(String temp : finalFavEvents){
//                        if(temp.equals(postSnapshot.getKey())){
//                            Log.d("flashchatfav","MATCHED" + temp);
//                            EventsInfo Etemp = dataSnapshot.child(postSnapshot.getKey()).getValue(EventsInfo.class);
//                            favList.add(Etemp);
//                        }
//                    }
//                    Log.d("flashchatfav", "size of favList : " + favList);
////                    Log.d("flashchat","with IMAGE url  : " + temp.getImage());
//
//                }
//                mIfFirebaseLoad.onFirebaseLoadSuccess(favList);
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//                Log.d("flashchat","Something went WRONG");
//            }
//        });

    @Override
    public void onResume() {
        super.onResume();
        Log.d("flashchatad","On RESUME");
        getFavEvents();
    }
    @Override
    public void onFirebaseLoadSuccess(List<Register> list) {
        Log.d("flashchatfav","SENDING FAvLIST " + Integer.toString(favList.size()));
        //mListView = rootView.findViewById(R.id.favlist);
        if(list.size()>0){
            textView.setVisibility(View.INVISIBLE);
        } else {
            Set<String> favEvents = new ArraySet<>();
            prefs.edit().putStringSet(FAVEVENTS_LIST,favEvents).apply();
            textView.setVisibility(View.VISIBLE);
            mListView.setVisibility(View.INVISIBLE);
        }
//        Log.d("flashchat","ROOTVIEW : " + mListView.toString());
        mListView.setAdapter(new FavEventsAdapter(list,mContext));
    }

    @Override
    public void onFirebaseLoadFail(String message) {

    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}