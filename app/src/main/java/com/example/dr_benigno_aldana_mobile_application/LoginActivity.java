package com.example.dr_benigno_aldana_mobile_application;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.lang.reflect.Field;

public class LoginActivity extends AppCompatActivity {

    private TextView txt_sign_up;

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
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                }

                else{
                    Toast.makeText(LoginActivity.this,"Incorrect password", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}