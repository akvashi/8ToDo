package com.akvashi.focused.a8todo.injector.component;

import com.akvashi.focused.a8todo.activity.MainActivity;
import com.akvashi.focused.a8todo.fragment.ToDoFragment;
import com.akvashi.focused.a8todo.injector.module.ActivityModule;
import com.akvashi.focused.a8todo.injector.module.ToDoModule;
import com.akvashi.focused.a8todo.injector.scope.PerActivity;

import dagger.Component;

/**
 * Created by akvashi on 7/28/16.
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class,
        modules = {ActivityModule.class,
                ToDoModule.class}
)
public interface MainComponent {
    void inject(MainActivity activity);
    void inject(ToDoFragment activity);
}
