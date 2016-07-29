package com.akvashi.focused.a8todo.injector.module;

import android.app.Application;

import com.akvashi.focused.a8todo.a8ToDoApplication;
import com.akvashi.focused.a8todo.injector.scope.PerApplication;

import dagger.Module;
import dagger.Provides;
@Module
public class ApplicationModule {
    private final a8ToDoApplication application;

    public ApplicationModule(a8ToDoApplication application) {
        this.application = application;
    }

    @Provides
    @PerApplication
    public a8ToDoApplication provideMvpApplication() {
        return application;
    }

    @Provides
    @PerApplication
    public Application provideApplication() {return application; }

}
