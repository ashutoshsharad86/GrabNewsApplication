package com.ashutosh.grab.di.module;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.ashutosh.grab.data.rest.WebService;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module(includes = {ViewModelModule.class, ContextModule.class})
public class ApplicationModule {

    private static final String BASE_URL = "https://newsapi.org/v2/";

    private static Cache cache = null;

    static Cache getCache(Context applicationContext) {
        if (cache == null) {
            cache = new Cache(applicationContext.getApplicationContext().getCacheDir(),
                    10 * 1024 * 1024);
        }
        return cache;
    }

    @Inject
    @Provides
    @Singleton
    Retrofit provideRetrofit(Context context) {

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder()
                .cache(getCache(context.getApplicationContext()))
                .addInterceptor(logging)
                .addNetworkInterceptor(provideCacheInterceptor())
                .addInterceptor(provideOfflineCacheInterceptor(context));
        return new Retrofit.Builder().baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();
    }

    @Provides
    @Singleton
    WebService provideRetrofitService(Retrofit retrofit) {
        return retrofit.create(WebService.class);
    }

    private Interceptor provideCacheInterceptor() {

        return chain -> {
            okhttp3.Response originalResponse = chain.proceed(chain.request());
            String cacheControl = originalResponse.header("Cache-Control");
            if (cacheControl == null || cacheControl.contains("no-store") || cacheControl.contains("no-cache") ||
                    cacheControl.contains("must-revalidate") || cacheControl.contains("max-age=0")) {
                return originalResponse.newBuilder()
                        .removeHeader("Pragma")
                        .header("Cache-Control", "public, max-age=" + 5000)
                        .build();
            } else {
                return originalResponse;
            }
        };
    }


    private Interceptor provideOfflineCacheInterceptor(Context context) {

        return chain -> {
            Request request = chain.request();
            if (!isOnline(context.getApplicationContext())) {
                request = request.newBuilder()
                        .removeHeader("Pragma")
                        .header("Cache-Control", "public, only-if-cached")
                        .build();
            }
            return chain.proceed(request);
        };
    }

    private static ConnectivityManager connectivityManager;

    static boolean isOnline(Context context) {
        boolean connected = false;
        try {
            connectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);

            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            connected = networkInfo != null && networkInfo.isAvailable() &&
                    networkInfo.isConnected();
            return connected;

        } catch (Exception e) {
            return false;
        }
    }
}
