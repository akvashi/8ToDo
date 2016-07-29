package com.akvashi.focused.a8todo.injector.module;

import com.akvashi.focused.a8todo.domain.FetchTasksUsecase;
import com.akvashi.focused.a8todo.injector.scope.PerActivity;
import com.akvashi.focused.a8todo.mvp.presenter.ToDoPresenter;
import com.akvashi.focused.a8todo.network.Repository;

import dagger.Module;
import dagger.Provides;

@PerActivity
@Module
public class ToDoModule {

    @PerActivity
    @Provides
    public FetchTasksUsecase provideToDoTaskUsecase(Repository repository) {
        return new FetchTasksUsecase(repository);
    }

    @PerActivity
    @Provides
    public ToDoPresenter provideToDoPresneter(FetchTasksUsecase usecase) {
        return new ToDoPresenter(usecase);
    }

}

