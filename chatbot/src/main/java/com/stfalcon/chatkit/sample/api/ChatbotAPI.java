package com.stfalcon.chatkit.sample.api;

import com.stfalcon.chatkit.sample.api.model.MessageAPI;
import com.stfalcon.chatkit.sample.api.model.SentMessage;
import com.stfalcon.chatkit.sample.api.model.Status;
import com.stfalcon.chatkit.sample.api.model.Token;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ChatbotAPI {

    @GET("/get_token")
    Call<Token> login(@Query("login") String login, @Query("password") String password);

    @POST("/registration")
    Call<Status> registration(@Query("login") String login, @Query("password") String password);

    @GET("/receive_message")
    Call<List<MessageAPI>> receiveMessage(@Header("Authorization") String token, @Query("after_msg_id") int afterId);

    @POST("/send_message")
    Call<MessageAPI> sendMessage(@Header("Authorization") String token, @Query("message") String message);


}
