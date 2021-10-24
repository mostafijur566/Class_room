package com.example.dr_benigno_aldana_mobile_application;

public class User {
    String name;
    String user_type;
    String phone_number;

    public User(String name, String user_type, String phone_number) {
        this.name = name;
        this.user_type = user_type;
        this.phone_number = phone_number;
    }

    public User() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUser_type() {
        return user_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }
}
