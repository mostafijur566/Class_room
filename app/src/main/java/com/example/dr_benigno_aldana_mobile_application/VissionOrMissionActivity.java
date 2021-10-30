package com.example.dr_benigno_aldana_mobile_application;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class VissionOrMissionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vission_or_mission);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setTitle("Vision/Mission");
    }
}