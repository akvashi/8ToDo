package com.akvashi.focused.a8todo.gson;

import com.akvashi.focused.a8todo.injector.module.NetworkModule;
import com.akvashi.focused.a8todo.mvp.model.FriendlyMessage;
import com.akvashi.focused.a8todo.mvp.model.ResponseWrapper;
import com.akvashi.focused.a8todo.mvp.model.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tomasz Kulikowski on 03.12.2015.
 * Copyright © 2015 Macoscope sp. z o.o. All rights reserved.
 */
public class ResponseDeserializer implements JsonDeserializer<ResponseWrapper> {


    private Type eventType = new TypeToken<FriendlyMessage>() {
    }.getType();

    private Type userType = new TypeToken<User>() {
    }.getType();

    private Gson gson = new GsonBuilder()
            .setDateFormat(NetworkModule.API_DATE_FORMAT)
            .setFieldNamingPolicy(NetworkModule.API_JSON_NAMING_POLICY)
            .create();

    @Override
    public ResponseWrapper deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {

        ResponseWrapper responseWrapper = null;
        JsonObject jsonObject = json.getAsJsonObject();
        if (jsonObject.has(FriendlyMessage.JSON_ARRAY_NAME)) {
            JsonArray jsonArray = jsonObject.getAsJsonArray(FriendlyMessage.JSON_ARRAY_NAME);
            int size = jsonArray.size();
            List<FriendlyMessage> events = new ArrayList<>(size);
            JsonElement jsonElement;
            for (int i = 0; i < size; i++) {
                jsonElement = jsonArray.get(i);
                FriendlyMessage event = parseTask(jsonElement);
                events.add(event);
            }
            responseWrapper = new ResponseWrapper<List<FriendlyMessage>>();
            responseWrapper.body = events;
        } else if (jsonObject.has(User.JSON_ARRAY_NAME)) {
            JsonArray jsonArray = jsonObject.getAsJsonArray(User.JSON_ARRAY_NAME);
            int size = jsonArray.size();
            List<User> users = new ArrayList<>(size);
            JsonElement jsonElement;
            for (int i = 0; i < size; i++) {
                jsonElement = jsonArray.get(i);
                User user = parseUser(jsonElement);
                users.add(user);
            }
            responseWrapper = new ResponseWrapper<List<User>>();
            responseWrapper.body = users;
        } else if (jsonObject.has(FriendlyMessage.JSON_OBJECT_NAME)) {
            JsonElement jsonElement = jsonObject.get(FriendlyMessage.JSON_OBJECT_NAME);
            FriendlyMessage event = parseTask(jsonElement);
            responseWrapper = new ResponseWrapper<FriendlyMessage>();
            responseWrapper.body = event;
        }
        return responseWrapper;
    }

    private FriendlyMessage parseTask(JsonElement jsonElement) {
        return gson.fromJson(jsonElement, eventType);
    }

    private User parseUser(JsonElement jsonElement) {
        return gson.fromJson(jsonElement, userType);
    }
}
