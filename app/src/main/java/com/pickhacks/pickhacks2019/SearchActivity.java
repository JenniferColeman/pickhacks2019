package com.pickhacks.pickhacks2019;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {

    private ArrayList<SearchItem> mSearchList;

    private SharedPreferences mSharedPrefs;
    private String mCurrentDiet;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mSharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        mCurrentDiet = mSharedPrefs.getString("currentDiet", "Not Available");

        System.out.println("CURRENT DIET: " + mCurrentDiet);
        // Make a query to the API


        // Take out the JSON

        // Create a list of the JSON Objects

        mSearchList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            mSearchList.add(new SearchItem(
                    R.drawable.food,
                    "This is a brief description of the thing.",
                    false,
                    "3 months",
                    "Carrot"));
        }

        // Create from our list
        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(false);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new SearchItemAdapter(mSearchList);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }

}
