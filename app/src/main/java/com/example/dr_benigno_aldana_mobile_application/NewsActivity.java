package com.example.dr_benigno_aldana_mobile_application;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.hardware.lights.LightsManager;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class NewsActivity extends AppCompatActivity {

    private ListView txt_message_list;
    private EditText txt_message;

    private ImageView btn_send;

    private List<News> newsList;
    private NewsAdapter newsAdapter;

    private AlertDialog.Builder alert_dialog_builder;

    FirebaseUser user_uid;

    String uid;

    CreateClass createClass;

    DatabaseReference databaseReference, get_name;

    boolean flag = false;

    LoadingDialog loadingDialog = new LoadingDialog(NewsActivity.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setTitle("News");

        createClass = getIntent().getParcelableExtra("create_class");

        databaseReference = FirebaseDatabase.getInstance().getReference();
        get_name = FirebaseDatabase.getInstance().getReference();

        user_uid = FirebaseAuth.getInstance().getCurrentUser();
        uid = user_uid.getUid();

        newsList = new ArrayList<>();

        newsAdapter = new NewsAdapter(NewsActivity.this, newsList);

        txt_message_list = (ListView) findViewById(R.id.news_list_view);
        txt_message = (EditText) findViewById(R.id.txt_news);

        btn_send = (ImageView) findViewById(R.id.btn_news);

        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = txt_message.getText().toString().trim();

                if(message.isEmpty())
                {
                    txt_message.setError("Please enter text message");
                    txt_message.requestFocus();
                    return;
                }

                String key = databaseReference.push().getKey();

                get_name.child("Teachers").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot dataSnapshot : snapshot.getChildren())
                        {
                            if(dataSnapshot.getKey().equals(uid))
                            {
                                User user = dataSnapshot.getValue(User.class);
                                News news = new News(user.getName(), message, key, uid);

                                databaseReference.child("News").child("messages").child(key).setValue(news);
                                txt_message.setText("");

                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

        loadingDialog.startLoadingDialog();
    }

    @Override
    protected void onStart() {

        databaseReference.child("News").child("messages").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                loadingDialog.dismissDialog();

                newsList.clear();

                for(DataSnapshot dataSnapshot : snapshot.getChildren())
                {
                    News news = dataSnapshot.getValue(News.class);
                    newsList.add(news);
                }

                txt_message_list.setAdapter(newsAdapter);

                get_name.child("Students").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot dataSnapshot : snapshot.getChildren())
                        {
                            if(dataSnapshot.getKey().equals(uid))
                            {
                                flag = true;
                            }
                        }


                        if(flag == false)
                        {
                            txt_message_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    News news = newsList.get(position);

                                    try {
                                        if(news.getUid().equals(uid))
                                        {
                                            alert_dialog_builder = new AlertDialog.Builder(NewsActivity.this);
                                            alert_dialog_builder.setIcon(R.drawable.ic_baseline_info_24);
                                            alert_dialog_builder.setTitle("Edit or Delete");
                                            alert_dialog_builder.setNegativeButton("Edit", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    EditText message = new EditText(view.getContext());
                                                    message.setText(news.getMessage());
                                                    androidx.appcompat.app.AlertDialog.Builder alertDialogBuilder = new androidx.appcompat.app.AlertDialog.Builder(NewsActivity.this);
                                                    alertDialogBuilder.setTitle("Edit");
                                                    alertDialogBuilder.setView(message);
                                                    alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                                        @Override
                                                        public void onClick(DialogInterface dialog, int which) {

                                                            News news1 = new News(news.getUser(), message.getText().toString(), news.getKey(), uid);

                                                            databaseReference.child("News").child("messages").child(news.getKey()).setValue(news1);
                                                        }
                                                    });

                                                    alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                                        @Override
                                                        public void onClick(DialogInterface dialog, int which) {

                                                        }
                                                    });

                                                    androidx.appcompat.app.AlertDialog alertDialog = alertDialogBuilder.create();
                                                    alertDialog.show();
                                                }
                                            });

                                            alert_dialog_builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    databaseReference.child("News").child("messages").child(news.getKey()).removeValue();
                                                }
                                            });

                                            alert_dialog_builder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {

                                                }
                                            });

                                            AlertDialog alertDialog = alert_dialog_builder.create();
                                            alertDialog.show();
                                        }
                                    } catch (Exception e) {
                                        Toast.makeText(getApplicationContext(), "You can't delete or edit other's content", Toast.LENGTH_LONG).show();
                                    }

                                }
                            });
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        get_name.child("Students").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren())
                {
                    if(dataSnapshot.getKey().equals(uid))
                    {
                        txt_message.setVisibility(View.GONE);
                        btn_send.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        super.onStart();
    }
}