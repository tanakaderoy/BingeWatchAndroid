package com.tanaka.binge.Controllers;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tanaka.binge.ApiInterface;
import com.tanaka.binge.Models.Item;
import com.tanaka.binge.Models.JWBody;
import com.tanaka.binge.Models.JWProvider;
import com.tanaka.binge.Models.JWShowResponseModel;
import com.tanaka.binge.Models.Offer;
import com.tanaka.binge.R;
import com.tanaka.binge.Views.ProviderAdapter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class ProviderActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    Retrofit retrofit;
    ApiInterface apiInterface;
    JWBody jwBody;
    ArrayList<Offer> offers;
    ProviderAdapter adapter;
    LinearLayoutManager linearLayoutManager;
    String showName = "";
    Intent intent;
    ArrayList<JWProvider> providers;
    String providerName = "";
    ArrayList<String> providerNames = new ArrayList<>();
    private ProgressDialog progressDialog;

    final String regex = "^\\/[^\\/]+\\/([^\\/]+)\\/";




    private static OkHttpClient buildClient() {
        return new OkHttpClient
                .Builder()
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider);
        int color = getResources().getColor(R.color.colorPrimary);
        String htmlColor = String.format(Locale.US, "#%06X", (0xFFFFFF & Color.argb(0, Color.red(color), Color.green(color), Color.blue(color))));

        intent = getIntent();
        showName = intent.getStringExtra("showName");
        recyclerView = findViewById(R.id.providerRecyclerView);
        linearLayoutManager = new LinearLayoutManager(this);
        providers = new ArrayList<>();
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading....");
        offers = new ArrayList<>();
        adapter = new ProviderAdapter(offers);
        adapter.setProviderNames(providerNames);

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
        setTitle(Html.fromHtml("<font color=" + htmlColor + ">" + showName + "</font>"));


        setupRetrofit();
        getProviders();





//        jwBody.setQuery("Bojack Horseman");
//        List<String> contentTypes = new ArrayList<>();
//        contentTypes.add("show");
//        jwBody.setContentTypes(contentTypes);


    }


    private void getProviders() {
        progressDialog.show();
        apiInterface.getProviders().enqueue(new Callback<List<JWProvider>>() {
            @Override
            public void onResponse(Call<List<JWProvider>> call, Response<List<JWProvider>> response) {
                providers.addAll(response.body());

fetchProviders();
            }

            @Override
            public void onFailure(Call<List<JWProvider>> call, Throwable t) {
                t.printStackTrace();
                progressDialog.dismiss();
            }
        });
    }


    private void fetchProviders() {
        //System.out.println(jwBody.getQuery());
        apiInterface.searchForShow(new JWBody(showName, "show")).enqueue(new Callback<JWShowResponseModel>() {
            @Override
            public void onResponse(Call<JWShowResponseModel> call, Response<JWShowResponseModel> response) {
                RequestBody test = call.request().body();
                test.contentType();
                System.out.println(call.request().body().toString() + "request: " + call.request().url());


                    Item item = response.body().getItems().get(0);
                   ArrayList<Offer> offers = (ArrayList<Offer>) item.getOffers();
                   System.out.println(offers.get(0).getUrls().getStandardWeb());



                        generateOffersList(offers);

                        progressDialog.dismiss();


            }

            @Override
            public void onFailure(Call<JWShowResponseModel> call, Throwable t) {
t.printStackTrace();
progressDialog.dismiss();
            }
        });
    }

    private void generateOffersList(@NonNull List<Offer> offersList) {
        progressDialog.dismiss();

        offers.addAll(offersList);
        for(JWProvider provider: providers){
            for(Offer offer: offers) {
                System.out.println(offer.getProviderId() + " : " + provider.getId());
                if (provider.getId().equals(offer.getProviderId())) {

                    final Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE);
                    final Matcher matcher = pattern.matcher(provider.getIconUrl());

                    while (matcher.find()) {
                        System.out.println("Full match: " + matcher.group(0));
                        for (int i = 1; i <= matcher.groupCount(); i++) {
                            System.out.println("Group " + i + ": " + matcher.group(i));
                            offer.setIconURL(matcher.group(i));
                        }
                    }

                    offer.setProviderName(provider.getClearName());

                }

            }
        }


        adapter.notifyDataSetChanged();
    }

    private ArrayList<Offer> unique(ArrayList<Offer> list) {
        ArrayList<Offer> uniqueList = new ArrayList<>();
        Set<Offer> uniqueSet = new HashSet<>();
        for (Offer obj : list) {
            if (uniqueSet.add(obj)) {
                uniqueList.add(obj);
            }
        }
        return uniqueList;
    }

    private void setupRetrofit() {
        retrofit = new Retrofit.Builder()
                .client(buildClient())
                .baseUrl("https://apis.justwatch.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .addConverterFactory(ScalarsConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        apiInterface = retrofit.create(ApiInterface.class);
    }
}
