package com.example.andy.player.weight;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.andy.player.R;
import com.example.andy.player.bean.Comment;
import com.example.andy.player.bean.Post;

import java.util.List;


/**
 * 用来显示Post的自定义控件
 *
 * @author Aige
 * @since 2014/11/14
 */
public class PostView extends LinearLayout {
    private TextView tvType, tvUserName, tvLocation, tvDate, tvPraise, tvContent;//依次为显示类型标签、用户名、地理位置、日期、赞数据和最后一条评论内容的TextView
    private CircleImageView civNick;//用户圆形头像显示控件
    private FloorView floorView;//盖楼控件

    public PostView(Context context) {
        this(context, null);
    }

    public PostView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    @SuppressLint("NewApi")
    public PostView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        //初始化控件
        initWidget(context);
    }

    /**
     * 初始化控件
     *
     * @param context 上下文环境引用
     */
    private void initWidget(Context context) {
        //设置布局
        LayoutInflater.from(context).inflate(R.layout.view_post, this);

        //获取控件
        tvType = (TextView) findViewById(R.id.view_post_type_tv);
        tvUserName = (TextView) findViewById(R.id.view_post_username_tv);
        tvLocation = (TextView) findViewById(R.id.view_post_location_tv);
        tvDate = (TextView) findViewById(R.id.view_post_date_tv);
        tvPraise = (TextView) findViewById(R.id.view_post_praise_tv);
        tvContent = (TextView) findViewById(R.id.view_post_content_tv);

        civNick = (CircleImageView) findViewById(R.id.view_post_nick_civ);

        floorView = (FloorView) findViewById(R.id.view_post_floor_fv);
    }

    /**
     * 为PostView设置数据
     *
     * @param post 数据源
     */
    public void setPost(Post post) {

        //设置Post的赞数据
        setPraise(post);

        //获取该条帖子下的评论列表
        List<Comment> comments = post.getComments();

        /*
        判断评论长度
        1.如果只有一条评论那么则显示该评论即可并隐藏盖楼布局
        2.否则我们进行盖楼显示
         */
        if (comments.size() == 1) {
            floorView.setVisibility(GONE);

            Comment comment = comments.get(0);

            //设置控件显示数据
            initUserDate(comment);
        } else {
            //盖楼前我们要把最后一条评论数据提出来显示在Post最外层
            int index = comments.size() - 1;
            Comment comment = comments.get(index);

            //设置控件显示数据
            initUserDate(comment);

            floorView.setComments(comments);
        }
    }

    /**
     * 设置与用户相关的控件数据显示
     *
     * @param comment 评论对象
     */
    private void initUserDate(Comment comment) {
        tvContent.setText(comment.getContent());
        tvDate.setText(comment.getCreateAt());
        tvUserName.setText(comment.getUesername());
        tvLocation.setText("");
        civNick.setImageResource(R.drawable.image2);
    }

    /**
     * 设置Post的赞数据
     *
     * @param post 数据源
     */
    private void setPraise(Post post) {
        tvPraise.setText(post.getUserPraises() + "赞");
    }
}
