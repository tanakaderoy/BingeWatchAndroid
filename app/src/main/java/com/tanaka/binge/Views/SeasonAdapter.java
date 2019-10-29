package com.tanaka.binge.Views;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.tanaka.binge.Controllers.EpisodeInfoActivity;
import com.tanaka.binge.Controllers.ProviderActivity;
import com.tanaka.binge.ImageSize;
import com.tanaka.binge.Models.Season;
import com.tanaka.binge.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tanaka Mazi on 2019-10-20.
 * Copyright (c) 2019 All rights reserved.
 */
public class SeasonAdapter extends RecyclerView.Adapter<SeasonAdapter.ViewHolder> {
    private List<Season> seasonsList = new ArrayList<>();
    private int showId = 0;
    private String showName = "";

    public void setShowInfo(int showId, String showName) {
        this.showId = showId;
        this.showName = showName;
    }

    public SeasonAdapter(List<Season> seasonListData) {
        this.seasonsList = seasonListData;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View seasonListItem = layoutInflater.inflate(R.layout.season_list_item, parent, false);
        SeasonAdapter.ViewHolder viewHolder = new SeasonAdapter.ViewHolder(seasonListItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Season season = seasonsList.get(position);
        holder.showID = showId;
        holder.showName = showName;
        holder.seasonNumber = season.getSeasonNumber();
        holder.seasonName = season.getName();
        holder.backdropURL = season.getPosterPath();

        Picasso.get().load("https://image.tmdb.org/t/p/"+ ImageSize.w780+season.getPosterPath()).into(holder.seasonPosterImageView);

    }

    @Override
    public int getItemCount() {
        return seasonsList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
ImageView seasonPosterImageView;
        private String backdropURL;
        private int showID;
        private String seasonName;
        private String showName;
        private int seasonNumber;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            seasonPosterImageView = itemView.findViewById(R.id.seasonPosterimageView);
            setUpClickListeners(itemView);
        }

        private void setUpClickListeners(@NonNull final View itemView) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(itemView.getContext(), EpisodeInfoActivity.class);
                    intent.putExtra("backdropImage", backdropURL);
                    intent.putExtra("showID", showID);
                    intent.putExtra("seasonNumber", seasonNumber);
                    intent.putExtra("seasonName", seasonName);
                    intent.putExtra("showName", showName);
                    itemView.getContext().startActivity(intent);
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    Intent intent = new Intent(itemView.getContext(), ProviderActivity.class);
                    intent.putExtra("showName", showName);
                    itemView.getContext().startActivity(intent);

                    return false;
                }
            });
        }
    }
}
