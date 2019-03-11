package com.ashutosh.grab.ui.list;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.widget.ListView;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

import com.ashutosh.grab.data.model.NewsResult;
import com.ashutosh.grab.data.rest.ArticleRepository;

public class ListViewModel extends ViewModel {

    private ArticleRepository articleRepository;
    private CompositeDisposable disposable;

    private final MutableLiveData<NewsResult> newsResult = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isNewsLoadError = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isNewsLoading = new MutableLiveData<>();

    @Inject
    public ListViewModel(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
        disposable = new CompositeDisposable();
        fetchRepos();
    }

    LiveData<NewsResult> getNewsResult() {
        return newsResult;
    }
    LiveData<Boolean> isNewsLoadError() {
        return isNewsLoadError;
    }
    LiveData<Boolean> isNewsLoading() {
        return isNewsLoading;
    }

    private void fetchRepos() {
        isNewsLoading.setValue(true);
        disposable.add(articleRepository.getNewsItems("us").subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribeWith(new DisposableSingleObserver<NewsResult>() {
                    @Override
                    public void onSuccess(NewsResult value) {
                        isNewsLoadError.setValue(false);
                        newsResult.setValue(value);
                        isNewsLoading.setValue(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        isNewsLoadError.setValue(true);
                        isNewsLoading.setValue(false);
                    }
                }));
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        if (disposable != null) {
            disposable.clear();
            disposable = null;
        }
    }
}
