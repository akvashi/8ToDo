package com.akvashi.focused.a8todo.domain;

import com.akvashi.focused.a8todo.mvp.model.FriendlyMessage;
import com.akvashi.focused.a8todo.network.Repository;

import java.util.List;

import rx.Observable;

public class FetchTasksUsecase implements Usecase<List<FriendlyMessage>> {

    private Repository repository;

    public FetchTasksUsecase(Repository repository) {
        this.repository = repository;
    }

    @Override
    public Observable<List<FriendlyMessage>> execute() {
        return repository.getTasks().map(new ResponseMappingFunc<>());
    }
    
}
