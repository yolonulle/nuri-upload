package com.inhwa.nan05.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.inhwa.nan05.R;
import com.squareup.picasso.Picasso;

/**
 * Created by Inhwa_ on 2017-03-22.
 */

public class ListByRegionFragment extends Fragment{

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        RecyclerView recyclerView = (RecyclerView) inflater.inflate(R.layout.recycler_view,container,false);
        ContentAdapter adapter = new ContentAdapter(recyclerView.getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        // Set padding for Tiles
        int tilePadding = getResources().getDimensionPixelSize(R.dimen.tile_padding);
        recyclerView.setPadding(tilePadding, tilePadding, tilePadding, tilePadding);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return recyclerView;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public static ImageView picture;
        public TextView name;
        public ViewHolder(LayoutInflater inflater, ViewGroup parent){
            super(inflater.inflate(R.layout.item_region, parent, false));
            picture = (ImageView) itemView.findViewById(R.id.region_imageview);
            name = (TextView) itemView.findViewById(R.id.region_textview);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = v.getContext();
                    Intent intent = new Intent(context, ListOfPerformanceActivity.class);
                    intent.putExtra(ListOfPerformanceActivity.EXTRA_POSITION, getAdapterPosition());
                    intent.putExtra(ListOfPerformanceActivity.EXTRA_SELECTION, 0);
                    context.startActivity(intent);
                }
            });

        }
    }

    public static class ContentAdapter extends RecyclerView.Adapter<ViewHolder>{

        private static final int LENGTH = 4;
        private final String[] mRegions;
        private final String[] mRegionPictures;
        private Context context;

        public ContentAdapter(Context context){
            this.context = context;
            Resources resources = context.getResources();
            mRegions = resources.getStringArray(R.array.region);
            mRegionPictures = resources.getStringArray(R.array.region_picture);
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ListByRegionFragment.ViewHolder(LayoutInflater.from(parent.getContext()),parent);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            Picasso.with(context).load(mRegionPictures[position % mRegionPictures.length]).fit().into(ViewHolder.picture);
            holder.name.setText("#"+mRegions[position % mRegions.length]);
        }

        @Override
        public int getItemCount() {
            return LENGTH;
        }
    }
}
