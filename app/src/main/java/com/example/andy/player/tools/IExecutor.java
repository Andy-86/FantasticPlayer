package com.example.andy.player.tools;

/**
 * Created by andy on 2017/1/20.
 */
public interface IExecutor<T> {
    void execute();

    void onPrepare();

    void onExecuteSuccess(T t);

    void onExecuteFail(Exception e);
}
