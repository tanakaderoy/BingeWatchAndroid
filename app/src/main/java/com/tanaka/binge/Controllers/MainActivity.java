package com.tanaka.binge.Controllers;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.tanaka.binge.Models.TvShowResult;
import com.tanaka.binge.R;
import com.tanaka.binge.Views.ViewPagerAdapter;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    public final static String GOOGLE_ACCOUNT = "googleAccount";
    final FragmentManager fragmentManager = getSupportFragmentManager();
    Fragment fragment1;
    Fragment fragment2;
    Fragment fragment3;
    ViewPager viewPager;
    FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    ArrayList<TvShowResult> favoritesList = new ArrayList<>();
    Fragment active = fragment1;
    MenuItem prevMenuItem;
    BottomNavigationView bottomNav;
    private CollectionReference tvShowRef;
    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                    switch (item.getItemId()) {
                        case R.id.nav_home:
                            viewPager.setCurrentItem(0);
                            break;

                        case R.id.nav_search:
                            viewPager.setCurrentItem(1);
                            break;
                        case R.id.nav_favorites:
                            viewPager.setCurrentItem(2);
                            break;
                    }

                    return true;
                }
            };
    private ViewPager.OnPageChangeListener pageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            if (prevMenuItem != null) {
                prevMenuItem.setChecked(false);
            } else {
                bottomNav.getMenu().getItem(0).setChecked(false);
            }
            Log.d("page", "onPageSelected: " + position);
            bottomNav.getMenu().getItem(position).setChecked(true);
            prevMenuItem = bottomNav.getMenu().getItem(position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewPager = findViewById(R.id.viewpager);
        int color = getResources().getColor(R.color.colorPrimary);
        String htmlColor = String.format(Locale.US, "#%06X", (0xFFFFFF & Color.argb(0, Color.red(color), Color.green(color), Color.blue(color))));
        getSupportActionBar().setTitle(Html.fromHtml("<font color=" + htmlColor + ">" + getString(R.string.app_name) + "</font>"));

        bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
        MenuItem item = bottomNav.getMenu().findItem(R.id.nav_favorites);
        if (currentUser == null) {
            item.setVisible(false);
        } else {
            tvShowRef = db.collection("FavShows").document(currentUser.getEmail()).collection("TvShows");
        }
        viewPager.setOnPageChangeListener(pageChangeListener);


        setupViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        fragment1 = new HomeFragment();
        fragment2 = new SearchFragment();
        fragment3 = new FavoritesFragment();
        adapter.addFragment(fragment1);
        adapter.addFragment(fragment2);
        if (currentUser != null) {
            adapter.addFragment(fragment3);
        }
        int limit = (adapter.getCount() > 1 ? adapter.getCount() - 1 : 1);
        viewPager.setOffscreenPageLimit(limit);
        viewPager.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);
        MenuItem loginItem = menu.findItem(R.id.logIn);
        MenuItem logOutItem = menu.findItem(R.id.logOut);
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            loginItem.setVisible(false);
        } else {
            logOutItem.setVisible(false);
        }
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logOut:
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(this, "Signed Out", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                finish();
            case R.id.logIn:
                Intent intent2 = new Intent(this, LoginActivity.class);

                startActivity(intent2);
                finish();


            default:
                return super.onOptionsItemSelected(item);

        }


    }

    //    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
//            new BottomNavigationView.OnNavigationItemSelectedListener() {
//                @Override
//                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                    Fragment selectedFragment = null;
//
//                    switch (item.getItemId()) {
//                        case R.id.nav_home:
//                            if (active == fragment1){
//                                break;
//                            }
//                           fragmentManager.beginTransaction().hide(active).show(fragment1).commit();
//
//                            active = fragment1;
//                            break;
//
//                        case R.id.nav_search:
//                            if(active == fragment2){
//                                break;
//                            }
//                            fragmentManager.beginTransaction().hide(active).show(fragment2).commit();
//                            active = fragment2;
//                            break;
//                    }
//
//
//
//                    return true;
//                }
//            };
}