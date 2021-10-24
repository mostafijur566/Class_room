package com.example.dr_benigno_aldana_mobile_application;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class ClassActivity extends AppCompatActivity {

    private TextView txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class);


        txt = (TextView) findViewById(R.id.class_test);

        CreateClass createClass = getIntent().getParcelableExtra("create_class");
        txt.setText(createClass.getClass_code());

        getSupportActionBar().setTitle(createClass.class_name);
    }
}