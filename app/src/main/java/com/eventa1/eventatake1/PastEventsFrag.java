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
 * {@link PastEventsFrag.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PastEventsFrag#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PastEventsFrag extends Fragment implements BookedLoad {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private List<BookedEvents> bookList= new ArrayList<>();
    //private OnFragmentInteractionListener mListener;
    private SharedPreferences prefs;
    private ListView mListView;
    private String usrID;
    private View rootView;
    private Context mContext;
    private TextView textView;
    private BookedLoad mIfFirebaseLoad;
    private OnFragmentInteractionListener mListener;

    public PastEventsFrag() {
        // Required empty public constructor
        Log.d("flashchat" ,"IN PAST EVENTS NOW");
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PastEventsFrag.
     */
    // TODO: Rename and change types and number of parameters
    public static PastEventsFrag newInstance(String param1, String param2) {
        PastEventsFrag fragment = new PastEventsFrag();
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
//        ListView mListView = getActivity().findViewById(R.id.pastlist);
        SharedPreferences prefs = this.getActivity().getSharedPreferences(CHAT_PREFS, MODE_PRIVATE);
        usrID = prefs.getString(USER_ID,null);
        mIfFirebaseLoad = this;
        // mListView.setAdapter(new );
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_past_events, container, false);
        textView = rootView.findViewById(R.id.pastmsg);
        Log.d("flashchat","CURR TEXT : " + textView.getText().toString());
        //textView.setText("WELL GOT IN");
        mListView = rootView.findViewById(R.id.pastlist);
//        List<String> eveList = new Array
        prefs = this.getActivity().getSharedPreferences(CHAT_PREFS,MODE_PRIVATE);
        getBookedEvents();
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
    private void getBookedEvents(){
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("BookedEvents");
        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChild(usrID)){
                    final List<BookedEvents> booklist = new ArrayList<>();
                    //dataSnapshot = dataSnapshot.child(usrID);
                    DatabaseReference dbRefin = FirebaseDatabase.getInstance().getReference("BookedEvents").child(usrID);
                    dbRefin.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for(DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                                BookedEvents bookedEvents = dataSnapshot.child(postSnapshot.getKey()).getValue(BookedEvents.class);
                                booklist.add(bookedEvents);
                                Log.d("flachchatss","KEY FOUND : " + bookedEvents.getEveName());
                                Log.d("flachchatss","Image_Url : " + bookedEvents.getImage_url());
                            }
                            Log.d("flachchat","FOUND EVENTS : " + booklist);
                            mIfFirebaseLoad.onFirebaseLoadSuccess(booklist);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
//                    Log.d("flachchat","CHILDERN : " + dataSnapshot.getChildren());

                }else{
                    List<BookedEvents> emptylist = new ArrayList<>();
                    mIfFirebaseLoad.onFirebaseLoadSuccess(emptylist);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    @Override
    public void onFirebaseLoadSuccess(List<BookedEvents> list) {
        if(list.size()>0){
            textView.setVisibility(View.INVISIBLE);
        } else {
            textView.setVisibility(View.VISIBLE);
            mListView.setVisibility(View.INVISIBLE);
        }
//        Log.d("flashchat","ROOTVIEW : " + mListView.toString());
        Log.d("flachchat","SENDING LIST : " + list);
        mListView.setAdapter(new BookedEventsAdapter(list,mContext));
    }

    @Override
    public void onFirebaseLoadFail(String message) {

    }


}
