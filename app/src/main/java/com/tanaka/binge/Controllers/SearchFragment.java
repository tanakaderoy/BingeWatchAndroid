package com.tanaka.binge.Controllers;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tanaka.binge.ApiInterface;
import com.tanaka.binge.Models.SearchResultModel;
import com.tanaka.binge.Models.TvShowResult;
import com.tanaka.binge.R;
import com.tanaka.binge.Views.HomeAdapter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

/**
 * Created by Tanaka Mazi on 2019-10-20.
 * Copyright (c) 2019 All rights reserved.
 */
public class SearchFragment extends Fragment implements SearchView.OnQueryTextListener {
    ApiInterface apiInterface;
    Retrofit retrofit;
    OkHttpClient client;
    private RecyclerView recyclerView;
    private HomeAdapter adapter;
    private ProgressDialog progressDialog;
    private ArrayList<TvShowResult> showResultList;
    private LinearLayoutManager linearLayoutManager;
    private TextView prompTextView;

    public SearchFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.search_fragment, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        recyclerView = view.findViewById(R.id.searchRecyclerView);
        linearLayoutManager = new LinearLayoutManager(view.getContext());

        showResultList = new ArrayList<>();
        adapter = new HomeAdapter(showResultList);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
        progressDialog = new ProgressDialog(view.getContext());
        progressDialog.setMessage("Loading....");
        prompTextView = view.findViewById(R.id.promptTextView);
        prompTextView.setVisibility(View.VISIBLE);

        setUpRetrofit();
        System.out.println("about to fetch data");

        System.out.println(showResultList);


    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        getActivity().getMenuInflater().inflate(R.menu.search_menu_items, menu);
        MenuItem item = menu.findItem(R.id.app_bar_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(this);

        MenuItemCompat.setOnActionExpandListener(item, new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {

                prompTextView.setVisibility(View.INVISIBLE);


                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                if(showResultList.isEmpty()) {
                    prompTextView.setVisibility(View.VISIBLE);
                }
                return true;
            }
        });


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

    private void searchForShow(String showName) {
        apiInterface.searchForTMDBShow(showName).enqueue(new Callback<SearchResultModel>() {
            @Override
            public void onResponse(Call<SearchResultModel> call, Response<SearchResultModel> response) {
                adapter.clear();
                adapter.addAll((ArrayList<TvShowResult>) response.body().getResults());
                progressDialog.dismiss();

            }

            @Override
            public void onFailure(Call<SearchResultModel> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getContext(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void generateDataList(List<TvShowResult> tvShowResults) {
//adapter.setShowID
        showResultList.addAll(tvShowResults);
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        showResultList.clear();
        adapter.notifyDataSetChanged();
        System.out.print(query);
        if (!query.isEmpty()) {
            searchForShow(query);
        }
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if(newText.isEmpty()){

            prompTextView.setVisibility(View.VISIBLE);
            showResultList.clear();

        }
        prompTextView.setVisibility(View.INVISIBLE);
        System.out.println(newText);
        return false;
    }
}
