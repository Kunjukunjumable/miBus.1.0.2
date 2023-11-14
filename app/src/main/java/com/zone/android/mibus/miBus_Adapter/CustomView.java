package com.zone.android.mibus.miBus_Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.zone.android.mibus.R;

public class CustomView extends FrameLayout {

    TextView textView;

    public CustomView(Context context) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.custom_view, this);
        textView = (TextView)getRootView().findViewById(R.id.textView);
    }

    public void display(String text, boolean isSelected) {
        textView.setText(text);
        display(isSelected);
    }

    public void display(boolean isSelected) {
        textView.setBackgroundColor(isSelected? Color.RED : Color.DKGRAY);
    }
}