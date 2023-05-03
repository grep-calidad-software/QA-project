package com.example.practicaps;

import static androidx.test.espresso.Espresso.closeSoftKeyboard;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.rule.ActivityTestRule;

import com.example.practicaps.utils.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import static java.lang.Thread.sleep;

import android.support.annotation.NonNull;
import android.util.Log;


public class SignUpTest {

    @Rule
    public ActivityTestRule<SignUpActivity> activityRule = new ActivityTestRule<>(SignUpActivity.class);

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(MainActivity.class, false, false);

    private FirebaseAuth mAuth;
    private FirebaseDatabase mDatabase;
    private DatabaseReference dbUserRef;

    @Before
    public void setUp() {
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();
        dbUserRef = mDatabase.getReference("users");

        mAuth.signInWithEmailAndPassword(email, password);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null)
            user.delete();
        mAuth.signOut();
    }
    @After
    public void tearDown() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        //assert user != null;
        if (user != null)
            user.delete();
    }

    String email = "test@gmail.com";
    String password = "123456";
    String name = "Test";
    String surname = "User";

    @Test
    public void testSignUp() throws InterruptedException {

        Espresso.onView(ViewMatchers.withId(R.id.edit_email_sig)).perform(ViewActions.typeText(email));
        Espresso.onView(ViewMatchers.withId(R.id.edit_pass_sig)).perform(ViewActions.typeText(password));
        closeSoftKeyboard();
        Espresso.onView(ViewMatchers.withId(R.id.edit_confirm_pass_sig)).perform(ViewActions.typeText(password));
        Espresso.onView(ViewMatchers.withId(R.id.edit_name_sig)).perform(ViewActions.typeText(name));
        Espresso.onView(ViewMatchers.withId(R.id.edit_lastnames_sig)).perform(ViewActions.typeText(surname));

        closeSoftKeyboard();
        Espresso.onView(ViewMatchers.withId(R.id.button_sign)).perform(ViewActions.click());

        sleep(5000);

        assertNotNull(mAuth.getCurrentUser());
        
        assertNotNull(dbUserRef.child(mAuth.getCurrentUser().getUid()));
    }
}
