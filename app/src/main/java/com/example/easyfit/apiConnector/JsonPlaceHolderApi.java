package com.example.easyfit.apiConnector;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface JsonPlaceHolderApi {

    @GET("/simpleProducts")
    Call<List<SimpleProduct>> getSimpleProducts();

    @GET("/complexMeals")
    Call<List<ComplexMeal>> getComplexMeals();

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
    Call<String>saveEatenMeals(@Body EatenProductsWrapper wrapper);

    @POST("/simpleProducts")
    Call<ID>saveSimpleProduct(@Body NewSimpleProduct newSimpleProduct);

    @POST("/complexMeals")
    Call<ID>saveComplexMeal(@Body NewComplexMeal newComplexMeal);

    @POST("/login")
    Call<UserId>logIn(@Body User user);

    @POST("/register")
    Call<UserId>register(@Body User user);

    @GET("/goals/{userId}")
    Call<Goals>getGoals(@Path("userId") int userId);

    @PUT("/goals/{userId}")
    Call<Void>updateGoals(@Path("userId") int userId, @Body Goals goals);

    @GET("/notifications/{userId}")
    Call<List<String>>getNotifications(@Path("userId") int userId);

    @POST("/notifications/{userId}")
    Call<Void>addNotification(@Path("userId") int userId, @Body Notification notification);

    @HTTP(method = "DELETE", path = "/notifications/{userId}", hasBody = true)
    Call<Void>deleteNotification(@Path("userId") int userId, @Body Notification notification);

    @POST("/eatenComplexMeal")
    Call<Void>saveEatenComplexMeal(@Body NewEatenComplexMeal meal);

    @GET("/complexMealDetailed/{id}")
    Call<ComplexMealDetailed>getComplexMealDetailed(@Path("id") int id);

    @GET("/eatenMeals/{userId}")
    Call<List<HistoryItem>>getHistory(@Path("userId") int userId);


}
