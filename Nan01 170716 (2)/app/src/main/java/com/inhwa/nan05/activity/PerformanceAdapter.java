package com.inhwa.nan05.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.inhwa.nan05.R;

import java.util.List;

/**
 * Created by Inhwa_ on 2017-06-19.
 */

public class PerformanceAdapter extends RecyclerView.Adapter<PerformanceAdapter.MyViewHolder> {

    private Context mContext;
    private List<Performance> performanceList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public TextView region;
        public TextView genre;
        public TextView pdate;
        public TextView ptime;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.card_title);
            region = (TextView) view.findViewById(R.id.card_region);
            genre = (TextView) view.findViewById(R.id.card_genre);
            pdate = (TextView) view.findViewById(R.id.card_date);
            ptime = (TextView) view.findViewById(R.id.card_time);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = v.getContext();
                    Intent intent = new Intent(context, PerformanceDetailActivity.class);
                    intent.putExtra(PerformanceDetailActivity.PERFORMANCE, performanceList.get(getAdapterPosition()));
                    context.startActivity(intent);
                }
            });

            // Adding Snackbar to Action Button inside card
            Button button = (Button)itemView.findViewById(R.id.action_button);
            button.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    Snackbar.make(v, "Action is pressed",
                            Snackbar.LENGTH_LONG).show();
                }
            });

            final ImageButton favoriteImageButton =
                    (ImageButton) itemView.findViewById(R.id.favorite_button);
            favoriteImageButton.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    Snackbar.make(v, "좋아요를 눌렀습니다.",
                            Snackbar.LENGTH_LONG).show();
                    favoriteImageButton.setColorFilter(Color.RED);
                }
            });

            final ImageButton shareImageButton = (ImageButton) itemView.findViewById(R.id.scrap_button);
            shareImageButton.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    Snackbar.make(v, "스크랩 되었습니다.",
                            Snackbar.LENGTH_LONG).show();
                    shareImageButton.setColorFilter(Color.YELLOW);
                }
            });

        }
    }


    public PerformanceAdapter(Context mContext, List<Performance> albumList) {
        this.mContext = mContext;
        this.performanceList = albumList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.performance_card, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        Performance performance = performanceList.get(position);

        holder.title.setText(performance.getTitle());
        holder.region.setText(performance.getRegion());
        holder.genre.setText(performance.getGenre());
        holder.pdate.setText(performance.getPdate());
        holder.ptime.setText(performance.getPtime());

//        // loading album cover using Glide library
//        Glide.with(mContext).load(album.getThumbnail()).into(holder.thumbnail);

    }

    @Override
    public int getItemCount() {
        return performanceList.size();
    }
}
