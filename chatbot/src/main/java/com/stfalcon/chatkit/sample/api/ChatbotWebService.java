package com.stfalcon.chatkit.sample.api;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ChatbotWebService {

    private static ChatbotWebService mInstance;
    private static final String BASE_URL = "http://167.71.55.24";
    private Retrofit mRetrofit;

    private ChatbotWebService() {

        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(120 , TimeUnit.SECONDS)
                .readTimeout(120,TimeUnit.SECONDS).build();

        mRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(client)
                .build();

    }

    public static ChatbotWebService getInstance() {
        if (mInstance == null) {
            mInstance = new ChatbotWebService();
        }
        return mInstance;
    }

    public ChatbotAPI getChatbotAPI() {
        return mRetrofit.create(ChatbotAPI.class);
    }
}
