package com.ashutosh.grab.ui.detail;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.ashutosh.grab.R;
import com.ashutosh.grab.base.BaseFragment;

import butterknife.BindView;

import static com.ashutosh.grab.ui.list.ListFragment.URL_TO_LOAD;

public class DetailsFragment extends BaseFragment {

    @BindView(R.id.repo_description)
    WebView webView;

    @Override
    protected int layoutRes() {
        return R.layout.screen_details;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        View v = super.onCreateView(inflater, container, savedInstanceState);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setAppCacheMaxSize(1024*1024*16);
        webSettings.setAppCachePath(getActivity().getApplicationContext().getCacheDir().getAbsolutePath());
        webSettings.setAppCacheEnabled(true);
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        webView.setWebViewClient(new WebViewClient());
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        displayRepo();
    }

    private void displayRepo() {
        String url = getArguments().getString(URL_TO_LOAD);
        webView.loadUrl(url);
    }
}
