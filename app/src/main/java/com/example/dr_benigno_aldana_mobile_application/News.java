package com.example.dr_benigno_aldana_mobile_application;

public class News {
    String user;
    String message;
    String key;

    public News(String user, String message, String key) {
        this.user = user;
        this.message = message;
        this.key = key;
    }

    public News() {

    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
