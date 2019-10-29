package com.tanaka.binge.Controllers;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.tanaka.binge.ApiInterface;
import com.tanaka.binge.Models.Season;
import com.tanaka.binge.Models.ShowResponseModel;
import com.tanaka.binge.R;
import com.tanaka.binge.Views.SeasonAdapter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ShowActivity extends AppCompatActivity {
    ImageView showBackdropImageView;
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    SeasonAdapter adapter;
    ArrayList<Season> seasonsList;
    ApiInterface apiInterface;
    Retrofit retrofit;
    OkHttpClient client;
    String apiKey = "a5968df59cc3e43725bcb8d5a89aa34c";
    int showID = 0;
    String showName = "";
    String backdropURL;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);
        int color = getResources().getColor(R.color.colorPrimary);
        String htmlColor = String.format(Locale.US, "#%06X", (0xFFFFFF & Color.argb(0, Color.red(color), Color.green(color), Color.blue(color))));


        Intent intent = getIntent();
        showName = intent.getStringExtra("showName");
        showID = intent.getIntExtra("showID", 0);
        showBackdropImageView = findViewById(R.id.showBackDropImageView);
        backdropURL = intent.getStringExtra("backdropImage");
//        Picasso.get().load(backdropURL).fit().into(showBackdropImageView);
        Glide.with(this)
                .load(backdropURL).thumbnail(Glide.with(this).load(R.drawable.loading))
                .into(showBackdropImageView);
        recyclerView = findViewById(R.id.seasonsRecyclerView);
        linearLayoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);

        seasonsList = new ArrayList<>();
        adapter = new SeasonAdapter(seasonsList);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
        setTitle(Html.fromHtml("<font color=" + htmlColor + ">" + showName + "</font>"));
        setupRetrofit();
        fetchShow();

    }

    private void fetchShow() {
        apiInterface.getShow(showID).enqueue(new Callback<ShowResponseModel>() {
            @Override
            public void onResponse(Call<ShowResponseModel> call, Response<ShowResponseModel> response) {
                System.out.println(call.request());
                generateSeasonList(response.body().getSeasons());
            }

            @Override
            public void onFailure(Call<ShowResponseModel> call, Throwable t) {
                Toast.makeText(ShowActivity.this, "Something went wrong: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void generateSeasonList(List<Season> seasons) {
        adapter.setShowInfo(showID, showName);
        seasonsList.addAll(seasons);
        adapter.notifyDataSetChanged();
    }

    private void setupRetrofit() {
        client = new OkHttpClient().newBuilder().addInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                HttpUrl url = request.url().newBuilder().addQueryParameter("api_key", apiKey).build();
                request = request.newBuilder().url(url).build();
                return chain.proceed(request);
            }
        }).build();


        retrofit = new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/3/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        apiInterface = retrofit.create(ApiInterface.class);
    }

    public void pickForMe(View view) {
        Random generator = new Random();
        int randomIndex = generator.nextInt(seasonsList.size());
        Season season = seasonsList.get(randomIndex);
        int randomEpisode = generator.nextInt(season.getEpisodeCount());
        Intent intent = new Intent(getApplicationContext(), EpisodeInfoActivity.class);
        RecyclerView.SmoothScroller smoothScroller = new
                LinearSmoothScroller(this) {
                    @Override
                    protected int getVerticalSnapPreference() {
                        return LinearSmoothScroller.SNAP_TO_START;
                    }
                };

            smoothScroller.setTargetPosition(randomIndex);
            linearLayoutManager.startSmoothScroll(smoothScroller);
        intent.putExtra("backdropImage", backdropURL);
        intent.putExtra("showID", showID);
        intent.putExtra("seasonNumber", season.getSeasonNumber());
        intent.putExtra("seasonName", season.getName());
        intent.putExtra("showName", showName);
        intent.putExtra("episodeNumber", randomEpisode);
        startActivityForResult(intent, 1);

        //if let season = seasons.randomElement(), let seasonNumber = season.seasonNumber, let episodeCount = season.episodeCount{
        //            randomEpisode = String(arc4random_uniform(UInt32(episodeCount)) + 1)
        //            sentSeasonNo = String(seasonNumber)
        //
        //
        //
        //            if #available(iOS 13.0, *) {
        //                let vc =  UIStoryboard(name: "Main", bundle: nil).instantiateViewController(identifier: "seasonInfo") as! SeasonInfoViewController
        //                vc.id = String(self.id!)
        //                vc.showName = showName
        //                            vc.seasonNumber = sentSeasonNo
        //                            vc.episode = randomEpisode
        //                navigationController?.pushViewController(vc, animated: true)
        //            } else {
        //                // Fallback on earlier versions
        //                let vc = storyboard?.instantiateViewController(withIdentifier: "seasonInfo") as! SeasonInfoViewController
        //                vc.id = String(self.id!)
        //                vc.showName = showName
        //                vc.seasonNumber = sentSeasonNo
        //                vc.episode = randomEpisode
        //                navigationController?.pushViewController(vc, animated: true)
        //            }
    }
}
