package com.example.dr_benigno_aldana_mobile_application;

public class CreateClass {
    String class_name;
    String section;
    String room;
    String subject;
    String class_code;
    String user_name;

    public CreateClass(String class_name, String section, String room, String subject, String class_code, String user_name) {
        this.class_name = class_name;
        this.section = section;
        this.room = room;
        this.subject = subject;
        this.class_code = class_code;
        this.user_name = user_name;
    }

    public CreateClass() {

    }

    public String getClass_name() {
        return class_name;
    }

    public void setClass_name(String class_name) {
        this.class_name = class_name;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getClass_code() {
        return class_code;
    }

    public void setClass_code(String class_code) {
        this.class_code = class_code;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }
}
