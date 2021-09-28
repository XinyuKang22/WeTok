package com.example.wetok.view.my;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.wetok.R;
import com.example.wetok.bean.Post;
import com.example.wetok.bean.User;
import com.example.wetok.resources.InformationResource;
import com.example.wetok.view.LoginActivity;
import com.example.wetok.view.UserListActivity;
import com.example.wetok.view.home.PostAdapter;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class MyFragment extends Fragment {


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = getLayoutInflater().inflate(R.layout.fragment_my, container, false);
        ListView lv = view.findViewById(R.id.profile_post_list);
        List<Post> posts;
        List<Post> posts1 = null;
        //TODO read data from Firebase
        InformationResource info = new InformationResource();
        InputStream input;
        try{
            input = getResources().getAssets().open("infoResource.json");
            info.readFromJson(input);
        }catch (Exception e){
            System.out.println(e);
        }
        posts1.addAll(info.getPosts());
        posts = posts1.subList(0,5);




        PostAdapter adapter = new PostAdapter(getContext(), R.layout.post_list_view, posts);
        lv.setAdapter(adapter);

        Button btnSub = view.findViewById(R.id.profile_subscriber);

        btnSub.setOnClickListener(e -> {
            Intent intent = new Intent(getContext(), UserListActivity.class);
            startActivity(intent);
        });

        Button btnFol = view.findViewById(R.id.profile_follower);
        btnFol.setOnClickListener(e -> {
            Intent intent = new Intent(getContext(), UserListActivity.class);
            startActivity(intent);
        });

        Button btnOut = view.findViewById(R.id.profile_logout);
        btnOut.setOnClickListener(e -> {
            Intent intent = new Intent(getContext(), LoginActivity.class);
            startActivity(intent);
        });


        //btnOut.setVisibility(View.GONE);
        view.setPadding(40, 0, 40, 0);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}