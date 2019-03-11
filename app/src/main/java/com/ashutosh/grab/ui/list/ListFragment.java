package com.ashutosh.grab.ui.list;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.ashutosh.grab.R;
import com.ashutosh.grab.base.BaseFragment;
import com.ashutosh.grab.data.model.Article;
import com.ashutosh.grab.ui.detail.DetailsFragment;
import com.ashutosh.grab.util.ViewModelFactory;

import javax.inject.Inject;

import butterknife.BindView;

public class ListFragment extends BaseFragment implements ArticleSelectedListener {

    public static final String URL_TO_LOAD = "urlToLoad";
    @BindView(R.id.recyclerView) RecyclerView listView;
    @BindView(R.id.tv_error) TextView errorTextView;
    @BindView(R.id.loading_view) View loadingView;

    @Inject
    ViewModelFactory viewModelFactory;
    private ListViewModel viewModel;

    @Override
    protected int layoutRes() {
        return R.layout.screen_list;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(ListViewModel.class);
        listView.addItemDecoration(new DividerItemDecoration(getBaseActivity(), DividerItemDecoration.VERTICAL));
        listView.setAdapter(new ArticleListAdapter(viewModel, this, this));
        listView.setLayoutManager(new LinearLayoutManager(getContext()));
        observableViewModel();
    }

    @Override
    public void onArticleSelected(Article article) {
        Bundle args = new Bundle();
        args.putString(URL_TO_LOAD, article.url);
        DetailsFragment fragment = new DetailsFragment();
        fragment.setArguments(args);
        getBaseActivity().getSupportFragmentManager().beginTransaction().replace(R.id
                .screenContainer, fragment)
                .addToBackStack(null).commit();
    }

    private void observableViewModel() {
        viewModel.getNewsResult().observe(this, repos -> {
            if(repos != null) listView.setVisibility(View.VISIBLE);
        });

        viewModel.isNewsLoadError().observe(this, isError -> {
            if (isError != null) if(isError) {
                errorTextView.setVisibility(View.VISIBLE);
                listView.setVisibility(View.GONE);
                errorTextView.setText("An Error Occurred While Loading Data!");
            }else {
                errorTextView.setVisibility(View.GONE);
                errorTextView.setText(null);
            }
        });

        viewModel.isNewsLoading().observe(this, isLoading -> {
            if (isLoading != null) {
                loadingView.setVisibility(isLoading ? View.VISIBLE : View.GONE);
                if (isLoading) {
                    errorTextView.setVisibility(View.GONE);
                    listView.setVisibility(View.GONE);
                }
            }
        });
    }
}
