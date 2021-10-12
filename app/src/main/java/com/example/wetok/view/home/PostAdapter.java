package com.example.wetok.view.home;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.wetok.R;
import com.example.wetok.bean.Post;
import com.example.wetok.view.ProfileActivity;
import com.example.wetok.view.SearchActivity;

import java.util.List;

public class PostAdapter extends ArrayAdapter<Post> {
    private int resourceId;

    public PostAdapter(@NonNull Context context, int resource, @NonNull List<Post> objects) {
        super(context, resource, objects);
        resourceId = resource;
    }

    @SuppressLint("ViewHolder")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Post post = getItem(position);
        System.out.println(post.getAuthor());
        View view;
        ViewHolder viewHolder;
        // if (convertView == null) {
        view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
        TextView likeButton = view.findViewById(R.id.list_post_like);
        // 点赞listener
        likeButton.setOnClickListener(e -> {
            paddingPicture(likeButton, R.drawable.ic_like_gray);
            post.setLikes(post.getLikes() + 1);
            likeButton.setText(post.getLikes());
        });
        // 订阅listener
        TextView subButton = view.findViewById(R.id.list_post_btn_sub);
        subButton.setOnClickListener(e -> {
            paddingPicture(subButton, R.drawable.ic_subscribe_gray);
            post.setStar(post.getStar()+1);
            subButton.setText(post.getStar());
        });
        subButton.setText(""+post.getStar());
        likeButton.setText(""+post.getLikes());
        paddingPicture(likeButton, R.drawable.ic_like);
        paddingPicture(subButton, R.drawable.ic_subscribe);
        viewHolder = new ViewHolder();
        viewHolder.photo = view.findViewById(R.id.list_post_user_image);
        viewHolder.username = view.findViewById(R.id.list_post_user_name);
        viewHolder.content = view.findViewById(R.id.list_post_content);
        viewHolder.content.setPadding(15, 15, 15, 15);
        viewHolder.tags = view.findViewById(R.id.list_post_tags);
        view.setTag(viewHolder);
        viewHolder.username.setOnClickListener(e -> {
            Intent intent = new Intent(getContext(), ProfileActivity.class);
            intent.putExtra("user", post.user);
            getContext().startActivity(intent);
        });
        viewHolder.photo.setOnClickListener(e -> {
            Intent intent = new Intent(getContext(), ProfileActivity.class);
            intent.putExtra("user", post.user);
            getContext().startActivity(intent);
        });
        viewHolder.username.setText(post.getAuthor());
        viewHolder.content.setText(post.getContent());

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        for (int i = 0; i < 5; i++) {
            TextView tv = new TextView(getContext(), null);
            tv.setPadding(28, 0, 28, 5);
            tv.setHeight(32);
            tv.setText(post.getTag());
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

    private void paddingPicture(TextView tv, int pic) {
        Drawable drawable1 = getContext().getResources().getDrawable(pic);

        drawable1.setBounds(10, 0, 60, 60);//第一0是距左边距离，第二0是距上边距离，40分别是长宽
        tv.setCompoundDrawables(drawable1, null, null, null);//只放左边
    }

}
