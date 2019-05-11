package com.example.easyfit.apiConnector;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface JsonPlaceHolderApi {

    @GET("/simpleProducts")
    Call<List<SimpleProduct>> getSimpleProducts();

    @GET("/EatenMeals/{userId}/{year}/{month}/{day}")
    Call<EatenMeals> getEatenMeals(
            @Path("userId") int userId,
            @Path("year") int year,
            @Path("month") int month,
            @Path("day") int day);

    @GET("/EatenMealsDetailed/{userId}/{year}/{month}/{day}")
    Call<List<EatenMealDetailed>> getEatenMealsDetailed(
            @Path("userId") int userId,
            @Path("year") int year,
            @Path("month") int month,
            @Path("day") int day);

    @POST("/eatenMeals")
    Call<String>postEatenMeals(@Body EatenProductsWrapper wrapper);

}
