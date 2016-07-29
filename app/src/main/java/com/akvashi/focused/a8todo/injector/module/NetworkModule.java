package com.akvashi.focused.a8todo.injector.module;

import com.akvashi.focused.a8todo.BuildConfig;
import com.akvashi.focused.a8todo.gson.JsonExclusionStrategy;
import com.akvashi.focused.a8todo.gson.ResponseDeserializer;
import com.akvashi.focused.a8todo.injector.scope.PerApplication;
import com.akvashi.focused.a8todo.mvp.model.ResponseWrapper;
import com.akvashi.focused.a8todo.network.Repository;
import com.akvashi.focused.a8todo.rest.RetrofitRestRepository;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.logging.HttpLoggingInterceptor;

import dagger.Module;
import dagger.Provides;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;

@Module
public class NetworkModule {

    public static final String API_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSZZZZZ";
    public static final FieldNamingPolicy API_JSON_NAMING_POLICY = FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES;

    @Provides
    @PerApplication
    Repository provideRepository(Retrofit retrofit) {
        return new RetrofitRestRepository(retrofit);
    }

    @Provides
    @PerApplication
    Retrofit provideRetrofit() {
        String endpointUrl = BuildConfig.API_ENDPOINT;


        Gson gson = new GsonBuilder()
                .setDateFormat(API_DATE_FORMAT)
                .setFieldNamingPolicy(API_JSON_NAMING_POLICY)
                .registerTypeAdapter(ResponseWrapper.class, new ResponseDeserializer())
                .addSerializationExclusionStrategy(new JsonExclusionStrategy())
                .addDeserializationExclusionStrategy(new JsonExclusionStrategy())
                .create();

        GsonConverterFactory gsonConverterFactory = GsonConverterFactory.create(gson);
        OkHttpClient client = new OkHttpClient();
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        client.interceptors().add(logging);


        return new Retrofit.Builder()
                .baseUrl(endpointUrl)
                .client(client)
                .addConverterFactory(gsonConverterFactory)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }

}
