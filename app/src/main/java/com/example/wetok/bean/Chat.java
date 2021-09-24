package com.example.wetok.bean;

public class Chat {
    private int c_id;
    private int sender_id;
    private int receiver_id;
    private String time;

    public Chat(int c_id, int sender_id, int receiver_id, String time) {
        this.c_id = c_id;
        this.sender_id = sender_id;
        this.receiver_id = receiver_id;
        this.time = time;
    }

    public int getC_id() {
        return c_id;
    }

    public void setC_id(int c_id) {
        this.c_id = c_id;
    }

    public int getSender_id() {
        return sender_id;
    }

    public void setSender_id(int sender_id) {
        this.sender_id = sender_id;
    }

    public int getReceiver_id() {
        return receiver_id;
    }

    public void setReceiver_id(int receiver_id) {
        this.receiver_id = receiver_id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
