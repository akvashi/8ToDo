package com.akvashi.focused.a8todo.mvp.presenter;

import com.akvashi.focused.a8todo.domain.FetchTasksUsecase;
import com.akvashi.focused.a8todo.mvp.model.FriendlyMessage;
import com.akvashi.focused.a8todo.mvp.view.ToDoView;

import java.util.List;

import rx.Subscription;
import rx.schedulers.Schedulers;

public class ToDoPresenter implements Presenter<ToDoView> {

    private ToDoView toDoView;
    private Subscription getEventsSubscription;
    private FetchTasksUsecase fetchEventCollectionUsecase;
    private List<FriendlyMessage> friendlyMessages;

    public ToDoPresenter(FetchTasksUsecase fetchEventCollectionUsecase) {
        this.fetchEventCollectionUsecase = fetchEventCollectionUsecase;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onStart() {
        getEvents();
    }

    @Override
    public void onStop() {
        if (getEventsSubscription != null && !getEventsSubscription.isUnsubscribed()) {
            getEventsSubscription.unsubscribe();
        }
    }

    @Override
    public void onPause() {

    }

    @Override
    public void attachView(ToDoView view) {
        this.toDoView = view;
    }

    private void getEvents() {
        if (friendlyMessages != null && friendlyMessages.size() > 0) {
            toDoView.showTasks(friendlyMessages);
        } else {
            toDoView.showLoading();
        }

        getEventsSubscription = fetchEventCollectionUsecase.execute()
                .subscribeOn(Schedulers.io())
                .onErrorReturn(
                    error -> {
                        error.printStackTrace();
                        toDoView.showError();
                        return null;
                    })
                .subscribe(
                    friendlyMessages -> {
                        if (friendlyMessages != null && friendlyMessages.size() > 0) {
                            this.friendlyMessages = friendlyMessages;
                            toDoView.showTasks(friendlyMessages);
                        } else {
                            //TODO Add empty state
                            toDoView.showTasks(null);
                        }
                    });
    }

    public void onRefresh() {
        friendlyMessages = null;
        getEvents();
    }
}
