package com.example.practicaps;

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

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText nameInput, passwordInput;
    private Button loginButton, signUpButton;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
        findInputsAndButtons();
        addListenersToButtons();
    }

    private void addListenersToButtons() {
        loginButton.setOnClickListener(this);
        signUpButton.setOnClickListener(this);
    }


    private void findInputsAndButtons() {
        nameInput = findViewById(R.id.edit_usuario_log);
        passwordInput = findViewById(R.id.edit_pass_log);
        loginButton = findViewById(R.id.button_log);
        signUpButton = findViewById(R.id.button_registro);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override

    public void onClick(View view) {
        switch (view.getId()){
        
            case R.id.button_log:

                mAuth.signInWithEmailAndPassword(nameInput.getText().toString(), passwordInput.getText().toString())
                        .addOnCompleteListener(this, task -> {

                            if (task.isSuccessful() && !nameInput.getText().toString().isEmpty()
                                    && !passwordInput.getText().toString().isEmpty()) {

                                Log.d("login", "signInWithEmail:success");

                                startActivity(new Intent(MainActivity.this, MenuActivity.class));
                                finish();

                            } else {
                                Log.w("login", "signInWithEmail:failure", task.getException());
                                Toast.makeText(getApplicationContext(), "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        });
                break;

            case R.id.button_registro:
                Intent i = new Intent(getApplicationContext(), SignUpActivity.class);

                startActivity(i);
                break;
        }
    }
}
