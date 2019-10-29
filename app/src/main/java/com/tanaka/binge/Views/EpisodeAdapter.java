package com.tanaka.binge.Views;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;
import com.tanaka.binge.ImageSize;
import com.tanaka.binge.Models.Episode;
import com.tanaka.binge.R;

import java.util.ArrayList;

/**
 * Created by Tanaka Mazi on 2019-10-28.
 * Copyright (c) 2019 All rights reserved.
 */
public class EpisodeAdapter extends RecyclerView.Adapter<EpisodeAdapter.ViewHolder> {
ArrayList<Episode> episodes;

public EpisodeAdapter(ArrayList<Episode> episodes){
    this.episodes = episodes;
}

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View episodeListItem = layoutInflater.inflate(R.layout.episode_list_item, parent, false);
        EpisodeAdapter.ViewHolder viewHolder = new EpisodeAdapter.ViewHolder(episodeListItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    Episode episode = episodes.get(position);
    holder.summaryTextView.setText(episode.getOverview());
    holder.airdateTextView.setText(episode.getAirDate());
//    Picasso.get().load("https://image.tmdb.org/t/p/"+ ImageSize.w780+episode.getStillPath()).into(holder.episodeImageView);

        Glide.with(holder.itemView)
                .load("https://image.tmdb.org/t/p/"+ImageSize.w780+episode.getStillPath()).thumbnail(Glide.with(holder.itemView).load(R.drawable.loading))
                .into(holder.episodeImageView);
    }

    @Override
    public int getItemCount() {
        return episodes.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
    ImageView episodeImageView;
    TextView airdateTextView;
    TextView summaryTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            episodeImageView = itemView.findViewById(R.id.episodeImageView);
            airdateTextView = itemView.findViewById(R.id.episodeAirdateTextView);
            summaryTextView = itemView.findViewById(R.id.episodeSummaryTextView);
        }
    }
}
