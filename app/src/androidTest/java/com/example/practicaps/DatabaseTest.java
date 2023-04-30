package com.example.practicaps;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.*;
import static junit.framework.TestCase.assertEquals;

@RunWith(AndroidJUnit4.class)
public class DatabaseTest {
    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(MainActivity.class);

    private DatabaseReference mDatabaseReference;

    @Before
    public void setup() {
        // Initialize FirebaseApp and DatabaseReference
        FirebaseApp.initializeApp(mActivityRule.getActivity());
        mDatabaseReference = FirebaseDatabase.getInstance().getReference("users");
    }

    @Test
    public void testDatabase() {
        // Insert a record into the Firebase database
        String key = mDatabaseReference.push().getKey();
        mDatabaseReference.child(key).child("name").setValue("John Doe");

        // Verify that the record was inserted correctly
        Task<DataSnapshot> task = mDatabaseReference.child(key).get();
        task.addOnSuccessListener(snapshot -> {
            DataSnapshot dataSnapshot = (DataSnapshot) snapshot;
            assertEquals("John Doe", dataSnapshot.child("name").getValue(String.class));
        });

        // Click the button to show the records from the database
        onView(withId(R.id.rv_eventos)).perform(click());

        // Verify that the inserted record is displayed
        onView(withId(R.id.rv_eventos)).check(matches(hasDescendant(withText("John Doe"))));
    }
}