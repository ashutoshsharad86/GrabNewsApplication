package com.ashutosh.grab.data.rest;

import com.ashutosh.grab.data.model.NewsResult;

import javax.inject.Inject;

import io.reactivex.Single;

public class ArticleRepository {

    private final WebService webService;
    private final String API_KEY = "ba9f98fcff244983a0034eb1cefba9ab";

    @Inject
    public ArticleRepository(WebService webService) {
        this.webService = webService;
    }

    public Single<NewsResult> getNewsItems(String owner) {
        return webService.getNewsItems(owner, API_KEY);
    }
}
