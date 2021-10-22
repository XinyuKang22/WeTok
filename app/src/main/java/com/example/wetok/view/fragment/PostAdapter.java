package com.example.wetok.view.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.example.wetok.R;
import com.example.wetok.bean.Post;
import com.example.wetok.bean.User;
import com.example.wetok.dao.CurrentUser;
import com.example.wetok.dao.UserDao;
import com.example.wetok.searchTree.Search;
import com.example.wetok.view.ProfileActivity;
import com.example.wetok.view.SearchActivity;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;

/**
 * This is the postAdapter
 * @author Zhaoting Jiang
 * @author Xinyue Hu
 * @author Yuxin Hong
 */
public class PostAdapter extends ArrayAdapter<Post> {
    private int resourceId;
    private List<Post> posts;

    public PostAdapter(@NonNull Context context, int resource, @NonNull List<Post> objects) {
        super(context, resource, objects);
        resourceId = resource;
        posts = objects;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressLint({"SetTextI18n", "ViewHolder", "ResourceType"})
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Post post = getItem(position);
        View view;
        ViewHolder viewHolder;
        // if (convertView == null) {
        view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);

        //delete post
        Button delete = view.findViewById(R.id.btn_delete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserDao.findUserById(Integer.parseInt(post.getUid())).getPosts().remove(post);
                Boolean succ = false;
                try {
                    succ = posts.remove(post);
                    //remove data from search tree
                    Search.instance.remove(post.getTime());
                    notifyDataSetChanged();

                } catch (Exception e) {

                } finally {
                    if (!succ) Toast.makeText(getContext(), "Delete Failed", Toast.LENGTH_LONG);
                }

            }
        });

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
            paddingPicture(subButton, R.drawable.ic_dislike,60);
            post.setDislikes(post.getDislikes()+1);
            subButton.setText(String.valueOf(post.getDislikes()));
        });
        subButton.setText(""+post.getDislikes());
        likeButton.setText(""+post.getLikes());
        paddingPicture(likeButton, R.drawable.ic_like_gray,60);
        paddingPicture(subButton, R.drawable.ic_dislike_gray,60);

        // time
        TextView Time = view.findViewById(R.id.list_post_time);
        String theYear = post.getTime().substring(2,4);
        String theTime = post.getTime().substring(11,16);
        LocalDate currentTime = LocalDate.now();
        LocalDate postTime = LocalDate.parse(post.getTime(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        int days = (int) ChronoUnit.DAYS.between(postTime, currentTime);
        String theMonth = postTime.getMonth().toString().toLowerCase();
        theMonth = theMonth.substring(0,1).toUpperCase() + theMonth.substring(1,3);
        if (days == 0){
            Time.setText("Today " + theTime);
        }else if (days == 1){
            Time.setText("Yesterday " + theTime);
        }else {
            if (postTime.getYear() < currentTime.getYear()){
                Time.setText("'" + theYear + "  " + theMonth + " " + postTime.getDayOfMonth() + "  " + theTime);
            }else {
                Time.setText(theMonth + " " + postTime.getDayOfMonth() + "  " + theTime);
            }
        }


        viewHolder = new ViewHolder();
        viewHolder.photo = view.findViewById(R.id.list_post_user_image);
        viewHolder.username = view.findViewById(R.id.list_post_user_name);
        viewHolder.content = view.findViewById(R.id.list_post_content);
        viewHolder.content.setPadding(15, 15, 15, 15);
        viewHolder.tags = view.findViewById(R.id.list_post_tags);
        view.setTag(viewHolder);
        viewHolder.username.setOnClickListener(e -> {
            Intent intent = new Intent(getContext(), ProfileActivity.class);
            CurrentUser.current_visitor = UserDao.findUserById(Integer.parseInt(post.getUid()));
            getContext().startActivity(intent);
        });
        viewHolder.photo.setOnClickListener(e -> {
            Intent intent = new Intent(getContext(), ProfileActivity.class);
            CurrentUser.current_visitor = UserDao.findUserById(Integer.parseInt(post.getUid()));
            getContext().startActivity(intent);
        });
        viewHolder.username.setText(post.getAuthor());
        viewHolder.content.setText(post.getContent());
        User sender = UserDao.findUserById(Integer.parseInt(post.getUid()));
        if (sender == null){
            sender = CurrentUser.current_user;
        }
        User.setImage(sender, viewHolder.photo);

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
        view.setPadding(0, 40, 0, 0);
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
