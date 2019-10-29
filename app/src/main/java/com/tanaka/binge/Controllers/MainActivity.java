package com.tanaka.binge.Controllers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.tanaka.binge.R;
import com.tanaka.binge.Views.ViewPagerAdapter;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    final Fragment fragment1 = new HomeFragment();
    final Fragment fragment2 = new SearchFragment();
    final FragmentManager fragmentManager = getSupportFragmentManager();
    ViewPager viewPager;

    Fragment active = fragment1;
    MenuItem prevMenuItem;
    BottomNavigationView bottomNav;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewPager = findViewById(R.id.viewpager);
        int color = getResources().getColor(R.color.colorPrimary);
        String htmlColor = String.format(Locale.US, "#%06X", (0xFFFFFF & Color.argb(0, Color.red(color), Color.green(color), Color.blue(color))));
        getSupportActionBar().setTitle(Html.fromHtml("<font color="+htmlColor+">" + getString(R.string.app_name) + "</font>"));

        bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        viewPager.setOnPageChangeListener(pageChangeListener);


        setupViewPager(viewPager);
    }

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
                    }

                    return true;
                }
            };

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        adapter.addFragment(fragment1);
        adapter.addFragment(fragment2);
        viewPager.setAdapter(adapter);
    }
private ViewPager.OnPageChangeListener pageChangeListener = new ViewPager.OnPageChangeListener() {
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        if (prevMenuItem != null) {
            prevMenuItem.setChecked(false);
        }
        else
        {
            bottomNav.getMenu().getItem(0).setChecked(false);
        }
        Log.d("page", "onPageSelected: "+position);
        bottomNav.getMenu().getItem(position).setChecked(true);
        prevMenuItem = bottomNav.getMenu().getItem(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
};

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