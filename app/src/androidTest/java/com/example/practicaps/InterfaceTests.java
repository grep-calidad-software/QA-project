package com.example.practicaps;

import static androidx.core.content.ContextCompat.startActivity;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;

import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.test.rule.ActivityTestRule;

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


import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runners.MethodSorters;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class InterfaceTests {

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(MainActivity.class, false, false);
    @Rule
    public ActivityTestRule<MenuActivity> mMenuActivityRule = new ActivityTestRule<>(MenuActivity.class, false, false);
    @Rule
    public ActivityTestRule<SignUpActivity> mSignUpActivityRule = new ActivityTestRule<>(SignUpActivity.class, false, false);

    private FirebaseAuth mAuth;



    @Before
    public void setUp(){
        mAuth = FirebaseAuth.getInstance();
        FirebaseDatabase db = FirebaseDatabase.getInstance("https://practicaps-d596b-default-rtdb.europe-west1.firebasedatabase.app/");


    }


    //Con este test vamos a probar como cambia la interfaz al iniciar sesión con una cuenta existente,
    //para ello vamos a usar el email y la contraseña de una cuenta ya existente en la base de datos
    //y comprobaremos que se cambia a la interfaz de la pantalla principal
    @Test

    public void TestInicioSesión(){
        mActivityRule.launchActivity(new Intent());;
        String email = "testInterfaz@gmail.com";
        String password ="123456";

        //Rellenamos los campos de email y contraseña con los datos de la cuenta existente
        Espresso.onView(ViewMatchers.withId(R.id.edit_usuario_log)).perform(ViewActions.typeText(email));
        Espresso.onView(ViewMatchers.withId(R.id.edit_pass_log)).perform(ViewActions.typeText(password));
        Espresso.onView(ViewMatchers.withId(R.id.button_log)).perform(ViewActions.click());
    }

    @Test
    public void TestSignUp() throws InterruptedException {
        mActivityRule.launchActivity(new Intent());
        Espresso.onView(ViewMatchers.withId(R.id.button_registro)).perform(ViewActions.click());
        String name = "testInterfaz";
        String apellido = "testInterfaz";
        String email ="prueba@gmail.com";
        String pwd="prueba12345";
        Espresso.onView(ViewMatchers.withId(R.id.edit_name_sig)).perform(ViewActions.typeText(name));
        Espresso.onView(ViewMatchers.withId(R.id.edit_lastnames_sig)).perform(ViewActions.typeText(apellido));
        Espresso.onView(ViewMatchers.withId(R.id.edit_email_sig)).perform(ViewActions.typeText(email));
        Espresso.onView(ViewMatchers.withId(R.id.edit_pass_sig)).perform(ViewActions.typeText(pwd));
        Espresso.onView(ViewMatchers.isRoot()).perform(ViewActions.closeSoftKeyboard());
        Espresso.onView(ViewMatchers.withId(R.id.edit_confirm_pass_sig)).perform(ViewActions.typeText(pwd));
        Espresso.onView(ViewMatchers.isRoot()).perform(ViewActions.closeSoftKeyboard());
        Espresso.onView(ViewMatchers.withId(R.id.button_sign)).perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withId(R.id.edit_usuario_log)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        Espresso.onView(ViewMatchers.withId(R.id.edit_pass_log)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        Espresso.onView(ViewMatchers.withId(R.id.button_log)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));



    }


    @Test
    public void TestCerrarSesión() {
        //Comprobamos que se cierra sesión y vuelve a la pantalla de inicio de sesión
        mMenuActivityRule.launchActivity(new Intent());
        Espresso.onView(ViewMatchers.withId(R.id.cerrar_sesion)).perform(ViewActions.click());

        //Comprobar que se ha cambiado a la interfaz de la pantalla de inicio de sesión y registro
        Espresso.onView(ViewMatchers.withId(R.id.edit_usuario_log)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        Espresso.onView(ViewMatchers.withId(R.id.edit_pass_log)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        Espresso.onView(ViewMatchers.withId(R.id.button_log)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }


}
