package com.tanaka.binge.Controllers;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.tanaka.binge.Models.TvShowResult;
import com.tanaka.binge.TvShowRepository;

import java.util.List;

public class TvShowViewModel extends AndroidViewModel {
    private TvShowRepository tvShowRepository;
    private LiveData<List<TvShowResult>> allTvShows;


    public TvShowViewModel(@NonNull Application application) {
        super(application);
        tvShowRepository = new TvShowRepository();
        allTvShows = tvShowRepository.getAllTvShows(1);

    }

    public LiveData<List<TvShowResult>> getAllTvShows(int page) {
        return tvShowRepository.getAllTvShows(page);
    }

}
