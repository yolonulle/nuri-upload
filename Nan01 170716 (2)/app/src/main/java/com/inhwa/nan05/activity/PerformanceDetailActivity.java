package com.inhwa.nan05.activity;

/**
 * Created by Inhwa_ on 2017-06-19.
 */

import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;
import com.inhwa.nan05.R;

public class PerformanceDetailActivity extends AppCompatActivity {

    public static final String PERFORMANCE = "performance";
    public TextView artist;
    public TextView region;
    public TextView genre;
    public TextView pdate;
    public TextView ptime;
    public TextView detail;

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

        Performance p = (Performance) getIntent().getSerializableExtra(PERFORMANCE);

        collapsingToolbar.setTitle(p.getTitle());

//        artist = (TextView)findViewById(R.id.performance_artist);
        pdate = (TextView)findViewById(R.id.performance_date);
        ptime = (TextView)findViewById(R.id.performance_time);
        region = (TextView)findViewById(R.id.performance_location);
        detail = (TextView)findViewById(R.id.performance_detail);

        pdate.setText(p.getPdate());
        ptime.setText(p.getPtime());
        region.setText(p.getRegion());
        detail.setText(p.getContent());

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
