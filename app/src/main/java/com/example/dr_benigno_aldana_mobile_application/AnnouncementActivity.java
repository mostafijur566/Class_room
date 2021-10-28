package com.example.dr_benigno_aldana_mobile_application;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AnnouncementActivity extends AppCompatActivity {

    private ListView txt_message_list;
    private EditText txt_message;

    private ImageView btn_send;

    private List<SendMessage> sendMessageList;
    private MessageAdapter messageAdapter;

    LoadingDialog loadingDialog = new LoadingDialog(AnnouncementActivity.this);

    FirebaseUser user_uid;

    String uid;

    CreateClass createClass;

    DatabaseReference databaseReference, get_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_announcement);

        getSupportActionBar().setTitle("Announcement");

        createClass = getIntent().getParcelableExtra("create_class");

        databaseReference = FirebaseDatabase.getInstance().getReference();
        get_name = FirebaseDatabase.getInstance().getReference();

        user_uid = FirebaseAuth.getInstance().getCurrentUser();
        uid = user_uid.getUid();

        sendMessageList = new ArrayList<>();

        messageAdapter = new MessageAdapter(AnnouncementActivity.this, sendMessageList);

        txt_message_list = (ListView) findViewById(R.id.announce_list_view);
        txt_message = (EditText) findViewById(R.id.txt_announce);

        btn_send = (ImageView) findViewById(R.id.btn_announce);

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
                                SendMessage sendMessage = new SendMessage(user.getName(), message);

                                databaseReference.child("Announcements").child("messages").child(key).setValue(sendMessage);
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

        databaseReference.child("Announcements").child("messages").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                loadingDialog.dismissDialog();

                sendMessageList.clear();

                for(DataSnapshot dataSnapshot : snapshot.getChildren())
                {
                    SendMessage sendMessage = dataSnapshot.getValue(SendMessage.class);
                    sendMessageList.add(sendMessage);
                }

                txt_message_list.setAdapter(messageAdapter);
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