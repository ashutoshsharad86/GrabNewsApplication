package com.ashutosh.grab.ui.main;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import com.ashutosh.grab.ui.detail.DetailsFragment;
import com.ashutosh.grab.ui.list.ListFragment;

@Module
public abstract class MainFragmentBindingModule {

    @ContributesAndroidInjector
    abstract ListFragment provideListFragment();

    @ContributesAndroidInjector
    abstract DetailsFragment provideDetailsFragment();
}
