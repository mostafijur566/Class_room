package com.example.dr_benigno_aldana_mobile_application;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.lang.reflect.Field;

public class LoginActivity extends AppCompatActivity {

    private TextView txt_sign_up, reset_password;

    private EditText txt_email, txt_password;

    private Button btn_login;

    LoadingDialog loadingDialog = new LoadingDialog(LoginActivity.this);

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();

        txt_sign_up = (TextView) findViewById(R.id.txtSignUp);

        txt_email = (EditText) findViewById(R.id.login_txt_email);
        txt_password = (EditText) findViewById(R.id.login_txt_password);

        btn_login = (Button) findViewById(R.id.btn_login);

        reset_password = (TextView) findViewById(R.id.reset_password);

        firebaseAuth = FirebaseAuth.getInstance();

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                authentication();
            }
        });

        txt_sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });

        reset_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText resetEmail = new EditText(v.getContext());
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(LoginActivity.this);
                alertDialogBuilder.setTitle("Reset Password");
                alertDialogBuilder.setMessage("Enter your email to received reset link");
                alertDialogBuilder.setView(resetEmail);

                alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        String mail = resetEmail.getText().toString().trim();

                        firebaseAuth.sendPasswordResetEmail(mail).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(LoginActivity.this, "Reset link sent to your email", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(LoginActivity.this, "Error!"+e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });

                alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });

    }

    public void authentication()
    {
        String email = txt_email.getText().toString().trim();
        String password = txt_password.getText().toString().trim();

        if(email.isEmpty())
        {
            txt_email.setError("This field is required");
            txt_email.requestFocus();
            return;
        }

        else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            txt_email.setError("Enter a valid email address");
            txt_email.requestFocus();
            return;
        }

        else if(password.isEmpty())
        {
            txt_password.setError("This field is required");
            txt_password.requestFocus();
            return;
        }

        else if(password.length() < 6)
        {
            txt_password.setError("Your password is less then 6 character");
            txt_password.requestFocus();
            return;
        }

        loadingDialog.startLoadingDialog();

        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                loadingDialog.dismissDialog();
                if(task.isSuccessful())
                {

                    if(firebaseAuth.getCurrentUser().isEmailVerified())
                    {
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                    }
                    else
                        Toast.makeText(LoginActivity.this,"Please verify your email address", Toast.LENGTH_LONG).show();
                }

                else{
                    Toast.makeText(LoginActivity.this,"Incorrect password", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}