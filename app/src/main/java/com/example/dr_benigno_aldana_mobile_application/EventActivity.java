package com.example.dr_benigno_aldana_mobile_application;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EventActivity extends AppCompatActivity {

    private EditText event_txt_title, event_txt_location, event_txt_desc;
    private Button event_btn_add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        getSupportActionBar().setTitle("School Calendar");

        event_txt_title = (EditText) findViewById(R.id.event_txt_title);
        event_txt_location = (EditText) findViewById(R.id.event_txt_location);
        event_txt_desc = (EditText) findViewById(R.id.event_txt_desc);

        event_btn_add = (Button) findViewById(R.id.event_btn_add);

        event_btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = event_txt_title.getText().toString().trim();
                String location = event_txt_location.getText().toString().trim();
                String desc = event_txt_desc.getText().toString().trim();

                if(title.isEmpty())
                {
                    event_txt_title.setError("Please fill the field");
                    event_txt_title.requestFocus();
                    return;
                }

                else if(location.isEmpty())
                {
                    event_txt_location.setError("Please fill the field");
                    event_txt_location.requestFocus();
                    return;
                }

                if(desc.isEmpty())
                {
                    event_txt_desc.setError("Please fill the field");
                    event_txt_desc.requestFocus();
                    return;
                }

                Intent intent = new Intent(Intent.ACTION_INSERT);
                intent.setData(CalendarContract.Events.CONTENT_URI);
                intent.putExtra(CalendarContract.Events.TITLE, title);
                intent.putExtra(CalendarContract.Events.EVENT_LOCATION, location);
                intent.putExtra(CalendarContract.Events.DESCRIPTION, desc);
                intent.putExtra(CalendarContract.Events.ALL_DAY, "true");

                if(intent.resolveActivity(getPackageManager()) != null)
                {
                    startActivity(intent);
                }

                else
                    Toast.makeText(getApplicationContext(), "There is no app that can support this action", Toast.LENGTH_LONG).show();
            }
        });
    }
}