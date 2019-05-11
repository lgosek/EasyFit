package com.example.easyfit.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.easyfit.R;

public class ProductsFragment extends Fragment {
    TabLayout productsTab;
    ViewPager productsPager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_products,container,false);
        productsTab = view.findViewById(R.id.productsTabLayout);
        productsPager = view.findViewById(R.id.productsPager);

//        productsPager.setAdapter(new PagerAdapter(getFragmentManager()));
        productsPager.setAdapter(new PagerAdapter(getFragmentManager()));
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

        return view;
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
