package co.uk.babylon.di.module;

import co.uk.babylon.ui.main.MainActivity;
import co.uk.babylon.ui.main.MainFragmentBindingModule;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBindingModule {

    @ContributesAndroidInjector(modules = {MainFragmentBindingModule.class})
    abstract MainActivity bindMainActivity();
}
