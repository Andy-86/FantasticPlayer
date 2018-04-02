package com.example.andy.player.mvp.comment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.andy.player.R;
import com.example.andy.player.adapter.CommentAdapter;
import com.example.andy.player.aidl.SongBean;
import com.example.andy.player.bean.Comment;
import com.example.andy.player.bean.Post;
import com.example.andy.player.bean.User;
import com.example.andy.player.interfaces.ClickMoreListner;
import com.example.andy.player.mvp.base.MvpFragment;
import com.example.andy.player.tools.LogUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by andy on 2018/1/2.
 */

public class CommentFragment extends MvpFragment<CommentPresenter> {
    public int status=-1;
    @BindView(R.id.comment_fm_content_lv)
    ListView commentFmContentLv;
    Unbinder unbinder;
    @BindView(R.id.comment_content)
    EditText commentContent;
    @BindView(R.id.comment_fm_send)
    ImageView commentFmSend;
    List<Post> list;
    private CommentAdapter adapter;
    public static SongBean songBean;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //获取根布局
        View rootView = inflater.inflate(R.layout.fragment_comment, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        changeDate();
    }

    public void changeDate(){
        mPresenter.findBySongId((int) songBean.getSongid());
    }
    @Override
    public CommentPresenter createPresenter() {
        return new CommentPresenter();
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void dismissLoading() {

    }

    @Override
    protected void initEvent() {


    }

    @Override
    protected void initData() {
        list = new ArrayList<>();
//        for (int i = 0; i < 10; i++) {
//            Post post = new Post();
//            List<Comment> list = new ArrayList<>();
//            User user = new User();
//            user.setPassword("  asdfasf");
//            user.setUser("18819259421");
//            list.add(new Comment("详细信息", user));
//            post.setComments(list);
//            post.setUserPraises(1222);
//            post.setFlag(System.currentTimeMillis() + "");
//            this.list.add(post);
//        }
        adapter = new CommentAdapter(list, getActivity());
        adapter.setClickMoreListner(new ClickMoreListner<Post>() {
            @Override
            public void ClickMore(Post post) {
                LogUtil.doLog("ClickMore","");
                commentContent.performClick();
                status=list.indexOf(post);
                LogUtil.doLog("ClickMore",""+status);
            }
        });
        commentFmContentLv.setAdapter(adapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.comment_fm_send)
    public void onViewClicked() {
        if(status==-1) {
            String content = commentContent.getText().toString();
            mPresenter.addCommnet((int) songBean.getSongid(), content);
            commentContent.setText("");

            Post post = new Post();
            List<Comment> clist = new ArrayList<>();
            User user = new User();
            user.setPassword("******");
            user.setUser("你");
            Comment comment = new Comment(content, user);
            comment.setUesername("you");
            comment.setCreateAt(System.currentTimeMillis() + "");
            clist.add(comment);
            post.setComments(clist);
            post.setUserPraises(0);
            post.setFlag(System.currentTimeMillis() + "");
            this.list.add(post);
            adapter.notifyDataSetChanged();
        }else {
            String content = commentContent.getText().toString();
            commentContent.setText("");
            User user = new User();
            user.setPassword("******");
            user.setUser("你");
            Comment comment = new Comment(content, user);
            comment.setCreateAt(System.currentTimeMillis() + "");
            list.get(status).getComments().add(comment);
            adapter.notifyDataSetChanged();
            status=-1;
        }
    }

    public void onGetCommentList(List<Comment> list){
        this.list.removeAll(this.list);
        for(Comment comment:list){
            Post post = new Post();
            List<Comment> clist = new ArrayList<>();
            User user = new User();
            user.setPassword("******");
            user.setUser("18819259421");
            clist.add(comment);
            post.setComments(clist);
            post.setUserPraises(comment.getLiks());
            post.setFlag(System.currentTimeMillis() + "");
            this.list.add(post);
        }
        adapter.notifyDataSetChanged();
    }
}
