package com.example.practicaps.adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.practicaps.R;

import com.example.practicaps.holders.EventHolder;
import com.example.practicaps.utils.Information;

import java.util.ArrayList;
import java.util.List;

public class AdaptadorEventos extends RecyclerView.Adapter<EventHolder> {
    public List<Information> listMensaje = new ArrayList();

    private Context c;

    public AdaptadorEventos(Context c) {
        this.c = c;
    }

    public void addEvento(Information i){

        listMensaje.add(i);
        //notifyItemInserted(listMensaje.size());
    }

    @NonNull
    @Override

    public EventHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(c).inflate(R.layout.card_view_eventos,parent,false);
        return new EventHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventHolder holder, int position) {
        holder.getName().setText(listMensaje.get(position).getInfo());
        holder.getDate().setText(listMensaje.get(position).getDate());

        int hora = listMensaje.get(position).getHour();
        int min = listMensaje.get(position).getMinute();

        StringBuilder sb = new StringBuilder();
        sb.append(hora);
        sb.append(":");
        sb.append(min);
        holder.getHour().setText(sb);
    }

    @Override
    public int getItemCount() {
        return listMensaje.size();
    }
}
