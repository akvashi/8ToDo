package com.akvashi.focused.a8todo.mvp.model;

public class ResponseWrapper<T> {
    public T body;

    public ResponseWrapper(){
    }

    public ResponseWrapper(T body) {
        this.body = body;
    }
}
