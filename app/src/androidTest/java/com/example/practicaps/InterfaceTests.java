package com.example.practicaps;

import static org.junit.Assert.assertNotNull;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;

import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;



import org.junit.Before;
import org.junit.Test;

public class InterfaceTests {

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;


    @Before
    public void setUp(){
        mAuth = FirebaseAuth.getInstance();
        FirebaseDatabase db = FirebaseDatabase.getInstance("https://practicaps-d596b-default-rtdb.europe-west1.firebasedatabase.app/");
        mDatabase = db.getReference("usuarios");



    }


    //Con este test vamos a probar como cambia la interfaz al iniciar sesión con una cuenta existente,
    //para ello vamos a usar el email y la contraseña de una cuenta ya existente en la base de datos
    //y comprobaremos que se cambia a la interfaz de la pantalla principal
    @Test
    public void TestInicioSesión(){
        String email = "test@gmail.com";
        String password ="123456";

        //Rellenamos los campos de email y contraseña con los datos de la cuenta existente
        Espresso.onView(ViewMatchers.withId(R.id.edit_usuario_log)).perform(ViewActions.typeText(email));
        Espresso.onView(ViewMatchers.withId(R.id.edit_pass_log)).perform(ViewActions.typeText(password));
        Espresso.onView(ViewMatchers.withId(R.id.button_log)).perform(ViewActions.click());

        //Comprobamos que se ha cambiado a la interfaz de la pantalla principal y que el usuario existe
        Espresso.onView(ViewMatchers.withId(R.id.button_log)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        assertNotNull(mAuth.getCurrentUser());
    }


}
