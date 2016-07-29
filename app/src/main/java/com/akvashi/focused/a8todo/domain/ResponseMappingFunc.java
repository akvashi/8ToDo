package com.akvashi.focused.a8todo.domain;

import com.akvashi.focused.a8todo.mvp.model.ResponseWrapper;

import rx.functions.Func1;

public class ResponseMappingFunc<R> implements Func1<ResponseWrapper<R>, R> {
    @Override
    public R call(ResponseWrapper<R> responseWrapper) {
        if (responseWrapper == null) {
            return null;
        }
        return responseWrapper.body;
    }
}
