package com.example.dr_benigno_aldana_mobile_application;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ClassActivity extends AppCompatActivity {

    private TextView txt;

    private ListView txt_message_list;
    private EditText txt_message;
    private ImageView btn_send;

    private List<SendMessage> sendMessageList;
    private MessageAdapter messageAdapter;

    LoadingDialog loadingDialog = new LoadingDialog(ClassActivity.this);

    FirebaseUser user_uid;

    String uid;

    CreateClass createClass;

    DatabaseReference databaseReference, get_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class);

        createClass = getIntent().getParcelableExtra("create_class");

        txt = (TextView) findViewById(R.id.class_test) ;


        getSupportActionBar().setTitle(createClass.class_name);

        databaseReference = FirebaseDatabase.getInstance().getReference("Classes");
        get_name = FirebaseDatabase.getInstance().getReference();

        user_uid = FirebaseAuth.getInstance().getCurrentUser();
        uid = user_uid.getUid();

        sendMessageList = new ArrayList<>();

        messageAdapter = new MessageAdapter(ClassActivity.this, sendMessageList);

        txt_message_list = (ListView) findViewById(R.id.txt_message_list);

        txt_message = (EditText) findViewById(R.id.txt_message);

        //txt.setText(uid);

        btn_send = (ImageView) findViewById(R.id.btn_send);

        get_name.child("Students").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren())
                {
                    if(dataSnapshot.getKey().equals(uid))
                    {
                        txt_message.setText("hello");
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

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

                get_name.child("Students").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot dataSnapshot : snapshot.getChildren())
                        {
                            if(dataSnapshot.getKey().equals(uid))
                            {
                                User user = dataSnapshot.getValue(User.class);
                                SendMessage sendMessage = new SendMessage(user.getName(), message);

                                databaseReference.child(createClass.getClass_code()).child("messages").child(key).setValue(sendMessage);
                                txt_message.setText("");
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                get_name.child("Teachers").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot dataSnapshot : snapshot.getChildren())
                        {
                            if(dataSnapshot.getKey().equals(uid))
                            {
                                User user = dataSnapshot.getValue(User.class);
                                SendMessage sendMessage = new SendMessage(user.getName(), message);

                                databaseReference.child(createClass.getClass_code()).child("messages").child(key).setValue(sendMessage);
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

        databaseReference.child(createClass.getClass_code()).child("messages").addValueEventListener(new ValueEventListener() {
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

        super.onStart();
    }
}