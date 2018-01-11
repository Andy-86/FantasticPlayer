package com.example.andy.player.mvp.comment;

import com.example.andy.player.bean.Comment;
import com.example.andy.player.mvp.base.BasePresenter;
import com.example.andy.player.tools.ToastUtil;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import okhttp3.ResponseBody;

/**
 * Created by andy on 2018/1/2.
 */

public class CommentPresenter extends BasePresenter<CommentFragment,CommentModle> {
    @Override
    public CommentModle createModel() {
        return new CommentModle(this);
    }

    public void addCommnet(int SongId, String cotent){
        mModel.addComment(new Observer<ResponseBody>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(ResponseBody value) {
                ToastUtil.Toast("添加成功");
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        },SongId,cotent);
    }

    public void findBySongId(int SongId){
        mModel.finBySongid(new Observer<List<Comment>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(List<Comment> value) {
                if(value!=null){
                    mView.onGetCommentList(value);
                }
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        }, SongId);
    }
}
