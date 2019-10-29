package com.tanaka.binge;
import com.tanaka.binge.Models.Episode;
import com.tanaka.binge.Models.JWBody;
import com.tanaka.binge.Models.JWProvider;
import com.tanaka.binge.Models.JWSeason;
import com.tanaka.binge.Models.JWShowResponseModel;
import com.tanaka.binge.Models.Result;
import com.tanaka.binge.Models.SearchResultModel;
import com.tanaka.binge.Models.ShowResponseModel;
import com.tanaka.binge.Models.TrendingResponseModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {


    @GET("trending/tv/week")
    Call<TrendingResponseModel> getTrendingShows(@Query("page") int page);

    @GET("tv/{id}")
    Call<ShowResponseModel> getShow(@Path("id") int id);

    @GET("search/tv")
    Call<SearchResultModel>searchForTMDBShow(@Query("query") String show);



@GET("tv/{id}/season/{seasonNo}")
Call<JWSeason> getSeasonInfo(@Path("id") String id, @Path("seasonNo") String seasonNo);



    @Headers("Content-Type: application/json")
    @POST("content/titles/en_US/popular")
    Call<JWShowResponseModel>searchForShow(@Body JWBody body);

    @GET("content/providers/locale/en_US")
    Call<List<JWProvider>> getProviders();

}