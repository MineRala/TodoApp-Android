package com.example.todoapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    public BottomNavigationView bottomNavigationView;
    private ViewPager viewPager;
    public static MainActivity instance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        instance = this;
        Toolbar toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.tasks);


        bottomNavigationView = findViewById(R.id.bottomNavigationBar);
        viewPager = findViewById(R.id.viewpager);
        setUpViewPager();

        bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.profile:
                        if(getSupportActionBar() != null) {
                            getSupportActionBar().setTitle(R.string.profile);
                        }
                        viewPager.setCurrentItem(1);
                        break;
                    default:
                        if(getSupportActionBar() != null) {
                            getSupportActionBar().setTitle(R.string.tasks);
                        }
                        viewPager.setCurrentItem(0);
                }
                return true;
            }
        });
    }

    private void setUpViewPager() {
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPager.setAdapter(viewPagerAdapter);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 1:
                        if(getSupportActionBar() != null) {
                            getSupportActionBar().setTitle(R.string.profile);
                        }
                        bottomNavigationView.getMenu().findItem(R.id.profile).setChecked(true);
                        break;
                    default:
                        if(getSupportActionBar() != null) {
                            getSupportActionBar().setTitle(R.string.tasks);
                        }
                        bottomNavigationView.getMenu().findItem(R.id.list).setChecked(true);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    public static MainActivity getInstance() {
        return instance;
    }
}