package com.example.dr_benigno_aldana_mobile_application;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

import java.util.List;

public class CardAdapter extends ArrayAdapter<CreateClass> {

    private Activity context;
    private List<CreateClass> createClassList;

    public CardAdapter( Activity context, List<CreateClass> createClassList) {
        super(context, R.layout.class_card_view, createClassList);
        this.context = context;
        this.createClassList = createClassList;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater layoutInflater = context.getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.class_card_view, null, true);

        CreateClass createClass = createClassList.get(position);

        LinearLayout cardView = view.findViewById(R.id.class_card);
        TextView t1 = view.findViewById(R.id.card_class_title);
        TextView t2 = view.findViewById(R.id.card_user_name);

        t1.setText(createClass.getClass_name());
        t2.setText(createClass.getUser_name());

        return view;
    }
}
