package me.hassans.www.popularmovies.api;

import com.squareup.okhttp.HttpUrl;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;

import java.io.IOException;

import me.hassans.www.popularmovies.BuildConfig;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

/**
 * Created by hassan on 11/15/2015.
 */
public class ServiceGenerator {


    private static OkHttpClient okHttpClient     = new OkHttpClient();
    public static final String HTTP_API_THEMOVIEDB_ORG = "http://api.themoviedb.org";
    private static Retrofit.Builder builder = new Retrofit.Builder().baseUrl(HTTP_API_THEMOVIEDB_ORG).addConverterFactory(GsonConverterFactory.create());

    public static <S> S getMoviesService(Class<S> serviceClass){
        final String API_KEY_QUERY_PARAM = "api_key";
        okHttpClient.interceptors().clear();
        okHttpClient.interceptors().add(new Interceptor() {
            @Override
            public com.squareup.okhttp.Response intercept(Chain chain) throws IOException {
                HttpUrl url = chain.request().httpUrl().newBuilder().addQueryParameter(API_KEY_QUERY_PARAM, BuildConfig.MOVIES_API_KEY).build();
                Request request = chain.request().newBuilder().url(url).build();
                return chain.proceed(request);

            }
        });
        Retrofit retrofit = builder.client(okHttpClient).build();
        return retrofit.create(serviceClass);
    }
}
