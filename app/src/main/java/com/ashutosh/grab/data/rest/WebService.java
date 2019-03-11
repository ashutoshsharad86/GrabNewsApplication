package com.ashutosh.grab.data.rest;

import com.ashutosh.grab.data.model.NewsResult;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WebService {

    @GET("top-headlines")
    Single<NewsResult> getNewsItems(@Query("country") String country, @Query("apiKey") String apiKey);
}
