package com.tanaka.binge.Controllers;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.tanaka.binge.Models.TrendingResponseModel;
import com.tanaka.binge.Models.TvShowResult;
import com.tanaka.binge.PaginationScrollListener;
import com.tanaka.binge.R;
import com.tanaka.binge.Views.HomeAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Tanaka Mazi on 2019-10-20.
 * Copyright (c) 2019 All rights reserved.
 */
public class HomeFragment extends Fragment {
    public final static String GOOGLE_ACCOUNT = "googleAccount";
    private static final int PAGE_START = 1;
    static ArrayList<TvShowResult> favoritesList = new ArrayList<>();
    BottomNavigationView bottomNav;
    FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private RecyclerView recyclerView;
    private HomeAdapter adapter;
    private TvShowViewModel tvShowViewModel;

    private ProgressDialog progressDialog;

    private ArrayList<TvShowResult> showResultList;
    private LinearLayoutManager linearLayoutManager;
    private boolean isLoading = false;
    private boolean isLastPage = false;
    // limiting to 5 for this tutorial, since total pages in actual API is very large. Feel free to modify.
    private int TOTAL_PAGES = 10;
    private int currentPage = PAGE_START;
    private CollectionReference tvShowRef;

    public HomeFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        return inflater.inflate(R.layout.home_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        recyclerView = view.findViewById(R.id.homeRecyclerView);
        linearLayoutManager = new LinearLayoutManager(view.getContext());
        if (currentUser != null) {
            tvShowRef = db.collection("FavShows").document(currentUser.getEmail()).collection("TvShows");

        }

        showResultList = new ArrayList<>();
        adapter = new HomeAdapter(showResultList);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
        progressDialog = new ProgressDialog(view.getContext());
        progressDialog.setMessage("Loading....");
        if (currentUser != null) {
            loadFavorites();
        }

        System.out.println("about to fetch data");
        if (savedInstanceState == null) {
            progressDialog.show();

            fetchData();
        } else {
            adapter.addAll(savedInstanceState.<TvShowResult>getParcelableArrayList("TvShows"));
        }
        System.out.println(showResultList);
        recyclerView.addOnScrollListener(new PaginationScrollListener(linearLayoutManager) {
            @Override
            protected void loadMoreItems() {
                isLoading = true;
                currentPage += 1;
                if (currentUser != null) {
                    loadFavorites();
                }
//                progressDialog.show();
//                loadNextPage();
                tvShowViewModel.getAllTvShows(currentPage).observe(HomeFragment.this, new Observer<List<TvShowResult>>() {
                    @Override
                    public void onChanged(List<TvShowResult> tvShowResults) {
                        adapter.addAll((ArrayList<TvShowResult>) tvShowResults);
                    }
                });
            }

            @Override
            public int getTotalPageCount() {
                return TOTAL_PAGES;
            }

            @Override
            public boolean isLastPage() {
                return isLastPage;
            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }
        });

        tvShowViewModel = ViewModelProviders.of(this).get(TvShowViewModel.class);
        tvShowViewModel.getAllTvShows(currentPage).observe(this, new Observer<List<TvShowResult>>() {
            @Override
            public void onChanged(List<TvShowResult> tvShowResults) {
                adapter.addAll((ArrayList<TvShowResult>) tvShowResults);
            }
        });
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("TvShows", showResultList);
        Log.i("Saved", "Saved");
    }

    private void loadNextPage() {
        TMDBAPI.getInstance.getApiInterface().getTrendingShows(currentPage).enqueue(new Callback<TrendingResponseModel>() {
            @Override
            public void onResponse(Call<TrendingResponseModel> call, Response<TrendingResponseModel> response) {
                isLoading = false;

                System.out.println(response.body().getTotalResults());
                System.out.println("Fetched");
                TrendingResponseModel model = response.body();
                generateDataList(response.body().getTvShowResults());
                progressDialog.dismiss();
                if (!(currentPage != TOTAL_PAGES)) isLastPage = true;
            }

            @Override
            public void onFailure(Call<TrendingResponseModel> call, Throwable t) {
                System.out.println(t);
                progressDialog.dismiss();
                Toast.makeText(getContext(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }



    private void fetchData() {


        TMDBAPI.getInstance.getApiInterface().getTrendingShows(currentPage).enqueue(new Callback<TrendingResponseModel>() {
            @Override
            public void onResponse(Call<TrendingResponseModel> call, Response<TrendingResponseModel> response) {
                System.out.println(call.request());
                System.out.println(response.body());
                System.out.println("Fetched");
                TrendingResponseModel model = response.body();
                generateDataList(model.getTvShowResults());
                progressDialog.dismiss();
                if (!(currentPage <= TOTAL_PAGES)) isLastPage = true;
            }

            @Override
            public void onFailure(Call<TrendingResponseModel> call, Throwable t) {
                System.out.println(t);
                progressDialog.dismiss();
                Toast.makeText(getContext(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        if (currentUser != null) {
            tvShowRef = db.collection("FavShows").document(currentUser.getEmail()).collection("TvShows");


            tvShowRef.addSnapshotListener(new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                    if (e != null) {
                        return;
                    }

                    adapter.setFavoritesList((ArrayList<TvShowResult>) queryDocumentSnapshots.toObjects(TvShowResult.class));
                    adapter.notifyDataSetChanged();

                }
            });

        }
    }

    private void loadFavorites() {
        progressDialog.show();
        tvShowRef.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                    TvShowResult favShow = documentSnapshot.toObject(TvShowResult.class);
                    favoritesList.add(favShow);
                }
                adapter.setFavoritesList(favoritesList);
                adapter.notifyDataSetChanged();
                progressDialog.dismiss();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });

    }


    private void generateDataList(List<TvShowResult> tvShowResults) {

        showResultList.addAll(tvShowResults);
        adapter.notifyDataSetChanged();
    }
}
