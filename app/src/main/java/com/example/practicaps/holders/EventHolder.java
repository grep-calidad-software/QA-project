package com.example.practicaps.holders;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.practicaps.R;

public class EventHolder extends RecyclerView.ViewHolder {

    private TextView name, hour, date;

    public EventHolder(@NonNull View itemView) {
        super(itemView);
        name = itemView.findViewById(R.id.evento);
        hour = itemView.findViewById(R.id.hora);
        date = itemView.findViewById(R.id.fecha);
    }

    public TextView getName() {
        return name;
    }

    public void setName(TextView name) {
        this.name = name;
    }

    public TextView getHour() {
        return hour;
    }

    public void setHour(TextView hour) {
        this.hour = hour;
    }

    public TextView getDate() {
        return date;
    }

    public void setDate(TextView date) {
        this.date = date;
    }
}
