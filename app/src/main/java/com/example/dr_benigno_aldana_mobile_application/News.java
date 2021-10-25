package com.example.dr_benigno_aldana_mobile_application;

public class News {
    String news;
    String title;

    public News(String news, String title) {
        this.news = news;
        this.title = title;
    }

    public News() {

    }

    public String getNews() {
        return news;
    }

    public void setNews(String news) {
        this.news = news;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
