package com.tanaka.binge.Views;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.tanaka.binge.ApiInterface;
import com.tanaka.binge.Models.JWProvider;
import com.tanaka.binge.Models.Offer;
import com.tanaka.binge.R;
import com.tanaka.binge.Controllers.WebviewActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by Tanaka Mazi on 2019-10-20.
 * Copyright (c) 2019 All rights reserved.
 */
public class ProviderAdapter extends RecyclerView.Adapter<ProviderAdapter.ViewHolder> {

    ArrayList<JWProvider> providers = new ArrayList<>();
   ArrayList<String> providerNames;
    final String regex = "[l-p]\\w\\w\\w\\.\\w++|[a-d]\\w\\w\\w[m-o]\\w|(?<=\\.)\\w[^com]\\w++|itunes|\\w\\w\\w\\w\\w\\w\\w\\w\\w[l-p]\\w|(?<=:\\/\\/)\\w\\w\\w\\w\\w[^_]|\\w[com]\\w\\w[m-o]\\w|amc";
//List<Season> seasonsList = new ArrayList<>();
//    public SeasonAdapter(List<Season> seasonListData) {
//        this.seasonsList = seasonListData;
//    }

    List<Offer> offers = new ArrayList<>();
    public ProviderAdapter(List<Offer> offerList){
offers = offerList;
    }


    public void setProviderNames(ArrayList<String> providerNames) {
        this.providerNames = providerNames;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View providerListItem = layoutInflater.inflate(R.layout.provider_list_item, parent, false);
        ProviderAdapter.ViewHolder viewHolder = new ProviderAdapter.ViewHolder(providerListItem);


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
final Offer offer = offers.get(position);

holder.providerName.setText(offer.getProviderName());
        Picasso.get().load(offer.getIconURL()).into(holder.providerImageView);
if (offer.getUrls().getDeeplinkAndroidTv() != null){
    holder.openProviderButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {


            Intent netflix = new Intent();
            netflix.setAction(Intent.ACTION_VIEW);
            netflix.setData(Uri.parse(offer.getUrls().getStandardWeb()));
            netflix.putExtra("source","30"); // careful: String, not int
            netflix.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
            v.getContext().startActivity(netflix);



        }
    });
}else{
    holder.openProviderButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(v.getContext(), WebviewActivity.class);
            intent.putExtra("url", offer.getUrls().getStandardWeb());
            v.getContext().startActivity(intent);
        }
    });
}
    }







    @Override
    public int getItemCount() {
        return offers.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        Button openProviderButton;
        TextView providerName;
        ImageView providerImageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            openProviderButton = itemView.findViewById(R.id.openProviderButton);
            providerName = itemView.findViewById(R.id.providerNameTextView);
            providerImageView = itemView.findViewById(R.id.providerImageView);
        }
    }
}
