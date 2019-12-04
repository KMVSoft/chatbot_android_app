package com.stfalcon.chatkit.sample.api;

import com.stfalcon.chatkit.sample.api.model.Status;
import com.stfalcon.chatkit.sample.api.model.Token;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ChatbotAPI {


    @GET("get_new_token")
    Token getNewToken();

    @POST("registration")
    Call<Status> registration(@Query("login") String login, @Query("login") String password);

    //        Product getProduct(long productId);
    //
    //        List<Order> getOrders();
    //
    //        Order getOrder(long orderId);
    //
    //        @POST("createOrder")
    //        void createOrder(Order order);

}
