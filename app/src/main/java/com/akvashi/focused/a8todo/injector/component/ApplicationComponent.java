package com.akvashi.focused.a8todo.injector.component;

import android.app.Application;

import com.akvashi.focused.a8todo.a8ToDoApplication;
import com.akvashi.focused.a8todo.injector.module.ApplicationModule;
import com.akvashi.focused.a8todo.injector.module.NetworkModule;
import com.akvashi.focused.a8todo.injector.scope.PerApplication;
import com.akvashi.focused.a8todo.network.Repository;

import dagger.Component;

@PerApplication
@Component(modules = {
            ApplicationModule.class,
            NetworkModule.class
        })

public interface ApplicationComponent {
    Application application();
    a8ToDoApplication a8ToDoApplication();
    Repository repository();
}
