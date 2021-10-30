package com.example.dr_benigno_aldana_mobile_application;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class GoalAndObjectiveActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goal_and_objective);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setTitle("Goal and Objective");
    }
}