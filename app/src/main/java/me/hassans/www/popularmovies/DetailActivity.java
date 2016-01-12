package me.hassans.www.popularmovies;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import me.hassans.www.popularmovies.api.models.Result;
import me.hassans.www.popularmovies.constants.ImageSizeConstants;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_MOVIE = "EXTRA_MOVIE";
    private Result movie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar myToolbar = (Toolbar)findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        Intent intent = getIntent();
        if(intent != null){
            movie = intent.getParcelableExtra(EXTRA_MOVIE);
            Toast.makeText(this, movie.getOriginalTitle(), Toast.LENGTH_SHORT).show();
            loadMovieInfo();
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private void loadMovieInfo() {
        ImageView imageView = (ImageView) findViewById(R.id.detail_imageView);
        Picasso.with(this).load(movie.getPosterUri(ImageSizeConstants.w500)).into(imageView);

        getSupportActionBar().setTitle(movie.getTitle());

        TextView releaseTV = (TextView)findViewById(R.id.detail_movieRelease);
        releaseTV.setText(movie.getReleaseDate());

        TextView userRatingTV = (TextView)findViewById(R.id.detail_movieVoteAvg);
        userRatingTV.setText(movie.getVoteAverage().toString());

        TextView synopsisTV = (TextView)findViewById(R.id.detail_movieSynopsis);
        synopsisTV.setText(movie.getOverview());
    }
}
