package me.hassans.www.popularmovies;

import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import me.hassans.www.popularmovies.adapter.MovieAdapter;
import me.hassans.www.popularmovies.api.models.MovieResults;
import me.hassans.www.popularmovies.api.MoviesDB;
import me.hassans.www.popularmovies.api.models.Result;
import me.hassans.www.popularmovies.api.ServiceGenerator;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class MainActivity extends AppCompatActivity {


    private MovieAdapter movieAdapter;
    private List<Result> moviesResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);
        Toolbar myToolbar = (Toolbar)findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        RecyclerView rv = (RecyclerView) findViewById(R.id.main_image_recycler);
        moviesResult = new ArrayList<>();
        movieAdapter = new MovieAdapter(moviesResult, this);
        rv.setAdapter(movieAdapter);
        rv.setLayoutManager(new GridLayoutManager(this, getColumnsToShow()));

        setupSpinner();


    }





    @NonNull
    private Callback<MovieResults> getCallback() {
        return new Callback<MovieResults>() {
            @Override
            public void onResponse(Response<MovieResults> response, Retrofit retrofit) {
                List<Result> results = response.body().getResults();
                moviesResult.clear();
                moviesResult.addAll(results);
                movieAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Throwable t) {
                Toast.makeText(MainActivity.this, "An error has occurred", Toast.LENGTH_LONG).show();
            }
        };
    }


    private void setupSpinner() {
        Spinner spinner = (Spinner)findViewById(R.id.sort_spnr);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.search_spinner_array,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter); // set the adapter to provide layout of rows and content
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                MoviesDB moviesService = ServiceGenerator.getMoviesService(MoviesDB.class);
                switch (position) {
                    case 0: moviesService.getMoviesByPopularity().enqueue(getCallback());
                        break;
                    case 1:moviesService.getMoviesByRating().enqueue(getCallback());
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        //pull down data
        //populate the images
    }



    public int getColumnsToShow() {
        int orientation = getResources().getConfiguration().orientation;
        if(orientation == Configuration.ORIENTATION_PORTRAIT){
            return 2;
        }else{
            return 5;
        }

    }
}
