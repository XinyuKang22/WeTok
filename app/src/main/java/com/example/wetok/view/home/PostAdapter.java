package com.example.wetok.view.home;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.wetok.R;
import com.example.wetok.bean.Post;

import java.util.List;

public class PostAdapter extends ArrayAdapter<Post> {
    private int resourceId;
    public PostAdapter(@NonNull Context context, int resource, @NonNull List<Post> objects) {
        super(context, resource, objects);
        resourceId=resource;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Post post = getItem(position);
        View view;
        ViewHolder viewHolder;
        if (convertView == null){
            view = LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
            TextView likeButton = view.findViewById(R.id.list_post_like);
            TextView subButton = view.findViewById(R.id.list_post_btn_sub);
            paddingPicture(likeButton, R.drawable.ic_like);
            paddingPicture(subButton, R.drawable.ic_subscribe);
            viewHolder = new ViewHolder();
            viewHolder.username = view.findViewById(R.id.list_post_user_name);
            viewHolder.content = view.findViewById(R.id.list_post_content);
            viewHolder.content.setPadding(15,15,15,15);
            viewHolder.tags = view.findViewById(R.id.list_post_tags);
            view.setTag(viewHolder);
        }else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.username.setText(post.user.name);
        viewHolder.content.setText(post.content);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        TextView tv = new TextView(getContext());
        tv.setPadding(28, 10, 28, 10);
        tv.setText("Java");
        tv.setMaxEms(10);
        tv.setSingleLine();
        tv.setLayoutParams(layoutParams);
        viewHolder.tags.addView(tv, layoutParams);
        view.setPadding(0,40,0,0);
        return view;
    }
    class ViewHolder{
        TextView username;
        TextView content;
        FlowLayout tags;
    }

    private void paddingPicture(TextView tv, int pic) {
        Drawable drawable1 = getContext().getResources().getDrawable(pic);
        drawable1.setBounds(10, 0, 60, 60);//第一0是距左边距离，第二0是距上边距离，40分别是长宽
        tv.setCompoundDrawables(drawable1, null, null, null);//只放左边
    }

}
