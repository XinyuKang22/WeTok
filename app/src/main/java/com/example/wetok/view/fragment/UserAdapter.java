package com.example.wetok.view.fragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.wetok.R;
import com.example.wetok.bean.User;

import java.util.List;

/**
 * This is the UserAdapter
 * @author Zhaoting Jiang
 */
public class UserAdapter extends ArrayAdapter<User> {
    private int resourceId;
    private Context context;
    public UserAdapter(@NonNull Context context, int resource, @NonNull List<User> objects) {
        super(context, resource, objects);
        this.context = context;
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
        viewHolder.username.setText(user.getName());
        viewHolder.userid.setText(user.getId());
        if (user.getImgloc() == "default" || user.getImgloc() == "null") {
            viewHolder.photo.setImageResource(R.drawable.photo);
        }

        view.setPadding(0,40,0,0);
        return view;
    }

    class ViewHolder{
        ImageView photo;
        TextView username;
        TextView userid;
    }


}
