package com.tanaka.binge.Controllers;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.tanaka.binge.Models.TvShowResult;
import com.tanaka.binge.R;
import com.tanaka.binge.Views.HomeAdapter;

import java.util.ArrayList;

/**
 * Created by Tanaka Mazi on 2019-10-29.
 * Copyright (c) 2019 All rights reserved.
 */
public class FavoritesFragment extends Fragment {
    public final static String GOOGLE_ACCOUNT = "googleAccount";
    FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    ArrayList<TvShowResult> favoritesList = new ArrayList<>();
    private RecyclerView favRecyclerView;
    private HomeAdapter adapter;
    private ProgressDialog progressDialog;
    private LinearLayoutManager linearLayoutManager;
    private SwipeRefreshLayout swipeContainer;
    private CollectionReference tvShowRef;

    public FavoritesFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        return inflater.inflate(R.layout.favorite_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        favRecyclerView = view.findViewById(R.id.favRecyclerView);
        linearLayoutManager = new LinearLayoutManager(view.getContext());
        if (currentUser != null) {
            tvShowRef = db.collection("FavShows").document(currentUser.getEmail()).collection("TvShows");

        }

        adapter = new HomeAdapter(favoritesList);
        favRecyclerView.setLayoutManager(linearLayoutManager);
        favRecyclerView.setAdapter(adapter);
        progressDialog = new ProgressDialog(view.getContext());
        progressDialog.setMessage("Loading....");
        if (currentUser != null) {
            loadFavorites();
        }
        swipeContainer = view.findViewById(R.id.swipeContainer);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Your code to refresh the list here.
                // Make sure you call swipeContainer.setRefreshing(false)
                // once the network request has completed successfully.
                loadFavorites();
            }
        });
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);


    }

    @Override
    public void onStart() {
        super.onStart();
        if (currentUser != null) {
            tvShowRef = db.collection("FavShows").document(currentUser.getEmail()).collection("TvShows");

        }
        tvShowRef.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    return;
                }

                adapter.clear();
                adapter.setFavoritesList((ArrayList<TvShowResult>) queryDocumentSnapshots.toObjects(TvShowResult.class));
                adapter.addAll((ArrayList<TvShowResult>) queryDocumentSnapshots.toObjects(TvShowResult.class));

            }
        });
    }

    private void loadFavorites() {

        tvShowRef.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                progressDialog.dismiss();
                adapter.clear();
                adapter.addAll((ArrayList<TvShowResult>) queryDocumentSnapshots.toObjects(TvShowResult.class));
                swipeContainer.setRefreshing(false);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
            }
        });

    }


    private void generateDataList(ArrayList<TvShowResult> tvShowResults) {
        favoritesList = new ArrayList<>();
        favoritesList = tvShowResults;
        adapter.notifyDataSetChanged();
    }
}
