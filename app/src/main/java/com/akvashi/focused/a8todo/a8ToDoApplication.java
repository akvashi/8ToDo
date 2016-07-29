package com.akvashi.focused.a8todo;

import android.app.Application;

import com.akvashi.focused.a8todo.injector.component.ApplicationComponent;
import com.akvashi.focused.a8todo.injector.component.DaggerApplicationComponent;
import com.akvashi.focused.a8todo.injector.module.ApplicationModule;

/**
 * Created by akvashi on 7/11/16.
 */
public class a8ToDoApplication extends Application {
    private ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        setupInjector();
    }

    private void setupInjector() {
        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }

    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }
}
