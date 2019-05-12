package com.example.easyfit.activities;

import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.easyfit.R;
import com.example.easyfit.fragments.MealsFragment;
import com.example.easyfit.fragments.SimpleProductsFragment;

public class AddEatenProductActivity extends AppCompatActivity {
    TabLayout productsTab;
    ViewPager productsPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_eaten_product);

        productsTab = findViewById(R.id.addProductsTabLayout);
        productsPager = findViewById(R.id.addProductsPager);

//        productsPager.setAdapter(new PagerAdapter(getFragmentManager()));
        productsPager.setAdapter(new PagerAdapter(getSupportFragmentManager()));
        productsTab.setupWithViewPager(productsPager);

        //might put a listener for tabs
        productsTab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                productsPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    class PagerAdapter extends FragmentStatePagerAdapter {

        String[] names = {getResources().getString(R.string.products), getResources().getString(R.string.meals)};

        public PagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            if(i==0){
                return new SimpleProductsFragment();
            }
            if(i==1){
                return new MealsFragment();
            }
            return null;
        }

        @Override
        public int getCount() {
            return names.length;
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return names[position];
        }
    }
}
