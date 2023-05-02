package com.example.practicaps;
import androidx.appcompat.app.AppCompatActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.practicaps.fragments.CalendarFragment;
import com.example.practicaps.fragments.ForoFragment;

import com.example.practicaps.utils.User;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.Objects;

public class MenuActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private String userName, currentEmail;
    private TextView displayedName, displayedEmail;
    private Toolbar topToolbar;

    private final FirebaseAuth mAuth = FirebaseAuth.getInstance();

    private final DatabaseReference usersDbRef = FirebaseDatabase.getInstance().getReference("users");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_activity);

        setUpLayoutElements();
        setHeaderInfoToLoggedUser();
    }

    private void setUpLayoutElements(){
        setUpTopToolbar();
        setUpLeftDrawer();
    }

    private void setUpTopToolbar(){
        topToolbar = findViewById(R.id.toolbar);
        topToolbar.setTitle("URJC App");
        setSupportActionBar(topToolbar);
    }

    private void setUpLeftDrawer(){
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggleBar = new ActionBarDrawerToggle(this, drawer, topToolbar, R.string.open, R.string.close);
        drawer.setDrawerListener(toggleBar);
        toggleBar.syncState();
        setUpNavigationView();
    }

    private void setUpNavigationView(){
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View headView = navigationView.getHeaderView(0);
        displayedName = headView.findViewById(R.id.id_nombre_apellido_perfil);
        displayedEmail = headView.findViewById(R.id.email_Perfil);
    }

    private void loadFragment(Fragment fragment) {

        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.contenedor_fragments, fragment).commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private void setHeaderInfoToLoggedUser() {

        String userID = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();

        DatabaseReference userRef = usersDbRef.child(userID);

        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);

                assert user != null;

                userName = user.getName();
                currentEmail = user.getEmail();

                displayedName.setText(userName);
                displayedEmail.setText(currentEmail);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_cerrar_sesion, menu);
        return true;

    }

    //On tap top menu
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.cerrar_sesion) {
            returnToLoginScreen();
        }

        return super.onOptionsItemSelected(item);
    }

    //On tap left-side menu
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        switch (id){
            case R.id.nav_calendar:
                loadFragment(new CalendarFragment());
                break;
            case R.id.nav_chat:
                loadFragment(ForoFragment.newInstance(FirebaseAuth.getInstance().getCurrentUser().getUid()));
                break;
            case R.id.nav_cerrarSesion:
                returnToLoginScreen();
                break;
            case R.id.nav_delete:
                Intent deleteActivityIntent = new Intent(getApplicationContext(), DeleteAccountActivity.class);
                startActivity(deleteActivityIntent);
                break;
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return false;
    }

    private void returnToLoginScreen() {
        Intent mainActivityIntent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(mainActivityIntent);

        finish();
    }
}
