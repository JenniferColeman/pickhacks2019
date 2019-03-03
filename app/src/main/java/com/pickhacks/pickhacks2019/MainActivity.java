package com.pickhacks.pickhacks2019;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class MainActivity extends AppCompatActivity {

    private RadioGroup mRadios;
    private SharedPreferences mSharedPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get the view from activity_main.xml
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mRadios = findViewById(R.id.dietRadioGroup);
        mSharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                System.out.println("CURRENT: " + mRadios.getCheckedRadioButtonId());
                if (mRadios.getCheckedRadioButtonId() == -1)
                {
                    Snackbar.make(view, "You must select a diet type!", Snackbar.LENGTH_LONG).show();
                }
                else
                {
                    RadioButton rb = findViewById(mRadios.getCheckedRadioButtonId());
                    String currentDietName = rb.getText().toString();

                    // Create object of SharedPreferences.
                    //now get Editor
                    SharedPreferences.Editor editor = mSharedPrefs.edit();
                    //put your value
                    editor.putString("currentDiet", currentDietName);
                    //commits your edits
                    editor.commit();

                    //Start SearchActivity.class
                    Intent openSearch = new Intent(MainActivity.this, SearchActivity.class);
                    startActivity(openSearch);
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
