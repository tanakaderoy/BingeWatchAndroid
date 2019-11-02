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

import com.tanaka.binge.Models.SearchResultModel;
import com.tanaka.binge.Models.TvShowResult;
import com.tanaka.binge.R;
import com.tanaka.binge.Views.HomeAdapter;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Tanaka Mazi on 2019-10-20.
 * Copyright (c) 2019 All rights reserved.
 */
public class SearchFragment extends Fragment implements SearchView.OnQueryTextListener {
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
        linearLayoutManager = new LinearLayoutManager(getContext());
        showResultList = new ArrayList<>();
        adapter = new HomeAdapter(showResultList);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Loading....");
        prompTextView = view.findViewById(R.id.promptTextView);
        prompTextView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        getActivity().getMenuInflater().inflate(R.menu.search_menu_items, menu);
        MenuItem item = menu.findItem(R.id.app_bar_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(SearchFragment.this);

        MenuItemCompat.setOnActionExpandListener(item, new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {

                prompTextView.setVisibility(View.INVISIBLE);


                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                if (showResultList.isEmpty()) {
                    prompTextView.setVisibility(View.VISIBLE);
                }
                return true;
            }
        });


    }



    private void searchForShow(String showName) {
        adapter.setFavoritesList(HomeFragment.favoritesList);
        TMDBAPI.getInstance.getApiInterface().searchForTMDBShow(showName).enqueue(new Callback<SearchResultModel>() {
            @Override
            public void onResponse(Call<SearchResultModel> call, Response<SearchResultModel> response) {
                adapter.clear();
                ArrayList<TvShowResult> tvShowResults = new ArrayList<>();

                for (TvShowResult tvShow : response.body().getResults()) {
                        if (isValidTvShow(tvShow)) {
                            tvShowResults.add(tvShow);
                        }
                    }
                    adapter.addAll(tvShowResults);

                progressDialog.dismiss();

            }

            @Override
            public void onFailure(Call<SearchResultModel> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getContext(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private Boolean isValidTvShow(TvShowResult tvShow) {
        boolean b = tvShow.getName() != null && tvShow.getOverview() != null && tvShow.getBackdropPath() != null && tvShow.getPosterPath() != null;
        return b;

    }


    @Override
    public boolean onQueryTextSubmit(String query) {
        showResultList.clear();
        adapter.notifyDataSetChanged();
        System.out.print(query);
        if (!query.isEmpty()) {
            progressDialog.show();
            searchForShow(query);
        }
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if (newText.isEmpty()) {

            prompTextView.setVisibility(View.VISIBLE);
            showResultList.clear();

        }
        prompTextView.setVisibility(View.INVISIBLE);
        return false;
    }
}
