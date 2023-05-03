package com.example.practicaps.adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.practicaps.R;

import com.example.practicaps.holders.EventHolder;
import com.example.practicaps.utils.EventInfo;

import java.util.ArrayList;
import java.util.List;

public class AdaptadorEventos extends RecyclerView.Adapter<EventHolder> {
    private final List<EventInfo> eventInfos = new ArrayList<>();

    private final Context c;

    public AdaptadorEventos(Context c) {
        this.c = c;
    }

    public void addEvento(EventInfo i){

        eventInfos.add(i);
        //notifyItemInserted(eventInfos.size());
    }

    @NonNull
    @Override

    public EventHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(c).inflate(R.layout.card_view_eventos,parent,false);
        return new EventHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventHolder holder, int position) {
        holder.getName().setText(eventInfos.get(position).getInfo());
        holder.getDate().setText(eventInfos.get(position).getDate());

        int hora = eventInfos.get(position).getHour();
        int min = eventInfos.get(position).getMinute();

        StringBuilder sb = new StringBuilder();
        sb.append(hora);
        sb.append(":");
        sb.append(min);
        holder.getHour().setText(sb);
    }

    @Override
    public int getItemCount() {
        return eventInfos.size();
    }
}
