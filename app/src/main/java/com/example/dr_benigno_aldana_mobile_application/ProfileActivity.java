package com.example.dr_benigno_aldana_mobile_application;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ProfileActivity extends AppCompatActivity implements ExampleDialog.ExampleDialogListener, ExampleDialog_2.ExampleDialog2Listener {

    private ListView class_card;

    private TextView txt;

    private List<CreateClass> createClassList;

    private CardAdapter cardAdapter;
    LoadingDialog loadingDialog = new LoadingDialog(ProfileActivity.this);

    DatabaseReference databaseReference, push_course_code, get_user;
    FirebaseUser user_uid;

    String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setTitle("Profile");

        user_uid = FirebaseAuth.getInstance().getCurrentUser();
        uid = user_uid.getUid();

        databaseReference = FirebaseDatabase.getInstance().getReference();
        push_course_code = FirebaseDatabase.getInstance().getReference();
        get_user = FirebaseDatabase.getInstance().getReference();

        createClassList = new ArrayList<>();

        cardAdapter = new CardAdapter(ProfileActivity.this, createClassList);


        class_card = (ListView) findViewById(R.id.class_card);

        txt = (TextView) findViewById(R.id.test);

        loadingDialog.startLoadingDialog();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.join_class, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId() == R.id.join_class)
        {
            databaseReference.child("Students").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    for(DataSnapshot dataSnapshot : snapshot.getChildren())
                    {
                        if(dataSnapshot.getKey().equals(uid))
                        {
                            openDialog();
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

            databaseReference.child("Teachers").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    for(DataSnapshot dataSnapshot : snapshot.getChildren())
                    {
                        if(dataSnapshot.getKey().equals(uid))
                        {
                            openDialog2();
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }

        return super.onOptionsItemSelected(item);
    }

    public void openDialog2()
    {
        ExampleDialog_2 exampleDialog_2 = new ExampleDialog_2();
        exampleDialog_2.show(getSupportFragmentManager(), "example dialog 2");
    }

    public void openDialog()
    {
        ExampleDialog exampleDialog = new ExampleDialog();
        exampleDialog.show(getSupportFragmentManager(), "example dialog");
    }

    //..........................................Student....................................
    @Override
    public void applyTexts(String class_code) {

        databaseReference.child("Classes").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String key = "null";

                for(DataSnapshot dataSnapshot : snapshot.getChildren())
                {
                    if(dataSnapshot.getKey().equals(class_code))
                    {
                        key = dataSnapshot.getKey();
                        break;
                    }
                }

                if(key.equals(class_code))
                {
                    CreateClass createClass = snapshot.child(class_code).getValue(CreateClass.class);
                    push_course_code.child("Students").child(uid).child("classes").child(class_code).setValue(createClass);
                    Toast.makeText(ProfileActivity.this,"Class added successfully", Toast.LENGTH_LONG).show();
                }

                else {
                    Toast.makeText(ProfileActivity.this, "Invalid class code!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    //..........................................Teacher....................................
    @Override
    public void applyTexts(String name, String section, String subject, String room, String code) {



        databaseReference.child("Classes").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String key = "null";

                for(DataSnapshot dataSnapshot : snapshot.getChildren())
                {
                    if(dataSnapshot.getKey().equals(code))
                    {
                        key = dataSnapshot.getKey();
                        break;
                    }
                }

                if(!key.equals(code)) {

                    get_user.child("Teachers").child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            User user = snapshot.getValue(User.class);

                            CreateClass createClass = new CreateClass(name, section, subject, room, code, user.getName());

                            databaseReference.child("Classes").child(code).setValue(createClass);

                            push_course_code.child("Teachers").child(uid).child("classes").child(code).setValue(createClass);
                            Toast.makeText(ProfileActivity.this,"Class created successfully", Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }

                else {
                    Toast.makeText(ProfileActivity.this, "This class code already exists", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    protected void onStart() {

        push_course_code.child("Students").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                loadingDialog.dismissDialog();
                for(DataSnapshot dataSnapshot : snapshot.getChildren())
                {
                    if(dataSnapshot.getKey().equals(uid))
                    {
                        databaseReference.child("Students").child(uid).child("classes").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {

                                loadingDialog.dismissDialog();

                                createClassList.clear();

                                for(DataSnapshot dataSnapshot : snapshot.getChildren())
                                {
                                    CreateClass createClass = dataSnapshot.getValue(CreateClass.class);
                                    createClassList.add(createClass);
                                }

                                class_card.setAdapter(cardAdapter);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        push_course_code.child("Teachers").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                loadingDialog.dismissDialog();
                for(DataSnapshot dataSnapshot : snapshot.getChildren())
                {
                    if(dataSnapshot.getKey().equals(uid))
                    {
                        databaseReference.child("Teachers").child(uid).child("classes").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {

                                loadingDialog.dismissDialog();

                                createClassList.clear();

                                for(DataSnapshot dataSnapshot : snapshot.getChildren())
                                {
                                    CreateClass createClass = dataSnapshot.getValue(CreateClass.class);
                                    createClassList.add(createClass);
                                }

                                class_card.setAdapter(cardAdapter);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
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