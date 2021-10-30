package com.example.dr_benigno_aldana_mobile_application;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class HymnActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hymn);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setTitle("Hymn");
    }
}