package com.example.dr_benigno_aldana_mobile_application;

public class News {
    String user;
    String message;
    String key;
    String uid;

    public News(String user, String message, String key, String uid) {
        this.user = user;
        this.message = message;
        this.key = key;
        this.uid = uid;
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

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
