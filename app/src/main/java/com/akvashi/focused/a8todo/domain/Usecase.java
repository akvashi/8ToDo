package com.akvashi.focused.a8todo.domain;

import rx.Observable;

public interface Usecase<T> {
    Observable<T> execute();
}
