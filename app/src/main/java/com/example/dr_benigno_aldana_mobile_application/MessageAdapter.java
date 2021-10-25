package com.example.dr_benigno_aldana_mobile_application;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class MessageAdapter extends ArrayAdapter<SendMessage> {

    private Activity context;
    private List<SendMessage> sendMessageList;

    public MessageAdapter(Activity context, List<SendMessage> sendMessageList) {
        super(context, R.layout.send_message_layout, sendMessageList);
        this.context = context;
        this.sendMessageList = sendMessageList;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater layoutInflater = context.getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.send_message_layout, null, true);

        SendMessage sendMessage = sendMessageList.get(position);

        TextView t1 = view.findViewById(R.id.txt_user_name);
        TextView t2 = view.findViewById(R.id.message_txt);

        t1.setText(sendMessage.getUser());
        t2.setText(sendMessage.getMessage());

        return view;
    }
}
