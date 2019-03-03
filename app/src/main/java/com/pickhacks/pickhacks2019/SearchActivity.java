package com.pickhacks.pickhacks2019;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {

    static private String url = "http://192.241.144.36:5678/HelloWorld.html?diet=";
    static private ArrayList<SearchItem> mSearchList;
    static private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        SharedPreferences mSharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        String mCurrentDiet = mSharedPrefs.getString("currentDiet", "Not Available");
        requestQueue = Volley.newRequestQueue(this);
        System.out.println("CURRENT DIET: " + mCurrentDiet);
        // Make a query to the API

        // Take out the JSON
        try {

            getSearchItemList(mCurrentDiet);
            // add to the list
        } catch (JSONException e) {
            e.printStackTrace();
        }
        // Create a list of the JSON Objects

        // Create from our list
        RecyclerView mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(false);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        RecyclerView.Adapter mAdapter = new SearchItemAdapter(mSearchList);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }



    private static void getSearchItemList(String diet) throws JSONException {


        mSearchList = new ArrayList<>();
        SearchActivity.url += diet;

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {
                // Check the length of our response (to see if the user has any repos)
                if (response.length() > 0) {
                    // The user does have repos, so let's loop through them all.
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            // For each repo, add a new line to our repo list.
                            JSONObject jsonObj = response.getJSONObject(i);

                            String plantName = jsonObj.getString("name");
                            String plantTime = jsonObj.getString("time");
                            String plantPhoto = jsonObj.getString("photo"); // Needs to be decoded
                            boolean plantStar = jsonObj.getBoolean("star");
                            String plantBrief = jsonObj.getString("brief");

                            // add to the list
                            mSearchList.add(new SearchItem(
                                    plantPhoto,
                                    plantBrief,
                                    plantStar,
                                    plantTime,
                                    plantName));
                        }
                        catch (JSONException e) {
                            // If there is an error then output this to the logs.
                            e.printStackTrace();

                        }

                    }
                }
            }
        },

        new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // If there a HTTP error then add a note to our repo list.
                System.out.println("Volley");
                error.printStackTrace();
            }
        });

        // Add the request we just defined to our request queue.
        requestQueue.add(request);
    }
}
