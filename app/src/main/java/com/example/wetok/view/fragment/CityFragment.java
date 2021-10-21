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
import com.example.wetok.dao.UserDao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * This is the CityFragment
 * @author Yuxin Hong
 * @author Zhaoting Jiang
 */
public class CityFragment extends Fragment {
    int pindex = 0;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view = getLayoutInflater().inflate(R.layout.fragment_city, container, false);



        List<User> user_data = UserDao.filterLocation(CurrentUser.current_user.getAddress());
        List<Post> post_data = UserDao.getPosts(user_data);
        Collections.sort(post_data);

        // indexing newest post according to current time
        pindex = PostDao.findInsertIndex(post_data);
        if (post_data.size() >= 3) {
            List<Post> posts = new ArrayList<>();
            posts.add(post_data.get(pindex));
            pindex++;
            posts.add(post_data.get(pindex));
            pindex++;
            posts.add(post_data.get(pindex));
            pindex++;

            ListView lv = view.findViewById(R.id.post_list_city);

            PostAdapter adapter = new PostAdapter(getContext(), R.layout.post_list_view, posts);
            lv.setAdapter(adapter);
            lv.setOnScrollListener(new AbsListView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(AbsListView absListView, int state) {
                    if (state == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                        if (absListView.getLastVisiblePosition() == (absListView.getCount()) - 1) {
                            // end of the list, start again
                            if (pindex >= PostDao.post_size) {
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
        } else if (post_data.size() >= 0) {
            ListView lv = view.findViewById(R.id.post_list_city);
            PostAdapter adapter = new PostAdapter(getContext(), R.layout.post_list_view, post_data);
        } else {
            PostAdapter adapter = new PostAdapter(getContext(), R.layout.post_list_view, new ArrayList<>());
            Toast.makeText(getContext(),"There's no user at the same city as you",Toast.LENGTH_LONG);
        }
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    public void paddingPicture(EditText tv, int pic) {
        @SuppressLint("UseCompatLoadingForDrawables") Drawable drawable1 = getResources().getDrawable(pic);
        drawable1.setBounds(20, 0, 50, 50);//set distance
        tv.setCompoundDrawables(drawable1, null, null, null);//left only
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}