@RunWith(AndroidJUnit4::class)
class RegistroActivityTest {

    @get:Rule
    var activityRule: ActivityTestRule<RegistroActivity> = ActivityTestRule(RegistroActivity::class.java)

    @Test
    fun registerNewUser_success() {
        // Type in the user's information
        onView(withId(R.id.edit_email_sig)).perform(typeText("testuser@test.com"))
        onView(withId(R.id.edit_pass_sig)).perform(typeText("password123"))
        onView(withId(R.id.edit_confirm_pass_sig)).perform(typeText("password123"))
        onView(withId(R.id.edit_name_sig)).perform(typeText("Test"))
        onView(withId(R.id.edit_lastnames_sig)).perform(typeText("User"))

        // Click on the register button
        onView(withId(R.id.button_sign)).perform(click())

        // Wait for the registration to complete
        Thread.sleep(5000)

        // Check if the user has been added to the Firebase database
        val database = FirebaseDatabase.getInstance("https://practicaps-d596b-default-rtdb.europe-west1.firebasedatabase.app/")
        val reference = database.getReference("usuarios")
        val query = reference.orderByChild("email").equalTo("testuser@test.com")
        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                assertTrue(snapshot.exists())
            }

            override fun onCancelled(error: DatabaseError) {
                fail("Test failed: ${error.message}")
            }
        })
    }
}
