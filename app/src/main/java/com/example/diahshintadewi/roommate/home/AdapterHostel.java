package com.example.diahshintadewi.roommate.home;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.diahshintadewi.roommate.R;


import java.util.Collections;
import java.util.List;

public class AdapterHostel extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private LayoutInflater inflater;
    List<DataHostel> data= Collections.emptyList();
    DataHostel current;
    int currentPos=0;

    // create constructor to innitilize context and data sent from MainActivity
    public AdapterHostel(Context context, List<DataHostel> data){
        this.context=context;
        inflater= LayoutInflater.from(context);
        this.data=data;
    }

    // Inflate the layout when viewholder created
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=inflater.inflate(R.layout.container, parent,false);
        MyHolder holder=new MyHolder(view);
        return holder;
    }

    // Bind data
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        // Get current position of item in recyclerview to bind data and assign values from list
        MyHolder myHolder= (MyHolder) holder;
        DataHostel current=data.get(position);
        myHolder.hostelName.setText(current.hName);
        myHolder.hostelAddress.setText(current.hAddress);
        myHolder.hostelPhone.setText(current.hTelp);


    }

    // return total item from List
    @Override
    public int getItemCount() {
        return data.size();
    }


    class MyHolder extends RecyclerView.ViewHolder{

        TextView hostelName, hostelAddress, hostelPhone;

        // create constructor to get widget reference
        public MyHolder(View itemView) {
            super(itemView);
            hostelName = (TextView) itemView.findViewById(R.id.name);
            hostelAddress = (TextView) itemView.findViewById(R.id.address);
            hostelPhone = (TextView) itemView.findViewById(R.id.phone);
        }

    }

}
