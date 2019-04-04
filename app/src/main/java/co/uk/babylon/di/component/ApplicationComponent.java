package co.uk.babylon.di.component;

import android.app.Application;

import javax.inject.Singleton;

import co.uk.babylon.base.BaseApplication;
import co.uk.babylon.di.module.ActivityBindingModule;
import co.uk.babylon.di.module.ApplicationModule;
import co.uk.babylon.di.module.ContextModule;
import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;
import dagger.android.support.DaggerApplication;

@Singleton
@Component(modules = {ContextModule.class, ApplicationModule.class, AndroidSupportInjectionModule.class, ActivityBindingModule.class})
public interface ApplicationComponent extends AndroidInjector<DaggerApplication> {

    void inject(BaseApplication application);

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(Application application);

        ApplicationComponent build();
    }
}