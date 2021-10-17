package com.example.lib;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class ImportanceScore {
    // TODO: 连接搜索结果（posts)
    ArrayList<String> searchResults;

    // TODO: 搜索的tag放在一个list里
    // Assume all query words are in lower case
    ArrayList<String> query;

    // importance部分思路：
    // 发帖时间更近的更重要
    // 发帖者粉丝更多的更重要
    // 帖子赞数更多的更重要

    public static void main(String[] args) {

//        for (int i = 0; i < 20; i ++){
//            int hour=(int)(Math.random()*23)+1;
//            int minute= (int)(Math.random()*6);
//            int month = ThreadLocalRandom.current().nextInt(1, 13);
//            int date = ThreadLocalRandom.current().nextInt(1, 29);
//
//            System.out.println(month + "." + date + " " + hour + ":" + minute + "0");
//        }
    }
}
