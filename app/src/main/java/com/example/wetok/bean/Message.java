package com.example.wetok.bean;

public class Message {
    private int m_id;
    private int c_id;
    private String content;

    public Message(int m_id, int c_id, String content) {
        this.m_id = m_id;
        this.c_id = c_id;
        this.content = content;
    }

    public int getM_id() {
        return m_id;
    }

    public void setM_id(int m_id) {
        this.m_id = m_id;
    }

    public int getC_id() {
        return c_id;
    }

    public void setC_id(int c_id) {
        this.c_id = c_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
