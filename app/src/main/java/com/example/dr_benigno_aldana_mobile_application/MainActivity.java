package com.example.dr_benigno_aldana_mobile_application;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private CardView card_profile, card_news, card_calender, card_announce, card_objective, card_hymn, card_vision, card_about, card_contact;

    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setTitle("Dashboard");

        firebaseAuth = FirebaseAuth.getInstance();

        card_profile = (CardView) findViewById(R.id.card_profile);
        card_news = (CardView) findViewById(R.id.card_news);
        card_calender = (CardView) findViewById(R.id.card_calender);
        card_announce = (CardView) findViewById(R.id.card_announce);
        card_objective = (CardView) findViewById(R.id.card_objective);
        card_hymn = (CardView) findViewById(R.id.card_hymn);
        card_vision = (CardView) findViewById(R.id.card_vision);
        card_about = (CardView) findViewById(R.id.card_about);
        card_contact = (CardView) findViewById(R.id.card_contact);

        card_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });

        card_news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, NewsActivity.class);
                startActivity(intent);
            }
        });

        card_calender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, EventActivity.class);
                startActivity(intent);
            }
        });

        card_announce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AnnouncementActivity.class);
                startActivity(intent);
            }
        });

        card_objective.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, GoalAndObjectiveActivity.class);
                startActivity(intent);
            }
        });

        card_hymn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, HymnActivity.class);
                startActivity(intent);
            }
        });

        card_vision.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, VissionOrMissionActivity.class);
                startActivity(intent);
            }
        });

        card_about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AboutSchoolActivity.class);
                startActivity(intent);
            }
        });

        card_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ContactUsActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.log_out, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId())
        {
            case R.id.menu_logout:
                log_out();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    public void log_out()
    {
        firebaseAuth.signOut();
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}