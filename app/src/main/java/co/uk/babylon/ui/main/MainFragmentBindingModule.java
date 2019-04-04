package co.uk.babylon.ui.main;

import co.uk.babylon.ui.detail.DetailsFragment;
import co.uk.babylon.ui.list.ListFragment;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class MainFragmentBindingModule {

    @ContributesAndroidInjector
    abstract ListFragment provideListFragment();

    @ContributesAndroidInjector
    abstract DetailsFragment provideDetailsFragment();
}
