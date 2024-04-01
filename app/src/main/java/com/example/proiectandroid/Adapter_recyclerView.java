package com.example.proiectandroid;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Adapter_recyclerView extends RecyclerView.Adapter<Adapter_recyclerView.MyViewHolder>{
    Context context;
    ArrayList<Note> notes;

    public Adapter_recyclerView(Context context, ArrayList<Note> notes) {
        this.context = context;
        this.notes = notes;
    }


    @NonNull
    @Override
    public Adapter_recyclerView.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.note_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_recyclerView.MyViewHolder holder, int position) {
        if (notes != null && !notes.isEmpty() && position < notes.size()) {
            holder.titleTextView.setText(notes.get(position).getTitle());
            Log.d("DIsplaty", notes.get(position).getTitle());
            holder.contentTextView.setText(notes.get(position).getContent());
        } else {
            // Handle the case where notes ArrayList is empty or position is invalid
            holder.titleTextView.setText("No Title");
            holder.contentTextView.setText("No Content");
        }
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView, contentTextView;

        public MyViewHolder(@NonNull View itemView) {//constructorul
            super(itemView);
            //se seteaza referintele la UI pt a putea fi folosite ulterior
            titleTextView = itemView.findViewById(R.id.titleTextView);
            contentTextView = itemView.findViewById(R.id.contentTextView);
        }
    }
}
