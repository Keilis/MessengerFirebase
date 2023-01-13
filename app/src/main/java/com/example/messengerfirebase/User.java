package com.example.messengerfirebase;

public class User {
    private String id;
    private String name;
    private String lastName;
    private String age;
    private boolean online;

    public User(String id, String name, String lastName, String age, boolean online) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.age = age;
        this.online = online;
    }

    public User() {
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastName;
    }

    public String getAge() {
        return age;
    }

    public boolean isOnline() {
        return online;
    }
}
