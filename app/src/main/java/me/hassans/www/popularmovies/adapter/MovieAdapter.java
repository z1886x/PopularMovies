package me.hassans.www.popularmovies.adapter;

import android.content.Context;
import android.graphics.Movie;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import me.hassans.www.popularmovies.R;
import me.hassans.www.popularmovies.api.models.Result;

/**
 * Created by hassan on 11/15/2015.
 */
public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {
    private final Context mContext;
    private List<Result> mMovies;

    public MovieAdapter(List<Result> mMovies, Context context) {
        this.mMovies = mMovies;
        this.mContext = context.getApplicationContext();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View v  =layoutInflater.inflate(R.layout.movie_grid_fl, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        //get data
        Result currentResult = mMovies.get(position);
        String title = currentResult.getTitle();
        String currentImageSize = mContext.getString(R.string.default_api_image_size);
        Picasso.with(mContext).load(currentResult.getPosterUri(currentImageSize)).into(holder.movieImageIv);
        holder.movieTitleTv.setText(title);


    }

    @Override
    public int getItemCount() {
        return mMovies.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView movieTitleTv;
        public ImageView movieImageIv;

        public ViewHolder(View itemView) {
            super(itemView);
            movieImageIv = (ImageView) itemView.findViewById(R.id.movie_banner_iv);
            movieTitleTv = (TextView) itemView.findViewById(R.id.movie_title_tv);
        }
    }
}
