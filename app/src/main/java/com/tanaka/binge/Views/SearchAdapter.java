package com.tanaka.binge.Views;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.tanaka.binge.Controllers.ProviderActivity;
import com.tanaka.binge.Controllers.ShowActivity;
import com.tanaka.binge.ImageSize;
import com.tanaka.binge.Models.Result;
import com.tanaka.binge.R;

import java.util.List;

/**
 * Created by Tanaka Mazi on 2019-10-27.
 * Copyright (c) 2019 All rights reserved.
 */
public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {
    private List<Result> tvShowResultList;
    public SearchAdapter(List<Result> listdata) {
        this.tvShowResultList = listdata;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View searchListItem = layoutInflater.inflate(R.layout.search_list_item, parent, false);
        SearchAdapter.ViewHolder viewHolder = new SearchAdapter.ViewHolder(searchListItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Result tvShow = tvShowResultList.get(position);
        
        holder.titleTextView.setText(tvShow.getName());
        holder.summaryTextView.setText(tvShow.getOverview());
        holder.yearTextView.setText(tvShow.getFirstAirDate());
        Glide.with(holder.itemView.getContext()).clear(holder.posterImageView);
        Glide.with(holder.itemView.getContext())
                .load("https://image.tmdb.org/t/p/"+ImageSize.w780+tvShow.getPosterPath()).thumbnail(Glide.with(holder.itemView).load(R.drawable.loading).fitCenter())
                .fitCenter().into(holder.posterImageView);
//        Picasso.get().load("https://image.tmdb.org/t/p/"+ ImageSize.w780+tvShow.getPosterPath()).into(holder.posterImageView);
//        Picasso.get().load("https://image.tmdb.org/t/p/"+ImageSize.w780+tvShow.getBackdropPath()).fit().into(holder.background);
        Glide.with(holder.itemView.getContext())
                .load("https://image.tmdb.org/t/p/"+ImageSize.w780+tvShow.getBackdropPath()).thumbnail(Glide.with(holder.itemView).load(R.drawable.loading).fitCenter())
                .into(holder.background);
        holder.backdropURL = "https://image.tmdb.org/t/p/"+ImageSize.original+tvShow.getBackdropPath();
        holder.showID = tvShow.getId();
    }

    @Override
    public int getItemCount() {
        return tvShowResultList.size();
    }

    class ViewHolder extends  RecyclerView.ViewHolder{
        public ImageView posterImageView;
        public TextView titleTextView;
        TextView yearTextView;
        TextView summaryTextView;

        ImageView background;
        String backdropURL = "";
        int showID = 0;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            setUpClickListeners(itemView);
            this.posterImageView = itemView.findViewById(R.id.searchPosterImageView);
            this.titleTextView = itemView.findViewById(R.id.searchTitleTextView);
            yearTextView = itemView.findViewById(R.id.searchYearTextView);
            summaryTextView = itemView.findViewById(R.id.searchSummaryTextView);
            background = itemView.findViewById(R.id.searchBackgroundImageView);
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
