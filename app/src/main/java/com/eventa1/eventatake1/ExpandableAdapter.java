package com.eventa1.eventatake1;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.paytm.pgsdk.PaytmPGService;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class ExpandableAdapter extends BaseExpandableListAdapter {
    private Context context;
    private List<String> expandableListTitle;
    private HashMap<String, Compete> expandableListDetail;
    private String status;
    private String eveName,endDate;
    Button bookie;

    public ExpandableAdapter(Context context, List<String> expandableListTitle, HashMap<String, Compete> expandableListDetail,String eveName,String status,String endDate) {
        this.context = context;
        this.expandableListTitle = expandableListTitle;
        this.expandableListDetail = expandableListDetail;
        this.eveName = eveName;
        this.status = status;
        this.endDate = endDate;
    }

    @Override
    public int getGroupCount() {
        return this.expandableListTitle.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.expandableListTitle.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return this.expandableListDetail.get(this.expandableListTitle.get(groupPosition));
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String listTitle = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.expandable_event_head, null);
        }
        TextView textView = convertView.findViewById(R.id.comp_head);
        textView.setTypeface(null, Typeface.BOLD);
        textView.setText(listTitle);
        //RadioButton mradio = convertView.findViewById(R.id.isSelected);

        return convertView;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        //final String expandedListText = (String) getChild(groupPosition, childPosition);
        final Compete temp = (Compete) getChild(groupPosition,childPosition);
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.expandable_listview_event, null);
        }
        TextView textCat = convertView.findViewById(R.id.comp_cat);
        Log.d("flashchat","Category as " + temp.getText());
        textCat.setText("Category : " + temp.getText());
        TextView textdesc = convertView.findViewById(R.id.comp_desc);
        textdesc.setText("Description:\n" + temp.getDes2());
        TextView textPrice = convertView.findViewById(R.id.comp_price);

        textPrice.setText("Price : " + Integer.toString(temp.getPric()));
        bookie=convertView.findViewById(R.id.bookie);
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        Date date1 = null;
        try {
            date1=new SimpleDateFormat("dd/MM/yyyy").parse(endDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        // System.out.println(df.format(date));
        Log.d("flashchatad","TODAY's DATE : " + df.format(date));
        if(date.compareTo(date1)>0){
            Log.d("flashchatad","Date AHEAD");
            bookie.setText("EVENT IS OVER" );
        }
        else if(status.equals("YES")) {
            bookie.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                    dialog.setMessage("Please Select any option");
                    dialog.setTitle("DO YOU WANT TO BOOK EVENT");
                    dialog.setPositiveButton("YES",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int which) {
                                    Toast.makeText(context.getApplicationContext(), "Yes is clicked", Toast.LENGTH_LONG).show();
                                  Intent b = new Intent(context, Receipt.class);
                                    b.putExtra("CompDetails", (Serializable) expandableListDetail.get(expandableListTitle.get(groupPosition)));
                                    b.putExtra("EventName", eveName);
                                    Log.d("flashchat", "Opening Receipt");

                                    context.startActivity(b);

                                }
                            });
                    dialog.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(context.getApplicationContext(), "cancel is clicked", Toast.LENGTH_LONG).show();
                        }
                    });
                    AlertDialog alertDialog = dialog.create();
                    alertDialog.show();
                }
            });
        }else {
            bookie.setText("CAN'T BOOK EVENTS FROM HERE" );
        }
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
