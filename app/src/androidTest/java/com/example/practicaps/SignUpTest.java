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

import android.support.annotation.NonNull;
import android.util.Log;


public class SignUpTest {

    @Rule
    public ActivityTestRule<SignUpActivity> activityRule = new ActivityTestRule<>(SignUpActivity.class);

    private FirebaseAuth mAuth;
    private FirebaseDatabase mDatabase;

    private DatabaseReference dbUserRef;

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(MainActivity.class, false, false);


    @Before
    public void setUp() {
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();
        dbUserRef = mDatabase.getReference("users");
    }

    @After
    public void tearDown() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        assert user != null;
        user.delete();
    }

    @Test
    public void testSignUp() {
        String email = "test@gmail.com";
        String password = "123456";
        String name = "Test";
        String surname = "User";

        // Fill in the EditText fields with the test data
        Espresso.onView(ViewMatchers.withId(R.id.edit_email_sig)).perform(ViewActions.typeText(email));
        Espresso.onView(ViewMatchers.withId(R.id.edit_pass_sig)).perform(ViewActions.typeText(password));
        Espresso.onView(ViewMatchers.withId(R.id.edit_confirm_pass_sig)).perform(ViewActions.typeText(password));
        Espresso.onView(ViewMatchers.withId(R.id.edit_name_sig)).perform(ViewActions.typeText(name));
        Espresso.onView(ViewMatchers.withId(R.id.edit_lastnames_sig)).perform(ViewActions.typeText(surname));

        closeSoftKeyboard();
        // Click on the Sign Up button
        Espresso.onView(ViewMatchers.withId(R.id.button_sign)).perform(ViewActions.click());

        // Wait for the authentication to complete
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Check if the user is successfully authenticated
        assertNotNull(mAuth.getCurrentUser());

        // Check if the user data is successfully saved in the Firebase database
        assertNotNull(dbUserRef.child(mAuth.getCurrentUser().getUid()));
    }
}
