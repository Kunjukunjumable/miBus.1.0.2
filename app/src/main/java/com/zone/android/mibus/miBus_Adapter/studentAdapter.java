package com.zone.android.mibus.miBus_Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zone.android.mibus.R;
import com.zone.android.mibus.miBus_Entity.Mark;

import java.util.ArrayList;
import java.util.List;

public class studentAdapter  extends RecyclerView.Adapter<studentAdapter.MyViewHolder> {

    private Context context;
    public static ArrayList<Mark> studentList;

    public studentAdapter(Context context, ArrayList<Mark> studentList) {
        this.context = context;
        this.studentList = studentList;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_studentrow, parent, false);
        context=parent.getContext();

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        Log.e("MyViewHolder ","MyViewHolder "+studentList.size());
       // holder.editText.setText(editModelArrayList.get(position).getEditTextValue());


        holder.textName.setText(studentList.get(position).getName());
        holder.edMark.setText(studentList.get(position).getMark());

        if(position==studentList.size()-1){

            holder.edMark.setImeOptions(EditorInfo.IME_ACTION_DONE );

        }else{

            holder.edMark.setImeOptions(EditorInfo.IME_ACTION_NEXT );

        }



    }

    @Override
    public int getItemCount() {
        return studentList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        public RelativeLayout view_foreground,view_background;
        public TextView textName;
        public EditText edMark;
        public final View mView;

        public MyViewHolder(View view) {
            super(view);
            mView = view;

            textName = (TextView) view.findViewById(R.id.textName);
            edMark = (EditText) view.findViewById(R.id.edMark);
            //   alertContent=(TextView)view.findViewById(R.id.alertContent);



            edMark.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    Log.e("beforeTextChanged", "beforeTextChanged "+getAdapterPosition());

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                    studentList.get(getAdapterPosition()).setMark(edMark.getText().toString());
                    Log.e("onTextChanged", "onTextChanged "+getAdapterPosition());


                }

                @Override
                public void afterTextChanged(Editable s) {

                    Log.e("afterTextChanged", "afterTextChanged "+getAdapterPosition());

                }
            });

            edMark.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {

                    if(hasFocus){
                        edMark.setText("");
                        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.showSoftInput(edMark, InputMethodManager.SHOW_IMPLICIT);
                    }else if(edMark.getText().toString().equals("")){
                        edMark.setText("100");
                    }

                }
            });



        }



    }


}
