package com.akvashi.focused.a8todo.mvp.presenter;


import com.akvashi.focused.a8todo.mvp.view.View;

public interface Presenter<T extends View> {

    void onCreate();

    void onStart();

    void onStop();

    void onPause();

    void attachView(T view);

}
