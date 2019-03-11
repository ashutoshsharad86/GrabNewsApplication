package com.ashutosh.grab.ui.main;

import android.os.Bundle;

import com.ashutosh.grab.R;
import com.ashutosh.grab.base.BaseActivity;
import com.ashutosh.grab.ui.list.ListFragment;

public class MainActivity extends BaseActivity {

    @Override
    protected int layoutRes() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState == null)
            getSupportFragmentManager().beginTransaction().add(R.id.screenContainer, new ListFragment()).commit();
    }
}
