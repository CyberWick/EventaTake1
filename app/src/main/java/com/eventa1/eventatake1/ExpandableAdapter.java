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

import java.util.HashMap;
import java.util.List;

public class ExpandableAdapter extends BaseExpandableListAdapter {
    private Context context;
    private List<String> expandableListTitle;
    private HashMap<String, Compete> expandableListDetail;
    Button bookie;

    public ExpandableAdapter(Context context, List<String> expandableListTitle, HashMap<String, Compete> expandableListDetail) {
        this.context = context;
        this.expandableListTitle = expandableListTitle;
        this.expandableListDetail = expandableListDetail;
    }

    public void confirm()
    {
        alertDialog();
    }
    private void alertDialog() {
        AlertDialog.Builder dialog=new AlertDialog.Builder(context);
        dialog.setMessage("Please Select any option");
        dialog.setTitle("DO YOU WANT TO BOOK EVENT");
        dialog.setPositiveButton("YES",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int which) {
                        Toast.makeText(context.getApplicationContext(),"Yes is clicked",Toast.LENGTH_LONG).show();
                        Intent b=new Intent(context, Receipt.class);
                        context.startActivity(b);
                    }
                });
        dialog.setNegativeButton("cancel",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(context.getApplicationContext(),"cancel is clicked",Toast.LENGTH_LONG).show();
            }
        });
        AlertDialog alertDialog=dialog.create();
        alertDialog.show();
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
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
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
        bookie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog();
            }
        });
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
