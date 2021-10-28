package com.example.dr_benigno_aldana_mobile_application;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {

    private Spinner spinner;

    private String[] users;

    private TextView txtLogin;
    private EditText txt_name, txt_number, txt_email, txt_password;
    private Button btn_sign_up;

    LoadingDialog loadingDialog = new LoadingDialog(SignUpActivity.this);

    private FirebaseAuth mAuth;
    private FirebaseUser current_user;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        getSupportActionBar().hide();

        spinner = (Spinner) findViewById(R.id.spinner);

        txtLogin = (TextView) findViewById(R.id.txtLogin);

        txt_name = (EditText) findViewById(R.id.txt_name);
        txt_number = (EditText) findViewById(R.id.txt_number);
        txt_email = (EditText) findViewById(R.id.txt_email);
        txt_password = (EditText) findViewById(R.id.txt_password);
        btn_sign_up = (Button) findViewById(R.id.btn_sign_up);

        users = new String[]{"Student", "Teacher"};

        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_layout, R.id.txt, users);

        spinner.setAdapter(adapter);

        btn_sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user_register();
            }
        });

        txtLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    public void user_register()
    {
        String name = txt_name.getText().toString().trim();
        String  type = spinner.getSelectedItem().toString();
        String number = txt_number.getText().toString().trim();
        String email = txt_email.getText().toString().trim();
        String password = txt_password.getText().toString().trim();

        if(name.isEmpty())
        {
            txt_name.setError("This field is required");
            txt_name.requestFocus();
            return;
        }

        else if(number.isEmpty())
        {
            txt_number.setError("This field is required");
            txt_number.requestFocus();
            return;
        }

        else if(email.isEmpty())
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

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        loadingDialog.dismissDialog();
                        if (task.isSuccessful()) {

                            mAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful())
                                    {
                                        current_user = FirebaseAuth.getInstance().getCurrentUser();
                                        String uid = current_user.getUid();

                                        if(type.equals("Teacher"))
                                        {
                                            User user = new User(name, type, number);
                                            databaseReference.child("Teachers").child(uid).setValue(user);
                                        }
                                        else{
                                            User user = new User(name, type, number);
                                            databaseReference.child("Students").child(uid).setValue(user);
                                        }
                                        Toast.makeText(SignUpActivity.this, "Verification email has been sent", Toast.LENGTH_LONG).show();
                                    }

                                    else
                                        Toast.makeText(SignUpActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                }
                            });

                        }
                        else {
                            if(task.getException() instanceof FirebaseAuthUserCollisionException)
                            {
                                Toast.makeText(SignUpActivity.this, "You have already registered", Toast.LENGTH_LONG).show();
                            }
                            else {
                                Toast.makeText(SignUpActivity.this, "Error: " +task.getException().getMessage() , Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                });

    }
}