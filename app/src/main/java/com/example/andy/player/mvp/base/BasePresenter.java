package com.example.andy.player.mvp.base;

/**
 * Created by Administrator on 2017-7-17.
 */

public abstract class BasePresenter<V extends IView, M extends BaseModel> {

    public V mView;

    public M mModel;

    public void attach(V view) {
        mView = view;
        mModel = createModel();
    }

    public abstract M createModel();

    public void detach() {
        mView = null;
    }

}
