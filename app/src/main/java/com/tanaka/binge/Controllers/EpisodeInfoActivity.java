package com.tanaka.binge.Controllers;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.RecyclerView;

import com.tanaka.binge.ApiInterface;
import com.tanaka.binge.Models.Episode;
import com.tanaka.binge.Models.JWSeason;
import com.tanaka.binge.R;
import com.tanaka.binge.Views.EpisodeAdapter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class EpisodeInfoActivity extends AppCompatActivity {
    RecyclerView episodeRecyclerView;
    EpisodeAdapter adapter;
    int episodeNumber = 0;
    private String backdropURL;
    private int showID;
    private String seasonName;
    private String showName;
    private int seasonNumber;
    private ApiInterface apiInterface;
    private Retrofit retrofit;
    private ProgressDialog progressDialog;
    private OkHttpClient client;
    private ArrayList<Episode> episodes;
    private LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_episode_info);
        setupIntentData();
        setUpRecyclerView();
        setUpTitleAndProgressBar();
        setUpRetrofit();
        fetchData();


    }

    private void setUpTitleAndProgressBar() {
        int color = getResources().getColor(R.color.colorPrimary);
        String htmlColor = String.format(Locale.US, "#%06X", (0xFFFFFF & Color.argb(0, Color.red(color), Color.green(color), Color.blue(color))));
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading....");

        setTitle(Html.fromHtml("<font color=" + htmlColor + ">" + showName + ": " + seasonName + "</font>"));
    }

    private void setUpRecyclerView() {
        episodeRecyclerView = findViewById(R.id.episodeRecyclerView);
        episodes = new ArrayList<>();
        adapter = new EpisodeAdapter(episodes);
        linearLayoutManager = new LinearLayoutManager(this);
        episodeRecyclerView.setLayoutManager(linearLayoutManager);
        episodeRecyclerView.setAdapter(adapter);
    }

    private void setupIntentData() {
        showID = getIntent().getIntExtra("showID", 0);
        showName = getIntent().getStringExtra("showName");
        seasonNumber = getIntent().getIntExtra("seasonNumber", 0);
        seasonName = getIntent().getStringExtra("seasonName");
        episodeNumber = getIntent().getIntExtra("episodeNumber", 0);
    }


    private void fetchData() {
        progressDialog.show();
        apiInterface.getSeasonInfo(String.valueOf(showID), String.valueOf(seasonNumber)).enqueue(new Callback<JWSeason>() {
            @Override
            public void onResponse(Call<JWSeason> call, Response<JWSeason> response) {
                JWSeason season = response.body();
                System.out.println(season);
                generateDataSet(response.body().getEpisodes());
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<JWSeason> call, Throwable t) {
                progressDialog.dismiss();
                t.printStackTrace();
            }
        });
    }

    private void generateDataSet(List<Episode> episodes) {
        this.episodes.addAll(episodes);
        adapter.notifyDataSetChanged();

        RecyclerView.SmoothScroller smoothScroller = new
                LinearSmoothScroller(this) {
                    @Override
                    protected int getVerticalSnapPreference() {
                        return LinearSmoothScroller.SNAP_TO_START;
                    }
                };
        if (episodeNumber != 0) {
            smoothScroller.setTargetPosition(episodeNumber);
            linearLayoutManager.startSmoothScroll(smoothScroller);

        }

    }


    private void setUpRetrofit() {
        client = new OkHttpClient().newBuilder().addInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                HttpUrl url = request.url().newBuilder().addQueryParameter("api_key", getString(R.string.api_key)).build();
                request = request.newBuilder().url(url).build();
                return chain.proceed(request);
            }
        }).addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)).build();


        retrofit = new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/3/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        apiInterface = retrofit.create(ApiInterface.class);
    }
}
