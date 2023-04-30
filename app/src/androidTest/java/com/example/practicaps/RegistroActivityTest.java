package com.example.practicaps;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.rule.ActivityTestRule;

import com.example.practicaps.utils.User;
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


public class RegistroActivityTest {

    @Rule
    public ActivityTestRule<SignUpActivity> activityRule = new ActivityTestRule<>(SignUpActivity.class);

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(MainActivity.class, false, false);


    @Before
    public void setUp() {
        mAuth = FirebaseAuth.getInstance();
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://practicaps-d596b-default-rtdb.europe-west1.firebasedatabase.app/");

    }

    @After
    public void tearDown() {
        mAuth.signOut();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            mDatabase.child(mAuth.getCurrentUser().getUid()).removeValue();
        }
    }

    @Test
    public void testRegistroUsuario() {
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
        mDatabase.child(mAuth.getCurrentUser().getUid()).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                User user = task.getResult().getValue(User.class);
                assertNotNull(user);
                assertEquals(email, user.getEmail());
                assertEquals(name, user.getName());
                assertEquals(surname, user.getSurname());
            }
        });
    }
}
