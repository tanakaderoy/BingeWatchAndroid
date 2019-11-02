package com.tanaka.binge;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.tanaka.binge.Controllers.TMDBAPI;
import com.tanaka.binge.Models.TrendingResponseModel;
import com.tanaka.binge.Models.TvShowResult;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TvShowRepository {
    private LiveData<List<TvShowResult>> allTvShows;
    private ApiInterface apiInterface = TMDBAPI.getInstance.getApiInterface();

    public LiveData<List<TvShowResult>> getAllTvShows(int page) {
        final MutableLiveData<List<TvShowResult>> data = new MutableLiveData<>();
        apiInterface.getTrendingShows(page).enqueue(new Callback<TrendingResponseModel>() {
            @Override
            public void onResponse(Call<TrendingResponseModel> call, Response<TrendingResponseModel> response) {
                data.setValue(response.body().getTvShowResults());
            }

            @Override
            public void onFailure(Call<TrendingResponseModel> call, Throwable t) {
                t.printStackTrace();
            }
        });


        return data;
    }


}
