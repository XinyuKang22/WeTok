package com.example.wetok.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.wetok.R;
import com.example.wetok.bean.Post;
import com.example.wetok.dao.CurrentUser;
import com.example.wetok.dao.PostDao;
import com.example.wetok.view.LoginActivity;
import com.example.wetok.view.UserListActivity;

import java.util.ArrayList;
import java.util.List;


public class MyFragment extends Fragment {


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = getLayoutInflater().inflate(R.layout.fragment_my, container, false);
        ListView lv = view.findViewById(R.id.profile_post_list);

        // set username and id
        TextView tv_name = view.findViewById(R.id.profile_username);
        TextView tv_id = view.findViewById(R.id.profile_userid);
        if (CurrentUser.current_user == null) {
            Button sub = view.findViewById(R.id.profile_subscriber);
            Button follower = view.findViewById(R.id.profile_follower);
            sub.invalidate();
            follower.invalidate();

            Button btnOut = view.findViewById(R.id.profile_logout);
            btnOut.setOnClickListener(e -> {
                CurrentUser.logout();
                Intent intent = new Intent(getContext(), LoginActivity.class);
                startActivity(intent);
            });

            tv_name.setText("Guest");
            tv_id.setText("no id");
        } else {

            ArrayList<Post> posts = new ArrayList<>(CurrentUser.current_user.getPosts());
            // resort post by current time
            if (! posts.isEmpty()) {
                int index = PostDao.findInsertIndex(posts);
                List<Post> reposts = posts.subList(index, posts.size());
                reposts.addAll(posts.subList(0,index));
                PostAdapter adapter = new PostAdapter(getContext(), R.layout.post_list_view, reposts);
                lv.setAdapter(adapter);
            } else {
                List<Post> empty = new ArrayList<>();
                PostAdapter adapter = new PostAdapter(getContext(), R.layout.post_list_view, empty);
                lv.setAdapter(adapter);
            }

            tv_name.setText(CurrentUser.current_user.getName());
            tv_id.setText("User Id: " + CurrentUser.current_user.getId());

            Button btnSub = view.findViewById(R.id.profile_subscriber);

            btnSub.setOnClickListener(e -> {
                Intent intent = new Intent(getContext(), UserListActivity.class);
                intent.putExtra("title", "Subscriber");
                startActivity(intent);
            });

            Button btnFol = view.findViewById(R.id.profile_follower);
            btnFol.setOnClickListener(e -> {
                Intent intent = new Intent(getContext(), UserListActivity.class);
                intent.putExtra("title", "Follower");
                startActivity(intent);
            });

            Button btnOut = view.findViewById(R.id.profile_logout);
            btnOut.setOnClickListener(e -> {
                CurrentUser.logout();
                Intent intent = new Intent(getContext(), LoginActivity.class);
                startActivity(intent);
            });
        }

        //btnOut.setVisibility(View.GONE);
        view.setPadding(40, 0, 40, 0);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}