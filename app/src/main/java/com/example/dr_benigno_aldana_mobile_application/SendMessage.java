package com.example.dr_benigno_aldana_mobile_application;

public class SendMessage {
    String user;
    String message;

    public SendMessage(String user, String message) {
        this.user = user;
        this.message = message;
    }

    public SendMessage()
    {

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
}
