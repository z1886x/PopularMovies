package me.hassans.www.popularmovies;

import android.content.res.Configuration;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.squareup.okhttp.HttpUrl;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import me.hassans.www.popularmovies.adapter.MovieAdapter;
import me.hassans.www.popularmovies.api.models.MovieResults;
import me.hassans.www.popularmovies.api.MoviesDB;
import me.hassans.www.popularmovies.api.models.Result;
import me.hassans.www.popularmovies.api.ServiceGenerator;
import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
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


    }



    private void getMovies() {
        MoviesDB moviesService = ServiceGenerator.getMoviesService(MoviesDB.class);
        moviesService.getMovies().enqueue(new Callback<MovieResults>() {
            @Override
            public void onResponse(Response<MovieResults> response, Retrofit retrofit) {
                List<Result> results = response.body().getResults();
                moviesResult.addAll(results);
                movieAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Throwable t) {
                Toast.makeText(MainActivity.this, "An error has occurred", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        setUpSortActionView(menu);

        return true;
    }

    private void setUpSortActionView(Menu menu) {
        MenuItem item = menu.findItem(R.id.action_spinner);
        Spinner spinner = (Spinner) MenuItemCompat.getActionView(item);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.search_spinner_array,R.layout.spinner_item);
        adapter.setDropDownViewResource(R.layout.spinner_item);
        spinner.setAdapter(adapter); // set the adapter to provide layout of rows and content
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this,"SELECTED", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        getMovies();
        //pull down data
        //populate the images
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_spinner) {
            return true;
        }

        return super.onOptionsItemSelected(item);
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
