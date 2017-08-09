package com.inhwa.nan05.activity;

/**
 * Created by Inhwa_ on 2017-06-19.
 */


import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.inhwa.nan05.R;
import com.inhwa.nan05.app.AppConfig;
import com.inhwa.nan05.app.AppController;
import com.inhwa.nan05.helper.SQLiteHandler;
import com.inhwa.nan05.helper.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Provides UI for the Detail page with Collapsing Toolbar.
 */
public class PerformanceDetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "position";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Set Collapsing Toolbar layout to the screen
        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        // Set title of Detail page
        // collapsingToolbar.setTitle(getString(R.string.item_title));

        int postion = getIntent().getIntExtra(EXTRA_POSITION, 0);
        Resources resources = getResources();

        String[] performance_title = resources.getStringArray(R.array.performance);
        collapsingToolbar.setTitle(performance_title[postion % performance_title.length]);

//        String[] placeDetails = resources.getStringArray(R.array.place_details);
//        TextView placeDetail = (TextView) findViewById(R.id.place_detail);
//        placeDetail.setText(placeDetails[postion % placeDetails.length]);
//
//        String[] placeLocations = resources.getStringArray(R.array.place_locations);
//        TextView placeLocation =  (TextView) findViewById(R.id.place_location);
//        placeLocation.setText(placeLocations[postion % placeLocations.length]);
//
//        TypedArray placePictures = resources.obtainTypedArray(R.array.places_picture);
//        ImageView placePicutre = (ImageView) findViewById(R.id.image);
//        placePicutre.setImageDrawable(placePictures.getDrawable(postion % placePictures.length()));
//
//        placePictures.recycle();


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
//                NavUtils.navigateUpFromSameTask(this);
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
