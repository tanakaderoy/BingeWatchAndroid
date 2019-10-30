package com.tanaka.binge.Views;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.tanaka.binge.Controllers.ProviderActivity;
import com.tanaka.binge.Controllers.ShowActivity;
import com.tanaka.binge.ImageSize;
import com.tanaka.binge.Models.TvShowResult;
import com.tanaka.binge.R;

import java.util.ArrayList;

/**
 * Created by Tanaka Mazi on 2019-10-29.
 * Copyright (c) 2019 All rights reserved.
 */
public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.ViewHolder> {
    ArrayList<TvShowResult> favShows;

    public FavoritesAdapter(ArrayList<TvShowResult> favShows) {
        this.favShows = favShows;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View homeListItem = layoutInflater.inflate(R.layout.home_list_item, parent, false);
        FavoritesAdapter.ViewHolder viewHolder = new FavoritesAdapter.ViewHolder(homeListItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final TvShowResult tvShow = favShows.get(position);
        holder.titleTextView.setText(tvShow.getName());
        holder.summaryTextView.setText(tvShow.getOverview());
        holder.yearTextView.setText(tvShow.getFirstAirDate());

        Glide.with(holder.itemView)
                .load("https://image.tmdb.org/t/p/" + ImageSize.w780 + tvShow.getPosterPath()).thumbnail(Glide.with(holder.itemView).load(R.drawable.loading).fitCenter())
                .fitCenter().into(holder.posterImageView);
//        Picasso.get().load("https://image.tmdb.org/t/p/"+ImageSize.w780+tvShow.getPosterPath()).into(holder.posterImageView);
        Glide.with(holder.itemView)
                .load("https://image.tmdb.org/t/p/" + ImageSize.w780 + tvShow.getBackdropPath()).thumbnail(Glide.with(holder.itemView).load(R.drawable.loading).fitCenter())
                .into(holder.background);
//        Picasso.get().load("https://image.tmdb.org/t/p/"+ ImageSize.w780+tvShow.getBackdropPath()).fit().into(holder.background);
        holder.backdropURL = "https://image.tmdb.org/t/p/" + ImageSize.original + tvShow.getBackdropPath();
        holder.showID = tvShow.getId();

    }

    public void clear() {
        favShows.clear();
        notifyDataSetChanged();
    }

    public void addAll(ArrayList<TvShowResult> list) {
        favShows.addAll(list);
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return favShows.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView posterImageView;
        public TextView titleTextView;
        TextView yearTextView;
        TextView summaryTextView;
        ToggleButton favoriteButton;

        ImageView background;
        String backdropURL = "";
        int showID = 0;


        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            setUpClickListeners(itemView);
            this.posterImageView = itemView.findViewById(R.id.posterImageView);
            this.titleTextView = itemView.findViewById(R.id.titleTextView);
            yearTextView = itemView.findViewById(R.id.yearTextView);
            summaryTextView = itemView.findViewById(R.id.summaryTextView);
            background = itemView.findViewById(R.id.backgroundImageView);
            favoriteButton = itemView.findViewById(R.id.favButton);


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
