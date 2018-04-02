package com.example.andy.player.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.andy.player.bean.Post;
import com.example.andy.player.interfaces.ClickMoreListner;
import com.example.andy.player.weight.PostView;

import java.util.List;

/**
 * Created by andy on 2018/1/2.
 */

public class CommentAdapter   extends BaseAdapter {
    private List<Post> posts;
    private Context context;

    public void setClickMoreListner(ClickMoreListner<Post> clickMoreListner) {
        this.clickMoreListner = clickMoreListner;
    }

    public ClickMoreListner<Post> clickMoreListner;
    public CommentAdapter(List<Post> posts, Context context) {
        this.posts = posts;
        this.context=context;
    }

    @Override
    public int getCount() {
        return posts.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (null==convertView){
            convertView = new PostView(context);
        }
        ((PostView)convertView).setPost(posts.get(position));
        ((PostView) convertView).setClickMoreListner(clickMoreListner);
        return convertView;
    }
}