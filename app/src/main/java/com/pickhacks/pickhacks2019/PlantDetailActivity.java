package com.pickhacks.pickhacks2019;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class PlantDetailActivity extends AppCompatActivity {

    private ArrayList<PlantItem> mPlantBio;
    private SharedPreferences mSharedPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plant_detail);

        mSharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        String mCurrentDiet = mSharedPrefs.getString("currentPlantName", "Unknown");

        mPlantBio = new ArrayList<>();
        mPlantBio.add(new PlantItem(
                R.drawable.food,
                "This is a brief description of the thing.",
                false,
                "3 months",
                "Carrot",
                "5",
                "This is for general advice",
                "This is for harvest_advice",
                "This is for care advice",
                "This is for planting advice"
        ));

    }



}
