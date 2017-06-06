package com.example.xenom.opticsmenu.UI;

import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.os.Bundle;

import com.example.xenom.opticsmenu.R;
import com.example.xenom.opticsmenu.UI.CustomSwipeAdapter;

public class MainActivity extends FragmentActivity {
    ViewPager viewPager;
    CustomSwipeAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Learn Optics");

        setContentView(R.layout.activity_main);
        viewPager = (ViewPager)findViewById(R.id.pager);
        adapter = new CustomSwipeAdapter(this);
        viewPager.setAdapter(adapter);


    }







}
