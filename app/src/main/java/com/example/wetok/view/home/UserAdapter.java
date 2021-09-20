package com.example.wetok.view.home;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.wetok.R;
import com.example.wetok.bean.Post;
import com.example.wetok.bean.User;

import java.util.List;

public class UserAdapter extends ArrayAdapter<User> {
    private int resourceId;
    public UserAdapter(@NonNull Context context, int resource, @NonNull List<User> objects) {
        super(context, resource, objects);
        resourceId=resource;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        User user = getItem(position);
        View view;
        ViewHolder viewHolder;
        if (convertView == null){
            view = LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
            viewHolder = new ViewHolder();
            viewHolder.username = view.findViewById(R.id.userlist_username);
            viewHolder.userid = view.findViewById(R.id.userlist_userid);
            viewHolder.photo = view.findViewById(R.id.userlist_photo);
            view.setTag(viewHolder);
        }else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.username.setText(user.name);
        viewHolder.userid.setText(user.id);
        viewHolder.photo.setImageResource(user.photo);
        view.setPadding(0,40,0,0);
        return view;
    }
    class ViewHolder{
        ImageView photo;
        TextView username;
        TextView userid;
    }


}
