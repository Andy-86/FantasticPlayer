package com.example.andy.player.weight;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.andy.player.R;
import com.example.andy.player.bean.Comment;
import com.example.andy.player.bean.User;

import java.util.List;


/**
 * 用来显示PostView中盖楼的自定义控件
 *
 * @author Aige
 * @since 2014/11/14
 */
public class FloorView extends LinearLayout {
    private Context context;//上下文环境引用

    private Drawable drawable;//背景Drawable

    public FloorView(Context context) {
        this(context, null);
    }

    public FloorView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    @SuppressLint("NewApi")
    public FloorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;

        //获取背景Drawable的资源文件
        drawable = context.getResources().getDrawable(R.drawable.view_post_comment_bg);
    }

    /**
     * 设置Comment数据
     *
     * @param comments Comment数据列表
     */
    public void setComments(List<Comment> comments) {
        //清除子View
        removeAllViews();
        setOrientation(VERTICAL);
        //获取评论数
        int count = comments.size();

        /*
        如果评论条数小于9条则直接显示，否则我们只显示评论的头两条和最后一条（这里的最后一条是相对于PostView中已经显示的一条评论来说的）
         */
        if (count < 9) {
            initViewWithAll(comments);
        } else {
            initViewWithHide(comments);
        }
    }

    /**
     * 初始化所有的View
     *
     * @param comments 评论数据列表
     */
    private void initViewWithAll(List<Comment> comments) {
        for (int i = 1; i < comments.size(); i++) {
            View commentView = getView(comments.get(i), i, comments.size() - 1, false);
            addView(commentView);
        }
    }

    /**
     * 初始化带有隐藏楼层的View
     *
     * @param comments 评论数据列表
     */
    private void initViewWithHide(final List<Comment> comments) {
        View commentView = null;

        //初始化一楼
        commentView = getView(comments.get(0), 0, comments.size() - 1, false);
        addView(commentView);

        //初始化二楼
        commentView = getView(comments.get(1), 1, comments.size() - 1, false);
        addView(commentView);

        //初始化隐藏楼层标识
        commentView = getView(null, 2, comments.size() - 1, true);
        commentView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                initViewWithAll(comments);
            }
        });
        addView(commentView);

        //初始化倒数第二楼
        commentView = getView(comments.get(comments.size() - 2), 3, comments.size() - 1, false);
        addView(commentView);
    }

    /**
     * 获取单个评论子视图
     *
     * @param comment 评论对象
     * @param index   第几个评论
     * @param count   总共有几个评论
     * @param isHide  是否是隐藏显示
     * @return 一个评论子视图
     */
    private View getView(Comment comment, int index, int count, boolean isHide) {
        //获取根布局
        View commentView = LayoutInflater.from(context).inflate(R.layout.view_post_comment, null);

        //获取控件
        TextView tvUserName = (TextView) commentView.findViewById(R.id.view_post_comment_username_tv);
        TextView tvContent = (TextView) commentView.findViewById(R.id.view_post_comment_content_tv);
        TextView tvNum = (TextView) commentView.findViewById(R.id.view_post_comment_num_tv);
        TextView tvHide = (TextView) commentView.findViewById(R.id.view_post_comment_hide_tv);


        /*
        判断是否是隐藏楼层
         */
        if (isHide) {
            /*
            是则显示“点击显示隐藏楼层”控件而隐藏其他的不相干控件
             */
            tvUserName.setVisibility(GONE);
            tvContent.setVisibility(GONE);
            tvNum.setVisibility(GONE);
            tvHide.setVisibility(VISIBLE);
        } else {
            /*
            否则隐藏“点击显示隐藏楼层”控件而显示其他的不相干控件
             */
            tvUserName.setVisibility(VISIBLE);
            tvContent.setVisibility(VISIBLE);
            tvNum.setVisibility(VISIBLE);
            tvHide.setVisibility(GONE);

            //获取用户对象
            User user = comment.getUser();

            //设置显示数据
            tvUserName.setText(user.getUser());
            tvContent.setText(comment.getContent());
            tvNum.setText(String.valueOf(index + 1));


        }

        //设置布局参数
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);

        //计算margin指数，这个指数的意义在于将第一个的margin值设置为最大的，然后依次递减体现层叠效果
        int marginIndex = count - index;
        int margin = marginIndex * 3;

        params.setMargins(margin, margin, margin, 0);
        commentView.setLayoutParams(params);

        return commentView;
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        /*
        在FloorView绘制子控件前先绘制层叠的背景图片
         */
        for (int i = getChildCount() - 1; i >= 0; i--) {
            View view = getChildAt(i);
            drawable.setBounds(view.getLeft(), view.getLeft(), view.getRight(), view.getBottom());
            drawable.draw(canvas);
        }
        super.dispatchDraw(canvas);
    }
}
