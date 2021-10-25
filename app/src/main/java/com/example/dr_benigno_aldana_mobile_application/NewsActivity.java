package com.example.dr_benigno_aldana_mobile_application;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class NewsActivity extends AppCompatActivity {

    private TextView txt_heading, txt_news, txt_heading2, txt_news2;

    DatabaseReference databaseReference;

    LoadingDialog loadingDialog = new LoadingDialog(NewsActivity.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setTitle("News");

        txt_heading = (TextView) findViewById(R.id.txt_heading);
        txt_news = (TextView) findViewById(R.id.txt_news);

        txt_heading2 = (TextView) findViewById(R.id.txt_heading2);
        txt_news2 = (TextView) findViewById(R.id.txt_news2);

        databaseReference = FirebaseDatabase.getInstance().getReference("News");

        loadingDialog.startLoadingDialog();

    }

    @Override
    protected void onStart() {

        //....................................news1.........................................
        databaseReference.child("news1").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                loadingDialog.dismissDialog();
                News news = snapshot.getValue(News.class);

                txt_heading.setText(news.getTitle());
                txt_news.setText(news.getNews());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        //....................................news1.........................................


        //....................................news2.........................................
        databaseReference.child("news2").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                loadingDialog.dismissDialog();
                News news = snapshot.getValue(News.class);

                txt_heading2.setText(news.getTitle());
                txt_news2.setText(news.getNews());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        //....................................news2.........................................

        super.onStart();
    }
}