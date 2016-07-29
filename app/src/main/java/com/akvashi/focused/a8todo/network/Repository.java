package com.akvashi.focused.a8todo.network;

import com.akvashi.focused.a8todo.mvp.model.FriendlyMessage;
import com.akvashi.focused.a8todo.mvp.model.ResponseWrapper;

import java.util.List;

import rx.Observable;

public interface Repository {

    Observable<ResponseWrapper<List<FriendlyMessage>>> getTasks();

}
