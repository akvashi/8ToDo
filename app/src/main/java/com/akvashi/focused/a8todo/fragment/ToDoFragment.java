package com.akvashi.focused.a8todo.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.akvashi.focused.a8todo.R;
import com.akvashi.focused.a8todo.activity.MainActivity;
import com.akvashi.focused.a8todo.adapter.TasksAdapter;
import com.akvashi.focused.a8todo.mvp.model.FriendlyMessage;
import com.akvashi.focused.a8todo.mvp.presenter.ToDoPresenter;
import com.akvashi.focused.a8todo.mvp.view.ToDoView;
import com.jakewharton.rxbinding.view.RxView;
import com.jakewharton.rxbinding.widget.RxTextView;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ToDoFragment extends Fragment implements ToDoView {

    @Bind(R.id.add_task_button)
    Button addTaskButton;
    @Bind(R.id.tasks_recycler_view)
    RecyclerView tasksRecyclerView;
    @Bind(R.id.loading)
    ProgressBar loading;
    @Bind(R.id.add_task_edit_text)
    EditText addTaskEditText;
    @Bind(R.id.content)
    View content;

    @Inject
    ToDoPresenter toDoPresenter;

    private LinearLayoutManager linearLayoutManager;

    public static final String MESSAGES_CHILD = "messages";

    private TasksAdapter tasksAdapter;

    public ToDoFragment() {
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, root);
        initAdapter();
        initList();
        showLoading();
        toDoPresenter.attachView(this);
        return root;
    }


    private void initAdapter() {
        if (tasksAdapter == null) {
            tasksAdapter = new TasksAdapter(getContext(), Collections.EMPTY_LIST);
        }
    }

    private void initList() {
        linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setStackFromEnd(true);
        tasksRecyclerView.setLayoutManager(linearLayoutManager);
        tasksRecyclerView.setAdapter(tasksAdapter);
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ((MainActivity)getActivity()).getComponent().inject(this);

        toDoPresenter.onCreate();

        RxTextView
            .textChangeEvents(addTaskEditText)
            .map(event -> event.text().length() > 3)
            .subscribe(longEnough -> addTaskButton.setEnabled(longEnough));

        RxView
            .clicks(addTaskButton)
            .subscribe(
                click -> {
                    isInLayout();
                }
            );
    }

    @Override
    public void onStart() {
        toDoPresenter.onStart();
    }

    @Override
    public void onStop() {
        toDoPresenter.onStop();
    }

    @Override
    public void onPause() {
        toDoPresenter.onPause();
    }

    @Override
    public void showTasks(List<FriendlyMessage> tasks) {
        tasksAdapter.updateList(tasks);
    }

    @Override
    public void showLoading() {
        loading.setVisibility(View.VISIBLE);
        content.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showError() {

    }

    @Override
    public void showContent() {
        loading.setVisibility(View.INVISIBLE);
        content.setVisibility(View.VISIBLE);
    }
}
