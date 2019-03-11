package com.ashutosh.grab.di.module;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import com.ashutosh.grab.ui.main.MainActivity;
import com.ashutosh.grab.ui.main.MainFragmentBindingModule;

@Module
public abstract class ActivityBindingModule {

    @ContributesAndroidInjector(modules = {MainFragmentBindingModule.class})
    abstract MainActivity bindMainActivity();
}
