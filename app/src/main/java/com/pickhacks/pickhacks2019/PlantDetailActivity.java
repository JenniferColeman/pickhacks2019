package com.pickhacks.pickhacks2019;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class PlantDetailActivity extends AppCompatActivity {

    static private String url = "http://5f95dab0.ngrok.io" + "/getDetail?name=";
    String  mBrief,
            mTime,
            mName,
            mFreqWater,
            mGenAdvice,
            mHarvestAdvice,
            mCareAdvice,
            mPlantingAdvice,
            mPhoto;
    boolean mStar;
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plant_detail);


        requestQueue = Volley.newRequestQueue(this);

        mName = getIntent().getStringExtra("plantName");
        updatePlantInformation();
    }

    private void updatePlantInformationView() {
        ImageView plantPhotoView = findViewById(R.id.plantPagePhoto);
        TextView plantNameView = findViewById(R.id.plantPagePlantName);
        TextView plantInfoView = findViewById(R.id.plantPageMainContent);
        plantNameView.setText(mName);
        plantInfoView.setText(mBrief + "\n\n" +
                        "Watering Frequency: " + mFreqWater + "\n" +
                "General Advice: " + mGenAdvice + "\n" +
                "Harvest Advice: " + mHarvestAdvice + "\n" +
                "Care Advice: " + mCareAdvice + "\n" +
                "Planting Advice: " + mPlantingAdvice + "\n");

        loadImageFromBase64IntoView(plantPhotoView, mPhoto);
    }


    private void updatePlantInformation() {

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url + mName, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject jsonObj) {
                // Check the length of our response (to see if the user has any repos)
                // The user does have repos, so let's loop through them all.
                try {
                    // For each repo, add a new line to our repo list.
                    mBrief = jsonObj.getString("brief");
                    mFreqWater = jsonObj.getString("freqWater");
                    mGenAdvice = jsonObj.getString("genAdvice");
                    mHarvestAdvice = jsonObj.getString("harvestAdvice");
                    mCareAdvice = jsonObj.getString("careAdvice");
                    mPlantingAdvice = jsonObj.getString("plantingAdvice");
                    mPhoto = jsonObj.getString("photo");
                    mStar = jsonObj.getBoolean("star");
                } catch (JSONException e) {
                    // If there is an error then output this to the logs.
                    e.printStackTrace();
                }
                updatePlantInformationView();
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


    private void loadImageFromBase64IntoView(ImageView imageView, String encodedImage) {
        System.out.println("encoded image: " + encodedImage);
        byte[] decodedString = Base64.decode(encodedImage, Base64.DEFAULT);
        System.out.println(encodedImage);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        imageView.setImageBitmap(decodedByte);
    }

}
