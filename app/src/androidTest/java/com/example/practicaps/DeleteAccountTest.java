package com.example.practicaps;

import static androidx.test.espresso.Espresso.closeSoftKeyboard;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import android.support.annotation.NonNull;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.example.practicaps.utils.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class DeleteAccountTest {

    @Rule
    public ActivityTestRule<DeleteAccountActivity> deleteActivityRule =
            new ActivityTestRule<>(DeleteAccountActivity.class, false, true);

    private FirebaseAuth mAuth;
    private FirebaseDatabase mDatabase;
    private DatabaseReference dbUsersRef;

    @Before
    public void setUp() {
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();
        dbUsersRef = mDatabase.getReference("users");

        mAuth.createUserWithEmailAndPassword("testDelete@gmail.com", PSW);
    }

    private final String PSW = "123456";

    @Test
    public void testDelete() {

        DatabaseReference dbUserRef = dbUsersRef.child("testDelete");

        dbUserRef.setValue(new User());

        dbUserRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                assertNull(dataSnapshot.getValue());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                fail();
            }
        });

        Espresso.onView(ViewMatchers.withId(R.id.psw_delete_confirm)).perform(ViewActions.typeText(PSW));
        closeSoftKeyboard();
        Espresso.onView(ViewMatchers.withId(R.id.delete_button)).perform(ViewActions.click());

        assertNull(mAuth.getCurrentUser());
    }
}
