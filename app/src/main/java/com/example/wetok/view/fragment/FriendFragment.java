package com.example.wetok.view.fragment;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.wetok.R;
import com.example.wetok.bean.Post;
import com.example.wetok.bean.User;
import com.example.wetok.dao.CurrentUser;
import com.example.wetok.dao.PostDao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class FriendFragment extends Fragment {
    int pindex = 0;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view = getLayoutInflater().inflate(R.layout.fragment_friend, container, false);

        if (CurrentUser.current_user != null) {
            List<Post> post_data = new ArrayList<>();
            for (User friend : CurrentUser.current_user.getSubscribers()) {
                post_data.addAll(friend.getPosts());
            }
            post_data.addAll(CurrentUser.current_user.getPosts());
            List<Post> posts = new ArrayList<>();

            if (!post_data.isEmpty()) {
                Collections.sort(post_data);
                if (post_data.size() < 3) {
                    PostAdapter adapter = new PostAdapter(getContext(), R.layout.post_list_view, post_data);
                    ListView lv = view.findViewById(R.id.post_list_friend);
                    lv.setAdapter(adapter);
                } else {
                    // indexing newest post according to current time
                    pindex = PostDao.findInsertIndex(post_data);

                    posts.add(post_data.get(pindex));
                    pindex++;
                    posts.add(post_data.get(pindex));
                    pindex++;
                    posts.add(post_data.get(pindex));
                    pindex++;

                    PostAdapter adapter = new PostAdapter(getContext(), R.layout.post_list_view, posts);
                    ListView lv = view.findViewById(R.id.post_list_friend);
                    lv.setAdapter(adapter);
                    lv.setOnScrollListener(new AbsListView.OnScrollListener() {
                        @Override
                        public void onScrollStateChanged(AbsListView absListView, int state) {
                            if (state == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                                if (absListView.getLastVisiblePosition() == (absListView.getCount()) - 1) {
                                    // end of the list, start again
                                    if (pindex >= post_data.size()) {
                                        pindex = 0;
                                    }
                                    Post post = post_data.get(pindex);
                                    pindex++;
                                    posts.add(post);
                                    adapter.notifyDataSetChanged();
                                }
                            }
                        }

                        @Override
                        public void onScroll(AbsListView absListView, int i, int i1, int i2) {

                        }
                    });
                }
            } else {
                List<Post> empty = new ArrayList<>();
                PostAdapter adapter = new PostAdapter(getContext(), R.layout.post_list_view, empty);
                ListView lv = view.findViewById(R.id.post_list_friend);
                lv.setAdapter(adapter);
            }

        }

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    public void paddingPicture(EditText tv, int pic) {
        @SuppressLint("UseCompatLoadingForDrawables") Drawable drawable1 = getResources().getDrawable(pic);
        drawable1.setBounds(20, 0, 50, 50);//第一0是距左边距离，第二0是距上边距离，40分别是长宽
        tv.setCompoundDrawables(drawable1, null, null, null);//只放左边
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}