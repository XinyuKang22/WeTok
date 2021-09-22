package com.example.wetok.resouce;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.*;

import com.example.wetok.bean.Post;
import com.example.wetok.bean.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class DataGenerator {

    public DataGenerator() throws Exception {
    }

    public static List<String> readFromFile(String path) throws Exception {
        FileReader fileReader =new FileReader(path);
        BufferedReader bufferedReader =new BufferedReader(fileReader);
        String line;
        List<String> list =new ArrayList<String>();

        while((line=bufferedReader.readLine())!=null) {
            if(line.trim().length()>2) {
                list.add(line);
            }
        }
        bufferedReader.close();
        fileReader.close();
        return list;
    }


    public static String Password(){
        final int maxNum = 36;
        int i; //generate random number
        int count = 0; //count the length of the password
        StringBuilder pwd = new StringBuilder("");
        Random r = new Random();
        char[] str = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k',
                'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x',
                'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8',
                '9' };

        while (count < 6) {
            i = Math.abs(r.nextInt(maxNum));
            pwd.append(str[i]);
            count++;
        }

        return pwd.toString();
    }

    public static Map<String,String> generateUserInfo(int size){
        Map<String,String> userinfo = new HashMap<>();
        try {
            List<String> firstName =  readFromFile("src/main/java/com/example/wetok/Resouce/SourceFile/FirstName.txt");
            List<String> lastName = readFromFile("src/main/java/com/example/wetok/Resouce/SourceFile/LastName.txt");
            List<String> gender = readFromFile("src/main/java/com/example/wetok/Resouce/SourceFile/Gender.txt");
            userinfo = generateNameSex(firstName, lastName,gender,size);
            return userinfo;
        } catch (Exception e) {
            System.out.println("can't read file!");
        }
       return userinfo;
    }


    //generate user's name and sex
    public static Map<String,String> generateNameSex(List<String> firstName, List<String> lastName, List<String> gender, int size) throws Exception {
            Map<String,String> info = new HashMap<>();
            for(int i=0;i<size;i++) {
                int findex = (int) (Math.random() * firstName.size());
                int lindex = (int) (Math.random() * lastName.size());
                String name = firstName.get(findex) + lastName.get(lindex);
                String sex = gender.get(findex);
                info.put(name, sex);
            }
            return info;
    }


    public static int generateAge(){
        return (int)(Math.random()*55)+18;
    }


    // generate posts
    public static List<Post> generatePosts(int uid,String name,String u_img,List<String> statu){
        List<Post> pos = new ArrayList<>();
            for (int i = 0; i < 3; i++) {
                //pid
                int pid = i;
                //Comments
                String comment = "";
                //likes
                int like = 0;
                //reposts
                int repost = 0;
                //content
                int index = (int)(Math.random()*statu.size());
                String content = statu.get(index);
                //img
                String imgPath = "";
                Post currentpost = new Post(pid,uid, name, u_img, comment,like,repost,content,imgPath);
                pos.add(currentpost);
            }
        return pos;
    }

    public static String generateAdress(){
        String[] location = {"Sydney","Melbourne","Brisbane",
                "Perth","Adelaide","Canberra","Broome","Hobart",
                "Darwin","Cairns"};
        Random r = new Random();
        int i = Math.abs(r.nextInt(10));
        //可以调用google map api
        return location[i];
    }

    public static String generateEmail(){
        String[] suffix = new String[]{"@qq.com", "@gmail.com", "@163.com", "@126.com",
                "@yahoo.com","sina.com","hotmail.com"};
        StringBuilder sb = new StringBuilder();
        for(int i=0;i<9;i++){
            int j = (int)(Math.random()*9)+1;
            sb.append(j);
        }
        int index = (int)(Math.random()*suffix.length);
        sb.append(suffix[index]);
        return sb.toString();
    }


    public static String generatePhone(){
        String first = "4";
        int count = 1;
        StringBuilder sb = new StringBuilder();
        sb.append(first);
        while(count<9){
            int i = (int)(Math.random()*10)+1;
            sb.append(i);
            count++;
        }
        return sb.toString();
    }

    public static void createJsonFile() throws Exception {
        File file = new File("src/main/java/com/example/wetok/Resouce/infoResource.json");
        file.delete();//Ensure the file is unique
        ArrayList<User> userlist = new ArrayList<User>();
        Map<String,String> userInfo = generateUserInfo(1100);
        List<String> statu = readFromFile("src/main/java/com/example/wetok/Resouce/SourceFile/Statu.txt");
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        //add a object to Json
        int i = 0;
        for(Map.Entry<String,String> entry : userInfo.entrySet()){
            //ID
            int id = i++;
            //name
            String name = entry.getKey();
            //sex
            String sex = entry.getValue();
            //age
            int age = generateAge();
            //password
            String psd = Password();
            //Follower
            List<User> followers = null;
            //Subscriber
            List<User> subscribers = null;
            //Address
            String address = generateAdress();
            //Email
            String email = generateEmail();
            //phone
            String phone = generatePhone();
            //image
            String imgPath = "image"+Integer.toString(i+1)+".jpg";
            //Posts
            List<Post> posts = generatePosts(id,name,imgPath,statu);
            //write into a User object
            User currentUser = new User(id, name, psd,sex,age,followers, subscribers, posts, address,email,phone,imgPath);
            userlist.add(currentUser);
        }
        try(FileWriter fileWriter = new FileWriter(file)){
            gson.toJson(userlist,fileWriter);
            System.out.println("Successfully create json file !");
        }catch(Exception e){
            System.out.println("cannot write json file!");
        }
    }



}
