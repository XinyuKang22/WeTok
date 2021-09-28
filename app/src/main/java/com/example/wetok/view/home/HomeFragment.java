package com.example.wetok.view.home;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.wetok.R;
import com.example.wetok.bean.Post;
import com.example.wetok.bean.User;
import com.example.wetok.view.MainActivity;

import java.util.ArrayList;
import java.util.Random;

public class HomeFragment extends Fragment {


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view = getLayoutInflater().inflate(R.layout.fragment_home, container, false);
//        paddingPicture(view.findViewById(R.id.edt_search), R.drawable.ic_find);
        ListView lv = view.findViewById(R.id.post_list);
        ArrayList<Post> posts = new ArrayList<>();
        Post post = new Post();
        post.user.setName("Jack");
        post.setContent( "Hello\nWorld");
        posts.add(post);
        posts.add(post);
        posts.add(post);
        posts.add(post);
        posts.add(post);


        PostAdapter adapter = new PostAdapter(getContext(), R.layout.post_list_view, posts);
        lv.setAdapter(adapter);
        lv.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int state) {
                if (state == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                    if (absListView.getLastVisiblePosition() == (absListView.getCount()) - 1) {
                        Post post = new Post();
                        post.user.setName("Jack");
                        post.setContent( "Hello\nWorld");
                        posts.add(post);
                        adapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onScroll(AbsListView absListView, int i, int i1, int i2) {

            }
        });
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //getActivity().setTitle("Wetok");
    }

    private void paddingPicture(EditText tv, int pic) {
        Drawable drawable1 = getResources().getDrawable(pic);
        drawable1.setBounds(20, 0, 50, 50);//第一0是距左边距离，第二0是距上边距离，40分别是长宽
        tv.setCompoundDrawables(drawable1, null, null, null);//只放左边
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }


}