package com.eventa1.eventatake1;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;
import static com.eventa1.eventatake1.MainActivity.CHAT_PREFS;
import static com.eventa1.eventatake1.MainActivity.USER_ID;


public class HostedEventsFrag extends Fragment implements HostFirebase{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private HostFirebase mHostFire;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private ListView mListView;
    private View rootView;
    private Context mContext;
    private TextView textView;
    private String usrID;
    public HostedEventsFrag() {
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
    public static HostedEventsFrag newInstance(String param1, String param2) {
        HostedEventsFrag fragment = new HostedEventsFrag();
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
        mHostFire= this;


        //mListView.setAdapter(new FavEventsAdapter());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_hosted_events, container, false);
        textView = rootView.findViewById(R.id.hostedtext);
        Log.d("flashchat","CURR TEXT : " + textView.getText().toString());
        //textView.setText("WELL GOT IN");
        mListView = rootView.findViewById(R.id.hostlist);
//        List<String> eveList = new ArrayList<>();
//        eveList.add("This");
//        eveList.add("iS");
//        eveList.add("Sparta");
//        ArrayAdapter<String> usrItems= new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,eveList);
        //mListView.setAdapter(usrItems);
        getHostEvents();
        Log.d("flashchat","ListView should be set");
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
    private void getHostEvents() {
        //FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
        //String userid=user.getUid();

        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("Unconfirmed");
        dbRef.addValueEventListener(new ValueEventListener() {
            List<Register> hostList= new ArrayList<>();
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d("flashchat", "Searching for EVENTS");
                if (dataSnapshot.hasChild(usrID)) {

                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        Log.d("flashchatad", "USER"+postSnapshot.getKey());

                        DataSnapshot temp = dataSnapshot.child(usrID);//getValue(Register.class);

                        for (DataSnapshot postSnapshot1 : temp.getChildren()) {
                            Register tempo = dataSnapshot.child(postSnapshot1.getKey()).getValue(Register.class);
                            hostList.add(tempo);
                            Log.d("flashchatad", "event"+postSnapshot1.getKey());
                        }

//                        Log.d("flashchat", "IN BOOKMARKS" + temp.getEve());
//                    Log.d("flashchat","with IMAGE url  : " + temp.getImage());

                    }
                    mHostFire.onFirebaseLoadSuccess(hostList);

                }
                else {
                   List<Register> HostList1 = new ArrayList<>();
                    Log.d("flashchatad","NOT IN IF SIZE OF FAVEVELIST : " + HostList1.size());
                    mHostFire.onFirebaseLoadSuccess(HostList1);
//                }
            }
            }
                @Override
                public void onCancelled (@NonNull DatabaseError databaseError){
                    Log.d("flashchat", "Something went WRONG");
                }


        });
    }


    public void onFirebaseLoadSuccess(List<Register> list) {
        Log.d("flashchatad","SENDING RegLIST " + list);

        //mListView = rootView.findViewById(R.id.favlist);
        if(list.size()>0){
            textView.setVisibility(View.INVISIBLE);
        }
        Log.d("flashchat","ROOTVIEW : " + mListView.toString());
        mListView.setAdapter(new HostEventsAdapter(list,mContext));
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
