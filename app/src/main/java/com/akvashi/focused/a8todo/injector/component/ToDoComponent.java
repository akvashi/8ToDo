package com.akvashi.focused.a8todo.injector.component;

import android.content.Context;

import com.akvashi.focused.a8todo.fragment.ToDoFragment;
import com.akvashi.focused.a8todo.injector.module.ActivityModule;
import com.akvashi.focused.a8todo.injector.module.ToDoModule;
import com.akvashi.focused.a8todo.injector.scope.PerActivity;

import dagger.Component;

@PerActivity
@Component (dependencies = ApplicationComponent.class,
            modules = {
                ActivityModule.class,
                ToDoModule.class
            })
public interface ToDoComponent {
    void inject(ToDoFragment toDoFragment);
    Context context();
}