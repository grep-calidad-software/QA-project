package com.example.practicaps;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class DeleteAccountActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText mConfirmPasswordEditText;
    private Button mDeleteButton;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_deletion);

        mAuth = FirebaseAuth.getInstance();

        mConfirmPasswordEditText = findViewById(R.id.psw_delete_confirm);
        mDeleteButton = findViewById(R.id.delete_button);

        mDeleteButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.delete_button:
                String confirmPassword = mConfirmPasswordEditText.getText().toString().trim();
                if (!TextUtils.isEmpty(confirmPassword)) {
                    deleteAccount(confirmPassword);
                } else {
                    Toast.makeText(this, "Por favor introduzca su contraseña para borrar su cuenta", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private void deleteAccount(String confirmPassword) {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            AuthCredential credential = EmailAuthProvider.getCredential(currentUser.getEmail(), confirmPassword);
            currentUser.reauthenticate(credential).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    currentUser.delete().addOnCompleteListener(task1 -> {
                        if (task1.isSuccessful()) {
                            Toast.makeText(DeleteAccountActivity.this, "Se borró tu cuenta", Toast.LENGTH_SHORT).show();
                            mAuth.signOut();
                            Intent mainActivityIntent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(mainActivityIntent);
                            finish();
                        } else {
                            Toast.makeText(DeleteAccountActivity.this, "No se pudo borrar tu cuenta", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    Toast.makeText(DeleteAccountActivity.this, "Contraseña incorrecta", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
