package me.hassans.www.popularmovies;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import me.hassans.www.popularmovies.api.models.Result;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();
        if(intent != null){
            Result movie  = intent.getParcelableExtra("EXTRA_MOVIE");
            Toast.makeText(this, movie.getOriginalTitle(), Toast.LENGTH_SHORT).show();
        }
    }
}
