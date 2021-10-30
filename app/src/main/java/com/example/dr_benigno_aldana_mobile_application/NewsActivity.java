package com.example.dr_benigno_aldana_mobile_application;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.hardware.lights.LightsManager;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class NewsActivity extends AppCompatActivity {

    private ListView listView_news;

    private List<News> newsList;
    private NewsAdapter newsAdapter;

    DatabaseReference databaseReference;

    LoadingDialog loadingDialog = new LoadingDialog(NewsActivity.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setTitle("News");

        newsList = new ArrayList<>();

        newsAdapter = new NewsAdapter(NewsActivity.this, newsList);

        listView_news = (ListView) findViewById(R.id.listView_news);

        databaseReference = FirebaseDatabase.getInstance().getReference("News");

        loadingDialog.startLoadingDialog();

    }

    @Override
    protected void onStart() {

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                loadingDialog.dismissDialog();

                newsList.clear();

                for(DataSnapshot dataSnapshot : snapshot.getChildren())
                {
                    News news = dataSnapshot.getValue(News.class);
                    newsList.add(news);
                }

                listView_news.setAdapter(newsAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        super.onStart();
    }
}