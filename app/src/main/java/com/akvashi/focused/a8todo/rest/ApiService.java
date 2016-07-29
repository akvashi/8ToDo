package com.akvashi.focused.a8todo.rest;

import com.akvashi.focused.a8todo.mvp.model.FriendlyMessage;
import com.akvashi.focused.a8todo.mvp.model.ResponseWrapper;

import java.util.List;

import retrofit.http.GET;
import rx.Observable;

public interface ApiService {

    @GET("messages")
    Observable<ResponseWrapper<List<FriendlyMessage>>> getTasks();

}
