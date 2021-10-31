package com.example.dr_benigno_aldana_mobile_application;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.List;

public class NewsAdapter extends ArrayAdapter<News> {

    private Activity context;
    private List<News> newsList;

    public NewsAdapter(Activity context, List<News> newsList) {
        super(context, R.layout.news_layout, newsList);
        this.context = context;
        this.newsList = newsList;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater layoutInflater = context.getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.news_layout, null, true);

        News news = newsList.get(position);

        TextView t1 = view.findViewById(R.id.news_txt_heading);
        TextView t2 = view.findViewById(R.id.news_txt_news);

        t1.setText(news.getUser());
        t2.setText(news.getMessage());

        return view;
    }
}
