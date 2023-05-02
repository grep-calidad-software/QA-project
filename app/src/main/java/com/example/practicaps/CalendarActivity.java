package com.example.practicaps;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.practicaps.adaptadores.AdaptadorEventos;
import com.example.practicaps.utils.EventInfo;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class CalendarActivity extends AppCompatActivity {
    private RecyclerView recyclerEventos;
    private AdaptadorEventos adaptadorEventos;
    private LinearLayoutManager linearLayoutManager;
    private FirebaseDatabase database;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    private DatabaseReference userDbRef;

    private String date;


    public CalendarActivity() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eventos);
        date = getIntent().getExtras().get("date").toString();
        date = date.replace("/","-");
        System.out.println("Funciona: " + date);

        instancias();
        acciones();
    }

    private void acciones() {
        recyclerEventos.setLayoutManager(linearLayoutManager);
        recyclerEventos.setAdapter(adaptadorEventos);
    }

    private void instancias() {
        recyclerEventos = findViewById(R.id.rv_eventos);

        database = FirebaseDatabase.getInstance();

        userDbRef = database.getReference().child("Users").child(Objects.requireNonNull(mAuth.
                getCurrentUser()).getUid()).child("Calendar");

        adaptadorEventos = new AdaptadorEventos(this);
        linearLayoutManager = new LinearLayoutManager(this);

        adaptadorEventos.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);
                setScrollBar();
            }
        });

        userDbRef.child(date).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                System.out.println(dataSnapshot.getValue().toString());
                EventInfo inputInfo = dataSnapshot.getValue(EventInfo.class);
                assert inputInfo != null;
                inputInfo.setDate(date);
                String eventoArray = dataSnapshot.getValue().toString();
                String[] arrOfStr = eventoArray.split(",");

                String eventDescription = arrOfStr[0].substring(8);
                inputInfo.setInfo(eventDescription);
                System.out.println(inputInfo.getInfo());
                adaptadorEventos.addEvento(inputInfo);
                adaptadorEventos.notifyDataSetChanged();
                System.out.println(inputInfo);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void setScrollBar(){
        recyclerEventos.scrollToPosition(adaptadorEventos.getItemCount()-1);
    }
}

