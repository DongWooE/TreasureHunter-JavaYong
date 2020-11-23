package com.example.javayongtest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


//Recycler View Adapter

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.CustomViewHolder> {

    private ArrayList<Members> arrayList;
    private Context context;


    public CustomAdapter(ArrayList<Members> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_members, parent, false);
        CustomViewHolder holder = new CustomViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        String on = "준비완료";
        String off = "준비미완료";

        holder.userNickName.setText(arrayList.get(position).getUserNickName());
        if(!arrayList.get(position).isReadyCheck()){  // readyCheck가 false이면
            holder.readyCheck.setText(off);
        }
        else{
            holder.readyCheck.setText(on);
        }
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {

        TextView userNickName;
        TextView readyCheck;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            this.userNickName = itemView.findViewById(R.id.userNickName);
            this.readyCheck = itemView.findViewById(R.id.readyCheck);
        }
    }
}
