package com.zone.android.mibus.miBus_Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zone.android.mibus.R;

import java.util.ArrayList;

public class rolesadapter extends BaseAdapter {


    private Context mContext;
    private ArrayList<String> RollsList;

    public rolesadapter(Context c, ArrayList<String> RollsList ) {
        mContext = c;
        this.RollsList = RollsList;

    }

    @Override
    public int getCount() {
        return RollsList.size();
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
        View grid;
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {

            grid = new View(mContext);
            grid = inflater.inflate(R.layout.activity_gridrow, null);

            TextView textView = (TextView) grid.findViewById(R.id.grid_text);
            textView.setText(""+RollsList.get(position));
        } else {
            grid = (View) convertView;
        }

        return grid;
    }
}
