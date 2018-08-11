package com.friendfill.foodifie.network;

/**
 * Created by FriendFill on 30-Jan-18.
 */

import com.friendfill.foodifie.model.BillStatus;
import com.friendfill.foodifie.model.Cart;
import com.friendfill.foodifie.model.CartItem;
import com.friendfill.foodifie.model.Item;
import com.friendfill.foodifie.model.Login;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface APIInterface {

    @POST("Admin_user/login/")
    Call<ResponseBody> Login(@Header("Content-Type") String content_type, @Body Login jsonObject);

    @PUT("bill/add_item/")
    Call<ResponseBody> AddItemToBill(@Header("Content-Type") String content_type, @Body CartItem jsonObject);

    @PUT("bill/add_item_table/")
    Call<ResponseBody> AddItemToTable(@Header("Content-Type") String content_type, @Body CartItem jsonObject);

    @PUT("bill/create/")
    Call<ResponseBody> AddItemToBill(@Header("Content-Type") String content_type, @Body Cart jsonObject);

    @PUT("bill/update/")
    Call<ResponseBody> UpdateBill(@Header("Content-Type") String content_type, @Body Cart jsonObject);

    @PUT("bill/bill_status/")
    Call<ResponseBody> ChangeBillStatus(@Header("Content-Type") String content_type, @Body BillStatus jsonObject);

    @PUT("bill/update_item/")
    Call<ResponseBody> UpdateItemToBill(@Header("Content-Type") String content_type, @Body CartItem jsonObject);

    @PUT("bill/review/")
    Call<ResponseBody> SaveReview(@Header("Content-Type") String content_type, @Body ArrayList<Item> jsonObject);


    @GET("item/category/")
    Call<ResponseBody> Category(@Header("Content-Type") String content_type);

    @GET("Admin_user/")
    Call<ResponseBody> Staff(@Header("Content-Type") String content_type);


    @GET("item/category_item/{category_id}/{page}/{limit}")
    Call<ResponseBody> CategoryItem(@Header("Content-Type") String content_type, @Path("category_id") String category_id, @Path("page") String page, @Path("limit") String limit);

    @GET("item/{query}/{page}/{limit}")
    Call<ResponseBody> SearchItem(@Header("Content-Type") String content_type, @Path("query") String query, @Path("page") String page, @Path("limit") String limit);

    @GET("table/{page}/{limit}")
    Call<ResponseBody> Table(@Header("Content-Type") String content_type, @Path("page") String page, @Path("limit") String limit);

    @GET("bill/{page}/{limit}")
    Call<ResponseBody> Bill(@Header("Content-Type") String content_type, @Path("page") String page, @Path("limit") String limit);

    @GET("bill/detail/{id}")
    Call<ResponseBody> BillDetail(@Header("Content-Type") String content_type, @Path("id") String id);

    @GET("item/review/{id}")
    Call<ResponseBody> GetReview(@Header("Content-Type") String content_type, @Path("id") String id);

}