package com.akvashi.focused.a8todo.mvp.view;

import com.akvashi.focused.a8todo.mvp.model.FriendlyMessage;

import java.util.List;

public interface ToDoView extends LCEView {
    void showTasks(List<FriendlyMessage> tasks);
}
