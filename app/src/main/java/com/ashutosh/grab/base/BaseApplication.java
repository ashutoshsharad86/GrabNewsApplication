package com.ashutosh.grab.base;

import com.ashutosh.grab.di.component.ApplicationComponent;
import com.ashutosh.grab.di.component.DaggerApplicationComponent;

import dagger.android.AndroidInjector;
import dagger.android.support.DaggerApplication;

public class BaseApplication extends DaggerApplication {


    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        ApplicationComponent component = DaggerApplicationComponent.builder().
                application(this).
                build();
        component.inject(this);

        return component;
    }
}
