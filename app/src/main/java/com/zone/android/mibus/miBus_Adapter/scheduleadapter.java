package com.zone.android.mibus.miBus_Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zone.android.mibus.R;

import java.util.ArrayList;
import java.util.List;

public class scheduleadapter extends BaseAdapter {
    public static  List<Integer> selectedPositions = new ArrayList<>();
    private Context mContext;
    List<String> RollNumber;

    public scheduleadapter(Context c, List<String> RollNumber ) {
        mContext = c;
        this.RollNumber = RollNumber;

    }


    @Override
    public int getCount() {
        return RollNumber.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
       /* View grid;
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {

            grid = new View(mContext);
            grid = inflater.inflate(R.layout.grid_schedule_row, null);

            TextView textView = (TextView) grid.findViewById(R.id.grid_text);
            textView.setText(""+RollNumber[position]);
        } else {
            grid = (View) convertView;
        }

        return grid;*/


        CustomView customView = (convertView == null) ?
                new CustomView(mContext) : (CustomView) convertView;
        customView.display(""+RollNumber.get(position), selectedPositions.contains(position));
        return customView;
    }
    }

