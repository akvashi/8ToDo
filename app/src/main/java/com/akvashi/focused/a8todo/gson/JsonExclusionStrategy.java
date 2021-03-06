package com.akvashi.focused.a8todo.gson;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.annotations.Expose;

public class JsonExclusionStrategy implements ExclusionStrategy {
    @Override
    public boolean shouldSkipField(FieldAttributes fieldAttributes) {
        final Expose expose = fieldAttributes.getAnnotation(Expose.class);
        return expose != null && !expose.serialize();
    }

    @Override
    public boolean shouldSkipClass(Class<?> aClass) {
        return false;
    }
}
