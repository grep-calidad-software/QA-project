package com.example.practicaps;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.practicaps.utils.User;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {
    
    private EditText nameInput, surnameInput, emailInput, passwordInput, confirmPasswordInput;
    private Button signUpButton, loginButton;
    
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        findLayoutElements();
        addListenersToButtons();
    }

    private void addListenersToButtons() {
        signUpButton.setOnClickListener(this);
        loginButton.setOnClickListener(this);
    }

    private void findLayoutElements() {
        mAuth = FirebaseAuth.getInstance();
        nameInput = findViewById(R.id.edit_name_sig);
        surnameInput = findViewById(R.id.edit_lastnames_sig);
        emailInput = findViewById(R.id.edit_email_sig);
        passwordInput = findViewById(R.id.edit_pass_sig);
        confirmPasswordInput = findViewById(R.id.edit_confirm_pass_sig);
        signUpButton = findViewById(R.id.button_sign);
        loginButton = findViewById(R.id.button_log_sign);
    }
    
    private void emptyInputBoxes(){
        nameInput.setText("");
        surnameInput.setText("");
        emailInput.setText("");
        passwordInput.setText("");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_sign:
                handleSignUp();
                finish();
                break;
            case R.id.button_log_sign:
                finish();
                break;
        }
    }

    private void handleSignUp(){
        if (passwordsMatch()) {
            final String INPUT_EMAIL = emailInput.getText().toString();
            final String INPUT_PASSWORD = passwordInput.getText().toString();

            Task<AuthResult> registerTask = mAuth.createUserWithEmailAndPassword(INPUT_EMAIL, INPUT_PASSWORD);

            addOnCompleteListenerToRegTask(registerTask);
        } else {
            Toast.makeText(getApplicationContext(), "Passwords don't match", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean passwordsMatch(){
        return passwordInput.getText().toString().equals(confirmPasswordInput.getText().toString());
    }

    public void addOnCompleteListenerToRegTask(Task<AuthResult> registerTask){
        registerTask.addOnCompleteListener(this, task -> {

            if (isSignUpSuccessful(task)) {

                Log.d("login", "createUserWithEmail:success");

                FirebaseUser currentUser = mAuth.getCurrentUser();

                assert currentUser != null;
                registerUserInDb(currentUser.getUid());

                displaySignUpSuccess();

                emptyInputBoxes();

            } else {
                Log.d("login", "createUserWithEmail:failure", task.getException());
                displaySignUpFailure();
            }
        });
    }

    private boolean isSignUpSuccessful(@NonNull Task<AuthResult> task){
        return  !emailInput.getText().toString().isEmpty()
                && !passwordInput.getText().toString().isEmpty()
                && task.isSuccessful();
    }

    private void registerUserInDb(final String uid){
        User user = new User(uid, emailInput.getText().toString(),
                nameInput.getText().toString(), surnameInput.getText().toString());

        final String dbUrl = "https://practicaps-d596b-default-rtdb.europe-west1.firebasedatabase.app/";

        FirebaseDatabase database = FirebaseDatabase.getInstance(dbUrl);
        DatabaseReference reference = database.getReference("usuarios").child(user.getUid());
        reference.setValue(user);
    }

    private void displaySignUpSuccess(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
        builder.setTitle("Sign up dialog:");
        builder.setMessage("Account successfully created");
    }

    private void displaySignUpFailure(){
        Toast.makeText(getApplicationContext(), "Authentication failed.", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStart() {super.onStart();}
}



