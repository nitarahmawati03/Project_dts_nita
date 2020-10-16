package com.example.retrofit_dts;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.retrofit_dts.Models.Movie;
import com.example.retrofit_dts.Rest.ApiClient;
import com.example.retrofit_dts.Rest.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailActivity extends AppCompatActivity {
    private Integer id;
    private final static String API_KEY = "a8eb1d63a1ab074c164ae06f25b90e08";
    TextView title,date,rating,description;
    ImageView backdrop,poster;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        backdrop = (ImageView)findViewById(R.id.movie_backdrop);
        poster = (ImageView)findViewById(R.id.movie_poster);
        title = (TextView)findViewById(R.id.movie_title);
        date = (TextView)findViewById(R.id.movie_date);
        rating = (TextView)findViewById(R.id.movie_ratting);
        description = (TextView)findViewById(R.id.movie_description);
        id = getIntent().getIntExtra("id",0);
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        retrofit2.Call<Movie> call = apiService.getMovieDetails(id,API_KEY);
        call.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(retrofit2.Call<Movie> call, Response<Movie> response) {
                Movie movie = response.body();
                Glide.with(getApplicationContext()).asBitmap().load("https://image.tmdb.org/t/p/w1400_and_h450_face"+movie.getBackdrop_path()).into(backdrop);
                Glide.with(getApplicationContext()).asBitmap().load("https://image.tmdb.org/t/p/w600_and_h900_bestv2"+movie.getPoster_path()).into(poster);
                title.setText(movie.getTitle());
                date.setText(movie.getReleaseDate());
                rating.setText(movie.getVoteAverage().toString());
                description.setText(movie.getOverview());
            }
            @Override
            public void onFailure(Call<Movie> call, Throwable t) {
                // Log error here since request failed
//                Log.e(TAG, t.toString());
            }
        });
    }
}

