package com.tanaka.binge.Views;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.tanaka.binge.Controllers.ProviderActivity;
import com.tanaka.binge.Controllers.ShowActivity;
import com.tanaka.binge.ImageSize;
import com.tanaka.binge.Models.TvShowResult;
import com.tanaka.binge.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tanaka Mazi on 2019-10-19.
 * Copyright (c) 2019 All rights reserved.
 */
public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {

    ArrayList<TvShowResult> favoritesList = new ArrayList<>();
    private List<TvShowResult> tvShowResultList;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
    private CollectionReference tvShowRef;

    public HomeAdapter(List<TvShowResult> listdata) {

        this.tvShowResultList = listdata;
    }

    public void filterTvShowResultList() {

        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View homeListItem = layoutInflater.inflate(R.layout.home_list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(homeListItem);
        return viewHolder;
    }

    public void setFavoritesList(ArrayList<TvShowResult> favoritesList) {

        this.favoritesList = favoritesList;
    }

    public void clear() {
        tvShowResultList.clear();
        notifyDataSetChanged();
    }

    public void addAll(ArrayList<TvShowResult> list) {
        tvShowResultList.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final TvShowResult tvShow = tvShowResultList.get(position);

        holder.titleTextView.setText(tvShow.getName());
        holder.summaryTextView.setText(tvShow.getOverview());
        holder.yearTextView.setText(tvShow.getFirstAirDate());
        Glide.with(holder.itemView.getContext()).clear(holder.background);
        Glide.with(holder.itemView.getContext()).clear(holder.posterImageView);

        Glide.with(holder.itemView)
                .load("https://image.tmdb.org/t/p/" + ImageSize.w500 + tvShow.getPosterPath())

                .override(500, 1000).thumbnail(0.5f)
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.ALL).fitCenter().into(holder.posterImageView);
//        Picasso.get().load("https://image.tmdb.org/t/p/"+ImageSize.w780+tvShow.getPosterPath()).into(holder.posterImageView);
        Glide.with(holder.itemView)
                .load("https://image.tmdb.org/t/p/" + ImageSize.w780 + tvShow.getBackdropPath())
                .thumbnail(0.5f)
                .override(900, 800)
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.ALL).apply(RequestOptions.centerCropTransform()).into(holder.background);
//        Picasso.get().load("https://image.tmdb.org/t/p/"+ ImageSize.w780+tvShow.getBackdropPath()).fit().into(holder.background);
        holder.backdropURL = "https://image.tmdb.org/t/p/" + ImageSize.w780 + tvShow.getBackdropPath();
        holder.showID = tvShow.getId();
        if (currentUser == null) {
            holder.favoriteButton.setVisibility(View.INVISIBLE);
        }


        holder.favoriteButton.setChecked(false);

        for (TvShowResult favShow : favoritesList) {
            if (tvShow.getId().equals(favShow.getId())) {
                holder.favoriteButton.setChecked(true);
            }
        }
        holder.favoriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.favoriteButton.isChecked()) {
                    favoriteShow(tvShow, holder);
                } else {
                    unFavoriteShow(tvShow, holder);
                }
            }
        });
        Double two = 2.0;
        Double rating = tvShow.getVoteAverage() / two;
        holder.ratingBar.setRating(rating.floatValue());


    }

    private void unFavoriteShow(TvShowResult tvShow, final ViewHolder holder) {
        if (currentUser != null) {
            tvShowRef = db.collection("FavShows").document(currentUser.getEmail()).collection("TvShows");

            tvShowRef.document(tvShow.getName()).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Toast.makeText(holder.itemView.getContext(), "Delete Success", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(holder.itemView.getContext(), "Delete Failed", Toast.LENGTH_SHORT).show();

                }
            });
        }
    }

    private void favoriteShow(TvShowResult tvShow, final ViewHolder holder) {
        if (currentUser != null) {
            tvShowRef = db.collection("FavShows").document(currentUser.getEmail()).collection("TvShows");
            tvShowRef.document(tvShow.getName()).set(tvShow).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(holder.itemView.getContext(), "Failure to Favorite: " + e.toString(), Toast.LENGTH_SHORT).show();

                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return tvShowResultList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView posterImageView;
        TextView titleTextView;
        TextView yearTextView;
        TextView summaryTextView;
        ToggleButton favoriteButton;
        RatingBar ratingBar;
        ImageView background;
        String backdropURL = "";
        int showID = 0;


        ViewHolder(@NonNull final View itemView) {
            super(itemView);
            setUpClickListeners(itemView);
            this.posterImageView = itemView.findViewById(R.id.posterImageView);
            this.titleTextView = itemView.findViewById(R.id.titleTextView);
            yearTextView = itemView.findViewById(R.id.yearTextView);
            summaryTextView = itemView.findViewById(R.id.summaryTextView);
            background = itemView.findViewById(R.id.backgroundImageView);
            favoriteButton = itemView.findViewById(R.id.favButton);
            ratingBar = itemView.findViewById(R.id.ratingBar);


        }


        private void setUpClickListeners(@NonNull final View itemView) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(itemView.getContext(), ShowActivity.class);
                    intent.putExtra("backdropImage", backdropURL);
                    intent.putExtra("showID", showID);
                    intent.putExtra("showName", titleTextView.getText());
                    itemView.getContext().startActivity(intent);
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    Intent intent = new Intent(itemView.getContext(), ProviderActivity.class);
                    intent.putExtra("showName", titleTextView.getText());
                    itemView.getContext().startActivity(intent);

                    return false;
                }
            });
        }
    }
}
