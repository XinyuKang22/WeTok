package com.example.wetok.bean;

import com.example.wetok.R;

import java.io.Serializable;

public class User implements Serializable {
    public int photo = R.drawable.photo;
    public String name;
    public String id = "123456";

    public User() {
    }

    public User(String name, String id) {
        this.name = name;
        this.id = id;
    }
}
