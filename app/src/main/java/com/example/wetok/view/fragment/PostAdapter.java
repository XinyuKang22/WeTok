package com.example.wetok.view.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.wetok.R;
import com.example.wetok.bean.Post;
import com.example.wetok.dao.CurrentUser;
import com.example.wetok.dao.UserDao;
import com.example.wetok.view.ProfileActivity;
import com.example.wetok.view.SearchActivity;

import java.util.List;

public class PostAdapter extends ArrayAdapter<Post> {
    private int resourceId;

    public PostAdapter(@NonNull Context context, int resource, @NonNull List<Post> objects) {
        super(context, resource, objects);
        resourceId = resource;
    }

//    @SuppressLint("ViewHolder")
    @SuppressLint({"SetTextI18n", "ViewHolder"})
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Post post = getItem(position);
        System.out.println(post.getAuthor());
        View view;
        ViewHolder viewHolder;
        // if (convertView == null) {
        view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);

        // like
        TextView likeButton = view.findViewById(R.id.list_post_like);
        likeButton.setOnClickListener(e -> {
            paddingPicture(likeButton, R.drawable.ic_like,60);
            post.setLikes(post.getLikes() + 1);
            likeButton.setText(String.valueOf(post.getLikes()));
        });
        // subscript
        TextView subButton = view.findViewById(R.id.list_post_btn_sub);
        subButton.setOnClickListener(e -> {
            paddingPicture(subButton, R.drawable.ic_subscribe,100);
            post.setStar(post.getStar()+1);
            subButton.setText(String.valueOf(post.getStar()));
        });
        subButton.setText(""+post.getStar());
        likeButton.setText(""+post.getLikes());
        paddingPicture(likeButton, R.drawable.ic_like_gray,60);
        paddingPicture(subButton, R.drawable.ic_subscribe_gray,100);

        // time
        TextView Time = view.findViewById(R.id.list_post_time);
        String theDate = post.getTime().substring(5,10);
        String theYear = post.getTime().substring(2,4);
        String theTime = post.getTime().substring(11,16);
        Time.setText(theTime + " " + theDate);

        viewHolder = new ViewHolder();
        viewHolder.photo = view.findViewById(R.id.list_post_user_image);
        viewHolder.username = view.findViewById(R.id.list_post_user_name);
        viewHolder.content = view.findViewById(R.id.list_post_content);
        viewHolder.content.setPadding(15, 15, 15, 15);
        viewHolder.tags = view.findViewById(R.id.list_post_tags);
        view.setTag(viewHolder);
        viewHolder.username.setOnClickListener(e -> {
            Intent intent = new Intent(getContext(), ProfileActivity.class);
            Toast.makeText(getContext(), "post", Toast.LENGTH_SHORT).show();
            CurrentUser.current_visitor = UserDao.findUserById(post.getUid());
            getContext().startActivity(intent);
        });
        viewHolder.photo.setOnClickListener(e -> {
            Intent intent = new Intent(getContext(), ProfileActivity.class);
            CurrentUser.current_visitor = UserDao.findUserById(post.getEmail());
            getContext().startActivity(intent);
        });
        viewHolder.username.setText(post.getAuthor());
        viewHolder.content.setText(post.getContent());

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

            TextView tv = new TextView(getContext(), null);
            tv.setPadding(28, 0, 28, 5);
            tv.setHeight(32);
            String theTag = post.getTag().toString();
            theTag = theTag.replace(",","");
            theTag = theTag.replace("[","");
            theTag = theTag.replace("]","");
            tv.setText(theTag);
            tv.setSingleLine();
            tv.setBackgroundResource(R.drawable.round_corner);
            tv.setLayoutParams(layoutParams);

            tv.setOnClickListener(e -> {
                Intent intent = new Intent(getContext(), SearchActivity.class);
                intent.putExtra("tag", tv.getText());
                getContext().startActivity(intent);
            });
            viewHolder.tags.addView(tv, layoutParams);
//        }

        view.setPadding(0, 40, 0, 0);
//    } else {
//            view = convertView;
//            viewHolder = (ViewHolder) view.getTag();
//        }
//
        return view;
    }

    class ViewHolder {
        TextView username;
        TextView content;
        MyLinearLayout tags;
        ImageView photo;
    }

    private void paddingPicture(TextView tv, int pic, int size) {
        @SuppressLint("UseCompatLoadingForDrawables") Drawable drawable1 = getContext().getResources().getDrawable(pic);

        drawable1.setBounds(10, 0, size, size);//第一0是距左边距离，第二0是距上边距离，40分别是长宽
        tv.setCompoundDrawables(drawable1, null, null, null);//只放左边
    }

}
