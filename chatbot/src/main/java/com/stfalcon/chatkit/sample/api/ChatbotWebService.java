package com.stfalcon.chatkit.sample.api;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ChatbotWebService {

    private static ChatbotWebService mInstance;
    private static final String BASE_URL = "http://142.93.134.15/";
    private Retrofit mRetrofit;

    private ChatbotWebService() {
        mRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
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
