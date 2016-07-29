package com.akvashi.focused.a8todo.rest;

import com.akvashi.focused.a8todo.mvp.model.FriendlyMessage;
import com.akvashi.focused.a8todo.mvp.model.ResponseWrapper;
import com.akvashi.focused.a8todo.network.Repository;

import java.util.List;

import retrofit.Retrofit;
import rx.Observable;

public class RetrofitRestRepository implements Repository {

    private ApiService apiService;

    public RetrofitRestRepository(Retrofit retrofit) {
        apiService = retrofit.create(ApiService.class);
    }

    @Override
    public Observable<ResponseWrapper<List<FriendlyMessage>>> getTasks() {
        return apiService.getTasks();
    }

}
